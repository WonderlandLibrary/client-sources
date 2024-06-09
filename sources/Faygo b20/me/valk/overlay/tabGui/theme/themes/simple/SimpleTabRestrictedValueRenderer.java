package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Color;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabRestrictedValuePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;


public class SimpleTabRestrictedValueRenderer extends TabPartRenderer<TabRestrictedValuePart> {

	public SimpleTabRestrictedValueRenderer() {
		super(TabRestrictedValuePart.class);
	}

	@Override
	public void render(TabRestrictedValuePart object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();

		if(theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER){
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getValue().getValue() + "",
					object.getParent().getWidth()/2 - this.getTheme().getFontRenderer().getStringWidth(object.getValue().getValue() + "")/2,
					2, Color.WHITE.getRGB());
		}else{
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getValue().getValue() + "",
					4,
					2, Color.WHITE.getRGB());
		}
	}
	
}
