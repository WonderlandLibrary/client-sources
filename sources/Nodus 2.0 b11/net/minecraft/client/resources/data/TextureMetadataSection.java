/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public class TextureMetadataSection
/*  7:   */   implements IMetadataSection
/*  8:   */ {
/*  9:   */   private final boolean textureBlur;
/* 10:   */   private final boolean textureClamp;
/* 11:   */   private final List field_148536_c;
/* 12:   */   private static final String __OBFID = "CL_00001114";
/* 13:   */   
/* 14:   */   public TextureMetadataSection(boolean p_i45102_1_, boolean p_i45102_2_, List p_i45102_3_)
/* 15:   */   {
/* 16:15 */     this.textureBlur = p_i45102_1_;
/* 17:16 */     this.textureClamp = p_i45102_2_;
/* 18:17 */     this.field_148536_c = p_i45102_3_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean getTextureBlur()
/* 22:   */   {
/* 23:22 */     return this.textureBlur;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean getTextureClamp()
/* 27:   */   {
/* 28:27 */     return this.textureClamp;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public List func_148535_c()
/* 32:   */   {
/* 33:32 */     return Collections.unmodifiableList(this.field_148536_c);
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.TextureMetadataSection
 * JD-Core Version:    0.7.0.1
 */