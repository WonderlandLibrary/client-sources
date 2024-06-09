package com.kilo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class ClientUtils {
	//this was skidded in like 3 mins for alt manager
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP player() {
        return mc().thePlayer;
    }
    
    public static WorldClient world() {
        mc();
        return Minecraft.theWorld;
    }
    
    
}
