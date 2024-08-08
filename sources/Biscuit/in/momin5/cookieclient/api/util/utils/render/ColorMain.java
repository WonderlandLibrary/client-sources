package in.momin5.cookieclient.api.util.utils.render;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class ColorMain extends Module {

    private static final Module ColorMain = null;
    public static SettingMode colorModel = new SettingMode("hi", ColorMain, "HSB", "RGB", "HSB");

    public ColorMain(){
        super("Colors","", Category.RENDER);
    }


    public void setup() {
        ArrayList<String> tab = new ArrayList<>();
        tab.add("Black");
        tab.add("Dark Green");
        tab.add("Dark Red");
        tab.add("Gold");
        tab.add("Dark Gray");
        tab.add("Green");
        tab.add("Red");
        tab.add("Yellow");
        tab.add("Dark Blue");
        tab.add("Dark Aqua");
        tab.add("Dark Purple");
        tab.add("Gray");
        tab.add("Blue");
        tab.add("Aqua");
        tab.add("Light Purple");
        tab.add("White");
        ArrayList<String> models=new ArrayList<>();
        models.add("RGB");
        models.add("HSB");
    }

    public void onEnable(){
        this.disable();
    }

    private static TextFormatting settingToFormatting () {
        return TextFormatting.AQUA;
    }

    public static TextFormatting getFriendColor(){
        return settingToFormatting();
    }

    public static TextFormatting getEnemyColor() {
        return settingToFormatting();
    }

    public static TextFormatting getEnabledColor(){return settingToFormatting();}

    public static TextFormatting getDisabledColor(){return settingToFormatting();}

    public static CustomColor getFriendGSColor(){
        return new CustomColor(0xffffffff);
    }

    public static CustomColor getEnemyGSColor(){
        return new CustomColor(0xffffffff);
    }
}
