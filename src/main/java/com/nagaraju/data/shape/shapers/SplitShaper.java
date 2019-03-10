package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.Expression;
import com.nagaraju.data.shape.expression.SplitExpression;

public class SplitShaper extends BaseShaper implements Shaper {

	private static final String EXP = "exp";
	private SplitExpression expression;

	public SplitShaper(String name) {
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
		if (!(expr instanceof SplitExpression)) {
			throw new InvalidTemplateException("Invalid expression for split shape: " + expStr);
		}
		this.expression = (SplitExpression) expr;
	}

	@Override
	public void shape(Data data) {
		expression.eval(data, val -> super.shape(new Data(data, val)));
	}
}
