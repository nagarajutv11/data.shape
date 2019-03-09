package com.nagaraju.data.shape.expression;

import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class NumberExpression implements Expression {

	private String number;

	public NumberExpression(String number) {
		this.number = number;
	}

	@Override
	public JsonPrimitive eval(Data data) {
		if (number.contains(".")) {
			return new JsonPrimitive(Double.valueOf(number));
		}
		return new JsonPrimitive(Long.valueOf(number));
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public void addArg(Expression arg, int index) {
	}
}
