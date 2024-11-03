package net.silentclient.client.gui.elements;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.TimerUtils;

public class IconButton extends Button
{
	
	private ResourceLocation icon;
	private int iconWidth;
	private int iconHeight;

    public IconButton(int buttonId, int x, int y, int widthIn, int heightIn, int iconWidth, int iconHeight, ResourceLocation icon) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.icon = icon;
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
	}

	public IconButton(int buttonId, int x, int y, ResourceLocation icon)
    {
        this(buttonId, x, y, 20, 20, 12, 12, icon);
    }

    @Override
    protected void drawButtonContent() {
        RenderUtil.drawImage(icon, this.xPosition + ((this.width / 2) - (this.iconWidth / 2)), this.yPosition + ((this.height / 2) - (this.iconHeight / 2)), this.iconWidth, this.iconHeight, false);
    }
}
