package com.kilo.ui;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.WorldManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.render.TextureImage;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.ui.inter.slotlist.slot.WorldSlot;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.Util;


public class UISingleplayer extends UI {

	private UI popup, popupTo;
	private boolean popupFade;
	
	private float formOffset;
	private boolean invalid, deleting, loadingWorld;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();

	private int selectedIndex;
	private float fX, fY, fW, fH, popupOpacity;
	
	public SlotList wsl;
	
	public TextureImage model;
	
	public UISingleplayer(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Singleplayer";

		
		formOffset = 0;
		invalid = false;

		WorldManager.loadWorlds();
		wsl = new SlotList(6f);
		
		fX = Display.getWidth()/1.5f+32;
		fY = (Display.getHeight()/2)+44;
		fW = Display.getWidth()/1.5f-38-64;
		fH = Display.getHeight()-124;

		inters.clear();
		int i = 0;
		inters.add(new IconButton(this, 32+(64*(i++)), 32, 32, 32, Colors.WHITE.c, Resources.iconReturn[3]));
		inters.add(new Button(this, "Play Selected World", fX-(fW/2)+24, fY+(fH/2)-24-48-12-48, (fW/2)-48, 48, Fonts.ttfRoundedBold20, Colors.DARKMAGENTA.c, null, 0));
		inters.add(new Button(this, "Create New World", fX+24, fY+(fH/2)-24-48-12-48, (fW/2)-48, 48, Fonts.ttfRoundedBold20, Colors.GREEN.c, null, 0));
		inters.add(new Button(this, "Rename", fX-(fW/2)+24, fY+(fH/2)-24-48, (fW/2)-48, 48, Fonts.ttfRoundedBold20, Colors.DARKGREY.c, null, 0));
		inters.add(new Button(this, "Re-Create", fX+24, fY+(fH/2)-24-48, (((fW/2)-48)/2)-12, 48, Fonts.ttfRoundedBold20, Colors.DARKGREY.c, null, 0));
		inters.add(new Button(this, "Delete", fX+24+(((fW/2)-48)/2)+12, fY+(fH/2)-24-48, (((fW/2)-48)/2)-12, 48, Fonts.ttfRoundedBold20, Colors.DARKGREY.c, null, 0));
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		if (invalid) {
			formOffset+= ((-Fonts.ttfStandard14.getHeight()*2)-formOffset)/5f;
			if (invalidTimer.isTime(2f)) {
				invalid = false;
			}
		} else {
			invalidTimer.reset();
		}
		if (!invalid) {
			formOffset+= (0-formOffset)/5f;
		}

		SaveFormatComparator activeWorld = null;
		for(Slot w : wsl.slots) {
			if (w.active) {
				activeWorld = WorldManager.getWorld(((WorldSlot)w).index);
				break;
			}
		}
		
		((Button)inters.get(1)).enabled = activeWorld != null;
		((Button)inters.get(3)).enabled = activeWorld != null;
		((Button)inters.get(4)).enabled = activeWorld != null;
		((Button)inters.get(5)).enabled = activeWorld != null;

		wsl.x = fX-(fW/2)+32;
		wsl.y = fY-(fH/2)+32-wsl.oy;
		wsl.w = fW-64;
		wsl.h = fH-24-48-12-48-24-64;
		
		if (wsl.slots.size() != WorldManager.getSize()) {
			wsl.slots.clear();
			int i = 0;
			for(SaveFormatComparator sfc : WorldManager.getList()) {
				wsl.slots.add(new WorldSlot(wsl, WorldManager.getIndex(sfc), wsl.x, wsl.y, wsl.w, 80, 0, i*80));
				i++;
			}
		}
		
		wsl.update(mx, my);
		
		if (popup != null) {
			popup.update(mx, my);
		}
		popupOpacity+= popupFade?-0.2f:0.2f;//((uiFadeIn?1:0)-uiFade)/4f;
		popupOpacity = Math.min(Math.max(0f, popupOpacity), 1f);
		if (popupFade) {
			if (popupOpacity < 0.05f) {
				popup = popupTo;
				popupFade = false;
				if (popup != null) {
					popup.init();
				}
			}
		}
	}
	
	@Override
	public void interact(Inter i) {
		SaveFormatComparator activeWorld = null;
		int index = -1;
		
		int a = 0;
		for(Slot s : wsl.slots) {
			if (s.active) {
				index = a;
				activeWorld = WorldManager.getWorld(((WorldSlot)s).index);
				break;
			}
			a++;
		}
		switch(inters.indexOf(i)) {
		case 0:
            mc.displayGuiScreen(new GuiMainMenu());
			break;
		case 1:
			loadWorld(index);
			break;
		case 2:
            mc.displayGuiScreen(new GuiCreateWorld(mc.currentScreen));
			break;
		case 3:
			changePopup(new UIPopupWorldRename(this, activeWorld));
			break;
		case 4:
			if (index != -1) {
                GuiCreateWorld var5 = new GuiCreateWorld(mc.currentScreen);
                ISaveHandler var6 = this.mc.getSaveLoader().getSaveLoader(WorldManager.getWorld(index).getFileName(), false);
                WorldInfo var4 = var6.loadWorldInfo();
                var6.flush();
                var5.func_146318_a(var4);
                this.mc.displayGuiScreen(var5);
                ((UICreateWorld)UIHandler.uiTo).set(var4);
			}
			break;
		case 5:
			removeWorld(activeWorld);
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (popup == null) {
			super.mouseClick(mx, my, b);
			wsl.mouseClick(mx, my, b);
		} else {
			popup.mouseClick(mx, my, b);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		if (popup == null) {
			super.mouseRelease(mx, my, b);
			wsl.mouseRelease(mx, my, b);
		} else {
			popup.mouseRelease(mx, my, b);
		}
	}
	
	public void mouseScroll(int s) {
		if (popup == null) {
			super.mouseScroll(s);
			wsl.mouseScroll(s);
		} else {
			popup.mouseScroll(s);
		}
	}
	
	public void keyboardPress(int key) {
		if (popup == null) {
			SaveFormatComparator activeWorld = null;
			int index = -1;
			
			int a = 0;
			for(Slot s : wsl.slots) {
				if (s.active) {
					index = a;
					activeWorld = WorldManager.getWorld(((WorldSlot)s).index);
					break;
				}
				a++;
			}
			
			super.keyboardPress(key);
			switch (key) {
			case Keyboard.KEY_RETURN:
				break;
			case Keyboard.KEY_DELETE:
				break;
			}
		} else {
			popup.keyboardPress(key);
		}
	}

	public void keyTyped(int key, char keyChar) {
		if (popup == null) {
			super.keyTyped(key, keyChar);
		} else {
			popup.keyTyped(key, keyChar);
		}
	}
	
	public void changePopup(UI u) {
		popupTo = u;
		popupFade = true;
	}
	
	public void removeWorld(SaveFormatComparator s) {
        ISaveFormat var3 = this.mc.getSaveLoader();
        var3.flushCache();
        var3.deleteWorldDirectory(s.getFileName());

        WorldManager.loadWorlds();
	}
	
	public void loadWorld(int index)
    {
        mc.displayGuiScreen((GuiScreen)null);

        if (!loadingWorld)
        {
            loadingWorld = true;
            String var2 = WorldManager.getWorld(index).getFileName();

            if (var2 == null)
            {
                var2 = "World" + index;
            }

            String var3 = getWorldName(index);

            if (var3 == null)
            {
                var3 = "World" + index;
            }

            if (mc.getSaveLoader().canLoadWorld(var2))
            {
                mc.launchIntegratedServer(var2, var3, (WorldSettings)null);
            }
        }
    }
	
	public String getWorldName(int index) {
        String var2 = WorldManager.getWorld(index).getDisplayName();

        if (StringUtils.isEmpty(var2))
        {
            var2 = I18n.format("selectWorld.world", new Object[0]) + " " + (index + 1);
        }

        return var2;
	}
	
	public void render(float opacity) {
		drawDarkerBackground(false, opacity);
		
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF111111, 1f*opacity));
		
		Draw.rect(fX-(fW/2)+24, fY+(fH/2)-24-48-12-48-26, fX+(fW/2)-24, fY+(fH/2)-24-48-12-48-24, Util.reAlpha(Colors.WHITE.c, 0.2f*opacity));



		Draw.startClip(fX-(fW/2)+32, fY-(fH/2)+32, fX+(fW/2)-31+wsl.sbw, fY+(fH/2)-24-48-12-48-24-32);
		wsl.render(opacity);
		Draw.endClip();
		
		GlStateManager.pushMatrix();

		
		GlStateManager.popMatrix();
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-40-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
		
		super.render(opacity);
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
	}
}
