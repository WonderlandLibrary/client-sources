package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.ColorValue;

import java.awt.*;
import java.util.function.Supplier;

public class ColorBuilder extends AbstractBuilder<ColorValue, Color> {

    private int index;
    private Supplier<Boolean> showIf;

    public ColorBuilder(String name) {
        this.name = name;
    }

    @Override
    public ColorBuilder index(int index) {
        this.index = index;
        return this;
    }

    @Override
    @Deprecated
    public ColorBuilder defaultOf(Color defaultValue) {
        return this;
    }

    @Override
    public ColorBuilder showIf(Supplier<Boolean> supplier) {
        this.showIf = supplier;
        return this;
    }

    @Override
    @Deprecated
    public ColorBuilder whenChanged(ValueConsumer<Color> consumer) {
        return this;
    }

    @Override
    @Deprecated
    public ColorBuilder whenChanged(Runnable runnable) {
        return this;
    }

    @Override
    public ColorValue build() {
        return new ColorValue(this.name)
                .setIndex(index)
                .showIf(showIf);
    }
}
