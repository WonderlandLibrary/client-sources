/*  1:   */ package net.minecraft.entity.boss;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.IEntityMultiPart;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ import net.minecraft.util.DamageSource;
/*  7:   */ 
/*  8:   */ public class EntityDragonPart
/*  9:   */   extends Entity
/* 10:   */ {
/* 11:   */   public final IEntityMultiPart entityDragonObj;
/* 12:   */   public final String field_146032_b;
/* 13:   */   private static final String __OBFID = "CL_00001657";
/* 14:   */   
/* 15:   */   public EntityDragonPart(IEntityMultiPart par1IEntityMultiPart, String par2Str, float par3, float par4)
/* 16:   */   {
/* 17:17 */     super(par1IEntityMultiPart.func_82194_d());
/* 18:18 */     setSize(par3, par4);
/* 19:19 */     this.entityDragonObj = par1IEntityMultiPart;
/* 20:20 */     this.field_146032_b = par2Str;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void entityInit() {}
/* 24:   */   
/* 25:   */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/* 26:   */   
/* 27:   */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/* 28:   */   
/* 29:   */   public boolean canBeCollidedWith()
/* 30:   */   {
/* 31:40 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 35:   */   {
/* 36:48 */     return isEntityInvulnerable() ? false : this.entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean isEntityEqual(Entity par1Entity)
/* 40:   */   {
/* 41:56 */     return (this == par1Entity) || (this.entityDragonObj == par1Entity);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.boss.EntityDragonPart
 * JD-Core Version:    0.7.0.1
 */