package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.util.math.Range;

import java.util.function.Supplier;

import static java.lang.Integer.MIN_VALUE;

public class IntegerBuilder extends AbstractBuilder<IntegerValue, Integer> {

    private int index, ofDefault = MIN_VALUE, increment;
    private Range<Integer> range;
    private Supplier<Boolean> showIf;
    private ValueConsumer<Integer> valueConsumer;

    public IntegerBuilder(String name) {
        this.name = name;
    }

    public IntegerBuilder range(Range<Integer> range) {
        this.range = range;
        return this;
    }

    public IntegerBuilder increment(int increment) {
        this.increment = increment;
        return this;
    }

    @Override
    public IntegerBuilder index(int index) {
        this.index = index;
        return this;
    }

    @Override
    public IntegerBuilder defaultOf(Integer defaultValue) {
        this.ofDefault = defaultValue;
        return this;
    }

    @Override
    public IntegerBuilder showIf(Supplier<Boolean> supplier) {
        this.showIf = supplier;
        return this;
    }

    @Override
    public IntegerBuilder whenChanged(ValueConsumer<Integer> consumer) {
        this.valueConsumer = consumer;
        return this;
    }

    @Override
    public IntegerBuilder whenChanged(Runnable runnable) {
        return whenChanged((before, after) -> {
            runnable.run();
            return after;
        });
    }

    @Override
    public IntegerValue build() {
        if(ofDefault == MIN_VALUE)
            ofDefault = range.getMin();
        else ofDefault = range.clamp(ofDefault);

        return new IntegerValue(name, ofDefault, range.getMin(), range.getMax(), increment)
                .showIf(showIf)
                .setValueChangeListener(valueConsumer)
                .setIndex(index);
    }
}
