package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.gson.JsonParseException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;

public class CommandMessageRaw extends CommandBase {
	/**
	 * Gets the name of the command
	 */
	@Override
	public String getCommandName() {
		return "tellraw";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.tellraw.usage";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
		} else {
			EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
			String s = buildString(args, 1);

			try {
				ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
				entityplayer.addChatMessage(TextComponentUtils.processComponent(sender, itextcomponent, entityplayer));
			} catch (JsonParseException jsonparseexception) {
				throw toSyntaxException(jsonparseexception);
			}
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String> emptyList();
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
}
