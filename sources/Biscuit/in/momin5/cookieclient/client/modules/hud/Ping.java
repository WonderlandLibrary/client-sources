package in.momin5.cookieclient.client.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import com.mojang.realmsclient.gui.ChatFormatting;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.HudModule;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;

import java.awt.*;

public class Ping extends HudModule {

    SettingColor color = register(new SettingColor("Color",this,new CustomColor(112,225,229,149)));

    public Ping(){
        super("Ping",new Point(10,22), Category.HUD);
    }

    @Override
    public void onRender(){
        CookieClient.clickGUI.render();
    }


    private int getPing(){

        int p;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null || mc.isSingleplayer()) {
            p = -1;
        }
        else {
            p = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }
        return p;
    }
    @Override
    public void populate(Theme theme) {
        component = new ListComponent(getName(),theme.getPanelRenderer(),position,new ModuleList());
    }

    public class ModuleList implements HUDList{

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public String getItem(int index) {
            if(getPing() > 200) {
                return "Ping: " + ChatFormatting.RED + getPing();
            }else if(getPing() > 100)
                return "Ping: " + ChatFormatting.YELLOW + getPing();

            else return "Ping: " + ChatFormatting.GREEN + getPing();
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
