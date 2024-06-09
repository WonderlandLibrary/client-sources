package me.valk.overlay.tabGui.theme.themes.simple;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabTypePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;

public class SimpleTabTypeRenderer extends TabPartRenderer<TabTypePart> {

    public SimpleTabTypeRenderer() {
        super(TabTypePart.class);
    }

    @Override
    public void render(TabTypePart object) {
        SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();

        if (theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER) {
            this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
                    object.getParent().getWidth() / 2 - this.getTheme().getFontRenderer().getStringWidth(object.getText()) / 2,
                    2, theme.getProperties().getTextColor().getRGB());
        } else {
            if (object.isSelected()) {
                this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
                        4,
                        2, theme.getProperties().getTextColor().getRGB());
            } else {
                this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
                        4,
                        2, theme.getProperties().getTextColor().getRGB());
            }
        }
    }

}