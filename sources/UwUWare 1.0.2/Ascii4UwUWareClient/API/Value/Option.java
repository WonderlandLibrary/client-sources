
package Ascii4UwUWareClient.API.Value;

public class Option<V> extends Value<V> {
	public Option(String displayName, String name, V enabled) {
		super(displayName, name);
		this.setValue(enabled);
	}
}
