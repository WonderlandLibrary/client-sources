/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinNT;

public abstract class Ole32Util {
    public static Guid.GUID getGUIDFromString(String string) {
        Guid.GUID.ByReference byReference = new Guid.GUID.ByReference();
        WinNT.HRESULT hRESULT = Ole32.INSTANCE.IIDFromString(string, byReference);
        if (!hRESULT.equals(W32Errors.S_OK)) {
            throw new RuntimeException(hRESULT.toString());
        }
        return byReference;
    }

    public static String getStringFromGUID(Guid.GUID gUID) {
        int n;
        char[] cArray;
        Guid.GUID.ByReference byReference = new Guid.GUID.ByReference(gUID.getPointer());
        int n2 = Ole32.INSTANCE.StringFromGUID2(byReference, cArray = new char[n = 39], n);
        if (n2 == 0) {
            throw new RuntimeException("StringFromGUID2");
        }
        cArray[n2 - 1] = '\u0000';
        return Native.toString(cArray);
    }

    public static Guid.GUID generateGUID() {
        Guid.GUID.ByReference byReference = new Guid.GUID.ByReference();
        WinNT.HRESULT hRESULT = Ole32.INSTANCE.CoCreateGuid(byReference);
        if (!hRESULT.equals(W32Errors.S_OK)) {
            throw new RuntimeException(hRESULT.toString());
        }
        return byReference;
    }
}

