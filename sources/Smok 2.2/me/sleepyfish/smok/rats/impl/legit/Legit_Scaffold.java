package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import net.minecraft.client.settings.KeyBinding;

// Class from SMok Client by SleepyFish
public class Legit_Scaffold extends Rat {

    BoolSetting blocksOnly;
    BoolSetting backwardsOnly;
    BoolSetting betaFastMode;
    BoolSetting fixForward;
    BoolSetting onlyFixWithoutBlocks;

    DoubleSetting eagleFixWalkingTime;

    private Timer legitTimer = new Timer();

    public Legit_Scaffold() {
        super("Legit Scaffold", Category.Legit, "Also called 'Eagle'");
    }

    @Override
    public void setup() {
        this.addSetting(this.blocksOnly = new BoolSetting("Blocks Only", true));
        this.addSetting(this.backwardsOnly = new BoolSetting("Backwards Only", "Only works when walking Back", true));
        this.addSetting(this.betaFastMode = new BoolSetting("Fast Mode", "Beta Feature", false));
        this.addSetting(this.fixForward = new BoolSetting("Fix forward", "Unsneak when you Walk forward", false));
        this.addSetting(this.eagleFixWalkingTime = new DoubleSetting("Fix walking time", "How much time you need to move forward", 500.0, 50.0, 1000.0, 5.0));
        this.addSetting(this.onlyFixWithoutBlocks = new BoolSetting("Only fix without blocks", true));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (this.blocksOnly.isEnabled() && !Utils.holdingBlock()) {
                return;
            }

            if (this.backwardsOnly.isEnabled() && !Utils.isMovingBackwards()) {
                return;
            }

            if (this.fixForward.isEnabled() && mc.thePlayer.isSneaking() && Utils.isMovingForward()) {
                if (this.onlyFixWithoutBlocks.isEnabled() && Utils.holdingBlock()) {
                    return;
                }

                if (this.legitTimer.delay(this.eagleFixWalkingTime.getValueToLong())) {
                    if (mc.thePlayer.isSneaking() && Utils.isMovingForward()) {
                        this.setShift(false);
                        this.legitTimer.reset();
                    }
                }
            }

            if (this.betaFastMode.isEnabled()) {
                boolean overAir = Utils.overAirCustom(0.0, 0.05, 0.0) && mc.thePlayer.isSneaking() && mc.thePlayer.onGround;
                this.setShift(overAir);
            } else if (Utils.overAir(1.0) && mc.thePlayer.onGround) {
                this.setShift(true);
            } else if (!Utils.overAir(1.0) && mc.thePlayer.isSneaking() && mc.thePlayer.onGround) {
                this.setShift(false);
            }
        }
    }

    private void setShift(boolean shift) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), shift);
    }
}