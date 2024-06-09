package me.valk.overlay.tabGui.theme.themes.simple;

import java.awt.Color;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabSelectionBox;
import me.valk.utils.render.PrizonRenderUtils;
import me.valk.utils.render.RenderUtil;

public class SimpleTabSelectionBoxRenderer extends TabPartRenderer<TabSelectionBox> {
	
	public float hue = 0.0f;

	public SimpleTabSelectionBoxRenderer() {
		super(TabSelectionBox.class);
	}

	@Override
	public void render(TabSelectionBox object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();
		
        float h = this.hue;
        
		this.hue += 0.1;

		if (this.hue > 255.0f) {
			this.hue = 0.0f;
		}
		
		final Color color = Color.getHSBColor(h / 255.0f, 1.0f, 1.0f);

		PrizonRenderUtils.drawGradientRect(0, 0, object.getParent().getWidth(),
				this.getTheme().getFontRenderer().getHeight() + 3.5, 0xffffaa00, 0xffff6000);
	}

}
