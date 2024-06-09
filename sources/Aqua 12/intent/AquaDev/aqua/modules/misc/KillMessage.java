// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import java.util.Random;
import events.listeners.EventPacket;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class KillMessage extends Module
{
    private final String[] messages;
    
    public KillMessage() {
        super("KillMessage", Type.Misc, "KillMessage", 0, Category.Misc);
        this.messages = new String[] { "Get Good Get Aqua, Tenacity = Shit?", "ac is trash", "ezs", "Hacking is Fun", "Aqua Best", "Killed BY Aqua, https://discord.gg/qnzcdrCx7Q" };
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPacket) {
            final Packet e = EventPacket.getPacket();
            final Random rnd = new Random();
            if (e instanceof S02PacketChat) {
                final S02PacketChat s02PacketChat = (S02PacketChat)e;
                final String cp21 = s02PacketChat.getChatComponent().getUnformattedText();
                if (cp21.contains("was killed by " + KillMessage.mc.session.getUsername())) {
                    KillMessage.mc.thePlayer.sendChatMessage(this.messages[rnd.nextInt(this.messages.length)]);
                }
                if (cp21.contains("was slain by " + KillMessage.mc.session.getUsername())) {
                    KillMessage.mc.thePlayer.sendChatMessage(this.messages[rnd.nextInt(this.messages.length)]);
                }
            }
        }
    }
}
