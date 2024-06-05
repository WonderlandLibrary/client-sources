package zamorozka.modules.GUIMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;

public class ClickGui extends Module {
	
    public ClickGui() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.Hud);
    }

    @Override
    public void setup() {
		ArrayList<String> options1 = new ArrayList<>();
        options1.add("Defaulted");
        options1.add("ArrayColor");
        Zamorozka.settingsManager.rSetting(new Setting("Type", this, "Defaulted", options1));
        Zamorozka.settingsManager.rSetting(new Setting("GuiRed", this, 39, 0, 255, true));
        Zamorozka.settingsManager.rSetting(new Setting("GuiGreen", this, 30, 0, 255, true));
        Zamorozka.settingsManager.rSetting(new Setting("GuiBlue", this, 255, 0, 255, true));
    }
    
    public static ClickGUI clickGui;
    
    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(Zamorozka.clickGui);
        toggle();
    }
    public void onDisable() {
        super.onDisable();
        mc.displayGuiScreen(Zamorozka.clickGui);
    }
}
