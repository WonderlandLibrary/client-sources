// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Combat;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Criticals extends Mod
{
    public boolean lockview;
    private TimeMeme timer;
    
    public Criticals() {
        super("Criticals", Category.COMBAT);
        this.lockview = false;
    }
    
    @Override
    public void onEnable() {
        (this.timer = new TimeMeme()).reset();
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
        this.timer.reset();
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s§7", "Crits"));
    }
}
