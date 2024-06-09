package me.swezedcode.client;

import java.awt.Font;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.opengl.Display;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

import me.swezedcode.client.gui.font.FontRenderer;
import me.swezedcode.client.irc.TeaIRCBot;
import me.swezedcode.client.manager.managers.CommandManager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.FileUtils;
import me.swezedcode.client.utils.console.ConsoleUtils;
import me.swezedcode.client.utils.events.BoundingBoxEvent;

public class Tea implements Runnable {
	private static Tea instance = new Tea();

	public static FontRenderer mainMenuFont;
	public static FontRenderer fontRenderer;
	public static FontRenderer bigFont;
	public static FontRenderer waterMark;

	private static TeaIRCBot bot;

	private String name = new String("Tea");
	private float version = new Float(11.6F);

	/** Method that will run in Minecraft.class, also executes everything in the run void. */
	public void startClient() {
		this.run();
	}

	public String getName() {
		return name;
	}

	public float getVersion() {
		return version;
	}

	public static Tea getInstance() {
		return instance;
	}

	public static void setBot(String username) {
		Tea.bot = new TeaIRCBot(username);
	}

	public static TeaIRCBot getBot() {
		return Tea.bot;
	}

	@Override
	public void run() {
		ConsoleUtils.writeLine("Launching " + (String) getName());
		ConsoleUtils.writeLine("Adding sounds handlers");
		try {
			FileUtils.clip = AudioSystem.getClip();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ConsoleUtils.writeLine("Setting title title");
		/** Client title */
		Display.setTitle((String) getName() + " b" + (Float) getVersion());
		
		ConsoleUtils.writeLine("Adding handlers");
		/** Call event handlers */
		EventManager.register(new CommandManager());
		
		ConsoleUtils.writeLine("Checking files");
		/** Create files if not existing */
		FileManager.setup();
		ConsoleUtils.writeLine("Registering fonts");
		/** Adding fonts */
		this.fontRenderer = FontRenderer.createFontRenderer(FontRenderer.FontObjectType.CFONT,
				new Font("Arial", 0, 18));
		this.bigFont = FontRenderer.createFontRenderer(FontRenderer.FontObjectType.CFONT, new Font("Arial", 0, 22));
		this.waterMark = FontRenderer.createFontRenderer(FontRenderer.FontObjectType.CFONT,
				new Font("FIFA Welcome", 0, 18));
		this.mainMenuFont = FontRenderer.createFontRenderer(FontRenderer.FontObjectType.CFONT,
				new Font("FIFA Welcome", 0, 30));
		ConsoleUtils.writeLine("Done, welcome to Tea Client!");
	}

}