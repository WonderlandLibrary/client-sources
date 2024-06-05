/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  com.google.common.util.concurrent.AtomicDouble
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.MoveRawEvent;
import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.TickEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.HypixelHelper;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.helpers.player.MoveUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class Speed
extends Module {
    public ModeSetting mode = new ModeSetting("Speed mode", "Verus", "Vanilla", "Watchdog", "WatchdogLow", "HypixelSlime", "HypixelSlimeLow", "Verus", "NCP", "Verus Lowhop", "Verus YP", "SlowHop", "VanillaHop", "VanillaLowHop", "Funcraft", "GhostlyOnGround", "Invaded", "Dev", "Gwen");
    public BooleanSetting dmgboost = new BooleanSetting("Damage Boost", true);
    public NumberSetting timer = new NumberSetting("Timer", 1.2, 1.0, 5.0, 0.05);
    public BooleanSetting sneakTimer = new BooleanSetting("Sneak Timer", false);
    public NumberSetting speed = new NumberSetting("Speed", 1.2, 1.0, 4.0, 0.05);
    public boolean resetLastDist;
    public boolean porting;
    public boolean shouldPort;
    public boolean hasPorted;
    public static double moveSpeed;
    public double hypixelMultiplier;
    public float yawHypixel;
    public boolean isChangedVelocity;
    private final AtomicDouble hDist = new AtomicDouble();
    public ArrayList<Packet> packets = new ArrayList();

    public Speed() {
        super("Speed", "Speed Up Player Movement", 0, Category.Movement);
        this.addSettings(this.mode, this.dmgboost, this.timer, this.sneakTimer, this.speed);
    }

    @Override
    public void onEnable() {
        this.hypixelMultiplier = 1.0;
        this.shouldPort = false;
        this.hasPorted = false;
        this.porting = false;
        this.resetLastDist = false;
        this.isChangedVelocity = false;
        moveSpeed = 0.0;
        this.hDist.set(0.0);
        this.packets.clear();
        switch (this.mode.getMode()) {
            case "Vanilla": 
            case "VanillaHop": 
            case "VanillaLowHop": 
            case "HypixelSlime": 
            case "HypixelSlimeLow": {
                moveSpeed = this.speed.getVal();
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        switch (this.mode.getMode()) {
            case "WatchdogLow": {
                EntityHelper.setMotion(0.0);
            }
        }
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Subscribe
    public void onMoveRaw(MoveRawEvent e) {
        switch (this.mode.getMode()) {
            case "Vanilla": 
            case "VanillaHop": 
            case "VanillaLowHop": 
            case "Watchdog": 
            case "WatchdogLow": 
            case "HypixelSlime": 
            case "HypixelSlimeLow": 
            case "Gwen": {
                if (this.sneakTimer.isChecked()) {
                    if (Speed.mc.gameSettings.keyBindSneak.pressed) {
                        MovementInput.sneak = false;
                        Timer.timerSpeed = (float)this.timer.getVal();
                        break;
                    }
                    Timer.timerSpeed = 1.0f;
                    break;
                }
                Timer.timerSpeed = (float)this.timer.getVal();
            }
        }
        String mode = this.mode.getMode();
        double sqrtedSpeed = Math.sqrt(Speed.mc.thePlayer.motionX * Speed.mc.thePlayer.motionX + Speed.mc.thePlayer.motionZ * Speed.mc.thePlayer.motionZ);
        switch (mode) {
            case "Watchdog": {
                if (Speed.mc.thePlayer.isMovingOnGround()) {
                    Speed.mc.thePlayer.motionY = this.getMotion(0.41f);
                    e.setY(Speed.mc.thePlayer.motionY);
                }
                double speed = this.doFriction(this.hDist);
                moveSpeed = speed * 0.6213145;
                this.setMotion((double)this.getBaseSpeed() * 1.0524);
                break;
            }
            case "WatchdogLow": {
                if (!Speed.mc.gameSettings.keyBindForward.isKeyDown() && !Speed.mc.gameSettings.keyBindLeft.isKeyDown() && !Speed.mc.gameSettings.keyBindRight.isKeyDown() || Speed.mc.thePlayer == null || Minecraft.theWorld == null) break;
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.thePlayer.jump();
                    Speed.mc.thePlayer.motionY = 0.045;
                    MoveUtils.setMoveSpeed(e, 0.411132324);
                    break;
                }
                Speed.mc.thePlayer.setSpeed(0.2955234335);
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                    Speed.mc.thePlayer.setSpeed(0.32264563);
                }
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() != 1) break;
                Speed.mc.thePlayer.setSpeed(0.37342335);
                break;
            }
            case "Gwen": {
                Timer.timerSpeed = 1.3f;
                if (Speed.mc.thePlayer.isMovingOnGround()) {
                    Speed.mc.thePlayer.motionY = this.getMotion(0.41f);
                    e.setY(Speed.mc.thePlayer.motionY);
                }
                this.setMotion(MoveUtils.getBaseMoveSpeed() * (double)MathHelper.getRandomInRange(0.932412f, 1.11935f));
                break;
            }
            case "Verus YP": {
                if (Speed.mc.thePlayer.isInLava() || Speed.mc.thePlayer.isInWater() || Speed.mc.thePlayer.isOnLadder() || Speed.mc.thePlayer.ridingEntity != null || !Speed.mc.thePlayer.isMoving()) break;
                Speed.mc.gameSettings.keyBindJump.pressed = false;
                if (Speed.mc.thePlayer.onGround) {
                    if (Speed.mc.gameSettings.keyBindForward.isPressed()) {
                        Speed.mc.thePlayer.setSprinting(true);
                    }
                    Speed.mc.thePlayer.jump();
                    e.z = 0.42f;
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (Speed.mc.thePlayer.getActivePotionEffects().toString().contains("moveSpeed") && !Speed.mc.gameSettings.keyBindBack.isPressed() && !Speed.mc.gameSettings.keyBindRight.isPressed() && !Speed.mc.gameSettings.keyBindLeft.isPressed()) {
                    if (Speed.mc.thePlayer.isSprinting()) {
                        EntityHelper.setMotion(0.469);
                    } else {
                        EntityHelper.setMotion(0.41);
                    }
                } else {
                    EntityHelper.setMotion(0.36);
                }
                if (!Speed.mc.gameSettings.keyBindBack.isPressed() || !Speed.mc.gameSettings.keyBindRight.isPressed() || !Speed.mc.gameSettings.keyBindLeft.isPressed()) break;
                EntityHelper.setMotion(0.3125);
            }
        }
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (this.hypixelMultiplier > 0.94) {
            this.hypixelMultiplier -= 0.0125;
        }
    }

    @Subscribe
    public void onMove(UpdateEvent e) {
        String mode;
        double yaw = Math.toRadians(Speed.mc.thePlayer.rotationYaw);
        double x2 = -Math.sin(yaw) * this.speed.getVal();
        double z2 = Math.cos(yaw) * this.speed.getVal();
        this.setDisplayName("Speed\u00a77 " + this.mode.getMode());
        switch (mode = this.mode.getMode()) {
            case "Verus": {
                if (Speed.mc.thePlayer.isMovingOnGround()) {
                    Speed.mc.thePlayer.jump();
                    Speed.mc.thePlayer.motionY = 0.42f;
                    moveSpeed = MoveUtils.getSpeedModifier(this.dmgboost.isChecked() ? (Speed.mc.thePlayer.hurtTime != 0 ? 0.65 : 0.325) : 0.325);
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "Verus Lowhop": {
                if (Speed.mc.thePlayer.isMoving()) {
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        Speed.mc.thePlayer.motionY = 0.42f;
                        moveSpeed = MoveUtils.getSpeedModifier(this.dmgboost.isChecked() ? (Speed.mc.thePlayer.hurtTime != 0 ? 0.65 : 0.325) : 0.325);
                        this.shouldPort = true;
                    } else if (this.shouldPort) {
                        Speed.mc.thePlayer.motionY = 0.0;
                        e.setOnGround(true);
                        this.hasPorted = true;
                        this.shouldPort = false;
                    }
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "SlowHop": {
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically && Speed.mc.thePlayer.isMoving()) {
                    Timer.timerSpeed = 1.0f;
                    e.setPosZ(0.42f);
                    Speed.mc.thePlayer.motionY = 0.42f;
                    moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * 0.525 : MoveUtils.getBaseMoveSpeed() * 0.85;
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "NCP": {
                KillAuraHelper.setRotations(e, (float)Speed.getDirection(), Speed.mc.thePlayer.rotationPitch);
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically) {
                    if (Speed.mc.thePlayer.isMoving()) {
                        Timer.timerSpeed = 1.05f;
                        this.hypixelMultiplier = 1.1;
                        e.setPosZ(0.42f);
                        Speed.mc.thePlayer.motionY = 0.42f;
                        moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * 0.5 : MoveUtils.getBaseMoveSpeed() * 0.8;
                    }
                    this.resetLastDist = true;
                } else if (this.resetLastDist) {
                    moveSpeed -= (double)0.66f * (moveSpeed - MoveUtils.getBaseMoveSpeed());
                    this.resetLastDist = false;
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "Funcraft": {
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically) {
                    if (Speed.mc.thePlayer.isMoving()) {
                        Timer.timerSpeed = 1.6f;
                        this.hypixelMultiplier = 1.1;
                        e.setPosZ(0.21999998688697814);
                        Speed.mc.thePlayer.motionY = 0.21999998688697814;
                        moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * 0.425 : MoveUtils.getBaseMoveSpeed() * 0.85;
                    }
                    this.resetLastDist = true;
                } else if (this.resetLastDist) {
                    moveSpeed -= (double)0.66f * (moveSpeed - MoveUtils.getBaseMoveSpeed());
                    this.resetLastDist = false;
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "VanillaHop": {
                Speed.mc.thePlayer.setSprinting(true);
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically && Speed.mc.thePlayer.isMoving()) {
                    Timer.timerSpeed = 1.0f;
                    e.setPosZ(0.42f);
                    Speed.mc.thePlayer.motionY = 0.42f;
                    moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * this.speed.getVal() : MoveUtils.getBaseMoveSpeed() * (this.speed.getVal() * 1.5);
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "HypixelSlime": {
                if (Speed.mc.thePlayer.onGround) {
                    HypixelHelper.slimeDisable();
                }
                if (!this.isChangedVelocity) break;
                Speed.mc.thePlayer.setSprinting(true);
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically && Speed.mc.thePlayer.isMoving()) {
                    e.setPosZ(0.42f);
                    Speed.mc.thePlayer.motionY = 0.42f;
                    moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * this.speed.getVal() : MoveUtils.getBaseMoveSpeed() * (this.speed.getVal() * 1.5);
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "HypixelSlimeLow": {
                if (Speed.mc.thePlayer.onGround) {
                    HypixelHelper.slimeDisable();
                }
                if (this.isChangedVelocity) {
                    Speed.mc.thePlayer.setSprinting(true);
                    if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically && Speed.mc.thePlayer.isMoving()) {
                        e.setPosZ(0.21999998688697814);
                        Speed.mc.thePlayer.motionY = 0.21999998688697814;
                        moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * this.speed.getVal() : MoveUtils.getBaseMoveSpeed() * (this.speed.getVal() * 1.5);
                    }
                    EntityHelper.setMotion(moveSpeed);
                }
            }
            case "VanillaLowHop": {
                Speed.mc.thePlayer.setSprinting(true);
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically && Speed.mc.thePlayer.isMoving()) {
                    Timer.timerSpeed = 1.0f;
                    e.setPosZ(0.21999998688697814);
                    Speed.mc.thePlayer.motionY = 0.21999998688697814;
                    moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * this.speed.getVal() : MoveUtils.getBaseMoveSpeed() * (this.speed.getVal() * 1.5);
                }
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "Vulcan": {
                Timer.timerSpeed = 0.4f;
                if (!Speed.mc.thePlayer.isMoving()) {
                    return;
                }
                if (Speed.mc.thePlayer.ticksExisted % 3 == 0 || Speed.mc.thePlayer.ticksExisted % 4 == 0 || Speed.mc.thePlayer.ticksExisted % 5 == 0 || Speed.mc.thePlayer.ticksExisted % 7 == 0 || Speed.mc.thePlayer.ticksExisted % 8 == 0 || Speed.mc.thePlayer.ticksExisted % 9 == 0 || Speed.mc.thePlayer.ticksExisted % 10 == 0) {
                    Timer.timerSpeed = 2.8f;
                    this.isChangedVelocity = true;
                    break;
                }
                this.isChangedVelocity = false;
                break;
            }
            case "Invaded": {
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isCollidedVertically) {
                    if (Speed.mc.thePlayer.isMoving()) {
                        this.yawHypixel = Speed.mc.thePlayer.rotationYaw;
                        Timer.timerSpeed = 2.2f;
                        this.hypixelMultiplier = 1.1;
                        e.setPosZ(0.42f);
                        Speed.mc.thePlayer.motionY = 0.42f;
                        moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * 0.4 : MoveUtils.getBaseMoveSpeed() * 0.7;
                    }
                    this.resetLastDist = true;
                } else if (this.resetLastDist) {
                    moveSpeed -= (double)0.66f * (moveSpeed - MoveUtils.getBaseMoveSpeed());
                    this.resetLastDist = false;
                }
                if (Speed.mc.gameSettings.keyBindForward.pressed && Speed.mc.gameSettings.keyBindLeft.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw - 45.0f, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindForward.pressed && Speed.mc.gameSettings.keyBindRight.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw + 45.0f, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindBack.pressed && Speed.mc.gameSettings.keyBindLeft.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw - 135.0f, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindBack.pressed && Speed.mc.gameSettings.keyBindRight.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw + 135.0f, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindForward.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindLeft.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw - 90.0f, 25), 0.0f);
                    break;
                }
                if (Speed.mc.gameSettings.keyBindRight.pressed) {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw + 90.0f, 25), 0.0f);
                    break;
                }
                if (!Speed.mc.gameSettings.keyBindBack.pressed) break;
                KillAuraHelper.setRotations(e, (float)MathHelper.round(Speed.mc.thePlayer.rotationYaw - 180.0f, 25), 0.0f);
                break;
            }
            case "Vanilla": {
                moveSpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? MoveUtils.getBaseMoveSpeed() * this.speed.getVal() : MoveUtils.getBaseMoveSpeed() * (this.speed.getVal() * 1.5);
                EntityHelper.setMotion(moveSpeed);
                break;
            }
            case "Watchdog": {
                double var1 = (double)this.getBaseSpeed() * 0.7221;
                this.hDist.set(var1);
                KillAuraHelper.setRotations(e, (float)Speed.getDirection(), Speed.mc.thePlayer.rotationPitch);
                break;
            }
            case "Gwen": {
                KillAuraHelper.setRotations(e, (float)Speed.getDirection(), Speed.mc.thePlayer.rotationPitch);
                break;
            }
            case "GhostlyOnGround": {
                if (!Speed.mc.thePlayer.isMoving()) {
                    return;
                }
                if (Speed.mc.thePlayer.ticksExisted % 5 != 0) break;
                Speed.mc.thePlayer.setPosition(Speed.mc.thePlayer.posX + x2, Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ + z2);
            }
        }
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        double yaw = Math.toRadians(Speed.mc.thePlayer.rotationYaw);
        double x2 = -Math.sin(yaw) * this.speed.getVal();
        double z2 = Math.cos(yaw) * this.speed.getVal();
        switch (this.mode.getMode()) {
            case "HypixelSlime": 
            case "HypixelSlimeLow": {
                if (!(e.getPacket() instanceof S12PacketEntityVelocity) || Speed.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)e.getPacket()).getEntityID()) break;
                e.setCancelled(true);
                this.isChangedVelocity = true;
                break;
            }
            case "Vulcan": {
                if (this.isChangedVelocity) {
                    if (!e.isIncoming()) break;
                    e.setCancelled(true);
                    this.packets.add(e.getPacket());
                    break;
                }
                if (this.packets.isEmpty()) break;
                for (Packet p : this.packets) {
                    PacketHelper.sendPacketNoEvent(p);
                }
                break;
            }
        }
    }

    public double doFriction(AtomicDouble hdist) {
        double value = hdist.get();
        hdist.set(value - value / 159.0);
        return Math.max(hdist.get(), this.getVerusBaseSpeed());
    }

    private double getVerusBaseSpeed() {
        double base = 0.2865;
        if (Speed.mc.thePlayer.isPotionActive(1)) {
            base *= 1.0 + 0.0495 * (double)(Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return base;
    }

    private float getBaseSpeed() {
        float baseSpeed = 0.2873f;
        if (Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amp = Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0f + 0.2f * (float)(amp + 1);
        }
        return baseSpeed;
    }

    private double getMotion(float baseMotionY) {
        Potion potion = Potion.jump;
        if (Speed.mc.thePlayer.isPotionActive(potion)) {
            int amplifier = Speed.mc.thePlayer.getActivePotionEffect(potion).getAmplifier();
            baseMotionY += (float)(amplifier + 1) * 0.1f;
        }
        return baseMotionY;
    }

    private void setMotion(double moveSpeed) {
        EntityLivingBase entity = KillAura.target;
        MovementInput movementInput = Speed.mc.thePlayer.movementInput;
        double moveForward = MovementInput.moveForward;
        double moveStrafe = MovementInput.moveStrafe;
        double rotationYaw = Speed.mc.thePlayer.rotationYaw;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            Speed.mc.thePlayer.motionZ = 0.0;
            Speed.mc.thePlayer.motionX = 0.0;
        } else {
            if (moveStrafe > 0.0) {
                moveStrafe = 1.0;
            } else if (moveStrafe < 0.0) {
                moveStrafe = -1.0;
            }
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    rotationYaw += (double)(moveForward > 0.0 ? -45 : 45);
                } else if (moveStrafe < 0.0) {
                    rotationYaw += (double)(moveForward > 0.0 ? 45 : -45);
                }
                moveStrafe = 0.0;
                if (moveForward > 0.0) {
                    moveForward = 1.0;
                } else if (moveForward < 0.0) {
                    moveForward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(rotationYaw + 90.0));
            double sin = Math.sin(Math.toRadians(rotationYaw + 90.0));
            Speed.mc.thePlayer.motionX = moveForward * moveSpeed * cos + moveStrafe * moveSpeed * sin;
            Speed.mc.thePlayer.motionZ = moveForward * moveSpeed * sin - moveStrafe * moveSpeed * cos;
        }
    }

    public static double getDirection() {
        float rotationYaw = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return rotationYaw;
    }
}

