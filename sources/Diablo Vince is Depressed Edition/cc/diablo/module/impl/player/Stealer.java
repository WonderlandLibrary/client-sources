/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Stealer
extends Module {
    public NumberSetting delay = new NumberSetting("Delay", 30.0, 1.0, 250.0, 1.0);
    public NumberSetting delayMin = new NumberSetting("Random Delay Min", 1.0, 0.0, 50.0, 1.0);
    public NumberSetting delayMax = new NumberSetting("Random Delay Max", 10.0, 0.0, 50.0, 1.0);
    public BooleanSetting onlyChestGui = new BooleanSetting("Only Chest GUI", true);
    public static BooleanSetting silent = new BooleanSetting("Silent", false);
    private final Stopwatch timer = new Stopwatch();
    private long delaySave = (long)(this.delay.getVal() + (double)MathHelper.getRandInt((int)Math.round(this.delayMin.getVal()), (int)Math.round(this.delayMax.getVal())));
    public static boolean isStealing;

    public Stealer() {
        super("Stealer", "become jewish", 0, Category.Player);
        this.addSettings(this.delay, this.delayMax, this.delayMax, this.onlyChestGui, silent);
    }

    @Override
    public void onEnable() {
        isStealing = false;
        super.onEnable();
    }

    @Subscribe
    public void onTick(UpdateEvent event) {
        if (Stealer.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)Stealer.mc.thePlayer.openContainer;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i) == null || !this.timer.hasReached(this.delaySave)) continue;
                if (this.onlyChestGui.isChecked()) {
                    if (container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Storage")) {
                        if (silent.isChecked()) {
                            isStealing = true;
                        }
                        Stealer.mc.playerController.windowClick(container.windowId, i, 0, 1, Stealer.mc.thePlayer);
                    } else {
                        isStealing = false;
                    }
                } else {
                    if (silent.isChecked()) {
                        isStealing = true;
                    }
                    Stealer.mc.playerController.windowClick(container.windowId, i, 0, 1, Stealer.mc.thePlayer);
                }
                this.delaySave = (long)(this.delay.getVal() + (double)MathHelper.getRandInt((int)Math.round(this.delayMin.getVal()), (int)Math.round(this.delayMax.getVal())));
                this.timer.reset();
            }
            if (this.isContainerEmpty(container)) {
                Stealer.mc.thePlayer.closeScreen();
            }
        } else {
            isStealing = false;
        }
    }

    public boolean isValid(Item item) {
        if (item instanceof ItemSword) {
            return true;
        }
        if (item instanceof ItemAxe) {
            return true;
        }
        if (item instanceof ItemFood) {
            return true;
        }
        if (item instanceof ItemArmor) {
            return true;
        }
        return item instanceof ItemPotion;
    }

    public boolean isContainerEmpty(Container container) {
        int slotAmount;
        boolean temp = true;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            temp = false;
        }
        return temp;
    }

    private EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (!Minecraft.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.UP;
        } else if (!Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.DOWN;
        } else if (!Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.EAST;
        } else if (!Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isFullCube()) {
            direction = EnumFacing.WEST;
        } else if (!Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube()) {
            direction = EnumFacing.SOUTH;
        } else if (!Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube()) {
            direction = EnumFacing.NORTH;
        }
        return direction;
    }
}

