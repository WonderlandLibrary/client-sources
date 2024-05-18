package me.protocol_client.modules;

import java.util.ArrayList;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPrePlayerUpdate;


public class Blink extends Module{

	public Blink() {//TODO: NetworkingManager.java
		super("Blink", "blink", Keyboard.KEY_C, Category.MOVEMENT, new String[]{"dmg", "damage"});
	}
	private static ArrayList<Packet> packets = new ArrayList<Packet>();
	private EntityOtherPlayerMP fakePlayer = null;
	private double oldX;
	private double oldY;
	private double oldZ;
	private static long blinkTime;
	private static long lastTime;
	int time = 0;
	
	@EventTarget
	public void onPacketSent(EventPacketSent event){
		Packet packet = event.getPacket();
		this.addToBlinkQueue(packet);
		event.setCancelled(true);
	}
	
	@Override
	public void onEnable()
	{
		EventManager.register(this);
		lastTime = System.currentTimeMillis();
		
		oldX = Wrapper.getPlayer().posX;
		oldY = Wrapper.getPlayer().posY;
		oldZ = Wrapper.getPlayer().posZ;
		fakePlayer = new EntityOtherPlayerMP(Wrapper.getWorld(), Wrapper.getPlayer().getGameProfile());
		fakePlayer.clonePlayer(Wrapper.getPlayer(), true);
		fakePlayer.copyLocationAndAnglesFrom(Wrapper.getPlayer());
		fakePlayer.rotationYawHead = Wrapper.getPlayer().rotationYawHead;
		Wrapper.getWorld().addEntityToWorld(-69, fakePlayer);
	}
	
	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event) {
		if(isToggled()){
			updateMS();
			if(hasTimePassedM(1000)){
				time = time + 1;
				updateLastMS();
			}
			setDisplayName("Blink \247b[" + time + "s] [" + (blinkTime += System.currentTimeMillis() - lastTime) + "]");
		}else{
			setDisplayName("Blink");
		}
	}
	
	@Override
	public void onDisable()
	{
		EventManager.unregister(this);
		time = 0;
		for(Packet packet : packets)
			Wrapper.getPlayer().sendQueue.addToSendQueue(packet);
		packets.clear();
		Wrapper.getWorld().removeEntityFromWorld(-69);
		fakePlayer = null;
		blinkTime = 0;
	}
	
	public static void addToBlinkQueue(Packet packet)
	{
		if(Wrapper.getPlayer().posX != Wrapper.getPlayer().prevPosX || Wrapper.getPlayer().posZ != Minecraft.getMinecraft().thePlayer.prevPosZ	|| Wrapper.getPlayer().posY != Minecraft.getMinecraft().thePlayer.prevPosY)
		{
			blinkTime += System.currentTimeMillis() - lastTime;
			packets.add(packet);
		}
		lastTime = System.currentTimeMillis();
	}
	
	public void cancel()
	{
		packets.clear();
		Wrapper.getPlayer().setPositionAndRotation(oldX, oldY, oldZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch);
		setToggled(false);
	}
	public void runCmd(String s){
		for (int i = 0; i < 80.0 + 40.0 * (0.5 - 0.5); ++i) {
	            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.049, Minecraft.getMinecraft().thePlayer.posZ, false));
	            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
	        }
	        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));		
	}
}

