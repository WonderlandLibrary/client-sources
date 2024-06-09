package org.darkstorm.minecraft.gui.theme.simple;

import java.awt.Font;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.main.neptune.Wrapper;

import org.darkstorm.minecraft.gui.theme.AbstractTheme;

public class SimpleTheme extends AbstractTheme {
	private final FontRenderer fontRenderer;

	public SimpleTheme() {
		fontRenderer = Wrapper.fr;

		installUI(new SimpleFrameUI(this));
		installUI(new SimplePanelUI(this));
		installUI(new SimpleLabelUI(this));
		installUI(new SimpleButtonUI(this));
		installUI(new SimpleCheckButtonUI(this));
		installUI(new SimpleComboBoxUI(this));
		installUI(new SimpleSliderUI(this));
		installUI(new SimpleProgressBarUI(this));
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}
