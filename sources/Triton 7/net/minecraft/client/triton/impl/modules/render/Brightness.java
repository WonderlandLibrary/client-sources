// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Mod(shown = false, displayName = "Full-Bright")
public class Brightness extends Module
{
	@EventTarget
	private void onTick(UpdateEvent event){
		ClientUtils.player().addPotionEffect(new PotionEffect(16, 1337, 1));
	}
	
	public void onDisable(){
		ClientUtils.player().removePotionEffect(16);
	}
	
}
