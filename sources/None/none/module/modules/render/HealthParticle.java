package none.module.modules.render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.module.Category;
import none.module.Module;
import none.utils.render.TTFFontRenderer;

public class HealthParticle extends Module{

	public HealthParticle() {
		super("HealthParticle", "HealthParticle", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	public static ArrayList<Data> datas = new ArrayList<>();
	
	@Override
	protected void onEnable() {
		datas.clear();
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		datas.clear();
		super.onDisable();
	}
	
	@Override
	@RegisterEvent(events = {Event3D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof Event3D) {
			Event3D event3d = (Event3D) event;
			if (mc.thePlayer == null) return;
			if (mc.thePlayer.ticksExisted <= 10) return;
			if (!mc.theWorld.loadedEntityList.isEmpty())
			for (Entity e : mc.theWorld.loadedEntityList) {
				if (e instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase) e;
					if (mc.thePlayer.getDistanceToEntity(entity) <= 10) {
						if (entity.lasthealth != entity.getHealth()) {
							float data = entity.getHealth() - entity.lasthealth;
							datas.add(new Data((float) entity.posX, (float) entity.posY, (float) entity.posZ, data));
						}
						entity.lasthealth = entity.getHealth();
					}
				}
			}
			if (!datas.isEmpty()) {
				for (Data data : datas) {
					float datas = data.getData();
					float[] xyz = {(float) (data.x - mc.thePlayer.posX), (float) (data.y - mc.thePlayer.posY), (float) (data.z - mc.thePlayer.posZ)};
					float f = RenderManager.playerViewY;
					float f1 = RenderManager.playerViewX;
					boolean flag1 = RenderManager.options.thirdPersonView == 2;
					TTFFontRenderer fr = Client.fm.getFont("BebasNeue");
					drawNameplate(fr, "" + datas, xyz[0], xyz[1] + (data.renderCount / 10), xyz[2], 0, f, f1, flag1, false);
					data.renderCount--;
				}
			}
			
			if (!datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {
					Data data = datas.get(i);
					if (data.renderCount <= 0) {
						datas.remove(i);
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
	
	public class Data {
		public float x,y,z;
		public float data;
		public int renderCount = 20;
		public Data(float x, float y, float z, float data) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.data = data;
		}
		
		public float getData() {
			return data;
		}
	}
}
