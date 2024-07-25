package club.bluezenith.module.value.types;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.ModuleToggledEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.util.client.Pair;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Supplier;

public class ExtendedModeValue extends Value<Pair<String, ? extends ExtendedModeValue.Mode>> {

    private final Module parent;
    private final Pair<String, ? extends ExtendedModeValue.Mode>[] values;
    private int currentValueIndex;

    @SafeVarargs
    public ExtendedModeValue(Module parent, String name, Pair<String, ? extends ExtendedModeValue.Mode>... values) {
        super(name, values[0], true, null, null);

        this.parent = parent;

        this.currentValueIndex = 0;
        this.values = values;
    }

    //no Listener annotation because called in the Module class
    public void onParentToggle(ModuleToggledEvent event) {
        if(event.module != this.parent) return;

        if(event.isEnabled)
            this.get().getValue().onEnable(this.parent);
        else this.get().getValue().onDisable(this.parent);
    }

    @Override
    public Pair<String, ? extends ExtendedModeValue.Mode> get() {
        return this.value;
    }

    public Pair<String, ? extends ExtendedModeValue.Mode>[] getAllValues() {
        return values;
    }

    public boolean is(String other) {
        return this.value.getKey().equalsIgnoreCase(other);
    }

    @Override
    public void set(Pair<String, ? extends ExtendedModeValue.Mode> newValue) {
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            final Pair<String, ? extends Mode> pair = values[i];

            if (newValue.equals(pair)) {

                if (this.parent.getState()) {
                    BlueZenith.getBlueZenith().unregister(this.value.getValue());
                }

                this.value = pair;
                this.currentValueIndex = i;

                if (this.parent.getState()) {
                    BlueZenith.getBlueZenith().register(this.value.getValue());
                }

                break;
            }
        }
    }

    @Override
    public void next() {
        final int next = currentValueIndex + 1;

        if(this.values.length <= next) set(values[0]);
        else set(values[++currentValueIndex]);
    }

    @Override
    public void previous() {
        final int next = currentValueIndex - 1;

        if(next < 0) set(values[values.length - 1]);
        else set(values[--currentValueIndex]);
    }

    @Override
    public ExtendedModeValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public ExtendedModeValue setValueChangeListener(ValueConsumer<Pair<String, ? extends ExtendedModeValue.Mode>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ExtendedModeValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }

    @Override
    public ExtendedModeValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public ExtendedModeValue setID(String id) {
        this.id = id;
        return this;
    }

    @Override
    public JsonElement getPrimitive() {
        return new JsonPrimitive(get().getKey());
    }

    @Override
    public void fromElement(JsonElement primitive) {
        final String key = primitive.getAsString();

        for (Pair<String, ? extends ExtendedModeValue.Mode> pair : this.values) {
            if(key.equals(pair.getKey())) {
                set(pair);
                break;
            }
        }
    }

    public interface Mode {
        default void onEnable(Module module) {}

        default void onDisable(Module module) {}
    }

    public static final EMPTY_MODE_TYPE EMPTY_MODE = new EMPTY_MODE_TYPE();
    private static final class EMPTY_MODE_TYPE implements Mode {}
}
