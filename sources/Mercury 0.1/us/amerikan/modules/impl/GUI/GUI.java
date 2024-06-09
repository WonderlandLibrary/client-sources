/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.GUI;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import us.amerikan.amerikan;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class GUI
extends Module {
    public GUI() {
        super("GUI", "GUI", 54, Category.GUI);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Client");
        options.add("HeroCode");
        amerikan.setmgr.rSetting(new Setting("Design", this, "Client", options));
        amerikan.setmgr.rSetting(new Setting("Sound", this, true));
        amerikan.setmgr.rSetting(new Setting("GuiRed", this, 255.0, 0.0, 255.0, true));
        amerikan.setmgr.rSetting(new Setting("GuiGreen", this, 26.0, 0.0, 255.0, true));
        amerikan.setmgr.rSetting(new Setting("GuiBlue", this, 42.0, 0.0, 255.0, true));
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(amerikan.instance.clickgui);
        this.toggle();
        super.onEnable();
    }
}

