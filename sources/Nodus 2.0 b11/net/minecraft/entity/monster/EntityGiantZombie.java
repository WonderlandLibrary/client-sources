/*  1:   */ package net.minecraft.entity.monster;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.SharedMonsterAttributes;
/*  4:   */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityGiantZombie
/*  8:   */   extends EntityMob
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00001690";
/* 11:   */   
/* 12:   */   public EntityGiantZombie(World par1World)
/* 13:   */   {
/* 14:12 */     super(par1World);
/* 15:13 */     this.yOffset *= 6.0F;
/* 16:14 */     setSize(this.width * 6.0F, this.height * 6.0F);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected void applyEntityAttributes()
/* 20:   */   {
/* 21:19 */     super.applyEntityAttributes();
/* 22:20 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
/* 23:21 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/* 24:22 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public float getBlockPathWeight(int par1, int par2, int par3)
/* 28:   */   {
/* 29:31 */     return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityGiantZombie
 * JD-Core Version:    0.7.0.1
 */