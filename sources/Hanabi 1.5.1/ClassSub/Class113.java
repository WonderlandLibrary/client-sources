package ClassSub;

import net.minecraft.client.*;
import net.minecraft.potion.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import cn.Hanabi.events.*;

public class Class113
{
    Minecraft mc;
    private double nextMotionSpeed;
    private double xMotionSpeed;
    private double zDist;
    private double moveSpeed;
    int stage;
    
    
    public Class113() {
        this.mc = Minecraft.getMinecraft();
        this.stage = 0;
    }
    
    public void onMove(final EventMove eventMove) {
        this.moveSpeed = Class200.getBaseMoveSpeed();
        if (this.stage < 1) {
            ++this.stage;
            this.nextMotionSpeed = 0.0;
        }
        if (this.stage == 2 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) && this.mc.thePlayer.isCollidedVertically && this.mc.thePlayer.onGround) {
            this.xMotionSpeed = 0.4200123123131243;
            if (this.mc.thePlayer.isPotionActive(Potion.jump)) {
                this.xMotionSpeed += (this.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
            }
            eventMove.setY(this.mc.thePlayer.motionY = this.xMotionSpeed);
            this.moveSpeed *= 2.1498624684;
        }
        else if (this.stage == 3) {
            this.xMotionSpeed = ((this.stage % 3 == 0) ? 0.678994565156 : 0.719499495154) * (this.nextMotionSpeed - Class200.getBaseMoveSpeed());
            this.moveSpeed = this.nextMotionSpeed - this.xMotionSpeed;
        }
        else {
            if ((this.mc.theWorld.getCollidingBoundingBoxes((Entity)this.mc.thePlayer, ((IEntity)this.mc.thePlayer).getBoundingBox().offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) && this.stage > 0) {
                this.stage = ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
            }
            this.moveSpeed = this.nextMotionSpeed - this.nextMotionSpeed / 159.0;
        }
        this.moveSpeed = Math.max(this.moveSpeed, Class200.getBaseMoveSpeed());
        this.xMotionSpeed = this.mc.thePlayer.movementInput.moveForward;
        this.zDist = this.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = this.mc.thePlayer.rotationYaw;
        if (this.xMotionSpeed == 0.0 && this.zDist == 0.0) {
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + 1.0, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + 1.0);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.prevPosX, this.mc.thePlayer.posY, this.mc.thePlayer.prevPosZ);
            eventMove.setX(0.0);
            eventMove.setZ(0.0);
        }
        else if (this.xMotionSpeed != 0.0) {
            if (this.zDist >= 1.0) {
                rotationYaw += ((this.xMotionSpeed > 0.0) ? -45.0f : 45.0f);
                this.zDist = 0.0;
            }
            else if (this.zDist <= -1.0) {
                rotationYaw += ((this.xMotionSpeed > 0.0) ? 45.0f : -45.0f);
                this.zDist = 0.0;
            }
            if (this.xMotionSpeed > 0.0) {
                this.xMotionSpeed = 1.0;
            }
            else if (this.xMotionSpeed < 0.0) {
                this.xMotionSpeed = -1.0;
            }
        }
        final double cos = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double x = (this.xMotionSpeed * this.moveSpeed * cos + this.zDist * this.moveSpeed * sin) * 0.987;
        final double z = (this.xMotionSpeed * this.moveSpeed * sin - this.zDist * this.moveSpeed * cos) * 0.987;
        if (Math.abs(x) < 1.0 && Math.abs(z) < 1.0) {
            eventMove.setX(x);
            eventMove.setZ(z);
        }
        this.mc.thePlayer.stepHeight = 0.6f;
        if (this.xMotionSpeed == 0.0 && this.zDist == 0.0) {
            eventMove.setX(0.0);
            eventMove.setZ(0.0);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + 1.0, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + 1.0);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.prevPosX, this.mc.thePlayer.posY, this.mc.thePlayer.prevPosZ);
        }
        else if (this.xMotionSpeed != 0.0) {
            if (this.zDist >= 1.0) {
                final float n = rotationYaw + ((this.xMotionSpeed > 0.0) ? -45.0f : 45.0f);
                this.zDist = 0.0;
            }
            else if (this.zDist <= -1.0) {
                final float n2 = rotationYaw + ((this.xMotionSpeed > 0.0) ? 45.0f : -45.0f);
                this.zDist = 0.0;
            }
            if (this.xMotionSpeed > 0.0) {
                this.xMotionSpeed = 1.0;
            }
            else if (this.xMotionSpeed < 0.0) {
                this.xMotionSpeed = -1.0;
            }
        }
        ++this.stage;
    }
    
    public void onEnable() {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionX *= 0.0;
        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        thePlayer2.motionZ *= 0.0;
        if (this.mc.thePlayer != null) {
            this.moveSpeed = Class200.getBaseMoveSpeed();
        }
        this.nextMotionSpeed = 0.0;
        this.stage = 2;
        Class211.getTimer().timerSpeed = 1.0f;
    }
    
    public void onPre(final EventPreMotion eventPreMotion) {
        this.xMotionSpeed = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        this.zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.nextMotionSpeed = Math.sqrt(this.xMotionSpeed * this.xMotionSpeed + this.zDist * this.zDist);
    }
}
