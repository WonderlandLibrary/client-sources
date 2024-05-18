package pw.latematt.xiv.value;

import pw.latematt.xiv.XIV;

/**
 * @author Matthew
 */
public class Value<T> {
    private final String name;
    private final T def;
    private T value;

    public Value(String name, T value) {
        this.name = name;
        this.def = this.value = value;
        XIV.getInstance().getValueManager().getContents().add(this);
    }

    public String getName() {
        return name;
    }

    public T getDefault() {
        return def;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
