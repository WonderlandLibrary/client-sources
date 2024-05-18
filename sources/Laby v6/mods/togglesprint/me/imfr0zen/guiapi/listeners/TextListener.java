package mods.togglesprint.me.imfr0zen.guiapi.listeners;

/**
 * 
 * Used by {@link mods.togglesprint.me.imfr0zen.guiapi.components.GuiTextField}
 * @author ImFr0zen
 *
 */
public interface TextListener {
	
	/**
	 * Gets called when entering a character
	 * @param key
	 * @param text
	 */
	void keyTyped(char key, String text);
	
	/**
	 * Gets called when pressing enter or 
	 * @param text
	 */
	void stringEntered(String text);
}
