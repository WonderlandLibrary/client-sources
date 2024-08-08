// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.client;

import me.perry.mcdonalds.Discord;
import me.perry.mcdonalds.features.modules.Module;

public class RPC extends Module
{
    public static RPC INSTANCE;
    
    public RPC() {
        super("RPC", "Discord rich presence", Category.CLIENT, false, false, false);
        RPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Discord.start();
    }
    
    @Override
    public void onDisable() {
        Discord.stop();
    }
    
    public void onClientDisconnect() {
        this.disable();
    }
    
    public void onClientStartup() {
        this.enable();
    }
}
