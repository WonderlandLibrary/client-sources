/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.optifine.shaders.IShaderPack;
import net.optifine.util.StrUtils;

public class ShaderPackFolder
implements IShaderPack {
    protected File packFile;

    public ShaderPackFolder(String string, File file) {
        this.packFile = file;
    }

    @Override
    public void close() {
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        try {
            String string2 = StrUtils.removePrefixSuffix(string, "/", "/");
            File file = new File(this.packFile, string2);
            return !file.exists() ? null : new BufferedInputStream(new FileInputStream(file));
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean hasDirectory(String string) {
        File file = new File(this.packFile, string.substring(1));
        if (!file.exists()) {
            return true;
        }
        return file.isDirectory();
    }

    @Override
    public String getName() {
        return this.packFile.getName();
    }
}

