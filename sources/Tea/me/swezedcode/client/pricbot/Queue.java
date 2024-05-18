package me.swezedcode.client.pricbot;

import java.util.*;

public class Queue {
	private Vector _queue;

	public Queue() {
		this._queue = new Vector();
	}

	public void add(final Object o) {
		synchronized (this._queue) {
			this._queue.addElement(o);
			this._queue.notify();
		}
		// monitorexit(this._queue)
	}

	public void addFront(final Object o) {
		synchronized (this._queue) {
			this._queue.insertElementAt(o, 0);
			this._queue.notify();
		}
		// monitorexit(this._queue)
	}

	public Object next() {
		Object o = null;
		synchronized (this._queue) {
			if (this._queue.size() == 0) {
				try {
					this._queue.wait();
				} catch (InterruptedException e) {
					// monitorexit(this._queue)
					return null;
				}
			}
			try {
				o = this._queue.firstElement();
				this._queue.removeElementAt(0);
			} catch (ArrayIndexOutOfBoundsException e2) {
				throw new InternalError("Race hazard in Queue object.");
			}
		}
		// monitorexit(this._queue)
		return o;
	}

	public boolean hasNext() {
		return this.size() != 0;
	}

	public void clear() {
		synchronized (this._queue) {
			this._queue.removeAllElements();
		}
		// monitorexit(this._queue)
	}

	public int size() {
		return this._queue.size();
	}
}
