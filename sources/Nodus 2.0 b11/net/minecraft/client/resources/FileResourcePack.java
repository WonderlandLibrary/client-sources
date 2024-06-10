/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Splitter;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import com.google.common.collect.Sets;
/*   6:    */ import java.io.Closeable;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.Enumeration;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.zip.ZipEntry;
/*  16:    */ import java.util.zip.ZipFile;
/*  17:    */ 
/*  18:    */ public class FileResourcePack
/*  19:    */   extends AbstractResourcePack
/*  20:    */   implements Closeable
/*  21:    */ {
/*  22: 20 */   public static final Splitter entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
/*  23:    */   private ZipFile resourcePackZipFile;
/*  24:    */   private static final String __OBFID = "CL_00001075";
/*  25:    */   
/*  26:    */   public FileResourcePack(File par1File)
/*  27:    */   {
/*  28: 26 */     super(par1File);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private ZipFile getResourcePackZipFile()
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 31 */     if (this.resourcePackZipFile == null) {
/*  35: 33 */       this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
/*  36:    */     }
/*  37: 36 */     return this.resourcePackZipFile;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected InputStream getInputStreamByName(String par1Str)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 41 */     ZipFile var2 = getResourcePackZipFile();
/*  44: 42 */     ZipEntry var3 = var2.getEntry(par1Str);
/*  45: 44 */     if (var3 == null) {
/*  46: 46 */       throw new ResourcePackFileNotFoundException(this.resourcePackFile, par1Str);
/*  47:    */     }
/*  48: 50 */     return var2.getInputStream(var3);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean hasResourceName(String par1Str)
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 58 */       return getResourcePackZipFile().getEntry(par1Str) != null;
/*  56:    */     }
/*  57:    */     catch (IOException var3) {}
/*  58: 62 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Set getResourceDomains()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 72 */       var1 = getResourcePackZipFile();
/*  66:    */     }
/*  67:    */     catch (IOException var8)
/*  68:    */     {
/*  69:    */       ZipFile var1;
/*  70: 76 */       return Collections.emptySet();
/*  71:    */     }
/*  72:    */     ZipFile var1;
/*  73: 79 */     Enumeration var2 = var1.entries();
/*  74: 80 */     HashSet var3 = Sets.newHashSet();
/*  75: 82 */     while (var2.hasMoreElements())
/*  76:    */     {
/*  77: 84 */       ZipEntry var4 = (ZipEntry)var2.nextElement();
/*  78: 85 */       String var5 = var4.getName();
/*  79: 87 */       if (var5.startsWith("assets/"))
/*  80:    */       {
/*  81: 89 */         ArrayList var6 = Lists.newArrayList(entryNameSplitter.split(var5));
/*  82: 91 */         if (var6.size() > 1)
/*  83:    */         {
/*  84: 93 */           String var7 = (String)var6.get(1);
/*  85: 95 */           if (!var7.equals(var7.toLowerCase())) {
/*  86: 97 */             logNameNotLowercase(var7);
/*  87:    */           } else {
/*  88:101 */             var3.add(var7);
/*  89:    */           }
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:107 */     return var3;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void finalize()
/*  97:    */     throws Throwable
/*  98:    */   {
/*  99:112 */     close();
/* 100:113 */     super.finalize();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void close()
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:118 */     if (this.resourcePackZipFile != null)
/* 107:    */     {
/* 108:120 */       this.resourcePackZipFile.close();
/* 109:121 */       this.resourcePackZipFile = null;
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.FileResourcePack
 * JD-Core Version:    0.7.0.1
 */