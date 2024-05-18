package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.gui.clickgui.ClickGui;
import me.finz0.osiris.gui.clickgui.ClickGuiScreen;
import me.finz0.osiris.gui.clickgui.elements.Frame;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.chat.Announcer;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGuiModule extends Module {

    public ClickGuiModule INSTANCE;
    public ClickGuiModule() {
        super("ClickGUI", Category.GUI, "Opens the ClickGUI");
        setBind(Keyboard.KEY_P);
        INSTANCE = this;
    }

    public static Setting theme;

    public static Setting shadow;
    public static Setting rainbow;

    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;

    private static int color;
    public static boolean isLight = false;

    public void setup(){
        ArrayList<String> themes = new ArrayList<>();
        themes.add("Dark");
        themes.add("Light");

        theme = new Setting("Theme", this, "Dark", themes, "ClickGuiTheme");
        AuroraMod.getInstance().settingsManager.rSetting(theme);

        shadow = new Setting("Shadow", this, false, "ClickGuiShadow");
        AuroraMod.getInstance().settingsManager.rSetting(shadow);

        rainbow = new Setting("Rainbow", this, true, "ClickGuiRainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);

        red = new Setting("Red", this, 170, 0, 255, true, "ClickGuiRed");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        green = new Setting("Green", this, 170, 0, 255, true, "ClickGuiGreen");
        AuroraMod.getInstance().settingsManager.rSetting(green);
        blue = new Setting("Blue", this, 170, 0, 255, true, "ClickGuiBlue");
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        alpha = new Setting("Alpha", this, 170, 0, 255, true, "ClickGuiAlpha");
        AuroraMod.getInstance().settingsManager.rSetting(alpha);

    }

    public static int getColor() {
        return rainbow.getValBoolean() ? ColourUtils.rainbow().getRGB() : color;
    }

    public static void setColor() {
        color = ColourUtils.color(red.getValInt(),
                green.getValInt(),
                blue.getValInt(),
                alpha.getValInt());
    }

    @Override
    public void onEnable() {
        isLight = theme.getValString().equalsIgnoreCase("Light");
        setColor();
        mc.displayGuiScreen(AuroraMod.getInstance().moduleManager.getGui());
        if(((Announcer)ModuleManager.getModuleByName("Announcer")).clickGui.getValBoolean() && ModuleManager.isModuleEnabled("Announcer") && mc.player != null)
            if(((Announcer)ModuleManager.getModuleByName("Announcer")).clientSide.getValBoolean()){
                Command.sendClientMessage(Announcer.guiMessage);
            } else {
                mc.player.sendChatMessage(Announcer.guiMessage);
            }
        disable();
    }

    @Override
    public void onUpdate() {
        setColor();
        isLight = theme.getValString().equalsIgnoreCase("Light");
    }

    @Override
    public void onRender() {
        if(shadow.getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(mc);
            OsirisTessellator.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColourUtils.color(0.0F, 0.0F, 0.0F, 0.5F));
        }


        super.onRender();
    }
}