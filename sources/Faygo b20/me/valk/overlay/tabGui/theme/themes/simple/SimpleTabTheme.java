package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Font;

import me.valk.agway.gui.AgwayClientOverlay;
import me.valk.overlay.tabGui.theme.TabTheme;
import me.valk.utils.render.VitalFontRenderer;
import me.valk.utils.render.VitalFontRenderer.FontObjectType;

public class SimpleTabTheme extends TabTheme {
	

	private final SimpleTabThemeProperties properties;
	
	public SimpleTabTheme(VitalFontRenderer fontRenderer, SimpleTabThemeProperties properties) {
		super(AgwayClientOverlay.arial);
		this.properties = properties;
		this.addPartRenderer(new SimpleTabPanelRenderer());
		this.addPartRenderer(new SimpleTabSelectionBoxRenderer());
		this.addPartRenderer(new SimpleTabTypeRenderer());
		this.addPartRenderer(new SimpleTabModulePartRenderer());
		this.addPartRenderer(new SimpleTabModeRenderer());
        this.addPartRenderer(new SimpleTabValueRenderer());
        this.addPartRenderer(new SimpleTabRestrictedValueRenderer());
        this.addPartRenderer(new SimpleTabBooleanValueRenderer());
	}

	public SimpleTabThemeProperties getProperties() {
		return properties;
	}
	
}
