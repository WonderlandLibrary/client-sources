/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public enum PathProperties {
    FILE_EXISTING("file.existing"){

        @Override
        boolean accept(Path path) {
            return Files.isRegularFile(path, new LinkOption[0]);
        }
    }
    ,
    DIRECTORY_EXISTING("directory.existing"){

        @Override
        boolean accept(Path path) {
            return Files.isDirectory(path, new LinkOption[0]);
        }
    }
    ,
    NOT_EXISTING("file.not.existing"){

        @Override
        boolean accept(Path path) {
            return Files.notExists(path, new LinkOption[0]);
        }
    }
    ,
    FILE_OVERWRITABLE("file.overwritable"){

        @Override
        boolean accept(Path path) {
            return FILE_EXISTING.accept(path) && WRITABLE.accept(path);
        }
    }
    ,
    READABLE("file.readable"){

        @Override
        boolean accept(Path path) {
            return Files.isReadable(path);
        }
    }
    ,
    WRITABLE("file.writable"){

        @Override
        boolean accept(Path path) {
            return Files.isWritable(path);
        }
    };

    private final String messageKey;

    private PathProperties(String messageKey) {
        this.messageKey = messageKey;
    }

    abstract boolean accept(Path var1);

    String getMessageKey() {
        return this.messageKey;
    }
}

