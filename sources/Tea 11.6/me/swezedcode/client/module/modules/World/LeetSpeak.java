package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventMessage;

public class LeetSpeak extends Module {

	public LeetSpeak() {
		super("1337Speak", Keyboard.KEY_NONE, 0xFF75FDFF, ModCategory.World);
		setDisplayName("1337 Speak");
	}

	@EventListener
	private void messageSent(final EventMessage event) {
		final String letters = event.getMessage().replaceAll("e", "3").replaceAll("a", "4").replaceAll("i", "!").replaceAll("o", "0").replaceAll("s", "5").replaceAll("t", "7").replaceAll("E", "3").replaceAll("A", "4").replaceAll("I", "!").replaceAll("O", "0").replaceAll("S", "5").replaceAll("T", "7");;
		final boolean valid = !event.getMessage().startsWith("/") && !event.getMessage().startsWith("-") && !event.isCancelled();
		if(valid) {
			event.setMessage(letters);
		}
	}
	
}
