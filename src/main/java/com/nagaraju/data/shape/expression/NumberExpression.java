package com.nagaraju.data.shape.expression;

import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class NumberExpression implements Expression {
	private String number;

	public NumberExpression(String number) {
		this.number = number;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JsonPrimitive eval(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addArg(Expression arg, int index) {
		// TODO Auto-generated method stub

	}

}
