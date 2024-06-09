package com.client.glowclient.utils;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;

public final class ControllerUtils
{
    public static void rightClick() {
        getPlayerController().processRightClick((EntityPlayer)MinecraftHelper.getPlayer(), (World)MinecraftHelper.getWorld(), EnumHand.MAIN_HAND);
    }
    
    public ControllerUtils() {
        super();
    }
    
    public static ItemStack quickMoveSlot(final int n) {
        final PlayerControllerMP playerController = getPlayerController();
        final int n2 = 0;
        return playerController.windowClick(n2, n, n2, ClickType.QUICK_MOVE, (EntityPlayer)MinecraftHelper.getPlayer());
    }
    
    public static ItemStack throwSlot(final int n) {
        return getPlayerController().windowClick(0, n, 1, ClickType.THROW, (EntityPlayer)MinecraftHelper.getPlayer());
    }
    
    public static void rightClickBlock(final BlockPos blockPos, final EnumFacing enumFacing, final Vec3d vec3d) {
        getPlayerController().processRightClickBlock(MinecraftHelper.getPlayer(), MinecraftHelper.getWorld(), blockPos, enumFacing, vec3d, EnumHand.MAIN_HAND);
    }
    
    public static ItemStack pickupSlot(final int n) {
        final PlayerControllerMP playerController = getPlayerController();
        final int n2 = 0;
        return playerController.windowClick(n2, n, n2, ClickType.PICKUP, (EntityPlayer)MinecraftHelper.getPlayer());
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }
}
