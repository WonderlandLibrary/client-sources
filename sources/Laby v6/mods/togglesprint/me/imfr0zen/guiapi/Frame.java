package mods.togglesprint.me.imfr0zen.guiapi;

/**
 * 
 * @author ImFr0zen
 * 	
 */
public interface Frame {

	/**
	 * Gets called at initialisation
	 */
	void init();
	
	/**
	 * Renders the Frame
	 * @param mouseX
	 * @param mouseY
	 */
	void render(int mouseX, int mouseY);
	
	/**
	 * Gets called when the user clicks with the mouse
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 */
	void mouseClicked(int mouseX, int mouseY, int mouseButton);
	
	/**
	 * Gets called when the user presses a key
	 * @param keyCode
	 * @param typedChar
	 */
	void keyTyped(int keyCode, char typedChar);
	
}
