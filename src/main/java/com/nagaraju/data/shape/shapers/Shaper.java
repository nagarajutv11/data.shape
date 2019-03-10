package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;

public interface Shaper {

	void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException;

	String name();

	void shape(Data data);
}
