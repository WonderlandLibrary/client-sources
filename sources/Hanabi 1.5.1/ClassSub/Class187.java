package ClassSub;

import net.minecraft.client.*;
import cn.Hanabi.events.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.World.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class Class187
{
    public boolean shouldslow;
    Minecraft mc;
    boolean collided;
    boolean lessSlow;
    private int stage;
    private int stageOG;
    private double moveSpeed;
    private double lastDist;
    double less;
    double stair;
    private double speed;
    private double speedvalue;
    Class205 timer;
    Class205 lastCheck;
    
    
    public Class187() {
        this.shouldslow = false;
        this.mc = Minecraft.getMinecraft();
        this.stage = 1;
        this.stageOG = 1;
        this.speed = 0.07999999821186066;
        this.timer = new Class205();
        this.lastCheck = new Class205();
    }
    
    public void onPullback(final EventPullback eventPullback) {
        this.stage = -4;
    }
    
    public void onUpdate(final EventUpdate eventUpdate) {
        this.lastDist = Math.sqrt((this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ));
    }
    
    private double getHypixelSpeed(final int n) {
        double n2 = defaultSpeed() + 0.028 * this.getSpeedEffect() + this.getSpeedEffect() / 15.0;
        final double n3 = 0.4145 + this.getSpeedEffect() / 12.5;
        final double n4 = 0.4045 + this.getSpeedEffect() / 12.5;
        final double n5 = n / 500.0 * 3.0;
        if (n == 0) {
            if (this.timer.isDelayComplete(300L)) {
                this.timer.reset();
            }
            if (!this.lastCheck.isDelayComplete(500L)) {
                if (!this.shouldslow) {
                    this.shouldslow = true;
                }
            }
            else if (this.shouldslow) {
                this.shouldslow = false;
            }
            n2 = 0.64 + (this.getSpeedEffect() + 0.028 * this.getSpeedEffect()) * 0.134;
        }
        else if (n == 1) {
            n2 = n3;
        }
        else if (n == 2) {
            if (Class211.getTimer().timerSpeed == 1.354f) {}
            n2 = n4;
        }
        else if (n >= 3) {
            if (Class211.getTimer().timerSpeed == 1.254f) {}
            n2 = n4 - n5;
        }
        if (this.shouldslow || !this.lastCheck.isDelayComplete(500L) || this.collided) {
            n2 = 0.2;
            if (n == 0) {
                n2 = 0.0;
            }
        }
        return Math.max(n2, this.shouldslow ? n2 : (defaultSpeed() + 0.028 * this.getSpeedEffect()));
    }
    
    public void onMove(final EventMove eventMove) {
        if (this.mc.thePlayer.isCollidedHorizontally) {
            this.collided = true;
        }
        if (this.collided) {
            Class211.getTimer().timerSpeed = 1.0f;
            this.stage = -1;
        }
        if (this.stair > 0.0) {
            this.stair -= 0.25;
        }
        this.less -= ((this.less > 1.0) ? 0.12 : 0.11);
        if (this.less < 0.0) {
            this.less = 0.0;
        }
        if (!this.isInLiquid() && this.isOnGround(0.01) && this.isMoving2()) {
            this.collided = this.mc.thePlayer.isCollidedHorizontally;
            if (this.stage >= 0 || this.collided) {
                this.stage = 0;
                final double motionY = 0.4086666 + this.getJumpEffect() * 0.1;
                if (this.stair == 0.0) {
                    this.mc.thePlayer.jump();
                    eventMove.setY(this.mc.thePlayer.motionY = motionY);
                }
                ++this.less;
                if (this.less > 1.0 && !this.lessSlow) {
                    this.lessSlow = true;
                }
                else {
                    this.lessSlow = false;
                }
                if (this.less > 1.12) {
                    this.less = 1.12;
                }
            }
        }
        this.speed = this.getHypixelSpeed(this.stage) + 0.0331;
        this.speed *= 0.91;
        if (this.stair > 0.0) {
            this.speed *= 0.7 - this.getSpeedEffect() * 0.1;
        }
        if (this.stage < 0) {
            this.speed = defaultSpeed();
        }
        if (this.lessSlow) {
            this.speed *= 0.96;
        }
        if (this.lessSlow) {
            this.speed *= 0.95;
        }
        if (this.isInLiquid()) {
            this.speed = 0.12;
        }
        if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
            if (!ModManager.getModule("Scaffold").getState() || !Scaffold.mode.isCurrentMode("HypixelGlobal")) {
                this.setMotion(eventMove, this.speed);
            }
            ++this.stage;
        }
    }
    
    private void setMotion(final EventMove eventMove, final double n) {
        double n2 = this.mc.thePlayer.movementInput.moveForward;
        double n3 = this.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = this.mc.thePlayer.rotationYaw;
        if (n2 == 0.0 && n3 == 0.0) {
            eventMove.setX(0.0);
            eventMove.setZ(0.0);
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            eventMove.setX(this.mc.thePlayer.motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 88.0)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 87.9000015258789)));
            eventMove.setZ(this.mc.thePlayer.motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 88.0)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 87.9000015258789)));
        }
    }
    
    public boolean isMoving2() {
        return this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f;
    }
    
    public boolean isOnGround(final double n) {
        return !this.mc.theWorld.getCollidingBoundingBoxes((Entity)this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public int getJumpEffect() {
        if (this.mc.thePlayer.isPotionActive(Potion.jump)) {
            return this.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public int getSpeedEffect() {
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    public boolean isInLiquid() {
        if (this.mc.thePlayer.isInWater()) {
            return true;
        }
        boolean b = false;
        final int n = (int)this.mc.thePlayer.getEntityBoundingBox().minY;
        for (int i = MathHelper.floor_double(this.mc.thePlayer.getEntityBoundingBox().minX); i < MathHelper.floor_double(this.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(this.mc.thePlayer.getEntityBoundingBox().minZ); j < MathHelper.floor_double(this.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++j) {
                final Block getBlock = this.mc.theWorld.getBlockState(new BlockPos(i, n, j)).getBlock();
                if (getBlock != null && getBlock.getMaterial() != Material.air) {
                    if (!(getBlock instanceof BlockLiquid)) {
                        return false;
                    }
                    b = true;
                }
            }
        }
        return b;
    }
    
    public void onEnable() {
        this.lessSlow = false;
        this.moveSpeed = defaultSpeed();
        this.less = 0.0;
        this.lastDist = 0.0;
        this.stage = 2;
        this.stage = 1;
        this.stage = 2;
        Class211.getTimer().timerSpeed = 1.0f;
        this.lessSlow = this.mc.thePlayer.inventory.inventoryChanged;
    }
    
    public static double defaultSpeed() {
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public void onDisable() {
        Class211.getTimer().timerSpeed = 1.0f;
    }
}
