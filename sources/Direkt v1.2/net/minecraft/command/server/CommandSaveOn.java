package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOn extends CommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "save-on";
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.save-on.usage";
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

				if (worldserver.disableLevelSaving) {
					worldserver.disableLevelSaving = false;
					flag = true;
				}
			}
		}

		if (flag) {
			notifyCommandListener(sender, this, "commands.save.enabled", new Object[0]);
		} else {
			throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
		}
	}
}
