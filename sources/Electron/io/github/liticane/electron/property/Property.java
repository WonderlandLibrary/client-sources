package io.github.liticane.electron.property;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
public abstract class Property <T> {

    private final String name;

    @Setter
    private T value;

    private final Supplier<Boolean> dependency;

    public Property(String name, T value, Supplier<Boolean> dependency) {
        this.name = name;
        this.value = value;
        this.dependency = dependency;
    }

    public boolean isVisible() {
        return dependency == null || dependency.get();
    }

}