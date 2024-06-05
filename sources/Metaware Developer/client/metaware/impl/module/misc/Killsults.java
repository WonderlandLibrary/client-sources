package client.metaware.impl.module.misc;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import net.minecraft.network.play.server.S02PacketChat;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Killsults", renderName = "Killsults", category = Category.PLAYER, keybind = Keyboard.KEY_NONE)
public class Killsults extends Module {
    private EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Blocksmc);

    public enum Mode{
        Blocksmc, Hypixel
    }
    String[] list = {"Should've gotten Whiz nigga", "Shit on by Whiz Client", "How are you Whizless in 2021?", "Whiz > ALL", "GG retard, get good and get Whiz"};

    @EventHandler
    private Listener<PacketEvent> packetEventListener = event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;;
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = event.getPacket();

            switch (mode.getValue()){
                case Blocksmc:{
                    if (packet.getChatComponent().getUnformattedText().contains("killed by ") && packet.getChatComponent().getUnformattedText().contains(mc.thePlayer.getGameProfile().getName())){
                        this.mc.thePlayer.sendChatMessage(list[ThreadLocalRandom.current().nextInt(0, list.length)]);
                    }
                }
            }
        }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        setSuffix(mode.getValue().toString());
    };
}
