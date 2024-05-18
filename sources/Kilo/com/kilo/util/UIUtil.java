package com.kilo.util;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.multiplayer.GuiConnecting;

import com.kilo.Kilo;
import com.kilo.ui.GuiGrabber;
import com.kilo.ui.UI;
import com.kilo.ui.UIBanned;
import com.kilo.ui.UIChat;
import com.kilo.ui.UIConnecting;
import com.kilo.ui.UICreateWorld;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIInGameMenu;
import com.kilo.ui.UILoggedIn;
import com.kilo.ui.UILogin;
import com.kilo.ui.UIMainMenu;
import com.kilo.ui.UIMultiplayer;
import com.kilo.ui.UINewAccount;
import com.kilo.ui.UISingleplayer;
import com.kilo.ui.UISleep;
import com.kilo.ui.UIVerified;
import com.kilo.ui.UIVerifyAccount;
import com.kilo.ui.UIWelcome;

public class UIUtil {

	public static UI newUI(GuiScreen mcGUI) {
		
		if (mcGUI instanceof GuiMainMenu) {
			if (Kilo.kilo().client == null) {
				return new UIWelcome(UIHandler.currentUI);
			}
			if (!Kilo.kilo().client.gameStatus.equalsIgnoreCase("verified") && !Kilo.kilo().client.gameStatus.equalsIgnoreCase("banned")) {
				return new UIWelcome(UIHandler.currentUI);
			} else {
				if (Kilo.kilo().client.gameStatus.equalsIgnoreCase("verified")) {
					return new UIMainMenu(UIHandler.currentUI);
				} else {
					return new UIBanned(UIHandler.currentUI);
				}
			}
		} else if (mcGUI instanceof GuiMultiplayer) {
			return new UIMultiplayer(UIHandler.currentUI);
		} else if (mcGUI instanceof GuiChat) {
			if (!(mcGUI instanceof GuiSleepMP)) {
				return new UIChat(UIHandler.currentUI);
			} else {
				return new UISleep(UIHandler.currentUI);
			}
		} else if (mcGUI instanceof GuiGrabber) {
			return null;
		} else if (mcGUI instanceof GuiIngameMenu) {
			return new UIInGameMenu(UIHandler.currentUI);
		} else if (mcGUI instanceof GuiSelectWorld) {
			return new UISingleplayer(UIHandler.currentUI == null?new UIMainMenu(null):UIHandler.currentUI);
		} else if (mcGUI instanceof GuiCreateWorld) {
			return new UICreateWorld(UIHandler.currentUI);
		} else if (mcGUI instanceof GuiConnecting) {
			return new UIConnecting(UIHandler.currentUI, ((GuiConnecting)mcGUI).ip+":"+((GuiConnecting)mcGUI).port);
		}
		
		return null;
	}
	
	public static float[] getScaledImage(float wi, float hi, float ws, float hs) {
		float ri = wi/hi;
		
		float rs = ws/hs;
		
		float dw = 0;
		float dh = 0;
		
		if (rs <= ri) {
			dw = wi * hs/hi;
			dh = hs;
		} else {
			dw = ws;
			dh = hi * ws/wi;
		}
		
		float x = (ws-dw)/2;
		float y = (hs-dh)/2;
		
		return new float[] {x, y, dw, dh};
	}
	
	public static boolean shouldDrawBackground(UI ui) {
		return ui instanceof UIBanned ||
				ui instanceof UILoggedIn ||
				ui instanceof UILogin ||
				ui instanceof UIMainMenu ||
				ui instanceof UINewAccount ||
				ui instanceof UIVerifyAccount ||
				ui instanceof UIVerified ||
				ui instanceof UIWelcome;
	}
	
	public static boolean shouldDrawBranding(UI ui) {
		return ui instanceof UIBanned ||
				ui instanceof UILoggedIn ||
				ui instanceof UILogin ||
				ui instanceof UINewAccount ||
				ui instanceof UIVerifyAccount ||
				ui instanceof UIVerified ||
				ui instanceof UIWelcome;
	}
}
