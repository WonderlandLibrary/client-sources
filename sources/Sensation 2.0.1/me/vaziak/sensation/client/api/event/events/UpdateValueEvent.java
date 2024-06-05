package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.property.impl.Value;

/**
 * @author antja03
 */
public class UpdateValueEvent {
    private Value value;

    private Object oldValue;
    private Object newValue;

    public UpdateValueEvent(Value value, Object oldValue, Object newValue) {
        this.value = value;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Value getValue() {
        return value;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}
