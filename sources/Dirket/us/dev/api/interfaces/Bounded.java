package us.dev.api.interfaces;

/**
 * @author Foundry
 */
public interface Bounded<T extends Comparable<T>> {
    T upperBound();

    T lowerBound();

    default T clamp(T value) {
        return (value.compareTo(upperBound()) > 0 ? upperBound() : (value.compareTo(lowerBound()) < 0 ? lowerBound() : value));
    }
}
