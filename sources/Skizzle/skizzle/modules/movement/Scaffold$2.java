/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import java.util.ArrayDeque;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class Scaffold$2
extends Module {
    public BooleanSetting swingItem;
    public NumberSetting extend;
    public NumberSetting delay;
    public Timer timer = new Timer();
    public Timer placeDelay = new Timer();
    public long lastMS;
    public String displayName = Qprot0.0("\u1893\u71c8\u23d5\ua7e2\u3271\u9d15\u8c23\u74e9\u5750");
    public ArrayDeque<BlockPos> posQueue;

    public void placeBlock(BlockPos Nigga, ItemStack Nigga2) {
        Scaffold$2 Nigga3;
        Nigga3.mc.playerController.onPlayerRightClick(Nigga3.mc.thePlayer, Minecraft.theWorld, Nigga2, Nigga, EnumFacing.fromAngle(Nigga3.mc.thePlayer.rotationYaw), new Vec3(Nigga3.mc.thePlayer.posX, Nigga3.mc.thePlayer.posY, Nigga3.mc.thePlayer.posZ));
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion && !Client.ghostMode && Nigga.isPre()) {
            Scaffold$2 Nigga2;
            BlockPos Nigga3 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ);
            BlockPos Nigga4 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ);
            Nigga2.setHeldItem();
            EventMotion Nigga5 = (EventMotion)Nigga;
            if (Nigga2.mc.thePlayer.getHeldItem() != null && Block.getBlockFromItem(Nigga2.mc.thePlayer.getHeldItem().getItem()) != null && Block.getBlockFromItem(Nigga2.mc.thePlayer.getHeldItem().getItem()).isFullBlock()) {
                Nigga3 = new BlockPos(Nigga3.getX() + (int)Nigga2.getXY()[0], Nigga3.getY() - 1, Nigga3.getZ() + (int)Nigga2.getXY()[1]);
                Minecraft.theWorld.isAirBlock(Nigga4.offsetDown().offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.cameraYaw), -1));
                Nigga3 = Nigga3.offset(EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYaw), -1);
                if (Nigga2.timer.hasTimeElapsed((long)1764375560 ^ 0x692A3C58L, true)) {
                    Nigga2.placeIllegalBlocks(Nigga3, Nigga2.mc.thePlayer.getHeldItem());
                    Nigga2.placeBlock(Nigga3, Nigga2.mc.thePlayer.getHeldItem());
                    Nigga5.setYaw(Nigga2.getRotations(Nigga3, EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYawHead))[0]);
                    Nigga5.setPitch(Nigga2.getRotations(Nigga3, EnumFacing.fromAngle(Nigga2.mc.thePlayer.rotationYawHead))[1]);
                }
            }
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean isAirBlock(BlockPos Nigga) {
        return Minecraft.theWorld.isAirBlock(Nigga);
    }

    public void placeIllegalBlocks(BlockPos Nigga, ItemStack Nigga2) {
        Scaffold$2 Nigga3;
        if (Nigga3.isAirBlock(Nigga.offsetEast()) && Nigga3.isIllegalBlock(Nigga)) {
            EntityPlayerSP Nigga4 = Nigga3.mc.thePlayer;
            if (!(Nigga.offsetEast().distanceSq(Nigga4.posX, Nigga4.posY, Nigga4.posZ) > Nigga.offsetWest().distanceSq(Nigga4.posX, Nigga4.posY, Nigga4.posZ))) {
                Nigga3.placeBlock(Nigga.offsetEast(), Nigga2);
            }
            return;
        }
        if (Nigga3.isAirBlock(Nigga.offsetWest()) && Nigga3.isIllegalBlock(Nigga)) {
            Nigga3.placeBlock(Nigga.offsetWest(), Nigga2);
            return;
        }
        if (Nigga3.isAirBlock(Nigga.offsetSouth()) && Nigga3.isIllegalBlock(Nigga)) {
            Nigga3.placeBlock(Nigga.offsetSouth(), Nigga2);
            return;
        }
        if (Nigga3.isAirBlock(Nigga.offsetNorth()) && Nigga3.isIllegalBlock(Nigga)) {
            Nigga3.placeBlock(Nigga.offsetNorth(), Nigga2);
            return;
        }
    }

    public float[] getXY() {
        Scaffold$2 Nigga;
        double Nigga2 = Math.cos(Math.toRadians(Nigga.mc.thePlayer.rotationYaw + Float.intBitsToFloat(1.02112493E9f ^ 0x7E69214F)));
        double Nigga3 = Math.sin(Math.toRadians(Nigga.mc.thePlayer.rotationYaw + Float.intBitsToFloat(1.03788384E9f ^ 0x7F68D9B6)));
        float Nigga4 = (float)((double)Nigga.mc.thePlayer.moveForward * Nigga2 + (double)Nigga.mc.thePlayer.moveStrafing * Nigga3);
        float Nigga5 = (float)((double)Nigga.mc.thePlayer.moveForward * Nigga3 - (double)Nigga.mc.thePlayer.moveStrafing * Nigga2);
        return new float[]{Nigga4, Nigga5};
    }

    public Scaffold$2() {
        super(Qprot0.0("\u1893\u71c8\u23d5\ua7e2\u3271\u9d15\u8c23\u74e9"), 0, Module.Category.MOVEMENT);
        Scaffold$2 Nigga;
        Nigga.swingItem = new BooleanSetting(Qprot0.0("\u1893\u71dc\u23dd\ua7ea\u3270\u9d5a\u8c06\u74f9\u5707\u9930"), false);
        Nigga.extend = new NumberSetting(Qprot0.0("\u1885\u71d3\u23c0\ua7e1\u3279\u9d1e"), 1.0, 1.0, 8.0, 1.0);
        Nigga.delay = new NumberSetting(Qprot0.0("\u1890\u71c7\u23d5\ua7e7\u3272\u9d5a\u8c0b\u74e8\u570e\u993c\u172e"), 50.0, 20.0, 400.0, 10.0);
        Nigga.lastMS = System.currentTimeMillis();
        Nigga.posQueue = new ArrayDeque();
        Nigga.addSettings(Nigga.swingItem, Nigga.extend, Nigga.delay);
    }

    public boolean isIllegalBlock(BlockPos Nigga) {
        Scaffold$2 Nigga2;
        return Nigga2.isAirBlock(Nigga.offsetEast()) && Nigga2.isAirBlock(Nigga.offsetNorth()) && Nigga2.isAirBlock(Nigga.offsetWest()) && Nigga2.isAirBlock(Nigga.offsetSouth());
    }

    public void setHeldItem() {
        for (int Nigga = 9; Nigga > -1; --Nigga) {
            Block Nigga2;
            Scaffold$2 Nigga3;
            if (Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga) == null || (Nigga2 = Block.getBlockFromItem(Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga).getItem())) == null || !Nigga2.isFullBlock()) continue;
            Nigga3.mc.thePlayer.inventory.currentItem = Nigga;
        }
    }

    public void setSpeed(float Nigga) {
        Nigga.mc.thePlayer.motionX *= (double)Nigga;
        Nigga.mc.thePlayer.motionZ *= (double)Nigga;
    }

    public float[] getRotations(BlockPos Nigga, EnumFacing Nigga2) {
        Scaffold$2 Nigga3;
        double Nigga4 = (double)Nigga.getX() + 0.0 - Nigga3.mc.thePlayer.posX + (double)Nigga2.getFrontOffsetX() / 2.0;
        double Nigga5 = (double)Nigga.getZ() + 0.0 - Nigga3.mc.thePlayer.posZ + (double)Nigga2.getFrontOffsetZ() / 2.0;
        double Nigga6 = (double)Nigga.getY() + 0.0;
        double Nigga7 = Nigga3.mc.thePlayer.posY + (double)Nigga3.mc.thePlayer.getEyeHeight() - Nigga6;
        double Nigga8 = MathHelper.sqrt_double(Nigga4 * Nigga4 + Nigga5 * Nigga5);
        float Nigga9 = (float)(Math.atan2(Nigga5, Nigga4) * 180.0 / Math.PI) - Float.intBitsToFloat(1.0349351E9f ^ 0x7F1BDB2E);
        float Nigga10 = (float)(Math.atan2(Nigga7, Nigga8) * 180.0 / Math.PI);
        if (Nigga9 < Float.intBitsToFloat(2.13027162E9f ^ 0x7EF95D7F)) {
            Nigga9 += Float.intBitsToFloat(1.0255945E9f ^ 0x7E955473);
        }
        return new float[]{Nigga9, Nigga10};
    }

    @Override
    public void onEnable() {
    }

    public static {
        throw throwable;
    }
}

