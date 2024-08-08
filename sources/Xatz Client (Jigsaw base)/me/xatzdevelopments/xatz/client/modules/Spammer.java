package me.xatzdevelopments.xatz.client.modules;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.MarisaTimer;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.play.client.C01PacketChatMessage;

/*
 * Module Name: Spammer
 * Author: Napoleon ZoomberParts
 * Date: 22/11/20
 */


public class Spammer extends Module {

	 private MarisaTimer time = new MarisaTimer();
	  private String[] phraseList = { "Do you like Xatz Too?" , "Want Xatz? Go to XatzClient.tk, Skills made free" , "If i had a penny for all pay to win people on redesky, i would be a millionare!", "All Hail Napoleon!", "Use Xatz, maybe you can finally win for once xd", "My client is full of kittens, this boosts my skills.", "NO! Im not gonna turn it off >:D", "lmao learn how to play", "me and de boyz when we get beanz", "u suck at this game just quit!", "Whats spam?","Whats the difference between me and you? you are a virgin badlion/vanillamc player while I use Xatz!", "Guess what me and your dad did, f​uck your mum thats what we did B!t6h", "Do I need to f​u​ck​i​n​g ressurect hitler to teach you how to play a block game?!?!", "Who tf is marshy and why is he a furry", "uwu my cat made marshy his bi​tc​h", "Marshy is a very bad skript kitty", "Death to marshy, Xatz shall prevail"};
	  private int lastUsed;
	  
	public Spammer() {
		super("Spammer", Keyboard.KEY_NONE, Category.MISC, "Spams some dank memes!");
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onUpdate() {
		 if (this.time.delay(randomDelay()))
		    {
		      mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(randomPhrase()));
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
	    
		  
		  private int randomDelay()
		  {
		    Random randy = new Random();
		    int randyInt = randy.nextInt(4000) + 4000; // Perfect delay for Redesky - Napoleon
		    return randyInt;
		  }
		}
