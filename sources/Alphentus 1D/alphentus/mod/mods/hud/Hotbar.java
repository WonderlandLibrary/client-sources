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
 * @since on 04/08/2020.
 */
public class Hotbar extends Mod {

    public Setting blur = new Setting("Blur", false, this);


    public Hotbar () {
        super("Hotbar", Keyboard.KEY_NONE, false, ModCategory.HUD);
        Init.getInstance().settingManager.addSetting(blur);
    }

    @EventTarget
    public void event (Event event) {
        if (event.getType() != Type.RENDER2D)
            return;

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
            blur.setVisible(true);
        } else {
            blur.setVisible(false);
        }
    }
}