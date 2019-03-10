package com.nagaraju.data.shape.shapers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Template;
import com.nagaraju.data.shape.expression.Expression;
import com.nagaraju.data.shape.expression.ExpressionBuilder;
import com.nagaraju.data.shape.handlers.DirectoryHandler;
import com.nagaraju.data.shape.handlers.RequestHandler;
import com.nagaraju.data.shape.handlers.WebhookHandler;

@Service
public class ShaperBuilder {

	private static final String TYPE = "type";
	private static final String NAME = "name";
	private static final String SPLIT_SHAPER = "split";
	private static final String MAP_SHAPER = "map";
	private static final String FILTER_SHAPER = "filter";
	private static final String COLLECT_SHAPER = "collect";
	private static final String DISTRIBUTE_SHAPER = "distribute";
	private static final String BRANCH_SHAPER = "branch";

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
		Map<String, Shaper> allShapers = createShapers(json);
		return initShapers(json, allShapers);
	}

	private Shaper initShapers(JsonArray json, Map<String, Shaper> allShapers) throws InvalidTemplateException {
		EndShaper endShaper = new EndShaper();
		allShapers.put(endShaper.name(), endShaper);
		ShaperInitContext context = new ShaperInitContextImpl(allShapers);
		Iterator<JsonElement> iterator = json.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			if (element instanceof JsonObject) {
				JsonObject shapeTemplate = (JsonObject) element;
				String name = shapeTemplate.get(NAME).getAsString();
				Shaper shaper = allShapers.get(name);
				shaper.init(shapeTemplate, context);
			} else {
				throw new InvalidTemplateException("Can not convert element to JSONObject: " + element);
			}
		}
		Shaper root = new RootShaper();
		root.init(null, context);
		return root;
	}

	private class ShaperInitContextImpl implements ShaperInitContext {

		private Map<String, Shaper> allShapers;

		private ShaperInitContextImpl(Map<String, Shaper> allShapers) {
			this.allShapers = allShapers;
		}

		@Override
		public Shaper getShaper(String name) {
			return allShapers.get(name);
		}

		@Override
		public Expression buildExpression(String expStr) {
			return expressionBuilder.build(expStr);
		}

	}

	private Map<String, Shaper> createShapers(JsonArray json) throws InvalidTemplateException {
		Iterator<JsonElement> iterator = json.iterator();
		Map<String, Shaper> allShapers = new HashMap<>();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			if (element instanceof JsonObject) {
				JsonObject shapeTemplate = (JsonObject) element;
				Shaper next = createShaper(shapeTemplate);
				allShapers.put(next.name(), next);
			} else {
				throw new InvalidTemplateException("Can not convert element to JSONObject: " + element);
			}
		}
		allShapers.put("end", new EndShaper());
		return allShapers;
	}

	private Shaper createShaper(JsonObject shapeTemplate) throws InvalidTemplateException {
		if (!shapeTemplate.has(NAME)) {
			throw new InvalidTemplateException("name not found in shape template: " + shapeTemplate);
		}
		if (!shapeTemplate.has(TYPE)) {
			throw new InvalidTemplateException("type not found in shape template: " + shapeTemplate);
		}
		String type = shapeTemplate.get(TYPE).getAsString();
		String name = shapeTemplate.get(NAME).getAsString();
		switch (type) {
		case SPLIT_SHAPER:
			return new SplitShaper(name);
		case MAP_SHAPER:
			return new MapShaper(name);
		case FILTER_SHAPER:
			return new FilterShaper(name);
		case BRANCH_SHAPER:
			return new BranchShaper(name);
		case COLLECT_SHAPER:
			return new CollectShaper(name);
		case DISTRIBUTE_SHAPER:
			return new DistributeShaper(name, webhookHandler, requestHandler, directoryHandler);
		default:
			throw new InvalidTemplateException("Invalid shape type found: " + type);
		}
	}
}
