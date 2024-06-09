/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import winter.Client;
import winter.event.EventListener;
import winter.event.events.MoveEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class Speed
extends Module {
    public Timer timer;
    public static int stage;
    public static double moveSpeed;
    public static double lastDist;
    public static boolean speed;
    int delay2 = 0;

    static {
        speed = true;
    }

    public Speed() {
        super("Lowhop", Module.Category.Movement, -16731798);
        this.setBind(34);
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
        moveSpeed = Speed.getBaseMoveSpeed();
        lastDist = 0.0;
        stage = 4;
        if (this.mc.theWorld != null) {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }

    @EventListener
    public void onMove(MoveEvent event) {
        boolean oldSpeed;
        this.mc.timer.timerSpeed = 1.0888f;
        System.out.println(this.mc.thePlayer.posY);
        boolean bl2 = oldSpeed = !Client.isEnabled("NCP");
        if (!this.mc.thePlayer.isCollidedHorizontally) {
            if (Speed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Speed.round(0.4, 3)) {
                this.mc.thePlayer.motionY = 0.31;
                event.setY(0.31);
            } else if (Speed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Speed.round(0.71, 3)) {
                this.mc.thePlayer.motionY = 0.04;
                event.setY(0.04);
            } else if (Speed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Speed.round(0.75, 3)) {
                this.mc.thePlayer.motionY = -0.2;
                event.setY(-0.2);
            } else if (Speed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Speed.round(0.55, 3)) {
                this.mc.thePlayer.motionY = -0.14;
                event.setY(-0.14);
            } else if (Speed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Speed.round(0.41, 3)) {
                this.mc.thePlayer.motionY = -0.2;
                event.setY(-0.2);
            }
        }
        if (stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            moveSpeed = 1.35 * Speed.getBaseMoveSpeed() - 0.01;
        } else if (stage == 2 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            this.mc.thePlayer.motionY = 0.4;
            event.setY(0.4);
            moveSpeed *= oldSpeed ? 2.14999 : (speed ? 1.56875 : 1.3445);
        } else if (stage == 3) {
            double difference = 0.66 * (lastDist - Speed.getBaseMoveSpeed());
            moveSpeed = lastDist - difference;
            speed = !speed;
        } else {
            List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
            if ((collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically) && stage > 0) {
                stage = this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f ? 1 : 0;
            }
            moveSpeed = lastDist - lastDist / 159.0;
        }
        moveSpeed = Math.max(moveSpeed, Speed.getBaseMoveSpeed());
        Speed.setMoveSpeed(event, moveSpeed);
        if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
            ++stage;
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    public static void setMoveSpeed(MoveEvent event, double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
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
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}

