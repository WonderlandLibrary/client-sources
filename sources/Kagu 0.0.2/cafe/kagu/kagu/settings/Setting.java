/**
 * 
 */
package cafe.kagu.kagu.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;

/**
 * @author lavaflowglow
 *
 */
public abstract class Setting<T> {

	/**
	 * @param name The name of the setting
	 */
	public Setting(String name) {
		
		// We use these two chars when saving and loading files, if the config name contains them it could create issues
		if (name.contains(Kagu.UNIT_SEPARATOR) || name.contains(Kagu.RECORD_SEPARATOR) || name.contains(Kagu.GROUP_SEPARATOR)) {
			logger.error("Name of setting (" + name + ") contains a forbidden character, please refrain from using the unit and record separator character when naming modules as they break file loading");
			System.exit(0);
			return;
		}
		
		this.name = name;
	}

	private String name;
	private SettingDependency dependency;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		
		// We use these two chars when saving and loading files, if the config name contains them it could create issues
		if (name.contains(Kagu.UNIT_SEPARATOR) || name.contains(Kagu.RECORD_SEPARATOR) || name.contains(Kagu.GROUP_SEPARATOR)) {
			logger.error("Name of setting (" + name + ") contains a forbidden character, please refrain from using the unit and record separator character when naming modules as they break file loading");
			System.exit(0);
			return;
		}
		
		this.name = name;
	}
	
	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return dependency == null ? false : !dependency.check();
	} 
	
	/**
	 * @return the dependency
	 */
	public SettingDependency getDependency() {
		return dependency;
	}

	/**
	 * @param dependency the dependency to set
	 */
	public T setDependency(SettingDependency dependency) {
		this.dependency = dependency;
		return (T) this;
	}
	
	/**
	 * Casts the setting for you
	 * @param <U> The type of the setting
	 * @param settingClass The class of the setting
	 * @return The setting casted
	 */
	@SuppressWarnings("unchecked")
	public <U extends Setting<?>> U get(Class<U> settingClass) {
		return (U)this;
	}
	
}
