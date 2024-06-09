/*
 * Decompiled with CFR 0.145.
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
        for (int paths = 0; paths < rps.length; ++paths) {
            IResourcePack rp2 = rps[paths];
            String[] ps2 = ResUtils.collectFiles(rp2, prefixes, suffixes, null);
            setPaths.addAll(Arrays.asList(ps2));
        }
        String[] var7 = setPaths.toArray(new String[setPaths.size()]);
        return var7;
    }

    public static String[] collectFiles(IResourcePack rp2, String prefix, String suffix, String[] defaultPaths) {
        return ResUtils.collectFiles(rp2, new String[]{prefix}, new String[]{suffix}, defaultPaths);
    }

    public static String[] collectFiles(IResourcePack rp2, String[] prefixes, String[] suffixes) {
        return ResUtils.collectFiles(rp2, prefixes, suffixes, null);
    }

    public static String[] collectFiles(IResourcePack rp2, String[] prefixes, String[] suffixes, String[] defaultPaths) {
        if (rp2 instanceof DefaultResourcePack) {
            return ResUtils.collectFilesFixed(rp2, defaultPaths);
        }
        if (!(rp2 instanceof AbstractResourcePack)) {
            return new String[0];
        }
        AbstractResourcePack arp = (AbstractResourcePack)rp2;
        File tpFile = arp.resourcePackFile;
        return tpFile == null ? new String[0] : (tpFile.isDirectory() ? ResUtils.collectFilesFolder(tpFile, "", prefixes, suffixes) : (tpFile.isFile() ? ResUtils.collectFilesZIP(tpFile, prefixes, suffixes) : new String[0]));
    }

    private static String[] collectFilesFixed(IResourcePack rp2, String[] paths) {
        if (paths == null) {
            return new String[0];
        }
        ArrayList<String> list = new ArrayList<String>();
        for (int pathArr = 0; pathArr < paths.length; ++pathArr) {
            String path = paths[pathArr];
            ResourceLocation loc = new ResourceLocation(path);
            if (!rp2.resourceExists(loc)) continue;
            list.add(path);
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
        for (int names = 0; names < files.length; ++names) {
            String dirPath;
            File file = files[names];
            if (file.isFile()) {
                dirPath = String.valueOf(basePath) + file.getName();
                if (!dirPath.startsWith(prefixAssets) || !StrUtils.startsWith(dirPath = dirPath.substring(prefixAssets.length()), prefixes) || !StrUtils.endsWith(dirPath, suffixes)) continue;
                list.add(dirPath);
                continue;
            }
            if (!file.isDirectory()) continue;
            dirPath = String.valueOf(basePath) + file.getName() + "/";
            String[] names1 = ResUtils.collectFilesFolder(file, dirPath, prefixes, suffixes);
            for (int n2 = 0; n2 < names1.length; ++n2) {
                String name = names1[n2];
                list.add(name);
            }
        }
        String[] var13 = list.toArray(new String[list.size()]);
        return var13;
    }

    private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
        ArrayList<String> list = new ArrayList<String>();
        String prefixAssets = "assets/minecraft/";
        try {
            ZipFile e2 = new ZipFile(tpFile);
            Enumeration<? extends ZipEntry> en2 = e2.entries();
            while (en2.hasMoreElements()) {
                ZipEntry names = en2.nextElement();
                String name = names.getName();
                if (!name.startsWith(prefixAssets) || !StrUtils.startsWith(name = name.substring(prefixAssets.length()), prefixes) || !StrUtils.endsWith(name, suffixes)) continue;
                list.add(name);
            }
            e2.close();
            String[] names1 = list.toArray(new String[list.size()]);
            return names1;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }
}

