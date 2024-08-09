/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.LegacyResourcePackWrapper;
import net.minecraft.client.resources.LegacyResourcePackWrapperV4;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ResUtils {
    public static String[] collectFiles(String string, String string2) {
        return ResUtils.collectFiles(new String[]{string}, new String[]{string2});
    }

    public static String[] collectFiles(String[] stringArray, String[] stringArray2) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        IResourcePack[] iResourcePackArray = Config.getResourcePacks();
        for (int i = 0; i < iResourcePackArray.length; ++i) {
            IResourcePack iResourcePack = iResourcePackArray[i];
            String[] stringArray3 = ResUtils.collectFiles(iResourcePack, stringArray, stringArray2, (String[])null);
            linkedHashSet.addAll(Arrays.asList(stringArray3));
        }
        return linkedHashSet.toArray(new String[linkedHashSet.size()]);
    }

    public static String[] collectFiles(IResourcePack iResourcePack, String string, String string2, String[] stringArray) {
        return ResUtils.collectFiles(iResourcePack, new String[]{string}, new String[]{string2}, stringArray);
    }

    public static String[] collectFiles(IResourcePack iResourcePack, String[] stringArray, String[] stringArray2) {
        return ResUtils.collectFiles(iResourcePack, stringArray, stringArray2, (String[])null);
    }

    public static String[] collectFiles(IResourcePack iResourcePack, String[] stringArray, String[] stringArray2, String[] stringArray3) {
        IResourcePack iResourcePack2;
        if (iResourcePack instanceof VanillaPack) {
            return ResUtils.collectFilesFixed(iResourcePack, stringArray3);
        }
        if (iResourcePack instanceof LegacyResourcePackWrapper) {
            iResourcePack2 = (IResourcePack)Reflector.getFieldValue(iResourcePack, Reflector.LegacyResourcePackWrapper_pack);
            if (iResourcePack2 == null) {
                Config.warn("LegacyResourcePackWrapper base resource pack not found: " + iResourcePack);
                return new String[0];
            }
            iResourcePack = iResourcePack2;
        }
        if (iResourcePack instanceof LegacyResourcePackWrapperV4) {
            iResourcePack2 = (IResourcePack)Reflector.getFieldValue(iResourcePack, Reflector.LegacyResourcePackWrapperV4_pack);
            if (iResourcePack2 == null) {
                Config.warn("LegacyResourcePackWrapperV4 base resource pack not found: " + iResourcePack);
                return new String[0];
            }
            iResourcePack = iResourcePack2;
        }
        if (!(iResourcePack instanceof ResourcePack)) {
            Config.warn("Unknown resource pack type: " + iResourcePack);
            return new String[0];
        }
        iResourcePack2 = (ResourcePack)iResourcePack;
        File file = ((ResourcePack)iResourcePack2).file;
        if (file == null) {
            return new String[0];
        }
        if (file.isDirectory()) {
            return ResUtils.collectFilesFolder(file, "", stringArray, stringArray2);
        }
        if (file.isFile()) {
            return ResUtils.collectFilesZIP(file, stringArray, stringArray2);
        }
        Config.warn("Unknown resource pack file: " + file);
        return new String[0];
    }

    private static String[] collectFilesFixed(IResourcePack iResourcePack, String[] stringArray) {
        if (stringArray == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (!ResUtils.isLowercase(string)) {
                Config.warn("Skipping non-lowercase path: " + string);
                continue;
            }
            ResourceLocation resourceLocation = new ResourceLocation(string);
            if (!iResourcePack.resourceExists(ResourcePackType.CLIENT_RESOURCES, resourceLocation)) continue;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static String[] collectFilesFolder(File file, String string, String[] stringArray, String[] stringArray2) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        String string2 = "assets/minecraft/";
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return new String[0];
        }
        for (int i = 0; i < fileArray.length; ++i) {
            Object object;
            File file2 = fileArray[i];
            if (file2.isFile()) {
                object = string + file2.getName();
                if (!((String)object).startsWith(string2) || !StrUtils.startsWith((String)(object = ((String)object).substring(string2.length())), stringArray) || !StrUtils.endsWith((String)object, stringArray2)) continue;
                if (!ResUtils.isLowercase((String)object)) {
                    Config.warn("Skipping non-lowercase path: " + (String)object);
                    continue;
                }
                arrayList.add(object);
                continue;
            }
            if (!file2.isDirectory()) continue;
            object = string + file2.getName() + "/";
            String[] stringArray3 = ResUtils.collectFilesFolder(file2, (String)object, stringArray, stringArray2);
            for (int j = 0; j < stringArray3.length; ++j) {
                String string3 = stringArray3[j];
                arrayList.add(string3);
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static String[] collectFilesZIP(File file, String[] stringArray, String[] stringArray2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = "assets/minecraft/";
        try {
            String[] stringArray3;
            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                stringArray3 = enumeration.nextElement();
                String string2 = stringArray3.getName();
                if (!string2.startsWith(string) || !StrUtils.startsWith(string2 = string2.substring(string.length()), stringArray) || !StrUtils.endsWith(string2, stringArray2)) continue;
                if (!ResUtils.isLowercase(string2)) {
                    Config.warn("Skipping non-lowercase path: " + string2);
                    continue;
                }
                arrayList.add(string2);
            }
            zipFile.close();
            stringArray3 = arrayList.toArray(new String[arrayList.size()]);
            return stringArray3;
        } catch (IOException iOException) {
            iOException.printStackTrace();
            return new String[0];
        }
    }

    private static boolean isLowercase(String string) {
        return string.equals(string.toLowerCase(Locale.ROOT));
    }

    public static Properties readProperties(String string, String string2) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        try {
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return null;
            }
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            Config.dbg(string2 + ": Loading " + string);
            return propertiesOrdered;
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (IOException iOException) {
            Config.warn(string2 + ": Error reading " + string);
            return null;
        }
    }

    public static Properties readProperties(InputStream inputStream, String string) {
        if (inputStream == null) {
            return null;
        }
        try {
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            return propertiesOrdered;
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (IOException iOException) {
            return null;
        }
    }
}

