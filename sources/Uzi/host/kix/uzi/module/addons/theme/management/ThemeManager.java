package host.kix.uzi.module.addons.theme.management;

import host.kix.uzi.management.ListManager;
import host.kix.uzi.module.addons.theme.Theme;
import host.kix.uzi.module.addons.theme.themes.*;

/**
 * Created by Kix on 6/10/2017.
 * Made for the Uzi Universal project.
 */
public class ThemeManager extends ListManager<Theme> {

    public ThemeManager() {
        addContent(new UziTheme());
        addContent(new StaminaTheme());
        addContent(new DirektTheme());
        addContent(new ExeterTheme());
        addContent(new TropicalTheme());
    }
}
