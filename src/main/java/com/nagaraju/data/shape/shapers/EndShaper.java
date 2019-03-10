package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;

public class EndShaper implements Shaper {

	@Override
	public String name() {
		return "end";
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		// Nothing to init
	}

	@Override
	public void shape(Data data) {
		// Not decided yet
	}

}
