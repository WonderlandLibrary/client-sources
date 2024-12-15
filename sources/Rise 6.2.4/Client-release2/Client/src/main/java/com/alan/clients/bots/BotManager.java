package com.alan.clients.bots;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class BotManager {
    private final HashMap<Object, ArrayList<Integer>> bots = new HashMap<>();

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> clear();

    public boolean contains(Entity target) {
        for (ArrayList<Integer> entities : bots.values()) {
            if (entities.contains(target.getEntityId())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Object object, Entity target) {
        if (!bots.containsKey(object)) return false;
        return bots.get(object).contains(target.getEntityId());
    }

    public void add(Object object, Entity entity) {
        int id = entity.getEntityId();
        if (!bots.containsKey(object)) bots.put(object, new ArrayList<>());
        ArrayList<Integer> entities = bots.get(object);

        if (!entities.contains(id)) {
            entities.add(id);
        }
    }

    public void remove(Object object, Entity entity) {
        if (bots.containsKey(object)) {
            ArrayList<Integer> entities = bots.get(object);
            entities.remove((Object) entity.getEntityId());
        }
    }

    public void clear() {
        bots.clear();
    }

    public void clear(Object object) {
        if (!bots.containsKey(object)) return;

        bots.get(object).clear();
    }
}