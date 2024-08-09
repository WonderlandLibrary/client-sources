/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.mac;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.FileUtils;
import com.sun.jna.ptr.PointerByReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MacFileUtils
extends FileUtils {
    public boolean hasTrash() {
        return false;
    }

    public void moveToTrash(File[] fileArray) throws IOException {
        File file = new File(System.getProperty("user.home"));
        File file2 = new File(file, ".Trash");
        if (!file2.exists()) {
            throw new IOException("The Trash was not found in its expected location (" + file2 + ")");
        }
        ArrayList<File> arrayList = new ArrayList<File>();
        for (int i = 0; i < fileArray.length; ++i) {
            File file3 = fileArray[i];
            if (FileManager.INSTANCE.FSPathMoveObjectToTrashSync(file3.getAbsolutePath(), null, 0) == 0) continue;
            arrayList.add(file3);
        }
        if (arrayList.size() > 0) {
            throw new IOException("The following files could not be trashed: " + arrayList);
        }
    }

    public static interface FileManager
    extends Library {
        public static final int kFSFileOperationDefaultOptions = 0;
        public static final int kFSFileOperationsOverwrite = 1;
        public static final int kFSFileOperationsSkipSourcePermissionErrors = 2;
        public static final int kFSFileOperationsDoNotMoveAcrossVolumes = 4;
        public static final int kFSFileOperationsSkipPreflight = 8;
        public static final FileManager INSTANCE = Native.loadLibrary("CoreServices", FileManager.class);

        public int FSPathMoveObjectToTrashSync(String var1, PointerByReference var2, int var3);
    }
}

