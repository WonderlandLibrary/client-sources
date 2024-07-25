package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.StringValue;

import java.util.function.Supplier;

public class StringVBuilder extends AbstractBuilder<StringValue, String> {

    private int index;
    private String defaultValue;
    private Supplier<Boolean> showIf;
    private ValueConsumer<String> valueConsumer;

    public StringVBuilder(String name) {
        this.name = name;
    }

    @Override
    public StringVBuilder index(int index) {
        this.index = index;
        return this;
    }

    @Override
    public StringVBuilder defaultOf(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public StringVBuilder showIf(Supplier<Boolean> supplier) {
        this.showIf = supplier;
        return this;
    }

    @Override
    public StringVBuilder whenChanged(ValueConsumer<String> consumer) {
        this.valueConsumer = consumer;
        return this;
    }

    @Override
    public StringVBuilder whenChanged(Runnable runnable) {
        return this.whenChanged((before, after) -> {
            runnable.run();
            return after;
        });
    }

    @Override
    public StringValue build() {
        return new StringValue(name, defaultValue)
                .setIndex(index)
                .showIf(showIf)
                .setValueChangeListener(valueConsumer);
    }
}
