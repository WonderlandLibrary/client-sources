/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API.Events.Render;

import gq.vapu.czfclient.API.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class EventRenderCape extends Event {
    public static ResourceLocation capeLocation;
    private final EntityPlayer player;

    public EventRenderCape(ResourceLocation capeLocation, EntityPlayer player) {
        EventRenderCape.capeLocation = capeLocation;
        this.player = player;
    }

    public ResourceLocation getLocation() {
        return capeLocation;
    }

    public void setLocation(ResourceLocation location) {
        capeLocation = location;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }
}
