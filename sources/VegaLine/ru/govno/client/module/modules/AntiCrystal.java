/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.BlockHelper;
import ru.govno.client.utils.CrystalField;
import ru.govno.client.utils.EntityHelper;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.TimerHelper;

public class AntiCrystal
extends Module {
    private final TimerHelper timer = new TimerHelper();
    private Settings Range = new Settings("Range", 4.5f, 5.0f, 2.0f, this);
    private Settings PlaceDelay;
    private Settings IgnoreWalls;
    private Settings UseInventory;

    public AntiCrystal() {
        super("AntiCrystal", 0, Module.Category.COMBAT);
        this.settings.add(this.Range);
        this.PlaceDelay = new Settings("Delay", 100.0f, 500.0f, 50.0f, this);
        this.settings.add(this.PlaceDelay);
        this.IgnoreWalls = new Settings("IgnoreWalls", true, (Module)this);
        this.settings.add(this.IgnoreWalls);
        this.UseInventory = new Settings("UseInventory", false, (Module)this);
        this.settings.add(this.UseInventory);
    }

    private boolean stackIsBlock(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemBlock;
    }

    private int getBlockSlot(boolean invUse) {
        for (int i = 0; i < (invUse ? 44 : 8); ++i) {
            if (!this.stackIsBlock(Minecraft.player.inventory.getStackInSlot(i))) continue;
            return i;
        }
        return -1;
    }

    private boolean isValidBlockPos(BlockPos pos) {
        block2: {
            block3: {
                IBlockState state = AntiCrystal.mc.world.getBlockState(pos);
                Block block = state.getBlock();
                if (!((double)pos.getY() < Minecraft.player.posY + 1.0) || block != Blocks.OBSIDIAN && block != Blocks.BEDROCK || !BlockUtils.canPlaceBlock(pos.up())) break block2;
                if (this.IgnoreWalls.bValue) break block3;
                if (!BlockUtils.canPosBeSeenCoord(Minecraft.player.getPositionEyes(1.0f), (double)pos.getY() + 0.5, (double)pos.getY() + 0.75, (double)pos.getZ() + 0.5)) break block2;
            }
            return pos != CrystalField.forCrystalPos && pos != CrystalField.forObsidianPos && (CrystalField.crystal == null || pos != BlockUtils.getEntityBlockPos(CrystalField.crystal).down());
        }
        return false;
    }

    private void rClickPos(BlockPos pos, EnumHand hand) {
        AntiCrystal.mc.playerController.processRightClickBlock(Minecraft.player, AntiCrystal.mc.world, pos, EnumFacing.UP, new Vec3d(pos), hand);
        Minecraft.player.swingArm(hand);
    }

    private void switchForActions(int slotTo, Runnable action) {
        boolean invSwap;
        if (slotTo <= -1) {
            if (slotTo == -2) {
                action.run();
            }
            return;
        }
        boolean bl = invSwap = slotTo > 8;
        if (invSwap) {
            AntiCrystal.mc.playerController.windowClick(0, slotTo, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
            AntiCrystal.mc.playerController.syncCurrentPlayItem();
            action.run();
            if (this.PlaceDelay.fValue >= 100.0f) {
                AntiCrystal.mc.playerController.windowClickMemory(0, slotTo, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player, 100);
            } else {
                AntiCrystal.mc.playerController.windowClick(0, slotTo, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
            }
            return;
        }
        int handSlot = Minecraft.player.inventory.currentItem;
        Minecraft.player.inventory.currentItem = slotTo;
        AntiCrystal.mc.playerController.syncCurrentPlayItem();
        action.run();
        Minecraft.player.inventory.currentItem = handSlot;
        AntiCrystal.mc.playerController.syncCurrentPlayItem();
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByDouble(this.Range.fValue);
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate event) {
        if (!this.timer.hasReached(this.PlaceDelay.fValue)) {
            return;
        }
        int blockSlot = -999;
        if (this.stackIsBlock(Minecraft.player.getHeldItemOffhand())) {
            blockSlot = -2;
        }
        if ((blockSlot = this.getBlockSlot(this.UseInventory.bValue)) == -1 || blockSlot == -999) {
            return;
        }
        BlockPos pos = BlockHelper.getSphere(BlockHelper.getPlayerPos(), this.Range.fValue, 6, false, true).stream().filter(this::isValidBlockPos).min(Comparator.comparing(blockPos -> EntityHelper.getDistanceOfEntityToBlock(Minecraft.player, blockPos))).orElse(null);
        if (pos == null) {
            return;
        }
        int copyBlockSlot = blockSlot;
        this.switchForActions(blockSlot, () -> {
            this.rClickPos(pos, copyBlockSlot == -2 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            this.timer.reset();
        });
    }
}

