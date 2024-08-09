/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;
import joptsimple.internal.Strings;

public abstract class ArgumentAcceptingOptionSpec<V>
extends AbstractOptionSpec<V> {
    private static final char NIL_VALUE_SEPARATOR = '\u0000';
    private final boolean argumentRequired;
    private final List<V> defaultValues = new ArrayList<V>();
    private boolean optionRequired;
    private ValueConverter<V> converter;
    private String argumentDescription = "";
    private String valueSeparator = String.valueOf('\u0000');

    ArgumentAcceptingOptionSpec(String string, boolean bl) {
        super(string);
        this.argumentRequired = bl;
    }

    ArgumentAcceptingOptionSpec(List<String> list, boolean bl, String string) {
        super(list, string);
        this.argumentRequired = bl;
    }

    public final <T> ArgumentAcceptingOptionSpec<T> ofType(Class<T> clazz) {
        return this.withValuesConvertedBy(Reflection.findConverter(clazz));
    }

    public final <T> ArgumentAcceptingOptionSpec<T> withValuesConvertedBy(ValueConverter<T> valueConverter) {
        if (valueConverter == null) {
            throw new NullPointerException("illegal null converter");
        }
        this.converter = valueConverter;
        return this;
    }

    public final ArgumentAcceptingOptionSpec<V> describedAs(String string) {
        this.argumentDescription = string;
        return this;
    }

    public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(char c) {
        if (c == '\u0000') {
            throw new IllegalArgumentException("cannot use U+0000 as separator");
        }
        this.valueSeparator = String.valueOf(c);
        return this;
    }

    public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(String string) {
        if (string.indexOf(0) != -1) {
            throw new IllegalArgumentException("cannot use U+0000 in separator");
        }
        this.valueSeparator = string;
        return this;
    }

    @SafeVarargs
    public final ArgumentAcceptingOptionSpec<V> defaultsTo(V v, V ... VArray) {
        this.addDefaultValue(v);
        this.defaultsTo(VArray);
        return this;
    }

    public ArgumentAcceptingOptionSpec<V> defaultsTo(V[] VArray) {
        for (V v : VArray) {
            this.addDefaultValue(v);
        }
        return this;
    }

    public ArgumentAcceptingOptionSpec<V> required() {
        this.optionRequired = true;
        return this;
    }

    @Override
    public boolean isRequired() {
        return this.optionRequired;
    }

    private void addDefaultValue(V v) {
        Objects.requireNonNull(v);
        this.defaultValues.add(v);
    }

    @Override
    final void handleOption(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet, String string) {
        if (Strings.isNullOrEmpty(string)) {
            this.detectOptionArgument(optionParser, argumentList, optionSet);
        } else {
            this.addArguments(optionSet, string);
        }
    }

    protected void addArguments(OptionSet optionSet, String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, this.valueSeparator);
        if (!stringTokenizer.hasMoreTokens()) {
            optionSet.addWithArgument(this, string);
        } else {
            while (stringTokenizer.hasMoreTokens()) {
                optionSet.addWithArgument(this, stringTokenizer.nextToken());
            }
        }
    }

    protected abstract void detectOptionArgument(OptionParser var1, ArgumentList var2, OptionSet var3);

    @Override
    protected final V convert(String string) {
        return this.convertWith(this.converter, string);
    }

    protected boolean canConvertArgument(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, this.valueSeparator);
        try {
            while (stringTokenizer.hasMoreTokens()) {
                this.convert(stringTokenizer.nextToken());
            }
            return true;
        } catch (OptionException optionException) {
            return true;
        }
    }

    protected boolean isArgumentOfNumberType() {
        return this.converter != null && Number.class.isAssignableFrom(this.converter.valueType());
    }

    @Override
    public boolean acceptsArguments() {
        return false;
    }

    @Override
    public boolean requiresArgument() {
        return this.argumentRequired;
    }

    @Override
    public String argumentDescription() {
        return this.argumentDescription;
    }

    @Override
    public String argumentTypeIndicator() {
        return this.argumentTypeIndicatorFrom(this.converter);
    }

    public List<V> defaultValues() {
        return Collections.unmodifiableList(this.defaultValues);
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return true;
        }
        ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec = (ArgumentAcceptingOptionSpec)object;
        return this.requiresArgument() == argumentAcceptingOptionSpec.requiresArgument();
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (this.argumentRequired ? 0 : 1);
    }
}

