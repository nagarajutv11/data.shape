package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class AfterExpression implements Expression {
	private Expression right;
	private Expression left;

	@Override
	public JsonElement eval(Data data) {
		long rightLong = right.eval(data).getAsLong();
		long leftLong = left.eval(data).getAsLong();
		return new JsonPrimitive(leftLong > rightLong);
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public void addArg(Expression arg, int index) {
		if (index == 0) {
			left = arg;
		} else {
			right = arg;
		}
	}
}
