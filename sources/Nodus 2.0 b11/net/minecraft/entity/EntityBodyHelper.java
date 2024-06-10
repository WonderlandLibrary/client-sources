/*  1:   */ package net.minecraft.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.MathHelper;
/*  4:   */ 
/*  5:   */ public class EntityBodyHelper
/*  6:   */ {
/*  7:   */   private EntityLivingBase theLiving;
/*  8:   */   private int field_75666_b;
/*  9:   */   private float field_75667_c;
/* 10:   */   private static final String __OBFID = "CL_00001570";
/* 11:   */   
/* 12:   */   public EntityBodyHelper(EntityLivingBase par1EntityLivingBase)
/* 13:   */   {
/* 14:15 */     this.theLiving = par1EntityLivingBase;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void func_75664_a()
/* 18:   */   {
/* 19:20 */     double var1 = this.theLiving.posX - this.theLiving.prevPosX;
/* 20:21 */     double var3 = this.theLiving.posZ - this.theLiving.prevPosZ;
/* 21:23 */     if (var1 * var1 + var3 * var3 > 2.500000277905201E-007D)
/* 22:   */     {
/* 23:25 */       this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
/* 24:26 */       this.theLiving.rotationYawHead = func_75665_a(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
/* 25:27 */       this.field_75667_c = this.theLiving.rotationYawHead;
/* 26:28 */       this.field_75666_b = 0;
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:32 */       float var5 = 75.0F;
/* 31:34 */       if (Math.abs(this.theLiving.rotationYawHead - this.field_75667_c) > 15.0F)
/* 32:   */       {
/* 33:36 */         this.field_75666_b = 0;
/* 34:37 */         this.field_75667_c = this.theLiving.rotationYawHead;
/* 35:   */       }
/* 36:   */       else
/* 37:   */       {
/* 38:41 */         this.field_75666_b += 1;
/* 39:42 */         boolean var6 = true;
/* 40:44 */         if (this.field_75666_b > 10) {
/* 41:46 */           var5 = Math.max(1.0F - (this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
/* 42:   */         }
/* 43:   */       }
/* 44:50 */       this.theLiving.renderYawOffset = func_75665_a(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, var5);
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   private float func_75665_a(float par1, float par2, float par3)
/* 49:   */   {
/* 50:56 */     float var4 = MathHelper.wrapAngleTo180_float(par1 - par2);
/* 51:58 */     if (var4 < -par3) {
/* 52:60 */       var4 = -par3;
/* 53:   */     }
/* 54:63 */     if (var4 >= par3) {
/* 55:65 */       var4 = par3;
/* 56:   */     }
/* 57:68 */     return par1 - var4;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityBodyHelper
 * JD-Core Version:    0.7.0.1
 */