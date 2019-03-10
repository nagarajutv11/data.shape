package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.Expression;

public class BranchShaper extends BaseShaper {

	private static final String IF_ATTR = "if";
	private static final String THEN_ATTR = "then";
	private static final String ELSE_ATTR = "else";
	private Shaper thenShaper;
	private Shaper elseShaper;
	private Expression condition;

	public BranchShaper(String name) {
		super(name);
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		if (!shapeTemplate.has(IF_ATTR)) {
			throw new InvalidTemplateException("`if` is required: " + shapeTemplate);
		}
		String expStr = shapeTemplate.get(IF_ATTR).getAsString();
		condition = context.buildExpression(expStr);
		if (!condition.isBoolean()) {
			throw new InvalidTemplateException("Invalid expression for if condition: " + expStr);
		}
		thenShaper = readShaper(THEN_ATTR, shapeTemplate, context);
		elseShaper = readShaper(ELSE_ATTR, shapeTemplate, context);
	}

	private Shaper readShaper(String thenAttr, JsonObject shapeTemplate, ShaperInitContext context)
			throws InvalidTemplateException {
		if (!shapeTemplate.has(thenAttr)) {
			throw new InvalidTemplateException("`" + thenAttr + "` is required: " + shapeTemplate);
		}
		String thenStr = shapeTemplate.get(thenAttr).getAsString();
		Shaper shaper = context.getShaper(thenStr);
		if (shaper == null) {
			throw new InvalidTemplateException("Shaper not found with name: " + thenStr);
		}
		return shaper;
	}

	@Override
	public void shape(Data data) {
		JsonElement eval = condition.eval(data);
		if (eval.getAsBoolean()) {
			thenShaper.shape(new Data(data));
		} else {
			elseShaper.shape(new Data(data));
		}
	}
}
