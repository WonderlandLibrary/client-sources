package net.minecraft.util.registry;

import java.util.*;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public class RegistrySimple<K, V> implements IRegistry<K, V> {
	private static final Logger LOGGER = LogManager.getLogger();
	protected final Map<K, V> registryObjects = this.createUnderlyingMap();
	private Object[] values;

	protected Map<K, V> createUnderlyingMap() {
		return Maps.<K, V> newHashMap();
	}

	@Override
	@Nullable
	public V getObject(@Nullable K name) {
		return this.registryObjects.get(name);
	}

	/**
	 * Register an object on this registry.
	 */
	@Override
	public void putObject(K key, V value) {
		Validate.notNull(key);
		Validate.notNull(value);
		this.values = null;

		if (this.registryObjects.containsKey(key)) {
			LOGGER.debug("Adding duplicate key \'{}\' to registry", new Object[] { key });
		}

		this.registryObjects.put(key, value);
	}

	@Override
	public Set<K> getKeys() {
		return Collections.<K> unmodifiableSet(this.registryObjects.keySet());
	}

	@Nullable
	public V getRandomObject(Random random) {
		if (this.values == null) {
			Collection<?> collection = this.registryObjects.values();

			if (collection.isEmpty()) { return null; }

			this.values = collection.toArray(new Object[collection.size()]);
		}

		return (V) this.values[random.nextInt(this.values.length)];
	}

	/**
	 * Does this registry contain an entry for the given key?
	 */
	public boolean containsKey(K key) {
		return this.registryObjects.containsKey(key);
	}

	@Override
	public Iterator<V> iterator() {
		return this.registryObjects.values().iterator();
	}
}
