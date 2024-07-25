package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.client.entity.EntityPlayerSP;

public class SpawnPlayerEvent extends Event {

    public final EntityPlayerSP player;

    public SpawnPlayerEvent(EntityPlayerSP player) {
         this.player = player;
    }

}
