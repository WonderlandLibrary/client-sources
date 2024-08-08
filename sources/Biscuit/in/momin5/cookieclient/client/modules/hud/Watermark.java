package in.momin5.cookieclient.client.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.HudModule;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;

import java.awt.*;

public class Watermark extends HudModule {

    SettingColor color = register(new SettingColor("Color",this,new CustomColor(112,225,229,149)));
    public Watermark(){
        super("Watermark", new Point(10,12), Category.HUD);
    }

    @Override
    public void populate(Theme theme) {
        component = new ListComponent(getName(),theme.getPanelRenderer(),position,new ModuleList());
    }

    @Override
    public void onRender(){
        CookieClient.clickGUI.render();
    }

    public class ModuleList implements HUDList {

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public String getItem(int index) {
            return CookieClient.MOD_NAME + " v" + CookieClient.MOD_VERSION;
        }

        @Override
        public Color getItemColor(int index) {
            return color.getValue();
        }

        @Override
        public boolean sortUp() {
            return false;
        }

        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
