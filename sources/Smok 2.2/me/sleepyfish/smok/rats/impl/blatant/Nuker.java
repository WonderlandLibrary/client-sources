package me.sleepyfish.smok.rats.impl.blatant;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;

// Class from SMok Client by SleepyFish
public class Nuker extends Rat {

    BoolSetting notify;
    BoolSetting retry;

    DoubleSetting breakingRange;
    ModeSetting<?> breakMode;

    private Timer timer = new Timer();

    public Nuker() {
        super("Nuker", Rat.Category.Blatant, "Destroys beds through blocks");
    }

    @Override
    public void setup() {
        this.addSetting(this.breakingRange = new DoubleSetting("Bed break range", 2.5, 1.0, 6.0, 0.5));
        this.addSetting(this.breakMode = new ModeSetting<>("Break Mode", Nuker.breakModes.UsePacket));
        this.addSetting(this.retry = new BoolSetting("Retry to Nuke", true));
        this.addSetting(this.notify = new BoolSetting("Notify", false));
    }

    @Override
    public void onEnableEvent() {
        Smok.inst.rotManager.stopRotate();
        Smok.inst.rotManager.rayTracePos = null;
        this.timer.reset();
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.stopRotate();
        Smok.inst.rotManager.rayTracePos = null;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
        this.timer.reset();
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            double breakRange = this.breakingRange.getValue();

            for (double y = breakRange; y >= -breakRange; --y) {
                for (double x = -breakRange; x <= breakRange; ++x) {
                    for (double z = -breakRange; z <= breakRange; ++z) {
                        BlockPos block = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);

                        if (Utils.getBlock(block) == Blocks.bed) {
                            Utils.BlockData data = Utils.Combat.getBlockDataFixed(block);
                            if (data == null)
                                return;

                            this.rotateBalls(data);

                            if (this.breakBlock(data)) {
                                Smok.inst.rotManager.rayTracePos = null;
                                Smok.inst.rotManager.stopRotate();

                                if (this.breakMode.getMode() == Nuker.breakModes.DamageBlock) {
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                                }

                                if (this.breakMode.getMode() == Nuker.breakModes.ActionPacket) {
                                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, block, data.face));
                                }

                                if (this.notify.isEnabled()) {
                                    ClientUtils.notify(this.getName(), "Tried to Nuke", Notification.Icon.Bell, 2L);
                                }

                                if (this.retry.isEnabled()) {
                                    this.timer.reset();
                                } else {
                                    Smok.inst.rotManager.rayTracePos = null;
                                    Smok.inst.rotManager.stopRotate();
                                    this.toggle();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void rotateBalls(Utils.BlockData block) {
        float[] rots = Utils.Combat.getBlockRotations(block.pos, block.face);
        float sens = Smok.inst.rotManager.getSensitivity();

        rots[0] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationYaw, rots[0], 210.0F);
        rots[1] = Smok.inst.rotManager.smoothRotation(mc.thePlayer.rotationPitch, rots[1], 120.0F);

        rots[0] = (float) Math.round(rots[0] / sens) * sens;
        rots[1] = (float) Math.round(rots[1] / sens) * sens;

        Smok.inst.rotManager.setRotations(rots[0], rots[1]);
        Smok.inst.rotManager.startRotate();
        Smok.inst.rotManager.rayTracePos = block.pos;
    }

    private boolean breakBlock(Utils.BlockData block) {
        if (this.breakMode.getMode() != Nuker.breakModes.Raytrace) {
            if (this.breakMode.getMode() == Nuker.breakModes.DamageBlock) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                mc.playerController.onPlayerDamageBlock(block.pos, block.face);
                return this.timer.delay(1200L) && Utils.getBlock(block.pos) == Blocks.air;
            }

            if (this.breakMode.getMode() == Nuker.breakModes.ActionPacket) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, block.pos, block.face));
                return this.timer.delay(1200L) && Utils.getBlock(block.pos) == Blocks.air;
            }

            if (this.breakMode.getMode() == Nuker.breakModes.UsePacket) {
                if (mc.objectMouseOver.sideHit != null) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                    KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                } else {
                    KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                }

                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, Utils.getCurrentItem());
                return this.timer.delay(350L) && Utils.getBlock(block.pos) == Blocks.air;
            }

            if (this.breakMode.getMode() == Nuker.breakModes.Controller) {
                mc.playerController.onPlayerDamageBlock(block.pos, block.face);

                mc.thePlayer.swingItem();
            }
        } else {
            if (mc.objectMouseOver.sideHit != null) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            } else {
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            }
        }

        return false;
    }

    public enum breakModes {
        Controller, DamageBlock, ActionPacket, UsePacket, Raytrace;
    }

}