package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.IGetter;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Setting<T> implements IGetter<T>, ISerializable<T> {
    private static final List<String> NO_SUGGESTIONS = new ArrayList<>(0);

    public final String name, command, description;
    private final IVisible visible;

    protected final T defaultValue;
    protected T value;

    public final Consumer<Setting<T>> onModuleEnabled;
    private final Consumer<T> onChanged;

    public Module module;
    public boolean lastWasVisible;

    public Setting(String name, String description, T defaultValue, Consumer<T> onChanged, Consumer<Setting<T>> onModuleEnabled, IVisible visible) {
        this.name = name;
        this.command = Utils.nameToCommand(name);
        this.description = description;
        this.defaultValue = defaultValue;
        this.onChanged = onChanged;
        this.onModuleEnabled = onModuleEnabled;
        this.visible = visible;

        resetImpl();
    }

    @Override
    public T get() {
        return value;
    }

    public boolean set(T value) {
        if (!isValueValid(value)) {
            return false;
        }

        this.value = value;
        onChanged();

        return true;
    }

    protected void resetImpl() {
        value = defaultValue;
    }

    public void reset() {
        resetImpl();
        onChanged();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean parse(String string) {
        T newValue = parseImpl(string);

        if (newValue != null) {
            if (isValueValid(newValue)) {
                value = newValue;
                onChanged();
            }
        }

        return newValue != null;
    }

    public boolean wasChanged() {
        return !Objects.equals(value, defaultValue);
    }

    public void onChanged() {
        if (onChanged != null) {
            onChanged.accept(value);
        }
    }

    public void onEnabled() {
        if (onModuleEnabled != null) {
            onModuleEnabled.accept(this);
        }
    }

    public boolean isVisible() {
        return visible == null || visible.isVisible();
    }

    protected abstract T parseImpl(String string);

    protected abstract boolean isValueValid(T value);

    public Iterable<Identifier> getIdentifierSuggestions() {
        return null;
    }

    public List<String> getSuggestions() {
        return NO_SUGGESTIONS;
    }

    protected abstract JSONObject save(JSONObject json);

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);

        save(json);

        return json;
    }

    protected abstract T load(JSONObject json);

    @Override
    public T fromJson(JSONObject json) {
        T value = load(json);
        onChanged();

        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Setting<?> setting = (Setting<?>) object;
        return Objects.equals(name, setting.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static <T> T parseId(Registry<T> registry, String name) {
        name = name.trim();

        Identifier id;
        if (name.contains(":")) {
            id = new Identifier(name);
        } else {
            id = new Identifier("minecraft", name);
        }

        if (registry.containsId(id)) {
            return registry.get(id);
        }

        return null;
    }

    public abstract static class SettingBuilder<B, V, S> {
        protected String name = "undefined", description = "";
        protected V defaultValue;
        protected IVisible visible;
        protected Consumer<V> onChanged;
        protected Consumer<Setting<V>> onModuleEnabled;

        protected SettingBuilder(V defaultValue) {
            this.defaultValue = defaultValue;
        }

        public B name(String name) {
            this.name = name;
            return (B) this;
        }

        public B description(String description) {
            this.description = description;
            return (B) this;
        }

        public B defaultValue(V defaultValue) {
            this.defaultValue = defaultValue;
            return (B) this;
        }

        public B visible(IVisible visible) {
            this.visible = visible;
            return (B) this;
        }

        public B onChanged(Consumer<V> onChanged) {
            this.onChanged = onChanged;
            return (B) this;
        }

        public B onModuleEnabled(Consumer<Setting<V>> onModuleEnabled) {
            this.onModuleEnabled = onModuleEnabled;
            return (B) this;
        }

        public abstract S build();
    }
}