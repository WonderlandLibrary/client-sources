package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S02PacketChat;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;

@ModuleMetaData(name = "Streamer Mode", description = "Hides your name and skin from other players", category = ModuleCategoryEnum.MISC)
public final class StreamerModeModule extends AbstractModule {
    private final BooleanSetting hideName = new BooleanSetting("Hide Name", true);
    private final BooleanSetting hideSkin = new BooleanSetting("Hide Skin", true);
    private final BooleanSetting hideOtherPlayers = new BooleanSetting("Hide Other Players", true);
    private final BooleanSetting hideJoinMessages = new BooleanSetting("Hide Join Messages", true);

    public StreamerModeModule() {
        this.registerSettings(this.hideName, this.hideSkin, this.hideOtherPlayers, this.hideJoinMessages);
    }

    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat) e.getPacket();
            final String message = packet.getChatComponent().getUnformattedText();

            if (message.contains("joined the game") && this.hideJoinMessages.getValue()) {
                e.setCancelled(true);
            }
        }
    };

    public final boolean isHideName() {
        return this.hideName.getValue();
    }

    public final boolean isHideSkin() {
        return this.hideSkin.getValue();
    }

    public final boolean isHideOtherPlayers() {
        return this.hideOtherPlayers.getValue();
    }

    public final boolean isHideJoinMessages() {
        return this.hideJoinMessages.getValue();
    }
}
