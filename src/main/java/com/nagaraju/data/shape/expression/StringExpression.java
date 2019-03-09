package com.nagaraju.data.shape.expression;

import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class StringExpression implements Expression {

	private String value;

	public StringExpression(String value) {
		this.value = value;
	}

	@Override
	public JsonPrimitive eval(Data data) {
		return new JsonPrimitive(value);
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public void addArg(Expression arg, int index) {
	}
}