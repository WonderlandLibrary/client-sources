// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import com.klintos.twelve.utils.PlayerUtils;
import net.minecraft.util.BlockPos;
import com.klintos.twelve.mod.events.EventClickBlock;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Mouse;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueDouble;

public class Speedmine extends Mod
{
    private boolean autoTool;
    private int oldSlot;
    private int newSlot;
    private ValueDouble speed;
    
    public Speedmine() {
        super("Speedmine", 34, ModCategory.WORLD);
        this.addValue(this.speed = new ValueDouble("Speed", 2.0, 0.0, 10.0, 1));
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (this.autoTool) {
            if (Mouse.isButtonDown(0)) {
                Speedmine.mc.thePlayer.inventory.currentItem = this.newSlot;
            }
            else {
                Speedmine.mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.autoTool = false;
            }
            Speedmine.mc.playerController.updateController();
        }
        final double s = Math.abs(this.speed.getValue() - this.speed.getMax()) / 10.0;
        if (Speedmine.mc.playerController.curBlockDamageMP > s) {
            Speedmine.mc.playerController.curBlockDamageMP = 1.0f;
        }
    }
    
    @EventTarget
    public void onClickBlock(final EventClickBlock event) {
        if (Speedmine.mc.playerController.isInCreativeMode()) {
            return;
        }
        if (!this.autoTool) {
            this.oldSlot = Speedmine.mc.thePlayer.inventory.currentItem;
            this.autoTool = true;
        }
        this.newSlot = this.bestTool(event.getPos());
        Speedmine.mc.playerController.blockHitDelay = 0;
    }
    
    public int bestTool(final BlockPos pos) {
        int slot = -1;
        float strength = 1.0f;
        final Block block = PlayerUtils.getBlock(pos);
        for (int index = 0; index < 9; ++index) {
            final ItemStack item = Speedmine.mc.thePlayer.inventory.getStackInSlot(index);
            if (item != null && item.getStrVsBlock(block) > strength) {
                strength = item.getStrVsBlock(block);
                slot = index;
            }
        }
        if (slot != -1) {
            return slot;
        }
        return Speedmine.mc.thePlayer.inventory.currentItem;
    }
}
