// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.movement;

import me.perry.mcdonalds.features.modules.Module;

public class Speed extends Module
{
    public Speed() {
        super("Speed", "Speed.", Category.MOVEMENT, true, true, false);
    }
    
    @Override
    public String getDisplayInfo() {
        return "Strafe";
    }
}
