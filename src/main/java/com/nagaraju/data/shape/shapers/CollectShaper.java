package com.nagaraju.data.shape.shapers;

import com.nagaraju.data.shape.core.Data;

public class CollectShaper extends BaseShaper implements Shaper {

	public CollectShaper(String name) {
		super(name);
	}

	@Override
	public void shape(Data data) {
		super.shape(new Data(data));
	}
}
