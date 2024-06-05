package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 14.08.2020.
 */
public class AntiVoid extends Mod {

    String[] modes = {"Push Up", "Fake Fly"};
    public Setting mode = new Setting("BugUp Mode", modes, "Fake Fly", this);
    TimeUtil timeUtil = new TimeUtil();
    private double x, y, z;
    private int count;

    public AntiVoid() {
        super("AntiVoid", Keyboard.KEY_NONE, true, ModCategory.PLAYER);

        Init.getInstance().settingManager.addSetting(mode);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;

        if (event.getType() == Type.TICKUPDATE) {

            if (mode.getSelectedCombo().equals("Push Up")) {
                if (count != 0) {
                    mc.gameSettings.keyBindForward.pressed = false;
                    mc.gameSettings.keyBindBack.pressed = false;
                    mc.gameSettings.keyBindLeft.pressed = false;
                    mc.gameSettings.keyBindRight.pressed = false;
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                }
                if (count == 1) {
                    if (timeUtil.isDelayComplete(250)) {
                        mc.thePlayer.setPosition(x, y, z);
                        timeUtil.reset();
                        count++;
                    }
                } else {
                    timeUtil.reset();
                }
            }

            for (int i = 0; i < mc.thePlayer.posY + 1; i++) {
                if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                    timeUtil.reset();
                    return;
                }
                if (mode.getSelectedCombo().equals("Push Up")) {
                    if (mc.thePlayer.fallDistance >= 1 && count == 0) {
                        mc.thePlayer.motionY = 1;
                        mc.thePlayer.fallDistance = 0;
                        count = 1;
                    }
                }

                if (mode.getSelectedCombo().equals("Fake Fly")) {
                    if (mc.thePlayer.fallDistance >= 1 && count == 0) {
                        mc.thePlayer.motionY = 0;
                        mc.thePlayer.fallDistance = 0;
                        count++;
                    }
                }
            }

            if (mc.thePlayer.onGround) {
                count = 0;
                x = mc.thePlayer.posX + 0.25;
                y = mc.thePlayer.posY;
                z = mc.thePlayer.posZ + 0.25;
                timeUtil.reset();
            }
        }
    }

    @Override
    public void onEnable() {
        count = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
