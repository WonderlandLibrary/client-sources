// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.perry.mcdonalds.event.events.PacketEvent;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.modules.Module;

public class ChatModifier extends Module
{
    private static ChatModifier INSTANCE;
    private final Timer timer;
    public Setting<Suffix> suffix;
    
    public ChatModifier() {
        super("ChatSuffix", "Modifies your chat", Category.MISC, true, false, false);
        this.timer = new Timer();
        this.suffix = (Setting<Suffix>)this.register(new Setting("Suffix", (T)Suffix.MCDONALDS, "Your Suffix."));
        this.setInstance();
    }
    
    public static ChatModifier getInstance() {
        if (ChatModifier.INSTANCE == null) {
            ChatModifier.INSTANCE = new ChatModifier();
        }
        return ChatModifier.INSTANCE;
    }
    
    private void setInstance() {
        ChatModifier.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = event.getPacket();
            String s = packet.getMessage();
            if (s.startsWith("/")) {
                return;
            }
            switch (this.suffix.getValue()) {
                case MCDONALDS: {
                    s += " \u23d0 \u1d0d\u1d04\u1d05\u1d0f\u0274\u1d00\u029f\u1d05\ua731 \u1d04\u029f\u026a\u1d07\u0274\u1d1b";
                    break;
                }
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            packet.message = s;
        }
    }
    
    static {
        ChatModifier.INSTANCE = new ChatModifier();
    }
    
    public enum Suffix
    {
        MCDONALDS;
    }
}
