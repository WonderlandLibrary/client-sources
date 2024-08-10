// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds;

import cc.slack.events.impl.input.onMoveInputEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import net.minecraft.client.Minecraft;

public interface ISpeed {

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

    default void onUpdate(UpdateEvent event) {
    }

    ;

    default void onMotion(MotionEvent event) {
    }

    ;

    default void onMoveInput(onMoveInputEvent event) {
    }

    ;

    default void onStrafe(StrafeEvent event) {

    }

    ;

    default void onJump(JumpEvent event) {

    }

    ;
}
