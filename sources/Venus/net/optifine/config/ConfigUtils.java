/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.util.PropertiesOrdered;

public class ConfigUtils {
    public static String readString(String string, String string2) {
        Properties properties = ConfigUtils.readProperties(string);
        if (properties == null) {
            return null;
        }
        String string3 = properties.getProperty(string2);
        if (string3 != null) {
            string3 = string3.trim();
        }
        return string3;
    }

    public static Properties readProperties(String string) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return null;
            }
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            return propertiesOrdered;
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (IOException iOException) {
            Config.warn("Error parsing: " + string);
            Config.warn(iOException.getClass().getName() + ": " + iOException.getMessage());
            return null;
        }
    }
}

