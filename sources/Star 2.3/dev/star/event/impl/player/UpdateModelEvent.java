package dev.star.event.impl.player;

import dev.star.event.Event;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class UpdateModelEvent extends Event {
    private ModelPlayer modelPlayer;
    private Entity entity;

    public UpdateModelEvent(Entity entity, ModelPlayer modelPlayer) {
        this.modelPlayer = modelPlayer;
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public ModelPlayer getModelPlayer() {
        return modelPlayer;
    }
}
