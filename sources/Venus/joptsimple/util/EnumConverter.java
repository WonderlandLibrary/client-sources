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
    public E convert(String string) {
        for (Enum enum_ : (Enum[])this.valueType().getEnumConstants()) {
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return (E)enum_;
        }
        throw new ValueConversionException(this.message(string));
    }

    @Override
    public Class<E> valueType() {
        return this.clazz;
    }

    public void setDelimiters(String string) {
        this.delimiters = string;
    }

    @Override
    public String valuePattern() {
        EnumSet<E> enumSet = EnumSet.allOf(this.valueType());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.delimiters.charAt(0));
        Iterator iterator2 = enumSet.iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append(((Enum)iterator2.next()).toString());
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(this.delimiters.charAt(1));
        }
        stringBuilder.append(this.delimiters.charAt(2));
        return stringBuilder.toString();
    }

    private String message(String string) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("joptsimple.ExceptionMessages");
        Object[] objectArray = new Object[]{string, this.valuePattern()};
        String string2 = resourceBundle.getString(EnumConverter.class.getName() + ".message");
        return new MessageFormat(string2).format(objectArray);
    }

    @Override
    public Object convert(String string) {
        return this.convert(string);
    }
}

