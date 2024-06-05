package gq.vapu.czfclient.API.Value;

public class Numbers<T extends Number> extends Value<T> {
    public T min;
    public T max;
    public T inc;
    private String name;
    private final boolean integer;

    public Numbers(String displayName, String name, T value, T min, T max, T inc) {
        super(displayName, name);
        setValue(value);
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.integer = false;
    }

    public T getMinimum() {
        return this.min;
    }

    public T getMaximum() {
        return this.max;
    }

    public T getIncrement() {
        return this.inc;
    }

    public void setIncrement(T inc) {
        this.inc = inc;
    }

    public String getId() {
        return this.name;
    }

    public boolean isInteger() {
        return false;
    }
}
