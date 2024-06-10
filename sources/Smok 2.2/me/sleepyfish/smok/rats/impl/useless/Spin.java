package me.sleepyfish.smok.rats.impl.useless;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.impl.blatant.Aura;
import me.sleepyfish.smok.rats.impl.blatant.Scaffold;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.render.notifications.Notification;

// Class from SMok Client by SleepyFish
public class Spin extends Rat {

    DoubleSetting speed;

    BoolSetting serverSided;
    BoolSetting reversedHead;
    BoolSetting randomize;

    private float yaw;

    public Spin() {
        super("Spin", Rat.Category.Favorites, "Most useless module that ever exists");
    }

    @Override
    public void setup() {
        this.addSetting(this.speed = new DoubleSetting("Spin Speed", 5.0, 1.0, 100.0, 1.0));
        this.addSetting(this.serverSided = new BoolSetting("Server sided", false));
        this.addSetting(this.randomize = new BoolSetting("Randomize rotations", false));
        this.addSetting(this.reversedHead = new BoolSetting("Reversed head", "Makes your head rotate in the opposite direction", true));
        this.yaw = 0.0F;
    }

    @Override
    public void onEnableEvent() {
        if (mc.theWorld != null) {
            if (Smok.inst.ratManager.getRatByClass(Aura.class).isEnabled()) {
                Smok.inst.ratManager.getRatByClass(Aura.class).toggle();
            }

            if (Smok.inst.ratManager.getRatByClass(Scaffold.class).isEnabled()) {
                Smok.inst.ratManager.getRatByClass(Scaffold.class).toggle();
            }
        }

        Smok.inst.rotManager.stopRotate();
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.stopRotate();
    }

    @SmokEvent
    public void onTick(EventTick e) {
        float rotation;

        if (this.randomize.isEnabled()) {
            rotation = MathUtils.randomFloat(0.0F, 360.0F);
        } else {
            rotation = this.yaw;
        }

        if (this.serverSided.isEnabled()) {
            if (!Gui.blatantMode.isEnabled()) {
                this.serverSided.toggle();
                ClientUtils.notify("Spin", "U cant enable this setting without blatant mode", Notification.Icon.Info, 3L);
            }

            Smok.inst.rotManager.setRotations(rotation, mc.thePlayer.rotationPitch);
            Smok.inst.rotManager.startRotate();
        } else {
            Smok.inst.rotManager.stopRotate();

            if (this.reversedHead.isEnabled()) {
                mc.thePlayer.rotationYawHead = -rotation;
            } else {
                mc.thePlayer.rotationYawHead = rotation;
            }

            mc.thePlayer.renderYawOffset = rotation;
        }

        this.yaw += this.speed.getValueToFloat();
    }

}