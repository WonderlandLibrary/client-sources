package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "save-all";
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.save.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new TextComponentTranslation("commands.save.start", new Object[0]));

		if (server.getPlayerList() != null) {
			server.getPlayerList().saveAllPlayerData();
		}

		try {
			for (WorldServer worldServer2 : server.worldServers) {
				if (worldServer2 != null) {
					WorldServer worldserver = worldServer2;
					boolean flag = worldserver.disableLevelSaving;
					worldserver.disableLevelSaving = false;
					worldserver.saveAllChunks(true, (IProgressUpdate) null);
					worldserver.disableLevelSaving = flag;
				}
			}

			if ((args.length > 0) && "flush".equals(args[0])) {
				sender.addChatMessage(new TextComponentTranslation("commands.save.flushStart", new Object[0]));

				for (WorldServer worldServer : server.worldServers) {
					if (worldServer != null) {
						WorldServer worldserver1 = worldServer;
						boolean flag1 = worldserver1.disableLevelSaving;
						worldserver1.disableLevelSaving = false;
						worldserver1.saveChunkData();
						worldserver1.disableLevelSaving = flag1;
					}
				}

				sender.addChatMessage(new TextComponentTranslation("commands.save.flushEnd", new Object[0]));
			}
		} catch (MinecraftException minecraftexception) {
			notifyCommandListener(sender, this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
			return;
		}

		notifyCommandListener(sender, this, "commands.save.success", new Object[0]);
	}
}
