package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;

public class TargetStrafe extends Module {
 
    public TargetStrafe() {
        super("Target Strafe", Category.MOVEMENT);
    }
}