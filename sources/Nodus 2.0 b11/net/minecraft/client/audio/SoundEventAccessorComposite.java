/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ 
/*  9:   */ public class SoundEventAccessorComposite
/* 10:   */   implements ISoundEventAccessor
/* 11:   */ {
/* 12:11 */   private final List field_148736_a = Lists.newArrayList();
/* 13:12 */   private final Random field_148734_b = new Random();
/* 14:   */   private final ResourceLocation field_148735_c;
/* 15:   */   private final SoundCategory field_148732_d;
/* 16:   */   private double field_148733_e;
/* 17:   */   private double field_148731_f;
/* 18:   */   private static final String __OBFID = "CL_00001146";
/* 19:   */   
/* 20:   */   public SoundEventAccessorComposite(ResourceLocation p_i45120_1_, double p_i45120_2_, double p_i45120_4_, SoundCategory p_i45120_6_)
/* 21:   */   {
/* 22:21 */     this.field_148735_c = p_i45120_1_;
/* 23:22 */     this.field_148731_f = p_i45120_4_;
/* 24:23 */     this.field_148733_e = p_i45120_2_;
/* 25:24 */     this.field_148732_d = p_i45120_6_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int func_148721_a()
/* 29:   */   {
/* 30:29 */     int var1 = 0;
/* 31:   */     ISoundEventAccessor var3;
/* 32:32 */     for (Iterator var2 = this.field_148736_a.iterator(); var2.hasNext(); var1 += var3.func_148721_a()) {
/* 33:34 */       var3 = (ISoundEventAccessor)var2.next();
/* 34:   */     }
/* 35:37 */     return var1;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public SoundPoolEntry func_148720_g()
/* 39:   */   {
/* 40:42 */     int var1 = func_148721_a();
/* 41:44 */     if ((!this.field_148736_a.isEmpty()) && (var1 != 0))
/* 42:   */     {
/* 43:46 */       int var2 = this.field_148734_b.nextInt(var1);
/* 44:47 */       Iterator var3 = this.field_148736_a.iterator();
/* 45:   */       ISoundEventAccessor var4;
/* 46:   */       do
/* 47:   */       {
/* 48:52 */         if (!var3.hasNext()) {
/* 49:54 */           return SoundHandler.field_147700_a;
/* 50:   */         }
/* 51:57 */         var4 = (ISoundEventAccessor)var3.next();
/* 52:58 */         var2 -= var4.func_148721_a();
/* 53:50 */       } while (
/* 54:   */       
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:   */ 
/* 63:60 */         var2 >= 0);
/* 64:62 */       SoundPoolEntry var5 = (SoundPoolEntry)var4.func_148720_g();
/* 65:63 */       var5.func_148651_a(var5.func_148650_b() * this.field_148733_e);
/* 66:64 */       var5.func_148647_b(var5.func_148649_c() * this.field_148731_f);
/* 67:65 */       return var5;
/* 68:   */     }
/* 69:69 */     return SoundHandler.field_147700_a;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void func_148727_a(ISoundEventAccessor p_148727_1_)
/* 73:   */   {
/* 74:75 */     this.field_148736_a.add(p_148727_1_);
/* 75:   */   }
/* 76:   */   
/* 77:   */   public ResourceLocation func_148729_c()
/* 78:   */   {
/* 79:80 */     return this.field_148735_c;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public SoundCategory func_148728_d()
/* 83:   */   {
/* 84:85 */     return this.field_148732_d;
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundEventAccessorComposite
 * JD-Core Version:    0.7.0.1
 */