package com.nagaraju.data.shape.shapers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.Expression;
import com.nagaraju.data.shape.expression.ExpressionBuilder;

public class MapShaper extends BaseShaper implements Shaper {

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String ONLY = "only";
	private Map<String, Expression> addedElements;
	private Map<String, Expression> onlyElements;
	private Set<String> removedElements;

	@Override
	public void init(JsonObject shapeTemplate, ExpressionBuilder expressionBuilder) throws InvalidTemplateException {
		if (shapeTemplate.has(ONLY)) {
			onlyElements = readElements(shapeTemplate.getAsJsonObject(ONLY), expressionBuilder);
			if (shapeTemplate.has(ADD) || shapeTemplate.has(REMOVE)) {
				throw new InvalidTemplateException(
						"Map shape should not contain `add` or `remove` along with `only` attribute");
			}
		} else {
			if (shapeTemplate.has(ADD)) {
				addedElements = readElements(shapeTemplate.getAsJsonObject(ADD), expressionBuilder);
			}
			if (shapeTemplate.has(REMOVE)) {
				removedElements = new HashSet<>();
				shapeTemplate.getAsJsonArray(REMOVE).forEach(e -> removedElements.add(e.getAsString()));
			}
		}
	}

	private Map<String, Expression> readElements(JsonObject jsonElements, ExpressionBuilder expressionBuilder) {
		Map<String, Expression> elements = new HashMap<>();
		jsonElements.entrySet().forEach(
				entry -> elements.put(entry.getKey(), expressionBuilder.build(entry.getValue().getAsString())));
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
			addedElements.forEach((k, v) -> object.add(k, v.eval(data)));
			removedElements.forEach(object::remove);
		}
	}
}
