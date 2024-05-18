/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import com.darkmagician6.eventapi.EventTarget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import pw.vertexcode.nemphis.events.EventPreMotion;
import pw.vertexcode.nemphis.events.MoveEvent;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-15294331, name="Longjump")
public class Longjump
extends ToggleableModule {
    private int level = 1;
    private boolean disabling;
    private double moveSpeed = 0.2873;
    public static double yOffset;
    private double lastDist;
    private boolean speedTick;
    private float speedTimer;
    private int timer;
    public static float boost;
    TimeHelper test = new TimeHelper();

    public Longjump() {
        boost = 4.5f;
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (this.test.hasReached(100.0f)) {
            EntityPlayerSP thePlayer2;
            if (Longjump.mc.thePlayer.moveStrafing <= 0.0f && Longjump.mc.thePlayer.moveForward <= 0.0f) {
                this.level = 1;
            }
            if (Longjump.round(Longjump.mc.thePlayer.posY - (double)((int)Longjump.mc.thePlayer.posY), 3) == Longjump.round(0.943, 3)) {
                EntityPlayerSP entityPlayerSP = thePlayer2 = Longjump.mc.thePlayer;
                EntityPlayerSP player = thePlayer2;
                entityPlayerSP.motionY -= 0.136;
                event.y = -0.019;
            }
            if (this.level == 1 && (Longjump.mc.thePlayer.moveForward != 0.0f || Longjump.mc.thePlayer.moveStrafing != 0.0f)) {
                this.level = 2;
                this.moveSpeed = 4.8 * this.getStandart() - 0.1;
            } else if (this.level == 2) {
                this.level = 3;
                thePlayer2 = Longjump.mc.thePlayer;
                double yee = 0.424;
                if (Longjump.mc.thePlayer.onGround) {
                    thePlayer2.motionY = 0.424;
                    event.y = 0.424;
                    this.moveSpeed *= 2.1498;
                }
            } else if (this.level == 3) {
                this.level = 4;
                double difference = 0.66 * (this.lastDist - this.getStandart());
                this.moveSpeed = this.lastDist - difference;
            } else {
                Minecraft.getMinecraft();
                if (Longjump.mc.theWorld.getCollidingBoundingBoxes(Longjump.mc.thePlayer, Longjump.mc.thePlayer.boundingBox.offset(0.0, Longjump.mc.thePlayer.motionY, 0.0)).size() > 0 || Longjump.mc.thePlayer.isCollidedVertically) {
                    this.level = 1;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getStandart());
            MovementInput movementInput = Longjump.mc.thePlayer.movementInput;
            float forward = movementInput.moveForward;
            float strafe = movementInput.moveStrafe;
            float yaw = Longjump.mc.thePlayer.rotationYaw;
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
            double mx = Math.cos(Math.toRadians(yaw + 90.0f));
            double mz = Math.sin(Math.toRadians(yaw + 90.0f));
            event.x = (double)forward * this.moveSpeed * mx + (double)strafe * this.moveSpeed * mz;
            event.z = (double)forward * this.moveSpeed * mz - (double)strafe * this.moveSpeed * mx;
        }
    }

    @Override
    public void onEnabled() {
        Longjump.mc.thePlayer.motionX = 0.0;
        this.test.resetTimer();
        Longjump.mc.thePlayer.motionZ = 0.0;
        super.onEnabled();
    }

    @Override
    public void onDisable() {
        Longjump.mc.timer.timerSpeed = 1.0f;
        this.moveSpeed = this.getStandart();
        this.test.resetTimer();
        yOffset = 0.0;
        this.level = 0;
        this.disabling = false;
        super.onDisable();
    }

    @EventListener
    public void onUpdate(EventPreMotion e) {
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (!this.test.hasReached(100.0f)) {
            Longjump.mc.thePlayer.motionX *= 0.2;
            Longjump.mc.thePlayer.motionZ *= 0.2;
        }
        double xDist = Longjump.mc.thePlayer.posX - Longjump.mc.thePlayer.prevPosX;
        double zDist = Longjump.mc.thePlayer.posZ - Longjump.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    private double getStandart() {
        double baseSpeed = 0.2873;
        return baseSpeed;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

