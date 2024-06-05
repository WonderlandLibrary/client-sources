package alphentus.mod.mods.visuals;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * @author avox
 * @since on 30/07/2020.
 */
public class ClickGUI extends Mod {


    public Setting modes = new Setting("Mode", new String[]{"Sigma", "Prestige", "Alphentus"}, "Sigma", this);

    public Setting themeModes = new Setting("Themes", new String[]{""}, "", this);

    public Setting settingSmoothScrolling = new Setting("Smooth Scrolling", true, this);

    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, false, ModCategory.VISUALS);
        Init.getInstance().settingManager.addSetting(modes);
        Init.getInstance().settingManager.addSetting(themeModes);
        Init.getInstance().settingManager.addSetting(settingSmoothScrolling);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;
        if (modes.getSelectedCombo().equals("Alphentus")) {
            this.themeModes.setVisible(true);
        }else{
            this.themeModes.setVisible(false);
        }
    }

    @Override
    public void onEnable() {

        switch (modes.getSelectedCombo()){
            case "Sigma":
                Minecraft.getMinecraft().displayGuiScreen(Init.getInstance().clickGUISigma);
                break;
            case "Prestige":
                Minecraft.getMinecraft().displayGuiScreen(Init.getInstance().clickGUI);
                break;
            case "Alphentus":
                mc.displayGuiScreen(Init.getInstance().panelClickGUI);
                break;
        }

        setState(false);
        super.onEnable();
    }
}