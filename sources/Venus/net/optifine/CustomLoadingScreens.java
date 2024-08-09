/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.CustomLoadingScreen;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.WorldUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomLoadingScreens {
    private static CustomLoadingScreen[] screens = null;
    private static int screensMinDimensionId = 0;

    public static CustomLoadingScreen getCustomLoadingScreen() {
        if (screens == null) {
            return null;
        }
        RegistryKey<World> registryKey = PacketThreadUtil.lastDimensionType;
        if (registryKey == null) {
            return null;
        }
        int n = WorldUtils.getDimensionId(registryKey);
        int n2 = n - screensMinDimensionId;
        CustomLoadingScreen customLoadingScreen = null;
        if (n2 >= 0 && n2 < screens.length) {
            customLoadingScreen = screens[n2];
        }
        return customLoadingScreen;
    }

    public static void update() {
        screens = null;
        screensMinDimensionId = 0;
        Pair<CustomLoadingScreen[], Integer> pair = CustomLoadingScreens.parseScreens();
        screens = pair.getLeft();
        screensMinDimensionId = pair.getRight();
    }

    private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
        String string;
        Integer[] integerArray;
        String string2 = "optifine/gui/loading/background";
        String string3 = ".png";
        String[] stringArray = ResUtils.collectFiles(string2, string3);
        HashMap<Integer, Integer[]> hashMap = new HashMap<Integer, Integer[]>();
        for (int i = 0; i < stringArray.length; ++i) {
            integerArray = stringArray[i];
            string = StrUtils.removePrefixSuffix((String)integerArray, string2, string3);
            int n = Config.parseInt(string, Integer.MIN_VALUE);
            if (n == Integer.MIN_VALUE) {
                CustomLoadingScreens.warn("Invalid dimension ID: " + string + ", path: " + (String)integerArray);
                continue;
            }
            hashMap.put(n, integerArray);
        }
        Set set = hashMap.keySet();
        integerArray = set.toArray(new Integer[set.size()]);
        Arrays.sort((Object[])integerArray);
        if (integerArray.length <= 0) {
            return new ImmutablePair<CustomLoadingScreen[], Integer>(null, 0);
        }
        string = "optifine/gui/loading/loading.properties";
        Properties properties = ResUtils.readProperties(string, "CustomLoadingScreens");
        int n = integerArray[0];
        int n2 = integerArray[integerArray.length - 1];
        int n3 = n2 - n + 1;
        CustomLoadingScreen[] customLoadingScreenArray = new CustomLoadingScreen[n3];
        for (int i = 0; i < integerArray.length; ++i) {
            Integer n4 = integerArray[i];
            String string4 = (String)hashMap.get(n4);
            customLoadingScreenArray[n4.intValue() - n] = CustomLoadingScreen.parseScreen(string4, n4, properties);
        }
        return new ImmutablePair<CustomLoadingScreen[], Integer>(customLoadingScreenArray, n);
    }

    public static void warn(String string) {
        Config.warn("CustomLoadingScreen: " + string);
    }

    public static void dbg(String string) {
        Config.dbg("CustomLoadingScreen: " + string);
    }
}

