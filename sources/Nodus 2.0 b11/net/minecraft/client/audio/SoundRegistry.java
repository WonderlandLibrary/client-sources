/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Maps;
/*  4:   */ import java.util.Map;
/*  5:   */ import net.minecraft.util.RegistrySimple;
/*  6:   */ 
/*  7:   */ public class SoundRegistry
/*  8:   */   extends RegistrySimple
/*  9:   */ {
/* 10:   */   private Map field_148764_a;
/* 11:   */   private static final String __OBFID = "CL_00001151";
/* 12:   */   
/* 13:   */   protected Map createUnderlyingMap()
/* 14:   */   {
/* 15:17 */     this.field_148764_a = Maps.newHashMap();
/* 16:18 */     return this.field_148764_a;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void func_148762_a(SoundEventAccessorComposite p_148762_1_)
/* 20:   */   {
/* 21:23 */     putObject(p_148762_1_.func_148729_c(), p_148762_1_);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void func_148763_c()
/* 25:   */   {
/* 26:28 */     this.field_148764_a.clear();
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundRegistry
 * JD-Core Version:    0.7.0.1
 */