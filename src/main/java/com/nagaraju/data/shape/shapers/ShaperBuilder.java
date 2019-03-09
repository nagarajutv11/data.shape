package com.nagaraju.data.shape.shapers;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Template;
import com.nagaraju.data.shape.handlers.DirectoryHandler;
import com.nagaraju.data.shape.handlers.RequestHandler;
import com.nagaraju.data.shape.handlers.WebhookHandler;

public final class ShaperBuilder {

	private static final String TYPE = "type";
	private static final String SPLIT_SHAPER = "split";
	private static final String MAP_SHAPER = "map";
	private static final String FILTER_SHAPER = "filter";
	private static final String COLLECT_SHAPER = "collect";
	private static final String DISTRIBUTE_SHAPER = "distribute";

	private WebhookHandler webhookHandler;
	private RequestHandler requestHandler;
	private DirectoryHandler directoryHandler;

	private ShaperBuilder() {
	}

	public ShaperBuilder getInstance() {
		return new ShaperBuilder();
	}

	public Shaper build(Template template) throws InvalidTemplateException {
		JsonArray json = template.getJson();
		Shaper root = new RootShaper();
		Iterator<JsonElement> iterator = json.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() instanceof JsonObject) {
				JsonObject shapeTemplate = (JsonObject) iterator.next();
				Shaper next = createShaper(shapeTemplate);
				next.init(shapeTemplate);
				root.next(next);
			} else {
				throw new InvalidTemplateException("Can not convert element to JSONObject: " + iterator.next());
			}
		}
		root.next(new EndShaper());
		return root;
	}

	private Shaper createShaper(JsonObject shapeTemplate) throws InvalidTemplateException {
		if (!shapeTemplate.has(TYPE)) {
			throw new InvalidTemplateException("type not found in shape template: " + shapeTemplate);
		}
		String type = shapeTemplate.get(TYPE).getAsString();
		switch (type) {
		case SPLIT_SHAPER:
			return new SplitShaper();
		case MAP_SHAPER:
			return new MapShaper();
		case FILTER_SHAPER:
			return new FilterShaper();
		case COLLECT_SHAPER:
			return new CollectShaper();
		case DISTRIBUTE_SHAPER:
			return new DistributeShaper(webhookHandler, requestHandler, directoryHandler);
		default:
			throw new InvalidTemplateException("Invalid shape type found: " + type);
		}
	}
}
