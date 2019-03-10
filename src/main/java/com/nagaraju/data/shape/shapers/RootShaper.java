package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;

public class RootShaper extends BaseShaper implements Shaper {

	private static final String NEXT_ATTR = "next";

	public RootShaper() {
		super("root");
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		JsonObject obj = new JsonObject();
		obj.addProperty(NEXT_ATTR, "main");
		super.init(obj, context);
	}
}
