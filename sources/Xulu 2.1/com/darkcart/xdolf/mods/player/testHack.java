package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class testHack extends Module {
	
	private boolean jump;
	  private boolean speedTick;
	  private float forward = 0.3F;
	
	public testHack() {
		super("testHack", "Dev", "Just a hack i use for testing.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 1.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 1.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 1.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 2.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 2.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 2.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 3.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 3.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 3.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 4.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 4.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 4.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 5.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 5.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 5.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 6.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 6.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 6.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 7.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 7.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 7.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 8.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 8.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 8.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 9.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 9.419D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 9.753D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
		    Wrapper.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 10.0D, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
}}
	
	@Override
	public void onDisable() {
	}
}
