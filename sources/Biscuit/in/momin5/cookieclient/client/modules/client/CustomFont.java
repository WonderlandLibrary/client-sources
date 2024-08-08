package in.momin5.cookieclient.client.modules.client;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.util.utils.font.CustomFontRenderer;
import in.momin5.cookieclient.api.util.utils.font.FontUtils;

import java.awt.*;

public class CustomFont extends Module {

    public SettingMode font = register(new SettingMode("Font",this,"Raleway","Comic Sans","Arial","Verdana","TimesRoman","Rockwell","Lato","Raleway"));
    public CustomFont(){
        super("CustomFont", Category.HUD);
        this.enable();
    }

    @Override
    public void onEnable() {

        if (font.is("Comic Sans")) {
            CookieClient.customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
        }

        if (font.is("Verdana")) {
            CookieClient.customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
        }

        if (font.is("Arial")) {
            CookieClient.customFontRenderer = new CustomFontRenderer(new Font("Arial Rounded MT Bold", Font.PLAIN, 18), true, true);
        }

        if (font.is("TimesRoman")) {
            CookieClient.customFontRenderer = new CustomFontRenderer(new Font("Times New Roman", Font.PLAIN, 18), false, true);
        }

        if(font.is("Rockwell")){
            CookieClient.customFontRenderer = new CustomFontRenderer(new Font("Rockwell",Font.PLAIN,18),true,true);
        }

        if(font.is("Lato")){
            CookieClient.customFontRenderer = new CustomFontRenderer(FontUtils.getFont("Lato.ttf",18),true,true);
        }

        if(font.is("Raleway")){
            CookieClient.customFontRenderer = new CustomFontRenderer(FontUtils.getFont("Raleway.ttf",18),true,true);
        }
    }

}
