/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.providers.DefaultGlyphProvider;
import net.minecraft.client.gui.fonts.providers.GlyphProviderTypes;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontResourceManager
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation field_238544_a_ = new ResourceLocation("minecraft", "missing");
    private final Font field_238545_c_;
    private final Map<ResourceLocation, Font> field_238546_d_ = Maps.newHashMap();
    private final TextureManager textureManager;
    private Map<ResourceLocation, ResourceLocation> field_238547_f_ = ImmutableMap.of();
    private final IFutureReloadListener reloadListener = new ReloadListener<Map<ResourceLocation, List<IGlyphProvider>>>(this){
        final FontResourceManager this$0;
        {
            this.this$0 = fontResourceManager;
        }

        @Override
        protected Map<ResourceLocation, List<IGlyphProvider>> prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
            iProfiler.startTick();
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            HashMap<ResourceLocation, List<IGlyphProvider>> hashMap = Maps.newHashMap();
            for (ResourceLocation resourceLocation : iResourceManager.getAllResourceLocations("font", 1::lambda$prepare$0)) {
                String string = resourceLocation.getPath();
                ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string.substring(5, string.length() - 5));
                List list = hashMap.computeIfAbsent(resourceLocation2, 1::lambda$prepare$1);
                iProfiler.startSection(resourceLocation2::toString);
                try {
                    for (IResource iResource : iResourceManager.getAllResources(resourceLocation)) {
                        iProfiler.startSection(iResource::getPackName);
                        try (Closeable closeable = iResource.getInputStream();
                             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)closeable, StandardCharsets.UTF_8));){
                            iProfiler.startSection("reading");
                            JsonArray jsonArray = JSONUtils.getJsonArray(JSONUtils.fromJson(gson, bufferedReader, JsonObject.class), "providers");
                            iProfiler.endStartSection("parsing");
                            for (int i = jsonArray.size() - 1; i >= 0; --i) {
                                JsonObject jsonObject = JSONUtils.getJsonObject(jsonArray.get(i), "providers[" + i + "]");
                                try {
                                    String string2 = JSONUtils.getString(jsonObject, "type");
                                    GlyphProviderTypes glyphProviderTypes = GlyphProviderTypes.byName(string2);
                                    iProfiler.startSection(string2);
                                    IGlyphProvider iGlyphProvider = glyphProviderTypes.getFactory(jsonObject).create(iResourceManager);
                                    if (iGlyphProvider != null) {
                                        list.add(iGlyphProvider);
                                    }
                                    iProfiler.endSection();
                                    continue;
                                } catch (RuntimeException runtimeException) {
                                    LOGGER.warn("Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", (Object)resourceLocation2, (Object)iResource.getPackName(), (Object)runtimeException.getMessage());
                                }
                            }
                            iProfiler.endSection();
                        } catch (RuntimeException runtimeException) {
                            LOGGER.warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", (Object)resourceLocation2, (Object)iResource.getPackName(), (Object)runtimeException.getMessage());
                        }
                        iProfiler.endSection();
                    }
                } catch (IOException iOException) {
                    LOGGER.warn("Unable to load font '{}' in fonts.json: {}", (Object)resourceLocation2, (Object)iOException.getMessage());
                }
                iProfiler.startSection("caching");
                IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
                for (Closeable closeable : list) {
                    intOpenHashSet.addAll(closeable.func_230428_a_());
                }
                intOpenHashSet.forEach(arg_0 -> 1.lambda$prepare$2(list, arg_0));
                iProfiler.endSection();
                iProfiler.endSection();
            }
            iProfiler.endTick();
            return hashMap;
        }

        @Override
        protected void apply(Map<ResourceLocation, List<IGlyphProvider>> map, IResourceManager iResourceManager, IProfiler iProfiler) {
            iProfiler.startTick();
            iProfiler.startSection("closing");
            this.this$0.field_238546_d_.values().forEach(Font::close);
            this.this$0.field_238546_d_.clear();
            iProfiler.endStartSection("reloading");
            map.forEach(this::lambda$apply$3);
            iProfiler.endSection();
            iProfiler.endTick();
        }

        @Override
        public String getSimpleName() {
            return "FontManager";
        }

        @Override
        protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
            this.apply((Map)object, iResourceManager, iProfiler);
        }

        @Override
        protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
            return this.prepare(iResourceManager, iProfiler);
        }

        private void lambda$apply$3(ResourceLocation resourceLocation, List list) {
            Font font = new Font(this.this$0.textureManager, resourceLocation);
            font.setGlyphProviders(Lists.reverse(list));
            this.this$0.field_238546_d_.put(resourceLocation, font);
        }

        private static void lambda$prepare$2(List list, int n) {
            block1: {
                IGlyphProvider iGlyphProvider;
                if (n == 32) break block1;
                Iterator iterator2 = Lists.reverse(list).iterator();
                while (iterator2.hasNext() && (iGlyphProvider = (IGlyphProvider)iterator2.next()).getGlyphInfo(n) == null) {
                }
            }
        }

        private static List lambda$prepare$1(ResourceLocation resourceLocation) {
            return Lists.newArrayList(new DefaultGlyphProvider());
        }

        private static boolean lambda$prepare$0(String string) {
            return string.endsWith(".json");
        }
    };

    public FontResourceManager(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.field_238545_c_ = Util.make(new Font(textureManager, field_238544_a_), FontResourceManager::lambda$new$0);
    }

    public void func_238551_a_(Map<ResourceLocation, ResourceLocation> map) {
        this.field_238547_f_ = map;
    }

    public FontRenderer func_238548_a_() {
        return new FontRenderer(this::lambda$func_238548_a_$1);
    }

    public IFutureReloadListener getReloadListener() {
        return this.reloadListener;
    }

    @Override
    public void close() {
        this.field_238546_d_.values().forEach(Font::close);
        this.field_238545_c_.close();
    }

    private Font lambda$func_238548_a_$1(ResourceLocation resourceLocation) {
        return this.field_238546_d_.getOrDefault(this.field_238547_f_.getOrDefault(resourceLocation, resourceLocation), this.field_238545_c_);
    }

    private static void lambda$new$0(Font font) {
        font.setGlyphProviders(Lists.newArrayList(new DefaultGlyphProvider()));
    }
}

