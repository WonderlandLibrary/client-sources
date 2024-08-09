/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform;

import com.sun.jna.platform.mac.MacFileUtils;
import com.sun.jna.platform.win32.W32FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class FileUtils {
    public boolean hasTrash() {
        return true;
    }

    public abstract void moveToTrash(File[] var1) throws IOException;

    public static FileUtils getInstance() {
        return Holder.INSTANCE;
    }

    static class 1 {
    }

    private static class DefaultFileUtils
    extends FileUtils {
        private DefaultFileUtils() {
        }

        private File getTrashDirectory() {
            File file;
            File file2 = new File(System.getProperty("user.home"));
            File file3 = new File(file2, ".Trash");
            if (!(file3.exists() || (file3 = new File(file2, "Trash")).exists() || !(file = new File(file2, "Desktop")).exists() || (file3 = new File(file, ".Trash")).exists() || (file3 = new File(file, "Trash")).exists())) {
                file3 = new File(System.getProperty("fileutils.trash", "Trash"));
            }
            return file3;
        }

        public boolean hasTrash() {
            return this.getTrashDirectory().exists();
        }

        public void moveToTrash(File[] fileArray) throws IOException {
            File file = this.getTrashDirectory();
            if (!file.exists()) {
                throw new IOException("No trash location found (define fileutils.trash to be the path to the trash)");
            }
            ArrayList<File> arrayList = new ArrayList<File>();
            for (int i = 0; i < fileArray.length; ++i) {
                File file2 = fileArray[i];
                File file3 = new File(file, file2.getName());
                if (file2.renameTo(file3)) continue;
                arrayList.add(file2);
            }
            if (arrayList.size() > 0) {
                throw new IOException("The following files could not be trashed: " + arrayList);
            }
        }

        DefaultFileUtils(1 var1_1) {
            this();
        }
    }

    private static class Holder {
        public static final FileUtils INSTANCE;

        private Holder() {
        }

        static {
            String string = System.getProperty("os.name");
            INSTANCE = string.startsWith("Windows") ? new W32FileUtils() : (string.startsWith("Mac") ? new MacFileUtils() : new DefaultFileUtils(null));
        }
    }
}

