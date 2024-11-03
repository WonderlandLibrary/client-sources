package net.silentclient.client.gui.elements;

import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.IButtonTheme;
import net.silentclient.client.utils.ColorUtils;

public class StaticButton {
	public static void render(float xPosition, float yPosition, int width, int height, String displayString)
    {
		StaticButton.render(xPosition, yPosition, width, height, displayString, false);
    }

    public static void render(float xPosition, float yPosition, int width, int height, String displayString, boolean bold)
    {
        StaticButton.render(xPosition, yPosition, width, height, displayString, bold, new DefaultButtonTheme());
    }
	
	public static void render(float xPosition, float yPosition, int width, int height, String displayString, boolean bold, IButtonTheme theme)
    {
            GlStateManager.disableBlend();
            ColorUtils.setColor(theme.getBackgroundColor().getRGB());
            RenderUtil.drawRoundedRect(xPosition, yPosition, width, height, 3, theme.getBackgroundColor().getRGB());
            ColorUtils.setColor(theme.getBorderColor().getRGB());
            RenderUtil.drawRoundedOutline(xPosition, yPosition, width, height, 3, 1, theme.getBorderColor().getRGB());

            ColorUtils.setColor(theme.getTextColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawCenteredString(displayString, (int) (xPosition + width / 2), (int) (yPosition + (height - (bold ? 8 : 10)) / 2), bold ? 8 : 10, bold ? SilentFontRenderer.FontType.HEADER : SilentFontRenderer.FontType.TITLE, width - (bold ? 8 : 10));
    }
	
	public static boolean isHovered(int mouseX, int mouseY, float xPosition, float yPosition, int width, int height) {
        return MouseUtils.isInside(mouseX, mouseY, xPosition, yPosition, width, height);
    }
}
