/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.net.URL;
/*  5:   */ 
/*  6:   */ public class ClasspathLocation
/*  7:   */   implements ResourceLocation
/*  8:   */ {
/*  9:   */   public URL getResource(String ref)
/* 10:   */   {
/* 11:16 */     String cpRef = ref.replace('\\', '/');
/* 12:17 */     return ResourceLoader.class.getClassLoader().getResource(cpRef);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public InputStream getResourceAsStream(String ref)
/* 16:   */   {
/* 17:24 */     String cpRef = ref.replace('\\', '/');
/* 18:25 */     return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.ClasspathLocation
 * JD-Core Version:    0.7.0.1
 */