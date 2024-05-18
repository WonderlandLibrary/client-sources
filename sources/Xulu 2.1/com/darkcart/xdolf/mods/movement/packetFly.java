package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class packetFly extends Module {
	
	public packetFly() {
		super("packetFly", "NoCheat+", "Sends position packets to the server which forces you to go up/forward. Bypasses NCP Latest", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			//double[] dir; I didn't get this shit to work, broke it
            if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()) {
                Wrapper.getPlayer().motionX = player.posX * 0.0f;
                Wrapper.getPlayer().motionZ = player.posZ * 0.0f;
            }
            Wrapper.getPlayer().motionY = 0;
           player.noClip = true;
            Wrapper.sendPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX + Wrapper.getPlayer().motionX, Wrapper.getPlayer().posY + (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() ? 0.0622 : 0) - (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown() ? 0.0622 : 0), Wrapper.getPlayer().posZ + Wrapper.getPlayer().motionZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, false));
            Wrapper.sendPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX + Wrapper.getPlayer().motionX, Wrapper.getPlayer().posY - 42069, Wrapper.getPlayer().posZ + Wrapper.getPlayer().motionZ, Wrapper.getPlayer().rotationYaw , Wrapper.getPlayer().rotationPitch, true));
	}}
	
	@Override
	public void onEnable() {
	}
}
