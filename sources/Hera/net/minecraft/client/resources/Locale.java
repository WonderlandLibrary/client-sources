/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public class Locale
/*     */ {
/*  19 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  20 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  21 */   Map<String, String> properties = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private boolean unicode;
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void loadLocaleDataFiles(IResourceManager resourceManager, List<String> p_135022_2_) {
/*  29 */     this.properties.clear();
/*     */     
/*  31 */     for (String s : p_135022_2_) {
/*     */       
/*  33 */       String s1 = String.format("lang/%s.lang", new Object[] { s });
/*     */       
/*  35 */       for (String s2 : resourceManager.getResourceDomains()) {
/*     */ 
/*     */         
/*     */         try {
/*  39 */           loadLocaleData(resourceManager.getAllResources(new ResourceLocation(s2, s1)));
/*     */         }
/*  41 */         catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     checkUnicode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnicode() {
/*  53 */     return this.unicode;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkUnicode() {
/*  58 */     this.unicode = false;
/*  59 */     int i = 0;
/*  60 */     int j = 0;
/*     */     
/*  62 */     for (String s : this.properties.values()) {
/*     */       
/*  64 */       int k = s.length();
/*  65 */       j += k;
/*     */       
/*  67 */       for (int l = 0; l < k; l++) {
/*     */         
/*  69 */         if (s.charAt(l) >= 'Ā')
/*     */         {
/*  71 */           i++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     float f = i / j;
/*  77 */     this.unicode = (f > 0.1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadLocaleData(List<IResource> p_135028_1_) throws IOException {
/*  85 */     for (IResource iresource : p_135028_1_) {
/*     */       
/*  87 */       InputStream inputstream = iresource.getInputStream();
/*     */ 
/*     */       
/*     */       try {
/*  91 */         loadLocaleData(inputstream);
/*     */       }
/*     */       finally {
/*     */         
/*  95 */         IOUtils.closeQuietly(inputstream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLocaleData(InputStream p_135021_1_) throws IOException {
/* 102 */     for (String s : IOUtils.readLines(p_135021_1_, Charsets.UTF_8)) {
/*     */       
/* 104 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */         
/* 106 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/* 108 */         if (astring != null && astring.length == 2) {
/*     */           
/* 110 */           String s1 = astring[0];
/* 111 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/* 112 */           this.properties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translateKeyPrivate(String p_135026_1_) {
/* 123 */     String s = this.properties.get(p_135026_1_);
/* 124 */     return (s == null) ? p_135026_1_ : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatMessage(String translateKey, Object[] parameters) {
/* 132 */     String s = translateKeyPrivate(translateKey);
/*     */ 
/*     */     
/*     */     try {
/* 136 */       return String.format(s, parameters);
/*     */     }
/* 138 */     catch (IllegalFormatException var5) {
/*     */       
/* 140 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\Locale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */