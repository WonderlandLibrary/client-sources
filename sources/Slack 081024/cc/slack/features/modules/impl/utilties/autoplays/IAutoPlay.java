package cc.slack.features.modules.impl.utilties.autoplays;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import net.minecraft.client.Minecraft;

public interface IAutoPlay {

    Minecraft mc = Minecraft.getMinecraft();

    default void onEnable() {
    }

    ;

    default void onDisable() {
    }

    ;

    default void onPacket(PacketEvent event) {
    }

    ;

    default void onUpdate(UpdateEvent event) {
    }

    ;

}
