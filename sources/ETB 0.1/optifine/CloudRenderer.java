package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class CloudRenderer
{
  private Minecraft mc;
  private boolean updated = false;
  private boolean renderFancy = false;
  int cloudTickCounter;
  float partialTicks;
  private int glListClouds = -1;
  private int cloudTickCounterUpdate = 0;
  private double cloudPlayerX = 0.0D;
  private double cloudPlayerY = 0.0D;
  private double cloudPlayerZ = 0.0D;
  
  public CloudRenderer(Minecraft mc)
  {
    this.mc = mc;
    glListClouds = net.minecraft.client.renderer.GLAllocation.generateDisplayLists(1);
  }
  
  public void prepareToRender(boolean renderFancy, int cloudTickCounter, float partialTicks)
  {
    if (this.renderFancy != renderFancy)
    {
      updated = false;
    }
    
    this.renderFancy = renderFancy;
    this.cloudTickCounter = cloudTickCounter;
    this.partialTicks = partialTicks;
  }
  
  public boolean shouldUpdateGlList()
  {
    if (!updated)
    {
      return true;
    }
    if (cloudTickCounter >= cloudTickCounterUpdate + 20)
    {
      return true;
    }
    

    Entity rve = mc.func_175606_aa();
    boolean belowCloudsPrev = cloudPlayerY + rve.getEyeHeight() < 128.0D + mc.gameSettings.ofCloudsHeight * 128.0F;
    boolean belowClouds = prevPosY + rve.getEyeHeight() < 128.0D + mc.gameSettings.ofCloudsHeight * 128.0F;
    return belowClouds ^ belowCloudsPrev;
  }
  

  public void startUpdateGlList()
  {
    GL11.glNewList(glListClouds, 4864);
  }
  
  public void endUpdateGlList()
  {
    GL11.glEndList();
    cloudTickCounterUpdate = cloudTickCounter;
    cloudPlayerX = mc.func_175606_aa().prevPosX;
    cloudPlayerY = mc.func_175606_aa().prevPosY;
    cloudPlayerZ = mc.func_175606_aa().prevPosZ;
    updated = true;
    GlStateManager.func_179117_G();
  }
  
  public void renderGlList()
  {
    Entity entityliving = mc.func_175606_aa();
    double exactPlayerX = prevPosX + (posX - prevPosX) * partialTicks;
    double exactPlayerY = prevPosY + (posY - prevPosY) * partialTicks;
    double exactPlayerZ = prevPosZ + (posZ - prevPosZ) * partialTicks;
    double dc = cloudTickCounter - cloudTickCounterUpdate + partialTicks;
    float cdx = (float)(exactPlayerX - cloudPlayerX + dc * 0.03D);
    float cdy = (float)(exactPlayerY - cloudPlayerY);
    float cdz = (float)(exactPlayerZ - cloudPlayerZ);
    GlStateManager.pushMatrix();
    
    if (renderFancy)
    {
      GlStateManager.translate(-cdx / 12.0F, -cdy, -cdz / 12.0F);
    }
    else
    {
      GlStateManager.translate(-cdx, -cdy, -cdz);
    }
    
    GlStateManager.callList(glListClouds);
    GlStateManager.popMatrix();
    GlStateManager.func_179117_G();
  }
  
  public void reset()
  {
    updated = false;
  }
}
