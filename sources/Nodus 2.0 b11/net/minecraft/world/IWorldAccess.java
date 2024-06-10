package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public abstract interface IWorldAccess
{
  public abstract void markBlockForUpdate(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void markBlockForRenderUpdate(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void markBlockRangeForRenderUpdate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public abstract void playSound(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
  
  public abstract void playSoundToNearExcept(EntityPlayer paramEntityPlayer, String paramString, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
  
  public abstract void spawnParticle(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
  
  public abstract void onEntityCreate(Entity paramEntity);
  
  public abstract void onEntityDestroy(Entity paramEntity);
  
  public abstract void playRecord(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void broadcastSound(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void playAuxSFX(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void destroyBlockPartially(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void onStaticEntitiesChanged();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.IWorldAccess
 * JD-Core Version:    0.7.0.1
 */