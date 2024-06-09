package lunadevs.luna.module.render;

import org.lwjgl.opengl.GL11;

import java.util.Iterator;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventRender3D;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class KekESP extends Module{

	public KekESP() {
		super("KekESP", 0, Category.RENDER, false);
	}
	
	public void onRender(EventRender3D e) {
		if(!this.isEnabled) return;
		 Iterator var9 = Minecraft.getMinecraft().theWorld.playerEntities.iterator();
		    while (var9.hasNext())
		    {
		      EntityPlayer player = (EntityPlayer)var9.next();
		      if (((player.isEntityAlive()) && (!(player instanceof EntityPlayerSP)) && (player.getName().toLowerCase().contains("kev"))) || (player.getName().toLowerCase().contains("kevin")))
		      {
		        double x = player.posX - RenderManager.renderPosX;
		        double y = player.posY - RenderManager.renderPosY;
		        double z = player.posZ - RenderManager.renderPosZ;
		        GL11.glColor3f(1.0F, 0.0F, 0.0F);
		        
		        GL11.glPushMatrix();
		        GL11.glLoadIdentity();
		        boolean bobbing1 = this.mc.gameSettings.viewBobbing;
		        this.mc.gameSettings.viewBobbing = false;
		        this.mc.entityRenderer.orientCamera(e.getPartialTicks());
		        GL11.glBegin(1);
		        GL11.glVertex3d(0.0D, this.mc.thePlayer.getEyeHeight(), 0.0D);
		        GL11.glVertex3d(x, y, z);
		        GL11.glEnd();
		        GL11.glBegin(1);
		        GL11.glVertex3d(x, y, z);
		        GL11.glVertex3d(x, y + player.height, z);
		        GL11.glEnd();
		        this.mc.gameSettings.viewBobbing = bobbing1;
		        GL11.glPopMatrix();
		      }
		    }
		  }

	
	public String getValue() {
		return null;
	}
	
}
