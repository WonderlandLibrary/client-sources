/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import joptsimple.ArgumentList;
import joptsimple.OptionArgumentConversionException;
import joptsimple.OptionDescriptor;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;
import joptsimple.internal.ReflectionException;

public abstract class AbstractOptionSpec<V>
implements OptionSpec<V>,
OptionDescriptor {
    private final List<String> options = new ArrayList<String>();
    private final String description;
    private boolean forHelp;

    AbstractOptionSpec(String string) {
        this(Collections.singletonList(string), "");
    }

    AbstractOptionSpec(List<String> list, String string) {
        this.arrangeOptions(list);
        this.description = string;
    }

    @Override
    public final List<String> options() {
        return Collections.unmodifiableList(this.options);
    }

    @Override
    public final List<V> values(OptionSet optionSet) {
        return optionSet.valuesOf(this);
    }

    @Override
    public final V value(OptionSet optionSet) {
        return optionSet.valueOf(this);
    }

    @Override
    public String description() {
        return this.description;
    }

    public final AbstractOptionSpec<V> forHelp() {
        this.forHelp = true;
        return this;
    }

    @Override
    public final boolean isForHelp() {
        return this.forHelp;
    }

    @Override
    public boolean representsNonOptions() {
        return true;
    }

    protected abstract V convert(String var1);

    protected V convertWith(ValueConverter<V> valueConverter, String string) {
        try {
            return Reflection.convertWith(valueConverter, string);
        } catch (ValueConversionException | ReflectionException runtimeException) {
            throw new OptionArgumentConversionException(this, string, runtimeException);
        }
    }

    protected String argumentTypeIndicatorFrom(ValueConverter<V> valueConverter) {
        if (valueConverter == null) {
            return null;
        }
        String string = valueConverter.valuePattern();
        return string == null ? valueConverter.valueType().getName() : string;
    }

    abstract void handleOption(OptionParser var1, ArgumentList var2, OptionSet var3, String var4);

    private void arrangeOptions(List<String> list) {
        if (list.size() == 1) {
            this.options.addAll(list);
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (String string : list) {
            if (string.length() == 1) {
                arrayList.add(string);
                continue;
            }
            arrayList2.add(string);
        }
        Collections.sort(arrayList);
        Collections.sort(arrayList2);
        this.options.addAll(arrayList);
        this.options.addAll(arrayList2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof AbstractOptionSpec)) {
            return true;
        }
        AbstractOptionSpec abstractOptionSpec = (AbstractOptionSpec)object;
        return this.options.equals(abstractOptionSpec.options);
    }

    public int hashCode() {
        return this.options.hashCode();
    }

    public String toString() {
        return this.options.toString();
    }
}

