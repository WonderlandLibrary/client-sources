package club.strifeclient.setting;

import club.strifeclient.util.callback.ChangeCallback;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Getter
public abstract class Setting<T> {

    private final String name;
    @Setter
    private Supplier<Boolean> dependency;
    private ChangeCallback<T, T> callback;
    protected final Class<?> ownerClass;
    protected T value;

    private final List<ChangeCallback<T, T>> changeCallbacks;

    public Setting(String name, T defaultValue, Supplier<Boolean> dependency) {
        this.name = name;
        this.value = defaultValue;
        this.dependency = dependency;
        this.ownerClass = this.getClass();
        changeCallbacks = new ArrayList<>();
    }

    public Setting(String name, T value) {
        this(name, value, () -> true);
    }

    public void addChangeCallback(ChangeCallback<T, T> callback) {
        if(!changeCallbacks.contains(callback))
            changeCallbacks.add(callback);
    }

    public void setValue(T value) {
        if(!this.value.equals(value)) {
            final T temp = this.value;
            this.value = value;
            changeCallbacks.forEach(callback -> callback.callback(temp, this));
        }
    }

    public String getSerializedName() {
        return name.contains(" ") ? name.replaceAll(" ", "-") : name;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @SuppressWarnings("unchecked")
    public void parse(Object original) {
        setValue((T)original);
    }

    public boolean isAvailable() {
        return dependency.get();
    }
}
