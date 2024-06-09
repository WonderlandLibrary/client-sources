package host.kix.uzi.ui.click.impl.themes.darcula.components;

import host.kix.uzi.module.Module;
import host.kix.uzi.ui.click.api.theme.ComponentUI;
import host.kix.uzi.ui.click.api.theme.Theme;
import host.kix.uzi.ui.click.impl.component.ModButton;
import host.kix.uzi.utilities.minecraft.NahrFont;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;


/**
 * @author Marc
 * @since 7/21/2016
 */
public class ModButtonUI extends ComponentUI<ModButton> {

    /**
     * The fonts of the component.
     */

    private final NahrFont font, keyBinding;


    public ModButtonUI(Theme theme) {
        super(theme);
        font = new NahrFont("Arial", 16F);
        keyBinding = new NahrFont("Arial", 12.5F);

    }

    @Override
    public void draw(ModButton component, int x, int y) {
        Module mod = component.getMod();

        String bindingText = "";

        if (component.isBinding())
            bindingText = "...";

        if (!component.isBinding()) {
            int keyCode = mod.getBind();

            if (keyCode == Keyboard.KEY_NONE)
                bindingText = "-";
            else
                bindingText = Keyboard.getKeyName(keyCode);

        }

        Gui.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(),
                component.getY() + component.getHeight(), component.isEnabled() ? 0xFF4B7EAF : 0xFF3F4041);
        if (component.isHovering(x, y)) {
            Gui.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(),
                    component.getY() + component.getHeight(), 0x26FFFFFF);
            keyBinding.drawString(bindingText,
                    component.getX() + component.getWidth() - keyBinding.getStringWidth(bindingText) - 4,
                    component.getY() + 3, NahrFont.FontType.EMBOSS_TOP, 0xFFEEEEEE);
        }
        font.drawString(component.getLabel(), component.getX() + 4, component.getY() + 1, NahrFont.FontType.SHADOW_THIN, 0xFFDEDEDE);

    }
}
