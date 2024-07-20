/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import com.sun.jna.platform.mac.MacFileUtils;
import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Wrapper {
    private static Wrapper theWrapper = new Wrapper();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static volatile Wrapper INSTANCE = new Wrapper();
    public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    public static MacFileUtils.FileManager fileManager;

    public static Wrapper getInstance() {
        return theWrapper;
    }

    public static float getCooldown() {
        return Minecraft.player.getCooledAttackStrength(0.0f);
    }

    public static MacFileUtils.FileManager getFileManager() {
        if (fileManager == null) {
            // empty if block
        }
        return fileManager;
    }

    public static Entity getRenderEntity() {
        return mc.getRenderViewEntity();
    }

    public static Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        Wrapper.getMinecraft();
        return Minecraft.player;
    }

    public static World getWorld() {
        return Wrapper.getMinecraft().world;
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, E value, String ... fieldNames) {
        try {
            Wrapper.findField(classToAccess, fieldNames).set(instance, value);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    private static Field findField(Class<?> clazz, String ... fieldNames) {
        Exception failed = null;
        for (String fieldName : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception e) {
                failed = e;
            }
        }
        return null;
    }

    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP player() {
        INSTANCE.mc();
        return Minecraft.player;
    }

    public WorldClient world() {
        return Wrapper.INSTANCE.mc().world;
    }

    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }

    public FontRenderer fontRenderer() {
        return Wrapper.INSTANCE.mc().fontRendererObj;
    }

    public void sendPacket(CPacketAnimation cPacketAnimation) {
        this.player().connection.sendPacket(cPacketAnimation);
    }

    public InventoryPlayer inventory() {
        return this.player().inventory;
    }

    public PlayerControllerMP controller() {
        return Wrapper.INSTANCE.mc().playerController;
    }

    public void sendPacket(CPacketPlayer cPacketPlayer) {
    }
}

