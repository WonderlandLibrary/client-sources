package cc.slack.features.modules.impl.player.nofalls;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;

public interface INoFall {
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

   default void onMotion(MotionEvent event) {
   }
}
