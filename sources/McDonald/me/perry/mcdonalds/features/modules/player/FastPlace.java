// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.item.ItemExpBottle;
import me.perry.mcdonalds.features.modules.Module;

public class FastPlace extends Module
{
    public FastPlace() {
        super("FastExp", "Fast everything.", Category.PLAYER, true, false, false);
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class)) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
    }
}
