// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueBoolean;

public class ESP extends Mod
{
    public ValueBoolean players;
    public ValueBoolean mobs;
    
    public ESP() {
        super("ESP", 0, ModCategory.RENDER);
        this.addValue(this.players = new ValueBoolean("Players", true));
        this.addValue(this.mobs = new ValueBoolean("Mobs", true));
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        RendererLivingEntity.updateESPColours();
    }
}
