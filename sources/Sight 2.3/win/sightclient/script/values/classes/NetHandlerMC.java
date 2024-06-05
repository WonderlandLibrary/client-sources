package win.sightclient.script.values.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import win.sightclient.script.values.ClassMC;
import win.sightclient.script.values.ScriptMC;

public class NetHandlerMC extends ClassMC {

	public void sendC03(boolean onGround) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(onGround));
	}
	
	public void sendC04(double posX, double posY, double posZ, boolean isOnGround) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, isOnGround));
	}
	
	public void sendC04(double posX, double posY, double posZ) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, ScriptMC.instance.thePlayer.onGround));
	}
	
	public void sendC05(float playerYaw, float playerPitch, boolean isOnGround) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(playerYaw, playerPitch, isOnGround));
	}
	
	public void sendC06(double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(playerX, playerY, playerZ, playerYaw, playerPitch, playerIsOnGround));
	}
	
	public void sendC08() {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem()));
	}
	
	public void sendC07(double x, double y, double z) {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(x, y, z), EnumFacing.DOWN));
	}
}
