/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.io.BufferedInputStream;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.FileInputStream;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import java.util.HashSet;
/* 10:   */ import java.util.Set;
/* 11:   */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/* 12:   */ 
/* 13:   */ public class FolderResourcePack
/* 14:   */   extends AbstractResourcePack
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00001076";
/* 17:   */   
/* 18:   */   public FolderResourcePack(File par1File)
/* 19:   */   {
/* 20:19 */     super(par1File);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected InputStream getInputStreamByName(String par1Str)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:24 */     return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, par1Str)));
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected boolean hasResourceName(String par1Str)
/* 30:   */   {
/* 31:29 */     return new File(this.resourcePackFile, par1Str).isFile();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Set getResourceDomains()
/* 35:   */   {
/* 36:34 */     HashSet var1 = Sets.newHashSet();
/* 37:35 */     File var2 = new File(this.resourcePackFile, "assets/");
/* 38:37 */     if (var2.isDirectory())
/* 39:   */     {
/* 40:39 */       File[] var3 = var2.listFiles(DirectoryFileFilter.DIRECTORY);
/* 41:40 */       int var4 = var3.length;
/* 42:42 */       for (int var5 = 0; var5 < var4; var5++)
/* 43:   */       {
/* 44:44 */         File var6 = var3[var5];
/* 45:45 */         String var7 = getRelativeName(var2, var6);
/* 46:47 */         if (!var7.equals(var7.toLowerCase())) {
/* 47:49 */           logNameNotLowercase(var7);
/* 48:   */         } else {
/* 49:53 */           var1.add(var7.substring(0, var7.length() - 1));
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:58 */     return var1;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.FolderResourcePack
 * JD-Core Version:    0.7.0.1
 */