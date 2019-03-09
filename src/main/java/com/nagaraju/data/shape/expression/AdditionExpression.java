package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class AdditionExpression implements Expression {

	private Expression left;
	private Expression right;

	@Override
	public void addArg(Expression arg, int index) {
		if (index == 0) {
			left = arg;
		} else {
			right = arg;
		}
	}

	@Override
	public JsonElement eval(Data data) {
		JsonElement leftVal = left.eval(data);
		JsonElement rightVal = right.eval(data);

		if (!rightVal.isJsonPrimitive() || !leftVal.isJsonPrimitive()) {
			return new JsonPrimitive(leftVal.getAsString() + rightVal.getAsString());
		}

		JsonPrimitive leftPrim = leftVal.getAsJsonPrimitive();
		JsonPrimitive rightPrim = rightVal.getAsJsonPrimitive();
		if (leftPrim.isNumber() && rightPrim.isNumber()) {
			if (leftPrim.getAsString().contains(".") || rightPrim.getAsString().contains(".")) {
				return new JsonPrimitive(leftPrim.getAsDouble() + rightPrim.getAsDouble());
			}
			return new JsonPrimitive(leftPrim.getAsLong() + rightPrim.getAsLong());
		}
		return new JsonPrimitive(leftVal.getAsString() + rightVal.getAsString());
	}

	@Override
	public boolean isBoolean() {
		return false;
	}
}
