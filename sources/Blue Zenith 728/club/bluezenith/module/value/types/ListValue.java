package club.bluezenith.module.value.types;

import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.ValueConsumer;
import club.bluezenith.util.client.ClientUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ListValue extends Value<List<String>> {

    public boolean expanded = false;
    private final LinkedHashMap<String, Boolean> choices = new LinkedHashMap<>();

    public ListValue(String name, boolean visible, String... options) {
        super(name, Arrays.asList(options), visible, null, null);
        Arrays.stream(options).forEach(option -> choices.put(option, false));
    }

    public ListValue(String name, boolean visible, Supplier<Boolean> modifier, String... options) {
        super(name, Arrays.asList(options), visible, null, modifier);
        Arrays.stream(options).forEach(option -> choices.put(option, false));
    }

    public ListValue(String name, String... options) {
        this(name, true, null, options);
    }

    @Override
    public ListValue setIndex(int index) {
        this.valIndex = index;
        return this;
    }

    @Override
    public List<String> get() {
        return value;
    }

    @Override
    public void set(List<String> newValue) {
         //unused
    }

    @Override
    public void next() {
        //unused
    }

    @Override
    public void previous() {
        //unused
    }

    @Override
    public ListValue showIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public ListValue setValueChangeListener(ValueConsumer<List<String>> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ListValue setDefaultVisibility(boolean state) {
        this.visible = state;
        return this;
    }


    public List<String> getOptions() {
        return new ArrayList<>(choices.keySet());
    }

    public List<String> getSelectedOptions() {
        return choices.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public void toggleOption(String key) {
        if(!choices.containsKey(key)) {
            ClientUtils.getLogger().error("Couldn't find option " + key + " for value " + name);
            return;
        }
        choices.put(key, !choices.get(key));
    }

    public void toggleOptions(String...key) {
        for (String s : key) {
            toggleOption(s);
        }
    }

    public boolean getOptionState(String key) {
        if(!choices.containsKey(key)) {
            ClientUtils.getLogger().error("Couldn't find option " + key + " for value " + name);
            return false;
        }
        return choices.get(key);
    }

    public ListValue setOptionState(String key, boolean state) {
        if(!choices.containsKey(key)) {
            ClientUtils.getLogger().error("Couldn't find option " + key + " for value " + name);
            return this;
        }
        choices.put(key, state);
        return this;
    }

    @Override
    public JsonElement getPrimitive() {
        return null;
    }

    @Override
    public void fromElement(JsonElement primitive) {

    }

    public void fromObject(JsonObject object) {
        object.entrySet().forEach(entry -> setOptionState(entry.getKey(), entry.getValue().getAsBoolean()));
    }

    public JsonObject toObject() {
        JsonObject settings = new JsonObject();
        this.choices.forEach((choice, state) -> settings.add(choice, new JsonPrimitive(state)));
        return settings;
    }

    @Override
    public ListValue setID(String id) {
        this.id = "id-" + id;
        return this;
    }
}
