package me.protocol_client.modules;


import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Fullbright extends Module
{
    public Fullbright()
    {
        super("Bright", "bright", 48, Category.RENDER, new String[]{"fullbright", "bright"});
    }
    
    @EventTarget
    public void onEvent(EventPreMotionUpdates event)
    { 
    	setDisplayName("Bright");
    		Wrapper.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 16340, 2));
    }
    public void onDisable()
    {
    	EventManager.unregister(this);
    	Wrapper.getPlayer().removePotionEffect(Potion.nightVision.getId());
    }
    public void onEnable()
    {
    	EventManager.register(this);
    	Wrapper.mc().gameSettings.gammaSetting = 1500;
    }
}
