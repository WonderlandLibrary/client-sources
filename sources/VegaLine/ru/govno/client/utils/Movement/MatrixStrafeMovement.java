/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.events.EventAction;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.module.modules.Criticals;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.MoveHelper;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.Strafe;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class MatrixStrafeMovement {
    public static double oldSpeed;
    public static double contextFriction;
    public static boolean needSwap;
    public static boolean prevSprint;
    public static int counter;
    public static int noSlowTicks;

    /*
     * Enabled aggressive block sorting
     */
    public static double calculateSpeed(boolean strict, EventMove2 move, boolean ely, double speed) {
        double d;
        boolean customSprintState;
        boolean cancelSlow;
        float noslowPercent;
        double max;
        double max2;
        float n8;
        float n6;
        block32: {
            block33: {
                boolean speed2;
                boolean noSlow = MoveHelper.instance.NoSlowDown.bValue;
                boolean biposNoSlow = noSlow && Minecraft.player.getActiveHand() == EnumHand.MAIN_HAND && MoveHelper.instance.NCPFapBypass.bValue;
                String slowType = MoveHelper.instance.NoSlowMode.currentMode;
                Minecraft mc = Minecraft.getMinecraft();
                boolean cancelByCR = false;
                if (Minecraft.player != null && Criticals.get.actived && Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop") && HitAura.TARGET != null && !Minecraft.player.isJumping() && !Minecraft.player.isInWater() && !Minecraft.player.onGround && Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 0.15, Minecraft.player.posZ)) {
                    cancelByCR = true;
                }
                n6 = 0.91f;
                boolean speed1 = Minecraft.player.getActivePotionEffect(MobEffects.SPEED) != null && Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() == 0;
                boolean bl = speed2 = Minecraft.player.getActivePotionEffect(MobEffects.SPEED) != null && Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() == 1;
                if (Minecraft.player.onGround) {
                    n6 = MatrixStrafeMovement.getFrictionFactor(Minecraft.player, move);
                }
                float n7 = 0.16277136f / (n6 * n6 * n6);
                n8 = 0.0f;
                if (Minecraft.player.onGround) {
                    n8 = MatrixStrafeMovement.getAIMoveSpeed(Minecraft.player) * n7;
                    if (move.motion().yCoord > 0.0) {
                        n8 = strict ? (float)((double)n8 + (speed2 ? 0.33 : (speed1 ? 0.315 : 0.24))) : (float)((double)n8 + 0.2);
                    }
                } else if (!cancelByCR) {
                    n8 = strict && Minecraft.player.fallDistance < 7.0f ? (speed2 ? 0.04f : (speed1 ? 0.035f : 0.03f)) : 0.024f;
                }
                max2 = oldSpeed + (double)n8;
                max = 0.0;
                noslowPercent = 1.0f;
                cancelSlow = false;
                if (!Minecraft.player.isHandActive()) break block32;
                if (!noSlow) break block33;
                switch (slowType) {
                    case "Vanilla": {
                        noslowPercent = 1.0f;
                        cancelSlow = true;
                        break;
                    }
                    case "MatrixOld": {
                        noslowPercent = 0.92f;
                        break;
                    }
                    case "MatrixLatest": {
                        boolean a = false;
                        if (Minecraft.player.isHandActive() && Minecraft.player.getItemInUseMaxCount() > 3 && !Minecraft.player.isInWater() && !Minecraft.player.isInLava() && !Minecraft.player.isInWeb && !Minecraft.player.capabilities.isFlying && Minecraft.player.getTicksElytraFlying() <= 1 && MoveMeHelp.isMoving()) {
                            double d2 = Minecraft.player.fallDistance;
                            double d3 = Minecraft.player.isJumping() ? 0.725 : 0.5;
                            if (d2 > d3 && (double)Minecraft.player.fallDistance <= 2.5 || (double)Minecraft.player.fallDistance > 2.5 && Minecraft.player.fallDistance % 2.0f == 0.0f) {
                                noslowPercent = 0.9925f;
                                a = true;
                            }
                        }
                        if (!a) {
                            cancelSlow = true;
                            break;
                        }
                        break block32;
                    }
                    case "AACOld": {
                        noslowPercent = 1.0f;
                        cancelSlow = true;
                        break;
                    }
                    case "NCP+": {
                        boolean stop;
                        if (Minecraft.player.isHandActive() && Minecraft.player.getActiveHand() == EnumHand.MAIN_HAND && Minecraft.player.isBlocking() && Minecraft.player.getActiveHand() == EnumHand.OFF_HAND && !EntityLivingBase.isMatrixDamaged && !Minecraft.player.isInWater() && !Minecraft.player.isInLava() && !biposNoSlow) {
                            boolean stop2;
                            float pc;
                            float f = Minecraft.player.isInWater() || Minecraft.player.isInLava() ? 0.0f : (pc = MathUtils.clamp((float)Minecraft.player.getItemInUseMaxCount() / (Minecraft.player.isJumping() ? 18.0f : 12.0f), 0.0f, 1.0f));
                            noslowPercent = 1.0f - (Minecraft.player.onGround ? (Minecraft.player.isJumping() ? 0.5f : 0.43f) : (move.motion().yCoord > 0.0 ? 0.56f : 0.13f)) * pc;
                            boolean bl2 = stop2 = Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.capabilities.isFlying || Minecraft.player.getTicksElytraFlying() > 1 || !MoveMeHelp.isMoving();
                            if (Minecraft.player.isHandActive() && Minecraft.player.getItemInUseMaxCount() > 3 && !stop2 && Minecraft.player.onGround && !Minecraft.player.isJumping()) break;
                            noslowPercent = 0.9925f;
                            break;
                        }
                        boolean bl3 = stop = Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.capabilities.isFlying || Minecraft.player.getTicksElytraFlying() > 1 || !MoveMeHelp.isMoving();
                        if (!Minecraft.player.isHandActive() || Minecraft.player.getItemInUseMaxCount() <= 3 || stop || !Minecraft.player.onGround || Minecraft.player.isJumping()) {
                            cancelSlow = true;
                            break;
                        }
                        break block32;
                    }
                }
                break block32;
            }
            noslowPercent = 0.4f;
            max2 /= 2.0;
        }
        if (Minecraft.player.isHandActive() && move.motion().yCoord <= 0.0 && !cancelSlow) {
            double d4;
            max = Math.max(0.043, oldSpeed + (double)n8 * 0.5 + (double)0.005f + (move.motion().yCoord != 0.0 && Math.abs(move.motion().yCoord) < 0.08 ? 0.055 : 0.0));
            noSlowTicks = max2 > d4 ? ++noSlowTicks : Math.max(noSlowTicks - 1, 0);
        } else {
            noSlowTicks = 0;
        }
        if (noSlowTicks > 0) {
            max2 = noSlowTicks > 0 ? max * (double)noslowPercent : Math.max(0.25, max2) - (counter++ % 2 == 0 ? 0.001 : 0.002);
        } else {
            max2 = Math.max(0.25, max2) - (counter++ % 2 == 0 ? 0.001 : 0.002);
            max2 *= (double)noslowPercent;
        }
        contextFriction = n6;
        if (!move.toGround() && !Minecraft.player.onGround) {
            needSwap = true;
        } else {
            prevSprint = false;
        }
        Strafe.needSprintState = customSprintState = (!move.toGround() || !Minecraft.player.onGround) && !Minecraft.player.serverSprintState;
        TargetStrafe.needSprintState = customSprintState;
        if (ely) {
            d = speed;
            return max2 += d;
        }
        d = 0.0;
        return max2 += d;
    }

    public static void postMove(double horizontal) {
        oldSpeed = horizontal * contextFriction;
    }

    public static float getAIMoveSpeed(EntityPlayer contextPlayer) {
        boolean prevSprinting = contextPlayer.isSprinting();
        contextPlayer.setSprinting(false);
        float speed = contextPlayer.getAIMoveSpeed() * 1.3f;
        contextPlayer.setSprinting(prevSprinting);
        return speed;
    }

    public static void actionEvent(EventAction eventAction) {
        if (needSwap) {
            eventAction.setSprintState(!Minecraft.player.serverSprintState);
            needSwap = false;
        }
        if (Minecraft.player.isJumping() && Minecraft.player.onGround) {
            eventAction.setSprintState(true);
        }
    }

    private static float getFrictionFactor(EntityPlayer contextPlayer, EventMove2 move) {
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(move.from().xCoord, move.getAABBFrom().minY - 1.0, move.from().zCoord);
        return contextPlayer.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos).getBlock().slipperiness * 0.91f;
    }
}

