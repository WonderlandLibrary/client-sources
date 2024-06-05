package zamorozka.modules.VISUALLY;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.OutlineUtils;

public class Traser extends Module{

	public Traser() {
		super("Tracers", 0, Category.VISUALLY);
	}
	@Override
    public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("TracerEnderChest", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("EndPortal", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Shulker", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Player", this, true));
        Zamorozka.settingsManager.rSetting(new Setting("Chest", this, false));
    	Zamorozka.settingsManager.rSetting(new Setting("Mob", this, false));
    	
    	Zamorozka.settingsManager.rSetting(new Setting("Range", this, true));
    }
	
	  public void onRender()
	  {
	    if (!getState()) {
	      return;
	    }
	    if(Zamorozka.settingsManager.getSettingByName("Player").getValBoolean()){
		    GL11.glPushMatrix();
		    GL11.glEnable(2848);
		    GL11.glDisable(2929);
		    GL11.glDisable(3553);
		    GL11.glDepthMask(false);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(3042);
		    GL11.glLineWidth(1F);
	        GL11.glDisable(GL11.GL_LIGHTING);
		    for (Entity entities : mc.world.loadedEntityList) {
		      if ((entities != mc.player) && (entities != null) && 
		        ((entities instanceof EntityPlayer)))
		      {
		        float distance = mc.renderViewEntity.getDistanceToEntity(entities);
		        double posX = entities.lastTickPosX + (entities.posX - entities.lastTickPosX) - 
		          RenderManager.renderPosX;
		        double posY = entities.lastTickPosY + (entities.posY - entities.lastTickPosY) - 
		          RenderManager.renderPosY;
		        double posZ = entities.lastTickPosZ + (entities.posZ - entities.lastTickPosZ) - 
		          RenderManager.renderPosZ;
		    	float x = System.currentTimeMillis() % 2000 / 1000F;
		    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
		    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
		    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);
		    	if(indexer.getFriends().isFriend(entities.getName())){
		    		GL11.glColor4d(0, 1, 0, 0.9F);
		    	 }else{
		    		// GL11.glColor4d(0, 0, 1, 0.5F);
		    		 if(Zamorozka.settingsManager.getSettingByName("Range").getValBoolean()){
		 		          float f = this.mc.player.getDistanceToEntity(entities) / 20.0F;
		 		          GL11.glColor4f(f, 0, 1, 0.5F);
		    		 }else{
		    			 Color n = new Color(19, 143, 255);
		                 GL11.glColor4d(n.getRed() / 255f, n.getGreen() / 255f, n.getBlue() / 255f, n.getAlpha() / 255f);
		    			 
		    		 }
		    	 }
		        
		        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
		        GL11.glBegin(2);
		        
		        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		        GL11.glVertex3d(posX, posY, posZ);
		        
		        
		        GL11.glEnd();
		      }
		    }
		    GL11.glEnable(GL11.GL_LIGHTING);
		    GL11.glDisable(3042);
		    GL11.glDepthMask(true);
		    GL11.glEnable(3553);
		    GL11.glEnable(2929);
		    GL11.glDisable(2848);
		    GL11.glPopMatrix();	
	    }
	    if(Zamorozka.settingsManager.getSettingByName("Chest").getValBoolean()){
		    GL11.glPushMatrix();
		    GL11.glEnable(2848);
		    GL11.glDisable(2929);
		    GL11.glDisable(3553);
		    GL11.glDepthMask(false);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(3042);
		    GL11.glLineWidth(1F);
	        GL11.glDisable(GL11.GL_LIGHTING);
		    for (TileEntity entities : mc.world.loadedTileEntityList) {
		      if ((entities != null) && 
		        ((entities instanceof TileEntityChest)))
		      {

		        double posX = entities.getPos().getX() - 
		          RenderManager.renderPosX;
		        double posY = entities.getPos().getY() - 
		          RenderManager.renderPosY;
		        double posZ = entities.getPos().getZ() - 
		          RenderManager.renderPosZ;
		    	float x = System.currentTimeMillis() % 2000 / 1000F;
		    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
		    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
		    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);

		    			 Color n = new Color(19, 143, 255);
		                 GL11.glColor4d(n.getRed() / 255f, n.getGreen() / 255f, n.getBlue() / 255f, n.getAlpha() / 255f);

		        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
		        GL11.glBegin(2);
		        
		        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		        GL11.glVertex3d(posX, posY, posZ);
		        
		        
		        GL11.glEnd();
		      }
		    }

		    GL11.glEnable(GL11.GL_LIGHTING);
		    GL11.glDisable(3042);
		    GL11.glDepthMask(true);
		    GL11.glEnable(3553);
		    GL11.glEnable(2929);
		    GL11.glDisable(2848);
		    GL11.glPopMatrix();	
	    }
	    if(Zamorozka.settingsManager.getSettingByName("TracerEnderChest").getValBoolean()){
		    GL11.glPushMatrix();
		    GL11.glEnable(2848);
		    GL11.glDisable(2929);
		    GL11.glDisable(3553);
		    GL11.glDepthMask(false);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(3042);
		    GL11.glLineWidth(1F);
	        GL11.glDisable(GL11.GL_LIGHTING);
		    for (TileEntity entities : mc.world.loadedTileEntityList) {
		      if ((entities != null) && 
		        ((entities instanceof TileEntityEnderChest)))
		      {

		        double posX = entities.getPos().getX() - 
		          RenderManager.renderPosX;
		        double posY = entities.getPos().getY() - 
		          RenderManager.renderPosY;
		        double posZ = entities.getPos().getZ() - 
		          RenderManager.renderPosZ;
		    	float x = System.currentTimeMillis() % 2000 / 1000F;
		    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
		    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
		    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);

		    			 Color n = new Color(194, 143, 229);
		                 GL11.glColor4d(n.getRed() / 255f, n.getGreen() / 255f, n.getBlue() / 255f, n.getAlpha() / 255f);

		        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
		        GL11.glBegin(2);
		        
		        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		        GL11.glVertex3d(posX, posY, posZ);
		        
		        
		        GL11.glEnd();
		      }
		    }
		    GL11.glEnable(GL11.GL_LIGHTING);
		    GL11.glDisable(3042);
		    GL11.glDepthMask(true);
		    GL11.glEnable(3553);
		    GL11.glEnable(2929);
		    GL11.glDisable(2848);
		    GL11.glPopMatrix();	
	    }

	    if(Zamorozka.settingsManager.getSettingByName("EndPortal").getValBoolean()){
	    	if (!getState()) {
			      return;
			    }
			    GL11.glPushMatrix();
			    GL11.glEnable(2848);
			    GL11.glDisable(2929);
			    GL11.glDisable(3553);
			    GL11.glDepthMask(false);
			    GL11.glBlendFunc(770, 771);
			    GL11.glEnable(3042);
			    GL11.glLineWidth(1F);
			    
			    for (TileEntity entities : mc.world.loadedTileEntityList) {
			      if (entities instanceof TileEntityEndPortal)
			      {
			    	float x = System.currentTimeMillis() % 2000 / 1000F;
			    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
			    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
			    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);
			        double posX = entities.getPos().getX() + (entities.getPos().getX() -entities.getPos().getX()) - 
			  	          RenderManager.renderPosX;
			  	        double posY = entities.getPos().getY() + (entities.getPos().getY() - entities.getPos().getY()) - 
			  	          RenderManager.renderPosY;
			  	        double posZ = entities.getPos().getZ() + (entities.getPos().getZ() - entities.getPos().getZ()) - 
			  	          RenderManager.renderPosZ;
			    	GL11.glColor4f(0.4f, 0.6f, 1.0f, 0.5f);
			        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
			        GL11.glBegin(2);
			        
			        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
			        GL11.glVertex3d(posX, posY, posZ);
			        GL11.glEnd();
			      }
			    }
			    
			    GL11.glDisable(3042);
			    GL11.glDepthMask(true);
			    GL11.glEnable(3553);
			    GL11.glEnable(2929);
			    GL11.glDisable(2848);
			    GL11.glPopMatrix();
	    }
	    if(Zamorozka.settingsManager.getSettingByName("Shulker").getValBoolean()){
	    	if (!getState()) {
			      return;
			    }
			    GL11.glPushMatrix();
			    GL11.glEnable(2848);
			    GL11.glDisable(2929);
			    GL11.glDisable(3553);
			    GL11.glDepthMask(false);
			    GL11.glBlendFunc(770, 771);
			    GL11.glEnable(3042);
			    GL11.glLineWidth(1F);
			    GL11.glDisable(GL11.GL_LIGHTING);
			    for (TileEntity entities : mc.world.loadedTileEntityList) {
			      if (entities instanceof TileEntityShulkerBox)
			      {
			    	float x = System.currentTimeMillis() % 2000 / 1000F;
			    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
			    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
			    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);
			        double posX = entities.getPos().getX() + (entities.getPos().getX() -entities.getPos().getX()) - 
			  	          RenderManager.renderPosX;
			  	        double posY = entities.getPos().getY() + (entities.getPos().getY() - entities.getPos().getY()) - 
			  	          RenderManager.renderPosY;
			  	        double posZ = entities.getPos().getZ() + (entities.getPos().getZ() - entities.getPos().getZ()) - 
			  	          RenderManager.renderPosZ;
			    	GL11.glColor4f(	253, 229, 204, 1.5f);
			        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
			        GL11.glBegin(2);
			        
			        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
			        GL11.glVertex3d(posX, posY, posZ);
			        GL11.glEnd();
			      }
			    }
			    GL11.glEnable(GL11.GL_LIGHTING);
			    GL11.glDisable(3042);
			    GL11.glDepthMask(true);
			    GL11.glEnable(3553);
			    GL11.glEnable(2929);
			    GL11.glDisable(2848);
			    GL11.glPopMatrix();
	    }
	    if(Zamorozka.settingsManager.getSettingByName("Mob").getValBoolean()){
		    GL11.glPushMatrix();
		    GL11.glEnable(2848);
		    GL11.glDisable(2929);
		    GL11.glDisable(3553);
		    GL11.glDepthMask(false);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(3042);
		    GL11.glLineWidth(1F);
		    for (Entity entities : mc.world.loadedEntityList) {
		      if ((entities != mc.player) && (entities != null) && 
		        (!(entities instanceof EntityPlayer)))
		      {
		        float distance = mc.renderViewEntity.getDistanceToEntity(entities);
		        double posX = entities.lastTickPosX + (entities.posX - entities.lastTickPosX) - 
		          RenderManager.renderPosX;
		        double posY = entities.lastTickPosY + (entities.posY - entities.lastTickPosY) - 
		          RenderManager.renderPosY;
		        double posZ = entities.lastTickPosZ + (entities.posZ - entities.lastTickPosZ) - 
		          RenderManager.renderPosZ;
		    	float x = System.currentTimeMillis() % 2000 / 1000F;
		    	float red = 0.5F + 0.5F * MathHelper.sin(x * (float)Math.PI);
		    	float green = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * (float)Math.PI);
		    	float blue = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * (float)Math.PI);
		    	if(indexer.getFriends().isFriend(entities.getName())){
		    		GL11.glColor4d(0, 1, 0, 0.9F);
		    	 }else{
		    		// GL11.glColor4d(0, 0, 1, 0.5F);
		    			 GL11.glColor4f(1, 2, 1f,  0.5F);
		    			 
		    		 
		    	 }
		        
		        Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
		        GL11.glBegin(2);
		        
		        GL11.glVertex3d(eyes.xCoord, mc.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		        GL11.glVertex3d(posX, posY, posZ);
		        
		        GL11.glEnd();
		      }
		    }
		    
		    GL11.glDisable(3042);
		    GL11.glDepthMask(true);
		    GL11.glEnable(3553);
		    GL11.glEnable(2929);
		    GL11.glDisable(2848);
		    GL11.glPopMatrix();	
	    }
	  }
	    public static int rainbow(int delay) {
	        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
	        rainbowState %= 360;
	        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	  }

}