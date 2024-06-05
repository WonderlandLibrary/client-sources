/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Module.Modules.Blatant;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.EventStep;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.PlayerUtil;
import gq.vapu.czfclient.Util.TimeHelper;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.Timer;
import net.minecraft.util.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Speed
        extends Module {
    public static Mode<Enum> mode = new Mode("Mode", "mode", SpeedMode.values(), SpeedMode.AAC5Hop);
    public static int stage;
    public static int aacCount;
    public static TimeHelper timer2 = new TimeHelper();
    public boolean shouldslow = false;
    //private Numbers<Double> CutomHigh = new Numbers<Double>("Custom High", "Po", 0.41, 0.0, 10.0, 10.0);
    double movementSpeed;
    int level = 1;
    double down;
    boolean collided = false;
    boolean lessSlow;
    TimerUtil lastCheck = new TimerUtil();
    double less;
    double stair;
    boolean iscolod;
    boolean isJump;
    int stoptick;
    boolean isstep = false;
    private double distance;
    private double lastDist;
    private int tick;
    private List<AxisAlignedBB> collidingList;
    private double speed;
    private final TimerUtil timer = new TimerUtil();

    public Speed() {
        super("Speed", new String[]{"zoom"}, ModuleType.Blatant);
        this.setColor(new Color(99, 248, 91).getRGB());
        this.addValues(mode);
    }

    public static int getJumpEffect() {
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump))
            return Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        else
            return 0;
    }

    @Override
    public void onEnable() {
        boolean player = mc.thePlayer == null;
        this.collided = !player && mc.thePlayer.isCollidedHorizontally;
        this.lessSlow = false;
        if (mc.thePlayer != null) {
            this.speed = defaultSpeed();
        }
        this.less = 0.0;
        this.lastDist = 0.0;
        stage = 2;
        Timer.timerSpeed = 1.0f;
    }

    @Override
    public void onDisable() {
        speed = PlayerUtil.getBaseMoveSpeed();
        level = 0;
        Timer.timerSpeed = 1.0f;
        this.tick = 0;
        aacCount = 0;
    }

    @EventHandler
    public void onMotion() {
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    public void setSpeed(double speed) {
        mc.thePlayer.motionX = -Math.sin(this.getDirection()) * speed;
        mc.thePlayer.motionZ = Math.cos(this.getDirection()) * speed;
    }

    public float getDirection() {
        float yaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= 0.017453292f;
    }

    private boolean canZoom() {
        return mc.thePlayer.moving() && mc.thePlayer.onGround;
    }

    @EventHandler
    public void onStep(EventStep e) {
        isstep = false;
    }

    @EventHandler
    private void onMove(EventMove e) {

        if (mode.getValue() == SpeedMode.AAC5Hop) {
            if (!mc.thePlayer.isMoving())
                return;
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                //mc.thePlayer.speedInAir = 0.0201;
                Timer.timerSpeed = 1.0F;
            }
            if (mc.thePlayer.fallDistance > 0.7 && mc.thePlayer.fallDistance < 1.3) {
                //mc.thePlayer.speedInAir = 0.02;
                Timer.timerSpeed = 2F;
            }
        }
        if (mode.getValue() == SpeedMode.Hmxix) {
//            mc.thePlayer.motionX = 0.2789;
//            mc.thePlayer.motionZ = 0.2789;
            if (this.canZoom() && stage == 1) {
                this.movementSpeed = 1.66 * MathUtil.getBaseMovementSpeed() - 0.01;
                //this.mc.timer.timerSpeed = 1.15f;
            } else if (this.canZoom() && stage == 2) {
                mc.thePlayer.motionY = 0.3999;
                EventMove.setY(0.3999);
                //this.movementSpeed *= 1.68;
                Timer.timerSpeed = 2.0f;
            } else if (stage == 3) {
                double difference = 0.66 * (this.distance - MathUtil.getBaseMovementSpeed());
                PlayerUtil.strafe(PlayerUtil.getBaseMoveSpeed() * 2);
                //this.movementSpeed = this.distance - difference;
                //this.mc.timer.timerSpeed = 1.1f;
            } else {
                List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                if (collidingList.size() > 0 || mc.thePlayer.isCollidedVertically && stage > 0) {
                    stage = mc.thePlayer.moving() ? 1 : 0;
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            //this.mc.thePlayer.setMoveSpeed(e, this.movementSpeed);
            PlayerUtil.strafe(PlayerUtil.getBaseMoveSpeed() * 2);
            if (mc.thePlayer.moving()) {
                ++stage;
            }
        }
        if (mode.getValue() == SpeedMode.Test) {
//            mc.thePlayer.motionX = 0.2789;
//            mc.thePlayer.motionZ = 0.2789;
            if (this.canZoom() && stage == 1 && new Random().nextBoolean()) {
                this.movementSpeed = 1.66 * MathUtil.getBaseMovementSpeed() - 0.01;
                //this.mc.timer.timerSpeed = 1.15f;
            } else if (this.canZoom() && stage == 2) {
                mc.thePlayer.motionY = 0.379;
                EventMove.setY(0.3799);
                //this.movementSpeed *= 1.68;
                //this.mc.timer.timerSpeed = 2.0f;
            } else if (stage == 3) {
                double difference = 0.66 * (this.distance - MathUtil.getBaseMovementSpeed());
                PlayerUtil.strafe(PlayerUtil.getBaseMoveSpeed() * 1.000002);
                //this.movementSpeed = this.distance - difference;
                //this.mc.timer.timerSpeed = 1.7f;
            } else {
                List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                if (collidingList.size() > 0 || mc.thePlayer.isCollidedVertically && stage > 0) {
                    stage = mc.thePlayer.moving() ? 1 : 0;
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            //this.mc.thePlayer.setMoveSpeed(e, this.movementSpeed);
            PlayerUtil.strafe(PlayerUtil.getBaseMoveSpeed() * 1.000002);
            if (mc.thePlayer.moving()) {
                ++stage;
            }
        }


        this.setSuffix(mode.getValue());

    }

    @EventHandler
    public void onPacket(EventPacketSend e) {
        final Packet<?> packet = EventPacketSend.getPacket();
        final C03PacketPlayer playerPacket = (C03PacketPlayer) packet;
        playerPacket.onGround = true;
    }

    private boolean checksoulsand() {
        return issoulsand(0, 0);
    }

    private boolean issoulsand(int X, int Z) {
        if (mc.thePlayer.posY < 0.0) {
        }
        for (int off = 0; off < (int) mc.thePlayer.posY + 2; off += 2) {
            Block block = mc.theWorld.getBlockState(new BlockPos(X, -off, Z)).getBlock();
            if (!Block.blockRegistry.equals("soul_sand") || !Block.blockRegistry.equals("soul_sand"))
                return false;
        }
        return true;
    }

    private double getHypixelBest(double speed, int T) {
        double base = PlayerUtil.getBaseMoveSpeed();
        boolean slow = false;

        if (T == 1) {
            speed = 0.025;
        } else if (mc.thePlayer.onGround && PlayerUtil.isMoving() && T == 2) {
            speed *= 2.149;
        } else if (T == 3) {
            double str = 0.7095;
            double fe = 1.0E-18;
            double strafe = str * (lastDist - base);
            speed = lastDist - (strafe + fe);
            iscolod = true;
        } else {
            if (T == 2 && mc.thePlayer.fallDistance > 0.0) {
                slow = true;
            }
            level = 1;
            speed = lastDist - lastDist / 159.0;
        }

        speed = Math.max(speed - (slow ? (lastDist * speed) * 0.0149336 : (0.0049336 * lastDist)), base);
        return speed;
    }

    private boolean isInLiquid() {
        if (mc.thePlayer == null) {
            return false;
        }
        int x2 = MathHelper.floor_double(mc.thePlayer.boundingBox.minX);
        while (x2 < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1) {
            int z2 = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ);
            while (z2 < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1) {
                BlockPos pos = new BlockPos(x2, (int) mc.thePlayer.boundingBox.minY, z2);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    return block instanceof BlockLiquid;
                }
                ++z2;
            }
            ++x2;
        }
        return false;
    }

    public boolean isMoving2() {
        Minecraft.getMinecraft();
        if (mc.thePlayer.moveForward == 0.0f) {
            return mc.thePlayer.moveStrafing != 0.0f;
        }
        return true;
    }

    public boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    private double defaultSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return baseSpeed;
    }

    private void setMotion(EventMove em2, double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            em2.setX(0.0);
            em2.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float) (forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float) (forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            em2.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            em2.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    public int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;

    }


    private double getHypixelSpeed(int stage) {
        double value = defaultSpeed() + 0.028 * (double) getSpeedEffect() + (double) getSpeedEffect() / 15.0;
        double firstvalue = 0.4145 + (double) getSpeedEffect() / 12.5;
        double decr = (double) stage / 500.0 * 2.0;
        if (stage == 0) {
            if (this.timer.delay(300.0f)) {
                this.timer.reset();
            }
            if (!this.lastCheck.delay(500.0f)) {
                if (!this.shouldslow) {
                    this.shouldslow = true;
                }
            } else if (this.shouldslow) {
                this.shouldslow = false;
            }
            value = 0.64 + ((double) getSpeedEffect() + 0.028 * (double) getSpeedEffect()) * 0.134;
        } else if (stage == 1) {
            value = firstvalue;
        } else if (stage >= 2) {
            value = firstvalue - decr;
        }
        if (this.shouldslow || !this.lastCheck.delay(500.0f) || this.collided) {
            value = 0.2;
            if (stage == 0) {
                value = 0.0;
            }
        }
        return Math.max(value, this.shouldslow ? value : defaultSpeed() + 0.028 * (double) getSpeedEffect());
    }

    public enum SpeedMode {
        AAC5Hop,
        Hmxix,
        Test
    }

}

