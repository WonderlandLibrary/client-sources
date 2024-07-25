package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;

import java.util.function.Supplier;

public class BooleanBuilder extends AbstractBuilder<BooleanValue, Boolean> {

    public BooleanBuilder(String name) {
        this.name = name;
        this.value = new BooleanValue(this.name, false);
    }

    @Override
    public BooleanBuilder index(int index) {
        this.value.setIndex(index);
        return this;
    }

    @Override
    public BooleanBuilder defaultOf(Boolean defaultValue) {
        this.value.set(defaultValue);
        return this;
    }

    @Override
    public BooleanBuilder showIf(Supplier<Boolean> supplier) {
        this.value.showIf(supplier);
        return this;
    }

    @Override
    public BooleanBuilder whenChanged(ValueConsumer<Boolean> consumer) {
        this.value.setValueChangeListener(consumer);
        return this;
    }

    @Override
    public BooleanBuilder whenChanged(Runnable runnable) {
        whenChanged((before, after) -> {
            runnable.run();
            return after;
        });
        return this;
    }

    @Override
    public BooleanValue build() {
        if(value.getIndex() == 0) throw new IllegalStateException("Value index wasn't set.");
        else return this.value;
    }
}
