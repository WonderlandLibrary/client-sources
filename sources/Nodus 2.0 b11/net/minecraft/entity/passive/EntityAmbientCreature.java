/*  1:   */ package net.minecraft.entity.passive;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public abstract class EntityAmbientCreature
/*  8:   */   extends EntityLiving
/*  9:   */   implements IAnimals
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001636";
/* 12:   */   
/* 13:   */   public EntityAmbientCreature(World par1World)
/* 14:   */   {
/* 15:13 */     super(par1World);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean allowLeashing()
/* 19:   */   {
/* 20:18 */     return false;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected boolean interact(EntityPlayer par1EntityPlayer)
/* 24:   */   {
/* 25:26 */     return false;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityAmbientCreature
 * JD-Core Version:    0.7.0.1
 */