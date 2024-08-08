package me.xatzdevelopments.ui;

import java.util.Collections;
import java.util.Comparator;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.util.ColorUtil;
import me.xatzdevelopments.util.GlyphPageFontRenderer;
import me.xatzdevelopments.util.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class HUD {

	public Minecraft mc = Minecraft.getMinecraft();
	
	
	
	public void draw() {
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRendererObj;
		//final GlyphPageFontRenderer frn = GlyphPageFontRenderer.create("Verdana", 18, false, false, false);
		TTFFontRenderer frn = Xatz.fm.getFont("Verdana 12");
		//Collections.sort(Xatz.modules, new ModuleComparator());
		String name = null;
		Xatz.modules.sort(Comparator.comparingInt(m -> ((Module)m).addonText == null ? mc.fontRendererObj.getStringWidth(((Module)m).name) : mc.fontRendererObj.getStringWidth(((Module)m).name) + mc.fontRendererObj.getStringWidth(((Module)m).addonText) + mc.fontRendererObj.getStringWidth(" - []")).reversed());
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(4, 4, 0);
		GlStateManager.scale(2, 2, 1);
		GlStateManager.translate(-4, -4, 0);
		fr.drawStringWithShadow(Xatz.name, 4, 4, ColorUtil.getRainbow(4, 1, 1));
		GlStateManager.popMatrix();
		if(Xatz.name.equalsIgnoreCase("Xatz")) {
		fr.drawStringWithShadow("v" + Xatz.version, 52, 11, ColorUtil.getRainbow(4, 1, 1));
		}
		GlStateManager.pushMatrix(); 
		GlStateManager.scale(0.8, 0.8, 1);
//		fr.drawStringWithShadow("Made by " + Xatz.author, 6, 27, ColorUtil.getRainbow(4, 1, 1));
		GlStateManager.popMatrix();

		int count = 0;
		
		for(Module m : Xatz.modules) {
			if(!m.toggled)
				continue;
		
		if(m.addonText == null) {
			name = m.name;
		}else if(m.addonText != null) {
			name = m.name + " - [" + m.getAddonText() + "]";
		}
			
		if(Xatz.getModuleByName("ActiveMods").isEnabled()) {
			double offset = count * (fr.FONT_HEIGHT + 6);
			int y = 1;
			
			Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(name) - 8, 6 + fr.FONT_HEIGHT + offset, ColorUtil.getRainbow(4, 1, 1, count * 150));
			Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
			fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(name) - 4 , (float) (4 + offset), ColorUtil.getRainbow(4, 1, 1, count * 150));
			fr.drawStringWithShadow(" - [" + m.getAddonText() + "]", sr.getScaledWidth() - fr.getStringWidth(name) + fr.getStringWidth(m.name) - 4 , (float) (4 + offset), 0xff0090ff);
			count++;
		  }
		}
		
		Xatz.onEvent(new EventRenderGUI());
		
	}

	
	
}
