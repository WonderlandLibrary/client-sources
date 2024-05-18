package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.data.*;
import java.io.*;
import java.util.*;

public class LanguageManager implements IResourceManagerReloadListener
{
    protected static final Locale currentLocale;
    private static final String[] I;
    private String currentLanguage;
    private static final Logger logger;
    private final IMetadataSerializer theMetadataSerializer;
    private Map<String, Language> languageMap;
    
    static {
        I();
        logger = LogManager.getLogger();
        currentLocale = new Locale();
    }
    
    private static void I() {
        (I = new String[0x28 ^ 0x2E])["".length()] = I("\u001c8?\t=\u0011>4", "pYQnH");
        LanguageManager.I[" ".length()] = I("4\u000f(!5\u0004A=,y\u0011\u0000;0<A\f,78\u0005\u0000=\"y\u0012\u0004*70\u000e\u000fi,?A\u0013,06\u0014\u0013*&)\u0000\u0002\"yy", "aaICY");
        LanguageManager.I["  ".length()] = I("<\u0000\u0017\u001b)\fN\u0002\u0016e\u0019\u000f\u0004\n I\u0003\u0013\r$\r\u000f\u0002\u0018e\u001a\u000b\u0015\r,\u0006\u0000V\u0016#I\u001c\u0013\n*\u001c\u001c\u0015\u001c5\b\r\u001dCe", "invyE");
        LanguageManager.I["   ".length()] = I("\u0014?\u001041", "qQOab");
        LanguageManager.I[0x51 ^ 0x55] = I("\b?\u001a2\t", "mQEgZ");
        LanguageManager.I[0x3F ^ 0x3A] = I(".'\n\u001e&", "KIUKu");
    }
    
    public boolean isCurrentLanguageBidirectional() {
        if (this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        final String[] array = new String[" ".length()];
        array["".length()] = LanguageManager.I["   ".length()];
        final ArrayList arrayList = Lists.newArrayList((Object[])array);
        if (!LanguageManager.I[0x40 ^ 0x44].equals(this.currentLanguage)) {
            arrayList.add(this.currentLanguage);
        }
        LanguageManager.currentLocale.loadLocaleDataFiles(resourceManager, arrayList);
        StringTranslate.replaceWith(LanguageManager.currentLocale.properties);
    }
    
    public SortedSet<Language> getLanguages() {
        return (SortedSet<Language>)Sets.newTreeSet((Iterable)this.languageMap.values());
    }
    
    public void setCurrentLanguage(final Language language) {
        this.currentLanguage = language.getLanguageCode();
    }
    
    public Language getCurrentLanguage() {
        Language language;
        if (this.languageMap.containsKey(this.currentLanguage)) {
            language = this.languageMap.get(this.currentLanguage);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            language = this.languageMap.get(LanguageManager.I[0xA3 ^ 0xA6]);
        }
        return language;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LanguageManager(final IMetadataSerializer theMetadataSerializer, final String currentLanguage) {
        this.languageMap = (Map<String, Language>)Maps.newHashMap();
        this.theMetadataSerializer = theMetadataSerializer;
        this.currentLanguage = currentLanguage;
        I18n.setLocale(LanguageManager.currentLocale);
    }
    
    public boolean isCurrentLocaleUnicode() {
        return LanguageManager.currentLocale.isUnicode();
    }
    
    public void parseLanguageMetadata(final List<IResourcePack> list) {
        this.languageMap.clear();
        final Iterator<IResourcePack> iterator = list.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IResourcePack resourcePack = iterator.next();
            try {
                final LanguageMetadataSection languageMetadataSection = resourcePack.getPackMetadata(this.theMetadataSerializer, LanguageManager.I["".length()]);
                if (languageMetadataSection == null) {
                    continue;
                }
                final Iterator<Language> iterator2 = languageMetadataSection.getLanguages().iterator();
                "".length();
                if (1 == 3) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Language language = iterator2.next();
                    if (!this.languageMap.containsKey(language.getLanguageCode())) {
                        this.languageMap.put(language.getLanguageCode(), language);
                    }
                }
                "".length();
                if (3 == 0) {
                    throw null;
                }
                continue;
            }
            catch (RuntimeException ex) {
                LanguageManager.logger.warn(LanguageManager.I[" ".length()] + resourcePack.getPackName(), (Throwable)ex);
                "".length();
                if (3 < 3) {
                    throw null;
                }
                continue;
            }
            catch (IOException ex2) {
                LanguageManager.logger.warn(LanguageManager.I["  ".length()] + resourcePack.getPackName(), (Throwable)ex2);
            }
        }
    }
}
