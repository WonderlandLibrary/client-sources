package xyz.vergoclient.modules.impl.miscellaneous;

import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.ServerUtils;
import xyz.vergoclient.util.main.Timer;
import xyz.vergoclient.util.main.TimerUtil;

public class AutoPlayGG extends Module implements OnEventInterface {

    public AutoPlayGG() {
        super("AutoPlay", Category.MISCELLANEOUS);
    }

    //public static NumberSetting commandDelay = new NumberSetting("Delay", 1000, 0, 5000, 10);

    public ModeSetting teamMode = new ModeSetting("Team Mode", "Solo Normal", "Solo Normal", "Solo Insane");

    public static BooleanSetting autoGG = new BooleanSetting("Auto-GG", true);

    public static TimerUtil autoTimer = new TimerUtil();

    @Override
    public void onEnable() {
        if(autoGG.isEnabled()) {
            setInfo("AutoGG");
        } else {
            setInfo("");
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void loadSettings() {

        addSettings(teamMode, autoGG);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventReceivePacket && e.isPre()) {
            if (!ServerUtils.isOnHypixel()) {
                return;
            }

            setInfo(teamMode.getMode());

            EventReceivePacket packetEvent = (EventReceivePacket) e;

            if (packetEvent.packet instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) packetEvent.packet;

                if (teamMode.is("Solo Normal") || teamMode.is("Solo Insane")) {
                    if (packet.getChatComponent().getUnformattedText().contains("You died! Want to play again?") || packet.getChatComponent().getUnformattedText().contains("You won! Want to play again?") ||
                            packet.getChatComponent().getUnformattedText().contains("Queued! Use the bed to return to lobby!") || packet.getChatComponent().getUnformattedText().contains("Queued! Use the bed to cancel!")) {

                        if (autoGG.isEnabled()) {
                            mc.thePlayer.sendChatMessage("gg");
                        }

                        doCommands();
                    } else {
                        if (packet.getChatComponent().getFormattedText().contains("A player has been removed from your game.")) {
                            packet.chatComponent = new ChatComponentText(packet.getChatComponent().getFormattedText().replace("A player", "A skidder"));
                        }
                    }
                }
            }
        }
    }

    public void doCommands() {
        //autoTimer.reset();
        //if(autoTimer.hasTimeElapsed(commandDelay.getValueAsLong(), true)) {
        if (autoTimer.hasTimeElapsed(3000, true))
            if (teamMode.is("Solo Normal")) {
                mc.thePlayer.sendChatMessage("/play solo_normal");
            } else if (teamMode.is("Solo Insane")) {
                mc.thePlayer.sendChatMessage("/play solo_insane");
            } else if (teamMode.is("Teams Normal")) {
                mc.thePlayer.sendChatMessage("/play teams_normal");
            } else if (teamMode.is("Teams Insane")) {
                mc.thePlayer.sendChatMessage("/play teams_insane");
            }
        //}
    }

}
