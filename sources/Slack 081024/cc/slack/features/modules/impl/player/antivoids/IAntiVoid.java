package cc.slack.features.modules.impl.player.antivoids;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.impl.movement.Strafe;
import net.minecraft.client.Minecraft;

public interface IAntiVoid {
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

    default void onStrafe(StrafeEvent event) {
    }

    ;

    default void onWorld(WorldEvent event) {
    }

    ;

    default void onJump(JumpEvent event) {
    }

    ;
}
