package me.protocol_client.modules;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.MathHelper;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketRecieve;
import events.EventPreMotionUpdates;

public class Day extends Module{

	public Day() {
		super("Day", "day", 0, Category.WORLD, new String[]{"gc"});
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		Wrapper.getWorld().setWorldTime(0);
	}
	@EventTarget
	public void onRecieve(EventPacketRecieve event){
		Packet packet = event.getPacket();
		if(packet instanceof S03PacketTimeUpdate){
			event.setCancelled(true);
		}
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void runCmd(String s){
		try{
			 Wrapper.tellPlayer("Thanks slick");
       	 final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posX);
            final int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posY);
            final int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posZ);
            final StringSelection coords = new StringSelection(String.valueOf(x) + " " + y + " " + z);
            clipboard.setContents(coords, null);
            Wrapper.tellPlayer("\2477Coords copied to cliboard");
            return;
		}catch(Exception e){
			Wrapper.tellPlayer("Invalid Command");
		}
		
	}
}
