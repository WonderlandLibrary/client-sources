/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Splitter;
/*   4:    */ import com.google.common.collect.Iterables;
/*   5:    */ import com.google.common.collect.Maps;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.util.IllegalFormatException;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.regex.Matcher;
/*  13:    */ import java.util.regex.Pattern;
/*  14:    */ import org.apache.commons.io.Charsets;
/*  15:    */ import org.apache.commons.io.IOUtils;
/*  16:    */ 
/*  17:    */ public class StringTranslate
/*  18:    */ {
/*  19: 20 */   private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  20: 25 */   private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
/*  21: 28 */   private static StringTranslate instance = new StringTranslate();
/*  22: 29 */   private final Map languageList = Maps.newHashMap();
/*  23:    */   private long lastUpdateTimeInMilliseconds;
/*  24:    */   private static final String __OBFID = "CL_00001212";
/*  25:    */   
/*  26:    */   public StringTranslate()
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 41 */       InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
/*  31: 42 */       Iterator var2 = IOUtils.readLines(var1, Charsets.UTF_8).iterator();
/*  32: 44 */       while (var2.hasNext())
/*  33:    */       {
/*  34: 46 */         String var3 = (String)var2.next();
/*  35: 48 */         if ((!var3.isEmpty()) && (var3.charAt(0) != '#'))
/*  36:    */         {
/*  37: 50 */           String[] var4 = (String[])Iterables.toArray(equalSignSplitter.split(var3), String.class);
/*  38: 52 */           if ((var4 != null) && (var4.length == 2))
/*  39:    */           {
/*  40: 54 */             String var5 = var4[0];
/*  41: 55 */             String var6 = numericVariablePattern.matcher(var4[1]).replaceAll("%$1s");
/*  42: 56 */             this.languageList.put(var5, var6);
/*  43:    */           }
/*  44:    */         }
/*  45:    */       }
/*  46: 61 */       this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*  47:    */     }
/*  48:    */     catch (IOException localIOException) {}
/*  49:    */   }
/*  50:    */   
/*  51:    */   static StringTranslate getInstance()
/*  52:    */   {
/*  53: 74 */     return instance;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static synchronized void replaceWith(Map par0Map)
/*  57:    */   {
/*  58: 82 */     instance.languageList.clear();
/*  59: 83 */     instance.languageList.putAll(par0Map);
/*  60: 84 */     instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized String translateKey(String par1Str)
/*  64:    */   {
/*  65: 92 */     return tryTranslateKey(par1Str);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public synchronized String translateKeyFormat(String par1Str, Object... par2ArrayOfObj)
/*  69:    */   {
/*  70:100 */     String var3 = tryTranslateKey(par1Str);
/*  71:    */     try
/*  72:    */     {
/*  73:104 */       return String.format(var3, par2ArrayOfObj);
/*  74:    */     }
/*  75:    */     catch (IllegalFormatException var5) {}
/*  76:108 */     return "Format error: " + var3;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private String tryTranslateKey(String par1Str)
/*  80:    */   {
/*  81:117 */     String var2 = (String)this.languageList.get(par1Str);
/*  82:118 */     return var2 == null ? par1Str : var2;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public synchronized boolean containsTranslateKey(String par1Str)
/*  86:    */   {
/*  87:123 */     return this.languageList.containsKey(par1Str);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public long getLastUpdateTimeInMilliseconds()
/*  91:    */   {
/*  92:131 */     return this.lastUpdateTimeInMilliseconds;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.StringTranslate
 * JD-Core Version:    0.7.0.1
 */