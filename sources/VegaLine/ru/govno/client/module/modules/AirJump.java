/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.RandomUtils;

public class AirJump
extends Module {
    public static AirJump get;
    Settings Mode;
    final TimerHelper tick = new TimerHelper();

    public AirJump() {
        super("AirJump", 0, Module.Category.MOVEMENT);
        this.Mode = new Settings("Mode", "Matrix", (Module)this, new String[]{"Matrix", "Matrix2", "Default"});
        this.settings.add(this.Mode);
        get = this;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Mode.currentMode);
    }

    @Override
    public void onUpdate() {
        block37: {
            block39: {
                block40: {
                    block41: {
                        block30: {
                            block38: {
                                block35: {
                                    block36: {
                                        block33: {
                                            block34: {
                                                float ex2;
                                                float ex;
                                                block32: {
                                                    block31: {
                                                        if (!this.Mode.currentMode.equalsIgnoreCase("Matrix")) break block30;
                                                        ex = 1.0f;
                                                        ex2 = 1.0f;
                                                        if (AirJump.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.PURPUR_SLAB) break block31;
                                                        if (AirJump.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.STONE_SLAB) break block31;
                                                        if (AirJump.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() != Blocks.WOODEN_SLAB) break block32;
                                                    }
                                                    ex += 0.6f;
                                                }
                                                Minecraft.player.jumpTicks = 0;
                                                if (Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)) break block33;
                                                if (Speed.posBlock(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)) break block34;
                                                if (Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)) break block34;
                                                if (!Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)) break block35;
                                            }
                                            if (!MoveMeHelp.isMoving()) break block35;
                                        }
                                        if (Minecraft.player.isCollidedHorizontally) break block35;
                                        if (AirJump.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.5, Minecraft.player.posZ)).getBlock() == Blocks.WATER) break block35;
                                        if (AirJump.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() == Blocks.WATER) break block35;
                                        if (!Minecraft.player.isCollidedVertically) break block36;
                                        if (Minecraft.player.ticksExisted % 2 != 0) break block35;
                                    }
                                    Minecraft.player.motionY = 0.0;
                                    Minecraft.player.onGround = true;
                                    Minecraft.player.fallDistance = 0.0f;
                                    if (MoveMeHelp.getSpeed() < -0.05 && !MoveMeHelp.isMoving()) {
                                        if (Minecraft.player.isJumping()) {
                                            Minecraft.player.motionX -= Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * MathUtils.clamp(RandomUtils.getRandomDouble(-1.0, 1.0), -0.005, 0.005);
                                            Minecraft.player.motionZ += Math.cos(Math.toRadians(Minecraft.player.rotationYaw)) * MathUtils.clamp(RandomUtils.getRandomDouble(-1.0, 1.0), -0.005, 0.005);
                                        }
                                    }
                                    Minecraft.player.isCollidedVertically = Minecraft.player.onGround;
                                    Minecraft.player.motionY = 0.0;
                                    if (Minecraft.player.motionY >= 0.0) {
                                        Minecraft.player.fallDistance = 0.0f;
                                    }
                                }
                                AxisAlignedBB B = Minecraft.player.boundingBox;
                                if (!Minecraft.player.isCollidedHorizontally) break block37;
                                if (Minecraft.player.motionY < -0.1) break block38;
                                if (Minecraft.player.ticksExisted % 2 != 0) break block37;
                            }
                            Minecraft.player.onGround = true;
                            Minecraft.player.jump();
                            Minecraft.player.fallDistance = 0.0f;
                            break block37;
                        }
                        if (!this.Mode.currentMode.equalsIgnoreCase("Matrix2")) break block39;
                        if (!Minecraft.player.isJumping()) break block40;
                        if (!(Minecraft.player.motionY < 0.0)) break block40;
                        float w = Minecraft.player.width / 2.0f - 0.001f;
                        if (Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX + (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ + (double)w)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX - (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ - (double)w)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX + (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ - (double)w)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX - (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ + (double)w)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX + (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ)) break block41;
                        if (Speed.posBlock(Minecraft.player.posX - (double)w, Minecraft.player.posY - 1.0, Minecraft.player.posZ + (double)w)) break block41;
                        if (!Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ - (double)w)) break block40;
                    }
                    Minecraft.player.onGround = true;
                    Minecraft.player.fallDistance = 0.0f;
                }
                if (Minecraft.player.isJumping()) {
                    if (Minecraft.player.isCollidedHorizontally) {
                        if (Minecraft.player.ticksExisted % 5 == 0) {
                            Minecraft.player.onGround = true;
                            Minecraft.player.fallDistance = 0.0f;
                        }
                    }
                }
                break block37;
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Default")) {
                Minecraft.player.onGround = true;
            }
        }
    }
}

