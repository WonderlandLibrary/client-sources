/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackFileNotFoundException;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Util;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderPack
extends ResourcePack {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final boolean OS_WINDOWS = Util.getOSType() == Util.OS.WINDOWS;
    private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');

    public FolderPack(File file) {
        super(file);
    }

    public static boolean validatePath(File file, String string) throws IOException {
        String string2 = file.getCanonicalPath();
        if (OS_WINDOWS) {
            string2 = BACKSLASH_MATCHER.replaceFrom((CharSequence)string2, '/');
        }
        return string2.endsWith(string);
    }

    @Override
    protected InputStream getInputStream(String string) throws IOException {
        File file = this.getFile(string);
        if (file == null) {
            throw new ResourcePackFileNotFoundException(this.file, string);
        }
        return new FileInputStream(file);
    }

    @Override
    protected boolean resourceExists(String string) {
        return this.getFile(string) != null;
    }

    @Nullable
    private File getFile(String string) {
        try {
            File file = new File(this.file, string);
            if (file.isFile() && FolderPack.validatePath(file, string)) {
                return file;
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType resourcePackType) {
        HashSet<String> hashSet = Sets.newHashSet();
        File file = new File(this.file, resourcePackType.getDirectoryName());
        File[] fileArray = file.listFiles(DirectoryFileFilter.DIRECTORY);
        if (fileArray != null) {
            for (File file2 : fileArray) {
                String string = FolderPack.getRelativeString(file, file2);
                if (string.equals(string.toLowerCase(Locale.ROOT))) {
                    hashSet.add(string.substring(0, string.length() - 1));
                    continue;
                }
                this.onIgnoreNonLowercaseNamespace(string);
            }
        }
        return hashSet;
    }

    @Override
    public void close() {
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType resourcePackType, String string, String string2, int n, Predicate<String> predicate) {
        File file = new File(this.file, resourcePackType.getDirectoryName());
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        this.func_199546_a(new File(new File(file, string), string2), n, string, arrayList, string2 + "/", predicate);
        return arrayList;
    }

    private void func_199546_a(File file, int n, String string, List<ResourceLocation> list, String string2, Predicate<String> predicate) {
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File file2 : fileArray) {
                if (file2.isDirectory()) {
                    if (n <= 0) continue;
                    this.func_199546_a(file2, n - 1, string, list, string2 + file2.getName() + "/", predicate);
                    continue;
                }
                if (file2.getName().endsWith(".mcmeta") || !predicate.test(file2.getName())) continue;
                try {
                    list.add(new ResourceLocation(string, string2 + file2.getName()));
                } catch (ResourceLocationException resourceLocationException) {
                    LOGGER.error(resourceLocationException.getMessage());
                }
            }
        }
    }
}

