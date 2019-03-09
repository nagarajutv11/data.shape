package com.nagaraju.data.shape.shapers;

import com.nagaraju.data.shape.core.Data;

public abstract class BaseShaper implements Shaper {

	private Shaper next;

	@Override
	public final void next(Shaper next) {
		if (this.next != null) {
			this.next.next(next);
		} else {
			this.next = next;
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
