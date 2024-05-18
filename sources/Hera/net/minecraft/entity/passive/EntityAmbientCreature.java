/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityAmbientCreature
/*    */   extends EntityLiving
/*    */   implements IAnimals {
/*    */   public EntityAmbientCreature(World worldIn) {
/* 11 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allowLeashing() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean interact(EntityPlayer player) {
/* 24 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\passive\EntityAmbientCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */