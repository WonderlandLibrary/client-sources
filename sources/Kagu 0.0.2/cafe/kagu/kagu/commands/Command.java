/**
 * 
 */
package cafe.kagu.kagu.commands;

import java.util.Map;

/**
 * @author lavaflowglow
 *
 */
public abstract class Command {

	/**
	 * @param name               The name of the command
	 * @param usage              How to use the command
	 * @param actionRequirements All the action requirements
	 */
	public Command(String name, String usage, ActionRequirement... actionRequirements) {
		this.name = name;
		this.usage = usage;
		this.actionRequirements = actionRequirements;
	}

	private String name, usage;
	private ActionRequirement[] actionRequirements;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * @return the actionRequirements
	 */
	public ActionRequirement[] getActionRequirements() {
		return actionRequirements;
	}

	public static class ActionRequirement {

		/**
		 * @param commandAction The command action
		 * @param requiredArgs  The required arguments
		 */
		public ActionRequirement(CommandAction commandAction, String... requiredArgs) {
			this.commandAction = commandAction;
			this.requiredArgs = requiredArgs;
		}

		private CommandAction commandAction;
		private String[] requiredArgs;

		/**
		 * @return the requiredArgs
		 */
		public String[] getRequiredArgs() {
			return requiredArgs;
		}

		/**
		 * @return the commandAction
		 */
		public CommandAction getCommandAction() {
			return commandAction;
		}

	}

}
