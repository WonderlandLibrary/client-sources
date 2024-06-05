package xyz.vergoclient.util.main;

import org.lwjgl.opengl.Display;

import xyz.vergoclient.Vergo;

public class DisplayUtils {

	public static void setTitle() {
		Display.setTitle("Vergo " + Vergo.version);
	}

	public static void setCustomTitle(String title) {
		Display.setTitle(title);
	}
	
}
