package Ascii4UwUWareClient.API.Value;

public abstract class Value<V> {
	private String displayName;
	private String name;
	public boolean drag;
	public float animX;
	public float animX1;

	public V value;

	public Value(String displayName, String name) {
		this.displayName = displayName;
		this.name = name;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getName() {
		return this.name;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}
