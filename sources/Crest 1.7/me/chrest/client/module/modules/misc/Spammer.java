// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import java.util.Random;
import me.chrest.event.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import me.chrest.utils.TimeUtils;
import me.chrest.client.module.Module;

@Mod
public class Spammer extends Module
{
    private TimeUtils time;
    private String[] phraseList;
    private int lastUsed;
    
    public Spammer() {
        this.time = new TimeUtils();
        this.phraseList = new String[] { "Crest 1.7 by Ruggero", "Searching RuggeroPvP a YT", "drink my ass", "run nigga run!", "what are those!?!?", "nigger faggot", "die", "nigger style", "mc.thePlayer() im a god coder", "yes helo. latememe here!", "wurst is best", "OpenGL errer <<=== KYS", "Skids... Skids everywhere...", "best clarinet 2k18", "i has op hax", "1v1 me u ho", "spam. spam spam. spam spam spam. getrekt antispam", "deeznutz lol", "ez boii", "u bad haxor", "RECORDED", "FloodHacker best hacker" };
    }
    
    @EventTarget
    private void onPreUpdate(final UpdateEvent E) {
        if (this.time.delay(this.randomDelay())) {
            ClientUtils.mc().getNetHandler().addToSendQueue(new C01PacketChatMessage(this.randomPhrase()));
            this.time.reset();
        }
    }
    
    private String randomPhrase() {
        Random rand;
        int randInt;
        for (rand = new Random(), randInt = rand.nextInt(this.phraseList.length); this.lastUsed == randInt; randInt = rand.nextInt(this.phraseList.length)) {}
        this.lastUsed = randInt;
        return this.phraseList[randInt];
    }
    
    private int randomDelay() {
        final Random randy = new Random();
        final int randyInt = randy.nextInt(2000) + 2000;
        return randyInt;
    }
}
