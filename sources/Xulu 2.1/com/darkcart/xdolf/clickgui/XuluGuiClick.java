package com.darkcart.xdolf.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.clickgui.windows.WindowAura;
import com.darkcart.xdolf.clickgui.windows.WindowInfo;
import com.darkcart.xdolf.clickgui.windows.WindowMovement;
import com.darkcart.xdolf.clickgui.windows.WindowPlayer;
import com.darkcart.xdolf.clickgui.windows.WindowRadar;
import com.darkcart.xdolf.clickgui.windows.WindowRender;
import com.darkcart.xdolf.clickgui.windows.WindowValues;
import com.darkcart.xdolf.clickgui.windows.WindowWorld;

import net.minecraft.client.gui.GuiScreen;

public class XuluGuiClick extends GuiScreen {
	
	public static ArrayList<XuluBWindow> windowList = new ArrayList<XuluBWindow>();
	public static ArrayList<XuluBWindow> unFocusedWindows = new ArrayList<XuluBWindow>();
	
	public static WindowPlayer player = new WindowPlayer();
	public static WindowMovement movement = new WindowMovement();
	public static WindowRender render = new WindowRender();
	public static WindowValues values = new WindowValues();
	public static WindowInfo info = new WindowInfo();
		public static WindowAura aura = new WindowAura();
	public static WindowWorld world = new WindowWorld();
	public static WindowRadar radar = new WindowRadar();
	
	public void onGuiClosed() {
		try {
			Wrapper.getFileManager().saveGuiSettings();
		} catch(Exception e) {}
	}
	
	public void mouseClicked(int x, int y, int b) throws IOException {
		try
		{
			for(XuluBWindow w : windowList) {
				w.mouseClicked(x, y, b);
			}
			super.mouseClicked(x, y, b);
		}catch(Exception e) {}
	}
	
	public void mouseReleased(int x, int y, int state) {
		try {
			for(XuluBWindow w : windowList) {
				w.mouseReleased(x, y, state);
			}
			super.mouseReleased(x, y, state);
		}catch(Exception e) {}
	}
	
	public void drawScreen(int x, int y, float ticks) {
		drawRect(0, 0, width, height, 0x333333);
		for(XuluBWindow w : windowList) {
			w.draw(x, y);
		}
		super.drawScreen(x, y, ticks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static void sendPanelToFront(XuluBWindow window)
	{
		if(windowList.contains(window))
		{
			int panelIndex = windowList.indexOf(window);
			windowList.remove(panelIndex);
			windowList.add(windowList.size(), window);
		}
	}
	
	public static XuluBWindow getFocusedPanel()
	{
		return windowList.get(windowList.size() - 1);
	}
}

