package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import java.math.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;

public class Hypixel extends Mode<Speed>
{
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public Hypixel(final Speed parent) {
        super(parent, "Hypixel");
    }
    
    public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public boolean isBlockUnder(final Material blockMaterial) {
        return this.mc.theWorld.getBlockState(this.underPlayer()).getBlock().getMaterial() == blockMaterial;
    }
    
    public BlockPos underPlayer() {
        return new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.getEntityBoundingBox().minY - 1.0, this.mc.thePlayer.posZ);
    }
    
    @Subscriber
    public void onMove(final EventMove event) {
        if (this.round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == this.round(0.138, 3)) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            --thePlayer.motionY;
            event.y -= 0.0931;
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            thePlayer2.posY -= 0.0931;
            this.moveSpeed *= 1.25;
        }
        Label_0259: {
            if (this.stage == 2) {
                if (this.mc.thePlayer.moveForward != 0.0f) {
                    break Label_0259;
                }
                if (this.mc.thePlayer.moveStrafing != 0.0f) {
                    break Label_0259;
                }
            }
            if (this.stage == 3) {
                this.moveSpeed = this.getBaseMoveSpeed() * 1.6799999475479126;
            }
            else {
                if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                    this.stage = 1;
                }
                this.moveSpeed = this.getBaseMoveSpeed() * 0.800000011920929;
                this.moveSpeed = this.lastDist - this.lastDist / 160.0;
            }
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
            final double motionX = thePlayer3.motionX;
            final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
            final double motionZ = thePlayer4.motionZ * 0.0;
            thePlayer4.motionZ = motionZ;
            thePlayer3.motionX = motionX * motionZ;
            event.x = 0.0;
            event.z = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz) * 0.99;
        event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx) * 0.99;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
            final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
            final double motionX2 = thePlayer5.motionX;
            final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
            final double motionZ2 = thePlayer6.motionZ * 0.0;
            thePlayer6.motionZ = motionZ2;
            thePlayer5.motionX = motionX2 * motionZ2;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        ++this.stage;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate e) {
        if (this.mc.thePlayer.isMoving()) {
            final double[] xd = { 0.368, 0.55, 0.249, 0.171, 0.427 };
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
                this.moveSpeed *= 2.0;
                final Timer timer = this.mc.timer;
                Timer.timerSpeed = 1.15f + (float)xd[MiscellaneousUtil.getRandom(xd.length - 1)];
            }
            else {
                final Timer timer2 = this.mc.timer;
                Timer.timerSpeed = ((this.mc.thePlayer.motionY <= -0.009999999776482582 && !this.isBlockUnder(Material.air)) ? 1.046f : 1.1f);
            }
        }
        final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2583000063896179;
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.1 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    @Override
    public void onEnable() {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionX *= 0.0;
        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        thePlayer2.motionZ *= 0.0;
    }
    
    @Override
    public void onDisable() {
        this.moveSpeed = 0.0;
        this.lastDist = 0.0;
        this.mc.thePlayer.setSpeed(0.0);
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionX *= 0.0;
        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        thePlayer2.motionZ *= 0.0;
    }
}
