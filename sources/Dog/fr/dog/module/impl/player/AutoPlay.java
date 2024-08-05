package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoPlay extends Module {

    public AutoPlay() {
        super("AutoPlay", ModuleCategory.PLAYER);
        this.registerProperties(mode);
    }

    public void onEnable() {
        mc.thePlayer.sendChatMessage("/lang english");
    }

    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Solo Insane", "Solo Normal", "BedWars Solo", "BedWars Duo", "BedWars Trio", "BedWars 4s"}, "Solo Insane");
    private static final String win = "You won! Want to play again? Click here!";
    private static final String lose = "You died! Want to play again? Click here!";
    private static final String bw = "1st Killer";

    @SubscribeEvent
    private void onPacketReceive(PacketReceiveEvent event) {
        this.setSuffix(mode.getValue());
        if (!event.isCancelled() && event.getPacket() instanceof S02PacketChat s02PacketChat) {
            String chatMessage = s02PacketChat.getChatComponent().getUnformattedText();
            if (chatMessage.contains(win) || chatMessage.contains(lose) || chatMessage.contains(bw)) {
                String command = "/play ";
                switch (mode.getValue()) {
                    case "Solo Insane":
                        command += "solo_insane";
                        break;
                    case "Solo Normal":
                        command += "solo_normal";
                        break;
                    case "BedWars Solo":
                        command += "bedwars_eight_one";
                        break;
                    case "BedWars Duo":
                        command += "bedwars_eight_two";
                        break;
                    case "BedWars Trio":
                        command += "bedwars_four_three";
                        break;
                    case "BedWars 4s":
                        command += "bedwars_four_four";
                        break;
                }
                mc.thePlayer.sendChatMessage(command);
            }
        }
    }
}
