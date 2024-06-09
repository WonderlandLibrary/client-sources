package host.kix.uzi.ui.click.impl.themes.darcula.components;

import host.kix.uzi.ui.click.api.theme.ComponentUI;
import host.kix.uzi.ui.click.api.theme.Theme;
import host.kix.uzi.ui.click.impl.component.ThemeSpinner;
import host.kix.uzi.utilities.minecraft.NahrFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * @author Marc
 * @since 7/26/2016
 */
public class ThemeSpinnerUI extends ComponentUI<ThemeSpinner> {

    /**
     * The font of the component.
     */
    private final NahrFont font, keyBinding;


    public ThemeSpinnerUI(Theme theme) {
        super(theme);
        font = new NahrFont("Arial", 16F);
        keyBinding = new NahrFont("Arial", 12.5F);
    }

    @Override
    public void draw(ThemeSpinner component, int x, int y) {
        Gui.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + component.getHeight(), 0xFF3F4041);

        if (component.isHovering(x, y))
            Gui.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + component.getHeight(), 0x26FFFFFF);

        font.drawString(component.getValue().getName(), component.getX() + 4, component.getY() + 1, NahrFont.FontType.SHADOW_THIN, -1);
        font.drawString(component.getLabel(), component.getX() + component.getWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(component.getLabel()) - 4, component.getY() + 1, NahrFont.FontType.SHADOW_THIN, -1);
    }


}
