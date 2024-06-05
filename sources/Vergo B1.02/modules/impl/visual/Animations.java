package xyz.vergoclient.modules.impl.visual;

import java.util.Arrays;

import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventSwordBlockAnimation;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.ModeSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;

public class Animations extends Module implements OnEventInterface {

	public Animations() {
		super("Animations", Category.VISUAL);
	}
	
	public ModeSetting mode = new ModeSetting("Style", "1.7", "1.7", "Exhi1", "Exhi2", "Swang", "SigmaBalls");
	
	@Override
	public void loadSettings() {
		
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("1.7", "Exhi1", "Exhi2", "Swang", "SigmaBalls"));
		
		addSettings(mode);
		
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventSwordBlockAnimation && e.isPre()) {
			EventSwordBlockAnimation event = (EventSwordBlockAnimation)e;
			event.setCanceled(false);
			
			ItemRenderer ir = mc.getItemRenderer();
			float partialTicks = ir.partTicks;
			
			float f = 1.0F - (mc.getItemRenderer().prevEquippedProgress + (ir.equippedProgress - ir.prevEquippedProgress) * partialTicks);
			float swingProgress = mc.thePlayer.getSwingProgress(partialTicks);
			
			if (mode.is("1.7")) {
				GlStateManager.translate(-0.15f, 0.15f, -0.2f);
				ir.transformFirstPersonItem(f, swingProgress);
				ir.func_178103_d();
			} else if(mode.is("Exhi1")) {
				GlStateManager.translate(0, 0.18F, 0);
				ir.transformFirstPersonItem(f / 2.0f, swingProgress);
				ir.func_178103_d();
			} else if(mode.is("Exhi2")) {
				ir.transformFirstPersonItem(f / 2.0F, swingProgress);
				float var15 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
				GlStateManager.rotate(var15 * 30F / 2.0F, -var15, -0.0F, 9.0F);
				GlStateManager.rotate(var15 * 40.0F, 1.0F, -var15 / 2.0F, -0.0F);
				GlStateManager.translate(0, 0.3F, 0);
				ir.func_178103_d();
			} else if(mode.is("SigmaBalls")) {
				ir.transformFirstPersonItem(f, 0.0f);
				float swong = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
				GlStateManager.rotate(-swong * 55 / 2.0F, -8.0F, -0.0F, 9.0F);
				GlStateManager.rotate(-swong * 45, 1.0F, swong/2, -0.0F);
				ir.func_178103_d();
				GL11.glTranslated(1.2, 0.3,0.5);
				GL11.glTranslatef(-1, -0.2F, 0.2F);
			} else if(mode.is("Swang")) {
				ir.transformFirstPersonItem(f / 2.0F, swingProgress);
				float var15 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
				GlStateManager.rotate(var15 * 30F / 2.0F, -var15, -0.0F, 9.0F);
				GlStateManager.rotate(var15 * 40.0F, 1.0F, -var15 / 2.0F, -0.0F);
				GlStateManager.translate(0, 0.3F, 0);
				ir.func_178103_d();
			}
			
		}
		else if (e instanceof EventTick && e.isPre()) {
			if(!mode.is("SigmaBalls")) {
				setInfo(mode.getMode());
			} else {
				setInfo("Sigma");
			}
		}
		
	}

}
