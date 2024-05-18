package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod
public class SongStealer extends Module {

	public void enable(){
		ClientUtils.sendMessage("too lazy to add this.");
		super.disable();
	}
	
}
