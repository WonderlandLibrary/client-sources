package studio.dreamys.module.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;

public class Font extends Module {
    public FontRenderer font;

    public Font() {
        super("Font", Category.CHAT);
        set(new Setting("Font", this, "Minecraft", new ArrayList<>(Arrays.asList("Minecraft", "Roboto", "Montserrat"))));
//        font = getFont();
//        System.out.println(font);
//        System.out.println(getFont());
        System.out.println(Minecraft.getMinecraft().fontRendererObj);
    }

    public FontRenderer getFont() {
        return Minecraft.getMinecraft().fontRendererObj;
    }

//    @Override
//    public void modeChanged() {
//        font = getFont(near.settingsManager.getSettingByName(this, "Font").getValString());
//        System.out.println("Changed to " + font);
//    }


}
