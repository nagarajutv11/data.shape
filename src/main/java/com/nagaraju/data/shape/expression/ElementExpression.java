package com.nagaraju.data.shape.expression;

import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.nagaraju.data.shape.core.Data;

public class ElementExpression implements Expression, SplitExpression {

	@Override
	public JsonElement eval(Data data) {
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

	@Override
	public void eval(Data data, Consumer<Data> object) {
		// TODO Auto-generated method stub

	}

}
