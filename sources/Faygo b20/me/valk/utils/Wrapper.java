package me.valk.utils;

import java.util.ArrayList;
import java.util.List;

import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.help.entity.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.util.MovementInput;

public class Wrapper {

	public static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public static Player getPlayer() {
		return getMinecraft().thePlayer;
	}

	public static WorldClient getWorld() {
		return getMinecraft().theWorld;
	}

	public static void packet(Packet packet) {
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
	}

	public static FontRenderer getFontRenderer() {
		return getMinecraft().fontRendererObj;
	}

	public static EntityPlayerSP player() {
		return Minecraft.getMinecraft().thePlayer;
	}

	public static List<Entity> loadedEntityList() {
		final List<Entity> loadedList = new ArrayList<Entity>(getWorld().loadedEntityList);
		loadedList.remove(player());
		return loadedList;
	}

	public static PlayerControllerMP playerController() {
		return Minecraft.getMinecraft().playerController;
	}

	public static void setMoveSpeed(final EventPlayerUpdate event, final double speed) {
		double forward = movementInput().moveForward;
		double strafe = movementInput().moveStrafe;
		float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			event.setX(0.0);
			event.setZ(0.0);
		} else {
			if (forward != 0.0) {
				if (strafe > 0.0) {
					yaw += ((forward > 0.0) ? -45 : 45);
				} else if (strafe < 0.0) {
					yaw += ((forward > 0.0) ? 45 : -45);
				}
				strafe = 0.0;
				if (forward > 0.0) {
					forward = 1.0;
				} else if (forward < 0.0) {
					forward = -1.0;
				}
			}
			event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
			event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
		}

	}

	public static MovementInput movementInput() {
		return Minecraft.getMinecraft().thePlayer.movementInput;
	}

}