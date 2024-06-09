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
import net.minecraft.util.MovementInput;
import winter.event.EventListener;
import winter.event.events.MoveEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Nametags;
import winter.utils.other.Timer;
import winter.utils.value.Value;
import winter.utils.value.types.NumberValue;

public class LongJump
extends Module {
    public Timer timer;
    public boolean zoom;
    public boolean justEnabled;
    public static int stage;
    public static double moveSpeed;
    public static double lastDist;
    int delay2 = 0;
    private NumberValue boost;

    public LongJump() {
        super("LongJump", Module.Category.Movement, -2986855);
        this.setBind(45);
        this.boost = new NumberValue("Boost", 2.3, 1.0, 12.0, 0.1);
        this.addValue(this.boost);
    }

    @Override
    public void onEnable() {
        this.zoom = true;
        this.timer = new Timer();
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        this.zoom = false;
        this.mc.timer.timerSpeed = 1.0f;
        moveSpeed = LongJump.getBaseMoveSpeed();
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
        this.mc.timer.timerSpeed = 0.97f;
        this.mode(" " + this.boost.getValue());
        if (Nametags.roundToPlace(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Nametags.roundToPlace(0.41, 3)) {
            this.mc.thePlayer.motionY = 0.0;
        }
        if (this.mc.thePlayer.moveStrafing <= 0.0f && this.mc.thePlayer.moveForward <= 0.0f) {
            stage = 1;
        }
        if (Nametags.roundToPlace(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == Nametags.roundToPlace(0.943, 3)) {
            this.mc.thePlayer.motionY = 0.0;
        }
        if (stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            stage = 2;
            moveSpeed = this.boost.getValue() * LongJump.getBaseMoveSpeed() - 0.01;
        } else if (stage == 2) {
            stage = 3;
            this.mc.thePlayer.motionY = 0.424;
            event.y = 0.424;
            moveSpeed *= 2.149802;
        } else if (stage == 3) {
            stage = 4;
            double difference = 0.66 * (lastDist - LongJump.getBaseMoveSpeed());
            moveSpeed = lastDist - difference;
        } else {
            if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                stage = 1;
            }
            moveSpeed = lastDist - lastDist / 159.0;
        }
        moveSpeed = Math.max(moveSpeed, LongJump.getBaseMoveSpeed());
        MovementInput movementInput = this.mc.thePlayer.movementInput;
        float forward = MovementInput.moveForward;
        MovementInput movementInput2 = this.mc.thePlayer.movementInput;
        float strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = (double)forward * moveSpeed * mx2 + (double)strafe * moveSpeed * mz2;
        event.z = (double)forward * moveSpeed * mz2 - (double)strafe * moveSpeed * mx2;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
}

