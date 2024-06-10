/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import org.apache.commons.lang3.Validate;
/*  4:   */ 
/*  5:   */ public class ResourceLocation
/*  6:   */ {
/*  7:   */   private final String resourceDomain;
/*  8:   */   private final String resourcePath;
/*  9:   */   private static final String __OBFID = "CL_00001082";
/* 10:   */   
/* 11:   */   public ResourceLocation(String par1Str, String par2Str)
/* 12:   */   {
/* 13:13 */     Validate.notNull(par2Str);
/* 14:15 */     if ((par1Str != null) && (par1Str.length() != 0)) {
/* 15:17 */       this.resourceDomain = par1Str;
/* 16:   */     } else {
/* 17:21 */       this.resourceDomain = "minecraft";
/* 18:   */     }
/* 19:24 */     this.resourcePath = par2Str;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ResourceLocation(String par1Str)
/* 23:   */   {
/* 24:29 */     String var2 = "minecraft";
/* 25:30 */     String var3 = par1Str;
/* 26:31 */     int var4 = par1Str.indexOf(':');
/* 27:33 */     if (var4 >= 0)
/* 28:   */     {
/* 29:35 */       var3 = par1Str.substring(var4 + 1, par1Str.length());
/* 30:37 */       if (var4 > 1) {
/* 31:39 */         var2 = par1Str.substring(0, var4);
/* 32:   */       }
/* 33:   */     }
/* 34:43 */     this.resourceDomain = var2.toLowerCase();
/* 35:44 */     this.resourcePath = var3;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getResourcePath()
/* 39:   */   {
/* 40:49 */     return this.resourcePath;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getResourceDomain()
/* 44:   */   {
/* 45:54 */     return this.resourceDomain;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:59 */     return this.resourceDomain + ":" + this.resourcePath;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean equals(Object par1Obj)
/* 54:   */   {
/* 55:64 */     if (this == par1Obj) {
/* 56:66 */       return true;
/* 57:   */     }
/* 58:68 */     if (!(par1Obj instanceof ResourceLocation)) {
/* 59:70 */       return false;
/* 60:   */     }
/* 61:74 */     ResourceLocation var2 = (ResourceLocation)par1Obj;
/* 62:75 */     return (this.resourceDomain.equals(var2.resourceDomain)) && (this.resourcePath.equals(var2.resourcePath));
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int hashCode()
/* 66:   */   {
/* 67:81 */     return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ResourceLocation
 * JD-Core Version:    0.7.0.1
 */