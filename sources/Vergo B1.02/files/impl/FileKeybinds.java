package xyz.vergoclient.files.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import com.google.gson.annotations.SerializedName;

import xyz.vergoclient.keybinds.Keybind;
import xyz.vergoclient.modules.impl.miscellaneous.ClickGui;

public class FileKeybinds {
	
	@SerializedName(value = "keybinds")
	public ArrayList<Keybind> keybinds = new ArrayList<>(Arrays.asList(new Keybind(Keyboard.KEY_RSHIFT, new ClickGui().getName())));
	
}
