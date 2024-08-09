/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.optifine.Config;
import net.optifine.shaders.IShaderPack;
import net.optifine.util.StrUtils;

public class ShaderPackZip
implements IShaderPack {
    protected File packFile;
    protected ZipFile packZipFile;
    protected String baseFolder;

    public ShaderPackZip(String string, File file) {
        this.packFile = file;
        this.packZipFile = null;
        this.baseFolder = "";
    }

    @Override
    public void close() {
        if (this.packZipFile != null) {
            try {
                this.packZipFile.close();
            } catch (Exception exception) {
                // empty catch block
            }
            this.packZipFile = null;
        }
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        try {
            ZipEntry zipEntry;
            String string2;
            if (this.packZipFile == null) {
                this.packZipFile = new ZipFile(this.packFile);
                this.baseFolder = this.detectBaseFolder(this.packZipFile);
            }
            if ((string2 = StrUtils.removePrefix(string, "/")).contains("..")) {
                string2 = this.resolveRelative(string2);
            }
            return (zipEntry = this.packZipFile.getEntry(this.baseFolder + string2)) == null ? null : this.packZipFile.getInputStream(zipEntry);
        } catch (Exception exception) {
            return null;
        }
    }

    private String resolveRelative(String string) {
        ArrayDeque<String> arrayDeque = new ArrayDeque<String>();
        String[] stringArray = Config.tokenize(string, "/");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (string2.equals("..")) {
                if (arrayDeque.isEmpty()) {
                    return "";
                }
                arrayDeque.removeLast();
                continue;
            }
            arrayDeque.add(string2);
        }
        return Joiner.on('/').join(arrayDeque);
    }

    private String detectBaseFolder(ZipFile zipFile) {
        ZipEntry zipEntry = zipFile.getEntry("shaders/");
        if (zipEntry != null && zipEntry.isDirectory()) {
            return "";
        }
        Pattern pattern = Pattern.compile("([^/]+/)shaders/");
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            String string;
            ZipEntry zipEntry2 = enumeration.nextElement();
            String string2 = zipEntry2.getName();
            Matcher matcher = pattern.matcher(string2);
            if (!matcher.matches() || (string = matcher.group(1)) == null) continue;
            if (string.equals("shaders/")) {
                return "";
            }
            return string;
        }
        return "";
    }

    @Override
    public boolean hasDirectory(String string) {
        try {
            String string2;
            ZipEntry zipEntry;
            if (this.packZipFile == null) {
                this.packZipFile = new ZipFile(this.packFile);
                this.baseFolder = this.detectBaseFolder(this.packZipFile);
            }
            return (zipEntry = this.packZipFile.getEntry(this.baseFolder + (string2 = StrUtils.removePrefix(string, "/")))) != null;
        } catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public String getName() {
        return this.packFile.getName();
    }
}

