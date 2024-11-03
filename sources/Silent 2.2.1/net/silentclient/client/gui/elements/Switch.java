package net.silentclient.client.gui.elements;

import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.switches.DefaultSwitchTheme;
import net.silentclient.client.gui.theme.switches.ISwitchSchema;
import net.silentclient.client.gui.util.RenderUtil;

public class Switch {
	public static void render(int mouseX, int mouseY, float x, float y, SimpleAnimation animation, boolean checked, boolean disabled) {
		render(mouseX, mouseY, x, y, animation, checked, disabled, null, new DefaultSwitchTheme());
	}

	public static void render(int mouseX, int mouseY, float x, float y, SimpleAnimation animation, boolean checked, boolean disabled, String tooltip) {
		render(mouseX, mouseY, x, y, animation, checked, disabled, tooltip, new DefaultSwitchTheme());
	}

	public static void render(int mouseX, int mouseY, float x, float y, SimpleAnimation animation, boolean checked, boolean disabled, String tooltip, ISwitchSchema theme) {
		RenderUtil.drawRoundedRect(x, y, 15, 8, 8, disabled ? theme.getDisabledBackgroundColor().getRGB() : checked ? theme.getSelectedBackgroundColor().getRGB() : theme.getBackgroundColor().getRGB());
		if(isHovered(mouseX, mouseY, x, y)) {
			RenderUtil.drawRoundedOutline(x, y, 15, 8, 8, 2, theme.getBorderColor().getRGB());
		}
		RenderUtil.drawRoundedRect(x + 0.5F + (animation.getValue()), y + 0.5F, 7, 7, 7, theme.getCircleColor().getRGB());
		animation.setAnimation(checked && !disabled ? 15 - 8 : 0, 20);
		Tooltip.render(mouseX, mouseY, x, y, 15, 8, tooltip);
	}
	
	public static boolean isHovered(int mouseX, int mouseY, float x, float y) {
		return MouseUtils.isInside(mouseX, mouseY, x, y, 15, 8);
	}
}
