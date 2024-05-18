package com.kilo.ui.inter.slotlist.slot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.SaveFormatComparator;

import org.apache.commons.lang3.StringUtils;
import org.newdawn.slick.opengl.Texture;

import com.kilo.manager.ServerManager;
import com.kilo.manager.WorldManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class WorldSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
    private final DateFormat dateFormat = new SimpleDateFormat();
	
	public int index;
	
	public int clicks = 0;
	public Timer clickTimer = new Timer();
	
	public WorldSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		clickTimer.reset();
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		hover = mouseOver(mx, my) && parent.mouseOver(mx, my);

		if (clicks == 2) {
			if (WorldManager.getSize() > 0) {
				if (WorldManager.getWorld(index) != null) {
					loadWorld(index);
				}
			}
			clicks = 0;
		}
		
		if (clickTimer.isTime(Util.doubleClickTimer)) {
			clickTimer.reset();
			clicks = 0;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (parent.mouseOver(mx, my)) {
			active = hover;
		}
		
		if (hover) {
			if (clicks == 0) {
				clickTimer.reset();
			}
			clicks++;
		}
	}
	
	public void loadWorld(int index)
    {
        mc.displayGuiScreen((GuiScreen)null);

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
	
	public String getWorldName(int index) {
        String var2 = WorldManager.getWorld(index).getDisplayName();

        if (StringUtils.isEmpty(var2))
        {
            var2 = I18n.format("selectWorld.world", new Object[0]) + " " + (index + 1);
        }

        return var2;
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, activeOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF0A0A0A, hoverOpacity*opacity));
		if (WorldManager.getWorld(index) != null) {

			SaveFormatComparator world = WorldManager.getWorld(index);

			Texture icon = null;
			int color = Colors.WHITE.c;
			switch (world.getEnumGameType()) {
			case ADVENTURE:
				icon = Resources.iconAdventure[4];
				color = Colors.YELLOW.c;
				break;
			case CREATIVE:
				icon = Resources.iconCreative[4];
				color = Colors.BLUE.c;
				break;
			case NOT_SET:
				break;
			case SPECTATOR:
				icon = Resources.iconSpectator[4];
				color = Colors.WHITE.c;
				break;
			case SURVIVAL:
				icon = Resources.iconSurvival[4];
				color = Colors.RED.c;
				break;
			}
			
			if (icon != null) {
				Draw.rectTexture(x+24, y+16, 48, 48, icon, Util.reAlpha(color, 1f*opacity));
			} else {
				Draw.loader(x+48, y+30, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			
			List<String> info = new ArrayList<String>();
			if (world.isHardcoreModeEnabled()) { info.add("\u00a7cHardcore"); }
			if (world.getCheatsEnabled()) { info.add("\u00a7aCheats"); }
			String infoLine = "";
			for(String s : info) {
				infoLine+= s;
				if (info.indexOf(s) < info.size()-1) {
					infoLine+= " \u00a77| ";
				}
			}
			
			if (world.requiresConversion()) {
				Draw.string(Fonts.ttfRoundedBold20, x+104+((width-104)/2), y+40, "This world needs to be converted!", Util.reAlpha(Colors.RED.c, 1f*opacity), Align.C, Align.C);
			} else {
				Draw.string(Fonts.ttfRoundedBold20, x+104, y+(infoLine.length() == 0?44:32), world.getDisplayName(), Util.reAlpha(Colors.BLUE.c, 1f*opacity), Align.L, Align.B);
				Draw.string(Fonts.ttfRoundedBold14, x+104, y+(infoLine.length() == 0?44:40), world.getFileName()+" ("+dateFormat.format(new Date(world.getLastTimePlayed()))+")", Util.reAlpha(Colors.GREY.c, 0.5f*opacity), Align.L, (infoLine.length() == 0?Align.T:Align.C));
				Draw.string(Fonts.ttfRoundedBold14, x+104, y+48, infoLine, Util.reAlpha(Colors.GREY.c, 0.5f*opacity), Align.L, Align.T);
			}
		}
		super.render(opacity);
	}
}
