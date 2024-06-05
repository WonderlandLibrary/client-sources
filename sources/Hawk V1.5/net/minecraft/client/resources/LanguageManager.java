package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener {
   private final IMetadataSerializer theMetadataSerializer;
   protected static final Locale currentLocale = new Locale();
   private Map languageMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00001096";
   private String currentLanguage;
   private static final Logger logger = LogManager.getLogger();

   public boolean isCurrentLocaleUnicode() {
      return currentLocale.isUnicode();
   }

   public Language getCurrentLanguage() {
      return this.languageMap.containsKey(this.currentLanguage) ? (Language)this.languageMap.get(this.currentLanguage) : (Language)this.languageMap.get("en_US");
   }

   public void parseLanguageMetadata(List var1) {
      this.languageMap.clear();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         IResourcePack var3 = (IResourcePack)var2.next();

         try {
            LanguageMetadataSection var4 = (LanguageMetadataSection)var3.getPackMetadata(this.theMetadataSerializer, "language");
            if (var4 != null) {
               Iterator var5 = var4.getLanguages().iterator();

               while(var5.hasNext()) {
                  Language var6 = (Language)var5.next();
                  if (!this.languageMap.containsKey(var6.getLanguageCode())) {
                     this.languageMap.put(var6.getLanguageCode(), var6);
                  }
               }
            }
         } catch (RuntimeException var7) {
            logger.warn(String.valueOf((new StringBuilder("Unable to parse metadata section of resourcepack: ")).append(var3.getPackName())), var7);
         } catch (IOException var8) {
            logger.warn(String.valueOf((new StringBuilder("Unable to parse metadata section of resourcepack: ")).append(var3.getPackName())), var8);
         }
      }

   }

   public boolean isCurrentLanguageBidirectional() {
      return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
   }

   public SortedSet getLanguages() {
      return Sets.newTreeSet(this.languageMap.values());
   }

   public void onResourceManagerReload(IResourceManager var1) {
      ArrayList var2 = Lists.newArrayList(new String[]{"en_US"});
      if (!"en_US".equals(this.currentLanguage)) {
         var2.add(this.currentLanguage);
      }

      currentLocale.loadLocaleDataFiles(var1, var2);
      StringTranslate.replaceWith(currentLocale.field_135032_a);
   }

   public void setCurrentLanguage(Language var1) {
      this.currentLanguage = var1.getLanguageCode();
   }

   public LanguageManager(IMetadataSerializer var1, String var2) {
      this.theMetadataSerializer = var1;
      this.currentLanguage = var2;
      I18n.setLocale(currentLocale);
   }
}
