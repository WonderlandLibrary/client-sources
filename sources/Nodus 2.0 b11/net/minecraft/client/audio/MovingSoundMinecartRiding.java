/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.item.EntityMinecart;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ 
/*  8:   */ public class MovingSoundMinecartRiding
/*  9:   */   extends MovingSound
/* 10:   */ {
/* 11:   */   private final EntityPlayer field_147672_k;
/* 12:   */   private final EntityMinecart field_147671_l;
/* 13:   */   private static final String __OBFID = "CL_00001119";
/* 14:   */   
/* 15:   */   public MovingSoundMinecartRiding(EntityPlayer p_i45106_1_, EntityMinecart p_i45106_2_)
/* 16:   */   {
/* 17:16 */     super(new ResourceLocation("minecraft:minecart.inside"));
/* 18:17 */     this.field_147672_k = p_i45106_1_;
/* 19:18 */     this.field_147671_l = p_i45106_2_;
/* 20:19 */     this.field_147666_i = ISound.AttenuationType.NONE;
/* 21:20 */     this.field_147659_g = true;
/* 22:21 */     this.field_147665_h = 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void update()
/* 26:   */   {
/* 27:29 */     if ((!this.field_147671_l.isDead) && (this.field_147672_k.isRiding()) && (this.field_147672_k.ridingEntity == this.field_147671_l))
/* 28:   */     {
/* 29:31 */       float var1 = MathHelper.sqrt_double(this.field_147671_l.motionX * this.field_147671_l.motionX + this.field_147671_l.motionZ * this.field_147671_l.motionZ);
/* 30:33 */       if (var1 >= 0.01D) {
/* 31:35 */         this.field_147662_b = (0.0F + MathHelper.clamp_float(var1, 0.0F, 1.0F) * 0.75F);
/* 32:   */       } else {
/* 33:39 */         this.field_147662_b = 0.0F;
/* 34:   */       }
/* 35:   */     }
/* 36:   */     else
/* 37:   */     {
/* 38:44 */       this.field_147668_j = true;
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.MovingSoundMinecartRiding
 * JD-Core Version:    0.7.0.1
 */