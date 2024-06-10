package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import java.awt.Robot;

// Class from SMok Client by SleepyFish
public class Clicker extends Rat {

    BoolSetting right;
    DoubleSetting rightCpsMin;
    DoubleSetting rightCpsMax;
    BoolSetting onlyWhileLooking;
    BoolSetting onlyWhileHoldingBlock;

    BoolSetting left;
    DoubleSetting leftCpsMax;
    DoubleSetting leftCpsMin;
    BoolSetting onlyWhileTargeting;
    BoolSetting checkBlocks;
    BoolSetting weaponOnly;
    BoolSetting hitselect;

    ModeSetting<Enum<?>> clickMode;

    private Timer Ltimer = new Timer();
    private Timer Rtimer = new Timer();

    private Robot LRobot;
    private Robot RRobot;

    public Clicker() {
        super("Clicker", Rat.Category.Legit, "Clicks for you");
    }

    @Override
    public void setup() {
        this.addSetting(this.right = new BoolSetting("Right", true));
        this.addSetting(this.rightCpsMin = new DoubleSetting("Right CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.rightCpsMax = new DoubleSetting("Right CPS Max", 12.0, 2.0, 25.0, 1.0));
        this.addSetting(this.onlyWhileLooking = new BoolSetting("Only while looking", true));
        this.addSetting(this.onlyWhileHoldingBlock = new BoolSetting("Only block in hand", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.left = new BoolSetting("Left", true));
        this.addSetting(this.leftCpsMin = new DoubleSetting("Left CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.leftCpsMax = new DoubleSetting("Left CPS Max", 12.0, 2.0, 25.0, 1.0));
        this.addSetting(this.onlyWhileTargeting = new BoolSetting("Only while targeting", false));
        this.addSetting(this.checkBlocks = new BoolSetting("Check block Break", "Allows you to break blocks", true));
        this.addSetting(this.weaponOnly = new BoolSetting("Weapon only", true));
        this.addSetting(this.hitselect = new BoolSetting("Hit select", "Get insane combos with this", false));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.clickMode = new ModeSetting<>("Click Mode", clickModes.Robot));

        try {
            this.LRobot = new Robot();
        } catch (Exception e) {
            ClientUtils.addMessage("Error: Clicker left robot");
        }

        try {
            this.RRobot = new Robot();
        } catch (Exception e) {
            ClientUtils.addMessage("Error: Clicker right robot");
        }
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (!Utils.canLegitWork())
            return;

        if (this.left.isEnabled() && MouseUtils.isButtonDown(MouseUtils.MOUSE_LEFT)) {
            if (this.onlyWhileTargeting.isEnabled() && mc.objectMouseOver.typeOfHit != MovingObjectType.ENTITY)
                return;

            if (this.weaponOnly.isEnabled() && !Utils.holdingWeapon())
                return;

            if (this.checkBlocks.isEnabled()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                if (blockPos != null) {
                    Block block = Utils.getBlock(blockPos);
                    if (block != Blocks.air && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water)
                        return;
                }
            }

            if (!this.hitselect.isEnabled()) {
                if (this.Ltimer.cpsTimer(this.leftCpsMin.getValueToInt(), this.leftCpsMax.getValueToInt())) {
                    this.clickMouse(MouseUtils.MOUSE_LEFT);
                    this.Ltimer.reset();
                }
            } else if (this.Ltimer.cpsTimer(1, 2)) {
                this.clickMouse(MouseUtils.MOUSE_LEFT);
                this.Ltimer.reset();
            }
        }

        if (this.right.isEnabled() && MouseUtils.isButtonDown(MouseUtils.MOUSE_RIGHT)) {
            if (this.onlyWhileLooking.isEnabled() && mc.objectMouseOver.typeOfHit != MovingObjectType.BLOCK)
                return;

            if (this.onlyWhileHoldingBlock.isEnabled() && !Utils.holdingBlock())
                return;

            if (this.Rtimer.cpsTimer(this.rightCpsMin.getValueToInt(), this.rightCpsMax.getValueToInt())) {
                this.clickMouse(MouseUtils.MOUSE_RIGHT);
                this.Rtimer.reset();
            }
        }
    }

    private void clickMouse(int button) {
        if (clickMode.getMode() == clickModes.KeyBindState) {
            if (button == MouseUtils.MOUSE_RIGHT) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
            }

            if (button == MouseUtils.MOUSE_LEFT) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            }
        }

        if (clickMode.getMode() == clickModes.Robot) {
            if (button == MouseUtils.MOUSE_RIGHT) {
                this.RRobot.mousePress(MouseUtils.MOUSE_RIGHT_EVENT);
            }

            if (button == MouseUtils.MOUSE_LEFT) {
                this.LRobot.mousePress(MouseUtils.MOUSE_LEFT_EVENT);
            }
        }
    }

    public enum clickModes {
        KeyBindState, Robot;
    }

}