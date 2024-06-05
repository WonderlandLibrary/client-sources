package alphentus.mod.mods.visuals;

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
 * @since on 29/07/2020.
 */
public class Cosmetics extends Mod {

    public Setting settingWings = new Setting("Wings", true, this);
    public Setting settingCape = new Setting("Cape", false, this);

    public Setting settingAura = new Setting("Aura", false, this);
    public Setting settingAuraSpeed = new Setting("Aura Creeper", 1, 5, 2, true, this);

    public Setting settingAuraRed = new Setting("Red Aura", 1, 255, Init.getInstance().CLIENT_COLOR.getRed(), true, this);
    public Setting settingAuraGreen = new Setting("Green Aura", 1, 255, Init.getInstance().CLIENT_COLOR.getGreen(), true, this);
    public Setting settingAuraBlue = new Setting("Blue Aura", 1, 255, Init.getInstance().CLIENT_COLOR.getBlue(), true, this);

    public String[] modes = {"Wither", "Creeper"};
    public Setting settingAuraModes = new Setting("Aura Modes", modes, "Wither", this);

    public Setting rainbow = new Setting("RainBow", false, this);

    public Cosmetics() {
        super("Cosmetics", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
        Init.getInstance().settingManager.addSetting(settingWings);
        Init.getInstance().settingManager.addSetting(settingCape);
        Init.getInstance().settingManager.addSetting(settingAura);
        Init.getInstance().settingManager.addSetting(settingAuraModes);

        Init.getInstance().settingManager.addSetting(settingAuraRed);
        Init.getInstance().settingManager.addSetting(settingAuraGreen);
        Init.getInstance().settingManager.addSetting(settingAuraBlue);
        Init.getInstance().settingManager.addSetting(settingAuraSpeed);

        Init.getInstance().settingManager.addSetting(rainbow);

    }

    @EventTarget
    public void render2D(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;


        if (settingAura.isState()) {
            settingAuraBlue.setVisible(true);
            settingAuraRed.setVisible(true);
            settingAuraSpeed.setVisible(true);
            settingAuraGreen.setVisible(true);
            settingAuraModes.setVisible(true);
        } else {
            settingAuraBlue.setVisible(false);
            settingAuraRed.setVisible(false);
            settingAuraSpeed.setVisible(false);
            settingAuraGreen.setVisible(false);
            settingAuraModes.setVisible(false);
        }
        if(rainbow.isState()){
            settingAuraBlue.setVisible(false);
            settingAuraRed.setVisible(false);
            settingAuraSpeed.setVisible(false);
            settingAuraGreen.setVisible(false);
        }
    }


}
