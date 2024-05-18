package com.kilo.input;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;

public class Input {

	public static int[] mouse = new int[2];
	
	public static void handle() {
		if (Keyboard.isCreated()) {
			Keyboard.enableRepeatEvents(true);
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					Kilo.kilo().keyboardPress(Keyboard.getEventKey());
					Kilo.kilo().keyTyped(Keyboard.getEventKey(), Keyboard.getEventCharacter());
				} else {
					Kilo.kilo().keyboardRelease(Keyboard.getEventKey());
				}
			}
			Keyboard.enableRepeatEvents(false);
		}
		if (Mouse.isCreated()) {
			while (Mouse.next()) {
				if (Mouse.getEventButton() == -1) {
					if (Mouse.getEventDWheel() != 0) {
						Kilo.kilo().mouseScroll(Mouse.getEventDWheel());
					}
					mouse[0] = Mouse.getEventX();
					mouse[1] = Display.getHeight()-Mouse.getEventY();
				} else {
					if (Mouse.getEventButtonState()) {
						Kilo.kilo().mouseClick(Mouse.getX(), Display.getHeight() - Mouse.getY(), Mouse.getEventButton());
					} else {
						Kilo.kilo().mouseRelease(Mouse.getX(), Display.getHeight() - Mouse.getY(), Mouse.getEventButton());
					}
				}
			}
		}
	}
}
