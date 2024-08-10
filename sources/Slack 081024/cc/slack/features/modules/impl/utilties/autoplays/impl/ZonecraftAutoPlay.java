package cc.slack.features.modules.impl.utilties.autoplays.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.utilties.AutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.IAutoPlay;
import cc.slack.utils.other.TimeUtil;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class ZonecraftAutoPlay implements IAutoPlay {

    private final TimeUtil timeUtil;

    public ZonecraftAutoPlay() {
        timeUtil = new TimeUtil();
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (!(event.getPacket() instanceof S02PacketChat)) return;

        String text = ((S02PacketChat) event.getPacket()).getChatComponent().getUnformattedText();
        if (!text.toLowerCase().contains("jugar de nuevo"))
            return;

        IChatComponent commandTextComponent = null;
        for (IChatComponent sibling : ((S02PacketChat) event.getPacket()).getChatComponent().getSiblings()) {
            if (sibling.getUnformattedText().toLowerCase().contains("jugar de nuevo")) {
                commandTextComponent = sibling;
                break;
            }
        }

        if (commandTextComponent == null)
            return;

        String command = commandTextComponent.getChatStyle().getChatClickEvent().getValue();
        if (timeUtil.hasReached(500L)) {
            mc.thePlayer.sendChatMessage(command);
            Slack.getInstance().getModuleManager().getInstance(AutoPlay.class).iscorrectjoin();
            timeUtil.reset();
        }

    }

    @Override
    public String toString() {
        return "Zonecraft";
    }


}
