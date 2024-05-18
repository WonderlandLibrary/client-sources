package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPreMotionUpdates;

public class RemoteView extends Module{

	public RemoteView() {
		super("Remoteview", "remoteview", 0, Category.OTHER, new String[]{"remoteview", "rv", "remote"});
	}
	public static EntityPlayer player = null;
	private EntityOtherPlayerMP fakePlayer = null;
	private double oldX;
	private double oldY;
	private double oldZ;
	
	public void onEnable(){
		EventManager.register(this);
		if(player == null){
			this.onDisable();
			Wrapper.tellPlayer("\2477There was an error trying to remoteview that entity. Please try again.");
		}
		oldX = Minecraft.getMinecraft().thePlayer.posX;
		oldY = Minecraft.getMinecraft().thePlayer.posY;
		oldZ = Minecraft.getMinecraft().thePlayer.posZ;
		fakePlayer = new EntityOtherPlayerMP(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getGameProfile());
		fakePlayer.clonePlayer(Minecraft.getMinecraft().thePlayer, true);
		fakePlayer.copyLocationAndAnglesFrom(Minecraft.getMinecraft().thePlayer);
		fakePlayer.rotationYawHead = Minecraft.getMinecraft().thePlayer.rotationYawHead;
		Minecraft.getMinecraft().theWorld.addEntityToWorld(-69, fakePlayer);
	}
	public void onDisable(){
		EventManager.unregister(this);
		Minecraft.getMinecraft().thePlayer.setPositionAndRotation(oldX, oldY, oldZ, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch);
		Minecraft.getMinecraft().theWorld.removeEntityFromWorld(-69);
		for(Object o : Wrapper.getWorld().loadedEntityList){
			if(o instanceof EntityPlayer){
				EntityPlayer e = (EntityPlayer)o;
				e.setInvisible(false);
			}
		}
		player = null;
		fakePlayer = null;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
	@EventTarget
	public void onPacket(EventPacketSent event){
		Packet packet = event.getPacket();
		if(packet instanceof C03PacketPlayer){
			event.setCancelled(true);
		}
	}
		@EventTarget
		public void onEvent(EventPreMotionUpdates event){
			if(player == null){
				this.onDisable();
				Wrapper.tellPlayer("\2477The selected entity has left the area.");
			}
			for(int i = 0; i < 30; i++){
			Wrapper.getPlayer().setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
			}
			player.setInvisible(true);
		}
		
		public void runCmd(String cmd){
			try{
			if(cmd.equalsIgnoreCase("clear")){
				this.onDisable();
				Wrapper.tellPlayer("\2477RemoteView cleared");
				return;
			}
			if(EntityUtils.getPlayerbyName(cmd) != null){
				player = EntityUtils.getPlayerbyName(cmd);
				this.onEnable();
				Wrapper.tellPlayer("\2477Now following " + Protocol.primColor + cmd);
				return;
			}
			}catch(Exception e){
				Wrapper.invalidCommand("remote view");
				Wrapper.tellPlayer("\2477-" + Protocol.primColor + "RV\2477 <name>");
			}
		}
}
