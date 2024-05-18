// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Other;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class FastUse extends Mod
{
    public boolean lockview;
    private TimeMeme timer;
    
    public FastUse() {
        super("FastUse", Category.OTHER);
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
        this.setRenderName(String.format("%s§7", this.getModName()));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (event.state == EventState.PRE && this.mc.thePlayer.onGround && this.mc.thePlayer.getItemInUseDuration() == 15 && !(this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
            for (int i = 0; i < 25; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
