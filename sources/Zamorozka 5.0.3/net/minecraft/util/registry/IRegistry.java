package net.minecraft.util.registry;

import javax.annotation.Nullable;
import java.util.Set;

public interface IRegistry<K, V> extends Iterable<V> {
    @Nullable
    V getObject(K name);

    /**
     * Register an object on this registry.
     */
    void putObject(K key, V value);

    Set<K> getKeys();
}
