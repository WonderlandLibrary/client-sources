/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.PropertiesOrdered;

public class FontUtils {
    public static Properties readFontProperties(ResourceLocation resourceLocation) {
        String string = resourceLocation.getPath();
        PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
        String string2 = ".png";
        if (!string.endsWith(string2)) {
            return propertiesOrdered;
        }
        String string3 = string.substring(0, string.length() - string2.length()) + ".properties";
        try {
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string3);
            InputStream inputStream = Config.getResourceStream(Config.getResourceManager(), resourceLocation2);
            if (inputStream == null) {
                return propertiesOrdered;
            }
            Config.log("Loading " + string3);
            propertiesOrdered.load(inputStream);
            inputStream.close();
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return propertiesOrdered;
    }

    public static Int2ObjectMap<Float> readCustomCharWidths(Properties properties) {
        Int2ObjectOpenHashMap<Float> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap<Float>();
        for (String string : properties.keySet()) {
            String string2;
            float f;
            String string3;
            int n;
            String string4;
            if (!string.startsWith(string4 = "width.") || (n = Config.parseInt(string3 = string.substring(string4.length()), -1)) < 0 || !((f = Config.parseFloat(string2 = properties.getProperty(string), -1.0f)) >= 0.0f)) continue;
            char c = (char)n;
            int2ObjectOpenHashMap.put(c, new Float(f));
        }
        return int2ObjectOpenHashMap;
    }

    public static float readFloat(Properties properties, String string, float f) {
        String string2 = properties.getProperty(string);
        if (string2 == null) {
            return f;
        }
        float f2 = Config.parseFloat(string2, Float.MIN_VALUE);
        if (f2 == Float.MIN_VALUE) {
            Config.warn("Invalid value for " + string + ": " + string2);
            return f;
        }
        return f2;
    }

    public static boolean readBoolean(Properties properties, String string, boolean bl) {
        String string2 = properties.getProperty(string);
        if (string2 == null) {
            return bl;
        }
        String string3 = string2.toLowerCase().trim();
        if (!string3.equals("true") && !string3.equals("on")) {
            if (!string3.equals("false") && !string3.equals("off")) {
                Config.warn("Invalid value for " + string + ": " + string2);
                return bl;
            }
            return true;
        }
        return false;
    }

    public static ResourceLocation getHdFontLocation(ResourceLocation resourceLocation) {
        if (!Config.isCustomFonts()) {
            return resourceLocation;
        }
        if (resourceLocation == null) {
            return resourceLocation;
        }
        if (!Config.isMinecraftThread()) {
            return resourceLocation;
        }
        Object object = resourceLocation.getPath();
        String string = "textures/";
        String string2 = "optifine/";
        if (!((String)object).startsWith(string)) {
            return resourceLocation;
        }
        object = ((String)object).substring(string.length());
        object = string2 + (String)object;
        ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), (String)object);
        return Config.hasResource(Config.getResourceManager(), resourceLocation2) ? resourceLocation2 : resourceLocation;
    }

    public static void reloadFonts() {
        IFutureReloadListener.IStage iStage = new IFutureReloadListener.IStage(){

            @Override
            public <T> CompletableFuture<T> markCompleteAwaitingOthers(T t) {
                return CompletableFuture.completedFuture(t);
            }
        };
        Executor executor = Util.getServerExecutor();
        Minecraft minecraft = Minecraft.getInstance();
        FontResourceManager fontResourceManager = (FontResourceManager)Reflector.getFieldValue(minecraft, Reflector.Minecraft_fontResourceManager);
        if (fontResourceManager != null) {
            fontResourceManager.getReloadListener().reload(iStage, Config.getResourceManager(), EmptyProfiler.INSTANCE, EmptyProfiler.INSTANCE, executor, minecraft);
        }
    }
}

