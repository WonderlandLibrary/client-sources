/*    */ package optfine;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.client.resources.AbstractResourcePack;
/*    */ 
/*    */ public class ResourceUtils
/*    */ {
/*  8 */   private static ReflectorClass ForgeAbstractResourcePack = new ReflectorClass(AbstractResourcePack.class);
/*  9 */   private static ReflectorField ForgeAbstractResourcePack_resourcePackFile = new ReflectorField(ForgeAbstractResourcePack, "resourcePackFile");
/*    */   
/*    */   private static boolean directAccessValid = true;
/*    */   
/*    */   public static File getResourcePackFile(AbstractResourcePack p_getResourcePackFile_0_) {
/* 14 */     if (directAccessValid) {
/*    */       
/*    */       try {
/*    */         
/* 18 */         return p_getResourcePackFile_0_.resourcePackFile;
/*    */       }
/* 20 */       catch (IllegalAccessError illegalaccesserror) {
/*    */         
/* 22 */         directAccessValid = false;
/*    */         
/* 24 */         if (!ForgeAbstractResourcePack_resourcePackFile.exists())
/*    */         {
/* 26 */           throw illegalaccesserror;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 31 */     return (File)Reflector.getFieldValue(p_getResourcePackFile_0_, ForgeAbstractResourcePack_resourcePackFile);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\ResourceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */