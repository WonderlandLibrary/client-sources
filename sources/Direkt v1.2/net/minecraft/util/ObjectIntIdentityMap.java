package net.minecraft.util;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class ObjectIntIdentityMap<T> implements IObjectIntIterable<T> {
	private final IdentityHashMap<T, Integer> identityMap;
	private final List<T> objectList;

	public ObjectIntIdentityMap() {
		this(512);
	}

	public ObjectIntIdentityMap(int expectedSize) {
		this.objectList = Lists.<T> newArrayListWithExpectedSize(expectedSize);
		this.identityMap = new IdentityHashMap(expectedSize);
	}

	public void put(T key, int value) {
		this.identityMap.put(key, Integer.valueOf(value));

		while (this.objectList.size() <= value) {
			this.objectList.add(null);
		}

		this.objectList.set(value, key);
	}

	public int get(T key) {
		Integer integer = this.identityMap.get(key);
		return integer == null ? -1 : integer.intValue();
	}

	@Nullable
	public final T getByValue(int value) {
		return (value >= 0) && (value < this.objectList.size()) ? this.objectList.get(value) : null;
	}

	@Override
	public Iterator<T> iterator() {
		return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
	}

	public int size() {
		return this.identityMap.size();
	}
}
