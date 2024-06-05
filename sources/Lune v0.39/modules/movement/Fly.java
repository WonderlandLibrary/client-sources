package me.superskidder.lune.modules.movement;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.*;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.potion.Potion;

public class Fly extends Mod {
    public Mode mode;
    private final Bool normalY;
    public Bool as;
    public Bool noStrafe;
    private final Bool bob;
    private final Bool timerZoom;
    public final Bool tpZoom;
    public Num<Number> speed;
    private final Num<Number> vanillaSpeed;
    private final Num<Number> damage;
    private boolean b2;
    private final TimerUtil timerHelper;
    public double moveSpeed;
    public int stage;
    private int zoomTick;
    private final TimerUtil zoomTicker;
    private Bool waittohurt;
    public static long zoomTimer;
    public boolean hurt = false;

    public Fly() {
        super("Fly", ModCategory.Movement, "Allow you fly");
        this.mode = new Mode<>("Mode", Fly.thisMode.values(), thisMode.HypixelZoom);
        this.normalY = new Bool<Boolean>("NormalYUp", true);
        this.noStrafe = new Bool<Boolean>("NoStrafe", false);
        this.bob = new Bool<Boolean>("ViewBobbing", false);
        this.timerZoom = new Bool<Boolean>("HypixelTimer", false);
        this.tpZoom = new Bool<Boolean>("HypixelTP", false);
        this.waittohurt = new Bool<Boolean>("WaittoHurt", false);
        this.speed = new Num<Number>("MotionSpeed", 2.1, 0.1, 10.0);
        this.vanillaSpeed = new Num<Number>("VanillaSpeed", 1.0, 1.0, 10.0);
        this.damage = new Num<Number>("Damage", 1.0, 0.0, 20.0);
        this.timerHelper = new TimerUtil();
        this.zoomTicker = new TimerUtil();
        this.addValues(this.mode, this.speed, this.vanillaSpeed, this.damage, this.normalY, this.timerZoom, this.tpZoom, this.noStrafe, this.bob);
    }

    @Override
    public void onEnabled() {
        if (mode.getValue() == thisMode.HypixelZoom) {
            if (mc.thePlayer == null)
                return;
            hurt = false;
            PlayerUtil.damagePlayer(false, 1);
            mc.thePlayer.jump();
            mc.thePlayer.motionY = 0.41F;
            if (mode.getValue() == thisMode.HypixelZoom) {
                this.stage = 7;
                moveSpeed = 0.1D;
                this.zoomTick = 0;
                this.zoomTicker.reset();
                this.b2 = true;
                return;
            }
            zoomTimer = System.currentTimeMillis();
            this.stage = 7;
            this.moveSpeed = 0.1D;
            mc.thePlayer.motionX *= 0;
            mc.thePlayer.motionZ *= 0;
            this.zoomTick = 0;
            this.zoomTicker.reset();
            this.b2 = true;
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6f;
        this.b2 = false;
        mc.thePlayer.jumpMovementFactor = 0.1f;
        if (this.mode.getValue() == thisMode.HypixelZoom) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
            mc.thePlayer.jumpMovementFactor = 0.0f;
        }else if(this.mode.getValue() == thisMode.Vanilla) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        }

        mc.timer.timerSpeed = 1.0f;
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate e) {
        this.setDisplayName(this.mode.getValue().toString());
        if ((boolean) this.noStrafe.getValue() && !(this.mode.getValue() == thisMode.HypixelZoom)) {
            this.b2 = false;
        } else if (!(boolean) this.noStrafe.getValue() && !this.b2) {
            this.b2 = true;
        }
        if ((Boolean) this.bob.getValue()) {
            mc.thePlayer.cameraYaw = 0.05454545f;
        }
        final String s = this.mode.getModeAsString();
        switch (s) {
            case "Vanilla": {
                mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 1.0 : (mc.thePlayer.movementInput.sneak ? -1.0 : 0.0);
                PlayerUtil.setMotion(vanillaSpeed.getValue().floatValue());
                break;
            }
            case "HypixelZoom": {
                if (this.stage >= 7) {
                    if ((mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f) || mc.thePlayer.isCollidedHorizontally) {
                        this.moveSpeed = 0.0;
                    }
//                    switch (++this.zoomTick) {
//                        case 11: {
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-4, mc.thePlayer.posZ);
//                            break;
//                        }
//                        case 3: {
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2.0E-4, mc.thePlayer.posZ);
//                            break;
//                        }
//                        case 7: {
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3.0E-4, mc.thePlayer.posZ);
//                            break;
//                        }
//                        case 12: {
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 4.0E-4, mc.thePlayer.posZ);
//                            this.zoomTick = 0;
//                            break;
//                        }
//                    }
                    mc.thePlayer.jumpMovementFactor = 0.0f;
                }
                mc.thePlayer.motionY = 0.0;
                break;
            }
        }
    }

    @EventTarget
    private void onMove(final EventMove e) {

        if (mc.thePlayer.hurtTime != 0) {
            hurt = true;
        }

        if (!hurt && this.mode.getValue() == thisMode.HypixelZoom && (Boolean) waittohurt.getValue()) {
            mc.thePlayer.motionX = Math.abs(mc.thePlayer.motionX) < 0.02 ? mc.thePlayer.motionX : 0;
            mc.thePlayer.motionZ = Math.abs(mc.thePlayer.motionZ) < 0.02 ? mc.thePlayer.motionZ : 0;
            return;
        }

        final String s = this.mode.getModeAsString();
        switch (s) {
            case "HypixelZoom": {
                if (!PlayerUtil.isMoving2()) {
                    e.setX(0.0);
                    e.setZ(0.0);
                    this.stage = 10;
                    break;
                }
                switch (this.stage) {
                    case 1: {
                        this.moveSpeed = 0.2873;
                        this.stage = 2;
                        break;
                    }
                    case 2: {
                        this.stage = 3;
                        break;
                    }
                    case 3: {
                        this.stage = 4;
                        break;
                    }
                    case 4: {
                        this.stage = 5;
                        break;
                    }
                    case 5: {
                        this.stage = 6;
                        break;
                    }
                    case 6: {
                        this.stage = 7;
                        break;
                    }
                    case 7: {
                        if (!(boolean) this.tpZoom.getValue() && (boolean) this.timerZoom.getValue()) {
                            this.timerHelper.reset();
                        } else if ((boolean) this.tpZoom.getValue()) {
                            this.timerHelper.reset();
                        }
                        this.moveSpeed = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.89 : 2.26) * MathUtil.getBaseMovementSpeed();
                        this.stage = 8;
                        break;
                    }
                    case 8: {
                        this.moveSpeed *= (double) speed.getValue();
                        this.stage = 9;
                        break;
                    }
                    case 9: {
                        this.moveSpeed -= ((mc.thePlayer.ticksExisted % 2 == 0) ? 0.0103 : 0.0123) * (this.moveSpeed - MathUtil.getBaseMovementSpeed());
                        this.stage = 10;
                        break;
                    }
                    default: {
                        this.moveSpeed -= this.moveSpeed / 159.0;
                        break;
                    }
                }
                PlayerUtil.setMotion(this.moveSpeed = Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed()));
                if (KillAura.target != null && Lune.moduleManager.getModByClass(TargetStrafe.class).getState()) {
                    Speed.targetstrafe.strafe(e,  moveSpeed/ 1.4);
                }
                break;
            }
            case "Vanilla":{
                if (KillAura.target != null && Lune.moduleManager.getModByClass(TargetStrafe.class).getState()) {
                    Speed.targetstrafe.strafe(e,  vanillaSpeed.getValue().floatValue());
                }
                break;
            }
        }
    }


    enum thisMode {
        Vanilla,
        HypixelZoom
    }


}
