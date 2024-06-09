package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventBoundingBox;
import events.EventPacketRecieve;
import events.EventPostMotionUpdates;
import events.EventPrePlayerUpdate;
import events.EventPushOutOfBlock;
import events.EventTick;

public class Phaseer extends Module {

	public Phaseer() {
		super("Phase", "phase", Keyboard.KEY_G, Category.MOVEMENT, new String[] { "" });
		setDisplayName("Phase [Latest]");
	}

	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(Wrapper.getPlayer().boundingBox.minX); x < MathHelper.floor_double(Wrapper.getPlayer().boundingBox.maxX) + 1; x++) {
			for (int y = MathHelper.floor_double(Wrapper.getPlayer().boundingBox.minY); y < MathHelper.floor_double(Wrapper.getPlayer().boundingBox.maxY) + 1; y++) {
				for (int z = MathHelper.floor_double(Wrapper.getPlayer().boundingBox.minZ); z < MathHelper.floor_double(Wrapper.getPlayer().boundingBox.maxZ) + 1; z++) {
					Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					AxisAlignedBB boundingBox;
					if ((block != null) && (!(block instanceof BlockAir)) && ((boundingBox = block.getCollisionBoundingBox(Wrapper.getWorld(), new BlockPos(x, y, z), Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)))) != null) && (Wrapper.getPlayer().boundingBox.intersectsWith(boundingBox))) {
						AxisAlignedBB boundingBox1;
						return true;
					}
				}
			}
		}
		return false;
	}
	@EventTarget
	public void onPost(EventPostMotionUpdates event) {
		double multiplier = 0.2D;
		double mx = Math.cos(Math.toRadians(Wrapper.getPlayer().rotationYaw + 90.0F));
		double mz = Math.sin(Math.toRadians(Wrapper.getPlayer().rotationYaw + 90.0F));
		double x = Wrapper.getPlayer().movementInput.moveForward * multiplier * mx + Wrapper.getPlayer().movementInput.moveStrafe * multiplier * mz;
		double z = Wrapper.getPlayer().movementInput.moveForward * multiplier * mz - Wrapper.getPlayer().movementInput.moveStrafe * multiplier * mx;
		multiplier = 0.2D;
		double xoffset = Wrapper.getPlayer().movementInput.moveForward * multiplier * mx + Wrapper.getPlayer().movementInput.moveStrafe * multiplier * mz;
		double zoffset = Wrapper.getPlayer().movementInput.moveForward * multiplier * mz - Wrapper.getPlayer().movementInput.moveStrafe * multiplier * mx;
		if ((Wrapper.getPlayer().isCollidedHorizontally) && (!Wrapper.getPlayer().isOnLadder()) && (!isInsideBlock())) {

			double[] startPos = { Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ };
			BlockPos endPos = new BlockPos(Wrapper.getPlayer().posX + xoffset, Wrapper.getPlayer().posY - 1.0D, Wrapper.getPlayer().posZ + zoffset);

			Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX + xoffset, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + zoffset, false));
			Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - (Protocol.jesus.isOnLiquid() ? 9000.0D : 0.09D), Wrapper.getPlayer().posZ, false));
			Wrapper.blinkToPos(startPos, endPos, 0.0D);
			Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX + xoffset, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + zoffset);
		}
	}

	@EventTarget
	public void onBB(EventBoundingBox bb) {
		if (Wrapper.getPlayer() == null) {
			return;
		}
		Wrapper.getPlayer().noClip = true;
		if (bb.getY() > Wrapper.getPlayer().posY + (isInsideBlock() ? 0 : 1)) {
			bb.setBoundingBox(null);
		}
		if ((Wrapper.getPlayer().isCollidedHorizontally) && (bb.getY() > Wrapper.getPlayer().boundingBox.minY - 0.4D)) {
			bb.setBoundingBox(null);
		}
	}

	@EventTarget
	public void onPush(EventPushOutOfBlock push) {
		mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
		push.setCancelled(true);
	}

	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event) {
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}
}
