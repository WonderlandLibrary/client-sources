package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.ModeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.stream;

public class ModeBuilder extends AbstractBuilder<ModeValue, String> {

    int index = 0;
    String defaultValue;
    Supplier<Boolean> supplier;
    ValueConsumer<String> consumer;
    String[] possibleValues;

    public ModeBuilder(String name) {
       this.name = name;
    }

    @Override
    public ModeBuilder index(int index) {
        this.index = index;
        return this;
    }

    @Override
    public ModeBuilder defaultOf(String defaultValue) {
        if(possibleValues != null) {
            final List<String> values = new ArrayList<>(Arrays.asList(possibleValues));
            if(!values.contains(defaultValue)) {
                values.add(defaultValue);
                possibleValues = values.toArray(new String[0]);
            }
        }
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public ModeBuilder showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public ModeBuilder whenChanged(ValueConsumer<String> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public ModeBuilder whenChanged(Runnable runnable) {
        whenChanged((before, after) -> {
            runnable.run();
            return after;
        });
        return this;
    }

    public ModeBuilder range(String... values) {
        this.possibleValues = values;
        return this;
    }

    public ModeBuilder range(String valuesSeparatedByComma) {
        return range(stream(valuesSeparatedByComma.split(",")).map(String::trim).toArray(String[]::new));
    }

    @Override
    public ModeValue build() {
        if(possibleValues == null && defaultValue == null) throw new IllegalArgumentException("Possible values range isn't initialized for value " + name);
        else if(possibleValues == null) {
            String[] holder = new String[1];
            holder[0] = defaultValue;
            possibleValues = holder;
        }
        if(index == 0) throw new IllegalArgumentException("Index isn't set for value " + name);
        if(defaultValue == null) defaultValue = possibleValues[0];
        return new ModeValue(name, defaultValue, possibleValues).setIndex(index).showIf(supplier).setValueChangeListener(consumer);
    }
}
