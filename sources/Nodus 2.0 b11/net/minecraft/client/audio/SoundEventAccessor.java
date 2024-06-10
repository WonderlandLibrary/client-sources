/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ public class SoundEventAccessor
/*  4:   */   implements ISoundEventAccessor
/*  5:   */ {
/*  6:   */   private final SoundPoolEntry field_148739_a;
/*  7:   */   private final int field_148738_b;
/*  8:   */   private static final String __OBFID = "CL_00001153";
/*  9:   */   
/* 10:   */   SoundEventAccessor(SoundPoolEntry p_i45123_1_, int p_i45123_2_)
/* 11:   */   {
/* 12:11 */     this.field_148739_a = p_i45123_1_;
/* 13:12 */     this.field_148738_b = p_i45123_2_;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int func_148721_a()
/* 17:   */   {
/* 18:17 */     return this.field_148738_b;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SoundPoolEntry func_148720_g()
/* 22:   */   {
/* 23:22 */     return new SoundPoolEntry(this.field_148739_a);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundEventAccessor
 * JD-Core Version:    0.7.0.1
 */