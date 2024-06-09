package me.protocol_client.thanks_slicky.properties;

import me.protocol_client.Protocol;


public class Value<T> {
    private final String name;
    private final T def;
    private T value;

    public Value(String name, T value) {
        this.name = name;
        this.def = this.value = value;
        Protocol.getValueManager().getContents().add(this);
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
        if(this.value.equals(true)){
        }
    }
}
