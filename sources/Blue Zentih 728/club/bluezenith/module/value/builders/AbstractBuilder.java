package club.bluezenith.module.value.builders;

import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.module.value.builders.impl.*;

import java.util.function.Supplier;

public abstract class AbstractBuilder<Value, ValueType> {

    protected Value value;
    protected String name;

    public static BooleanBuilder createBoolean(String name) {
       return new BooleanBuilder(name);
    }

    public static ModeBuilder createMode(String name) {
        return new ModeBuilder(name);
    }

    public static FloatBuilder createFloat(String name) {
        return new FloatBuilder(name);
    }

    public static ColorBuilder createColor(String name) {
        return new ColorBuilder(name);
    }

    public static IntegerBuilder createInteger(String name) {
        return new IntegerBuilder(name);
    }

    public static StringVBuilder createString(String name) {
        return new StringVBuilder(name);
    }

    public static ListBuilder createList(String name) {
        return new ListBuilder(name);
    }

    public abstract AbstractBuilder<Value, ValueType> index(int index);
    public abstract AbstractBuilder<Value, ValueType> defaultOf(ValueType defaultValue);
    public abstract AbstractBuilder<Value, ValueType> showIf(Supplier<Boolean> supplier);
    public abstract AbstractBuilder<Value, ValueType> whenChanged(ValueConsumer<ValueType> consumer);
    public abstract AbstractBuilder<Value, ValueType> whenChanged(Runnable runnable);

    public abstract Value build();
}
