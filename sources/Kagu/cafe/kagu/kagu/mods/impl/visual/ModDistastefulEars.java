/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.ghost.ModEagle;
import cafe.kagu.kagu.utils.SpoofUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author DistastefulBannock
 *
 */
public class ModDistastefulEars extends Module {

	public ModDistastefulEars() {
		super("DistastefulEars", Category.VISUAL);
	}

	@EventHandler
	private Handler<EventRender3D> onRender3D = e -> {
		
		if (mc.gameSettings.thirdPersonView == 0) {
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.enableDepth();
		if (Kagu.getModuleManager().getModule(ModEsp.class).isEnabled() && Kagu.getModuleManager().getModule(ModEsp.class).getChams().isEnabled()) {
			GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
			GL11.glPolygonOffset(1.0f, -1099998.0f);
		}
		
		if (mc.thePlayer.isSneaking()) {
			GlStateManager.translate(0, -0.17, 0);
		}

		double pitchFixer = 0.2;
		float timer = mc.getTimer().getRenderPartialTicks();
		double yaw = mc.thePlayer.prevRotationYawHead
				+ (mc.thePlayer.rotationYawHead - mc.thePlayer.prevRotationYawHead) * timer;
		
		if (SpoofUtils.isSpoofYaw() && Kagu.getModuleManager().getModule(ModViewModels.class).getOverrideF3().isEnabled()) {
			yaw = SpoofUtils.getSpoofedYaw();
		}

		GlStateManager.rotate((float) -yaw, 0, 1, 0);
		GlStateManager.rotate(90, 1, 0, 0);
		GlStateManager.translate(0, 0, -(mc.thePlayer.getEyeHeight() - pitchFixer));
		GlStateManager.rotate(SpoofUtils.isSpoofPitch() && Kagu.getModuleManager().getModule(ModViewModels.class).getOverrideF3().isEnabled() ? SpoofUtils.getSpoofedPitch() : mc.thePlayer.rotationPitch, 1,
				0, 0);
		GlStateManager.translate(0, 0, -(0.27 + pitchFixer));
		
		// Draw cat ears
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        double headSize = 0.23;
    	double earWidth = headSize * 0.7;
    	double earLength = earWidth;
    	double earSize = earWidth * 0.8;
    	
    	int mainEarR = 30;
    	int mainEarG = 30;
    	int mainEarB = 30;
    	
    	int innerEarR = 165;
    	int innerEarG = 224;
    	int innerEarB = 254;
    	
		// Main ear
    	worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2, earSize, -earLength).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, 0, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
    	tessellator.draw();
    	worldrenderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2 - earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, 0, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
    	tessellator.draw();
    	
		// Inner ear
    	worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2, earSize, -earLength * 0.7).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2 - earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, 0).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, earSize * 0.75, 0).color(mainEarR, innerEarR, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2 + earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, 0).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
    	tessellator.draw();
    	worldrenderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2 + earWidth / 2 - earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, -0.001).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, earSize * 0.75, -0.001).color(mainEarR, innerEarR, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2 + earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, -0.001).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
    	tessellator.draw();
    	
    	headSize *= -1;
    	
		// Main ear
    	worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2, earSize, -earLength).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, 0, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
    	tessellator.draw();
    	worldrenderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2 - earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, 0, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2, earSize, 0).color(mainEarR, mainEarG, mainEarB, 255).endVertex();
    	tessellator.draw();
    	
		// Inner ear
    	worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2, earSize, -earLength * 0.7).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 + earWidth / 2 - earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, 0).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, earSize * 0.75, 0).color(mainEarR, innerEarR, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2 + earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, 0).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
    	tessellator.draw();
    	worldrenderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(headSize / 2 + earWidth / 2 - earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, -0.001).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2, earSize * 0.75, -0.001).color(mainEarR, innerEarR, innerEarB, 255).endVertex();
		worldrenderer.pos(headSize / 2 - earWidth / 2 + earLength * 0.23, earSize * 0.7 + earSize * 0.25 * 0.75, -0.001).color(innerEarR, innerEarG, innerEarB, 255).endVertex();
    	tessellator.draw();
        
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

	};

}
