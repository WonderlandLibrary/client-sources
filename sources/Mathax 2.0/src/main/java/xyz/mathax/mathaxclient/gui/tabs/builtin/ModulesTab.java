package xyz.mathax.mathaxclient.gui.tabs.builtin;

import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab extends Tab {
    public ModulesTab() {
        super("Modules");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return theme.modulesScreen();
    }

    @Override
    public boolean isScreen(Screen screen) {
        return Systems.get(Themes.class).getTheme().isModulesScreen(screen);
    }
}
