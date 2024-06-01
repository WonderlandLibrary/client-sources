package com.polarware.component.impl.target;

import com.polarware.Client;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

/**
 * @author Auth
 * @since 3/03/2022
 */
public class BotComponent extends ArrayList<Entity> {

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        this.clear();
    };

    public boolean add(Entity entity) {
        if (!this.contains(entity)) super.add(entity);
        return false;
    }
}