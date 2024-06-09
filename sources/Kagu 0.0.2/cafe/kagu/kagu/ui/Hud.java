/**
 * 
 */
package cafe.kagu.kagu.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.combat.ModHitboxes;
import cafe.kagu.kagu.mods.impl.ghost.ModHideHud;
import cafe.kagu.kagu.mods.impl.player.ModNoFall;
import cafe.kagu.kagu.mods.impl.visual.ModHud;
import cafe.kagu.kagu.utils.StencilUtil;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author lavaflowglow
 *
 */
public class Hud {
	
	@EventHandler
	private Handler<EventRender2D> renderHud = e -> {
		
		// Don't render if hide hud is enabled
		if (Kagu.getModuleManager().getModule(ModHideHud.class).isEnabled())
			return;
		
		// We only want to render on the post event
		if (e.isPre()) {
			return;
		}
		
		// mc and sr
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution sr = new ScaledResolution(mc);
		
		// The fonts used
		FontRenderer mainFr = FontUtils.STRATUM2_MEDIUM_13_AA;
		
		// Render hud
		String watermark = Kagu.getName() + " v" + Kagu.getVersion();
		mainFr.drawString(watermark, 1.5, 1, 0x80000000);
		mainFr.drawString(watermark, 0.5, 0, -1);
		
		// Arraylist
		drawArraylist(mc, sr);
		
	};
	
	/**
	 * Renders the arraylist
	 * @param mc Minecraft
	 * @param sr Scaled Resolution
	 */
	private static void drawArraylist(Minecraft mc, ScaledResolution sr) {
		
		// Vars
		String separator = " - ";
		FontRenderer moduleFr = FontUtils.SAN_FRANCISCO_REGULAR_10_AA;
		FontRenderer infoFr = FontUtils.SAN_FRANCISCO_THIN_10_AA;
		List<Module> mods = new ArrayList<Module>(Kagu.getModuleManager().getModules());
		double rightPad = 2;
		double topPad = 0.5;
		double index = 0;
		
		// Sort mods
		mods = mods.stream().filter(mod -> mod.getArraylistAnimation() > 0.01).collect(Collectors.toList());
		mods.sort(Comparator.comparingDouble(module -> moduleFr.getStringWidth(module.getName()) + infoFr.getStringWidth(module.getInfoAsString(separator))));
		Collections.reverse(mods);
		
		// Draw
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.translate(-rightPad, topPad, 0);
		for (Module mod : mods) {
			
			// Module info string
			String info = mod.getInfoAsString(separator);
			double infoLength = infoFr.getStringWidth(info);
			
			// For animations
			double indexIncrement = 1;
			
			// Animation
			GlStateManager.pushMatrix();
			
			switch(Kagu.getModuleManager().getModule(ModHud.class).getArraylistAnimationModeSetting().getMode()) {
				case "Slide":{
					indexIncrement = mod.getArraylistAnimation();
					GlStateManager.translate((moduleFr.getStringWidth(mod.getName()) + infoLength + 4) * (1 - mod.getArraylistAnimation()), 0, 0);
				}break;
				case "Squeeze":{
					indexIncrement = mod.getArraylistAnimation();
					GlStateManager.translate(0, index * moduleFr.getFontHeight(), 0);
					GlStateManager.scale(1, indexIncrement, 1);
					GlStateManager.translate(0, -(index * moduleFr.getFontHeight()), 0);
				}break;
			}
			
			// Color for the text
			int textColor = Kagu.getModuleManager().getModule(ModHud.class).getArraylistColors().is("White") ? -1 : mod.getCategory().getArraylistColor();
			
			// Module name
			moduleFr.drawString(mod.getName(), sr.getScaledWidth() - moduleFr.getStringWidth(mod.getName()) - infoLength, index * moduleFr.getFontHeight(), textColor, true);
			
			// Module info
			infoFr.drawString(info, sr.getScaledWidth() - infoLength, index * moduleFr.getFontHeight(), textColor, true);
			
			GlStateManager.popMatrix();
			
			// Used to calculate y
			index += indexIncrement;
		}
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
	}
	
	/**
	 * Handles animations
	 */
	@EventHandler
	private Handler<EventCheatRenderTick> onCheatTick = e -> {
		if (e.isPost())
			return;
		
		double animationSpeed = 0.15;
		for (Module mod : Kagu.getModuleManager().getModules()) {
			
			// Arraylist animation
			if (mod.isEnabled()) {
				mod.setArraylistAnimation(mod.getArraylistAnimation() + ((1 - mod.getArraylistAnimation()) * animationSpeed));
			}else {
				mod.setArraylistAnimation(mod.getArraylistAnimation() - (mod.getArraylistAnimation() * animationSpeed));
			}
			
		}
		
	};
	
}
