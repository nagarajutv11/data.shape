package com.nagaraju.data.shape.expression;

import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class BooleanExpression implements Expression {

	private boolean value;

	public BooleanExpression(boolean value) {
		this.value = value;
	}

	@Override
	public JsonPrimitive eval(Data data) {
		return new JsonPrimitive(value);
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public void addArg(Expression arg, int index) {
	}
}
