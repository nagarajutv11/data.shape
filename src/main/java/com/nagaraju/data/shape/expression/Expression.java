package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.nagaraju.data.shape.core.Data;

public interface Expression {

	JsonElement eval(Data data);

	boolean isBoolean();

	void addArg(Expression arg, int index);
}
