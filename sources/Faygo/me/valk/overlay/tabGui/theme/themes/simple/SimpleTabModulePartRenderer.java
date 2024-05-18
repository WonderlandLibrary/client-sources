package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Color;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabModulePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;

public class SimpleTabModulePartRenderer extends TabPartRenderer<TabModulePart> {

	public boolean arrows = false;
	
    public SimpleTabModulePartRenderer() {
        super(TabModulePart.class);
    }

    @Override
    public void render(TabModulePart object) {
        SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();  
        if (theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER) {
            this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
                    object.getParent().getWidth() / 2 - this.getTheme().getFontRenderer().getStringWidth(object.getText()) / 2,
                    2, object.getModule().getState() ? theme.getProperties().getTextColor().getRGB() :theme.getProperties().getTextColor().darker().getRGB());
        } 
    }
}

