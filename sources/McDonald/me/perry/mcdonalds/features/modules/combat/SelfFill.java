// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import me.perry.mcdonalds.util.Util;
import me.perry.mcdonalds.features.modules.Module;

public class SelfFill extends Module
{
    public SelfFill() {
        super("SelfFill", "SelfFills yourself in a hole.", Category.COMBAT, true, true, true);
    }
    
    @Override
    public void onEnable() {
        if (Util.mc.player != null) {
            this.disable();
        }
    }
}
