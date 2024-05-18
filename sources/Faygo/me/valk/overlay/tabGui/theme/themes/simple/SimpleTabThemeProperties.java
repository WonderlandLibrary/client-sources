package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Color;

public class SimpleTabThemeProperties {

	private final Color selectedColor;
	private final Color textColor; 
	private final Color panelColor;
	private final float panelOutlineWidth;
	private final SimpleTabAlignment alignment;

	public SimpleTabThemeProperties(Color selectedColor, Color textColor, Color panelColor, float panelOutlineWidth, SimpleTabAlignment alignment) {
		this.selectedColor = selectedColor;
		this.textColor = textColor;
		this.panelColor = panelColor;
		this.panelOutlineWidth = panelOutlineWidth;
		this.alignment = alignment;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public Color getPanelColor() {
		return panelColor;
	}

	public float getPanelOutlineWidth() {
		return panelOutlineWidth;
	}

	public SimpleTabAlignment getAlignment(){
		return this.alignment;
	}
	
	public enum SimpleTabAlignment {
		
		LEFT,
		CENTER
		
	}
	
}
