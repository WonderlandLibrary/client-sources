/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class MetadataPlayerDeath
/*    */   extends Metadata
/*    */ {
/*    */   public MetadataPlayerDeath(EntityLivingBase p_i46066_1_, EntityLivingBase p_i46066_2_) {
/*  9 */     super("player_death");
/*    */     
/* 11 */     if (p_i46066_1_ != null)
/*    */     {
/* 13 */       func_152808_a("player", p_i46066_1_.getName());
/*    */     }
/*    */     
/* 16 */     if (p_i46066_2_ != null)
/*    */     {
/* 18 */       func_152808_a("killer", p_i46066_2_.getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\stream\MetadataPlayerDeath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */