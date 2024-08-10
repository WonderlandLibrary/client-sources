// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player.nofalls;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import net.minecraft.client.Minecraft;

public interface INoFall {

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

    default void onUpdate(UpdateEvent event) {

    }

    default void onMotion(MotionEvent event) {

    }

    default void onRender(RenderEvent event) {

    }

    ;
}