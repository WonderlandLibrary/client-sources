package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.entities.BotUtils;
import me.sleepyfish.smok.utils.entities.friend.FriendUtils;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class Aim_Assist extends Rat {

    DoubleSetting speedYaw;

    BoolSetting aimPitch;
    DoubleSetting speedPitch;

    DoubleSetting range;
    DoubleSetting fovRange;

    BoolSetting thruWalls;
    BoolSetting clickAim;
    BoolSetting stopBlocking;
    BoolSetting checkBlock;
    BoolSetting weaponOnly;
    BoolSetting aimlock;
    public static BoolSetting ignoreFriends;
    BoolSetting ignoreInvis;
    BoolSetting addSmoothness;
    BoolSetting multipoint;
    BoolSetting increaseStrafe;

    BoolSetting prediction;
    DoubleSetting predictionValue;
    ModeSetting<Enum<?>> predictMode;

    BoolSetting randomization;
    ModeSetting<Enum<?>> randomMode;
    DoubleSetting randomYawMin;
    DoubleSetting randomYawMax;
    DoubleSetting randomPitchMin;
    DoubleSetting randomPitchMax;

    public Aim_Assist() {
        super("Aim Assist", Rat.Category.Legit, "Aims at targets");
    }

    @Override
    public void setup() {
        this.addSetting(this.speedYaw = new DoubleSetting("Horizontal Speed", "Also called 'Yaw Speed'", 1.2, 1.0, 8.0, 0.1));
        this.addSetting(this.aimPitch = new BoolSetting("Aim Vertically", "Also called 'Aim Pitch'", true));
        this.addSetting(this.speedPitch = new DoubleSetting("Vertical Speed", "Also called 'Pitch Speed'", 1.2, 1.0, 8.0, 0.1));
        this.addSetting(this.fovRange = new DoubleSetting("Fov", "Also called 'Max Angle'", 75.0, 15.0, 360.0, 5.0));
        this.addSetting(this.range = new DoubleSetting("Range", 3.4, 1.0, 6.0, 0.2));
        this.addSetting(ignoreFriends = new BoolSetting("Ignore friends", true));
        this.addSetting(this.ignoreInvis = new BoolSetting("Ignore Invisible", true));
        this.addSetting(this.weaponOnly = new BoolSetting("Weapon only", true));
        this.addSetting(this.multipoint = new BoolSetting("Multipoint", false));
        this.addSetting(this.increaseStrafe = new BoolSetting("Increase strafe",  "Increases value (uses prediction value)", false));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.thruWalls = new BoolSetting("Through walls", "Allows you to aim through walls", true));
        this.addSetting(this.clickAim = new BoolSetting("Click Aim", "Only works when clicking", false));
        this.addSetting(this.stopBlocking = new BoolSetting("Check Blocking", "Doesn't aim when blocking", true));
        this.addSetting(this.checkBlock = new BoolSetting("Check block Break", false));
        this.addSetting(this.aimlock = new BoolSetting("Aimlock", "Locks on Targets", false));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.prediction = new BoolSetting("Predict", "Adds or removes rotation yaw when moving sideways", false));
        this.addSetting(this.predictMode = new ModeSetting<>("Mode", Aim_Assist.predictModes.Same));
        this.addSetting(this.predictionValue = new DoubleSetting("Predict Value", "Yaw + or - value", 2.5F, 0.5F, 10.0F, 0.25F));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.randomization = new BoolSetting("Randomize", false));
        this.addSetting(this.randomMode = new ModeSetting<>("Mode", Aim_Assist.randomModes.Random));
        this.addSetting(this.randomYawMin = new DoubleSetting("Random Yaw Min", 6.5F, 1.0F, 20.0F, 0.5F));
        this.addSetting(this.randomYawMax = new DoubleSetting("Random Yaw Max", 8.0F, 1.5F, 20.5F, 0.5F));
        this.addSetting(this.randomPitchMin = new DoubleSetting("Random Pitch Min", 10.0F, 1.0F, 40.0F, 0.5F));
        this.addSetting(this.randomPitchMax = new DoubleSetting("Random Pitch Max", 14.5F, 1.5F, 40.5F, 0.5F));
        this.addSetting(this.addSmoothness = new BoolSetting("Add Smoothness", "Removes speed by calculating the left Fov to your next Target", false));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (!this.weaponOnly.isEnabled() || Utils.holdingWeapon()) {
                if (this.checkBlock.isEnabled()) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                    if (blockPos != null) {
                        Block block = Utils.getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());

                        if (block != Blocks.air && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water)
                            return;
                    }
                }

                if (!this.clickAim.isEnabled() || MouseUtils.isButtonDown(0)) {
                    Entity target = this.getTarget();

                    if (target != null) {
                        float[] rotations = this.getRotations(target);

                        if (this.multipoint.isEnabled()) {
                            if (mc.pointedEntity == target) {
                                return;
                            }
                        }

                        if (this.stopBlocking.isEnabled())
                            if (mc.thePlayer.isBlocking())
                                return;

                        if (this.increaseStrafe.isEnabled()) {
                            if (mc.thePlayer.moveStrafing != 0) {
                                float value = 5;

                                if (this.prediction.isEnabled()) {
                                    value = this.predictionValue.getValueToFloat();
                                }

                                rotations[0] += value;
                            }
                        }

                        if (this.aimPitch.isEnabled())
                            mc.thePlayer.rotationPitch = rotations[1];

                        mc.thePlayer.rotationYaw = rotations[0];
                    }
                }
            }
        }
    }

    private float[] getRotations(Entity target) {
        float[] rotations = Utils.Combat.getEntityRotations(target);
        float sens = Smok.inst.rotManager.getSensitivity();

        if (!this.aimlock.isEnabled()) {
            float speedY = 2.0F * this.speedYaw.getValueToFloat();
            float speedP = 0.5F * this.speedPitch.getValueToFloat();

            if (this.randomization.isEnabled()) {
                float randomYaw = MathUtils.randomFloat(this.randomYawMin.getValueToFloat() / 2.0F, this.randomYawMax.getValueToFloat() / 2.0F);
                float randomPitch = MathUtils.randomFloat(this.randomPitchMin.getValueToFloat() / 2.0F, this.randomPitchMax.getValueToFloat() / 2.0F);

                int randomMinus = 0;

                if (this.randomMode.getMode() == randomModes.Random) {
                    randomMinus = MathUtils.randomInt(0, 1);
                }

                if (this.randomMode.getMode() == randomModes.Minus) {
                    randomMinus = 1;
                }

                if (this.randomMode.getMode() == randomModes.Plus) {
                    randomMinus = 0;
                }

                if (randomMinus == 0) {
                    rotations[0] -= randomYaw;
                    rotations[1] += randomPitch;
                }

                if (randomMinus == 1) {
                    rotations[0] += randomYaw;
                    rotations[1] -= randomPitch;
                }
            }

            if (this.addSmoothness.isEnabled()) {
                float endRot = Math.round(rotations[0] - mc.thePlayer.rotationYaw);

                ClientUtils.addMessage("Fov " + endRot);

                if (endRot > this.fovRange.getValueToFloat() / 2.0F) {
                    speedY /= 1.5F;
                    ClientUtils.addMessage("true 1");
                }

                if (endRot > this.fovRange.getValueToFloat() / 3.0F) {
                    speedY /= 2.0F;
                    ClientUtils.addMessage("true 2");
                }

                if (endRot > this.fovRange.getValueToFloat() / 4.0F) {
                    speedY /= 2.5F;
                    ClientUtils.addMessage("true 1");
                }

                if (endRot > this.fovRange.getValueToFloat() / 8.0F) {
                    speedY /= 3.0F;
                    ClientUtils.addMessage("true 2");
                }
            }

            if (this.prediction.isEnabled()) {
                float playerStrafe = mc.thePlayer.moveStrafing;

                if (playerStrafe != 0) {
                    if (mc.thePlayer.isSneaking()) {
                        if (playerStrafe > 0.2F) {
                            if (this.predictMode.getMode() == predictModes.Same) {
                                // left sneak prediction mode same
                                rotations[0] -= this.predictionValue.getValueToFloat() / 4;
                            }

                            if (this.predictMode.getMode() == predictModes.Opposite) {
                                // left sneak prediction mode opposite
                                rotations[0] += this.predictionValue.getValueToFloat() / 4;
                            }
                        }

                        if (playerStrafe < -0.2F) {
                            if (this.predictMode.getMode() == predictModes.Same) {
                                // right sneak prediction mode same
                                rotations[0] += this.predictionValue.getValueToFloat() / 4;
                            }

                            if (this.predictMode.getMode() == predictModes.Opposite) {
                                // right sneak prediction mode opposite
                                rotations[0] -= this.predictionValue.getValueToFloat() / 4;
                            }
                        }
                    } else {
                        if (playerStrafe > 0.6F) {
                            if (this.predictMode.getMode() == predictModes.Same) {
                                // left walk prediction mode same
                                rotations[0] -= this.predictionValue.getValueToFloat();
                            }

                            if (this.predictMode.getMode() == predictModes.Opposite) {
                                // left walk prediction mode opposite
                                rotations[0] += this.predictionValue.getValueToFloat();
                            }
                        }

                        if (playerStrafe < -0.6F) {
                            if (this.predictMode.getMode() == predictModes.Same) {
                                // right walk prediction mode same
                                rotations[0] += this.predictionValue.getValueToFloat();
                            }

                            if (this.predictMode.getMode() == predictModes.Opposite) {
                                // right walk prediction mode opposite
                                rotations[0] -= this.predictionValue.getValueToFloat();
                            }
                        }
                    }
                }
            }

            rotations[0] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], speedY);
            rotations[1] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], speedP);

            rotations[0] = (float) Math.round(rotations[0] / sens) * sens;
            rotations[1] = (float) Math.round(rotations[1] / sens) * sens;
        }

        return new float[]{rotations[0], rotations[1]};
    }

    // triangle for Syz $$$$
    private Entity getTarget() {
        Iterator<EntityPlayer> players = mc.theWorld.playerEntities.iterator();

        EntityPlayer target;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!players.hasNext())
                                            return null;

                                        target = players.next();
                                    } while (target == mc.thePlayer);
                                } while (BotUtils.isBot(target));
                            } while (target.isDead);
                        } while (!this.thruWalls.isEnabled() && !mc.thePlayer.canEntityBeSeen(target));
                    } while (ignoreInvis.isEnabled() && target.isInvisible());
                } while (ignoreFriends.isEnabled() && (FriendUtils.ignoreFriend(target) || mc.thePlayer.isOnSameTeam(target)));

            } while (!Utils.Combat.inRange(target, range.getValue()));
        } while (!aimlock.isEnabled() && !Utils.Combat.isInFov(target, (float) fovRange.getValue()));

        return target;
    }

    public enum predictModes {
        Same, Opposite; // random in 2.1
    }

    public enum randomModes {
        Random, Plus, Minus;
    }

}