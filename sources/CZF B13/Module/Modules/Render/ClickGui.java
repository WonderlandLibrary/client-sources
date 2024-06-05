package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.UI.ClickUi.ClickUi;
import net.minecraft.client.gui.GuiIngame;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGui extends Module {
    public static float red;
    public static float green;
    public static float blue;
    public static boolean theme;
    public static Color Theme;
    public Numbers<Double> r = new Numbers<Double>("Red", "Red", 210.0, 0.0, 255.0, 1.0);
    public Numbers<Double> g = new Numbers<Double>("Green", "Green", 132.0, 0.0, 255.0, 1.0);
    public Numbers<Double> b = new Numbers<Double>("Green", "Blue", 246.0, 0.0, 255.0, 1.0);
    public Option<Boolean> white;

    public ClickGui() {
        super("ClickGui", new String[]{"clickui"}, ModuleType.Render);
        this.white = new Option<Boolean>("WhiteText", "WhiteText", true);
        this.setKey(Keyboard.KEY_RSHIFT);
        this.addValues(this.r, this.g, this.b);
    }

    @EventHandler
    public void onTick() {
        red = getRed() / 255.0f;
        green = getGreen() / 255.0f;
        blue = getBlue() / 255.0f;
    }

    @Override
    public void onEnable() {
        red = getRed() / 255.0f;
        green = getGreen() / 255.0f;
        blue = getBlue() / 255.0f;
        mc.displayGuiScreen(new ClickUi());
    }

    public int getRed() {
        return this.r.getValue().intValue();
    }

    public int getGreen() {
        return this.g.getValue().intValue();
    }

    public int getBlue() {
        return this.b.getValue().intValue();
    }
}


