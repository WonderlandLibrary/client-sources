/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import com.google.common.collect.Sets;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.SortedSet;
/*  13:    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*  14:    */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*  15:    */ import net.minecraft.util.StringTranslate;
/*  16:    */ import org.apache.logging.log4j.LogManager;
/*  17:    */ import org.apache.logging.log4j.Logger;
/*  18:    */ 
/*  19:    */ public class LanguageManager
/*  20:    */   implements IResourceManagerReloadListener
/*  21:    */ {
/*  22: 20 */   private static final Logger logger = ;
/*  23:    */   private final IMetadataSerializer theMetadataSerializer;
/*  24:    */   private String currentLanguage;
/*  25: 23 */   protected static final Locale currentLocale = new Locale();
/*  26: 24 */   private Map languageMap = Maps.newHashMap();
/*  27:    */   private static final String __OBFID = "CL_00001096";
/*  28:    */   
/*  29:    */   public LanguageManager(IMetadataSerializer par1MetadataSerializer, String par2Str)
/*  30:    */   {
/*  31: 29 */     this.theMetadataSerializer = par1MetadataSerializer;
/*  32: 30 */     this.currentLanguage = par2Str;
/*  33: 31 */     I18n.setLocale(currentLocale);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void parseLanguageMetadata(List par1List)
/*  37:    */   {
/*  38: 36 */     this.languageMap.clear();
/*  39: 37 */     Iterator var2 = par1List.iterator();
/*  40: 39 */     while (var2.hasNext())
/*  41:    */     {
/*  42: 41 */       IResourcePack var3 = (IResourcePack)var2.next();
/*  43:    */       try
/*  44:    */       {
/*  45: 45 */         LanguageMetadataSection var4 = (LanguageMetadataSection)var3.getPackMetadata(this.theMetadataSerializer, "language");
/*  46: 47 */         if (var4 != null)
/*  47:    */         {
/*  48: 49 */           Iterator var5 = var4.getLanguages().iterator();
/*  49: 51 */           while (var5.hasNext())
/*  50:    */           {
/*  51: 53 */             Language var6 = (Language)var5.next();
/*  52: 55 */             if (!this.languageMap.containsKey(var6.getLanguageCode())) {
/*  53: 57 */               this.languageMap.put(var6.getLanguageCode(), var6);
/*  54:    */             }
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:    */       catch (RuntimeException var7)
/*  59:    */       {
/*  60: 64 */         logger.warn("Unable to parse metadata section of resourcepack: " + var3.getPackName(), var7);
/*  61:    */       }
/*  62:    */       catch (IOException var8)
/*  63:    */       {
/*  64: 68 */         logger.warn("Unable to parse metadata section of resourcepack: " + var3.getPackName(), var8);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/*  70:    */   {
/*  71: 75 */     ArrayList var2 = Lists.newArrayList(new String[] { "en_US" });
/*  72: 77 */     if (!"en_US".equals(this.currentLanguage)) {
/*  73: 79 */       var2.add(this.currentLanguage);
/*  74:    */     }
/*  75: 82 */     currentLocale.loadLocaleDataFiles(par1ResourceManager, var2);
/*  76: 83 */     StringTranslate.replaceWith(currentLocale.field_135032_a);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isCurrentLocaleUnicode()
/*  80:    */   {
/*  81: 88 */     return currentLocale.isUnicode();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isCurrentLanguageBidirectional()
/*  85:    */   {
/*  86: 93 */     return (getCurrentLanguage() != null) && (getCurrentLanguage().isBidirectional());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCurrentLanguage(Language par1Language)
/*  90:    */   {
/*  91: 98 */     this.currentLanguage = par1Language.getLanguageCode();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Language getCurrentLanguage()
/*  95:    */   {
/*  96:103 */     return this.languageMap.containsKey(this.currentLanguage) ? (Language)this.languageMap.get(this.currentLanguage) : (Language)this.languageMap.get("en_US");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public SortedSet getLanguages()
/* 100:    */   {
/* 101:108 */     return Sets.newTreeSet(this.languageMap.values());
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.LanguageManager
 * JD-Core Version:    0.7.0.1
 */