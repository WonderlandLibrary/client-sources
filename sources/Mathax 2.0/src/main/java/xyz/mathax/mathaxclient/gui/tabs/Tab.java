package xyz.mathax.mathaxclient.gui.tabs;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import net.minecraft.client.gui.screen.Screen;

import static xyz.mathax.mathaxclient.MatHax.mc;

public abstract class Tab {
    public final String name;

    public Tab(String name) {
        this.name = name;
    }

    public void openScreen(Theme theme) {
        TabScreen screen = this.createScreen(theme);
        screen.addDirect(theme.topBar()).top().centerX();
        mc.setScreen(screen);
    }

    public abstract TabScreen createScreen(Theme theme);

    public abstract boolean isScreen(Screen screen);
}
