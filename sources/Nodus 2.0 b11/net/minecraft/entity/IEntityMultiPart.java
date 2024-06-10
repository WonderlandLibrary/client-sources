package net.minecraft.entity;

import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract interface IEntityMultiPart
{
  public abstract World func_82194_d();
  
  public abstract boolean attackEntityFromPart(EntityDragonPart paramEntityDragonPart, DamageSource paramDamageSource, float paramFloat);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.IEntityMultiPart
 * JD-Core Version:    0.7.0.1
 */