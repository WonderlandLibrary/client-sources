package club.bluezenith.module.value;

@FunctionalInterface
public interface ValueConsumer<T> {
    T check(T old, T new_);
}