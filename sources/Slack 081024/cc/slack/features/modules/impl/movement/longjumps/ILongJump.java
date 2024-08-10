package cc.slack.features.modules.impl.movement.longjumps;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import net.minecraft.client.Minecraft;

public interface ILongJump {

    Minecraft mc = Minecraft.getMinecraft();

    default void onEnable() {
    }

    ;

    default void onDisable() {
    }

    ;

    default void onMove(MoveEvent event) {
    }

    ;

    default void onPacket(PacketEvent event) {
    }

    ;

    default void onCollide(CollideEvent event) {
    }

    ;

    default void onUpdate(UpdateEvent event) {
    }

    ;

    default void onMotion(MotionEvent event) {
    }

    ;

}
