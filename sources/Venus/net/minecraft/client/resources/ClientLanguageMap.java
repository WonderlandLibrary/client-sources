/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.Language;
import net.minecraft.client.util.BidiReorderer;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.optifine.Lang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientLanguageMap
extends LanguageMap {
    private static final Logger field_239493_a_ = LogManager.getLogger();
    private final Map<String, String> field_239495_c_;
    private final boolean field_239496_d_;

    private ClientLanguageMap(Map<String, String> map, boolean bl) {
        this.field_239495_c_ = map;
        this.field_239496_d_ = bl;
    }

    public static ClientLanguageMap func_239497_a_(IResourceManager iResourceManager, List<Language> list) {
        HashMap<String, String> hashMap = Maps.newHashMap();
        boolean bl = false;
        for (Language language : list) {
            bl |= language.isBidirectional();
            String string = String.format("lang/%s.json", language.getCode());
            for (String string2 : iResourceManager.getResourceNamespaces()) {
                try {
                    ResourceLocation resourceLocation = new ResourceLocation(string2, string);
                    ClientLanguageMap.func_239498_a_(iResourceManager.getAllResources(resourceLocation), hashMap);
                    Lang.loadResources(iResourceManager, language.getCode(), hashMap);
                } catch (FileNotFoundException fileNotFoundException) {
                } catch (Exception exception) {
                    field_239493_a_.warn("Skipped language file: {}:{} ({})", (Object)string2, (Object)string, (Object)exception.toString());
                }
            }
        }
        return new ClientLanguageMap(ImmutableMap.copyOf(hashMap), bl);
    }

    private static void func_239498_a_(List<IResource> list, Map<String, String> map) {
        for (IResource iResource : list) {
            try {
                InputStream inputStream = iResource.getInputStream();
                try {
                    LanguageMap.func_240593_a_(inputStream, map::put);
                } finally {
                    if (inputStream == null) continue;
                    inputStream.close();
                }
            } catch (IOException iOException) {
                field_239493_a_.warn("Failed to load translations from {}", (Object)iResource, (Object)iOException);
            }
        }
    }

    @Override
    public String func_230503_a_(String string) {
        return this.field_239495_c_.getOrDefault(string, string);
    }

    @Override
    public boolean func_230506_b_(String string) {
        return this.field_239495_c_.containsKey(string);
    }

    @Override
    public boolean func_230505_b_() {
        return this.field_239496_d_;
    }

    @Override
    public IReorderingProcessor func_241870_a(ITextProperties iTextProperties) {
        return BidiReorderer.func_243508_a(iTextProperties, this.field_239496_d_);
    }

    public Map<String, String> getLanguageData() {
        return this.field_239495_c_;
    }
}

