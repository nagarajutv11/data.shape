package com.nagaraju.data.shape.expression;

import java.util.Calendar;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class ThisMonthExpression implements Expression {

	@Override
	public JsonElement eval(Data data) {
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.DATE, 1);
		thisMonth.set(Calendar.HOUR, 0);
		thisMonth.set(Calendar.MINUTE, 0);
		thisMonth.set(Calendar.SECOND, 0);
		return new JsonPrimitive(thisMonth.getTime().getTime());
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public void addArg(Expression arg, int index) {
	}
}
