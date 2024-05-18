package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Step extends Module
{
    public Step()
    {
        super("Step", "step", Keyboard.KEY_L, Category.PLAYER, new String[]{"step"});
    }
    public final Value<Boolean> latest = new Value<>("step_bypass", false);
    public final ClampedValue<Float> height = new ClampedValue<>("step_height", 1f, 1f, 10f);
    public void onEnable(){
    	EventManager.register(this);
    }
    public void onDisable(){
    	EventManager.unregister(this);
    	Wrapper.getPlayer().stepHeight = 0.5f;
    }
    @EventTarget
    public void onEvent(EventPreMotionUpdates event)
    {
        	if(latest.getValue())
        	{
        		setDisplayName("Step [Latest]");
   			mc.thePlayer.stepHeight = 1F;
////        		if(mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround)
////        		{
////        			Wrapper.getPlayer().stepHeight = 1.0f;
////        			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.42D, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
////        		    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.75D, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
////        		}else{
////        			}
        	}else{
        		setDisplayName("Step [Vanilla]");
            Minecraft.getMinecraft().thePlayer.stepHeight = height.getValue();
        }
    }
}
