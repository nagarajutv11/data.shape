package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class SizeExpression implements Expression {

	private Expression collection;

	@Override
	public JsonElement eval(Data data) {
		return new JsonPrimitive(collection.eval(data).getAsJsonArray().size());
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
