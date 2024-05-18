package me.valk.overlay.tabGui.theme.themes.simple;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabBooleanValuePart;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;

public class SimpleTabBooleanValueRenderer extends TabPartRenderer<TabBooleanValuePart> {

	public SimpleTabBooleanValueRenderer() {
		super(TabBooleanValuePart.class);
	}

	@Override
	public void render(TabBooleanValuePart object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();

		if(theme.getProperties().getAlignment() == SimpleTabAlignment.CENTER){
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getValue().getValue() + "",
					object.getParent().getWidth()/2 - this.getTheme().getFontRenderer().getStringWidth(object.getValue().getValue() + "")/2,
					2, object.getValue().getValue() ? theme.getProperties().getTextColor().getRGB() :theme.getProperties().getTextColor().darker().getRGB());
		}else{
			this.getTheme().getFontRenderer().drawStringWithShadow(object.getValue().getValue() + "",
					4,
					2, object.getValue().getValue() ? theme.getProperties().getTextColor().getRGB() :theme.getProperties().getTextColor().darker().getRGB());
		}
	}
	
}
