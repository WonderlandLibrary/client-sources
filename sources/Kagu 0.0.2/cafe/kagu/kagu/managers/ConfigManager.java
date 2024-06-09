/**
 * 
 */
package cafe.kagu.kagu.managers;

import java.io.File;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.Module.Category;
import cafe.kagu.kagu.mods.impl.visual.ModClickGui;
import cafe.kagu.kagu.settings.Setting;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ColorSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.KeybindSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.settings.impl.SlotSetting;
import cafe.kagu.kagu.utils.MiscUtils;

/**
 * @author lavaflowglow
 *
 */
public class ConfigManager {
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Saves the config to a file
	 * @param saveFile The save file for the config
	 */
	public static void save(File saveFile) {
		
		// Serialize all the modules and settings in a format that our client can read
		String config = "";
		for (Module module : Kagu.getModuleManager().getModules()) {
			config += (config.isEmpty() ? "" : Kagu.UNIT_SEPARATOR) + module.getName() + Kagu.GROUP_SEPARATOR
				+ module.getCategory() + Kagu.GROUP_SEPARATOR
				+ module.isEnabled();
			for (Setting<?> setting : module.getSettings()) {
				
				// Get the setting type
				String settingType = MiscUtils.getSettingType(setting);
				
				// Get the setting value
				String settingValue = "";
				if (setting instanceof BooleanSetting) {
					settingValue = ((BooleanSetting)setting).isEnabled() + "";
				}
				else if (setting instanceof DoubleSetting) {
					settingValue = ((DoubleSetting)setting).getValue() + "";
				}
				else if (setting instanceof IntegerSetting) {
					settingValue = ((IntegerSetting)setting).getValue() + "";
				}
				else if (setting instanceof ModeSetting) {
					settingValue = ((ModeSetting)setting).getMode();
				}
				else if (setting instanceof KeybindSetting) {
					settingValue = ((KeybindSetting)setting).getKeybind() + "";
				}
				else if (setting instanceof SlotSetting) {
					settingValue = ((SlotSetting)setting).getSelectedSlot() + "";
				}
				else if (setting instanceof ColorSetting) {
					settingValue = Integer.toHexString(setting.get(ColorSetting.class).getColor());
				}
				else {
					settingValue = "error";
				}
				
				config += Kagu.GROUP_SEPARATOR + setting.getName()
						+ Kagu.RECORD_SEPARATOR + " " // Hidden fields were removed, we put this nothing value here for backwards compatibility
						+ Kagu.RECORD_SEPARATOR + settingType
						+ Kagu.RECORD_SEPARATOR + settingValue;
				
			}
		}
		
		// Save the config to a file
		FileManager.writeStringToFile(saveFile, config);
		
	}
	
	/**
	 * Loads configs from a file
	 * @param file The file to load the config from
	 */
	public static void load(File file) {
		
		// Load the file
		String config = FileManager.readStringFromFile(file);
		
		{ // Disable all modules, this way if the config doesn't contain the module it will just be disabled
			Collection<Module> modules = Kagu.getModuleManager().getModules();
			for (Module module : modules)
				module.disable();
		}
		
		// Load config
		String[] modulesString = config.split(Kagu.UNIT_SEPARATOR);
		for (String moduleString : modulesString) {
			
			String[] moduleSplit = moduleString.split(Kagu.GROUP_SEPARATOR);
			
			// Name and category
			String name = moduleSplit[0];
			Category category = Category.getCategoryFromName(moduleSplit[1]);
			if (category == null)
				continue; // Category doesn't exist and wasn't found
			boolean enabled = moduleSplit[2].equals("true");
			
			// Don't override clickgui settings
			if (Kagu.getModuleManager().getModule(ModClickGui.class).getName().equals(name) 
					&& Kagu.getModuleManager().getModule(ModClickGui.class).getCategory() == category)
				continue;
			
			// Find the module
			for (Module module : Kagu.getModuleManager().getModules()) {
				if (!(module.getCategory() == category && module.getName().equals(name)))
					continue;
				
				// Load settings
				if (moduleSplit.length > 3) for (int settingsIndex = 3; settingsIndex < moduleSplit.length; settingsIndex++) {
					String settingString = moduleSplit[settingsIndex];
					String[] settingSplit = settingString.split(Kagu.RECORD_SEPARATOR);
					
					// Load the setting info
					String settingName = settingSplit[0];
//					String settingHidden = settingSplit[1]; Removed
					String settingType = settingSplit[2];
					String settingValue = settingSplit[3];
					
					// Find setting and load the value
					for (Setting<?> setting : module.getSettings()) {
						if (!(setting.getName().equals(settingName) && MiscUtils.getSettingType(setting).equals(settingType)))
							continue;
						
						try {
							switch (settingType) {
								case "bool":{
									if (settingValue.equals("true")) {
										((BooleanSetting)setting).enable();
									}else {
										((BooleanSetting)setting).disable();
									}
								}break;
								
								case "dec":{
									((DoubleSetting)setting).setValue(Double.parseDouble(settingValue));
								}break;
								
								case "int":{
									((IntegerSetting)setting).setValue(Integer.parseInt(settingValue));
								}break;
								
								case "mode":{
									((ModeSetting)setting).setMode(settingValue);
								}break;
								
								case "bind":{
									((KeybindSetting)setting).setKeybind(Integer.parseInt(settingValue));
								}break;
								
								case "slot":{
									((SlotSetting)setting).setSelectedSlot(Integer.parseInt(settingValue));
								}break;
								
								case "color":{
									setting.get(ColorSetting.class).setColor((int)Long.parseLong(settingValue, 16));
								}break;
								
								default:
									break;
								}
						} catch (ClassCastException e) {
							logger.error("Setting was not of expected type \"" + settingType + "\"");
							continue;
						}
						
					}
					
				}
				
				// Set the module to the required state
				if (enabled) {
					module.enable();
				}else {
					module.disable();
				}
				
			}
			
		}
		
	}
	
}
