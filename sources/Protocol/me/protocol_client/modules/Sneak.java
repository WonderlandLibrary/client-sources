package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class Sneak extends Module
{
    public Sneak()
    {
        super("Sneak", "sneak", 44, Category.MOVEMENT, new String[]{"sneak"});
    }
    public final Value<Boolean> real = new Value<>("sneak_real", false);
    
    @EventTarget
    public void onEvent(EventPreMotionUpdates pre)
    {
    	if(real.getValue()){
    		Wrapper.mc().gameSettings.keyBindSneak.pressed = true;
    		return;
    	}
      Wrapper.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
      Wrapper.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
    
    @EventTarget
    public void onPost(EventPostMotionUpdates post)
    {
    	if(real.getValue()){
    		this.setDisplayName("Sneak [Real]");
    		Wrapper.getPlayer().setSneaking(true);
    		return;
    	}
    	this.setDisplayName("Sneak [Packet]");
      Wrapper.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
      Wrapper.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    }
    
    public void onEnable() {
        EventManager.register(this);//Registers the Object of this class to the EventManager.
   }

   public void onDisable() {
        EventManager.unregister(this);//Unregisters the Object of this class from the EventManager.
    		Wrapper.mc().gameSettings.keyBindSneak.pressed = false;
        Wrapper.getPlayer().setSneaking(false);
        Wrapper.sendPacket(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
   }
	}
