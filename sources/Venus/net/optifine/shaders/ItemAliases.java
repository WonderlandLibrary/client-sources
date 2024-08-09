/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ItemAliases {
    private static int[] itemAliases = null;
    private static boolean updateOnResourcesReloaded;

    public static int getItemAliasId(int n) {
        if (itemAliases == null) {
            return 1;
        }
        return n >= 0 && n < itemAliases.length ? itemAliases[n] : -1;
    }

    public static void resourcesReloaded() {
        if (updateOnResourcesReloaded) {
            updateOnResourcesReloaded = false;
            ItemAliases.update(Shaders.getShaderPack());
        }
    }

    public static void update(IShaderPack iShaderPack) {
        ItemAliases.reset();
        if (iShaderPack != null) {
            if (Reflector.Loader_getActiveModList.exists() && Minecraft.getInstance().getResourceManager() == null) {
                Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
                updateOnResourcesReloaded = true;
            } else {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                String string = "/shaders/item.properties";
                InputStream inputStream = iShaderPack.getResourceAsStream(string);
                if (inputStream != null) {
                    ItemAliases.loadItemAliases(inputStream, string, arrayList);
                }
                ItemAliases.loadModItemAliases(arrayList);
                if (arrayList.size() > 0) {
                    itemAliases = ItemAliases.toArray(arrayList);
                }
            }
        }
    }

    private static void loadModItemAliases(List<Integer> list) {
        String[] stringArray = ReflectorForge.getForgeModIds();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string, "shaders/item.properties");
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                ItemAliases.loadItemAliases(inputStream, resourceLocation.toString(), list);
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private static void loadItemAliases(InputStream inputStream, String string, List<Integer> list) {
        if (inputStream != null) {
            try {
                inputStream = MacroProcessor.process(inputStream, string, true);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                Config.dbg("[Shaders] Parsing item mappings: " + string);
                ConnectedParser connectedParser = new ConnectedParser("Shaders");
                for (String string2 : ((Hashtable)propertiesOrdered).keySet()) {
                    String string3 = propertiesOrdered.getProperty(string2);
                    String string4 = "item.";
                    if (!string2.startsWith(string4)) {
                        Config.warn("[Shaders] Invalid item ID: " + string2);
                        continue;
                    }
                    String string5 = StrUtils.removePrefix(string2, string4);
                    int n = Config.parseInt(string5, -1);
                    if (n < 0) {
                        Config.warn("[Shaders] Invalid item alias ID: " + n);
                        continue;
                    }
                    int[] nArray = connectedParser.parseItems(string3);
                    if (nArray != null && nArray.length >= 1) {
                        for (int i = 0; i < nArray.length; ++i) {
                            int n2 = nArray[i];
                            ItemAliases.addToList(list, n2, n);
                        }
                        continue;
                    }
                    Config.warn("[Shaders] Invalid item ID mapping: " + string2 + "=" + string3);
                }
            } catch (IOException iOException) {
                Config.warn("[Shaders] Error reading: " + string);
            }
        }
    }

    private static void addToList(List<Integer> list, int n, int n2) {
        while (list.size() <= n) {
            list.add(-1);
        }
        list.set(n, n2);
    }

    private static int[] toArray(List<Integer> list) {
        int[] nArray = new int[list.size()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = list.get(i);
        }
        return nArray;
    }

    public static void reset() {
        itemAliases = null;
    }
}

