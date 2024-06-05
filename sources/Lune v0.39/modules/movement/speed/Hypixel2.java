package me.superskidder.lune.modules.movement.speed;

import me.superskidder.lune.events.EventMove;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;


public class Hypixel2{
    private int stage;
    private int hops;
    private double movementSpeed;
    private double distance;
    public Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        mc.thePlayer.motionZ *= 0.0D;
        mc.thePlayer.motionX *= 0.0D;
        mc.timer.timerSpeed = 1.0F;
        this.distance = 0.0D;
        this.stage = 0;
        this.hops = 1;
    }

    public void onDisable() {
        mc.thePlayer.stepHeight = 0.625F;
        mc.timer.timerSpeed = 1.0F;
    }

    public void onMotion() {
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    public void onUpdate() {
    }

    public boolean canZoom() {
        return (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround;
    }

    private double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0D + 0.2D * (double) (amplifier + 1);
        }

        return baseSpeed;
    }

    private void setMotion(EventMove em, double speed) {
        double forward = (double) mc.thePlayer.movementInput.moveForward;
        double strafe = (double) mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            em.setX(0.0D);
            em.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float) (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float) (forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            em.setX(forward * speed * Math.cos(Math.toRadians((double) (yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double) (yaw + 90.0F))));
            em.setZ(forward * speed * Math.sin(Math.toRadians((double) (yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double) (yaw + 90.0F))));
            if (forward == 0.0D && strafe == 0.0D) {
                em.setX(0.0D);
                em.setZ(0.0D);
            }
        }

    }

    public void onMove(EventMove e) {


        double lnk = 0.4068888688697815D;
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            lnk += (double) ((float) (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (!this.canZoom() || this.stage != 2 || mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
            if (this.stage == 3) {
                double var6 = (0.66D + this.movementSpeed * 0.07D) * (this.distance - this.defaultSpeed());
                this.movementSpeed = this.distance - var6;
            } else {
                if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, lnk, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
                    this.stage = PlayerUtil.isMoving() ? 1 : 0;
                    mc.timer.timerSpeed = 1.0F;
                    ++this.hops;
                }

                this.movementSpeed = this.distance - this.distance / 159.0D;
            }
        } else {
            mc.thePlayer.motionY = lnk;
            e.setY(mc.thePlayer.motionY);
            BlockPos diff = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            boolean needslow = mc.theWorld.getBlockState(diff).getBlock() instanceof BlockStairs;
            if (mc.thePlayer.isInWater()) {
                this.movementSpeed *= 1.07D;
            } else if (mc.thePlayer.isInLava()) {
                this.movementSpeed *= 0.33D;
            } else {
                this.movementSpeed *= needslow ? 1.0D : 1.75D;
            }
        }

        this.movementSpeed = Math.max(this.movementSpeed, this.defaultSpeed());
        this.setMotion(e, this.movementSpeed);
        ++this.stage;

    }
}
