package in.momin5.cookieclient.client.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.HudModule;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Arraylist extends HudModule {
    public Arraylist(){
        super("ArrayList",new Point(-2,69), Category.HUD);
    }

    private ModuleArrayList list = new ModuleArrayList();

    public SettingMode sortLength = register(new SettingMode("SortHorizontal",this,"right","right","left"));
    public SettingMode sortHeight = register(new SettingMode("SortVertical",this,"up","up","down"));
    public SettingColor color = register(new SettingColor("Color",this,new CustomColor(112,225,229,149)));

    @Override
    public void populate(Theme theme) {
        component = new ListComponent(getName(),theme.getPanelRenderer(),position,list);
    }

    public void onRender() {
        CookieClient.clickGUI.render();
        list.activeModules.clear();
        for (Module module: ModuleManager.getModules()) {
           if (module.isEnabled() && !module.getCategory().equals(Category.HUD) && !module.getName().equals("baritone")) {
                list.activeModules.add(module);
            }
        }
        if(sortHeight.is("up") || sortHeight.is("down")) {
            list.activeModules.sort(Comparator.comparing(module -> - CookieClient.clickGUI.guiInterface.getFontWidth(module.getName())));
        }
    }

    private class ModuleArrayList implements HUDList {

        public List<Module> activeModules =new ArrayList<Module>();

        @Override
        public int getSize() {
            return activeModules.size();
        }

        @Override
        public String getItem(int index) {
            Module module = activeModules.get(index);
            if(sortLength.is("right")) return module.getName() + "<";
            else if(sortLength.is("left")) return ">" + module.getName();
            else return module.getName();
        }

        @Override
        public Color getItemColor(int index) {
            CustomColor c = color.getValue();
            return Color.getHSBColor(c.getHue() + (color.getRainbow() ? .05f * index : 0), (color.getRainbow() ? 0.5f : c.getSaturation()), c.getBrightness());
        }

        @Override
        public boolean sortUp() {
            return sortHeight.is("up");
        }

        @Override
        public boolean sortRight() {
            return sortLength.is("right");
        }
    }
}
