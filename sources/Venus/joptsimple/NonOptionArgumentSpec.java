/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;

public class NonOptionArgumentSpec<V>
extends AbstractOptionSpec<V> {
    static final String NAME = "[arguments]";
    private ValueConverter<V> converter;
    private String argumentDescription = "";

    NonOptionArgumentSpec() {
        this("");
    }

    NonOptionArgumentSpec(String string) {
        super(Arrays.asList(NAME), string);
    }

    public <T> NonOptionArgumentSpec<T> ofType(Class<T> clazz) {
        this.converter = Reflection.findConverter(clazz);
        return this;
    }

    public final <T> NonOptionArgumentSpec<T> withValuesConvertedBy(ValueConverter<T> valueConverter) {
        if (valueConverter == null) {
            throw new NullPointerException("illegal null converter");
        }
        this.converter = valueConverter;
        return this;
    }

    public NonOptionArgumentSpec<V> describedAs(String string) {
        this.argumentDescription = string;
        return this;
    }

    @Override
    protected final V convert(String string) {
        return this.convertWith(this.converter, string);
    }

    @Override
    void handleOption(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet, String string) {
        optionSet.addWithArgument(this, string);
    }

    @Override
    public List<?> defaultValues() {
        return Collections.emptyList();
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public boolean acceptsArguments() {
        return true;
    }

    @Override
    public boolean requiresArgument() {
        return true;
    }

    @Override
    public String argumentDescription() {
        return this.argumentDescription;
    }

    @Override
    public String argumentTypeIndicator() {
        return this.argumentTypeIndicatorFrom(this.converter);
    }

    @Override
    public boolean representsNonOptions() {
        return false;
    }
}

