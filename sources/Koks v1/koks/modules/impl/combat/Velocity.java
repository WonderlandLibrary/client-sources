package koks.modules.impl.combat;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.EventVelocity;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.lwjgl.Sys;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:07
 */
public class Velocity extends Module {

    public ModeValue<String> mode = new ModeValue<String>("Mode", "Legit", new String[]{"AAC4", "Legit", "Simple", "Intave", "Custom"}, this);

    public NumberValue<Double> velHorizontal = new NumberValue<>("Horizontal", 1D, 1D, 0D, this);
    public NumberValue<Double> velVertical = new NumberValue<>("Vertical", 1D, 1D, 0D, this);
    public NumberValue<Double> motionHorizontal = new NumberValue<>("Motion Horizontal", 1D, 1D, 0D, this);
    public NumberValue<Double> motionVertical = new NumberValue<>("Motion Vertical", 1D, 1D, 0D, this);
    public BooleanValue<Boolean> jump = new BooleanValue<>("Jump Legit", true, this);
    public BooleanValue<Boolean> sneak = new BooleanValue<>("Sneak on Damage", false, this);

    public NumberValue<Integer> betweenHurtTimeBoost = new NumberValue<>("Boost Between HurtTime", 5, 6, 10, 1, this);
    public NumberValue<Double> motionForward = new NumberValue<>("Motion Forward", 0.10D, 0.20D, 0.00D, this);
    public NumberValue<Integer> betweenHurtTimeReverse = new NumberValue<>("Reverse Between HurtTime", 5, 6, 10, 1, this);
    public NumberValue<Float> reverseFactor = new NumberValue<>("ReverseFactor", 0.02F, 0.2F, 0.02F, this);
    public BooleanValue<Boolean> tpBack = new BooleanValue<>("Teleport Back", false, this);
    public NumberValue<Integer> tpOnHurtTime = new NumberValue<>("HurtTime for Teleport", 1, 10, 1, this);
    public BooleanValue<Boolean> simulateGround = new BooleanValue<>("Simulate Ground", false, this);
    public BooleanValue<Boolean> cancelVelocity = new BooleanValue<>("Cancel Velocity", false, this);
    public double x, y, z;

    public Velocity() {
        super("Velocity", "Your haben not any velocity", Category.COMBAT);
        addValue(mode);
        addValue(velHorizontal);
        addValue(velVertical);
        addValue(motionHorizontal);
        addValue(motionVertical);
        addValue(jump);
        addValue(sneak);
        addValue(betweenHurtTimeBoost);
        addValue(motionForward);
        addValue(betweenHurtTimeReverse);
        addValue(reverseFactor);
        addValue(tpBack);
        addValue(tpOnHurtTime);
        addValue(simulateGround);
        addValue(cancelVelocity);
    }

    @Override
    public void onEvent(Event event) {

        if (mode.getSelectedMode().equals("Custom") && mc.thePlayer.hurtTime != 0) {
            if (event instanceof EventUpdate) {
                if (mc.thePlayer.hurtTime == 10) {
                    if (sneak.isToggled())
                        mc.gameSettings.keyBindSneak.pressed = true;
                    x = mc.thePlayer.posX;
                    y = mc.thePlayer.posY;
                    z = mc.thePlayer.posZ;
                } else {
                    mc.gameSettings.keyBindSneak.pressed = false;
                }
                if (tpBack.isToggled() && mc.thePlayer.hurtTime == tpOnHurtTime.getDefaultValue()) {
                    mc.thePlayer.setPosition(x, y, z);
                }
                mc.thePlayer.motionX *= motionHorizontal.getDefaultValue();
                mc.thePlayer.motionY *= motionVertical.getDefaultValue();
                mc.thePlayer.motionZ *= motionHorizontal.getDefaultValue();
                if (mc.thePlayer.hurtTime == 10 && jump.isToggled() && mc.thePlayer.onGround)
                    mc.thePlayer.jump();
                if (mc.thePlayer.hurtTime >= betweenHurtTimeBoost.getMinDefaultValue() && mc.thePlayer.hurtTime <= betweenHurtTimeBoost.getDefaultValue()) {
                    double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                    double x = -Math.sin(yaw) * motionForward.getDefaultValue();
                    double z = Math.cos(yaw) * motionForward.getDefaultValue();
                    if (motionForward.getDefaultValue() != 0.00) {
                        mc.thePlayer.motionX = x;
                        mc.thePlayer.motionZ = z;
                    }
                }
                if (mc.thePlayer.hurtTime >= betweenHurtTimeReverse.getMinDefaultValue() && mc.thePlayer.hurtTime <= betweenHurtTimeReverse.getDefaultValue()) {
                    if (simulateGround.isToggled())
                        mc.thePlayer.onGround = true;
                    mc.thePlayer.jumpMovementFactor = reverseFactor.getDefaultValue();
                } else {
                    mc.thePlayer.onGround = false;
                }
            }
            if (event instanceof EventVelocity) {
                event.setCanceled(cancelVelocity.isToggled());
                ((EventVelocity) event).setHorizontal(velHorizontal.getDefaultValue() * 100);
                ((EventVelocity) event).setVertical(velVertical.getDefaultValue() * 100);
            }
        }

        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getType() == PacketEvent.Type.RECIVE) {
                switch (this.mode.getSelectedMode()) {
                    case "Simple":
                        if (((PacketEvent) event).getPacket() instanceof S12PacketEntityVelocity || ((PacketEvent) event).getPacket() instanceof S27PacketExplosion) {
                            event.setCanceled(true);
                            break;
                        }
                }
            }
        }

        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());
            switch (mode.getSelectedMode()) {
                case "Legit":
                    if (mc.thePlayer.hurtTime == 10 && mc.thePlayer.onGround)
                        mc.thePlayer.jump();
                    break;
                case "AAC4":
                    if (mc.thePlayer.hurtTime > 0) {
                        mc.thePlayer.motionX *= 0.6;
                        mc.thePlayer.motionZ *= 0.6;
                        mc.thePlayer.motionY *= 1;

                    }
                    break;
                case "Intave":
                    KillAura killaura = Koks.getKoks().moduleManager.getModule(KillAura.class);
                    if (mc.thePlayer.hurtTime == 9 || mc.thePlayer.hurtTime == 10) {
                        if (mc.thePlayer.onGround)
                            mc.thePlayer.jump();
                    }
                    if (mc.thePlayer.hurtTime == 5 || mc.thePlayer.hurtTime == 6 && killaura.isToggled() && killaura.finalEntity != null) {
                        double yaw = Math.toRadians(killaura.yaw);
                        double x = -Math.sin(yaw) * 0.12;
                        double z = Math.cos(yaw) * 0.12;
                        mc.thePlayer.setSprinting(false);
                        mc.thePlayer.motionX = x;
                        mc.thePlayer.motionZ = z;
                    }
                    break;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}