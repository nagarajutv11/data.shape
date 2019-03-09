package com.nagaraju.data.shape.expression;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nagaraju.data.shape.core.Data;

public class ElementExpression implements Expression, SplitExpression {

	private Expression collection;

	@Override
	public JsonArray eval(Data data) {
		JsonElement element;
		if (collection == null) {
			element = data.getData();
		} else {
			element = collection.eval(data);
		}
		if (element.isJsonArray()) {
			return element.getAsJsonArray();
		} else {
			JsonArray array = new JsonArray();
			array.add(element);
			return array;
		}
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public void addArg(Expression arg, int index) {
		collection = arg;
	}
}
