package cc.slack.features.modules.impl.utilties.autoplays.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.utilties.AutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.IAutoPlay;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class LibrecraftAutoPlay implements IAutoPlay {

    @Override
    public void onPacket(PacketEvent event) {
        if (!(event.getPacket() instanceof S02PacketChat)) return;

        IChatComponent chatComponent = ((S02PacketChat) event.getPacket()).getChatComponent();
        String unformattedText = chatComponent.getUnformattedText();

        if (unformattedText.contains("¡Partida finalizada!")) {
            mc.thePlayer.sendChatMessage("/saliryentrar");
            Slack.getInstance().getModuleManager().getInstance(AutoPlay.class).iscorrectjoin();
        }
    }

    @Override
    public String toString() {
        return "Librecraft";
    }
}
