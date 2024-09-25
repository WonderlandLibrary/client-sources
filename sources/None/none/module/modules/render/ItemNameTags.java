package none.module.modules.render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.module.Category;
import none.module.Module;
import none.module.modules.render.HealthParticle.Data;
import none.utils.render.TTFFontRenderer;

public class ItemNameTags extends Module{

	public ItemNameTags() {
		super("ItemNameTags", "ItemNameTags", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}
	
	@Override
	@RegisterEvent(events = {Event3D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof Event3D) {
			Event3D event3d = (Event3D) event;
			
			if (mc.thePlayer == null) return;
			if (!mc.theWorld.loadedEntityList.isEmpty() && mc.thePlayer.ticksExisted > 10)
			for (Entity e : mc.theWorld.loadedEntityList) {
				if (e instanceof EntityItem) {
					EntityItem entity = (EntityItem) e;
					if (mc.thePlayer.getDistanceToEntity(entity) <= 36) {
						float[] xyz = {(float) (entity.posX - mc.thePlayer.posX), (float) (entity.posY - mc.thePlayer.posY), (float) (entity.posZ - mc.thePlayer.posZ)};
						float f = RenderManager.playerViewY;
						float f1 = RenderManager.playerViewX;
						boolean flag1 = RenderManager.options.thirdPersonView == 2;
						TTFFontRenderer fr = Client.fm.getFont("BebasNeue");
						drawNameplate(fr, "" + entity.getName(), xyz[0], xyz[1] + 0.8F, xyz[2], 0, f, f1, flag1, false);
					}
				}
			}
		}
	}
	
	public void drawNameplate(TTFFontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking)
    {
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager renderManager = mc.getRenderManager();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        byte b0 = 0;
        int renderColor = ClientColor.rainbow.getObject() ? ClientColor.rainbow(100) : ClientColor.getColor();

        int i = (int) (fontRendererIn.getStringWidth(str) / 2);
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)(-i - 1), (double)(-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(-i - 1), (double)(8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(i + 1), (double)(8 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(i + 1), (double)(-1 + b0), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, b0, renderColor);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, b0, renderColor);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
