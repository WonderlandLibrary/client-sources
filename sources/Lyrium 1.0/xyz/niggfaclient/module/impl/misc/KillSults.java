// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.entity.Entity;
import org.apache.commons.lang3.RandomUtils;
import java.util.function.Predicate;
import java.util.Arrays;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.combat.KillAura;
import net.minecraft.network.play.server.S02PacketChat;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "KillSults", description = "Insults people when you kill them", cat = Category.MISC)
public class KillSults extends Module
{
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public KillSults() {
        S02PacketChat s02;
        String text;
        Entity target;
        String[] insults;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE) {
                try {
                    if (e.getPacket() instanceof S02PacketChat) {
                        s02 = (S02PacketChat)e.getPacket();
                        text = s02.getChatComponent().getUnformattedText();
                        target = ModuleManager.getModule(KillAura.class).target;
                        insults = new String[] { "your difficulty settings must be stuck on easy", target.getName() + " is an skidded fornite ncp", "go back to fortnite already.", "stop being sweaty, just buy a good chair", "lol you probably speak dog eater", "you go to the doctors and they say you shrunk", "your parents abandoned you, then the orphanage did the same", target.getName() + " forgot to inject his vape", target.getName() + " got their skill from aliexpress", target.getName() + " get beamed", "welcome to the game, " + target.getName(), target.getName() + " shops for pc parts on wish", target.getName() + " is fat" };
                        if (!text.contains(":")) {
                            if (Arrays.stream(new String[] { "by *", "fue destrozado a manos de *", "* asesino a", "fue asesinado por *" }).anyMatch(text.replace(this.mc.thePlayer.getName(), "*")::contains)) {
                                this.mc.thePlayer.sendChatMessage(insults[RandomUtils.nextInt(0, insults.length)]);
                            }
                        }
                    }
                }
                catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
