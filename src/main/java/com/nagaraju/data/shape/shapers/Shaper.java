package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;

public interface Shaper {

	void init(JsonObject shapeTemplate) throws InvalidTemplateException;

	void next(Shaper next);

	void shape(Data data);
}
