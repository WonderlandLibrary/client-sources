/*  1:   */ package net.minecraft.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.entity.monster.IMob;
/*  5:   */ import net.minecraft.entity.passive.EntityAmbientCreature;
/*  6:   */ import net.minecraft.entity.passive.EntityAnimal;
/*  7:   */ import net.minecraft.entity.passive.EntityWaterMob;
/*  8:   */ 
/*  9:   */ public enum EnumCreatureType
/* 10:   */ {
/* 11:11 */   monster(IMob.class, 70, Material.air, false, false),  creature(EntityAnimal.class, 10, Material.air, true, true),  ambient(EntityAmbientCreature.class, 15, Material.air, true, false),  waterCreature(EntityWaterMob.class, 5, Material.water, true, false);
/* 12:   */   
/* 13:   */   private final Class creatureClass;
/* 14:   */   private final int maxNumberOfCreature;
/* 15:   */   private final Material creatureMaterial;
/* 16:   */   private final boolean isPeacefulCreature;
/* 17:   */   private final boolean isAnimal;
/* 18:   */   private static final String __OBFID = "CL_00001551";
/* 19:   */   
/* 20:   */   private EnumCreatureType(Class par3Class, int par4, Material par5Material, boolean par6, boolean par7)
/* 21:   */   {
/* 22:33 */     this.creatureClass = par3Class;
/* 23:34 */     this.maxNumberOfCreature = par4;
/* 24:35 */     this.creatureMaterial = par5Material;
/* 25:36 */     this.isPeacefulCreature = par6;
/* 26:37 */     this.isAnimal = par7;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Class getCreatureClass()
/* 30:   */   {
/* 31:42 */     return this.creatureClass;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getMaxNumberOfCreature()
/* 35:   */   {
/* 36:47 */     return this.maxNumberOfCreature;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Material getCreatureMaterial()
/* 40:   */   {
/* 41:52 */     return this.creatureMaterial;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean getPeacefulCreature()
/* 45:   */   {
/* 46:60 */     return this.isPeacefulCreature;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean getAnimal()
/* 50:   */   {
/* 51:68 */     return this.isAnimal;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EnumCreatureType
 * JD-Core Version:    0.7.0.1
 */