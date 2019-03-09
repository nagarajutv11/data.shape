package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;

public class CollectShaper extends BaseShaper implements Shaper {

	@Override
	public void shape(Data data) {
		super.shape(new Data(data));
	}

	@Override
	public void init(JsonObject shapeTemplate) {
		// Not decided yet
	}

}
