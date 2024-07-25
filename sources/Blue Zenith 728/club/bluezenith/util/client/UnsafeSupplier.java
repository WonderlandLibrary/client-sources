package club.bluezenith.util.client;

@FunctionalInterface
public interface UnsafeSupplier<T> {
    T get() throws Throwable;
}
