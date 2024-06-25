package cc.slack.events.impl.game;

import cc.slack.events.Event;
import net.minecraft.network.PacketDirection;

public class ChatEvent extends Event {
   private String message;
   private final PacketDirection direction;

   public String getMessage() {
      return this.message;
   }

   public PacketDirection getDirection() {
      return this.direction;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public ChatEvent(String message, PacketDirection direction) {
      this.message = message;
      this.direction = direction;
   }
}
