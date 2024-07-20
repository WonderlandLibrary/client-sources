/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;

public abstract class EnumConverter<E extends Enum<E>>
implements ValueConverter<E> {
    private final Class<E> clazz;
    private String delimiters = "[,]";

    protected EnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convert(String value) {
        for (Enum each : (Enum[])this.valueType().getEnumConstants()) {
            if (!each.name().equalsIgnoreCase(value)) continue;
            return (E)each;
        }
        throw new ValueConversionException(this.message(value));
    }

    @Override
    public Class<E> valueType() {
        return this.clazz;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    @Override
    public String valuePattern() {
        EnumSet<E> values = EnumSet.allOf(this.valueType());
        StringBuilder builder = new StringBuilder();
        builder.append(this.delimiters.charAt(0));
        Iterator i = values.iterator();
        while (i.hasNext()) {
            builder.append(((Enum)i.next()).toString());
            if (!i.hasNext()) continue;
            builder.append(this.delimiters.charAt(1));
        }
        builder.append(this.delimiters.charAt(2));
        return builder.toString();
    }

    private String message(String value) {
        ResourceBundle bundle = ResourceBundle.getBundle("joptsimple.ExceptionMessages");
        Object[] arguments = new Object[]{value, this.valuePattern()};
        String template = bundle.getString(EnumConverter.class.getName() + ".message");
        return new MessageFormat(template).format(arguments);
    }
}

