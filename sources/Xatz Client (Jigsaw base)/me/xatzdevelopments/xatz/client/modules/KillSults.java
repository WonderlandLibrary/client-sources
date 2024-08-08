package me.xatzdevelopments.xatz.client.modules;


import me.xatzdevelopments.xatz.utils.*;
import me.xatzdevelopments.xatz.utils.Timer.Stopwatch;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.server.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.Contract;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.*;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.module.state.Category;

import me.xatzdevelopments.xatz.module.Module;

public class KillSults extends Module {

    int counter;
    Stopwatch timer;
    

    public KillSults() {
    	super("KillSults", Keyboard.KEY_NONE, Category.RENDER, "Insult Sigma Virgins.");
      
        this.timer = new Stopwatch();
		
	} // Player
    
	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {
        this.counter = 0;
    }
	
	@Override
	public void onUpdate() {

		super.onUpdate();
	}

	@Override
	public void onPacketRecieved(AbstractPacket packetIn) {
        if (packetIn instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat) packetIn;
            final String msg = packet.getChatComponent().getUnformattedText();
            if (this.currentMode.equals("Mc-Central")) {
                if (msg.contains(this.mc.thePlayer.getName()) && msg.contains("Has Killed")) {
                   this.sendSult();
                }
             } else if (this.currentMode.equals("Hypixel")) {
                if (msg.contains(this.mc.thePlayer.getName()) && msg.contains("was Killed by")) {
                   this.sendSult();
                }
             } else if (this.currentMode.equals("Other")) {
                 if (msg.contains(this.mc.thePlayer.getName()) && msg.contains("foi morto por")) {
                    this.sendSult();
                 }
             } else if (this.currentMode.equals("Mineplex") && msg.contains(this.mc.thePlayer.getName()) && msg.contains("killed by")) {
                this.sendSult();
             }
			packetIn = packet;
          }
		super.onPacketRecieved(packetIn);

       }

       public void sendSult() {
          String[] exhiKillsults = new String[]{"Wow, you just died in a block game", "died in a block game lmfao.", "your mother is of the homophobic type", "That's a #VictoryRoyale!, better luck next time", "used Flux then got backhanded by the face of hypixel", "even loolitsalex has more wins then you", "my grandma plays minecraft better than you", "you should look into purchasing vape", "What's worse your skin or the fact your a casual f3ckin normie", "blind gamers deserve a chance too. I support you.", "that was a pretty bad move", "how does it feel to get stomped on", "do you really like dying this much?", "and jake paul, id choose jake paul", "what does your IQ and kills have in common? They are both low af", "want some PvP advice?", "wow, you just died in a game about legos", "i'm surprised that you were able hit the 'Install'", "I speak English not your gibberish.", "Take the L, kid", "got memed", "is a default skin!!!1!1!1!1!!1!1", "You died in a fucking block game", "likes anime", "Trash dawg, you barely even hit me.", "I just fucked him so hard he left the game", "get bent over and fucked kid", "couldn't even beat 4 block", "Someone get this kid a tissue,", "'s dad is bald", "Your family tree must be a cactus because everybody on it is a prick.", "You're so fucking trash that the binman mistook you for garbage and collected you in the morning", "some kids were dropped at birth but you were clearly thrown at a wall", "go back to your mother's womb you retarded piece of shit", "Thanks for the free kill", "Benjamin's forehead is bigger than your future Minecraft PvP career", "are you even trying?", "You. Are. Terrible.", "my mom is better at this game then you", "lololololol mad? lololololol", "/friend me so we can talk about how useless you are", "\"Staff! Staff! Help me! I am dogcrap at this game and i am getting rekt!\"", "Is it really that hard to trace me while i'm hopping around you?", "Vape is a cool thing you should look into!", "I'm not using reach, you just need to click faster.", "I hope you recorded that, so that you can watch how trash you really are.", "You have to use the left and right mouse button in this game, in case you forgot.", "I think that the amount of ping you have equates to your braincells dumbfuck asshat", "ALT+F4 to remove the problem", "alt+f4 for hidden perk window", "You'll eventually switch back to Fortnite again, so why not do it now?", "go back to fortnite where you belong, you degenerate 5 year old", "I'll be sure to Orange Justice the fucck out of your corpse", "Exhibob better than you!1", "I'm a real gamer, and you just got owned!!", "Take a taste of your own medicine you clapped closet cheater", "go drown in your own salt", "go and suck off prestonplayz, you 7 yr old fanboy", "how are you so bad. I'm losing brain cells just watching you play", "Jump down from your school building with a rope around your neck.", "dominated, monkey :dab:", "Please add me as a friend so that you can shout at me. I live for it.", "i fvcked your dad", "Yeah, I dare you, rage quit. Come on, make us both happy.", "No, you are not blind! I DID own you!", "easy 10 hearted L", "It's almost as if i can hear you squeal from the other side!", "If you read this, you are confirmed homosexual", "have you taken a dump lately? Because I just beat the shit of out you.", "6 block woman beater", "feminist demolisher", "chromosome count doubles the size of this game", "a million years of evolution and we get", "you're so fat that when you had a fire in your house you dialled 999 on the microwave", "is a Fluxuser", "is a Sigmauser", "I suffer from these fukking kicks, grow brain lol", "a crack user", "Hypixel thought could stop us from cheating, huh, you are just as delusional as him", "GET FUCKED IM ON BADLION CLIENT WHORE", "should ask tene if i was hacking or not", "check out ARITHMOS CHANNEL", "I play fortnite duos with your mom", "Lol commit not alive", "How'd you hit the DOWNLOAD button with that aim?", "I'd say your aim is cancer, but at least cancer kills people", "is about as useful as pedals on a wheelchair", "aim is now sponsored by Parkinson's!", "I'd say uninstall but you'd probably miss that too.", "I bet you edate.", "you probably watch tenebrous videos and are intruiged", "Please could you not commit not die kind sir thanks", "you probably suck on door knobs", "go commit stop breathing u dumb idot", "go commit to sucking on door knobs", "the only way you can improve at pvp %s is by taking a long walk off a short pier", "Does not have a good client", "client refused to work", ":potato:", "Super Mario Bros. deathsound", "and tell them how trash they are", "Just do a France 1940, thank you", "You mum your dad the ones you never had", "please be toxic to me, I enjoy it", "knock knock, FBI open up, we saw you searched for cracked vape.", "plez commit jump out of window for free rank", "you didn't even stand a chance!", "you're the type of player to get 3rd place in a 1v1", "I'm not saying you're worthless, but I would unplug your life support to charge my phone", "I didn't know dying was a special ability", "Stephen Hawking had better hand-eye coordination than you", "kids like you were the inspiration for birth control", "you're the definition of bane", "lol bad client what is it exhibition?", "L what are you lolitsalex?", "tene is my favorite youtuber and i bought his badlion client clock so i'm legit", "Don't forget to report me", "Your IQ is that of a Steve", "have you taken a dump lately? Because I just beat the shit of out you.", "dont ever put bean in my donut again.", "2 plus 2 is 4, minus 1 that's your IQ", "I think you need vape", "You just got oneTapped LUL", "You're the inspiration for birth control", "I don't understand why condoms weren't named by you.", "My blind grandpa has better aim than you.", "Exhibob better then you!", "Exhibition >", "your parents abondoned you, then the orphanage did the same", "stop using trash client like sigma.", "your client is worse than sigma, and that's an achievement", "ur fatter than Omikron", "please consider not alive", "probably bought sigma premium", "probably asks for sigma premium keys", "the type of person to murder someone and apologize saying it was a accident", "you're the type of person who would quickdrop irl", "got an F on the iq test.", "Don't forget to report me", "even viv is better than you LMAO", "your mom gaye", "I Just Sneezed On Your Forehead", "your teeth are like stars - golden, and apart.", "Rose are blue, stars are red, you just got hacked on and now you're dead", "i don't hack because watchdog is watching so it would ban me anyway.", "chill out on the paint bro", "You got died from the best client in the game, now with Infinite Sprint bypass", "you're so fat, that your bellybutton reaches your house 20 minutes before you do", "your dick is so small, that you bang cheerios"};
          int randomIndex = ThreadLocalRandom.current().nextInt(0, exhiKillsults.length);
          this.mc.thePlayer.sendChatMessage(exhiKillsults[randomIndex]);
       }
       
	public String[] getModes() {
		return new String[] { "Mc-Central", "Hypixel", "Mineplex", "Other" };
		
    }
	
	public String getModeName() {
		return "Mode: ";
	}
}
