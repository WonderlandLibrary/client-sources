/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ 
/*  5:   */ public class LanguageMetadataSection
/*  6:   */   implements IMetadataSection
/*  7:   */ {
/*  8:   */   private final Collection languages;
/*  9:   */   private static final String __OBFID = "CL_00001110";
/* 10:   */   
/* 11:   */   public LanguageMetadataSection(Collection par1Collection)
/* 12:   */   {
/* 13:12 */     this.languages = par1Collection;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Collection getLanguages()
/* 17:   */   {
/* 18:17 */     return this.languages;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.LanguageMetadataSection
 * JD-Core Version:    0.7.0.1
 */