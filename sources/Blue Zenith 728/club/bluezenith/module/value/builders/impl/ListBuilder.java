package club.bluezenith.module.value.builders.impl;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.ListValue;

import java.util.function.Supplier;

import static java.util.Arrays.stream;

public class ListBuilder extends AbstractBuilder<ListValue, String> {

    private int index;
    private Supplier<Boolean> showIf;
    private String[] range;

    public ListBuilder(String name) {
        this.name = name;
    }

    @Override
    public ListBuilder index(int index) {
        this.index = index;
        return this;
    }

    public ListBuilder range(String... range) {
        this.range = range;
        return this;
    }

    public ListBuilder range(String optionsSeparatedByComma) {
        return range(stream(optionsSeparatedByComma.split(",")).map(String::trim).toArray(String[]::new));
    }

    @Override
    @Deprecated
    public ListBuilder defaultOf(String defaultValue) {
        return this;
    }

    @Override
    public ListBuilder showIf(Supplier<Boolean> supplier) {
        this.showIf = supplier;
        return this;
    }

    @Override
    @Deprecated
    public ListBuilder whenChanged(ValueConsumer<String> consumer) {
        return this;
    }

    @Override
    @Deprecated
    public ListBuilder whenChanged(Runnable runnable) {
        return this;
    }

    @Override
    public ListValue build() {
        return new ListValue(name, range).setIndex(index)
                .showIf(showIf);
    }
}
