package host.kix.uzi.ui.click.impl.themes.era;
import host.kix.uzi.ui.click.api.component.Component;
import host.kix.uzi.ui.click.api.panel.Panel;
import host.kix.uzi.ui.click.api.theme.Theme;
import host.kix.uzi.ui.click.impl.component.*;
import host.kix.uzi.ui.click.impl.themes.era.components.ModButtonUI;
import host.kix.uzi.ui.click.impl.themes.era.components.ThemeSpinnerUI;
import host.kix.uzi.ui.click.impl.themes.era.components.ValueButtonUI;
import host.kix.uzi.ui.click.impl.themes.era.components.ValueSliderUI;
import host.kix.uzi.ui.click.impl.themes.era.components.ValueSpinnerUI;

import host.kix.uzi.utilities.minecraft.NahrFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * @author Marc
 * @since 7/20/2016
 */
public class EraTheme extends Theme {


    private final NahrFont font, keyBinding;


    public EraTheme() {
        super("Era", 90, 14);
        font = new NahrFont("Arial", 16F);
        keyBinding = new NahrFont("Arial", 12.5F);
    }

    @Override
    public void loadComponents() {
        this.themedComponents.put(ModButton.class, new ModButtonUI(this));
        this.themedComponents.put(ValueButton.class, new ValueButtonUI(this));
        this.themedComponents.put(ValueSlider.class, new ValueSliderUI(this));
        this.themedComponents.put(ValueSpinner.class, new ValueSpinnerUI(this));
        this.themedComponents.put(ThemeSpinner.class, new ThemeSpinnerUI(this));
    }

    @Override
    public void drawPanel(Panel panel, int x, int y) {
        Gui.drawRect(panel.getX(), panel.getY(), panel.getX() + panel.getWidth(), panel.getY() + panel.getHeight(),
                0xFF2D2D2D);
        font.drawString(panel.getLabel().substring(0, 1).toUpperCase() + panel.getLabel().substring(1).toLowerCase(), panel.getX() + 3, panel.getY() + 0.5F, NahrFont.FontType.NORMAL,-1);

        if (panel.isOpen()) {
            int height = panel.getY() + this.getHeight(), offset = 1;

            for (Component c : panel.getComponents()) {
                if (c instanceof ModButton) {
                    ModButton component = (ModButton) c;
                    if (component.isVisible()) {
                        component.setSize(panel.getX() + offset, height, this.getWidth() - offset - 1, 16);
                        component.draw(x, y);

                        height += component.getHeight();

                        // Draw sub components of the mod button, i.e., values.
                        for (Component subComponent : component.getSubComponents()) {
                            if (subComponent.isVisible()) {
                                subComponent.setSize(panel.getX() + offset * 2 + 2, height, this.getWidth() - offset * 2 - 6, 14);
                                subComponent.draw(x, y);

                                height += subComponent.getHeight();
                            }
                        }
                    }
                } else if (c instanceof ThemeSpinner) {
                    ThemeSpinner component = (ThemeSpinner) c;

                    if (component.isVisible()) {
                        component.setSize(panel.getX() + offset, height, this.getWidth() - offset * 2, 16);
                        component.draw(x, y);

                        height += component.getHeight();
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void drawComponent(Component component, int x, int y) {
        this.themedComponents.get(component.getClass()).draw(component, x, y);
    }

    @Override
    public void mouseClicked(Panel panel, int x, int y, int button) {
        if (panel.isHovering(x, y)) {
            switch (button) {
                case 1:
                    panel.setOpen(!panel.isOpen());
                    break;
            }
        } else {
            if (panel.isOpen())
                panel.getComponents().forEach(c -> c.mouseClicked(x, y, button));
        }
    }
}
