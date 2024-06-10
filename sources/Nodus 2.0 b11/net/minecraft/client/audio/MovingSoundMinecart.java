/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.item.EntityMinecart;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ import net.minecraft.util.ResourceLocation;
/*  6:   */ 
/*  7:   */ public class MovingSoundMinecart
/*  8:   */   extends MovingSound
/*  9:   */ {
/* 10:   */   private final EntityMinecart field_147670_k;
/* 11:10 */   private float field_147669_l = 0.0F;
/* 12:   */   private static final String __OBFID = "CL_00001118";
/* 13:   */   
/* 14:   */   public MovingSoundMinecart(EntityMinecart p_i45105_1_)
/* 15:   */   {
/* 16:15 */     super(new ResourceLocation("minecraft:minecart.base"));
/* 17:16 */     this.field_147670_k = p_i45105_1_;
/* 18:17 */     this.field_147659_g = true;
/* 19:18 */     this.field_147665_h = 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void update()
/* 23:   */   {
/* 24:26 */     if (this.field_147670_k.isDead)
/* 25:   */     {
/* 26:28 */       this.field_147668_j = true;
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:32 */       this.field_147660_d = ((float)this.field_147670_k.posX);
/* 31:33 */       this.field_147661_e = ((float)this.field_147670_k.posY);
/* 32:34 */       this.field_147658_f = ((float)this.field_147670_k.posZ);
/* 33:35 */       float var1 = MathHelper.sqrt_double(this.field_147670_k.motionX * this.field_147670_k.motionX + this.field_147670_k.motionZ * this.field_147670_k.motionZ);
/* 34:37 */       if (var1 >= 0.01D)
/* 35:   */       {
/* 36:39 */         this.field_147669_l = MathHelper.clamp_float(this.field_147669_l + 0.0025F, 0.0F, 1.0F);
/* 37:40 */         this.field_147662_b = (0.0F + MathHelper.clamp_float(var1, 0.0F, 0.5F) * 0.7F);
/* 38:   */       }
/* 39:   */       else
/* 40:   */       {
/* 41:44 */         this.field_147669_l = 0.0F;
/* 42:45 */         this.field_147662_b = 0.0F;
/* 43:   */       }
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.MovingSoundMinecart
 * JD-Core Version:    0.7.0.1
 */