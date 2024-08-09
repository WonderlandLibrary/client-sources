package xyz.mathax.mathaxclient.gui.tabs.builtin;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import net.minecraft.client.gui.screen.Screen;

public class GuiTab extends Tab {
    public GuiTab() {
        super("GUI");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return new GuiScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof GuiScreen;
    }

    private static class GuiScreen extends WindowTabScreen {
        public GuiScreen(Theme theme, Tab tab) {
            super(theme, tab);

            theme.settings.onEnabled();
        }

        @Override
        public void initWidgets() {
            add(theme.settings(theme.settings)).expandX();
        }

        @Override
        public void tick() {
            super.tick();

            theme.settings.tick(window, theme);
        }
    }
}
