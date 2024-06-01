package io.github.liticane.clients.feature.property;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@Setter
public class Property {
    public String name;
    protected Supplier<Boolean> visible = () -> true;
    protected Supplier<Boolean> save = () -> true;

    public boolean isVisible() {
        return visible.get();
    }

}
