package net.augustus.events;

import net.minecraft.client.multiplayer.WorldClient;

public class EventWorld extends Event {
   private final WorldClient worldClient;

   public EventWorld(WorldClient worldClient) {
      this.worldClient = worldClient;
   }

   public WorldClient getWorldClient() {
      return this.worldClient;
   }
}
