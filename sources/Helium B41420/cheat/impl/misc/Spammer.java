package rip.helium.cheat.impl.misc;
//    
// Created by Kansio on 3/2/2020.
//


import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.play.client.C01PacketChatMessage;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.utils.Timer;
//import sun.security.pkcs11.Secmod;

import java.util.Random;

public class Spammer extends Cheat {

    private Timer time;
    private String[] phraseList;
    private String[] nphraseList;
    private int lastUsed;
    public boolean nspam = false;

    public Spammer() {
        super("Spammer", "epicogamero", CheatCategory.MISC);
        this.time = new Timer();
        this.phraseList = new String[] { "Helium Client (322020 - Developer) by Kansio#6759", "Sub to Kansio on YT :)", "Helium good, sigma bad!", "sigma gey, get Helium - Kansio#6759 :)", "Rage at me on discord for killin u on mincecraft Kansio#6759", "yes i have cool gamin chair helium!111", "whatz the difference between a j3w and a pizza? the pizza wont scream in the oven"};
        this.nphraseList = new String[] { "" };
    }

    @Collect
    public void onPreUpdate(final PlayerUpdateEvent E) {
        if (this.time.delay((float)this.randomDelay())) {
            if (this.nspam) {
                mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(this.nrandomPhrase()));
                this.time.reset();
            }
            else {
                mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(this.randomPhrase()));
                this.time.reset();
            }
        }
    }

    private String randomPhrase() {
        Random rand;
        int randInt;
        for (rand = new Random(), randInt = rand.nextInt(this.phraseList.length); this.lastUsed == randInt; randInt = rand.nextInt(this.phraseList.length)) {}
        this.lastUsed = randInt;
        return this.phraseList[randInt];
    }

    private String nrandomPhrase() {
        Random rand;
        int randInt;
        for (rand = new Random(), randInt = rand.nextInt(this.nphraseList.length); this.lastUsed == randInt; randInt = rand.nextInt(this.nphraseList.length)) {}
        this.lastUsed = randInt;
        return this.nphraseList[randInt];
    }

    private int randomDelay() {
        final Random randy = new Random();
        final int randyInt = randy.nextInt(10000) + 50000;
        return randyInt;
    }
}

