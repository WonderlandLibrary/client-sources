package wtf.diablo.client.setting.api;

public abstract class AbstractSetting<T> {
    private final String name;
    private final T defaultValue; //this may be something to remove in the future along with the reset method
    private T value;

    public AbstractSetting(final String name, final T value) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
    }

    public final String getName() {
        return this.name;
    }

    public T getValue() {
        return this.value;
    }

    public abstract T parse(final String value);

    public void setValue(final T value) {
        this.value = value;
    }

    public void reset() {
        this.value = this.defaultValue;
    }
}