/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import net.minecraft.client.resources.AbstractResourcePack;
/*  5:   */ 
/*  6:   */ public class ResourceUtils
/*  7:   */ {
/*  8: 8 */   private static ReflectorClass ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
/*  9: 9 */   private static ReflectorField ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ForgeAbstractResourcePack, "resourcePackFile");
/* 10:10 */   private static boolean directAccessValid = true;
/* 11:   */   
/* 12:   */   public static File getResourcePackFile(AbstractResourcePack arp)
/* 13:   */   {
/* 14:14 */     if (directAccessValid) {
/* 15:   */       try
/* 16:   */       {
/* 17:18 */         return arp.resourcePackFile;
/* 18:   */       }
/* 19:   */       catch (IllegalAccessError var2)
/* 20:   */       {
/* 21:22 */         directAccessValid = false;
/* 22:24 */         if (!ForgeAbstractResourcePack_resourcePackFile.exists()) {
/* 23:26 */           throw var2;
/* 24:   */         }
/* 25:   */       }
/* 26:   */     }
/* 27:31 */     return (File)Reflector.getFieldValue(arp, ForgeAbstractResourcePack_resourcePackFile);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ResourceUtils
 * JD-Core Version:    0.7.0.1
 */