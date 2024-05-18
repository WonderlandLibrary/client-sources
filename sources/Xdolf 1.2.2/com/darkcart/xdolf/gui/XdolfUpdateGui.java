package com.darkcart.xdolf.gui;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.RenderUtils;
import com.darkcart.xdolf.util.XdolfUpdater;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class XdolfUpdateGui extends GuiScreen
{
	private GuiScreen parentScreen;
	public static boolean downloadedUpdate = false;
	
	public GuiButton backButton;

	public XdolfUpdateGui(GuiScreen screen)
	{
		parentScreen = screen;
	}

	public void initGui()
	{
		this.buttonList.add(backButton = new GuiButton(1, width / 2 - 100, height / 6 + 168, "Back"));
		this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 6 + 145, "Update"));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1)
		{
			Wrapper.getMinecraft().displayGuiScreen(parentScreen);
		}
		if(button.id == 2)
		{
			XdolfUpdater.downloadFile(); // :^)
			
		}
	}

	public void drawScreen(int i, int j, float k)
	{
		backButton.enabled = downloadedUpdate;
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "Update Xdolf", width / 2, 15, 0xFFFFFF);
		drawCenteredString(fontRendererObj, "There is a new update avaliable for Xdolf!", width / 2, 46, 0xFFFFFF);
		drawCenteredString(fontRendererObj, "Click \"Update\" to download the new update.", width / 2, 56, 0xFFFFFF);

		if(downloadedUpdate)
		{
			drawCenteredString(fontRendererObj, "\247aUpdate complete! \247fPlease open the READ_ME in \2472Xdolf.zip", width / 2 + 10, 96, 0xFFFFFF);
		}
		

		super.drawScreen(i, j, k);
	}
}
