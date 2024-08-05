package fr.dog.module.impl.player;

import com.sun.nio.sctp.IllegalReceiveException;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoGG extends Module {

    public AutoGG() {
        super("AutoGG", ModuleCategory.PLAYER);
        this.registerProperties(mode);
    }

    private ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"GG", "How to play BW", "gg ez"}, "GG");

    public void onEnable() {
        mc.thePlayer.sendChatMessage("/lang english");
    }


    @SubscribeEvent
    private void onPacketReceive(PacketReceiveEvent event){
        if(!event.isCancelled() && event.getPacket() instanceof S02PacketChat s02PacketChat){
            if(s02PacketChat.getChatComponent().getUnformattedText().toLowerCase().contains("1st killer")){
                String txt = switch (mode.getValue()){
                    case "GG" -> {
                        yield "GG";
                    }
                    case "How to play" -> {
                        yield "https://hypixel.net/threads/how-to-play-bedwars.3066561/";
                    }
                    case "gg ez" -> {
                        yield "gg éz";
                    }
                    default -> {
                        yield "gg";
                    }
                };
                mc.thePlayer.sendChatMessage("/ac " + txt);
            }
        }
    }

}
