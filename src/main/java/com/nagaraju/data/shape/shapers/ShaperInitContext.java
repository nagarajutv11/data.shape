package com.nagaraju.data.shape.shapers;

import com.nagaraju.data.shape.expression.Expression;

public interface ShaperInitContext {

	Shaper getShaper(String name);

	Expression buildExpression(String expStr);

}
