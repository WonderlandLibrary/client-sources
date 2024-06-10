/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.entity.EntityLivingBase;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class BlockPressurePlate
/* 12:   */   extends BlockBasePressurePlate
/* 13:   */ {
/* 14:   */   private Sensitivity field_150069_a;
/* 15:   */   private static final String __OBFID = "CL_00000289";
/* 16:   */   
/* 17:   */   protected BlockPressurePlate(String p_i45418_1_, Material p_i45418_2_, Sensitivity p_i45418_3_)
/* 18:   */   {
/* 19:18 */     super(p_i45418_1_, p_i45418_2_);
/* 20:19 */     this.field_150069_a = p_i45418_3_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected int func_150066_d(int p_150066_1_)
/* 24:   */   {
/* 25:24 */     return p_150066_1_ > 0 ? 1 : 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected int func_150060_c(int p_150060_1_)
/* 29:   */   {
/* 30:29 */     return p_150060_1_ == 1 ? 15 : 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected int func_150065_e(World p_150065_1_, int p_150065_2_, int p_150065_3_, int p_150065_4_)
/* 34:   */   {
/* 35:34 */     List var5 = null;
/* 36:36 */     if (this.field_150069_a == Sensitivity.everything) {
/* 37:38 */       var5 = p_150065_1_.getEntitiesWithinAABBExcludingEntity(null, func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
/* 38:   */     }
/* 39:41 */     if (this.field_150069_a == Sensitivity.mobs) {
/* 40:43 */       var5 = p_150065_1_.getEntitiesWithinAABB(EntityLivingBase.class, func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
/* 41:   */     }
/* 42:46 */     if (this.field_150069_a == Sensitivity.players) {
/* 43:48 */       var5 = p_150065_1_.getEntitiesWithinAABB(EntityPlayer.class, func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
/* 44:   */     }
/* 45:51 */     if ((var5 != null) && (!var5.isEmpty()))
/* 46:   */     {
/* 47:53 */       Iterator var6 = var5.iterator();
/* 48:55 */       while (var6.hasNext())
/* 49:   */       {
/* 50:57 */         Entity var7 = (Entity)var6.next();
/* 51:59 */         if (!var7.doesEntityNotTriggerPressurePlate()) {
/* 52:61 */           return 15;
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:66 */     return 0;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static enum Sensitivity
/* 60:   */   {
/* 61:71 */     everything("everything", 0),  mobs("mobs", 1),  players("players", 2);
/* 62:   */     
/* 63:75 */     private static final Sensitivity[] $VALUES = { everything, mobs, players };
/* 64:   */     private static final String __OBFID = "CL_00000290";
/* 65:   */     
/* 66:   */     private Sensitivity(String p_i45417_1_, int p_i45417_2_) {}
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPressurePlate
 * JD-Core Version:    0.7.0.1
 */