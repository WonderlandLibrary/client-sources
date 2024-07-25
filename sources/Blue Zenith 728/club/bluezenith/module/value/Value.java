package club.bluezenith.module.value;

import com.google.gson.JsonElement;

import java.util.function.Supplier;

public abstract class Value<T> {

    public String id;
    public final String name;
    protected T value;
    protected boolean visible;
    protected ValueConsumer<T> listener;
    protected Supplier<Boolean> supplier;
    protected int valIndex;
    protected boolean loaded;

    public Value(String valueName, T value, boolean visible, ValueConsumer<T> listener, Supplier<Boolean> supplier) {
        this.name = valueName;
        this.value = value;
        this.visible = visible;
        this.listener = listener;
        this.supplier = supplier;
    }

    public abstract T get();
    public abstract void set(T newValue);
    public abstract void next();
    public abstract void previous();

    public abstract Value<T> showIf(Supplier<Boolean> supplier);
    public abstract Value<T> setValueChangeListener(ValueConsumer<T> listener);
    public abstract Value<T> setDefaultVisibility(final boolean state);
    public abstract Value<T> setIndex(int index);
    public abstract Value<T> setID(String id);
    public abstract JsonElement getPrimitive();
    public abstract void fromElement(JsonElement primitive);
    public boolean isVisible() {
        if(supplier == null) return visible;
        visible = supplier.get();
        return visible;
    }
    public int getIndex() {
        return this.valIndex;
    }

}
