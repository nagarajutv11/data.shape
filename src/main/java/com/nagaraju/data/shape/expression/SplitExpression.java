package com.nagaraju.data.shape.expression;

import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.nagaraju.data.shape.core.Data;

public interface SplitExpression extends Expression<JsonArray> {

	void eval(Data data, Consumer<Data> object);

}
