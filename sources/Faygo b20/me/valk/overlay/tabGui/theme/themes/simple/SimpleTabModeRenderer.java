package me.valk.overlay.tabGui.theme.themes.simple;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabModePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;

public class SimpleTabModeRenderer extends TabPartRenderer<TabModePart> {

	public SimpleTabModeRenderer() {
		super(TabModePart.class);
	}

	@Override
	public void render(TabModePart object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();

		
		
		if(theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER){
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
					object.getParent().getWidth()/2 - this.getTheme().getFontRenderer().getStringWidth(object.getText())/2,
					2, object.getModule().getMode() == object.getMode() ? theme.getProperties().getTextColor().getRGB() :theme.getProperties().getTextColor().darker().getRGB());
		}else{
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
					4,
					2, object.getModule().getMode() == object.getMode() ? theme.getProperties().getTextColor().getRGB() :theme.getProperties().getTextColor().darker().getRGB());
		}
	}
	
}
