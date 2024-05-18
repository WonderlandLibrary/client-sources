// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;

public class ChestESP extends Mod
{
    public ChestESP() {
        super("ChestESP", 0, ModCategory.RENDER);
    }
    
    @Override
    public void onEnable() {
        TileEntityChestRenderer.a = 255.0f;
        TileEntityChestRenderer.r = 255.0f;
        TileEntityChestRenderer.g = 0.0f;
        TileEntityChestRenderer.b = 0.0f;
        RendererLivingEntity.state = 0;
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        TileEntityChestRenderer.updateESPColours();
    }
}
