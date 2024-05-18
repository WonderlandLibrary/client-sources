package com.darkcart.xdolf.mods.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.Timer;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

public class Spammer extends Module {
	
	public Spammer() {
		super("Spammer", "Spams the server with chat messages.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	public static String mode = "normal";
	public static int delay = 1800;
	public static String message = "test";
	Timer mode0, mode1, mode2;
	public static String file = "";

	@Override
	public void onEnable() {
		if (mode.equals("normal")) {
			mode0 = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Wrapper.getPlayer().sendChatMessage(message);
				}

			});
			mode0.start();
		}
		if (mode.equals("antispam")) {
			mode1 = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Wrapper.getPlayer().sendChatMessage(message + " " + getRandomCharacters());
				}

			});
			mode1.start();
		}
		if (mode.equals("file")) {
			try {
				final String[] lines = new String(Files.readAllBytes(Paths.get(file))).split("\n");
				final int MAX_INDEX = lines.length;
				mode2 = new Timer(delay, new ActionListener() {
					int index = 0;
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (index < MAX_INDEX) {
							Wrapper.getPlayer().sendChatMessage(lines[index]);
			                  index++;
			                  if (index == MAX_INDEX)
			                	  index = 0;
						}
					}
				});
				mode2.start();
			} catch (Exception e) {}
		}
	}

	@Override
	public void onDisable() {
		try {
			mode0.stop();
		}catch(Exception ex){}
		try {
			mode1.stop();
		}catch(Exception ex){}
		try {
			mode2.stop();
		}catch(Exception ex){}
	}

	private String getRandomCharacters() {
		String chars = "abcdefhijklmnopqrstuvxyz0123456789";
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			b.append(chars.toCharArray()[new Random().nextInt(chars.length())]);
		}
		return "[" + b.toString() + "]";
	}
}
