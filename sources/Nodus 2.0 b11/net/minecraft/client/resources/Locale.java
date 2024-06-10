/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Splitter;
/*   4:    */ import com.google.common.collect.Iterables;
/*   5:    */ import com.google.common.collect.Maps;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.IllegalFormatException;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.regex.Matcher;
/*  15:    */ import java.util.regex.Pattern;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ import org.apache.commons.io.Charsets;
/*  18:    */ import org.apache.commons.io.IOUtils;
/*  19:    */ 
/*  20:    */ public class Locale
/*  21:    */ {
/*  22: 20 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  23: 21 */   private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  24: 22 */   Map field_135032_a = Maps.newHashMap();
/*  25:    */   private boolean field_135029_d;
/*  26:    */   private static final String __OBFID = "CL_00001097";
/*  27:    */   
/*  28:    */   public synchronized void loadLocaleDataFiles(IResourceManager par1ResourceManager, List par2List)
/*  29:    */   {
/*  30: 31 */     this.field_135032_a.clear();
/*  31: 32 */     Iterator var3 = par2List.iterator();
/*  32:    */     Iterator var6;
/*  33: 34 */     for (; var3.hasNext(); var6.hasNext())
/*  34:    */     {
/*  35: 36 */       String var4 = (String)var3.next();
/*  36: 37 */       String var5 = String.format("lang/%s.lang", new Object[] { var4 });
/*  37: 38 */       var6 = par1ResourceManager.getResourceDomains().iterator();
/*  38:    */       
/*  39: 40 */       continue;
/*  40:    */       
/*  41: 42 */       String var7 = (String)var6.next();
/*  42:    */       try
/*  43:    */       {
/*  44: 46 */         loadLocaleData(par1ResourceManager.getAllResources(new ResourceLocation(var7, var5)));
/*  45:    */       }
/*  46:    */       catch (IOException localIOException) {}
/*  47:    */     }
/*  48: 55 */     checkUnicode();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isUnicode()
/*  52:    */   {
/*  53: 60 */     return this.field_135029_d;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void checkUnicode()
/*  57:    */   {
/*  58: 65 */     this.field_135029_d = false;
/*  59: 66 */     int var1 = 0;
/*  60: 67 */     int var2 = 0;
/*  61: 68 */     Iterator var3 = this.field_135032_a.values().iterator();
/*  62:    */     int var5;
/*  63:    */     int var6;
/*  64: 70 */     for (; var3.hasNext(); var6 < var5)
/*  65:    */     {
/*  66: 72 */       String var4 = (String)var3.next();
/*  67: 73 */       var5 = var4.length();
/*  68: 74 */       var2 += var5;
/*  69:    */       
/*  70: 76 */       var6 = 0; continue;
/*  71: 78 */       if (var4.charAt(var6) >= 'Ā') {
/*  72: 80 */         var1++;
/*  73:    */       }
/*  74: 76 */       var6++;
/*  75:    */     }
/*  76: 85 */     float var7 = var1 / var2;
/*  77: 86 */     this.field_135029_d = (var7 > 0.1D);
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void loadLocaleData(List par1List)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83: 94 */     Iterator var2 = par1List.iterator();
/*  84: 96 */     while (var2.hasNext())
/*  85:    */     {
/*  86: 98 */       IResource var3 = (IResource)var2.next();
/*  87: 99 */       loadLocaleData(var3.getInputStream());
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void loadLocaleData(InputStream par1InputStream)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:105 */     Iterator var2 = IOUtils.readLines(par1InputStream, Charsets.UTF_8).iterator();
/*  95:107 */     while (var2.hasNext())
/*  96:    */     {
/*  97:109 */       String var3 = (String)var2.next();
/*  98:111 */       if ((!var3.isEmpty()) && (var3.charAt(0) != '#'))
/*  99:    */       {
/* 100:113 */         String[] var4 = (String[])Iterables.toArray(splitter.split(var3), String.class);
/* 101:115 */         if ((var4 != null) && (var4.length == 2))
/* 102:    */         {
/* 103:117 */           String var5 = var4[0];
/* 104:118 */           String var6 = field_135031_c.matcher(var4[1]).replaceAll("%$1s");
/* 105:119 */           this.field_135032_a.put(var5, var6);
/* 106:    */         }
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private String translateKeyPrivate(String par1Str)
/* 112:    */   {
/* 113:130 */     String var2 = (String)this.field_135032_a.get(par1Str);
/* 114:131 */     return var2 == null ? par1Str : var2;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String formatMessage(String par1Str, Object[] par2ArrayOfObj)
/* 118:    */   {
/* 119:139 */     String var3 = translateKeyPrivate(par1Str);
/* 120:    */     try
/* 121:    */     {
/* 122:143 */       return String.format(var3, par2ArrayOfObj);
/* 123:    */     }
/* 124:    */     catch (IllegalFormatException var5) {}
/* 125:147 */     return "Format error: " + var3;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.Locale
 * JD-Core Version:    0.7.0.1
 */