// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.util.MovementInput;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Sprint extends Mod
{
    private Object omniSprint;
    
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        this.setBind(45);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        if (MovementInput.moveForward == 0.0f) {
            final MovementInput movementInput2 = this.mc.thePlayer.movementInput;
            if (MovementInput.moveStrafe == 0.0f) {
                return;
            }
        }
        if (!this.mc.thePlayer.isSneaking() && !this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
}
