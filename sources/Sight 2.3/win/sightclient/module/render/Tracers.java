package win.sightclient.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import win.sightclient.event.Event;
import win.sightclient.event.events.render.EventRender3D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ColorSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.utils.minecraft.CombatUtils;

public class Tracers extends Module {

	//private ModeSetting color = new ModeSetting("Color", this, new String[] {"Select", "Distance", "Health"});
	private ColorSetting red = new ColorSetting("Color", this, Color.white);
	public Tracers() {
		super("Tracers", Category.RENDER);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventRender3D) {
			GlStateManager.pushMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.disableDepth();
			GL11.glLineWidth(1);
			float partialTicks = ((EventRender3D)event).getPartialTicks();
			float x = (float) ((float) (mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * partialTicks) - RenderManager.renderPosX);
            float y = (float) ((float) (mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * partialTicks) - RenderManager.renderPosY);
            float z = (float) ((float) (mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * partialTicks) - RenderManager.renderPosZ);
            if (mc.gameSettings.thirdPersonView == 0) {
                GL11.glLoadIdentity();
    	        Minecraft.getMinecraft().entityRenderer.orientCamera(Minecraft.getMinecraft().timer.renderPartialTicks);
            } else {
                x = (float) (mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double)partialTicks);
                y = (float) (mc.thePlayer.prevPosY + (mc.thePlayer.posY - mc.thePlayer.prevPosY) * (double)partialTicks);
                z = (float) (mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double)partialTicks);
            	GlStateManager.translate(-mc.getRenderManager().viewerPosX,
						-mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
            }
            Vec3 playerPos = new Vec3(x, y + mc.thePlayer.getEyeHeight(), z);
            for (Entity e : mc.theWorld.loadedEntityList) {
				if (CombatUtils.isValid(e, false) && e != mc.thePlayer) {
					float x2 = (float) ((float) (e.lastTickPosX + (e.posX - e.lastTickPosX) * partialTicks) - RenderManager.renderPosX);
                    float y2 = (float) ((float) (e.lastTickPosY + (e.posY - e.lastTickPosY) * partialTicks) - RenderManager.renderPosY);
                    float z2 = (float) ((float) (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partialTicks) - RenderManager.renderPosZ);
			       
                    if (mc.gameSettings.thirdPersonView != 0) {
                    	x2 = (float) (e.prevPosX + (e.posX - e.prevPosX) * (double)partialTicks);
                        y2 = (float) (e.prevPosY + (e.posY - e.prevPosY) * (double)partialTicks);
                        z2 = (float) (e.prevPosZ + (e.posZ - e.prevPosZ) * (double)partialTicks);
                    }
        			this.color(e);
					GL11.glBegin(GL11.GL_LINES);
				    GL11.glVertex3d(playerPos.getX(), playerPos.getY(), playerPos.getZ());
				    GL11.glVertex3d(x2, y2, z2);
					GL11.glEnd();
					
					GL11.glBegin(GL11.GL_LINES);
				    GL11.glVertex3d(x2, y2, z2);
				    GL11.glVertex3d(x2, y2 + e.getEyeHeight(), z2);
					GL11.glEnd();
				}
			}
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
			GlStateManager.enableDepth();
			GL11.glColor3f(1F, 1F, 1F);
		}
	}
	
	private void color(Entity e) {
		GL11.glColor3f((float)this.red.getValue().getRed() / 255F, (float)this.red.getValue().getGreen() / 255F, (float)this.red.getValue().getBlue() / 255F);
	}
}
