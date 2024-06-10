/*  1:   */ package net.minecraft.entity.monster;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.IEntityLivingData;
/*  6:   */ import net.minecraft.entity.SharedMonsterAttributes;
/*  7:   */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  8:   */ import net.minecraft.potion.Potion;
/*  9:   */ import net.minecraft.potion.PotionEffect;
/* 10:   */ import net.minecraft.world.EnumDifficulty;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class EntityCaveSpider
/* 14:   */   extends EntitySpider
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00001683";
/* 17:   */   
/* 18:   */   public EntityCaveSpider(World par1World)
/* 19:   */   {
/* 20:18 */     super(par1World);
/* 21:19 */     setSize(0.7F, 0.5F);
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void applyEntityAttributes()
/* 25:   */   {
/* 26:24 */     super.applyEntityAttributes();
/* 27:25 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean attackEntityAsMob(Entity par1Entity)
/* 31:   */   {
/* 32:30 */     if (super.attackEntityAsMob(par1Entity))
/* 33:   */     {
/* 34:32 */       if ((par1Entity instanceof EntityLivingBase))
/* 35:   */       {
/* 36:34 */         byte var2 = 0;
/* 37:36 */         if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
/* 38:38 */           var2 = 7;
/* 39:40 */         } else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
/* 40:42 */           var2 = 15;
/* 41:   */         }
/* 42:45 */         if (var2 > 0) {
/* 43:47 */           ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
/* 44:   */         }
/* 45:   */       }
/* 46:51 */       return true;
/* 47:   */     }
/* 48:55 */     return false;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 52:   */   {
/* 53:61 */     return par1EntityLivingData;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityCaveSpider
 * JD-Core Version:    0.7.0.1
 */