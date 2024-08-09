/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import net.minecraft.client.resources.ClientLanguageMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.IResourcePack;
import net.minecraft.util.text.LanguageMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager
implements IResourceManagerReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Language field_239503_b_ = new Language("en_us", "US", "English", false);
    private Map<String, Language> languageMap = ImmutableMap.of("en_us", field_239503_b_);
    private String currentLanguage;
    private Language field_239504_e_ = field_239503_b_;

    public LanguageManager(String string) {
        this.currentLanguage = string;
    }

    private static Map<String, Language> func_239506_a_(Stream<IResourcePack> stream) {
        HashMap hashMap = Maps.newHashMap();
        stream.forEach(arg_0 -> LanguageManager.lambda$func_239506_a_$0(hashMap, arg_0));
        return ImmutableMap.copyOf(hashMap);
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.languageMap = LanguageManager.func_239506_a_(iResourceManager.getResourcePackStream());
        Language language = this.languageMap.getOrDefault("en_us", field_239503_b_);
        this.field_239504_e_ = this.languageMap.getOrDefault(this.currentLanguage, language);
        ArrayList<Language> arrayList = Lists.newArrayList(language);
        if (this.field_239504_e_ != language) {
            arrayList.add(this.field_239504_e_);
        }
        ClientLanguageMap clientLanguageMap = ClientLanguageMap.func_239497_a_(iResourceManager, arrayList);
        I18n.func_239502_a_(clientLanguageMap);
        LanguageMap.func_240594_a_(clientLanguageMap);
    }

    public void setCurrentLanguage(Language language) {
        this.currentLanguage = language.getCode();
        this.field_239504_e_ = language;
    }

    public Language getCurrentLanguage() {
        return this.field_239504_e_;
    }

    public SortedSet<Language> getLanguages() {
        return Sets.newTreeSet(this.languageMap.values());
    }

    public Language getLanguage(String string) {
        return this.languageMap.get(string);
    }

    private static void lambda$func_239506_a_$0(Map map, IResourcePack iResourcePack) {
        try {
            LanguageMetadataSection languageMetadataSection = iResourcePack.getMetadata(LanguageMetadataSection.field_195818_a);
            if (languageMetadataSection != null) {
                for (Language language : languageMetadataSection.getLanguages()) {
                    map.putIfAbsent(language.getCode(), language);
                }
            }
        } catch (IOException | RuntimeException exception) {
            LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", (Object)iResourcePack.getName(), (Object)exception);
        }
    }
}

