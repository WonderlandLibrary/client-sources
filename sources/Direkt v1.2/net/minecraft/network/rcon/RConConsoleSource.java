package net.minecraft.network.rcon;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class RConConsoleSource implements ICommandSender {
	/** RCon string buffer for log. */
	private final StringBuffer buffer = new StringBuffer();
	private final MinecraftServer server;

	public RConConsoleSource(MinecraftServer serverIn) {
		this.server = serverIn;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return "Rcon";
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(this.getName());
	}

	/**
	 * Send a chat message to the CommandSender
	 */
	@Override
	public void addChatMessage(ITextComponent component) {
		this.buffer.append(component.getUnformattedText());
	}

	/**
	 * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
	 */
	@Override
	public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
		return true;
	}

	/**
	 * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return the coordinates 0, 0, 0
	 */
	@Override
	public BlockPos getPosition() {
		return BlockPos.ORIGIN;
	}

	/**
	 * Get the position vector. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return 0.0D, 0.0D, 0.0D
	 */
	@Override
	public Vec3d getPositionVector() {
		return Vec3d.ZERO;
	}

	/**
	 * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return the overworld
	 */
	@Override
	public World getEntityWorld() {
		return this.server.getEntityWorld();
	}

	/**
	 * Returns the entity associated with the command sender. MAY BE NULL!
	 */
	@Override
	public Entity getCommandSenderEntity() {
		return null;
	}

	/**
	 * Returns true if the command sender should be sent feedback about executed commands
	 */
	@Override
	public boolean sendCommandFeedback() {
		return true;
	}

	@Override
	public void setCommandStat(CommandResultStats.Type type, int amount) {
	}

	/**
	 * Get the Minecraft server instance
	 */
	@Override
	public MinecraftServer getServer() {
		return this.server;
	}
}
