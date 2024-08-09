package net.optifine.util;

import net.minecraft.client.resources.LegacyResourcePackWrapper;
import net.minecraft.client.resources.LegacyResourcePackWrapperV4;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResUtils {
    public static String[] collectFiles(String prefix, String suffix) {
        return collectFiles(new String[]{prefix}, new String[]{suffix});
    }

    public static String[] collectFiles(String[] prefixes, String[] suffixes) {
        Set<String> set = new LinkedHashSet<>();
        IResourcePack[] airesourcepack = Config.getResourcePacks();

        for (IResourcePack iresourcepack : airesourcepack) {
            String[] astring = collectFiles(iresourcepack, prefixes, suffixes, null);
            set.addAll(Arrays.asList(astring));
        }

        return set.toArray(new String[0]);
    }

    public static String[] collectFiles(IResourcePack rp, String prefix, String suffix, String[] defaultPaths) {
        return collectFiles(rp, new String[]{prefix}, new String[]{suffix}, defaultPaths);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes) {
        return collectFiles(rp, prefixes, suffixes, null);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
        if (rp instanceof VanillaPack) {
            return collectFilesFixed(rp, defaultPaths);
        } else {
            if (rp instanceof LegacyResourcePackWrapper) {
                IResourcePack iresourcepack = (IResourcePack) Reflector.getFieldValue(rp, Reflector.LegacyResourcePackWrapper_pack);

                if (iresourcepack == null) {
                    Config.warn("LegacyResourcePackWrapper base resource pack not found: " + rp);
                    return new String[0];
                }

                rp = iresourcepack;
            }

            if (rp instanceof LegacyResourcePackWrapperV4) {
                IResourcePack iresourcepack1 = (IResourcePack) Reflector.getFieldValue(rp, Reflector.LegacyResourcePackWrapperV4_pack);

                if (iresourcepack1 == null) {
                    Config.warn("LegacyResourcePackWrapperV4 base resource pack not found: " + rp);
                    return new String[0];
                }

                rp = iresourcepack1;
            }

            if (!(rp instanceof ResourcePack resourcepack)) {
                Config.warn("Unknown resource pack type: " + rp);
                return new String[0];
            } else {
                File file1 = resourcepack.file;

                if (file1 == null) {
                    return new String[0];
                } else if (file1.isDirectory()) {
                    return collectFilesFolder(file1, "", prefixes, suffixes);
                } else if (file1.isFile()) {
                    return collectFilesZIP(file1, prefixes, suffixes);
                } else {
                    Config.warn("Unknown resource pack file: " + file1);
                    return new String[0];
                }
            }
        }
    }

    private static String[] collectFilesFixed(IResourcePack rp, String[] paths) {
        if (paths == null) {
            return new String[0];
        } else {
            List<String> list = new ArrayList<>();

            for (String s : paths) {
                if (isLowercase(s)) {
                    Config.warn("Skipping non-lowercase path: " + s);
                } else {
                    ResourceLocation resourcelocation = new ResourceLocation(s);

                    if (rp.resourceExists(ResourcePackType.CLIENT_RESOURCES, resourcelocation)) {
                        list.add(s);
                    }
                }
            }

            return list.toArray(new String[0]);
        }
    }

    private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
        List<String> list = new ArrayList<>();
        String s = "assets/minecraft/";
        File[] afile = tpFile.listFiles();

        if (afile == null) {
            return new String[0];
        } else {
            for (File file1 : afile) {
                if (file1.isFile()) {
                    String s3 = basePath + file1.getName();

                    if (s3.startsWith(s)) {
                        s3 = s3.substring(s.length());

                        if (StrUtils.startsWith(s3, prefixes) && StrUtils.endsWith(s3, suffixes)) {
                            if (isLowercase(s3)) {
                                Config.warn("Skipping non-lowercase path: " + s3);
                            } else {
                                list.add(s3);
                            }
                        }
                    }
                } else if (file1.isDirectory()) {
                    String s1 = basePath + file1.getName() + "/";
                    String[] astring = collectFilesFolder(file1, s1, prefixes, suffixes);

                    Collections.addAll(list, astring);
                }
            }

            return list.toArray(new String[0]);
        }
    }

    private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
        List<String> list = new ArrayList<>();
        String s = "assets/minecraft/";

        try {
            ZipFile zipfile = new ZipFile(tpFile);
            Enumeration<?> enumeration = zipfile.entries();

            while (enumeration.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry) enumeration.nextElement();
                String s1 = zipentry.getName();

                if (s1.startsWith(s)) {
                    s1 = s1.substring(s.length());

                    if (StrUtils.startsWith(s1, prefixes) && StrUtils.endsWith(s1, suffixes)) {
                        if (isLowercase(s1)) {
                            Config.warn("Skipping non-lowercase path: " + s1);
                        } else {
                            list.add(s1);
                        }
                    }
                }
            }

            zipfile.close();
            return list.toArray(new String[0]);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            return new String[0];
        }
    }

    private static boolean isLowercase(String str) {
        return !str.equals(str.toLowerCase(Locale.ROOT));
    }

    public static Properties readProperties(String path, String module) {
        ResourceLocation resourcelocation = new ResourceLocation(path);

        try {
            InputStream inputstream = Config.getResourceStream(resourcelocation);

            if (inputstream == null) {
                return null;
            } else {
                Properties properties = new PropertiesOrdered();
                properties.load(inputstream);
                inputstream.close();
                Config.dbg(module + ": Loading " + path);
                return properties;
            }
        } catch (FileNotFoundException filenotfoundexception) {
            return null;
        } catch (IOException ioexception) {
            Config.warn(module + ": Error reading " + path);
            return null;
        }
    }

    public static Properties readProperties(InputStream in, String module) {
        if (in == null) {
            return null;
        } else {
            try {
                Properties properties = new PropertiesOrdered();
                properties.load(in);
                in.close();
                return properties;
            } catch (IOException ioexception) {
                return null;
            }
        }
    }
}
