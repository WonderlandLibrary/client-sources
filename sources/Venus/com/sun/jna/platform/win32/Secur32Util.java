/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.Sspi;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.ptr.IntByReference;
import java.util.ArrayList;

public abstract class Secur32Util {
    public static String getUserNameEx(int n) {
        char[] cArray = new char[128];
        IntByReference intByReference = new IntByReference(cArray.length);
        boolean bl = Secur32.INSTANCE.GetUserNameEx(n, cArray, intByReference);
        if (!bl) {
            int n2 = Kernel32.INSTANCE.GetLastError();
            switch (n2) {
                case 234: {
                    cArray = new char[intByReference.getValue() + 1];
                    break;
                }
                default: {
                    throw new Win32Exception(Native.getLastError());
                }
            }
            bl = Secur32.INSTANCE.GetUserNameEx(n, cArray, intByReference);
        }
        if (!bl) {
            throw new Win32Exception(Native.getLastError());
        }
        return Native.toString(cArray);
    }

    public static SecurityPackage[] getSecurityPackages() {
        IntByReference intByReference = new IntByReference();
        Sspi.PSecPkgInfo.ByReference byReference = new Sspi.PSecPkgInfo.ByReference();
        int n = Secur32.INSTANCE.EnumerateSecurityPackages(intByReference, byReference);
        if (0 != n) {
            throw new Win32Exception(n);
        }
        Sspi.SecPkgInfo.ByReference[] byReferenceArray = byReference.toArray(intByReference.getValue());
        ArrayList<SecurityPackage> arrayList = new ArrayList<SecurityPackage>(intByReference.getValue());
        for (Sspi.SecPkgInfo.ByReference byReference2 : byReferenceArray) {
            SecurityPackage securityPackage = new SecurityPackage();
            securityPackage.name = byReference2.Name.toString();
            securityPackage.comment = byReference2.Comment.toString();
            arrayList.add(securityPackage);
        }
        n = Secur32.INSTANCE.FreeContextBuffer(byReference.pPkgInfo.getPointer());
        if (0 != n) {
            throw new Win32Exception(n);
        }
        return arrayList.toArray(new SecurityPackage[0]);
    }

    public static class SecurityPackage {
        public String name;
        public String comment;
    }
}

