package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Color;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabValuePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;


public class SimpleTabValueRenderer extends TabPartRenderer<TabValuePart> {

	public SimpleTabValueRenderer() {
		super(TabValuePart.class);
	}

	@Override
	public void render(TabValuePart object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();

		if(theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER){
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
					object.getParent().getWidth()/2 - this.getTheme().getFontRenderer().getStringWidth(object.getText())/2,
					2, Color.WHITE.getRGB());
		}else{
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getText(),
					4,
					2, Color.WHITE.getRGB());
		}
	}
	
}
