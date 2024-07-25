package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import com.google.gson.JsonElement;

import java.util.function.Supplier;

public final class ActionValue extends Value<Runnable> {

    public ActionValue(String valueName, Runnable value, Supplier<Boolean> modifier) {
        super(valueName, value, true, null, modifier);
    }

    public ActionValue(String valueName, Runnable value) {
        super(valueName, value, true, null, null);
    }

    public ActionValue(String valueName) {
        this(valueName, null, null);
    }

    @Override
    public Runnable get() {
        return value;
    }

    @Override
    public void next() {
        value.run();
    }

    @Override
    public void previous() {
        value.run();
    }

    @Override
    public ActionValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    /**
     * Use setOnClickListener method instead
     */
    @Override
    @Deprecated
    public ActionValue setValueChangeListener(ValueConsumer<Runnable> listener) {
        return this;
    }

    public ActionValue setOnClickListener(Runnable listener) {
        this.value = listener;
        return this;
    }

    @Override
    public ActionValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    @Override
    public ActionValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public JsonElement getPrimitive() {
        return null;
    }

    @Override
    public void fromElement(JsonElement primitive) {

    }

    @Override
    public void set(Runnable newValue) {

    }

    @Override
    public ActionValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
