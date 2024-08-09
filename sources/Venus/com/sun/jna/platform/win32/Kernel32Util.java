/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;

public abstract class Kernel32Util
implements WinDef {
    public static String getComputerName() {
        char[] cArray = new char[WinBase.MAX_COMPUTERNAME_LENGTH + 1];
        IntByReference intByReference = new IntByReference(cArray.length);
        if (!Kernel32.INSTANCE.GetComputerName(cArray, intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Native.toString(cArray);
    }

    public static String formatMessageFromHR(WinNT.HRESULT hRESULT) {
        PointerByReference pointerByReference = new PointerByReference();
        if (0 == Kernel32.INSTANCE.FormatMessage(4864, null, hRESULT.intValue(), 0, pointerByReference, 0, null)) {
            throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
        }
        String string = pointerByReference.getValue().getString(0L, !Boolean.getBoolean("w32.ascii"));
        Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
        return string.trim();
    }

    public static String formatMessageFromLastErrorCode(int n) {
        return Kernel32Util.formatMessageFromHR(W32Errors.HRESULT_FROM_WIN32(n));
    }

    public static String getTempPath() {
        WinDef.DWORD dWORD = new WinDef.DWORD(260L);
        char[] cArray = new char[dWORD.intValue()];
        if (Kernel32.INSTANCE.GetTempPath(dWORD, cArray).intValue() == 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Native.toString(cArray);
    }

    public static void deleteFile(String string) {
        if (!Kernel32.INSTANCE.DeleteFile(string)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
    }

    public static String[] getLogicalDriveStrings() {
        WinDef.DWORD dWORD = Kernel32.INSTANCE.GetLogicalDriveStrings(new WinDef.DWORD(0L), null);
        if (dWORD.intValue() <= 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        char[] cArray = new char[dWORD.intValue()];
        if ((dWORD = Kernel32.INSTANCE.GetLogicalDriveStrings(dWORD, cArray)).intValue() <= 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = "";
        for (int i = 0; i < cArray.length - 1; ++i) {
            if (cArray[i] == '\u0000') {
                arrayList.add(string);
                string = "";
                continue;
            }
            string = string + cArray[i];
        }
        return arrayList.toArray(new String[0]);
    }

    public static int getFileAttributes(String string) {
        int n = Kernel32.INSTANCE.GetFileAttributes(string);
        if (n == -1) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return n;
    }
}

