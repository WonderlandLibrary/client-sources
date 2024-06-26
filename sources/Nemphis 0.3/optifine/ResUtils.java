/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.StrUtils;

public class ResUtils {
    public static String[] collectFiles(String prefix, String suffix) {
        return ResUtils.collectFiles(new String[]{prefix}, new String[]{suffix});
    }

    public static String[] collectFiles(String[] prefixes, String[] suffixes) {
        LinkedHashSet<String> setPaths = new LinkedHashSet<String>();
        IResourcePack[] rps = Config.getResourcePacks();
        int paths = 0;
        while (paths < rps.length) {
            IResourcePack rp = rps[paths];
            String[] ps = ResUtils.collectFiles(rp, prefixes, suffixes, null);
            setPaths.addAll(Arrays.asList(ps));
            ++paths;
        }
        String[] var7 = setPaths.toArray(new String[setPaths.size()]);
        return var7;
    }

    public static String[] collectFiles(IResourcePack rp, String prefix, String suffix, String[] defaultPaths) {
        return ResUtils.collectFiles(rp, new String[]{prefix}, new String[]{suffix}, defaultPaths);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes) {
        return ResUtils.collectFiles(rp, prefixes, suffixes, null);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
        if (rp instanceof DefaultResourcePack) {
            return ResUtils.collectFilesFixed(rp, defaultPaths);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            return new String[0];
        }
        AbstractResourcePack arp = (AbstractResourcePack)rp;
        File tpFile = arp.resourcePackFile;
        return tpFile == null ? new String[]{} : (tpFile.isDirectory() ? ResUtils.collectFilesFolder(tpFile, "", prefixes, suffixes) : (tpFile.isFile() ? ResUtils.collectFilesZIP(tpFile, prefixes, suffixes) : new String[]{}));
    }

    private static String[] collectFilesFixed(IResourcePack rp, String[] paths) {
        if (paths == null) {
            return new String[0];
        }
        ArrayList<String> list = new ArrayList<String>();
        int pathArr = 0;
        while (pathArr < paths.length) {
            String path = paths[pathArr];
            ResourceLocation loc = new ResourceLocation(path);
            if (rp.resourceExists(loc)) {
                list.add(path);
            }
            ++pathArr;
        }
        String[] var6 = list.toArray(new String[list.size()]);
        return var6;
    }

    private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
        ArrayList<String> list = new ArrayList<String>();
        String prefixAssets = "assets/minecraft/";
        File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        int names = 0;
        while (names < files.length) {
            String dirPath;
            File file = files[names];
            if (file.isFile()) {
                dirPath = String.valueOf(basePath) + file.getName();
                if (dirPath.startsWith(prefixAssets) && StrUtils.startsWith(dirPath = dirPath.substring(prefixAssets.length()), prefixes) && StrUtils.endsWith(dirPath, suffixes)) {
                    list.add(dirPath);
                }
            } else if (file.isDirectory()) {
                dirPath = String.valueOf(basePath) + file.getName() + "/";
                String[] names1 = ResUtils.collectFilesFolder(file, dirPath, prefixes, suffixes);
                int n = 0;
                while (n < names1.length) {
                    String name = names1[n];
                    list.add(name);
                    ++n;
                }
            }
            ++names;
        }
        String[] var13 = list.toArray(new String[list.size()]);
        return var13;
    }

    private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
        ArrayList<String> list = new ArrayList<String>();
        String prefixAssets = "assets/minecraft/";
        try {
            ZipFile e = new ZipFile(tpFile);
            Enumeration<? extends ZipEntry> en = e.entries();
            while (en.hasMoreElements()) {
                ZipEntry names = en.nextElement();
                String name = names.getName();
                if (!name.startsWith(prefixAssets) || !StrUtils.startsWith(name = name.substring(prefixAssets.length()), prefixes) || !StrUtils.endsWith(name, suffixes)) continue;
                list.add(name);
            }
            e.close();
            String[] names1 = list.toArray(new String[list.size()]);
            return names1;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }
}

