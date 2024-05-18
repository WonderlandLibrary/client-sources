// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EnumPlayerModelParts;
import com.klintos.twelve.mod.events.EventPreUpdate;
import java.util.Random;

public class SkinDerp extends Mod
{
    private Random random;
    
    public SkinDerp() {
        super("SkinDerp", 0, ModCategory.MISC);
        this.random = new Random();
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.HAT, this.random.nextBoolean());
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.JACKET, this.random.nextBoolean());
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.LEFT_PANTS_LEG, this.random.nextBoolean());
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.RIGHT_PANTS_LEG, this.random.nextBoolean());
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.LEFT_SLEEVE, this.random.nextBoolean());
        SkinDerp.mc.gameSettings.func_178878_a(EnumPlayerModelParts.RIGHT_SLEEVE, this.random.nextBoolean());
    }
}
