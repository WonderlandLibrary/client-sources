package cafe.kagu.kagu.mods;

/**
 * @author DistastefulBannock
 *
 */
public interface Toggleable {
	
	/**
	 * Toggles the toggleable
	 */
	public void toggle();
	
	/**
	 * @return true if enabled, otherwise false
	 */
	public boolean isEnabled();
	
	/**
	 * @return false if enabled, otherwise false
	 */
	public boolean isDisabled();
	
	/**
	 * Enables the toggleable
	 */
	public void enable();
	
	/**
	 * Disables the toggleable
	 */
	public void disable();
	
}
