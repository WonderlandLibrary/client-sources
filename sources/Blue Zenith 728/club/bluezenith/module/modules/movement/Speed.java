package club.bluezenith.module.modules.movement;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.player.Scaffold;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

import java.util.function.Supplier;

import static club.bluezenith.util.player.MovementUtil.*;
import static java.lang.Math.max;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class Speed extends Module {
    private final MillisTimer strafeTimer = new MillisTimer();
    public final ModeValue mode = new ModeValue("Mode", "Vanilla", true, null, "Vanilla", "Watchdog").setIndex(1);
    final Supplier<Boolean> isWatchdog = () -> mode.is("Watchdog");

    private final FloatValue speed = new FloatValue("Speed", 0.31f, 0f, 5f, 0.05f, true, () -> mode.is("Vanilla") || mode.is("Boost")).setIndex(2);
    private final FloatValue slipperiness = new FloatValue("Slipperiness", 0.57f, 0f, 1f, 0.01f).showIf(isWatchdog).setIndex(3);
    private final FloatValue slipperinessSpeed2 = new FloatValue("Slipperiness speed 2", 0.89f, 0f, 1f, 0.01f).showIf(isWatchdog).setIndex(4);
    private final BooleanValue strafe = new BooleanValue("Strafe", true).showIf(isWatchdog).setIndex(5);
    private final BooleanValue strafeSpeed2 = new BooleanValue("Strafe speed 2", true).showIf(isWatchdog).setIndex(6);
    private final FloatValue strafeMultiplier = new FloatValue("Strafe multiplier", 1, 0, 1, 0.01f).showIf(isWatchdog).setIndex(7);
    private final FloatValue strafeMultiplierSpeed2 = new FloatValue("Strafe multiplier speed 2", 1, 0, 1, 0.1f).showIf(isWatchdog).setIndex(8);
    private final BooleanValue rotationStrafe = new BooleanValue("Rotation strafe", false).setIndex(9);
    private final IntegerValue strafeCooldown = new IntegerValue("StrafeCooldown", 250, 0, 1000, 1).showIf(isWatchdog).setIndex(10);
    private final FloatValue jumpBoostMult = new FloatValue("JumpBoostMult", 0.2f, 0, 1, 0.005f).showIf(isWatchdog).setIndex(11);
    private final FloatValue jumpBoostBase = new FloatValue("JumpBoostBase", 0.2f, 0, 1, 0.01f).showIf(isWatchdog).setIndex(12);
    private final FloatValue glideAbs = new FloatValue("GlideAbs", 0, 0, 0.5f, 0.1f).showIf(isWatchdog).setIndex(13);
    private final FloatValue glideMult = new FloatValue("GlideMult", 0, 0, 1.5f, 0.1f).showIf(isWatchdog).setIndex(14);
    private final FloatValue timer = new FloatValue("Timer", 1, 0.1f, 3, 0.05f).showIf(isWatchdog).setIndex(15);
    private final FloatValue speedInAir = new FloatValue("Speed in air", 2f, 2f, 3f, 0.01f).showIf(isWatchdog).setIndex(16);
    public final BooleanValue strafeBypass = new BooleanValue("Strafe bypass", true).showIf(isWatchdog).setIndex(17);
    final BooleanValue enableDamageBoost = new BooleanValue("Damage Boost", false).setIndex(18);
    private final FloatValue damageBoost = new FloatValue("Boost Multiplier", 0, 0, 5, 0.01f).showIf(enableDamageBoost::get).setIndex(19);
    final BooleanValue checkLagback = new BooleanValue("Lagback Check", false).setIndex(20);
    public final BooleanValue noSpeedReset = new BooleanValue("Check Collision", false).setIndex(22).showIf(isWatchdog);
    private final ModeValue ymotion = new ModeValue("Jump Motion", "Normal", true, null, "Normal", "Reduced").showIf(isWatchdog).setIndex(20);
    public static boolean straf = false;
    private int lastDirection = 0;
    int verusAirTicks;
    double lastGroundY;
    int jumpedTicks = 0;
    private float oldYaw = 0;
    public double actualSpeed = 0;

    public Speed() {
        super("OldSpeed", ModuleCategory.MOVEMENT, "bhop");
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        switch (mode.get()) {
            case "Vanilla":
                if(mc.thePlayer.onGround && areMovementKeysPressed()) {
                    mc.thePlayer.jump();
                }
                setSpeed((float) max(speed.get(), getNormalSpeed()));
                break;

            case "Verus Low": setSpeed((float) max(0.35, getNormalSpeed()));
            if(mc.thePlayer.onGround) {
                verusAirTicks = 0;
                float oldFactor = mc.thePlayer.jumpMovementFactor;
                mc.thePlayer.jump();
            } else if(mc.thePlayer.fallDistance < 1 && verusAirTicks++ > 1 && !mc.gameSettings.keyBindJump.pressed) {
                mc.thePlayer.motionY = -0.25;
                verusAirTicks = 0;
            }
            break;

            case "Watchdog":
                if (e.isPre()) {
                    if (mc.thePlayer.onGround && areMovementKeysPressed() && mc.thePlayer.jumpTicks == 0) {
                        if (ymotion.is("Normal")) {
                            player.jump();
                        }
                        else if (ymotion.is("Reduced")) {
                            player.jump(0.399999986886978D);
                        }
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            setSpeed(0.6202f); // 0.6202f + 2.9 speedinair for op bypass
                            actualSpeed = 0.492f;
                        }
                        else {
                            setSpeed(0.443f);
                            actualSpeed = 0.347f;
                        }
                        //ClientUtils.fancyMessage(jumpedTicks);
                        mc.thePlayer.jumpTicks = 1;
                        jumpedTicks = 0;
                    }

                    if (timer.get() != 1) {
                        mc.timer.timerSpeed = timer.get();
                    }

                    if (strafeBypass.get() && !BlueZenith.getBlueZenith().getModuleManager().getModule(Scaffold.class).getState()) {
                        final float target = (float) Math.toDegrees(Math.atan2(-(player.posX - player.prevPosX), player.posZ - player.prevPosZ));
                        e.yaw = target;

                    }
                    jumpedTicks++;
                }
                break;
        }
    }

    float b;

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.packet instanceof S08PacketPlayerPosLook && checkLagback.get()) {
            this.setState(false);
            BlueZenith.getBlueZenith().getNotificationPublisher().postWarning("Speed", "Disabled due to a lagback", 2000);
        }
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }

    @Override
    public void onEnable() {
        if(mc.thePlayer != null){
            oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
        }
    }

    @Override
    public String getTag() {
        return this.mode.get();
    }

    @Listener
    public void onMove(MoveEvent event) {
        if (event.isPost() || !mode.is("Watchdog") || player.onGround) return;

        if (enableDamageBoost.get() && damageBoost.get() > 0 && mc.thePlayer.hurtTime == 9) {
            mc.thePlayer.motionX *= (damageBoost.get() + 1);
            mc.thePlayer.motionZ *= (damageBoost.get() + 1);
        }

        straf = false;
        final boolean isSpeedEffectActive = mc.thePlayer.isPotionActive(Potion.moveSpeed);
        int speedEffectAmplifier = 0;
        if (isSpeedEffectActive){
            speedEffectAmplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        }

        if (mc.thePlayer.motionY < 0) {
            if (glideMult.get() > 0) {
                mc.thePlayer.motionY *= glideMult.get();
            } else if (glideAbs.get() > 0) {
                mc.thePlayer.motionY = max(mc.thePlayer.motionY, -glideAbs.get());
            }
        }

        if ((!isSpeedEffectActive && !strafe.get()) || (isSpeedEffectActive && !strafeSpeed2.get())) return;
        //float overallSpeed = (float) Math.sqrt((mc.thePlayer.motionX * mc.thePlayer.motionX) + (mc.thePlayer.motionZ * mc.thePlayer.motionZ));
        //if(!mc.thePlayer.onGround) overallSpeed *= 0.985;
        float rotationYaw = mc.thePlayer.rotationYaw;
        float strafe = mc.thePlayer.movementInput.moveStrafe;
        float forward = mc.thePlayer.movementInput.moveForward;

        if (strafe == 0 && forward == 0) {
            //actualSpeed = Math.sqrt(event.x * event.x + event.z * event.z);
            actualSpeed = Math.sqrt(event.x * event.x + event.z * event.z);
            return;
        }

        int direction = 0;

        if (forward > 0 && strafe == 0) {
            rotationYaw = rotationYaw;
            direction = 1;
        } else if (forward > 0 && strafe < 0) {
            rotationYaw += 45;
            direction = 2;
        } else if (forward > 0 && strafe > 0) {
            rotationYaw -= 45;
            direction = 3;
        } else if (forward < 0 && strafe == 0) {
            rotationYaw += 180;
            direction = 4;
        } else if (forward < 0 && strafe < 0) {
            rotationYaw += 135;
            direction = 5;
        } else if (forward < 0 && strafe > 0) {
            rotationYaw -= 135;
            direction = 6;
        } else if (forward == 0 && strafe < 0) {
            rotationYaw += 90;
            direction = 7;
        } else if (forward == 0 && strafe > 0) {
            rotationYaw -= 90;
            direction = 8;
        }
        //if (direction == lastDirection || !strafeTimer.hasTimeReached(strafeCooldown.get())) return;
        //lastDirection = direction;
        //strafeTimer.reset();

        if (direction == lastDirection && rotationStrafe.get()) {
            float sin = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
            float cos = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);

            /*mc.thePlayer.motionX = -sin * actualSpeed;
            mc.thePlayer.motionZ = cos * actualSpeed;
            event.x = -sin * actualSpeed;
            event.z = cos * actualSpeed;*/

            actualSpeed -= actualSpeed / 160d;
            /*if (actualSpeed < MovementUtil.getNormalSpeed()) {
                actualSpeed = MovementUtil.getNormalSpeed();
            }*/
            MovementUtil.setSpeed((float) actualSpeed, event);
            MovementUtil.setSpeed((float) actualSpeed);
        }
        else if (direction != lastDirection /*&& strafeTimer.hasTimeReached(strafeCooldown.get())*/) {
            straf = true;

            if (isSpeedEffectActive && speedEffectAmplifier == 1) {
                actualSpeed *= strafeMultiplierSpeed2.get();
            } else {
                actualSpeed *= strafeMultiplier.get();
            }

            lastDirection = direction;
            strafeTimer.reset();
            float sin = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
            float cos = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);

           /* mc.thePlayer.motionX = (-sin * actualSpeed)*//* * strafeMultiplier.get() + player.motionX * (1 - strafeMultiplier.get())*//*;
            mc.thePlayer.motionZ = (cos * actualSpeed)*//* * strafeMultiplier.get() + player.motionZ * (1 - strafeMultiplier.get())*//*;
            event.x = (-sin * actualSpeed)*//* * strafeMultiplier.get() + event.x * (1 - strafeMultiplier.get())*//*;
            event.z = (cos * actualSpeed)*//* * strafeMultiplier.get() + event.z * (1 - strafeMultiplier.get())*//*;*/

            //actualSpeed /= 1.099;
             actualSpeed -= actualSpeed / 160d;
            MovementUtil.setSpeed((float) actualSpeed, event);
            MovementUtil.setSpeed((float) actualSpeed);
//            if (actualSpeed < MovementUtil.getNormalSpeed()) {
//                actualSpeed = MovementUtil.getNormalSpeed();
//            }
            //PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - mc.thePlayer.motionX, mc.thePlayer.posY, mc.thePlayer.posZ - mc.thePlayer.motionZ, mc.thePlayer.onGround));
        }

        //mc.timer.timerSpeed = isSpeedEffectActive ? (float) ((10d/20d) / actualSpeed) : (float) ((6.75d/20d) / actualSpeed);
    }
}
