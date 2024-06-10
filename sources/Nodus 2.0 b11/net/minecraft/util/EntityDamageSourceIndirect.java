/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class EntityDamageSourceIndirect
/*  8:   */   extends EntityDamageSource
/*  9:   */ {
/* 10:   */   private Entity indirectEntity;
/* 11:   */   private static final String __OBFID = "CL_00001523";
/* 12:   */   
/* 13:   */   public EntityDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity)
/* 14:   */   {
/* 15:14 */     super(par1Str, par2Entity);
/* 16:15 */     this.indirectEntity = par3Entity;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Entity getSourceOfDamage()
/* 20:   */   {
/* 21:20 */     return this.damageSourceEntity;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Entity getEntity()
/* 25:   */   {
/* 26:25 */     return this.indirectEntity;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
/* 30:   */   {
/* 31:30 */     IChatComponent var2 = this.indirectEntity == null ? this.damageSourceEntity.func_145748_c_() : this.indirectEntity.func_145748_c_();
/* 32:31 */     ItemStack var3 = (this.indirectEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
/* 33:32 */     String var4 = "death.attack." + this.damageType;
/* 34:33 */     String var5 = var4 + ".item";
/* 35:34 */     return (var3 != null) && (var3.hasDisplayName()) && (StatCollector.canTranslate(var5)) ? new ChatComponentTranslation(var5, new Object[] { p_151519_1_.func_145748_c_(), var2, var3.func_151000_E() }) : new ChatComponentTranslation(var4, new Object[] { p_151519_1_.func_145748_c_(), var2 });
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EntityDamageSourceIndirect
 * JD-Core Version:    0.7.0.1
 */