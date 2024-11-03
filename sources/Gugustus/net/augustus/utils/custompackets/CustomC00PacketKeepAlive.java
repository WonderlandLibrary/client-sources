package net.augustus.utils.custompackets;

import net.minecraft.network.play.client.C00PacketKeepAlive;

public class CustomC00PacketKeepAlive extends C00PacketKeepAlive {
   private final long time;

   public CustomC00PacketKeepAlive(int key, long time) {
      super(key);
      this.time = time;
   }

   public long getTime() {
      return this.time;
   }
}
