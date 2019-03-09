package com.nagaraju.data.shape.expression;

import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nagaraju.data.shape.core.Data;

public interface SplitExpression extends Expression {

	JsonArray eval(Data data);

	default void eval(Data data, Consumer<JsonElement> action) {
		eval(data).forEach(action);
	}

}
