/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.util.Objects;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.CommonsCompressAction;
import org.apache.logging.log4j.core.appender.rolling.action.GzCompressAction;
import org.apache.logging.log4j.core.appender.rolling.action.ZipCompressAction;

public enum FileExtension {
    ZIP(".zip"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new ZipCompressAction(this.source(string), this.target(string2), bl, n);
        }
    }
    ,
    GZ(".gz"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new GzCompressAction(this.source(string), this.target(string2), bl);
        }
    }
    ,
    BZIP2(".bz2"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new CommonsCompressAction("bzip2", this.source(string), this.target(string2), bl);
        }
    }
    ,
    DEFLATE(".deflate"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new CommonsCompressAction("deflate", this.source(string), this.target(string2), bl);
        }
    }
    ,
    PACK200(".pack200"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new CommonsCompressAction("pack200", this.source(string), this.target(string2), bl);
        }
    }
    ,
    XZ(".xz"){

        @Override
        Action createCompressAction(String string, String string2, boolean bl, int n) {
            return new CommonsCompressAction("xz", this.source(string), this.target(string2), bl);
        }
    };

    private final String extension;

    public static FileExtension lookup(String string) {
        for (FileExtension fileExtension : FileExtension.values()) {
            if (!fileExtension.isExtensionFor(string)) continue;
            return fileExtension;
        }
        return null;
    }

    public static FileExtension lookupForFile(String string) {
        for (FileExtension fileExtension : FileExtension.values()) {
            if (!string.endsWith(fileExtension.extension)) continue;
            return fileExtension;
        }
        return null;
    }

    private FileExtension(String string2) {
        Objects.requireNonNull(string2, "extension");
        this.extension = string2;
    }

    abstract Action createCompressAction(String var1, String var2, boolean var3, int var4);

    String getExtension() {
        return this.extension;
    }

    boolean isExtensionFor(String string) {
        return string.endsWith(this.extension);
    }

    int length() {
        return this.extension.length();
    }

    File source(String string) {
        return new File(string);
    }

    File target(String string) {
        return new File(string);
    }

    FileExtension(String string2, 1 var4_4) {
        this(string2);
    }
}

