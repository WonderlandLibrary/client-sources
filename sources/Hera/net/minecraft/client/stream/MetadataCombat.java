/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class MetadataCombat
/*    */   extends Metadata
/*    */ {
/*    */   public MetadataCombat(EntityLivingBase p_i46067_1_, EntityLivingBase p_i46067_2_) {
/*  9 */     super("player_combat");
/* 10 */     func_152808_a("player", p_i46067_1_.getName());
/*    */     
/* 12 */     if (p_i46067_2_ != null)
/*    */     {
/* 14 */       func_152808_a("primary_opponent", p_i46067_2_.getName());
/*    */     }
/*    */     
/* 17 */     if (p_i46067_2_ != null) {
/*    */       
/* 19 */       func_152807_a("Combat between " + p_i46067_1_.getName() + " and " + p_i46067_2_.getName());
/*    */     }
/*    */     else {
/*    */       
/* 23 */       func_152807_a("Combat between " + p_i46067_1_.getName() + " and others");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\stream\MetadataCombat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */