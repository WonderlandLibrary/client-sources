package us.dev.api.registry;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Foundry on 11/15/2015.
 */
public interface Registry<T> {
    boolean register(T p0);

    boolean unregister(T p0);

    boolean has(String p0);

    Optional<T> get(String p0);

    Collection<T> getElements();
}
