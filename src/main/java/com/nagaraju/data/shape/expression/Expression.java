package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.nagaraju.data.shape.core.Data;

public interface Expression<T extends JsonElement> {

	T eval(Data data);

	boolean isBoolean();
}
