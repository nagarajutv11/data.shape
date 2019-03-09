package com.nagaraju.data.shape.core;

import com.google.gson.JsonElement;

public class Data {

	private Data parent;
	private JsonElement data;

	public Data(Data parent, JsonElement data) {
		this.parent = parent;
		this.data = data;
	}

	public Data(Data data) {
		this(data, data.data);
	}

	public JsonElement getData() {
		return data;
	}

}
