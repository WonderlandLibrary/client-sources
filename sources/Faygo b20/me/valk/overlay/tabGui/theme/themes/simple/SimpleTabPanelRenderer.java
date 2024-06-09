package me.valk.overlay.tabGui.theme.themes.simple;

import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.overlay.tabGui.parts.TabPanel;
import me.valk.utils.render.RenderUtil;

public class SimpleTabPanelRenderer extends TabPartRenderer<TabPanel> {

	public SimpleTabPanelRenderer() {
		super(TabPanel.class);
	}

	@Override
	public void render(TabPanel object) {
		SimpleTabTheme theme = (SimpleTabTheme) this.getTheme();
		float line = theme.getProperties().getPanelOutlineWidth();
		RenderUtil.drawRect(-line, -line, object.getWidth() + line, object.getHeight() + line, theme.getProperties().getPanelColor());
	}
	
}
