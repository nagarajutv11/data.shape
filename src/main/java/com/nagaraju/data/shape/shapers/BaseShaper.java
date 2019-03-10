package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;

public abstract class BaseShaper implements Shaper {

	private static final String NEXT_ATTR = "next";
	private Shaper next;
	private String name;

	protected BaseShaper(String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		if (!shapeTemplate.has(NEXT_ATTR)) {
			throw new InvalidTemplateException("next not found in shape template: " + shapeTemplate);
		}
		next = context.getShaper(shapeTemplate.get(NEXT_ATTR).getAsString());
		if (next == null) {
			throw new InvalidTemplateException(
					"Invalid shaper name in the `next`: " + shapeTemplate.get(NEXT_ATTR).getAsString());
		}
	}

	@Override
	public void shape(Data data) {
		if (next != null) {
			next.shape(data);
		} else {
			throw new UnsupportedOperationException("We reached end of the shaper: " + this);
		}
	}
}
