package me.swezedcode.client.module.modules.World;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Spammer extends Module {

	private TimerUtils time;
	private String[] phraseList;
	private int lastUsed;

	public Spammer() {
		super("Spammer", Keyboard.KEY_NONE, 0xFF9EA9FF, ModCategory.World);
		setDisplayName("Spammer");
		this.time = new TimerUtils();
		this.phraseList = new String[] { "Tea Clarinet", "Want a cup of tea? C:", "Omfg, such skids..",
				"Now when we talk buisness we use Nya to scam people", "Can you play on a clarinet?",
				"Sign up for wizardhax to get best clints 2002", "I bet u can't get a database working", "ez",
				"Skids", "Loading... What Tea you want anyways?",
				"Green Tea?", "Red Tea?" };
	}

	@EventListener
	public void onPreUpdate(final EventMotion event) {
		this.phraseList = new String[] { "Tea Client", "Want a cup of tea? C:", "Omfg, such skids..",
				"Now when we talk buisness we use Nya to scam people", "Can you play on a clarinet?",
				"Sign up for wizardhax to get best clints 2002", "I bet u can't get a database working", "ez",
				"Skids", "Loading... What Tea you want anyways?",
				"Green Tea?", "Red Tea?" };
		if (event.getType() == EventType.PRE && time.hasReached(this.randomDelay())) {
			this.mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(this.randomPhrase()));
			time.rt();
		}
	}

	private String randomPhrase() {
		Random rand;
		int randInt;
		for (rand = new Random(), randInt = rand.nextInt(
				this.phraseList.length); this.lastUsed == randInt; randInt = rand.nextInt(this.phraseList.length)) {
		}
		this.lastUsed = randInt;
		return this.phraseList[randInt];
	}

	private int randomDelay() {
		final Random randy = new Random();
		final int randyInt = randy.nextInt(3);
		return randyInt;
	}
}
