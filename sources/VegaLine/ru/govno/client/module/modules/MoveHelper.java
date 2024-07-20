/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventSlowLay;
import ru.govno.client.event.events.EventSlowSneak;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.OnFalling;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.BlockHelper;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class MoveHelper
extends Module {
    public static MoveHelper instance;
    public Settings NoJumpDelay;
    public Settings FastLadders;
    public Settings GmFlyStrafe;
    public Settings FlyStrafeMode;
    public Settings MatrixSnowFix;
    public Settings StairSpeed;
    public Settings NoSlowDown;
    public Settings NoSlowMode;
    public Settings NoJumpSlowGrim;
    public Settings NCPFapBypass;
    public Settings InWebMotion;
    public Settings WebMotionMode;
    public Settings WebZoom;
    public Settings WebSpeedXZ;
    public Settings WebSpeedYPlus;
    public Settings WebSpeedYMinus;
    public Settings AnchoorHole;
    public Settings Step;
    public Settings StepUpMode;
    public Settings ReverseStep;
    public Settings NoSlowSneak;
    public Settings SneakSlowBypass;
    public Settings NoSlowLay;
    public Settings LaySlowBypass;
    public Settings TrapdoorSpeed;
    public Settings GroundHalt;
    public Settings LevitateControl;
    public Settings NoSlowSoul;
    public Settings FastPilingUp;
    private boolean canPress;
    public static boolean stairTick;
    public static boolean holeTick;
    public static int holeTicks;

    public MoveHelper() {
        super("MoveHelper", 0, Module.Category.MOVEMENT);
        instance = this;
        this.NoJumpDelay = new Settings("NoJumpDelay", true, (Module)this);
        this.settings.add(this.NoJumpDelay);
        this.FastLadders = new Settings("FastLadders", true, (Module)this);
        this.settings.add(this.FastLadders);
        this.GmFlyStrafe = new Settings("GmFlyStrafe", true, (Module)this);
        this.settings.add(this.GmFlyStrafe);
        this.FlyStrafeMode = new Settings("FlyStrafeMode", "Matrix", this, new String[]{"Matrix", "NCP"}, () -> this.GmFlyStrafe.bValue);
        this.settings.add(this.FlyStrafeMode);
        this.MatrixSnowFix = new Settings("MatrixSnowFix", false, (Module)this);
        this.settings.add(this.MatrixSnowFix);
        this.StairSpeed = new Settings("StairSpeed", true, (Module)this);
        this.settings.add(this.StairSpeed);
        this.NoSlowDown = new Settings("NoSlowDown", true, (Module)this);
        this.settings.add(this.NoSlowDown);
        this.NoSlowMode = new Settings("NoSlowMode", "MatrixLatest", this, new String[]{"Vanilla", "MatrixOld", "MatrixLatest", "AACOld", "NCP+", "Grim"}, () -> this.NoSlowDown.bValue);
        this.settings.add(this.NoSlowMode);
        this.NoJumpSlowGrim = new Settings("NoJumpSlowGrim", true, (Module)this, () -> this.NoSlowDown.bValue && this.NoSlowMode.currentMode.equalsIgnoreCase("Grim"));
        this.settings.add(this.NoJumpSlowGrim);
        this.NCPFapBypass = new Settings("NCP+FapBypass", true, (Module)this, () -> this.NoSlowDown.bValue && this.NoSlowMode.currentMode.equalsIgnoreCase("NCP+"));
        this.settings.add(this.NCPFapBypass);
        this.InWebMotion = new Settings("InWebMotion", true, (Module)this);
        this.settings.add(this.InWebMotion);
        this.WebMotionMode = new Settings("WebMotionMode", "Matrix", this, new String[]{"Custom", "Matrix", "NoCollide"}, () -> this.InWebMotion.bValue);
        this.settings.add(this.WebMotionMode);
        this.WebZoom = new Settings("WebZoom", false, (Module)this, () -> this.InWebMotion.bValue && this.WebMotionMode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.WebZoom);
        this.WebSpeedXZ = new Settings("WebSpeedXZ", 0.5f, 1.0f, 0.1f, this, () -> this.InWebMotion.bValue && this.WebMotionMode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.WebSpeedXZ);
        this.WebSpeedYPlus = new Settings("WebSpeedY+", 0.5f, 1.0f, 0.0f, this, () -> this.InWebMotion.bValue && this.WebMotionMode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.WebSpeedYPlus);
        this.WebSpeedYMinus = new Settings("WebSpeedY-", 0.4f, 1.0f, 0.0f, this, () -> this.InWebMotion.bValue && this.WebMotionMode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.WebSpeedYMinus);
        this.AnchoorHole = new Settings("AnchoorHole", true, (Module)this);
        this.settings.add(this.AnchoorHole);
        this.Step = new Settings("Step", false, (Module)this);
        this.settings.add(this.Step);
        this.StepUpMode = new Settings("StepUpMode", "Vanilla", this, new String[]{"Vanilla", "Matrix"}, () -> this.Step.bValue);
        this.settings.add(this.StepUpMode);
        this.ReverseStep = new Settings("Reverse", false, (Module)this, () -> this.Step.bValue);
        this.settings.add(this.ReverseStep);
        this.NoSlowSneak = new Settings("NoSlowSneak", false, (Module)this);
        this.settings.add(this.NoSlowSneak);
        this.SneakSlowBypass = new Settings("SneakSlowBypass", "Vanilla", this, new String[]{"Vanilla", "Matrix", "NCP", "Grim"}, () -> this.NoSlowSneak.bValue);
        this.settings.add(this.SneakSlowBypass);
        this.NoSlowLay = new Settings("NoSlowLay", false, (Module)this, () -> Minecraft.player.hasNewVersionMoves);
        this.settings.add(this.NoSlowLay);
        this.LaySlowBypass = new Settings("LaySlowBypass", "Matrix", this, new String[]{"Vanilla", "Matrix", "NCP", "Grim"}, () -> this.NoSlowLay.bValue && Minecraft.player.hasNewVersionMoves);
        this.settings.add(this.LaySlowBypass);
        this.TrapdoorSpeed = new Settings("TrapdoorSpeed", false, (Module)this);
        this.settings.add(this.TrapdoorSpeed);
        this.GroundHalt = new Settings("GroundHalt", false, (Module)this);
        this.settings.add(this.GroundHalt);
        this.LevitateControl = new Settings("LevitateControl", false, (Module)this);
        this.settings.add(this.LevitateControl);
        this.NoSlowSoul = new Settings("NoSlowSoul", false, (Module)this);
        this.settings.add(this.NoSlowSoul);
        this.FastPilingUp = new Settings("FastPilingUp", false, (Module)this);
        this.settings.add(this.FastPilingUp);
    }

    @EventTarget
    public void onSlowSneak(EventSlowSneak event) {
        if (this.NoSlowSneak.bValue) {
            if (!Minecraft.player.isSneaking()) {
                return;
            }
            switch (this.SneakSlowBypass.currentMode) {
                case "Vanilla": {
                    event.cancel();
                    break;
                }
                case "Matrix": {
                    event.cancel();
                    if (Minecraft.player.ticksExisted % 2 == 0 && Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                        Minecraft.player.multiplyMotionXZ(Minecraft.player.moveStrafing == 0.0f ? 0.5f : 0.4f);
                        break;
                    }
                    if (!((double)Minecraft.player.fallDistance > 0.725 && (double)Minecraft.player.fallDistance <= 2.5) && !((double)Minecraft.player.fallDistance > 2.5)) break;
                    Minecraft.player.multiplyMotionXZ((double)Minecraft.player.fallDistance > 1.15 ? ((double)Minecraft.player.fallDistance > 1.4 ? 0.9375f : 0.9575f) : (Minecraft.player.moveStrafing == 0.0f ? 0.9725f : 0.9675f));
                    break;
                }
                case "NCP": {
                    if (Minecraft.player.isJumping()) {
                        event.setSlowFactor(0.82);
                        break;
                    }
                    if (!Minecraft.player.onGround) break;
                    event.setSlowFactor(Minecraft.player.moveStrafing == 0.0f ? 0.62 : 0.44);
                    break;
                }
                case "Grim": {
                    if (!(MoveMeHelp.getSpeed() < 0.2) || !Minecraft.player.onGround) break;
                    event.setSlowFactor(Minecraft.player.moveStrafing == 0.0f ? (double)0.799f : (double)0.65f);
                }
            }
        }
    }

    @EventTarget
    public void onSlowLay(EventSlowLay event) {
        if (this.NoSlowLay.bValue) {
            switch (this.LaySlowBypass.currentMode) {
                case "Vanilla": {
                    event.cancel();
                    break;
                }
                case "Matrix": {
                    event.cancel();
                    if (Minecraft.player.ticksExisted % 2 == 0 && Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                        Minecraft.player.multiplyMotionXZ(Minecraft.player.moveStrafing == 0.0f ? 0.5f : 0.4f);
                        break;
                    }
                    if (!((double)Minecraft.player.fallDistance > 0.725 && (double)Minecraft.player.fallDistance <= 2.5) && !((double)Minecraft.player.fallDistance > 2.5)) break;
                    Minecraft.player.multiplyMotionXZ((double)Minecraft.player.fallDistance > 1.15 ? ((double)Minecraft.player.fallDistance > 1.4 ? 0.9375f : 0.9575f) : (Minecraft.player.moveStrafing == 0.0f ? 0.9725f : 0.9675f));
                    break;
                }
                case "NCP": {
                    if (Minecraft.player.isJumping()) {
                        event.setSlowFactor(0.82);
                        break;
                    }
                    if (!Minecraft.player.onGround) break;
                    event.setSlowFactor(Minecraft.player.moveStrafing == 0.0f ? 0.62 : 0.44);
                    break;
                }
                case "Grim": {
                    if (!(MoveMeHelp.getSpeed() < 0.2) || !Minecraft.player.onGround) break;
                    event.setSlowFactor(Minecraft.player.moveStrafing == 0.0f ? (double)0.799f : (double)0.65f);
                }
            }
        }
    }

    public static boolean stopSlowingSoul(Entity entity) {
        boolean can = false;
        if (entity instanceof EntityPlayerSP) {
            EntityPlayerSP SP = (EntityPlayerSP)entity;
            if (MoveHelper.instance.actived && MoveHelper.instance.NoSlowSoul.bValue) {
                SP.multiplyMotionXZ(SP.movementInput.jump ? 0.8f : 0.855f);
                can = true;
            }
        }
        return can;
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (this.actived && this.TrapdoorSpeed.bValue && MoveMeHelp.isMoving() && !Minecraft.player.isSneaking() && MoveMeHelp.trapdoorAdobedEntity(Minecraft.player) && !Minecraft.player.isJumping()) {
            if (Minecraft.player.onGround) {
                Minecraft.player.jump();
            } else {
                Minecraft.player.posY -= 0.015;
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.FastPilingUp.bValue) {
            AxisAlignedBB B = Minecraft.player.boundingBox;
            if (!Minecraft.player.onGround && Minecraft.player.motionY == 0.08307781780646721 && !MoveHelper.mc.world.getCollisionBoxes(Minecraft.player, B.offsetMinDown(0.25)).isEmpty() && MoveHelper.mc.world.getCollisionBoxes(Minecraft.player, new AxisAlignedBB(B.minX, B.minY, B.minZ, B.maxX, B.minY + 1.0, B.maxZ)).isEmpty()) {
                Entity.motiony = -1.0;
            }
        }
        if (this.LevitateControl.bValue) {
            boolean isLevitating = Minecraft.player.isPotionActive(Potion.getPotionById(25));
            double motionY = Minecraft.player.motionY;
            if (isLevitating) {
                motionY = Minecraft.player.isJumping() ? 0.8 - 0.08 * Math.random() : (Minecraft.player.isSneaking() ? 0.0 : motionY);
            }
            Minecraft.player.motionY = motionY;
        }
        if (this.GroundHalt.bValue && Minecraft.player.onGround && Minecraft.player.isCollidedVertically && MoveMeHelp.getSpeed() < 0.15 && !MoveMeHelp.moveKeysPressed()) {
            Minecraft.player.multiplyMotionXZ(0.45f);
        }
        if (this.NoJumpDelay.bValue) {
            Minecraft.player.jumpTicks = 0;
        }
        if (this.GmFlyStrafe.bValue && Minecraft.player.capabilities.isFlying) {
            float min = 0.23f;
            float max = 1.199f;
            float motY = (float)(Entity.Getmotiony + (double)(Minecraft.player.isJumping() ? 1 : (Minecraft.player.isSneaking() ? -1 : 0)));
            if (this.FlyStrafeMode.currentMode.equalsIgnoreCase("NCP")) {
                min = 0.6f;
                max = 1.152f;
                motY = (float)(Entity.Getmotiony + (Minecraft.player.isJumping() ? (Minecraft.player.isInWater() ? 0.61 : 0.7) : (Minecraft.player.isSneaking() ? -(Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 3.0, Minecraft.player.posZ) ? 0.5 : 2.6) : 0.0)));
            }
            double speed = MathUtils.clamp(MoveMeHelp.getSpeed() * 1.5, (double)min, (double)max);
            double speed2 = MathUtils.clamp(MoveMeHelp.getSpeed() * 1.5, (double)min, (double)max);
            MoveMeHelp.setSpeed(speed2, 0.8f);
            if (MoveMeHelp.isMoving()) {
                MoveMeHelp.setCuttingSpeed(speed / 1.06);
            }
            Minecraft.player.motionY = motY;
            Minecraft.player.motionY /= 2.0;
        }
        if (this.FastLadders.bValue) {
            if (Minecraft.player.isOnLadder()) {
                MoveMeHelp.setCuttingSpeed((Minecraft.player.ticksExisted % 2 == 0 ? 0.2498 : 0.2499) / 1.06);
                Minecraft.player.motionY = 0.0;
                double d = Minecraft.player.isJumping() ? 0.12 : (Minecraft.player.isSneaking() ? -1.0 : (Entity.motiony = Minecraft.player.ticksExisted % 2 == 0 ? 0.0032 : -0.0032));
                if (MoveHelper.mc.timer.speed == 1.0) {
                    MoveHelper.mc.timer.speed = 1.04832343;
                }
            } else if (MoveHelper.mc.timer.speed == 1.04832343) {
                MoveHelper.mc.timer.speed = 1.0;
            }
        }
        if (this.MatrixSnowFix.bValue) {
            if (BlockHelper.getBlock(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)) == Blocks.SNOW_LAYER && BlockHelper.getBlock(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)) == Blocks.SOUL_SAND) {
                this.canPress = true;
                float ex = 1.0f;
                float ex2 = 1.0f;
                Minecraft.player.jumpTicks = 0;
                if (!(MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getBlock() == Blocks.AIR && MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getBlock() == Blocks.AIR || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex2, Minecraft.player.posY - (double)ex, Minecraft.player.posZ)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ + (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - (double)ex, Minecraft.player.posZ - (double)ex2)).getMaterial() == Material.SNOW || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)Minecraft.player.posX, (double)(Minecraft.player.posY - (double)ex), (double)Minecraft.player.posZ)).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)Minecraft.player.posX, (double)(Minecraft.player.posY - (double)ex), (double)Minecraft.player.posZ)).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX - (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ - (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX + (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ + (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX - (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ + (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX + (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ - (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX + (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)Minecraft.player.posZ)).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)(Minecraft.player.posX - (double)ex2), (double)(Minecraft.player.posY - (double)ex), (double)Minecraft.player.posZ)).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)Minecraft.player.posX, (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ + (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveHelper.mc.world.getBlockState((BlockPos)new BlockPos((double)Minecraft.player.posX, (double)(Minecraft.player.posY - (double)ex), (double)(Minecraft.player.posZ - (double)ex2))).getMaterial().getMaterialMapColor().colorIndex == 7 || MoveMeHelp.isBlockAboveHead() || Minecraft.player.isCollidedHorizontally)) {
                    Minecraft.player.onGround = true;
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                }
            } else {
                this.canPress = false;
            }
            if (MoveHelper.mc.currentScreen == null || this.canPress) {
                boolean bl = MoveHelper.mc.gameSettings.keyBindJump.pressed = this.canPress || Keyboard.isKeyDown(MoveHelper.mc.gameSettings.keyBindJump.getKeyCode());
            }
        }
        if (this.StairSpeed.bValue && stairTick && Minecraft.player.onGround) {
            double prev = Minecraft.player.motionY;
            Minecraft.player.jump();
            Minecraft.player.motionY = prev;
            stairTick = false;
        }
        if (this.NoSlowDown.bValue) {
            if (this.NoSlowMode.currentMode.equalsIgnoreCase("Vanilla")) {
                if (Minecraft.player.isHandActive() && !Minecraft.player.isSprinting() && !Minecraft.player.serverSprintState && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && MoveMeHelp.getSpeed() > 0.04) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
                    Minecraft.player.setSprinting(true);
                }
            } else if (this.NoSlowMode.currentMode.equalsIgnoreCase("MatrixOld")) {
                if (!(Minecraft.player.isEating() && Minecraft.player.isBlocking() && Minecraft.player.isBowing() && Minecraft.player.isDrinking() || MoveHelper.mc.timer.speed != (double)1.094745f)) {
                    MoveHelper.mc.timer.speed = 1.0;
                }
                if (Minecraft.player.isEating() || Minecraft.player.isBlocking() || Minecraft.player.isBowing() || Minecraft.player.isDrinking()) {
                    if (MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() || MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() || Minecraft.player.isMoving()) {
                        Minecraft.player.applyEntityCollision(Minecraft.player);
                        MoveHelper.mc.timer.speed = 1.094745f;
                    }
                    if (MoveHelper.mc.gameSettings.keyBindSprint.isKeyDown() && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && !Minecraft.player.isSprinting()) {
                        Minecraft.player.setSprinting(this.actived);
                    }
                    if (!Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && !Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.31f;
                        Minecraft.player.motionZ *= (double)0.31f;
                    }
                    if (Minecraft.player.isJumping() && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && !Minecraft.player.isMoving()) {
                        Minecraft.player.motionX *= (double)0.97245f;
                        Minecraft.player.motionZ *= (double)0.97245f;
                    }
                    if (Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && !Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.425f;
                        Minecraft.player.motionZ *= (double)0.425f;
                    }
                    if (Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindForward.isKeyDown() && Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.9725f;
                        Minecraft.player.motionZ *= (double)0.9725f;
                    }
                    if (!Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() && !Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.6645f;
                        Minecraft.player.motionZ *= (double)0.6645f;
                    }
                    if (Minecraft.player.isJumping() && MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() && !Minecraft.player.isMoving()) {
                        Minecraft.player.motionX *= (double)0.9845f;
                        Minecraft.player.motionZ *= (double)0.9845f;
                    }
                    if (Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() && Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.9845f;
                        Minecraft.player.motionZ *= (double)0.9845f;
                    }
                    if (Minecraft.player.isMoving() && MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() && !Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.64f;
                        Minecraft.player.motionZ *= (double)0.64f;
                    }
                    if (Minecraft.player.isMoving() && !MoveHelper.mc.gameSettings.keyBindBack.isKeyDown() && !Minecraft.player.isJumping()) {
                        Minecraft.player.motionX *= (double)0.6645f;
                        Minecraft.player.motionZ *= (double)0.6645f;
                    }
                }
            } else if (this.NoSlowMode.currentMode.equalsIgnoreCase("MatrixLatest")) {
                boolean stop;
                boolean bl = stop = Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.capabilities.isFlying || Minecraft.player.getTicksElytraFlying() > 1 || !MoveMeHelp.isMoving();
                if (Minecraft.player.isHandActive() && Minecraft.player.getItemInUseMaxCount() > 3 && !stop) {
                    if (Minecraft.player.ticksExisted % 2 == 0 && Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                        Minecraft.player.multiplyMotionXZ(Minecraft.player.moveStrafing == 0.0f ? 0.5f : 0.4f);
                    } else if ((double)Minecraft.player.fallDistance > 0.725 && (double)Minecraft.player.fallDistance <= 2.5 || (double)Minecraft.player.fallDistance > 2.5) {
                        Minecraft.player.multiplyMotionXZ((double)Minecraft.player.fallDistance > 1.15 ? ((double)Minecraft.player.fallDistance > 1.4 ? 0.9375f : 0.9575f) : (Minecraft.player.moveStrafing == 0.0f ? 0.9725f : 0.9675f));
                    }
                }
            } else if (this.NoSlowMode.currentMode.equalsIgnoreCase("AACOld")) {
                if (Minecraft.player.isHandActive()) {
                    if (MoveHelper.mc.timer.speed == (double)1.1f) {
                        MoveHelper.mc.timer.speed = 1.0;
                    }
                    if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                        Minecraft.player.multiplyMotionXZ(0.601f);
                    }
                    if (Minecraft.player.isJumping()) {
                        MoveHelper.mc.timer.speed = 1.0;
                        if (Minecraft.player.onGround) {
                            MoveHelper.mc.timer.speed = 1.1f;
                            Minecraft.player.multiplyMotionXZ(0.45f);
                        } else {
                            Minecraft.player.jumpMovementFactor = 0.02f;
                        }
                    }
                } else if (MoveHelper.mc.timer.speed == 1.1) {
                    MoveHelper.mc.timer.speed = 1.0;
                }
            } else if (this.NoSlowMode.currentMode.equalsIgnoreCase("NCP+")) {
                boolean stop;
                boolean bypassed;
                boolean bl = bypassed = Minecraft.player.getActiveHand() == EnumHand.MAIN_HAND && this.NCPFapBypass.bValue;
                if (!(bypassed || !Minecraft.player.isHandActive() || Minecraft.player.getActiveHand() != EnumHand.MAIN_HAND || Minecraft.player.isBlocking() || Minecraft.player.getActiveHand() != EnumHand.OFF_HAND || EntityLivingBase.isMatrixDamaged || Minecraft.player.isInWater() || Minecraft.player.isInLava())) {
                    float pc = MathUtils.clamp((float)Minecraft.player.getItemInUseMaxCount() / 28.0f, 0.0f, 1.0f);
                    float noslowPercent = 1.0f - MathUtils.clamp((Minecraft.player.onGround ? 0.43f : (Entity.Getmotiony > 0.0 ? 0.57f : 0.24f)) * pc, 0.0f, 0.3f);
                    Minecraft.player.multiplyMotionXZ(noslowPercent);
                }
                if (bypassed && Minecraft.player.getItemInUseMaxCount() == 1) {
                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange((Minecraft.player.inventory.currentItem + 1) % 8));
                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
                }
                boolean bl2 = stop = Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.capabilities.isFlying || Minecraft.player.getTicksElytraFlying() > 1 || !MoveMeHelp.isMoving();
                if (Minecraft.player.isHandActive() && Minecraft.player.getItemInUseMaxCount() > 3 && !stop && Minecraft.player.ticksExisted % 2 == 0 && Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                    Minecraft.player.multiplyMotionXZ(Minecraft.player.moveStrafing == 0.0f ? 0.5f : 0.4f);
                }
            } else if (this.NoSlowMode.currentMode.equalsIgnoreCase("Grim") && Minecraft.player.isHandActive()) {
                if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                    boolean speed;
                    boolean bl = speed = Minecraft.player.getActivePotionEffect(MobEffects.SPEED) != null;
                    Minecraft.player.multiplyMotionXZ(speed ? (Minecraft.player.getItemInUseMaxCount() % 2 == 0 ? 0.7f : 0.719f) : (Minecraft.player.getItemInUseMaxCount() % 2 == 0 ? 0.85f : 0.9f));
                } else if (!this.NoJumpSlowGrim.bValue && Minecraft.player.getItemInUseMaxCount() == 32 && Minecraft.player.getActiveItemStack() != null && Minecraft.player.getActiveItemStack().getItem() instanceof ItemFood) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    Minecraft.player.multiplyMotionXZ(0.5f);
                }
            }
        }
        if (this.InWebMotion.bValue) {
            if (this.WebMotionMode.currentMode.equalsIgnoreCase("NoCollide")) {
                Minecraft.player.isInWeb = false;
            } else if (this.WebMotionMode.currentMode.equalsIgnoreCase("Custom")) {
                if (Minecraft.player.isInWeb) {
                    Minecraft.player.jumpMovementFactor = this.WebSpeedXZ.fValue;
                    Minecraft.player.motionY = 0.0;
                    if (MoveHelper.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Minecraft.player.motionY += (double)(this.WebSpeedYPlus.fValue * 4.0f - 0.001f);
                    }
                    if (MoveHelper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        Minecraft.player.motionY -= (double)(this.WebSpeedYMinus.fValue * 4.0f - 0.001f);
                    }
                }
            } else if (this.WebMotionMode.currentMode.equalsIgnoreCase("Matrix")) {
                float ex = 0.1f;
                float ex2 = 0.01f;
                float ex3 = Minecraft.player.width / 2.0f - 0.005f;
                double x = Minecraft.player.posX;
                double y = Minecraft.player.posY;
                double z = Minecraft.player.posZ;
                if (Minecraft.player.isInWeb) {
                    Minecraft.player.motionY = 0.0;
                    Minecraft.player.jumpMovementFactor = 0.49f;
                    if (MoveHelper.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Minecraft.player.motionY += (double)1.999f;
                    } else if (MoveHelper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        Minecraft.player.motionY -= (double)1.999f;
                    }
                } else if (!MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y + (double)ex2, z, Blocks.WEB) && MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y - (double)ex, z, Blocks.WEB) && !Minecraft.player.isCollidedHorizontally && !Minecraft.player.onGround) {
                    if (this.WebZoom.bValue && Minecraft.player.motionY == -0.0784000015258789 && MoveMeHelp.getSpeed() < 0.2499) {
                        MoveMeHelp.setSpeed(1.484);
                    }
                    Minecraft.player.motionY = -0.06f;
                }
            }
        }
    }

    @Override
    public void onUpdateMovement() {
        if (this.AnchoorHole.bValue) {
            holeTick = false;
            float w = Minecraft.player.width / 2.0f;
            double x = Minecraft.player.posX;
            double y = Minecraft.player.posY + (Minecraft.player.lastTickPosY - Minecraft.player.posY) / 2.0;
            double z = Minecraft.player.posZ;
            if ((this.isHoledPosFull(new BlockPos(x, y, z)) || this.isHoledPosFull(new BlockPos(x, y - 1.0, z)) || this.isHoledPosFull(new BlockPos(x, y - 1.3, z))) && MoveHelper.getBlockFullWithExpand(w, x, y - 1.0, z, Blocks.AIR) && MoveHelper.getBlockFullWithExpand(w, x, y - 1.3, z, Blocks.AIR)) {
                Minecraft.player.jumpMovementFactor = 0.0f;
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(0.0);
                if (this.Step.bValue && this.ReverseStep.bValue) {
                    Entity.motiony = -3.0;
                    holeTicks = 0;
                } else {
                    holeTicks = -10;
                }
                holeTick = true;
            }
        }
        if (this.Step.bValue && this.StepUpMode.currentMode.equalsIgnoreCase("Matrix") && !Minecraft.player.isJumping() && Minecraft.player.isCollidedHorizontally && MoveMeHelp.isMoving()) {
            double moveYaw = MoveMeHelp.getMotionYaw();
            double offsetY = 1.001335979112147;
            double extendXZ = 1.0E-5;
            double sin = -Math.sin(Math.toRadians(moveYaw)) * extendXZ;
            double cos = Math.cos(Math.toRadians(moveYaw)) * extendXZ;
            AxisAlignedBB aabb = Minecraft.player.getEntityBoundingBox().offset(0.0, -0.42, 0.0);
            AxisAlignedBB aabbOff = Minecraft.player.getEntityBoundingBox().offset(sin, offsetY, cos);
            if (MoveHelper.mc.world.getCollisionBoxes(Minecraft.player, aabbOff).isEmpty() && !MoveHelper.mc.world.getCollisionBoxes(Minecraft.player, aabb).isEmpty()) {
                Minecraft.player.onGround = true;
                Minecraft.player.jump();
            }
        }
        if (this.Step.bValue && this.ReverseStep.bValue && Minecraft.player.onGround && Minecraft.player.isCollidedVertically && Minecraft.player.motionY < 0.0 && !Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 0.6, Minecraft.player.posZ) && OnFalling.getDistanceTofall() < 4.0 && !Minecraft.player.isJumping()) {
            Entity.motiony = -3.0;
            holeTicks = 0;
        }
        if (this.Step.bValue && this.StepUpMode.currentMode.equalsIgnoreCase("Vanilla")) {
            if (!Minecraft.player.isSneaking() && MoveMeHelp.moveKeysPressed()) {
                ++holeTicks;
            }
            if (holeTicks > 5 && !Minecraft.player.isSneaking() && MoveMeHelp.moveKeysPressed()) {
                Minecraft.player.stepHeight = 2.000121f;
            }
        } else if (Minecraft.player.stepHeight == 2.000121f) {
            Minecraft.player.stepHeight = 0.6f;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            holeTicks = 0;
            holeTick = false;
            if (Minecraft.player.stepHeight == 2.000121f) {
                Minecraft.player.stepHeight = 0.6f;
            }
        }
        super.onToggled(actived);
    }

    private Block getBlock(BlockPos position) {
        if (MoveHelper.mc.world != null) {
            return MoveHelper.mc.world.getBlockState(position).getBlock();
        }
        return Blocks.AIR;
    }

    private boolean isBedrock(BlockPos position) {
        Block state = Blocks.BEDROCK;
        return this.getBlock(position) == state;
    }

    private boolean isObsidian(BlockPos position) {
        Block state = Blocks.OBSIDIAN;
        return this.getBlock(position) == state;
    }

    private boolean isCurrentBlock(BlockPos position) {
        return this.isBedrock(position) || this.isObsidian(position);
    }

    private boolean isHoled(BlockPos position) {
        Block state = Blocks.AIR;
        return this.isCurrentBlock(position.add(1, 0, 0)) && this.isCurrentBlock(position.add(-1, 0, 0)) && this.isCurrentBlock(position.add(0, 0, 1)) && this.isCurrentBlock(position.add(0, 0, -1)) && Speed.posBlock(position.add(0, -1, 0).getX(), position.add(0, -1, 0).getY(), position.add(0, -1, 0).getZ()) && this.getBlock(position) == state && this.getBlock(position.add(0, 1, 0)) == state && this.getBlock(position.add(0, 2, 0)) == state;
    }

    private boolean isHoledPosFull(BlockPos pos) {
        return this.isHoled(new BlockPos(pos.getX(), pos.getY(), pos.getZ())) && !Speed.posBlock(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean getBlockWithExpand(float expand, double x, double y, double z, Block block) {
        return MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z + (double)expand)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z - (double)expand)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z - (double)expand)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z + (double)expand)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z + (double)expand)).getBlock() == block || MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z - (double)expand)).getBlock() == block;
    }

    public static boolean getBlockFullWithExpand(float expand, double x, double y, double z, Block block) {
        return MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z + (double)expand)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z - (double)expand)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z - (double)expand)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z + (double)expand)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z + (double)expand)).getBlock() == block && MoveHelper.mc.world.getBlockState(new BlockPos(x, y, z - (double)expand)).getBlock() == block;
    }

    static {
        stairTick = false;
        holeTick = false;
        holeTicks = 0;
    }
}

