package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Supplier;

public class StringValue extends Value<String> {

    public StringValue(String valueName, String value, boolean visible, ValueConsumer<String> consumer, Supplier<Boolean> modifier) {
        super(valueName, value, visible, consumer, modifier);
    }

    public StringValue(String valueName, String value, boolean visible, Supplier<Boolean> modifier) {
        super(valueName, value, visible, null, modifier);
    }

    public StringValue(String valueName, String defaultValue) {
        this(valueName, defaultValue, true, null, null);
    }

    @Override
    public StringValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public void set(String newValue) {
        if(listener != null) {
            value = listener.check(this.value, newValue);
        } else value = newValue;
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public StringValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public StringValue setValueChangeListener(ValueConsumer<String> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public StringValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    @Override
    public JsonElement getPrimitive() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public void fromElement(JsonElement primitive) {
        set(primitive.getAsString());
    }

    @Override
    public StringValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
