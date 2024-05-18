package mods.togglesprint.me.imfr0zen.guiapi.listeners;

/**
 * 
 * Used by {@link mods.togglesprint.me.imfr0zen.guiapi.components.GuiSlider}
 * @author ImFr0zen
 *
 */
public interface ValueListener {
	
	/**
	 * Gets called after moving the cursor.
	 * @param value
	 */
	void valueUpdated(float value);
	
	/**
	 * Gets called after releasing the cursor.
	 * @param value
	 */
	void valueChanged(float value);
	
}
