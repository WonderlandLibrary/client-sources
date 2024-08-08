package me.napoleon.napoline.modules.render;

import org.lwjgl.input.Keyboard;

import me.napoleon.napoline.guis.blurclickgui.Gui;
import me.napoleon.napoline.guis.clickgui.Clickgui;
import me.napoleon.napoline.guis.clickgui2.VapeClickGui;
import me.napoleon.napoline.guis.clickguis.tomorrow.MainWindow;
import me.napoleon.napoline.guis.nullclickgui.ClickyUI;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

public class ClickGui extends Mod {
    public static Mode<Enum<?>> mod = new Mode<>("Mode", Mods.values(), Mods.New);

    public ClickGui() {
        super("ClickGui", ModCategory.Render,"Display a settings gui");
        this.setKey(Keyboard.KEY_RSHIFT);
        this.addValues(mod);
    }

    public enum Mods {
        New,
        Light,
        Dark,
        Blur,
        Windows,
        Tomorrow
    }

    @Override
    public void onEnabled() {
        if (mod.getValue() == Mods.Light) {
            Clickgui.setLight();
            mc.displayGuiScreen(new Clickgui());
        } else if (mod.getValue() == Mods.Dark) {
            Clickgui.setDark();
            mc.displayGuiScreen(new Clickgui());
        } else if (mod.getValue() == Mods.New) {
            Clickgui.setDark();
            mc.displayGuiScreen(new VapeClickGui());
        } else if (mod.getValue() == Mods.Blur) {
            mc.displayGuiScreen(new Gui());
        } else if (mod.getValue() == Mods.Windows) {
            mc.displayGuiScreen(new ClickyUI());
        } else if (mod.getValue() == Mods.Tomorrow) {
            mc.displayGuiScreen(new MainWindow());
        }
        this.setStage(false);
    }
}
