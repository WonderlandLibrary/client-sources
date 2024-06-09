/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

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
import winter.Client;
import winter.event.EventListener;
import winter.event.events.MoveEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Speed;
import winter.utils.other.Timer;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;

public class BhopSpeed
extends Module {
    public Timer timer;
    public static int stage;
    public static double moveSpeed;
    public static double lastDist;
    public static boolean speed;
    int delay2 = 0;
    public BooleanValue low;

    static {
        speed = true;
    }

    public BhopSpeed() {
        super("Speed", Module.Category.Movement, -4329063);
        this.setBind(34);
        this.low = new BooleanValue("Lowhop", false);
        this.addValue(this.low);
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
        moveSpeed = BhopSpeed.getBaseMoveSpeed();
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
        this.mode(this.low.isEnabled() ? " Lowhop" : " Bhop");
        this.mc.timer.timerSpeed = 1.0888f;
        if (!this.mc.thePlayer.isCollidedHorizontally && this.low.isEnabled()) {
            if (BhopSpeed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == BhopSpeed.round(0.4, 3)) {
                this.mc.thePlayer.motionY = 0.31;
                event.setY(0.31);
            } else if (BhopSpeed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == BhopSpeed.round(0.71, 3)) {
                this.mc.thePlayer.motionY = 0.04;
                event.setY(0.04);
            } else if (BhopSpeed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == BhopSpeed.round(0.75, 3)) {
                this.mc.thePlayer.motionY = -0.2;
                event.setY(-0.2);
            } else if (BhopSpeed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == BhopSpeed.round(0.55, 3)) {
                this.mc.thePlayer.motionY = -0.14;
                event.setY(-0.14);
            } else if (BhopSpeed.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == BhopSpeed.round(0.41, 3)) {
                this.mc.thePlayer.motionY = -0.2;
                event.setY(-0.2);
            }
        }
        if (stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            moveSpeed = 1.35 * Speed.getBaseMoveSpeed() - 0.01;
        } else if (stage == 2 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            this.mc.thePlayer.motionY = 0.3999;
            event.setY(0.3999);
            moveSpeed *= Client.isEnabled("NCP") ? (this.low.isEnabled() ? (speed ? 1.5685 : 1.3445) : (speed ? 1.6835 : 1.395)) : 2.14999;
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
        moveSpeed = Math.max(moveSpeed, BhopSpeed.getBaseMoveSpeed());
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
}

