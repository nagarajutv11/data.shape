package com.nagaraju.data.shape.shapers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.Expression;

public class MapShaper extends BaseShaper implements Shaper {

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String ONLY = "only";
	private Map<String, Expression> addedElements;
	private Map<String, Expression> onlyElements;
	private Set<String> removedElements;

	public MapShaper(String name) {
		super(name);
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		super.init(shapeTemplate, context);
		if (shapeTemplate.has(ONLY)) {
			onlyElements = readElements(shapeTemplate.getAsJsonObject(ONLY), context);
			if (shapeTemplate.has(ADD) || shapeTemplate.has(REMOVE)) {
				throw new InvalidTemplateException(
						"Map shape should not contain `add` or `remove` along with `only` attribute");
			}
		} else {
			if (shapeTemplate.has(ADD)) {
				addedElements = readElements(shapeTemplate.getAsJsonObject(ADD), context);
			}
			if (shapeTemplate.has(REMOVE)) {
				removedElements = new HashSet<>();
				shapeTemplate.getAsJsonArray(REMOVE).forEach(e -> removedElements.add(e.getAsString()));
			}
		}
	}

	private Map<String, Expression> readElements(JsonObject jsonElements, ShaperInitContext context) {
		Map<String, Expression> elements = new HashMap<>();
		jsonElements.entrySet().forEach(
				entry -> elements.put(entry.getKey(), context.buildExpression(entry.getValue().getAsString())));
		return elements;
	}

	@Override
	public void shape(Data data) {
		if (onlyElements != null) {
			JsonObject object = new JsonObject();
			onlyElements.forEach((k, v) -> object.add(k, v.eval(data)));
			super.shape(new Data(data, object));
		} else {
			JsonObject object = (JsonObject) data.getData();
			if (addedElements != null) {
				addedElements.forEach((k, v) -> object.add(k, v.eval(data)));
			}
			if (removedElements != null) {
				removedElements.forEach(object::remove);
			}
			super.shape(new Data(data, object));
		}
	}
}
