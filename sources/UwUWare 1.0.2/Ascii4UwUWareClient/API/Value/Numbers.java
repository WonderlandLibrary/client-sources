package Ascii4UwUWareClient.API.Value;

public class Numbers<T extends Number> extends Value<T> {
	private String name;
	public T min;
	public T max;
	public T inc;
	private boolean integer;

	public Numbers(String displayName, String name, T value, T min, T max, T inc) {
		super(displayName, name);
		this.setValue(value);
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

	public void setIncrement(T inc) {
		this.inc = inc;
	}

	public T getIncrement() {
		return this.inc;
	}

	public String getId() {
		return this.name;
	}

	public boolean isInteger() {
		return this.integer;
	}
}
