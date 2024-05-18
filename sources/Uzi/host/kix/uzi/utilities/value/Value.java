package host.kix.uzi.utilities.value;

/**
 * Created by myche on 2/3/2017.
 */
public class Value<T> {

    private final String name;
    private final T def;
    private T value;
    private T min;
    private T max;

    public Value(String name, T value, T min, T max) {
        this.name = name;
        this.def = this.value = value;
        this.min = min;
        this.max = max;
    }

    public Value(String name, T value) {
        this.name = name;
        this.def = this.value = value;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
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
