/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.render;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import digital.rbq.events.Event;

public final class UpdateModelEvent
implements Event {
    private final EntityPlayer player;
    private final ModelPlayer model;

    public UpdateModelEvent(EntityPlayer player, ModelPlayer model) {
        this.player = player;
        this.model = model;
    }

    public final EntityPlayer getPlayer() {
        return this.player;
    }

    public final ModelPlayer getModel() {
        return this.model;
    }
}

