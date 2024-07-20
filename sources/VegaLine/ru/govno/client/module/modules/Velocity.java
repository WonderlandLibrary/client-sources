/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Random;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class Velocity
extends Module {
    public static Velocity get;
    Random RANDOM = new Random();
    public Settings OnKnockBack;
    public Settings KnockType;
    public Settings KnockFlagPauseMS;
    public Settings IgnoreKnockChance;
    public Settings NoBlockPush;
    public Settings NoWaterPush;
    public Settings NoEntityPush;
    public Settings MaxReducement;
    public Settings ReduceStopSprint;
    public Settings ReduceMS;
    public static boolean pass;
    static int cancelTransactionCounter;
    TimerHelper lastPauseTime = new TimerHelper();
    private static float[] motion;
    private static boolean moveBoost;
    private static boolean motionChange;
    public static Vec3d lastVelocityMotion;
    private boolean sendGrim;
    private boolean reduceStatus;
    private final TimerHelper reduceTimer = new TimerHelper();
    public static boolean stopGrim;

    public Velocity() {
        super("Velocity", 0, Module.Category.COMBAT);
        get = this;
        this.RANDOM.setSeed(1231231234L);
        this.OnKnockBack = new Settings("OnKnockBack", true, (Module)this);
        this.settings.add(this.OnKnockBack);
        this.KnockType = new Settings("KnockType", "Cancel", this, new String[]{"Cancel", "Stationary", "MoveBoost", "GrimAc", "AAC5.2.0", "Reduce"}, () -> this.OnKnockBack.bValue);
        this.settings.add(this.KnockType);
        this.MaxReducement = new Settings("MaxReducement", 0.7f, 1.0f, 0.0f, this, () -> this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("Reduce"));
        this.settings.add(this.MaxReducement);
        this.ReduceMS = new Settings("ReduceMS", 800.0f, 1000.0f, 50.0f, this, () -> this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("Reduce"));
        this.settings.add(this.ReduceMS);
        this.ReduceStopSprint = new Settings("ReduceStopSprint", true, (Module)this, () -> this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("Reduce"));
        this.settings.add(this.ReduceStopSprint);
        this.KnockFlagPauseMS = new Settings("KnockFlagPauseMS", 3000.0f, 8000.0f, 0.0f, this, () -> this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("GrimAc"));
        this.settings.add(this.KnockFlagPauseMS);
        this.IgnoreKnockChance = new Settings("IgnoreKnockChance", 80.0f, 100.0f, 0.0f, this, () -> this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("GrimAc"));
        this.settings.add(this.IgnoreKnockChance);
        this.NoBlockPush = new Settings("NoBlockPush", true, (Module)this);
        this.settings.add(this.NoBlockPush);
        this.NoWaterPush = new Settings("NoWaterPush", true, (Module)this);
        this.settings.add(this.NoWaterPush);
        this.NoEntityPush = new Settings("NoEntityPush", true, (Module)this);
        this.settings.add(this.NoEntityPush);
    }

    private static double getVelocitySpeed(Vec3d velocityMotion) {
        double xMotion = velocityMotion.xCoord;
        double zMotion = velocityMotion.zCoord;
        return Math.sqrt(xMotion * xMotion + zMotion * zMotion);
    }

    private static void addSpeedAtVelocity(Entity entityFor, Vec3d velocityMotion, boolean[] abuses) {
        if (abuses[1]) {
            motion = new float[]{(float)velocityMotion.xCoord / 9.0f, (float)velocityMotion.zCoord / 9.0f};
            moveBoost = Velocity.getVelocitySpeed(velocityMotion) / 8.0 <= 0.23;
            motionChange = false;
            return;
        }
        if (abuses[0]) {
            double speedAdd = Velocity.getVelocitySpeed(velocityMotion);
            float moveYaw = MoveMeHelp.moveYaw(entityFor.rotationYaw);
            double sin = -Math.sin(Math.toRadians(moveYaw)) * speedAdd;
            double cos = Math.cos(Math.toRadians(moveYaw)) * speedAdd;
            motion = new float[]{(float)sin, (float)cos};
            moveBoost = Math.hypot(motion[0], motion[1]) * 20.0 > MoveMeHelp.getCuttingSpeed();
            motionChange = true;
        }
    }

    private static void sendAAC520_BP() {
        mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Double.MAX_VALUE, Minecraft.player.posZ, true));
    }

    private static void sendGrim_BP() {
        BlockPos pos = new BlockPos(Minecraft.player.getPositionVector());
        mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }

    @EventTarget
    public void onMove(EventMove2 move) {
        if (moveBoost) {
            if (!motionChange) {
                move.motion().xCoord = motion[0];
                move.motion().zCoord = motion[1];
                Entity.motionx = move.motion().xCoord;
                Entity.motionz = move.motion().zCoord;
                if (!MoveMeHelp.moveKeysPressed()) {
                    Minecraft.player.addVelocity(Entity.motionx / 2.93, 0.0, Entity.motionz / 2.93);
                }
                moveBoost = false;
                return;
            }
            double speed = Velocity.getVelocitySpeed(new Vec3d(motion[0], 0.0, motion[1]));
            double yaw = Math.toDegrees(Math.atan2(motion[1], motion[0]));
            yaw = yaw < 0.0 ? yaw + 360.0 : yaw;
            double xB = -Math.sin(Math.toRadians(yaw - 90.0)) * speed;
            double zB = Math.cos(Math.toRadians(yaw - 90.0)) * speed;
            Minecraft.player.setVelocity(xB, Minecraft.player.motionY, zB);
            motionChange = false;
            move.motion().xCoord = Entity.Getmotionx;
            move.motion().zCoord = Entity.Getmotionz;
            moveBoost = false;
        }
    }

    public static boolean[] canAbuseVelocity(Entity entityFor, Vec3d velocityMotion) {
        boolean isSelf = entityFor instanceof EntityPlayerSP;
        boolean canMoveBoostAbuse = false;
        boolean canStationaryAbuse = false;
        boolean canAAC520Abuse = false;
        boolean canGrimNewCancel = false;
        if (isSelf) {
            String mode = Velocity.get.KnockType.getMode();
            boolean abuseMoveBoost = mode.equalsIgnoreCase("MoveBoost");
            boolean abuseMoveStationary = mode.equalsIgnoreCase("Stationary");
            boolean doCancelTrans = mode.equalsIgnoreCase("GrimAc");
            boolean abuseAAC520 = mode.equalsIgnoreCase("Stationary");
            lastVelocityMotion = velocityMotion;
            canMoveBoostAbuse = abuseMoveBoost && Velocity.getVelocitySpeed(velocityMotion) > 0.026 && MoveMeHelp.isMoving() && MoveMeHelp.getDirDiffOfMotions(velocityMotion.xCoord, velocityMotion.zCoord) < 20.0;
            canStationaryAbuse = abuseMoveStationary && MoveMeHelp.getSpeed() < 0.146;
            canAAC520Abuse = abuseAAC520 && !Minecraft.player.isCollidedHorizontally;
        }
        return new boolean[]{isSelf && canMoveBoostAbuse, isSelf && canStationaryAbuse, isSelf && canAAC520Abuse};
    }

    public static void abuseVelocity(Entity entityFor, Vec3d velocityMotion) {
        boolean[] abuse = Velocity.canAbuseVelocity(entityFor, velocityMotion);
        if (abuse[0] || abuse[1]) {
            Velocity.addSpeedAtVelocity(entityFor, velocityMotion, abuse);
        }
        if (abuse[2]) {
            Velocity.sendAAC520_BP();
        }
    }

    @Override
    public void onUpdate() {
        if (this.OnKnockBack.bValue) {
            boolean bl = pass = Velocity.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() instanceof BlockBasePressurePlate || Velocity.isGrim() || this.KnockType.currentMode.equalsIgnoreCase("Reduce");
        }
        if (this.NoEntityPush.bValue) {
            Minecraft.player.entityCollisionReduction = 1.0f;
        }
        if (Velocity.isGrim()) {
            --cancelTransactionCounter;
            if (stopGrim && this.lastPauseTime.hasReached(this.KnockFlagPauseMS.fValue)) {
                stopGrim = false;
            }
            if (this.sendGrim) {
                Velocity.sendGrim_BP();
                this.sendGrim = false;
            }
        } else {
            if (cancelTransactionCounter != 5) {
                cancelTransactionCounter = -5;
            }
            if (stopGrim) {
                stopGrim = false;
            }
            if (this.sendGrim) {
                this.sendGrim = false;
            }
            if (this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("Reduce")) {
                if (!this.reduceStatus) {
                    this.reduceTimer.reset();
                } else if (this.reduceTimer.hasReached(this.ReduceMS.fValue)) {
                    this.reduceStatus = false;
                }
                if (this.reduceStatus) {
                    float reduce = this.MaxReducement.fValue;
                    Minecraft.player.multiplyMotionXZ(reduce);
                    if (this.ReduceStopSprint.bValue) {
                        Minecraft.player.setFlag(3, false);
                        Minecraft.player.setSprinting(false);
                    }
                }
            }
        }
    }

    static boolean isGrim() {
        return Velocity.get.actived && Velocity.get.OnKnockBack.bValue && Velocity.get.KnockType.currentMode.equalsIgnoreCase("GrimAc");
    }

    @EventTarget
    public void onReceivePackets(EventReceivePacket eventPacket) {
        SPacketEntityVelocity velocity;
        Packet packet = eventPacket.getPacket();
        if (packet instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook look = (SPacketPlayerPosLook)packet;
            if (cancelTransactionCounter > 0 && Minecraft.player.getDistance(look.getX(), look.getY(), look.getZ()) < 1.0) {
                stopGrim = true;
                this.lastPauseTime.reset();
            }
        }
        if (!stopGrim && (packet = eventPacket.getPacket()) instanceof SPacketEntityVelocity) {
            velocity = (SPacketEntityVelocity)packet;
            if (Minecraft.player != null && velocity.getEntityID() == Minecraft.player.getEntityId() && Velocity.isGrim() && Velocity.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.getEntityBoundingBox().addExpandXZ(0.15)).isEmpty()) {
                if (cancelTransactionCounter > -5 || this.RANDOM.nextInt(100) > (int)this.currentFloatValue("IgnoreKnockChance")) {
                    stopGrim = true;
                    return;
                }
                cancelTransactionCounter = 2;
                this.sendGrim = true;
                eventPacket.cancel();
            }
        }
        if (this.OnKnockBack.bValue && this.KnockType.currentMode.equalsIgnoreCase("Reduce") && this.MaxReducement.fValue != 0.0f && (packet = eventPacket.getPacket()) instanceof SPacketEntityVelocity) {
            double veloSpeed;
            velocity = (SPacketEntityVelocity)packet;
            if (Minecraft.player != null && velocity.getEntityID() == Minecraft.player.getEntityId() && (veloSpeed = Velocity.getVelocitySpeed(new Vec3d((double)velocity.motionX / 8000.0, (double)velocity.motionY / 8000.0, (double)velocity.motionZ / 8000.0))) < 3.6 && veloSpeed > 0.06) {
                this.reduceStatus = true;
            }
        }
    }

    @Override
    public void onToggled(boolean actived) {
        cancelTransactionCounter = 0;
        stopGrim = false;
        super.onToggled(actived);
    }

    static {
        lastVelocityMotion = Vec3d.ZERO;
        stopGrim = false;
    }
}

