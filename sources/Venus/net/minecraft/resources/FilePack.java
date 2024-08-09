/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackFileNotFoundException;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class FilePack
extends ResourcePack {
    public static final Splitter PATH_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
    private ZipFile zipFile;

    public FilePack(File file) {
        super(file);
    }

    private ZipFile getResourcePackZipFile() throws IOException {
        if (this.zipFile == null) {
            this.zipFile = new ZipFile(this.file);
        }
        return this.zipFile;
    }

    @Override
    protected InputStream getInputStream(String string) throws IOException {
        ZipFile zipFile = this.getResourcePackZipFile();
        ZipEntry zipEntry = zipFile.getEntry(string);
        if (zipEntry == null) {
            throw new ResourcePackFileNotFoundException(this.file, string);
        }
        return zipFile.getInputStream(zipEntry);
    }

    @Override
    public boolean resourceExists(String string) {
        try {
            return this.getResourcePackZipFile().getEntry(string) != null;
        } catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType resourcePackType) {
        ZipFile zipFile;
        try {
            zipFile = this.getResourcePackZipFile();
        } catch (IOException iOException) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        HashSet<String> hashSet = Sets.newHashSet();
        while (enumeration.hasMoreElements()) {
            ArrayList<String> arrayList;
            ZipEntry zipEntry = enumeration.nextElement();
            String string = zipEntry.getName();
            if (!string.startsWith(resourcePackType.getDirectoryName() + "/") || (arrayList = Lists.newArrayList(PATH_SPLITTER.split(string))).size() <= 1) continue;
            String string2 = (String)arrayList.get(1);
            if (string2.equals(string2.toLowerCase(Locale.ROOT))) {
                hashSet.add(string2);
                continue;
            }
            this.onIgnoreNonLowercaseNamespace(string2);
        }
        return hashSet;
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    @Override
    public void close() {
        if (this.zipFile != null) {
            IOUtils.closeQuietly((Closeable)this.zipFile);
            this.zipFile = null;
        }
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType resourcePackType, String string, String string2, int n, Predicate<String> predicate) {
        ZipFile zipFile;
        try {
            zipFile = this.getResourcePackZipFile();
        } catch (IOException iOException) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        String string3 = resourcePackType.getDirectoryName() + "/" + string + "/";
        String string4 = string3 + string2 + "/";
        while (enumeration.hasMoreElements()) {
            String string5;
            String[] stringArray;
            String string6;
            ZipEntry zipEntry = enumeration.nextElement();
            if (zipEntry.isDirectory() || (string6 = zipEntry.getName()).endsWith(".mcmeta") || !string6.startsWith(string4) || (stringArray = (string5 = string6.substring(string3.length())).split("/")).length < n + 1 || !predicate.test(stringArray[stringArray.length - 1])) continue;
            arrayList.add(new ResourceLocation(string, string5));
        }
        return arrayList;
    }
}

