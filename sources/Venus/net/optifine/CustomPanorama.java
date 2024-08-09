/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomPanoramaProperties;
import net.optifine.util.MathUtils;
import net.optifine.util.PropertiesOrdered;

public class CustomPanorama {
    private static CustomPanoramaProperties customPanoramaProperties = null;
    private static final Random random = new Random();

    public static CustomPanoramaProperties getCustomPanoramaProperties() {
        return customPanoramaProperties;
    }

    public static void update() {
        customPanoramaProperties = null;
        String[] stringArray = CustomPanorama.getPanoramaFolders();
        if (stringArray.length > 1) {
            CustomPanoramaProperties customPanoramaProperties;
            Properties[] propertiesArray = CustomPanorama.getPanoramaProperties(stringArray);
            int[] nArray = CustomPanorama.getWeights(propertiesArray);
            int n = CustomPanorama.getRandomIndex(nArray);
            String string = stringArray[n];
            Properties properties = propertiesArray[n];
            if (properties == null) {
                properties = propertiesArray[0];
            }
            if (properties == null) {
                properties = new PropertiesOrdered();
            }
            CustomPanorama.customPanoramaProperties = customPanoramaProperties = new CustomPanoramaProperties(string, properties);
        }
    }

    private static String[] getPanoramaFolders() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add("textures/gui/title/background");
        for (int i = 0; i < 100; ++i) {
            String string = "optifine/gui/background" + i;
            String string2 = string + "/panorama_0.png";
            ResourceLocation resourceLocation = new ResourceLocation(string2);
            if (!Config.hasResource(resourceLocation)) continue;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static Properties[] getPanoramaProperties(String[] stringArray) {
        Properties[] propertiesArray = new Properties[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (i == 0) {
                string = "optifine/gui";
            } else {
                Config.dbg("CustomPanorama: " + string);
            }
            ResourceLocation resourceLocation = new ResourceLocation(string + "/background.properties");
            try {
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                if (inputStream == null) continue;
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                Config.dbg("CustomPanorama: " + resourceLocation.getPath());
                propertiesArray[i] = propertiesOrdered;
                inputStream.close();
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        return propertiesArray;
    }

    private static int[] getWeights(Properties[] propertiesArray) {
        int[] nArray = new int[propertiesArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            Properties properties = propertiesArray[i];
            if (properties == null) {
                properties = propertiesArray[0];
            }
            if (properties == null) {
                nArray[i] = 1;
                continue;
            }
            String string = properties.getProperty("weight", null);
            nArray[i] = Config.parseInt(string, 1);
        }
        return nArray;
    }

    private static int getRandomIndex(int[] nArray) {
        int n = MathUtils.getSum(nArray);
        int n2 = random.nextInt(n);
        int n3 = 0;
        for (int i = 0; i < nArray.length; ++i) {
            if ((n3 += nArray[i]) <= n2) continue;
            return i;
        }
        return nArray.length - 1;
    }
}

