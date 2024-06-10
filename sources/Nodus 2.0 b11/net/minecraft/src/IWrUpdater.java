package net.minecraft.src;

import java.util.List;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract interface IWrUpdater
{
  public abstract void initialize();
  
  public abstract WorldRenderer makeWorldRenderer(World paramWorld, List paramList, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void preRender(RenderGlobal paramRenderGlobal, EntityLivingBase paramEntityLivingBase);
  
  public abstract void postRender();
  
  public abstract boolean updateRenderers(RenderGlobal paramRenderGlobal, EntityLivingBase paramEntityLivingBase, boolean paramBoolean);
  
  public abstract void resumeBackgroundUpdates();
  
  public abstract void pauseBackgroundUpdates();
  
  public abstract void finishCurrentUpdate();
  
  public abstract void clearAllUpdates();
  
  public abstract void terminate();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.IWrUpdater
 * JD-Core Version:    0.7.0.1
 */