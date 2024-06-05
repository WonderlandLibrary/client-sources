package rip.helium.cheat.impl.visual;

import rip.helium.utils.property.impl.*;
import rip.helium.cheat.*;
import rip.helium.utils.*;
import rip.helium.utils.property.abs.*;
import rip.helium.event.minecraft.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.entity.*;
import me.hippo.systems.lwjeb.annotation.*;

public class KillSults extends Cheat
{
    int counter;
    Stopwatch timer;
    public StringsProperty mode;
    public BooleanProperty autogg;

    public KillSults() {
        super("KillSult", "Insult those sigma skids when you kill them!!111!1!", CheatCategory.VISUAL);
        this.timer = new Stopwatch();
        this.mode = new StringsProperty("Mode", "", null, false, true, new String[] { "AutoL", "Insult"}, new Boolean[] { true, false });
        this.autogg = new BooleanProperty("AutoGG", "", null, true);
        this.timer = new Stopwatch();
        this.registerProperties(this.mode, this.autogg);
    }

    public void onEnable() {
        this.counter = 0;
    }

    @Collect
    public void onPacket(final ProcessPacketEvent e) {
        if (e.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat) e.getPacket();
            final String msg = packet.getChatComponent().getUnformattedText();
            try {
                if (msg.contains("Has Killed") || (msg.contains("killed by") && msg.contains(String.valueOf(Cheat.getPlayer().getName())))) {
                    for (final Object o : KillSults.mc.theWorld.getLoadedEntityList()) {
                        if (o instanceof EntityLivingBase && o != KillSults.mc.thePlayer) {
                            final EntityLivingBase ent = (EntityLivingBase) o;
                            if (!msg.contains(ent.getName())) {
                                continue;
                            }
                            if (this.mode.getValue().get("AutoL")) {
                                KillSults.mc.thePlayer.sendChatMessage("L, " + ent.getName());
                            } else if (this.mode.getValue().get("Insult")) {
                                final String[] messages = {"Did ur parents ask you to run away, " + ent.getName(), "I don't cheat, " + ent.getName() + " I just use Helium.", "You do be lookin' kinda bad at the game, " + ent.getName(), "Did someone leave your cage open " + ent.getName() + "?", "rage at me on discord Kansio#6759" + ent.getName(), "Is being in the spectator mode fun, " + ent.getName() + "?", "I understand why your parents abused you, " + ent.getName(), "Do you practice being this bad, " + ent.getName(), "hi my name is " + ent.getName() + " and my iq is -420!", ent.getName() + "'s aim is sponsored by Parkinson's", ent.getName() + " go take a long walk on a short bridge", ent.getName() + " probably plays fortnite lmao.", "plz no repotr i no want ban " + ent.getName() + "!", ent.getName() + ", you probably have the coronavirus.", ent.getName() + ", you really like taking L's.", ent.getName() + " drown in your own salt", ent.getName() + ", I'm not saying you're worthless, but i'd unplug ur lifesupport to charge my phone.", ent.getName() + ", could you please commit not alive?", ent.getName() + " I don't cheat, you just need to click faster", ent.getName() + " I speak english not your gibberish", "Your mom do be lookin' kinda black doe, " + ent.getName(), "Hey look! It's a fortnite player " + ent.getName(), "Need some pvp advice? " + ent.getName() + ".", ent.getName() + ", do you really like dying this much?", ent.getName() + " probably reported me.", ent.getName() + " you're the type to get 3rd place in a 1v1.", ent.getName() + " how does it feel to get stomped on?", ent.getName() + ", the type of guy to use sigma.", ent.getName() + " that's a #VictoryRoyale! better luck next time!", "lol " + ent.getName() + " probably speaks dog eater", ent.getName() + " is a fricking monkey (black person)", ent.getName() + " be like: ''I'm black and this a robbery''", ent.getName() + ", even your mom is better than you in this game.", ent.getName() + " go back to fortnite you degenerate.", ent.getName() + " your iq is that of a steve.", ent.getName() + " go commit stop breathing plz", ent.getName() + ", your parents abandoned you, then the orphanage did the same", ent.getName() + " probably bought sigma premium", ent.getName() + " probably got an error on his hello world program lmao", ent.getName() + " how'd you hit the download button with that aim", "Someone in 1940 forgot to gas you, " + ent.getName() + " :)", ent.getName() + ", did your dad go get milk and never return?", ent.getName() + " you died in a block game.", ent.getName() + " thinks that his ping is equal to his iq.", ent.getName() + " stop eating dogs", "if the body is 70% water then how is " + ent.getName() + "'s body 100% salt?", "yo stop spreading corona, " + ent.getName() + ". I know you're asian, but stop spreading it.", ent.getName() + "'s got dropped him on his head by his parents.", "yo " + ent.getName() + " come rage at me on discord Kansio#6759", ent.getName() + " doesn't have parents L", "how are you so bad? im losing brain cells while watching you play", ent.getName() + " even lolitsalex has more wins than you", "some kids were dropped at birth, but " + ent.getName() + " got thrown at the wall.", ent.getName() + " black"};
                                if (this.counter >= messages.length) {
                                    this.counter = 0;
                                }
                                KillSults.mc.thePlayer.sendChatMessage(String.valueOf(messages[this.counter]));
                            }
                            ++this.counter;
                        }
                    }
                }
            } catch (Exception GAY) {
                GAY.printStackTrace();
            }
        }
    }

    @Collect
    public void onPacket3(final ProcessPacketEvent e) {
        if (e.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat)e.getPacket();
            final String msg = packet.getChatComponent().getUnformattedText();
            if ((msg.contains("You won!") || msg.contains("Has Won The Game!") || msg.contains("1st Place")) && this.timer.hasPassed(4000.0)) {
                KillSults.mc.thePlayer.sendChatMessage("GG! Are you mad at me for hacking? Rage at me on discord: Kansio#6759");
            }
        }
    }

    /*/@Collect
    public void nmasad(final ProcessPacketEvent e) {
        if (e.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat)e.getPacket();
            final String msg = packet.getChatComponent().getUnformattedText();
            final boolean cansay = true;
            if (msg.contains("why hack") || msg.contains("why cheat") || msg.contains("why do u cheat") || msg.contains("why do you hack") || msg.contains("hack") || msg.contains("cheat") || msg.contains("Hack") || (msg.contains("hax") && !msg.contains(KillSults.mc.thePlayer.getName() + ":"))) {
                KillSults.mc.thePlayer.sendChatMessage("no cheat i just use helium gamer mod");
            }
        }
    }/*/
}
