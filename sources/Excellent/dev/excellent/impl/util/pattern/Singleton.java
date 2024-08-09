package dev.excellent.impl.util.pattern;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class Singleton<T> {
    private final Supplier<T> supplier;
    private T inst;

    public static <T> Singleton<T> create(Supplier<T> supplier) {
        return new Singleton<>(supplier);
    }

    public T get() {
        if (inst == null) {
            inst = supplier.get();
        }

        return inst;
    }
}