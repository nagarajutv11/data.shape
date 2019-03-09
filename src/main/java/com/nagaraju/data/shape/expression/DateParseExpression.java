package com.nagaraju.data.shape.expression;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class DateParseExpression implements Expression {

	private Expression date;
	private Expression pattern;

	@Override
	public JsonElement eval(Data data) {
		String patternStr = pattern.eval(data).getAsString();
		SimpleDateFormat format = new SimpleDateFormat(patternStr);
		String dateStr = null;
		try {
			dateStr = date.eval(data).getAsString();
			return new JsonPrimitive(format.parse(dateStr).getTime());
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse the date: " + patternStr + " => " + dateStr, e);
		}
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public void addArg(Expression arg, int index) {
		if (index == 0) {
			date = arg;
		} else {
			pattern = arg;
		}
	}
}
