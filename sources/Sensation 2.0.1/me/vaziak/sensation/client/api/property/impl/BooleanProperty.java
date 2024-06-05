package me.vaziak.sensation.client.api.property.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.events.UpdateValueEvent;
import me.vaziak.sensation.client.api.property.Parser;

/**
 * @author antja03
 */
public class BooleanProperty extends Value<Boolean> {

    public BooleanProperty(String id, String description, me.vaziak.sensation.utils.anthony.Dependency dependency, Boolean defaultValue) {
        super(id, description, dependency);
        this.value = defaultValue;
    }
    
    public BooleanProperty(String id, String description, Boolean defaultValue) {
        super(id, description);
        this.value = defaultValue;
    }
    

    @Override
    public void setValue(String input) {
        Boolean newValue = Parser.parseBool(input);
        if (newValue != null && value != newValue) {
            Sensation.eventBus.publish(new UpdateValueEvent(this, getValue(), newValue));
            value = newValue;
        }
    }

}

