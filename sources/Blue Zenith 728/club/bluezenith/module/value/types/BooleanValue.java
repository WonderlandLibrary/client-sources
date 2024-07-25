package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Supplier;

public final class BooleanValue extends Value<Boolean> {
    public BooleanValue(String valueName, Boolean value, boolean visible) {
        super(valueName, value, visible, null, null);
    }

    public BooleanValue(String valueName, Boolean value, boolean visible, Supplier<Boolean> modifier) {
        super(valueName, value, visible, null, modifier);
    }

    public BooleanValue(String valueName, Boolean value, boolean visible, ValueConsumer<Boolean> consumer, Supplier<Boolean> modifier) {
        super(valueName, value, visible, consumer, modifier);
    }

    public BooleanValue(String valueName, Boolean defaultValue) {
        this(valueName, defaultValue, true, null, null);
    }

    @Override
    public Boolean get() {
        return this.value;
    }

    public boolean getIfVisible() {
        return this.isVisible() && this.get();
    }

    @Override
    public void set(Boolean newValue) {
        if(listener != null) {
            this.value = listener.check(this.value, newValue);
        } else this.value = newValue;
    }

    public void next() {
        set(!value);
    }

    @Override
    public void previous() {
        set(!value);
    }

    @Override
    public BooleanValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public BooleanValue setValueChangeListener(ValueConsumer<Boolean> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public BooleanValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    @Override
    public BooleanValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public JsonElement getPrimitive() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public void fromElement(JsonElement primitive) {
         set(primitive.getAsBoolean());
    }

    @Override
    public BooleanValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
