package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.Expression;

public class FilterShaper extends BaseShaper implements Shaper {

	private static final String EXP = "exp";
	private Expression condition;

	public FilterShaper(String name) {
		super(name);
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		super.init(shapeTemplate, context);
		if (!shapeTemplate.has(EXP)) {
			throw new InvalidTemplateException("`exp` is required: " + shapeTemplate);
		}
		String expStr = shapeTemplate.get(EXP).getAsString();
		Expression expr = context.buildExpression(expStr);
		if (!expr.isBoolean()) {
			throw new InvalidTemplateException("Invalid filter expression, it should be bookean: " + expStr);
		}
		condition = expr;
	}

	@Override
	public void shape(Data data) {
		JsonElement eval = condition.eval(data);
		if (eval.getAsBoolean()) {
			super.shape(new Data(data));
		}
	}
}
