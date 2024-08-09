/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.WString;
import com.sun.jna.platform.FileUtils;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShellAPI;
import java.io.File;
import java.io.IOException;

public class W32FileUtils
extends FileUtils {
    public boolean hasTrash() {
        return false;
    }

    public void moveToTrash(File[] fileArray) throws IOException {
        int n;
        Shell32 shell32 = Shell32.INSTANCE;
        ShellAPI.SHFILEOPSTRUCT sHFILEOPSTRUCT = new ShellAPI.SHFILEOPSTRUCT();
        sHFILEOPSTRUCT.wFunc = 3;
        String[] stringArray = new String[fileArray.length];
        for (n = 0; n < stringArray.length; ++n) {
            stringArray[n] = fileArray[n].getAbsolutePath();
        }
        sHFILEOPSTRUCT.pFrom = new WString(sHFILEOPSTRUCT.encodePaths(stringArray));
        sHFILEOPSTRUCT.fFlags = (short)1620;
        n = shell32.SHFileOperation(sHFILEOPSTRUCT);
        if (n != 0) {
            throw new IOException("Move to trash failed: " + sHFILEOPSTRUCT.pFrom + ": " + Kernel32Util.formatMessageFromLastErrorCode(n));
        }
        if (sHFILEOPSTRUCT.fAnyOperationsAborted) {
            throw new IOException("Move to trash aborted");
        }
    }
}

