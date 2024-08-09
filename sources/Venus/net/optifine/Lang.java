/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.I18n;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang {
    private static final Splitter splitter = Splitter.on('=').limit(2);
    private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

    public static void resourcesReloaded() {
        HashMap hashMap = new HashMap();
        ArrayList<CallSite> arrayList = new ArrayList<CallSite>();
        String string = "optifine/lang/";
        String string2 = "en_us";
        String string3 = ".lang";
        arrayList.add((CallSite)((Object)(string + string2 + string3)));
        if (!Config.getGameSettings().language.equals(string2)) {
            arrayList.add((CallSite)((Object)(string + Config.getGameSettings().language + string3)));
        }
        String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
        Lang.loadResources(Config.getDefaultResourcePack(), stringArray, hashMap);
        IResourcePack[] iResourcePackArray = Config.getResourcePacks();
        for (int i = 0; i < iResourcePackArray.length; ++i) {
            IResourcePack iResourcePack = iResourcePackArray[i];
            Lang.loadResources(iResourcePack, stringArray, hashMap);
        }
    }

    private static void loadResources(IResourcePack iResourcePack, String[] stringArray, Map map) {
        try {
            for (int i = 0; i < stringArray.length; ++i) {
                InputStream inputStream;
                String string = stringArray[i];
                ResourceLocation resourceLocation = new ResourceLocation(string);
                if (!iResourcePack.resourceExists(ResourcePackType.CLIENT_RESOURCES, resourceLocation) || (inputStream = iResourcePack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, resourceLocation)) == null) continue;
                Lang.loadLocaleData(inputStream, map);
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void loadLocaleData(InputStream inputStream, Map map) throws IOException {
        Iterator<String> iterator2 = IOUtils.readLines(inputStream, Charsets.UTF_8).iterator();
        inputStream.close();
        while (iterator2.hasNext()) {
            String[] stringArray;
            String string = iterator2.next();
            if (string.isEmpty() || string.charAt(0) == '#' || (stringArray = Iterables.toArray(splitter.split(string), String.class)) == null || stringArray.length != 2) continue;
            String string2 = stringArray[0];
            String string3 = pattern.matcher(stringArray[5]).replaceAll("%$1s");
            map.put(string2, string3);
        }
    }

    public static void loadResources(IResourceManager iResourceManager, String string, Map<String, String> map) {
        try {
            String string2 = "optifine/lang/" + string + ".lang";
            ResourceLocation resourceLocation = new ResourceLocation(string2);
            IResource iResource = iResourceManager.getResource(resourceLocation);
            InputStream inputStream = iResource.getInputStream();
            Lang.loadLocaleData(inputStream, map);
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    public static String get(String string) {
        return I18n.format(string, new Object[0]);
    }

    public static TranslationTextComponent getComponent(String string) {
        return new TranslationTextComponent(string);
    }

    public static String get(String string, String string2) {
        String string3 = I18n.format(string, new Object[0]);
        return string3 != null && !string3.equals(string) ? string3 : string2;
    }

    public static String getOn() {
        return I18n.format("options.on", new Object[0]);
    }

    public static String getOff() {
        return I18n.format("options.off", new Object[0]);
    }

    public static String getFast() {
        return I18n.format("options.graphics.fast", new Object[0]);
    }

    public static String getFancy() {
        return I18n.format("options.graphics.fancy", new Object[0]);
    }

    public static String getDefault() {
        return I18n.format("generator.default", new Object[0]);
    }
}

