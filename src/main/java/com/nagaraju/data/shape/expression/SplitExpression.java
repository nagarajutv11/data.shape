package com.nagaraju.data.shape.expression;

import java.util.function.Consumer;

import com.nagaraju.data.shape.core.Data;

public interface SplitExpression extends Expression {

	void eval(Data data, Consumer<Data> object);

}
