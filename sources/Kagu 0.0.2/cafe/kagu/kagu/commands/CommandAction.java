/**
 * 
 */
package cafe.kagu.kagu.commands;

/**
 * @author lavaflowglow
 *
 */
public interface CommandAction {
	
	/**
	 * Called when the command is ran and the args fit the reqirements for this command
	 * @param extraArgs Any extra arguments
	 * @return true if the command manager should stop running other command actions, false if it should continue
	 */
	public boolean execute(String... extraArgs);
	
}
