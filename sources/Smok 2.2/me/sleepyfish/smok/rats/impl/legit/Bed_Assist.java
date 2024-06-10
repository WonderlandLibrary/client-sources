package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

// Class from SMok Client by SleepyFish
public class Bed_Assist extends Rat {

    DoubleSetting speedYaw;
    DoubleSetting speedPitch;
    DoubleSetting range;
    BoolSetting weaponOnly;
    BoolSetting aimlock;

    public Bed_Assist() {
        super("Bed Assist", Rat.Category.Legit, "Aims at beds");
    }

    @Override
    public void setup() {
        this.addSetting(this.speedYaw = new DoubleSetting("Speed yaw", "Also called 'Vertical Speed'", 1.2, 1.0, 5.0, 0.1));
        this.addSetting(this.speedPitch = new DoubleSetting("Speed pitch", "Also called 'Horizontal Speed'", 1.2, 1.0, 5.0, 0.1));
        this.addSetting(this.range = new DoubleSetting("Range", 3.2, 1.0, 6.0, 0.2));
        this.addSetting(this.weaponOnly = new BoolSetting("Weapon only", true));
        this.addSetting(this.aimlock = new BoolSetting("Aimlock", "Locks on beds", false));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (!this.weaponOnly.isEnabled() || Utils.holdingWeapon()) {
                double breakRange = this.range.getValue();

                for (double y = breakRange; y >= -breakRange; --y) {
                    for (double x = -breakRange; x <= breakRange; ++x) {
                        for (double z = -breakRange; z <= breakRange; ++z) {
                            BlockPos block = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                            if (Utils.getBlock(block) == Blocks.bed) {
                                float[] rots = this.getRotations(block);
                                mc.thePlayer.rotationYaw = rots[0];
                                mc.thePlayer.rotationPitch = rots[1];
                            }
                        }
                    }
                }
            }
        }
    }

    private float[] getRotations(BlockPos pos) {
        float[] rotations = Utils.Combat.getBlockRotations(pos, mc.objectMouseOver.sideHit.getOpposite());
        float sens = Smok.inst.rotManager.getSensitivity();
        if (!this.aimlock.isEnabled()) {
            rotations[0] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], 39.0F * (this.speedYaw.getValueToFloat() / 40.0F));
            rotations[1] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], 9.0F * (this.speedPitch.getValueToFloat() / 40.0F));
            rotations[0] = (float) Math.round(rotations[0] / sens) * sens;
            rotations[1] = (float) Math.round(rotations[1] / sens) * sens;
        }

        return new float[]{rotations[0], rotations[1]};
    }
}
