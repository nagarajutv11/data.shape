package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.ExpressionBuilder;

public class EndShaper implements Shaper {

	@Override
	public void next(Shaper next) {
		throw new UnsupportedOperationException("We can't add another Shaper next to EndShaper");
	}

	@Override
	public void init(JsonObject shapeTemplate, ExpressionBuilder expressionBuilder) throws InvalidTemplateException {
		// Nothing to init
	}

	@Override
	public void shape(Data data) {
		// Not decided yet
	}

}
