package cc.slack.features.modules.impl.movement.speeds;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;

public interface ISpeed {
   default void onEnable() {
   }

   default void onDisable() {
   }

   default void onMove(MoveEvent event) {
   }

   default void onPacket(PacketEvent event) {
   }

   default void onUpdate(UpdateEvent event) {
   }
}
