package net.silentclient.client.mods;

import java.awt.Color;
import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.utils.ColorUtils;

public class CustomFontRenderer
{
    private CustomFontRenderer.RenderMode mode = CustomFontRenderer.RenderMode.CUSTOM;

    public CustomFontRenderer(Font font)
    {
    }

    public CustomFontRenderer()
    {
    }

    public void setRenderMode(CustomFontRenderer.RenderMode mode)
    {
        this.mode = mode;
    }

    public CustomFontRenderer.RenderMode getMode()
    {
        return this.mode;
    }

    public void drawString(String text, float x, float y, int color, boolean shadow)
    {
        if (this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT))
        {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, shadow);
        }
        else
        {
            this.drawString(text, x, y - 4, 14, SilentFontRenderer.FontType.TITLE, color, shadow);
        }
    }

    public void drawString(String text, int x, int y, int color, int size)
    {
        if (this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT))
        {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
        }
        else
        {
            this.drawString(text, x, y, size, SilentFontRenderer.FontType.TITLE, color, false);
        }
    }

    public void drawString(String text, float x, float y, int fontHeight, SilentFontRenderer.FontType type, int color, boolean shadow)
    {
        if (this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT))
        {
        	ColorUtils.setColor(color);
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, shadow);
        }
        else
        {
            if(shadow) {
            	ColorUtils.setColor(new Color(0, 0, 0).getRGB());
                Client.getInstance().getSilentFontRenderer().drawString(x + 1, y + 1, EnumChatFormatting.getTextWithoutFormattingCodes(text), fontHeight, type, true);
            }
            ColorUtils.setColor(color);
            Client.getInstance().getSilentFontRenderer().drawString(x, y, text, fontHeight, type, true);
        }
    }

    public int getStringWidth(String string, int fontHeight, SilentFontRenderer.FontType type)
    {
        return this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT) ? Minecraft.getMinecraft().fontRendererObj.getStringWidth(string) : Client.getInstance().getSilentFontRenderer().getStringWidth(string, fontHeight, type);
    }

    public int getStringWidth(String string)
    {
        return this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT) ? Minecraft.getMinecraft().fontRendererObj.getStringWidth(string) : this.getStringWidth(string, 14, SilentFontRenderer.FontType.TITLE);
    }

    public int getStringWidth(String string, SilentFontRenderer.FontType type)
    {
        return this.mode.equals(CustomFontRenderer.RenderMode.DEFAULT) ? Minecraft.getMinecraft().fontRendererObj.getStringWidth(string) : Client.getInstance().getSilentFontRenderer().getStringWidth(string, type);
    }

    public static enum RenderMode
    {
        DEFAULT,
        CUSTOM;
    }
}
