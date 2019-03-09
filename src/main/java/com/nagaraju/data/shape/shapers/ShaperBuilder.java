package com.nagaraju.data.shape.shapers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Template;
import com.nagaraju.data.shape.expression.ExpressionBuilder;
import com.nagaraju.data.shape.handlers.DirectoryHandler;
import com.nagaraju.data.shape.handlers.RequestHandler;
import com.nagaraju.data.shape.handlers.WebhookHandler;

@Service
public class ShaperBuilder {

	private static final String TYPE = "type";
	private static final String SPLIT_SHAPER = "split";
	private static final String MAP_SHAPER = "map";
	private static final String FILTER_SHAPER = "filter";
	private static final String COLLECT_SHAPER = "collect";
	private static final String DISTRIBUTE_SHAPER = "distribute";

	@Autowired
	private WebhookHandler webhookHandler;
	@Autowired
	private RequestHandler requestHandler;
	@Autowired
	private DirectoryHandler directoryHandler;
	@Autowired
	private ExpressionBuilder expressionBuilder;

	public Shaper build(Template template) throws InvalidTemplateException {
		JsonArray json = template.getJson();
		Shaper root = new RootShaper();
		Iterator<JsonElement> iterator = json.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			if (element instanceof JsonObject) {
				JsonObject shapeTemplate = (JsonObject) element;
				Shaper next = createShaper(shapeTemplate);
				next.init(shapeTemplate, expressionBuilder);
				root.next(next);
			} else {
				throw new InvalidTemplateException("Can not convert element to JSONObject: " + element);
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
