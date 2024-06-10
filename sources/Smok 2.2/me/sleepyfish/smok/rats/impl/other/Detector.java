package me.sleepyfish.smok.rats.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.utils.entities.BotUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class Detector extends Rat {

    BoolSetting flyChecks;
    BoolSetting speedChecks;

    BoolSetting reachChecks;
    DoubleSetting reachValue;

    BoolSetting veloChecks;
    DoubleSetting veloValue;

    BoolSetting invalidChecks;

    ModeSetting<Enum<?>> mode;

    private int counter = 1;

    Timer timer1 = new Timer();
    Timer timer2 = new Timer();

    public Detector() {
        super("Detector", Rat.Category.Other, "Client sided AC that is made by SleepyFish");
    }

    @Override
    public void setup() {
        this.addSetting(this.flyChecks = new BoolSetting("Fly Checks", "Bad Checks", false));
        this.addSetting(this.speedChecks = new BoolSetting("Speed Checks", "Mid Checks", true));
        this.addSetting(this.veloChecks = new BoolSetting("Velocity / BHop Checks", "Bad Checks", true));
        this.addSetting(this.veloValue = new DoubleSetting("Velocity Value", 1.86, 1.6, 3.0, 0.02));
        this.addSetting(this.reachChecks = new BoolSetting("Reach Checks", "Insane Checks", true));
        this.addSetting(this.reachValue = new DoubleSetting("Reach Value", 3.1, 3.0, 5.0, 0.02));
        this.addSetting(this.invalidChecks = new BoolSetting("Invalid Checks", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.mode = new ModeSetting<>("Message Mode", Detector.modes.Chat));
    }

    @Override
    public void onDisableEvent() {
        this.counter = 1;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (this.timer1.delay(1000L)) {
            for (EntityPlayer target : mc.theWorld.playerEntities) {
                if (!Smok.inst.debugMode && target == mc.thePlayer) {
                    return;
                }

                if (target != Utils.Npc.getNpc() && !BotUtils.isBot(target)) {
                    if (target.isDead || target.isSpectator()) {
                        return;
                    }

                    if (this.flyChecks(target)) {
                        this.send(target, "fly");
                    }

                    if (this.speedChecks(target)) {
                        this.send(target, "speed / noSlow");
                    }

                    if (this.reachChecks(target)) {
                        this.send(target, "reach");
                    }

                    if (this.veloABhopChecks(target)) {
                        this.send(target, "velocity / bhop");
                    }

                    if (this.invalidChecks(target)) {
                        this.send(target, "invalid / pitch");
                    }
                }
            }

            this.timer1.reset();
        }
    }

    private boolean flyChecks(EntityPlayer target) {
        return this.flyChecks.isEnabled() && target.capabilities.isFlying && !target.isSpectator();
    }

    private boolean speedChecks(EntityPlayer target) {
        if (this.speedChecks.isEnabled()) {
            if (!target.capabilities.isFlying) {
                String motionX = "" + target.motionX;
                if (target.isBlocking() && target.onGround && (target.motionX > 0.03F || motionX.startsWith("-0.03"))) {
                    return true;
                }

                if (target.isSneaking() && target.onGround && target.motionZ > 0.05F) {
                    return true;
                }

                if (target.onGround && Math.round(target.motionY) != 0L) {
                    return true;
                }
            }

            if (target.isSneaking() && target.onGround && !target.velocityChanged && target.hurtTime != 0) {
                return true;
            }
        }

        return false;
    }

    private boolean veloABhopChecks(EntityPlayer target) {
        return this.veloChecks.isEnabled()
                && target.moveForward != 0.0F
                && target.moveStrafing != 0.0F
                && (target.motionX > this.veloValue.getValue() || target.motionZ > this.veloValue.getValue() || target.motionY > this.veloValue.getValue());
    }

    private boolean reachChecks(EntityPlayer target) {
        if (this.reachChecks.isEnabled()) {
            if (Utils.Combat.inRange(target, this.reachValue.getValue() + 4.0) && target.swingProgress > 0.0F) {
                if (mc.thePlayer.posX - target.posX > this.reachValue.getValue()) {
                    return true;
                }

                if (mc.thePlayer.posY - target.posY > this.reachValue.getValue()) {
                    return true;
                }

                if (mc.thePlayer.posZ - target.posZ > this.reachValue.getValue()) {
                    return true;
                }
            }

            if (!target.canAttackPlayer(mc.thePlayer) && mc.thePlayer.hurtTime != 0 && target.swingProgress != 0.0F) {
                return true;
            }
        }

        return false;
    }

    private boolean invalidChecks(EntityPlayer target) {
        if (this.invalidChecks.isEnabled() && target.rotationPitch > 90.0F) {
            return true;
        } else {
            return (float) Math.round(target.motionX) > 5.0F || (float) Math.round(target.motionY) > 5.0F || (float) Math.round(target.motionZ) > 5.0F;
        }
    }

    private void send(EntityPlayer target, String str) {
        if (Gui.moduleNotify.isEnabled()) {
            if (target != null && this.timer2.delay(this.mode.getMode() == Detector.modes.Chat ? 50L : 3000L)) {
                if (this.mode.getMode() == Detector.modes.Chat) {
                    ClientUtils.addMessage(
                            "Detector: " + target.getName() + ChatFormatting.RED + " failed " + ChatFormatting.WHITE + str + ". [x" + this.counter + "]"
                    );
                }

                if (this.mode.getMode() == Detector.modes.Notification) {
                    ClientUtils.notify(
                            "Detector " + this.counter,
                            target.getName() + ChatFormatting.RED + " failed " + ChatFormatting.WHITE + str + ".",
                            Notification.Icon.Info,
                            3L
                    );
                }

                ++this.counter;
                this.timer2.reset();
            }
        }
    }

    public enum modes {
        Chat, Notification;
    }

}