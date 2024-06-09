package com.kilo.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.input.Input;
import com.kilo.manager.NotificationManager;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public class UIHandler {
	
	private static final Minecraft mc = Minecraft.getMinecraft();

	private static float uiFade = 1f;
	public static boolean uiFadeIn;
	
	public static UI currentUI, uiTo;

	public static void update() {
		if (currentUI != null) {
			Input.handle();
			currentUI.update(Input.mouse[0], Input.mouse[1]);
		}
		uiFade+= uiFadeIn?((currentUI instanceof UIChat)?1f:0.2f):((uiTo instanceof UIChat)?-1f:-0.2f);//((uiFadeIn?1:0)-uiFade)/4f;
		uiFade = Math.min(Math.max(0f, uiFade), 1f);
		if (uiFadeIn) {
			if (uiFade > 0.95f) {
				currentUI = uiTo;
				uiFadeIn = false;
				if (currentUI != null) {
					currentUI.init();
				}
			}
		}
		
		if (mc.theWorld != null) {
			UIChat.updateHistory(Input.mouse[0], Input.mouse[1]);
		}
	}
	
	public static void changeUI() {
		uiFadeIn = true;
		uiTo = UIUtil.newUI(mc.currentScreen);
	}
	
	public static void changeUI(UI ui) {
		uiFadeIn = true;
		uiTo = ui;
	}
	
	public static void render() {
		if (mc.theWorld != null && !mc.gameSettings.hideGUI) {
			for(Module m : ModuleManager.enabledList()) {
				m.render2D();
			}

			if (!mc.gameSettings.showDebugInfo) {
				List<Module> enabled = new ArrayList<Module>();
				List<String> orderable = new ArrayList<String>();
				
				int maxWidth = 0;
				for(Module m : ModuleManager.enabledList()) {
					orderable.add(m.finder);
					if (Fonts.ttfRoundedBold12.getWidth(m.name) > maxWidth) {
						maxWidth = Fonts.ttfRoundedBold12.getWidth(m.name);
					}
				}
				
				Collections.sort(orderable);
				for(String s : orderable) {
					enabled.add(ModuleManager.get(s));
				}
				
				int j = 0;
				int right = 0;
				for(Module m : enabled) {
					Draw.string(Fonts.ttfRoundedBold12, Display.getWidth()-3-right, 5+(j*Fonts.ttfRoundedBold12.getHeight()), m.name, Colors.BLACK.c, Align.R, Align.T);
					int color = Util.blendColor(Colors.WHITE.c, Colors.GREY.c, 0.75f);
					Draw.string(Fonts.ttfRoundedBold12, Display.getWidth()-4-right, 4+(j*Fonts.ttfRoundedBold12.getHeight()), m.name, color, Align.R, Align.T);
					j++;
					if (j > (Display.getHeight()-128)/Fonts.ttfRoundedBold12.getHeight()) {
						j = 0;
						right+= maxWidth;
					}
				}
			}
			UIChat.renderHistory(1f);
		}
		
		if (currentUI != null) {
			if (!(currentUI instanceof UIChat) && 
					!(currentUI instanceof UIChatSpam) &&
					!(currentUI instanceof UIFriends) &&
					!(currentUI instanceof UIHacks) &&
					!(currentUI instanceof UIHistory) &&
					!(currentUI instanceof UIInGameMenu) &&
					!(currentUI instanceof UIMacros) &&
					!(currentUI instanceof UIMusic) &&
					!(currentUI instanceof UINuker) &&
					!(currentUI instanceof UISleep) &&
					!(currentUI instanceof UIWaypoints) &&
					!(currentUI instanceof UIXray)) {
				UI.drawDefaultBackground(UIUtil.shouldDrawBranding(currentUI), uiTo!=null?1f:1f-Math.min(uiFade, 0.95f));
			}
			currentUI.render(1f-Math.min(uiFade, 0.95f));
		}
		
		if (mc.theWorld != null && !mc.gameSettings.hideGUI) {
			Draw.rect(0, 0, Display.getWidth(), 3, Util.reAlpha(Colors.GREEN.c, 1f));
			NotificationManager.render(1f);
			
			if (mc.theWorld != null && !mc.gameSettings.showDebugInfo) {
				Draw.rectTexture(16, 16, 81, 46, Resources.brandingSmall, Util.reAlpha(Colors.WHITE.c, 0.8f));
				Draw.string(Fonts.ttfRoundedBold10, 16+81+2, 16, Kilo.kilo().VERSION_NAME, Colors.WHITE.c);
			}
		}
		Display.setTitle(Kilo.kilo().NAME+" "+Kilo.kilo().VERSION_NAME+" ("+mc.getDebugFPS()+")");
	}
	
	public static void windowResize() {
		if (currentUI != null) {
			currentUI.init();
		}
	}
	
	public static void mouseClick(int mx, int my, int b) {
		if (uiFadeIn) { return; }
		currentUI.mouseClick(mx, my, b);
	}
	
	public static void mouseRelease(int mx, int my, int b) {
		if (uiFadeIn) { return; }
		currentUI.mouseRelease(mx, my, b);
	}
	
	public static void mouseScroll(int s) {
		if (uiFadeIn) { return; }
		currentUI.mouseScroll(s);
	}
	
	public static void keyboardPress(int key) {
		if (uiFadeIn) { return; }
		currentUI.keyboardPress(key);
	}
	
	public static void keyboardRelease(int key) {
		if (uiFadeIn) { return; }
		currentUI.keyboardRelease(key);
	}
	
	public static void keyTyped(int key, char keyChar) {
		currentUI.keyTyped(key, keyChar);
	}
	
}
