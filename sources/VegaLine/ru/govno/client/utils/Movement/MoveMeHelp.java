/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Movement;

import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.utils.Math.MathUtils;

public class MoveMeHelp {
    public static double getCurrentSpeed(int stage, boolean slowDownHop) {
        double speed = MoveMeHelp.getBasedSpeed() + 0.028 * (double)MoveMeHelp.getSpeedEffect() + (double)MoveMeHelp.getSpeedEffect() / 15.0;
        double initSpeed = 0.4145 + (double)MoveMeHelp.getSpeedEffect() / 12.5;
        double decrease = (double)stage / 500.0 * 1.87;
        if (stage == 0) {
            speed = 0.64 + ((double)MoveMeHelp.getSpeedEffect() + 0.028 * (double)MoveMeHelp.getSpeedEffect()) * 0.134;
        } else if (stage == 1) {
            speed = initSpeed;
        } else if (stage >= 2) {
            speed = initSpeed - decrease;
        }
        return Math.max(speed, slowDownHop ? speed : MoveMeHelp.getBasedSpeed() + 0.028 * (double)MoveMeHelp.getSpeedEffect());
    }

    public static double getMotionYaw() {
        double motionYaw = Math.toDegrees(Math.atan2(Entity.Getmotionz, Entity.Getmotionx) - 90.0);
        motionYaw = motionYaw < 0.0 ? motionYaw + 360.0 : motionYaw;
        return motionYaw;
    }

    public static double getDirDiffOfMotions(double motionX, double motionZ, double moveYaw) {
        return Math.abs(MathUtils.wrapDegrees(Math.toDegrees(Math.atan2(motionZ, motionX))) - MathUtils.wrapDegrees(moveYaw % 360.0 + 90.0));
    }

    public static double getDirDiffOfMotions(double motionX, double motionZ) {
        return MoveMeHelp.getDirDiffOfMotions(motionX, motionZ, Minecraft.player.rotationYaw);
    }

    public static double getDirDiffOfMotionsNoAbs(double motionX, double motionZ, double moveYaw) {
        return MathUtils.wrapDegrees(Math.toDegrees(Math.atan2(motionZ, motionX))) - MathUtils.wrapDegrees(moveYaw % 360.0 + 90.0);
    }

    public static double getDirDiffOfMotionsNoAbs(double motionX, double motionZ) {
        return MoveMeHelp.getDirDiffOfMotionsNoAbs(motionX, motionZ, Minecraft.player.rotationYaw);
    }

    public static boolean trapdoorAdobedEntity(EntityLivingBase livingIn) {
        double xzExpand = (double)livingIn.width / 2.0 - 0.01;
        int offsetsCount = 18;
        boolean trapDoorLevels = false;
        boolean anyCounter1 = false;
        boolean anyCounter2 = false;
        AxisAlignedBB entityBox = livingIn.getEntityBoundingBox();
        if (!Minecraft.getMinecraft().world.getCollisionBoxes(livingIn, entityBox.setMaxY(entityBox.maxY + (double)0.03f)).isEmpty()) {
            double[] xOffsets = new double[]{0.0, xzExpand, -xzExpand, xzExpand, -xzExpand, 0.0, 0.0, xzExpand, xzExpand};
            double[] zOffsets = new double[]{0.0, xzExpand, -xzExpand, 0.0, 0.0, xzExpand, -xzExpand, -xzExpand, -xzExpand};
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            Vec3d entityPos = livingIn.getPositionVector();
            ArrayList<IBlockState> checkStatesList = new ArrayList<IBlockState>();
            for (int offsetsNum = 0; offsetsNum < offsetsCount; ++offsetsNum) {
                double xOffset = xOffsets[offsetsNum / 2];
                double zOffset = zOffsets[offsetsNum / 2];
                Vec3d forSetMulVec = entityPos.addVector(xOffset, 1.26 - (double)(offsetsNum % 2), zOffset);
                IBlockState blockState = Minecraft.getMinecraft().world.getBlockState(mutPos.setPos(forSetMulVec.xCoord, forSetMulVec.yCoord, forSetMulVec.zCoord));
                checkStatesList.add(blockState);
            }
            if (checkStatesList.isEmpty()) {
                return false;
            }
            for (int blockStateCounter = 0; blockStateCounter < checkStatesList.size(); ++blockStateCounter) {
                Block block = ((IBlockState)checkStatesList.get(blockStateCounter)).getBlock();
                if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR) continue;
                if (blockStateCounter % 2 == 0) {
                    anyCounter1 = true;
                    continue;
                }
                anyCounter2 = true;
            }
            return (anyCounter1 || anyCounter2) && anyCounter1 != anyCounter2;
        }
        return false;
    }

    public static final boolean moveKeyPressed(int keyNumber) {
        boolean w = Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
        boolean a = GameSettings.keyBindLeft.isKeyDown();
        boolean s = Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown();
        boolean d = GameSettings.keyBindRight.isKeyDown();
        return keyNumber == 0 ? w : (keyNumber == 1 ? a : (keyNumber == 2 ? s : keyNumber == 3 && d));
    }

    public static final boolean w() {
        return MoveMeHelp.moveKeyPressed(0);
    }

    public static final boolean a() {
        return MoveMeHelp.moveKeyPressed(1);
    }

    public static final boolean s() {
        return MoveMeHelp.moveKeyPressed(2);
    }

    public static final boolean d() {
        return MoveMeHelp.moveKeyPressed(3);
    }

    public static final float moveYaw(float entityYaw) {
        return entityYaw + (float)(MoveMeHelp.a() && MoveMeHelp.d() && (!MoveMeHelp.w() || !MoveMeHelp.s()) && (MoveMeHelp.w() || MoveMeHelp.s()) ? (MoveMeHelp.w() ? 0 : (MoveMeHelp.s() ? 180 : 0)) : (MoveMeHelp.w() && MoveMeHelp.s() && (!MoveMeHelp.a() || !MoveMeHelp.d()) && (MoveMeHelp.a() || MoveMeHelp.d()) ? (MoveMeHelp.a() ? -90 : (MoveMeHelp.d() ? 90 : 0)) : (MoveMeHelp.a() && MoveMeHelp.d() && (!MoveMeHelp.w() || !MoveMeHelp.s()) || MoveMeHelp.w() && MoveMeHelp.s() && (!MoveMeHelp.a() || !MoveMeHelp.d()) ? 0 : (MoveMeHelp.a() || MoveMeHelp.d() || MoveMeHelp.s() ? (MoveMeHelp.w() && !MoveMeHelp.s() ? 45 : (MoveMeHelp.s() && !MoveMeHelp.w() ? (MoveMeHelp.a() || MoveMeHelp.d() ? 135 : 180) : (!MoveMeHelp.w() && !MoveMeHelp.s() || MoveMeHelp.w() && MoveMeHelp.s() ? 90 : 0))) * (MoveMeHelp.a() ? -1 : 1) : 0))));
    }

    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static boolean moving() {
        return MoveMeHelp.w() || MoveMeHelp.a() || MoveMeHelp.s() || MoveMeHelp.d();
    }

    public static double getSpeedByBPS(double bps) {
        return bps / 15.3571428571;
    }

    public static void setMotionSpeed(boolean cutting, boolean onlyMove, double speed) {
        float yawPre;
        if (!MoveMeHelp.moving()) {
            speed = 0.0;
        }
        if (MathUtils.getDifferenceOf(yawPre = -(Minecraft.player.lastReportedPreYaw - Minecraft.player.rotationYaw) * 3.0f, 0.0f) > 30.0) {
            yawPre = yawPre > 0.0f ? 30.0f : -30.0f;
        }
        float yaw = Minecraft.player.rotationYaw;
        if (HitAura.get.currentBooleanValue("RotateMoveSide") && HitAura.TARGET != null) {
            yaw = HitAura.get.rotationsVisual[0];
            yawPre = 0.0f;
        }
        float moveYaw = MoveMeHelp.moveYaw(yaw + yawPre);
        double sin = -Math.sin(Math.toRadians(moveYaw)) * speed;
        double cos = Math.cos(Math.toRadians(moveYaw)) * speed;
        if (!onlyMove || MoveMeHelp.moving()) {
            if (cutting) {
                Entity.motionx = sin / (double)1.06f;
            }
            Minecraft.player.motionX = sin;
            if (cutting) {
                Entity.motionz = cos / (double)1.06f;
            }
            Minecraft.player.motionZ = cos;
        }
    }

    public static void setMotionSpeed(boolean cutting, boolean onlyMove, double speed, float moveYaw) {
        if (!MoveMeHelp.moving()) {
            speed = 0.0;
        }
        double sin = -Math.sin(Math.toRadians(moveYaw)) * speed;
        double cos = Math.cos(Math.toRadians(moveYaw)) * speed;
        if (!onlyMove || MoveMeHelp.moving()) {
            if (cutting) {
                Entity.motionx = sin / (double)1.06f;
            }
            Minecraft.player.motionX = sin;
            if (cutting) {
                Entity.motionz = cos / (double)1.06f;
            }
            Minecraft.player.motionZ = cos;
        }
    }

    public static int getSpeedEffect() {
        if (Minecraft.player.isPotionActive(MobEffects.SPEED)) {
            return Objects.requireNonNull(Minecraft.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getSpeed2() {
        Minecraft mc = Minecraft.getMinecraft();
        return Math.sqrt(Math.pow(Minecraft.player.motionX, 2.0) + Math.pow(Minecraft.player.motionZ, 2.0));
    }

    public static double getBasedSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static void setPositionSpeed(float speedXZ, boolean stopMotion) {
        Minecraft mc = Minecraft.getMinecraft();
        float rad2 = 0.0f;
        boolean isDiagonal = (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && Minecraft.player.isMoving();
        boolean w = mc.gameSettings.keyBindForward.isKeyDown();
        boolean a = GameSettings.keyBindLeft.isKeyDown();
        boolean s = mc.gameSettings.keyBindBack.isKeyDown();
        boolean d = GameSettings.keyBindRight.isKeyDown();
        if (a) {
            rad2 -= isDiagonal ? 45.0f : 90.0f;
            if (d) {
                rad2 += isDiagonal ? 45.0f : 90.0f;
            }
        } else if (d) {
            rad2 += isDiagonal ? 45.0f : 90.0f;
        }
        if (s) {
            float f = isDiagonal ? (float)(a ? -135 : (d ? 135 : 0)) : (rad2 = 180.0f);
        }
        if (!MoveMeHelp.isMoving()) {
            return;
        }
        double yaw = Math.toRadians(Minecraft.player.rotationYaw + rad2);
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        double xPlus = -Math.sin(yaw) * (double)speedXZ;
        double zPlus = Math.cos(yaw) * (double)speedXZ;
        Minecraft.player.setPosition(x + xPlus, y, z + zPlus);
        if (stopMotion) {
            Minecraft.player.multiplyMotionXZ(0.0f);
        }
    }

    public static float getAllDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        float rotationYaw = Minecraft.player.rotationYaw;
        float factor = 0.0f;
        if (MovementInput.moveForward > 0.0f) {
            factor = 1.0f;
        }
        if (MovementInput.moveForward < 0.0f) {
            factor = -1.0f;
        }
        if (factor == 0.0f) {
            if (MovementInput.moveStrafe > 0.0f) {
                rotationYaw -= 90.0f;
            }
            if (MovementInput.moveStrafe < 0.0f) {
                rotationYaw += 90.0f;
            }
        } else {
            if (MovementInput.moveStrafe > 0.0f) {
                rotationYaw -= 45.0f * factor;
            }
            if (MovementInput.moveStrafe < 0.0f) {
                rotationYaw += 45.0f * factor;
            }
        }
        if (factor < 0.0f) {
            rotationYaw -= 180.0f;
        }
        return (float)Math.toRadians(rotationYaw);
    }

    public static void multiplySpeed(double speed) {
        MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * speed);
    }

    public static float getYawToEntity(EntityLivingBase entityIn) {
        return MoveMeHelp.getRotations(entityIn)[0] - 180.0f;
    }

    private static void Motion(double d, float f, double d2, double d3, boolean onMove, boolean smartKeep) {
        Minecraft mc = Minecraft.getMinecraft();
        double d4 = d3;
        double d5 = d2;
        float keep = 0.0f;
        float f2 = f;
        if (d4 != 0.0 || d5 != 0.0) {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += d4 > 0.0 ? -keep : keep;
                } else if (d5 < 0.0) {
                    f2 += d4 > 0.0 ? keep : -keep;
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 93.5f));
            double d7 = Math.sin(Math.toRadians(f2 + 93.5f));
            if (onMove) {
                Entity.motionx = (d4 * d * d6 + d5 * d * d7) / 1.06;
                Entity.motionz = (d4 * d * d7 - d5 * d * d6) / 1.06;
            } else {
                Minecraft.player.motionX = d4 * d * d6 + d5 * d * d7;
                Minecraft.player.motionZ = d4 * d * d7 - d5 * d * d6;
            }
        }
    }

    public static void silentDirection(float atYaw) {
        Minecraft mc = Minecraft.getMinecraft();
        float dirYaw = 0.0f;
        float rad2 = 0.0f;
        boolean isDiagonal = (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && Minecraft.player.isMoving();
        boolean w = mc.gameSettings.keyBindForward.isKeyDown();
        boolean a = GameSettings.keyBindLeft.isKeyDown();
        boolean s = mc.gameSettings.keyBindBack.isKeyDown();
        boolean d = GameSettings.keyBindRight.isKeyDown();
        float a45 = 45.0f;
        float a90 = 90.0f;
        if (a) {
            rad2 -= isDiagonal ? a45 : a90;
            if (d) {
                rad2 += isDiagonal ? a45 : a90;
            }
        } else if (d) {
            rad2 += isDiagonal ? a45 : a90;
        }
        if (s) {
            rad2 = isDiagonal ? (a ? -a45 * 3.0f : (d ? a45 * 3.0f : 0.0f)) : 180.0f;
        }
        double yaw = atYaw - 90.0f;
        float motYawX = (float)Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * (float)Entity.Getmotionx;
        float motYawZ = (float)(-Math.cos(Math.toRadians(Minecraft.player.rotationYaw))) * (float)Entity.Getmotionz;
        float yawSpeed = (float)Math.sqrt(motYawX * motYawX + motYawZ * motYawZ);
        int dir = yaw > (double)Minecraft.player.rotationYaw ? -1 : 1;
        MoveMeHelp.Motion(-MoveMeHelp.getCuttingSpeed(), (float)yaw + (dirYaw += rad2), -dir, 0.0, true, false);
    }

    public static void silentDirection(double speed, float atYaw) {
        double yaw = Math.toRadians(atYaw);
        Minecraft mc = Minecraft.getMinecraft();
        Minecraft.player.motionX = Math.sin(yaw) * speed;
        Minecraft.player.motionZ = Math.cos(yaw) * speed;
    }

    private static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY;
        return MoveMeHelp.getRotationFromPosition(x, z, y);
    }

    private static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.player.posX;
        double zDiff = z - Minecraft.player.posZ;
        double yDiff = y - Minecraft.player.posY - 1.8;
        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 92.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static void rotationStrafe(EventRotationStrafe event, float yaw) {
        float f;
        int dif = (int)((MathUtils.wrapDegrees(Minecraft.player.rotationYaw - yaw - 23.5f - 135.0f) + 180.0f) / 45.0f);
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float calcForward = 0.0f;
        float calcStrafe = 0.0f;
        switch (dif) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }
        if ((double)calcForward > 1.0 || (double)calcForward < 0.9 && (double)calcForward > 0.3 || (double)calcForward < -1.0 || (double)calcForward > -0.9 && (double)calcForward < -0.3) {
            calcForward *= 0.5f;
        }
        if ((double)calcStrafe > 1.0 || (double)calcStrafe < 0.9 && (double)calcStrafe > 0.3 || (double)calcStrafe < -1.0 || (double)calcStrafe > -0.9 && (double)calcStrafe < -0.3) {
            calcStrafe *= 0.5f;
        }
        float d = calcStrafe * calcStrafe + calcForward * calcForward;
        if (f >= 1.0E-4f) {
            float f2;
            d = MathHelper.sqrt(d);
            if (f2 < 1.0f) {
                d = 1.0f;
            }
            d = friction / d;
            float yawSin = (float)Math.sin((float)((double)yaw * Math.PI / 180.0));
            float yawCos = (float)Math.cos((float)((double)yaw * Math.PI / 180.0));
            Minecraft.player.motionX += (double)((calcStrafe *= d) * yawCos - (calcForward *= d) * yawSin);
            Minecraft.player.motionZ += (double)(calcForward * yawCos + calcStrafe * yawSin);
        }
    }

    public static void calculateSilentMove(EventRotationStrafe event, float yaw) {
        float dist;
        Minecraft mc = Minecraft.getMinecraft();
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        int difference = (int)((MathHelper.wrapDegrees(Minecraft.player.rotationYaw - yaw - 23.5f - 135.0f) + 180.0f) / 45.0f);
        float calcForward = 0.0f;
        float calcStrafe = 0.0f;
        switch (difference) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }
        if (calcForward > 1.0f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1.0f || calcForward > -0.9f && calcForward < -0.3f) {
            calcForward *= 0.5f;
        }
        if (calcStrafe > 1.0f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1.0f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
            calcStrafe *= 0.5f;
        }
        if ((dist = calcStrafe * calcStrafe + calcForward * calcForward) >= 1.0E-4f) {
            dist = (float)((double)friction / Math.max(1.0, Math.sqrt(dist)));
            float yawSin = MathHelper.sin((float)((double)yaw * Math.PI / 180.0));
            float yawCos = MathHelper.cos((float)((double)yaw * Math.PI / 180.0));
            Minecraft.player.motionX += (double)((calcStrafe *= dist) * yawCos - (calcForward *= dist) * yawSin);
            Minecraft.player.motionZ += (double)(calcForward * yawCos + calcStrafe * yawSin);
        }
    }

    public static double[] getSpeed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        float yaw = Minecraft.player.rotationYaw;
        double forward = MovementInput.field_192832_b;
        double strafe = MovementInput.moveStrafe;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        return new double[]{forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)), forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)), yaw};
    }

    public static double getSpeed() {
        Minecraft mc = Minecraft.getMinecraft();
        return Math.sqrt(Minecraft.player.motionX * Minecraft.player.motionX + Minecraft.player.motionZ * Minecraft.player.motionZ);
    }

    public static double getCuttingSpeed() {
        return Math.sqrt(Entity.Getmotionx * Entity.Getmotionx + Entity.Getmotionz * Entity.Getmotionz);
    }

    public static float getKeysDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        double yaw = Minecraft.player.rotationYaw;
        if (mc.gameSettings.keyBindForward.isKeyDown() && GameSettings.keyBindLeft.isKeyDown() && !GameSettings.keyBindRight.isKeyDown()) {
            yaw -= 45.0;
        }
        if (mc.gameSettings.keyBindForward.isKeyDown() && GameSettings.keyBindRight.isKeyDown() && !GameSettings.keyBindLeft.isKeyDown()) {
            yaw += 45.0;
        }
        if (!mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && GameSettings.keyBindLeft.isKeyDown() && !GameSettings.keyBindRight.isKeyDown()) {
            yaw -= 90.0;
        }
        if (!mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && GameSettings.keyBindRight.isKeyDown() && !GameSettings.keyBindLeft.isKeyDown()) {
            yaw += 90.0;
        }
        if (!mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindBack.isKeyDown() && GameSettings.keyBindLeft.isKeyDown() && !GameSettings.keyBindRight.isKeyDown()) {
            yaw -= 135.0;
        }
        if (!mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindBack.isKeyDown() && GameSettings.keyBindRight.isKeyDown() && !GameSettings.keyBindLeft.isKeyDown()) {
            yaw += 135.0;
        }
        if (!mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindBack.isKeyDown() && !GameSettings.keyBindRight.isKeyDown() && !GameSettings.keyBindLeft.isKeyDown()) {
            yaw += 180.0;
        }
        return 0.0f;
    }

    public static float getMoveDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        double motionX = Entity.Getmotionx;
        double motionZ = Entity.Getmotionz;
        float direction = (float)(Math.atan2(motionX, motionZ) / Math.PI * 180.0);
        return -direction;
    }

    public static boolean isBlockAboveHead() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX - 0.3, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ - 0.3, Minecraft.player.posX + 0.3, Minecraft.player.posY + (double)Minecraft.player.height + 0.4, Minecraft.player.posZ - 0.3);
        return bb != null && mc.world != null && Minecraft.player != null && !mc.world.getCollisionBoxes(Minecraft.player, bb).isEmpty();
    }

    public static boolean isBlockAboveHeadSolo() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ, Minecraft.player.posX, Minecraft.player.posY + (double)Minecraft.player.height + 0.4, Minecraft.player.posZ);
        return !mc.world.getCollisionBoxes(Minecraft.player, bb).isEmpty();
    }

    public static boolean isBlockAboveHead(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB bb = new AxisAlignedBB(entity.posX - 0.3, entity.posY + (double)entity.getEyeHeight(), entity.posZ + 0.3, entity.posX + 0.3, entity.posY + 2.5, entity.posZ - 0.3);
        return !mc.world.getCollisionBoxes(entity, bb).isEmpty();
    }

    public static boolean moveKeysPressed() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || GameSettings.keyBindLeft.isKeyDown() || GameSettings.keyBindRight.isKeyDown();
    }

    public static void setSpeed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = MovementInput.field_192832_b;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if (HitAura.get.currentBooleanValue("RotateMoveSide") && HitAura.TARGET != null) {
            yaw = HitAura.get.rotationsVisual[0];
        }
        if (MoveMeHelp.isMoving()) {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 89.5f));
            double sin = Math.sin(Math.toRadians(yaw + 89.5f));
            Minecraft.player.motionX = forward * speed * cos + strafe * speed * sin;
            Minecraft.player.motionZ = forward * speed * sin - strafe * speed * cos;
        } else {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        }
    }

    public static void setCuttingSpeed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean tickTime = Minecraft.player.ticksExisted % 2 == 0;
        double forward = MovementInput.field_192832_b;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw - (Minecraft.player.lastReportedPreYaw - Minecraft.player.rotationYaw) * 2.0f;
        if (HitAura.get.currentBooleanValue("RotateMoveSide") && HitAura.TARGET != null) {
            yaw = HitAura.get.rotationsVisual[0];
        }
        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || GameSettings.keyBindLeft.isKeyDown() || GameSettings.keyBindRight.isKeyDown()) {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 89.5f));
            double sin = Math.sin(Math.toRadians(yaw + 89.5f));
            Entity.motionx = forward * speed * cos + strafe * speed * sin;
            Entity.motionz = forward * speed * sin - strafe * speed * cos;
        } else {
            Entity.motionx = tickTime ? 1.0E-10 : -1.0E-10;
            Entity.motionz = tickTime ? 1.0E-10 : -1.0E-10;
        }
    }

    public static void setSpeed(double speed, float noMoveSlowSpeed) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = MovementInput.field_192832_b;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if (HitAura.get.currentBooleanValue("RotateMoveSide") && HitAura.TARGET != null) {
            yaw = HitAura.get.rotationsVisual[0];
        }
        if (MoveMeHelp.isMoving()) {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 89.5f));
            double sin = Math.sin(Math.toRadians(yaw + 89.5f));
            Minecraft.player.motionX = forward * speed * cos + strafe * speed * sin;
            Minecraft.player.motionZ = forward * speed * sin - strafe * speed * cos;
        } else {
            Minecraft.player.motionX *= (double)noMoveSlowSpeed;
            Minecraft.player.motionZ *= (double)noMoveSlowSpeed;
        }
    }

    public static void setSpeed(double speed, int strafeMove) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = MovementInput.field_192832_b;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if (HitAura.get.currentBooleanValue("RotateMoveSide") && HitAura.TARGET != null) {
            yaw = HitAura.get.rotationsVisual[0];
        }
        if (MoveMeHelp.isMoving()) {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -strafeMove : strafeMove);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? strafeMove : -strafeMove);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            Minecraft.player.motionX = forward * speed * cos + strafe * speed * sin;
            Minecraft.player.motionZ = forward * speed * sin - strafe * speed * cos;
        } else {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        }
    }

    public static boolean isMoving() {
        Minecraft mc = Minecraft.getMinecraft();
        return MovementInput.field_192832_b != 0.0f || MovementInput.moveStrafe != 0.0f;
    }

    public static float getMaxFallDist() {
        Minecraft mc = Minecraft.getMinecraft();
        PotionEffect potioneffect = Minecraft.player.getActivePotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("jump")));
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return Minecraft.player.getMaxFallHeight() + f;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        if (Minecraft.player.isPotionActive(Potion.REGISTRY.getObject(new ResourceLocation("jump")))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("jump"))).getAmplifier();
            baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        }
        return baseJumpHeight;
    }

    public static double getBaseJumpHeight() {
        return MoveMeHelp.getJumpBoostModifier(0.41999998688698);
    }
}

