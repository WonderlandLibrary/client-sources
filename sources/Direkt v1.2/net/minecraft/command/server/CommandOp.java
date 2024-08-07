package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandOp extends CommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "op";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.op.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if ((args.length == 1) && (args[0].length() > 0)) {
			GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(args[0]);

			if (gameprofile == null) {
				throw new CommandException("commands.op.failed", new Object[] { args[0] });
			} else {
				server.getPlayerList().addOp(gameprofile);
				notifyCommandListener(sender, this, "commands.op.success", new Object[] { args[0] });
			}
		} else {
			throw new WrongUsageException("commands.op.usage", new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if (args.length == 1) {
			String s = args[args.length - 1];
			List<String> list = Lists.<String> newArrayList();

			for (GameProfile gameprofile : server.getGameProfiles()) {
				if (!server.getPlayerList().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName())) {
					list.add(gameprofile.getName());
				}
			}

			return list;
		} else {
			return Collections.<String> emptyList();
		}
	}
}
