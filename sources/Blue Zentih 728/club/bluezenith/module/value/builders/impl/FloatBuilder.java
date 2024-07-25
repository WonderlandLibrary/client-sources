package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.math.Range;

import java.util.function.Supplier;

public class FloatBuilder extends AbstractBuilder<FloatValue, Float> {
    int index;
    Float defaultValue;
    Float minValue;
    Float maxValue;
    Float increment;
    Supplier<Boolean> supplier;
    ValueConsumer<Float> consumer;

    public FloatBuilder(String name) {
        this.name = name;
    }

    @Override
    public FloatBuilder index(int index) {
        this.index = index;
        return this;
    }

    @Override
    public FloatBuilder defaultOf(Float defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public FloatBuilder showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public FloatBuilder whenChanged(ValueConsumer<Float> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public FloatBuilder whenChanged(Runnable runnable) {
        whenChanged((before, after) -> {
            runnable.run();
            return after;
        });
        return this;
    }

    public FloatBuilder range(Range<Float> range) {
        min(range.getMin());
        return max(range.getMax());
    }

    public FloatBuilder min(Float min) {
        this.minValue = min;
        return this;
    }

    public FloatBuilder max(Float max) {
        this.maxValue = max;
        return this;
    }

    public FloatBuilder increment(Float increment) {
        this.increment = increment;
        return this;
    }

    @Override
    public FloatValue build() {
        if(minValue == null) throw new IllegalArgumentException("Minimum value wasn't set for value " + name);
        if(maxValue == null) throw new IllegalArgumentException("Maximum value wasn't set for value " + name);
        if(increment == null) throw new IllegalArgumentException("Increment value wasn't set for value " + name);
        if(index == 0) throw new IllegalArgumentException("Index value wasn't set for value " + name);
        if(defaultValue == null) defaultValue = minValue;
        return new FloatValue(name, defaultValue, minValue, maxValue, increment).setIndex(index).showIf(supplier).setValueChangeListener(consumer);
    }
}
