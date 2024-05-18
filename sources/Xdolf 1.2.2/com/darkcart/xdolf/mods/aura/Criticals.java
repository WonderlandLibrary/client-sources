package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class Criticals extends Module {
	
	public Criticals() {
		super("Criticals", "Deal critical damage everytime you hit.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.COMBAT);
	}
	
	public static void doCrit() {
		if(!Wrapper.getPlayer().isInWater() && !Wrapper.getPlayer().isInsideOfMaterial(Material.LAVA) && Wrapper.getPlayer().onGround) {
			double posX = Wrapper.getPlayer().posX;
			double posY = Wrapper.getPlayer().posY;
			double posZ = Wrapper.getPlayer().posZ;
					
			Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(posX, posY + 0.0625D, posZ, true));
			Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(posX, posY, posZ, false));
			Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(posX, posY + 1.1E-5D, posZ, false));
			Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(posX, posY, posZ, false));
		}
	}
}
