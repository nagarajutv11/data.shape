package com.nagaraju.data.shape.core;

import com.google.gson.JsonArray;

public class Template {

	private JsonArray template;

	public Template(JsonArray template) {
		this.template = template;
	}

	public JsonArray getJson() {
		return template;
	}

}
