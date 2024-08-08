package me.xatzdevelopments.modules.world;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.PlayerUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.BlockPos;

public class ChestStealer extends Module
{
    private double delay;
    private int slot;
    public NumberSetting Delay;
    public BooleanSetting Random;
    public BooleanSetting DetectGui;
    public BooleanSetting AutoClose;
    private BlockPos currentPos;
    
    public ChestStealer() {
        super("ChestStealer", 38, Category.WORLD, null);
        this.delay = 0.0;
        this.slot = 0;
        this.Delay = new NumberSetting("Delay", 5.0, 0.0, 20.0, 1.0);
        this.Random = new BooleanSetting("Random", true);
        this.DetectGui = new BooleanSetting("Detect Gui", true);
        this.AutoClose = new BooleanSetting("Auto Close", true);
        this.addSettings(this.Delay, this.Random, this.DetectGui, this.AutoClose);
    }
    
    @Override
    public void onEnable() {
        this.delay = -10.0;
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            ++this.delay;
        }
        if (e instanceof EventMotion && e.isPre()) {
            if (this.mc.currentScreen instanceof GuiChest) {
                if (PlayerUtils.isHoldingGuiSelector() || (this.DetectGui.isEnabled() && !this.IsChestNearby(9.0f)) || this.mc.thePlayer.ticksExisted < 50) {
                    return;
                }
                final ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
                ++this.slot;
                if (this.slot > chest.getLowerChestInventory().getSizeInventory()) {
                    this.slot = 0;
                }
                if (chest.getLowerChestInventory().getStackInSlot(this.slot) != null && this.delay > this.Delay.getValue()) {
                    this.mc.playerController.windowClick(chest.windowId, this.slot, 0, 1, this.mc.thePlayer);
                    this.delay = 0.0;
                }
                if (this.AutoClose.isEnabled()) {
                    boolean closeContainer = true;
                    for (int i = 0; i < chest.inventorySlots.size(); ++i) {
                        if (chest.getLowerChestInventory().getStackInSlot(i) != null) {
                            closeContainer = false;
                        }
                    }
                    if (closeContainer) {
                        this.mc.thePlayer.closeScreen();
                    }
                }
            }
            else {
                this.slot = (this.Random.isEnabled() ? ((int)(Math.random() * 8.0)) : 0);
            }
        }
    }
    
    final boolean IsChestNearby(final float range) {
        BlockPos pos = null;
        pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        this.isInteracting = false;
        for (double i = -range; i <= range; ++i) {
            for (double j = -range; j <= range; ++j) {
                for (double k = -range; k <= range; ++k) {
                    if (this.mc.theWorld.getBlockState(pos.add(k, i, j)).getBlock() == Blocks.chest) {
                        this.currentPos = pos.add(k, i, j);
                        pos = new BlockPos(this.mc.thePlayer.posX + k, this.mc.thePlayer.posY + i, this.mc.thePlayer.posZ + j);
                        if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockChest && this.mc.thePlayer.getDistance(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ()) < range) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
