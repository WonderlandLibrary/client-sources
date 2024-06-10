package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

// Class from SMok Client by SleepyFish
public class Auto_Tool extends Rat {

    public static BoolSetting bedCheck;

    private int oldSlot;
    private boolean allowSave;

    private Timer timer = new Timer();

    public Auto_Tool() {
        super("Auto Tool", Category.Legit, "Switches tools");
    }

    @Override
    public void setup() {
        this.addSetting(bedCheck = new BoolSetting("Bed checks", "Checks if a bed is nearby", true));
    }

    @Override
    public void onEnableEvent() {
        this.allowSave = true;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (!Utils.canLegitWork())
            return;

        // Goofy loofy hard code

        Block b = Utils.getBlock(mc.objectMouseOver.getBlockPos());
        if (b == null)
            return;

        if (MouseUtils.isButtonDown(MouseUtils.MOUSE_LEFT)) {
            if (this.bedCheck.isEnabled()) {
                if (!ClientUtils.bedNearby) {
                    return;
                }
            }

            if (this.allowSave) {
                this.oldSlot = mc.thePlayer.inventory.currentItem;
                this.allowSave = false;
            }

            if (this.timer.delay(400L)) {
                if (b == Blocks.end_stone || b == Blocks.sandstone || b == Blocks.obsidian || b == Blocks.stained_hardened_clay || b == Blocks.stone) {
                    if (!Utils.holdingPickaxe()) {
                        this.changeToPickaxe();
                    }
                }

                if (b.getLocalizedName().toLowerCase().contains("wood") || b == Blocks.ladder) {
                    if (!Utils.holdingAxe()) {
                        this.changeToAxe();
                    }
                }

                if (b == Blocks.wool || b == Blocks.web) {
                    this.changeToShears();
                }
            }
        } else {
            if (this.oldSlot != -1) {
                mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.allowSave = true;
                this.oldSlot = -1;
                this.timer.reset();
            }
        }
    }

    private void changeToPickaxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);

            if (iS != null && iS.getItem() instanceof ItemPickaxe) {
                Smok.inst.mc.thePlayer.inventory.currentItem = i;
            }
        }
    }

    private void changeToAxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);

            if (iS != null && iS.getItem() instanceof ItemAxe) {
                Smok.inst.mc.thePlayer.inventory.currentItem = i;
            }
        }
    }

    private void changeToShears() {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);

            if (iS != null && iS.getItem() instanceof ItemShears) {
                Smok.inst.mc.thePlayer.inventory.currentItem = i;
            }
        }
    }

}