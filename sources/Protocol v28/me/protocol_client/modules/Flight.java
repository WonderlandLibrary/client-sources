package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;
public class Flight extends Module
{
	public int ticks = 0;
	public float speed = 0.7f;
	double y;
	public final Value<Boolean> latest = new Value<>("flight_damage", false);
	
	public final ClampedValue<Float>	glidespeed	= new ClampedValue<>("flight_glide_speed", 0f, 0f, 30f);
    public Flight()
    {
        super("Flight", "flight", 33, Category.PLAYER, new String[]{"flight"});
    }
    
    
    public void onEnable()
	{
    	EventManager.register(this);
    	if(latest.getValue()){
    		for (int i = 0; i < 80.0 + 40.0 * (0.5 - 0.5); ++i) {
    	            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.049, Minecraft.getMinecraft().thePlayer.posZ, false));
    	            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
    	        }
    	        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
    	        Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.1, Wrapper.getPlayer().posZ);
    	}
	}
    public void onDisable()
	{
    	EventManager.unregister(this);
    	Wrapper.getPlayer().capabilities.isFlying = false;
    	
	}
    @EventTarget
    public void onEvent(EventPreMotionUpdates event)
    {
    	float speed = this.glidespeed.getValue() / 100;
    	if(this.glidespeed.getValue() < 1.5){
    		speed = 0;
    	}
        		if(Wrapper.mc().gameSettings.keyBindJump.pressed){
        			Wrapper.getPlayer().motionY = speed;
        		}else{
        			Wrapper.getPlayer().motionY = -speed;
        		}
    }
    public void runCmd(String s){
    	if(s.equalsIgnoreCase("latest")){
    		this.latest.setValue(true);
    		Wrapper.tellPlayer(Protocol.primColor + "Fly §7mode set to latest");
    		return;
    	}
    	if(s.equalsIgnoreCase("normal")){
    		this.latest.setValue(false);
    		Wrapper.tellPlayer(Protocol.primColor + "Fly §7mode set to normal");
    		return;
    	}
    	Wrapper.invalidCommand("Flight");
    	Wrapper.tellPlayer("\2477-" + Protocol.primColor + "fly\2477 <mode> <latest/normal>");
    }
}
    
