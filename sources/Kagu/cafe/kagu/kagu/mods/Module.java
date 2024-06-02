/**
 * 
 */
package cafe.kagu.kagu.mods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.eventBus.impl.EventModuleStateUpdate;
import cafe.kagu.kagu.settings.Setting;
import net.minecraft.client.Minecraft;

/**
 * @author lavaflowglow
 *
 */
public abstract class Module implements Toggleable {
	
	private String name = ""; // The name of the module
	private String[] info = new String[0]; // The info displayed next to the name on the arraylist
	private Setting[] settings = new Setting[0]; // Any settings that the module may have
	private Category category; // The category of the module
	private boolean enabled = false, isClickguiExtended = false;
	private double clickguiExtension = 0, clickguiToggle = 0, arraylistAnimation = 0;
	
	private static Logger logger = LogManager.getLogger();
	
	protected static Minecraft mc = Minecraft.getMinecraft(); // The minecraft instance used for the modules
	
	/**
	 * @param name The name of the module
	 */
	public Module(String name, Category category) {
		
		// We use these two chars when saving and loading files, if the config name contains them it could create issues
		if (name.contains(Kagu.UNIT_SEPARATOR) || name.contains(Kagu.RECORD_SEPARATOR) || name.contains(Kagu.GROUP_SEPARATOR)) {
			logger.error("Name of module (" + name + ") contains a forbidden character, please refrain from using the unit and record separator character when naming modules as they break file loading");
			System.exit(0);
			return;
		}
		this.name = name;
		this.category = category;
	}
	
	/**
	 * Called when the module is enabled
	 */
	public void onEnable() {}
	
	/**
	 * Called when the module is disabled
	 */
	public void onDisable() {}

	/**
	 * @param settings An array of settings that will replace the current array of
	 *                 settings on the module
	 */
	protected void setSettings(Setting... settings) {
		this.settings = settings;
	}
	
	/**
	 * @param info An array of strings that will be displayed next to the module on
	 *             the arraylist
	 */
	protected void setInfo(String... info) {
		this.info = info;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the info
	 */
	public String[] getInfo() {
		return info;
	}
	
	/**
	 * @param separator The separator to use
	 * @return the info array formatted as a string
	 */
	public String getInfoAsString(String separator) {
		String info = "";
		for (String str : getInfo()) {
			info += separator + str;
		}
		return info;
	}
	
	/**
	 * @return the settings
	 */
	public Setting[] getSettings() {
		return settings;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return true if enabled
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return true if disabled
	 */
	@Override
	public boolean isDisabled() {
		return !enabled;
	}
	
	/**
	 * @return the clickguiExtension
	 */
	public double getClickguiExtension() {
		return clickguiExtension;
	}

	/**
	 * @param clickguiExtension the clickguiExtension to set
	 */
	public void setClickguiExtension(double clickguiExtension) {
		this.clickguiExtension = clickguiExtension;
	}

	/**
	 * @return the clickguiToggle
	 */
	public double getClickguiToggle() {
		return clickguiToggle;
	}
	
	/**
	 * @param clickguiToggle the clickguiToggle to set
	 */
	public void setClickguiToggle(double clickguiToggle) {
		this.clickguiToggle = clickguiToggle;
	}
	
	/**
	 * @return the arraylistAnimation
	 */
	public double getArraylistAnimation() {
		return arraylistAnimation;
	}
	
	/**
	 * @param arraylistAnimation the arraylistAnimation to set
	 */
	public void setArraylistAnimation(double arraylistAnimation) {
		this.arraylistAnimation = arraylistAnimation;
	}
	
	/**
	 * @return the isClickguiExtended
	 */
	public boolean isClickguiExtended() {
		return isClickguiExtended;
	}

	/**
	 * @param isClickguiExtended the isClickguiExtended to set
	 */
	public void setClickguiExtended(boolean isClickguiExtended) {
		this.isClickguiExtended = isClickguiExtended;
	}
	
	/**
	 * Toggles the module
	 */
	@Override
	public void toggle() {
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.PRE, isEnabled(), isDisabled());
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
		
		enabled = !enabled;
		
		// Calls the onEnable or onDisable method
		if (isEnabled()){
			onEnable();
		}else{
			onDisable();
		}
		
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.POST, isDisabled(), isEnabled());
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
	}
	
	/**
	 * Disables the module, does nothing if the module is already disabled
	 */
	@Override
	public void disable() {
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.PRE, isEnabled(), false);
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
		
		boolean wasEnabled = isEnabled();
		enabled = false;
		if (wasEnabled)
			onDisable();
		
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.POST, isEnabled(), false);
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
	}
	
	/**
	 * Enables the module, does nothing if the module is already enabled
	 */
	@Override
	public void enable() {
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.PRE, isEnabled(), true);
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
		
		boolean wasDisabled = isDisabled();
		enabled = true;
		if (wasDisabled)
			onEnable();
		
		// Kagu hook
		{
			EventModuleStateUpdate eventModuleStateUpdate = new EventModuleStateUpdate(EventPosition.POST, isEnabled(), true);
			eventModuleStateUpdate.post();
			if (eventModuleStateUpdate.isCanceled())
				return;
		}
	}
	
	/**
	 * @author lavaflowglow 
	 * Used to sort modules in the clickgui
	 */
	public static enum Category {
		VISUAL("Visual", 0xff3dc2d9), 
		PLAYER("Player", 0xffd9883d), 
		EXPLOIT("Exploit", 0xfffff34a), 
		GHOST("Ghost", 0xff878787),
		COMBAT("Combat", 0xffe63e3e), 
		MOVEMENT("Movement", 0xff3dd979);

		private String name;
		private int arraylistColor;

		/**
		 * @param name           The display name of the category
		 * @param arraylistColor The color displayed on the arraylist
		 */
		Category(String name, int arraylistColor) {
			
			// We use these two chars when saving and loading files, if the config name contains them it could create issues
			if (name.contains(Kagu.UNIT_SEPARATOR) || name.contains(Kagu.RECORD_SEPARATOR) || name.contains(Kagu.GROUP_SEPARATOR)) {
				logger.error("Name of category (" + name + ") contains a forbidden character, please refrain from using unit, record, and grounp separator characters when naming modules as they break file saving and loading");
				System.exit(0);
				return;
			}
			
			this.name = name;
			this.arraylistColor = arraylistColor;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the arraylistColor
		 */
		public int getArraylistColor() {
			return arraylistColor;
		}
		
		/**
		 * @param name The name of the category
		 * @return The category
		 */
		public static Category getCategoryFromName(String name) {
			for (Category category : Category.values()) {
				if (category.getName().equalsIgnoreCase(name))
					return category;
			}
			return null;
		}
		
	}

}
