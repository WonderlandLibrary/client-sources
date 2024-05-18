// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.klintos.twelve.mod.events.EventPostUpdate;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class AntiHunger extends Mod
{
    private boolean onGround;
    
    public AntiHunger() {
        super("AntiHunger", 0, ModCategory.EXPLOITS);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        this.onGround = AntiHunger.mc.thePlayer.onGround;
        if (AntiHunger.mc.thePlayer.posY == AntiHunger.mc.thePlayer.prevPosY) {
            AntiHunger.mc.thePlayer.onGround = false;
        }
    }
    
    @EventTarget
    public void onPostUpdate(final EventPostUpdate event) {
        AntiHunger.mc.thePlayer.onGround = this.onGround;
    }
}
