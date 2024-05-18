package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "save-off";
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.save-off.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		boolean flag = false;

		for (WorldServer worldServer2 : server.worldServers) {
			if (worldServer2 != null) {
				WorldServer worldserver = worldServer2;

				if (!worldserver.disableLevelSaving) {
					worldserver.disableLevelSaving = true;
					flag = true;
				}
			}
		}

		if (flag) {
			notifyCommandListener(sender, this, "commands.save.disabled", new Object[0]);
		} else {
			throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
		}
	}
}
