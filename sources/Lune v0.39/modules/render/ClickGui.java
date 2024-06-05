package me.superskidder.lune.modules.render;

import me.superskidder.lune.guis.blurclickgui.Gui;
import me.superskidder.lune.guis.clickgui2.VapeClickGui;
import me.superskidder.lune.guis.clickguis.tomorrow.MainWindow;
import me.superskidder.lune.guis.nullclickgui.ClickyUI;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.guis.clickgui.Clickgui;
import me.superskidder.lune.values.type.Mode;
import org.lwjgl.input.Keyboard;

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
