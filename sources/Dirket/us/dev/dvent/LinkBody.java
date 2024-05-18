package us.dev.dvent;

import java.io.Serializable;

/**
 * @author Foundry
 */
@FunctionalInterface
public interface LinkBody<T> extends Serializable {
    void invoke(T event);
}
