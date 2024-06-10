/*   1:    */ package org.newdawn.slick.util;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ 
/*   9:    */ public class ResourceLoader
/*  10:    */ {
/*  11: 17 */   private static ArrayList locations = new ArrayList();
/*  12:    */   
/*  13:    */   static
/*  14:    */   {
/*  15: 20 */     locations.add(new ClasspathLocation());
/*  16: 21 */     locations.add(new FileSystemLocation(new File(".")));
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void addResourceLocation(ResourceLocation location)
/*  20:    */   {
/*  21: 30 */     locations.add(location);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static void removeResourceLocation(ResourceLocation location)
/*  25:    */   {
/*  26: 39 */     locations.remove(location);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void removeAllResourceLocations()
/*  30:    */   {
/*  31: 47 */     locations.clear();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static InputStream getResourceAsStream(String ref)
/*  35:    */   {
/*  36: 57 */     InputStream in = null;
/*  37: 59 */     for (int i = 0; i < locations.size(); i++)
/*  38:    */     {
/*  39: 60 */       ResourceLocation location = (ResourceLocation)locations.get(i);
/*  40: 61 */       in = location.getResourceAsStream(ref);
/*  41: 62 */       if (in != null) {
/*  42:    */         break;
/*  43:    */       }
/*  44:    */     }
/*  45: 67 */     if (in == null) {
/*  46: 69 */       throw new RuntimeException("Resource not found: " + ref);
/*  47:    */     }
/*  48: 72 */     return new BufferedInputStream(in);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static boolean resourceExists(String ref)
/*  52:    */   {
/*  53: 82 */     URL url = null;
/*  54: 84 */     for (int i = 0; i < locations.size(); i++)
/*  55:    */     {
/*  56: 85 */       ResourceLocation location = (ResourceLocation)locations.get(i);
/*  57: 86 */       url = location.getResource(ref);
/*  58: 87 */       if (url != null) {
/*  59: 88 */         return true;
/*  60:    */       }
/*  61:    */     }
/*  62: 92 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static URL getResource(String ref)
/*  66:    */   {
/*  67:103 */     URL url = null;
/*  68:105 */     for (int i = 0; i < locations.size(); i++)
/*  69:    */     {
/*  70:106 */       ResourceLocation location = (ResourceLocation)locations.get(i);
/*  71:107 */       url = location.getResource(ref);
/*  72:108 */       if (url != null) {
/*  73:    */         break;
/*  74:    */       }
/*  75:    */     }
/*  76:113 */     if (url == null) {
/*  77:115 */       throw new RuntimeException("Resource not found: " + ref);
/*  78:    */     }
/*  79:118 */     return url;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.ResourceLoader
 * JD-Core Version:    0.7.0.1
 */