package host.kix.uzi.module.addons.theme;

import host.kix.uzi.module.addons.theme.components.Watermark;
import host.kix.uzi.module.addons.theme.themes.UziTheme;
import host.kix.uzi.utilities.minecraft.Logger;

import java.util.ArrayList;
import java.util.List;

public class HudGui {

    private Theme theme = null;
    private List<HudObject> objects = new ArrayList<HudObject>();

    public HudGui() {
        theme = new UziTheme();
        objects.add(new Watermark());
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        Logger.logToChat("Hud theme has been set to " + theme.getLabel());
    }

    public void render() {
        if (theme == null) return;
        for (HudObject object : objects) {
            theme.dispatch(object);
        }
    }
}
