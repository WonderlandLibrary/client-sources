package cc.slack.features.modules.impl.utilties.autoplays.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.utilties.AutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.IAutoPlay;
import cc.slack.utils.other.TimeUtil;
import net.minecraft.network.play.server.S02PacketChat;

public class HypixelAutoPlay implements IAutoPlay {

    private final TimeUtil timer = new TimeUtil();
    private boolean confirmed;

    @Override
    public void onEnable() {
        confirmed = false;
        timer.reset();
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (confirmed && timer.currentMs >= 1500L) {
            String command = "";
            switch (Slack.getInstance().getModuleManager().getInstance(AutoPlay.class).hypixelmode.getValue()) {
                case "Solo normal": {
                    command = "/play solo_normal";
                    break;
                }
                case "Solo insane": {
                    command = "/play solo_insane";
                }
            }
            mc.thePlayer.sendChatMessage(command);
            timer.reset();
            confirmed = false;
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        String message;
        if (event.getPacket() instanceof S02PacketChat && ((message = ((S02PacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("You won! Want to play again? Click here!") && message.length() < "You won! Want to play again? Click here!".length() + 3 || message.contains("You died! Want to play again? Click here!") && message.length() < "You died! Want to play again? Click here!".length() + 3)) {
            confirmed = true;
            Slack.getInstance().getModuleManager().getInstance(AutoPlay.class).iscorrectjoin();
            timer.reset();
        }
    }

    @Override
    public String toString() {
        return "Hypixel";
    }
}
