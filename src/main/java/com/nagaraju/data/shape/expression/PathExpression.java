package com.nagaraju.data.shape.expression;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.nagaraju.data.shape.core.Data;

public class PathExpression implements Expression {

	private String[] path;

	public PathExpression(String[] path) {
		this.path = path;
	}

	@Override
	public JsonElement eval(Data data) {
		JsonElement element = data.getData();
		return findValue(element, path, 0);
	}

	private JsonElement findValue(JsonElement element, String[] path, int index) {
		if (path.length == index || element.isJsonArray() || element.isJsonPrimitive()) {
			return element;
		}
		JsonObject object = element.getAsJsonObject();
		if (object.has(path[index])) {
			return findValue(object.get(path[index]), path, index + 1);
		}
		return new JsonPrimitive("");
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public void addArg(Expression arg, int index) {
	}
}
