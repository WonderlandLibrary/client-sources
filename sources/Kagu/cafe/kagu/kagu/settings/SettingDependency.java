/**
 * 
 */
package cafe.kagu.kagu.settings;

/**
 * @author lavaflowglow
 *
 */
public interface SettingDependency {
	
	/**
	 * @return true if the setting should be shown, otherwise false
	 */
	public boolean check();
	
}
