/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.texture.Stitcher.Holder;
/*  4:   */ 
/*  5:   */ public class StitcherException
/*  6:   */   extends RuntimeException
/*  7:   */ {
/*  8:   */   private final Stitcher.Holder field_98149_a;
/*  9:   */   private static final String __OBFID = "CL_00001057";
/* 10:   */   
/* 11:   */   public StitcherException(Stitcher.Holder par1StitchHolder, String par2Str)
/* 12:   */   {
/* 13:12 */     super(par2Str);
/* 14:13 */     this.field_98149_a = par1StitchHolder;
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.StitcherException
 * JD-Core Version:    0.7.0.1
 */