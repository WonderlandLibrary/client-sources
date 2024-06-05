package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 12.08.2020.
 */
public class FakeBlock extends Mod {

    String[] blockModes = {"1.8", "1.7", "Eject", "Only Z", "Custom"};
    public Setting blockMode = new Setting("Block Mode", blockModes, "1.8", this);
    public Setting onlySword = new Setting("Only Sword", false, this);
    public Setting blockHeight = new Setting("Block Height", -0.50F, 0.25F, 0.0F, false, this);
    public Setting blockMultiplier = new Setting("Animation Value Multiply", 0.0F, 1.0F, 1.0F, false, this);
    public Setting blockAddition = new Setting("Animation Value Add", -1.0F, 1.0F, 0.0F, false, this);

    public FakeBlock() {
        super("FakeBlock", Keyboard.KEY_NONE, false, ModCategory.HUD);

        Init.getInstance().settingManager.addSetting(blockMode);
        Init.getInstance().settingManager.addSetting(onlySword);
        Init.getInstance().settingManager.addSetting(blockHeight);
        Init.getInstance().settingManager.addSetting(blockMultiplier);
        Init.getInstance().settingManager.addSetting(blockAddition);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() == Type.RENDER2D) {
            if (blockMode.getSelectedCombo().equals("Custom")) {
                blockMultiplier.setVisible(true);
                blockAddition.setVisible(true);
            } else {
                blockMultiplier.setVisible(false);
                blockAddition.setVisible(false);
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}