/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public abstract class Shell32Util {
    public static String getFolderPath(WinDef.HWND hWND, int n, WinDef.DWORD dWORD) {
        char[] cArray = new char[260];
        WinNT.HRESULT hRESULT = Shell32.INSTANCE.SHGetFolderPath(hWND, n, null, dWORD, cArray);
        if (!hRESULT.equals(W32Errors.S_OK)) {
            throw new Win32Exception(hRESULT);
        }
        return Native.toString(cArray);
    }

    public static String getFolderPath(int n) {
        return Shell32Util.getFolderPath(null, n, ShlObj.SHGFP_TYPE_CURRENT);
    }
}

