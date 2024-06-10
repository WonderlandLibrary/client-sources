package me.sleepyfish.smok.rats.impl.blatant;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;

// Class from SMok Client by SleepyFish
public class Bunny_Hop extends Rat {

    DoubleSetting speed;
    BoolSetting strafeCheck;
    ModeSetting<Enum<?>> mode;

    private boolean allowResetPosY;

    private Timer timer = new Timer();

    public Bunny_Hop() {
        super("Bunny Hop", Rat.Category.Blatant, "Jump like a autistic retard");
    }

    @Override
    public void setup() {
        this.addSetting(this.mode = new ModeSetting<>("Mode", Bunny_Hop.modes.NCP));
        this.addSetting(this.strafeCheck = new BoolSetting("Strafe checks", "Doesn't work while strafing", true));
        this.addSetting(this.speed = new DoubleSetting("Speed", 1.2, 1.0, 5.0, 0.1));
    }

    @Override
    public void onEnableEvent() {
        this.allowResetPosY = true;
    }

    @Override
    public void onDisableEvent() {
        this.allowResetPosY = true;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (this.allowResetPosY) {
                this.allowResetPosY = false;
            }

            if (Utils.isMoving()) {

                if (this.strafeCheck.isEnabled()) {
                    if (mc.thePlayer.moveStrafing != 0) {
                        return;
                    }
                }

                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }

                if (this.mode.getMode() == Bunny_Hop.modes.Smok && mc.thePlayer.jumpMovementFactor >= 0.4F) {
                    if (!mc.thePlayer.onGround) {
                        mc.thePlayer.noClip = false;
                    } else {
                        mc.thePlayer.velocityChanged = false;
                        mc.thePlayer.noClip = true;
                        mc.thePlayer.jumpMovementFactor /= 0.8F;
                    }
                }

                if (this.mode.getMode() == Bunny_Hop.modes.BoundingJump && !mc.thePlayer.onGround && this.timer.delay(150L)) {
                    AxisAlignedBB axis = mc.thePlayer.getEntityBoundingBox().expand(0.0, -0.3, 0.0);
                    mc.thePlayer.setEntityBoundingBox(axis);
                    mc.thePlayer.motionY -= 0.3;
                    this.timer.reset();
                }

                if (this.mode.getMode() == Bunny_Hop.modes.NCP) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
                    double forward = mc.thePlayer.movementInput.moveForward;
                    double strafe = mc.thePlayer.movementInput.moveStrafe;
                    if (forward > 0.0) {
                        mc.thePlayer.setSprinting(true);
                    }

                    mc.thePlayer.noClip = true;
                    double motionX = mc.thePlayer.motionX * mc.thePlayer.motionX;
                    double motionY = mc.thePlayer.motionZ * mc.thePlayer.motionZ;
                    float yaw = mc.thePlayer.rotationYaw;
                    double speed = (float) (Math.sqrt(motionX + motionY) + 0.0025 * this.speed.getValue());

                    if (forward == 0.0 && strafe == 0.0) {
                        if (Gui.blatantMode.isEnabled()) {
                            mc.thePlayer.motionX = 0.0;
                            mc.thePlayer.motionZ = 0.0;
                        } else {
                            ClientUtils.addDebug("MX: " + Math.round(mc.thePlayer.motionX) + ", MZ: " + Math.round(mc.thePlayer.motionZ));
                        }
                    } else if (forward != 0.0) {
                        if (strafe > 0.0) {
                            yaw += (float) (forward > 0.0 ? -45.0 : 45.0);
                        } else if (strafe < 0.0) {
                            yaw += (float) (forward > 0.0 ? 45.0 : -45.0);
                        }

                        strafe = 0.0;
                        if (forward > 0.0) {
                            forward = 1.0;
                        } else if (forward < 0.0) {
                            forward = -1.0;
                        }

                        double rad = Math.toRadians((double) yaw + 90.0);
                        mc.thePlayer.motionX = forward * speed * Math.cos(rad) + strafe * speed * Math.sin(rad);
                        mc.thePlayer.motionZ = forward * speed * Math.sin(rad) + strafe * speed * Math.cos(rad);
                    }
                }

                if (this.mode.getMode() == Bunny_Hop.modes.Bounce) {
                    this.bounceBalls();
                }
            }
        }
    }

    private void bounceBalls() {
        double motionX = mc.thePlayer.motionX * mc.thePlayer.motionX;
        double motionY = mc.thePlayer.motionZ * mc.thePlayer.motionZ;
        double pSpeedD = Math.sqrt(motionX + motionY);
        double sSpeedT = Math.sqrt(motionX + motionY) + this.speed.getValue();
        mc.thePlayer.motionZ = sSpeedT * Math.cos(Math.toRadians(this.getDirection())) * pSpeedD;
        mc.thePlayer.motionX = sSpeedT * -Math.sin(Math.toRadians(this.getDirection())) * pSpeedD;
    }

    private double getDirection() {
        float yaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F) {
            yaw += 180.0F;
        }

        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }

        if (mc.thePlayer.moveStrafing > 0.0F) {
            yaw -= 90.0F * forward;
        }

        if (mc.thePlayer.moveStrafing < 0.0F) {
            yaw += 90.0F * forward;
        }

        return yaw;
    }

    public enum modes {
        Smok, BoundingJump,
        NCP, Bounce;
    }

}