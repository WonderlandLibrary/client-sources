package com.darkcart.xdolf.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.clickgui.windows.WindowAura;
import com.darkcart.xdolf.clickgui.windows.WindowInfo;
import com.darkcart.xdolf.clickgui.windows.WindowPlayer;
import com.darkcart.xdolf.clickgui.windows.WindowRadar;
import com.darkcart.xdolf.clickgui.windows.WindowRender;
import com.darkcart.xdolf.clickgui.windows.WindowValues;
import com.darkcart.xdolf.clickgui.windows.WindowWorld;

import net.minecraft.client.gui.GuiScreen;

public class XdolfGuiClick extends GuiScreen {
	
	public static ArrayList<XdolfWindow> windowList = new ArrayList<XdolfWindow>();
	public static ArrayList<XdolfWindow> unFocusedWindows = new ArrayList<XdolfWindow>();
	
	public static WindowPlayer player = new WindowPlayer();
	public static WindowRender render = new WindowRender();
	public static WindowValues values = new WindowValues();
	public static WindowInfo info = new WindowInfo();
	public static WindowRadar radar = new WindowRadar();
	public static WindowAura aura = new WindowAura();
	public static WindowWorld world = new WindowWorld();
	
	public void onGuiClosed() {
		try {
			Wrapper.getFileManager().saveGuiSettings();
		} catch(Exception e) {}
	}
	
	public void mouseClicked(int x, int y, int b) throws IOException {
		try
		{
			for(XdolfWindow w : windowList) {
				w.mouseClicked(x, y, b);
			}
			super.mouseClicked(x, y, b);
		}catch(Exception e) {}
	}
	
	public void mouseReleased(int x, int y, int state) {
		try {
			for(XdolfWindow w : windowList) {
				w.mouseReleased(x, y, state);
			}
			super.mouseReleased(x, y, state);
		}catch(Exception e) {}
	}
	
	public void drawScreen(int x, int y, float ticks) {
		drawRect(0, 0, width, height, 0x8F000000);
		for(XdolfWindow w : windowList) {
			w.draw(x, y);
		}
		super.drawScreen(x, y, ticks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static void sendPanelToFront(XdolfWindow window)
	{
		if(windowList.contains(window))
		{
			int panelIndex = windowList.indexOf(window);
			windowList.remove(panelIndex);
			windowList.add(windowList.size(), window);
		}
	}
	
	public static XdolfWindow getFocusedPanel()
	{
		return windowList.get(windowList.size() - 1);
	}
}

