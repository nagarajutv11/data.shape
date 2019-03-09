package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.expression.ExpressionBuilder;

public interface Shaper {

	void init(JsonObject shapeTemplate, ExpressionBuilder expressionBuilder) throws InvalidTemplateException;

	void next(Shaper next);

	void shape(Data data);
}
