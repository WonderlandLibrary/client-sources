/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPostUpdate;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

import java.awt.*;

public class Fly
        extends Module {
    public Mode mode = new Mode("Mode", "mode", FlightMode.values(), FlightMode.Guardian);
    int counter, level;
    double moveSpeed, lastDist;
    boolean b2;
    private final TimerUtil timer = new TimerUtil();
    private double movementSpeed;
    private int hypixelCounter;
    private int hypixelCounter2;

    public Fly() {
        super("Fly", new String[]{"fly", "angel"}, ModuleType.Blatant);
        this.setColor(new Color(158, 114, 243).getRGB());
        this.addValues(this.mode);
    }

    public void damagePlayer(int damage) {
        if (damage < 1)
            damage = 1;
        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) { // TODO: teach rederpz (and myself) how math works
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, (i == ((3 + damage) / offset))));
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.mode.getValue() == FlightMode.Hypixel || this.mode.getValue() == FlightMode.HypixelZoom) {
            if (this.mode.getValue() == FlightMode.HypixelZoom) damagePlayer(5);
            this.hypixelCounter = 0;
            this.hypixelCounter2 = 1000;
            mc.thePlayer.motionY = 0.42f;
        }
        level = 1;
        moveSpeed = 0.1D;
        b2 = true;
        lastDist = 0.0D;
    }

    @Override
    public void onDisable() {
        if (this.mode.getValue() == FlightMode.Area51) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        }
        this.hypixelCounter = 0;
        this.hypixelCounter2 = 100;
        Timer.timerSpeed = 1.0f;
        level = 1;
        moveSpeed = 0.1D;
        b2 = false;
        lastDist = 0.0D;
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getValue());
        if (this.mode.getValue() == FlightMode.Guardian) {
            Timer.timerSpeed = 1.7f;
            if (!mc.thePlayer.onGround && mc.thePlayer.ticksExisted % 2 == 0) {
                mc.thePlayer.motionY = 0.04;
            }
            if (mc.gameSettings.keyBindJump.pressed) {
                mc.thePlayer.motionY += 1.0;
            }
            if (mc.gameSettings.keyBindSneak.pressed) {
                mc.thePlayer.motionY -= 1.0;
            }
        } else if (this.mode.getValue() == FlightMode.Vanilla) {
            mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 5.0 : (mc.thePlayer.movementInput.sneak ? -5.0 : 0.0);
            if (mc.thePlayer.moving()) {
                mc.thePlayer.setSpeed(5.0);
            } else {
                mc.thePlayer.setSpeed(0.0);
            }
        } else if (this.mode.getValue() == FlightMode.Area51) {
            mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 0.0 : (mc.thePlayer.movementInput.sneak ? -0.1 : 0.0);
        } else if (this.mode.getValue() == FlightMode.Hypixel || this.mode.getValue() == FlightMode.HypixelZoom) {
            ++counter;
            if (Minecraft.getMinecraft().thePlayer.moveForward == 0
                    && Minecraft.getMinecraft().thePlayer.moveStrafing == 0) {
                Minecraft.getMinecraft().thePlayer.setPosition(
                        Minecraft.getMinecraft().thePlayer.posX + 1.0D,
                        Minecraft.getMinecraft().thePlayer.posY + 1.0D,
                        Minecraft.getMinecraft().thePlayer.posZ + 1.0D);
                Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.prevPosX,
                        Minecraft.getMinecraft().thePlayer.prevPosY,
                        Minecraft.getMinecraft().thePlayer.prevPosZ);
                Minecraft.getMinecraft().thePlayer.motionX = 0.0D;
                Minecraft.getMinecraft().thePlayer.motionZ = 0.0D;
            }
            Minecraft.getMinecraft().thePlayer.motionY = 0.0D;
            if (Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
                Minecraft.getMinecraft().thePlayer.motionY += 0.5f;
            if (Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed)
                Minecraft.getMinecraft().thePlayer.motionY -= 0.5f;
            if (counter != 1 && counter == 2) {
                Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX,
                        Minecraft.getMinecraft().thePlayer.posY + 1.0E-10D,
                        Minecraft.getMinecraft().thePlayer.posZ);
                counter = 0;
            }
        } else if (this.mode.getValue() == FlightMode.OldGuardianLongJumpFly && mc.thePlayer.moving() && !ModuleManager.getModuleByClass(Speed.class).isEnabled()) {
            if (mc.thePlayer.isAirBorne) {
                if (mc.thePlayer.ticksExisted % 12 == 0 && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)).getBlock() instanceof BlockAir) {
                    mc.thePlayer.setSpeed(6.5);
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9, mc.thePlayer.posZ, mc.thePlayer.onGround));
                    mc.thePlayer.motionY = 0.455;
                } else {
                    mc.thePlayer.setSpeed((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ));
                }
            } else {
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
            }
            if (mc.thePlayer.movementInput.jump) {
                mc.thePlayer.motionY = 0.85;
            } else if (mc.thePlayer.movementInput.sneak) {
                mc.thePlayer.motionY = -0.85;
            }
        }
    }

    @EventHandler
    public void onPost(EventPostUpdate e) {
        if (this.mode.getValue() == FlightMode.Dash) {
            mc.thePlayer.motionY = 0;
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
            final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
            final double x = -Math.sin(yaw) * 6;
            final double z = Math.cos(yaw) * 6;
            if (timer.hasReached(1500)) {
                mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY - 2f, mc.thePlayer.posZ + z);
                timer.reset();
            }
        }
        if (this.mode.getValue() == FlightMode.Hypixel || this.mode.getValue() == FlightMode.HypixelZoom) {
            double xDist = Minecraft.getMinecraft().thePlayer.posX
                    - Minecraft.getMinecraft().thePlayer.prevPosX;
            double zDist = Minecraft.getMinecraft().thePlayer.posZ
                    - Minecraft.getMinecraft().thePlayer.prevPosZ;
            lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
    }

    @EventHandler
    private void onMove(EventMove e) {
//        if(this.mode.getValue() == FlightMode.Dash){
//            mc.thePlayer.motionY = 0;
//            mc.thePlayer.motionX = 0;
//            mc.thePlayer.motionZ = 0;
//            final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
//            final double x = -Math.sin(yaw) * 6;
//            final double z = Math.cos(yaw) * 6;
//            if (timer.hasReached(1500)) {
//                mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY - 2f, mc.thePlayer.posZ + z);
//                timer.reset();
//            }
//        }
        if (this.mode.getValue() == FlightMode.Hypixel || this.mode.getValue() == FlightMode.HypixelZoom) {
            float forward = MovementInput.moveForward;
            float strafe = MovementInput.moveStrafe;
            float yaw = mc.thePlayer.rotationYaw;
            double mx = Math.cos(Math.toRadians(yaw + 90.0F));
            double mz = Math.sin(Math.toRadians(yaw + 90.0F));

            if (forward == 0.0F && strafe == 0.0F) {
                EventMove.x = 0.0D;
                EventMove.z = 0.0D;
            } else if (forward != 0.0F) {
                if (strafe >= 1.0F) {
                    yaw += (float) (forward > 0.0F ? -45 : 45);
                    strafe = 0.0F;
                } else if (strafe <= -1.0F) {
                    yaw += (float) (forward > 0.0F ? 45 : -45);
                    strafe = 0.0F;
                }

                if (forward > 0.0F) {
                    forward = 1.0F;
                } else if (forward < 0.0F) {
                    forward = -1.0F;
                }
            }
            if (b2) {
                if (level != 1 || Minecraft.getMinecraft().thePlayer.moveForward == 0.0F
                        && Minecraft.getMinecraft().thePlayer.moveStrafing == 0.0F) {
                    if (level == 2) {
                        level = 3;
                        moveSpeed *= 2.1499999D;
                    } else if (level == 3) {
                        level = 4;
                        double difference = (mc.thePlayer.ticksExisted % 2 == 0 ? 0.0103D : 0.0123D)
                                * (lastDist - MathUtil.getBaseMovementSpeed());
                        moveSpeed = lastDist - difference;
                    } else {
                        if (Minecraft.getMinecraft().theWorld
                                .getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer,
                                        Minecraft.getMinecraft().thePlayer.boundingBox.offset(0.0D,
                                                Minecraft.getMinecraft().thePlayer.motionY, 0.0D))
                                .size() > 0 || Minecraft.getMinecraft().thePlayer.isCollidedVertically) {
                            level = 1;
                        }
                        moveSpeed = lastDist - lastDist / 159.0D;
                    }
                } else {
                    level = 2;
                    int amplifier = Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)
                            ? Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed)
                            .getAmplifier() + 1
                            : 0;
                    double boost = Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56
                            : 2.034;
                    moveSpeed = boost * MathUtil.getBaseMovementSpeed();
                }
                moveSpeed = this.mode.getValue() == FlightMode.HypixelZoom ? Math.max(moveSpeed, MathUtil.getBaseMovementSpeed()) : MathUtil.getBaseMovementSpeed();

                EventMove.x = (double) forward * moveSpeed * mx + (double) strafe * moveSpeed * mz;
                EventMove.z = (double) forward * moveSpeed * mz - (double) strafe * moveSpeed * mx;
                if (forward == 0.0F && strafe == 0.0F) {
                    EventMove.x = 0.0D;
                    EventMove.z = 0.0D;
                }
            }
        }
    }

    double getBaseMoveSpeed() {
        double baseSpeed = 0.275;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return baseSpeed;
    }

    public enum FlightMode {
        Vanilla,
        Dash,
        Guardian,
        Hypixel,
        HypixelZoom,
        Area51,
        OldGuardianLongJumpFly,
        GuardianLongJumpFly,
        AGC
    }

}

