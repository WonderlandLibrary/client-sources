package host.kix.uzi.ui.click.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import host.kix.uzi.Uzi;
import host.kix.uzi.ui.click.api.component.impl.Button;
import host.kix.uzi.ui.click.api.theme.Theme;
import host.kix.uzi.ui.click.impl.component.ThemeSpinner;
import host.kix.uzi.ui.click.impl.themes.era.EraTheme;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import host.kix.uzi.ui.click.api.panel.Panel;
import host.kix.uzi.ui.click.impl.component.ModButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Marc
 * @since 7/20/2016
 */
public class GuiClick extends GuiScreen {

    /**
     * The list of panels.
     */
    private List<Panel> panels;

    /**
     * The value of the current theme.
     */
    private Value<CurrentTheme> themeValue = new Value<>("Theme", CurrentTheme.ERA);

    public void setup() {
        panels = new CopyOnWriteArrayList<>();
        Theme theme = themeValue.getValue().getTheme();
        int x = 2, y = 2;
        for (Module.Category category : Module.Category.values()) {
            registerPanel(new Panel(this, category.name(), x, y, theme.getWidth(), theme.getHeight()) {
                @Override
                public void setup() {
                    Uzi.getInstance().getModuleManager().getContents().forEach(m -> {
                        if (m.getCategory() == category) {
                            getComponents().add(
                                    new ModButton(GuiClick.this, m, getX(), getY(), getWidth(), getHeight()));
                        }
                    });
                }

            });
            x += theme.getWidth() + 2;
        }
        registerPanel(new Panel(this, "Theme", x, y, theme.getWidth(), theme.getHeight()) {
            @Override
            protected void setup() {
                getComponents().add(new ThemeSpinner(GuiClick.this, themeValue, themeValue.getValue(), getX(), getY(),
                        getWidth(), getHeight()));
            }
        });

        x += theme.getWidth() + 2;
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.drawDefaultBackground();
        panels.forEach(p -> {
            p.drawPanel(x, y);
        });
    }

    @Override
    public void mouseClicked(int x, int y, int mouseID) {
        try {
            panels.forEach(p -> p.mouseClicked(x, y, mouseID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(int x, int y, int mouseID) {
        panels.forEach(p -> p.mouseReleased(x, y, mouseID));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        panels.forEach(p -> p.keyTyped(typedChar, keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Registers the specified panel.
     *
     * @param panel to register
     */
    public void registerPanel(Panel panel) {
        if (!this.panels.contains(panel))
            this.panels.add(panel);
    }

    /**
     * Unregisters the specified panel.
     *
     * @param panel to unregister
     */
    public void unregisterPanel(Panel panel) {
        if (this.panels.contains(panel))
            this.panels.remove(panel);
    }

    /**
     * Returns the list of panels.
     *
     * @return list of panels
     */
    public List<Panel> getPanels() {
        return panels;
    }

    /**
     * Returns the current theme.
     *
     * @return current theme
     */
    public Theme getTheme() {
        return ((CurrentTheme) (themeValue.getValue())).getTheme();
    }

    /**
     * A class determining the current theme.
     */
    public enum CurrentTheme {
        ERA(new EraTheme());

        /**
         * An instance of the Theme.
         */
        private Theme theme;

        CurrentTheme(Theme theme) {
            this.theme = theme;
        }

        /**
         * Returns the current theme.
         *
         * @return current theme
         */
        public Theme getTheme() {
            return theme;
        }
    }
}
