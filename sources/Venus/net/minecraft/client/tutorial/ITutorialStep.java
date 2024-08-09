/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.tutorial;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public interface ITutorialStep {
    default public void onStop() {
    }

    default public void tick() {
    }

    default public void handleMovement(MovementInput movementInput) {
    }

    default public void onMouseMove(double d, double d2) {
    }

    default public void onMouseHover(ClientWorld clientWorld, RayTraceResult rayTraceResult) {
    }

    default public void onHitBlock(ClientWorld clientWorld, BlockPos blockPos, BlockState blockState, float f) {
    }

    default public void openInventory() {
    }

    default public void handleSetSlot(ItemStack itemStack) {
    }
}

