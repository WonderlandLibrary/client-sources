package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public final class ModeValue extends Value<String> {

    public final ArrayList<String> range;

    public ModeValue(String valueName, String defaultValue, boolean visible, ValueConsumer<String> update, Supplier<Boolean> visibility, String... range) {
        super(valueName, defaultValue, visible, update, visibility);
        this.range = new ArrayList<>(Arrays.asList(range));
    }

    public ModeValue(String valueName, String defaultValue, boolean visible, Supplier<Boolean> visibility, String... range) {
        super(valueName, defaultValue, visible, null, visibility);
        this.range = new ArrayList<>(Arrays.asList(range));
    }

    public ModeValue(String valueName, String defaultValue, String... range) {
        this(valueName, defaultValue, true, null, null, range);
    }

    @Override
    public ModeValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public String get() {
        return this.value;
    }

    public boolean is(String other) { return this.value.equalsIgnoreCase(other); }

    @Override
    public void set(String newValue) {
        if(!range.contains(newValue)) return;
        if(listener != null) {
            this.value = listener.check(this.value, newValue);
        } else this.value = newValue;
    }

    public String find(String value) {
        return range.stream().filter(m -> m.equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    public void next() {
        int index = range.indexOf(this.value);
        if((index + 1) >= range.size()) {
            index = 0;
        } else index += 1;
        this.set(range.get(index));
    }

    public void previous() {
        int index = range.indexOf(this.value);
        if(index - 1 < 0) {
            index = range.size() - 1;
        } else index -= 1;
        this.set(range.get(index));
    }

    @Override
    public ModeValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public ModeValue setValueChangeListener(ValueConsumer<String> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ModeValue setDefaultVisibility(boolean state) {
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
    public ModeValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
