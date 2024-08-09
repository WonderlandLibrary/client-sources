/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class Advapi32Util {
    public static String getUserName() {
        char[] cArray = new char[128];
        IntByReference intByReference = new IntByReference(cArray.length);
        boolean bl = Advapi32.INSTANCE.GetUserNameW(cArray, intByReference);
        if (!bl) {
            switch (Kernel32.INSTANCE.GetLastError()) {
                case 122: {
                    cArray = new char[intByReference.getValue()];
                    break;
                }
                default: {
                    throw new Win32Exception(Native.getLastError());
                }
            }
            bl = Advapi32.INSTANCE.GetUserNameW(cArray, intByReference);
        }
        if (!bl) {
            throw new Win32Exception(Native.getLastError());
        }
        return Native.toString(cArray);
    }

    public static Account getAccountByName(String string) {
        return Advapi32Util.getAccountByName(null, string);
    }

    public static Account getAccountByName(String string, String string2) {
        char[] cArray;
        IntByReference intByReference = new IntByReference(0);
        IntByReference intByReference2 = new IntByReference(0);
        PointerByReference pointerByReference = new PointerByReference();
        if (Advapi32.INSTANCE.LookupAccountName(string, string2, null, intByReference, null, intByReference2, pointerByReference)) {
            throw new RuntimeException("LookupAccountNameW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        int n = Kernel32.INSTANCE.GetLastError();
        if (intByReference.getValue() == 0 || n != 122) {
            throw new Win32Exception(n);
        }
        Memory memory = new Memory(intByReference.getValue());
        WinNT.PSID pSID = new WinNT.PSID(memory);
        if (!Advapi32.INSTANCE.LookupAccountName(string, string2, pSID, intByReference, cArray = new char[intByReference2.getValue() + 1], intByReference2, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        Account account = new Account();
        account.accountType = pointerByReference.getPointer().getInt(0L);
        account.name = string2;
        String[] stringArray = string2.split("\\\\", 2);
        String[] stringArray2 = string2.split("@", 2);
        account.name = stringArray.length == 2 ? stringArray[0] : (stringArray2.length == 2 ? stringArray2[0] : string2);
        if (intByReference2.getValue() > 0) {
            account.domain = Native.toString(cArray);
            account.fqn = account.domain + "\\" + account.name;
        } else {
            account.fqn = account.name;
        }
        account.sid = pSID.getBytes();
        account.sidString = Advapi32Util.convertSidToStringSid(new WinNT.PSID(account.sid));
        return account;
    }

    public static Account getAccountBySid(WinNT.PSID pSID) {
        return Advapi32Util.getAccountBySid(null, pSID);
    }

    public static Account getAccountBySid(String string, WinNT.PSID pSID) {
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        PointerByReference pointerByReference = new PointerByReference();
        if (Advapi32.INSTANCE.LookupAccountSid(null, pSID, null, intByReference, null, intByReference2, pointerByReference)) {
            throw new RuntimeException("LookupAccountSidW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        int n = Kernel32.INSTANCE.GetLastError();
        if (intByReference.getValue() == 0 || n != 122) {
            throw new Win32Exception(n);
        }
        char[] cArray = new char[intByReference2.getValue()];
        char[] cArray2 = new char[intByReference.getValue()];
        if (!Advapi32.INSTANCE.LookupAccountSid(null, pSID, cArray2, intByReference, cArray, intByReference2, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        Account account = new Account();
        account.accountType = pointerByReference.getPointer().getInt(0L);
        account.name = Native.toString(cArray2);
        if (intByReference2.getValue() > 0) {
            account.domain = Native.toString(cArray);
            account.fqn = account.domain + "\\" + account.name;
        } else {
            account.fqn = account.name;
        }
        account.sid = pSID.getBytes();
        account.sidString = Advapi32Util.convertSidToStringSid(pSID);
        return account;
    }

    public static String convertSidToStringSid(WinNT.PSID pSID) {
        PointerByReference pointerByReference = new PointerByReference();
        if (!Advapi32.INSTANCE.ConvertSidToStringSid(pSID, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        String string = pointerByReference.getValue().getString(0L, false);
        Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
        return string;
    }

    public static byte[] convertStringSidToSid(String string) {
        WinNT.PSIDByReference pSIDByReference = new WinNT.PSIDByReference();
        if (!Advapi32.INSTANCE.ConvertStringSidToSid(string, pSIDByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return pSIDByReference.getValue().getBytes();
    }

    public static boolean isWellKnownSid(String string, int n) {
        WinNT.PSIDByReference pSIDByReference = new WinNT.PSIDByReference();
        if (!Advapi32.INSTANCE.ConvertStringSidToSid(string, pSIDByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Advapi32.INSTANCE.IsWellKnownSid(pSIDByReference.getValue(), n);
    }

    public static boolean isWellKnownSid(byte[] byArray, int n) {
        WinNT.PSID pSID = new WinNT.PSID(byArray);
        return Advapi32.INSTANCE.IsWellKnownSid(pSID, n);
    }

    public static Account getAccountBySid(String string) {
        return Advapi32Util.getAccountBySid(null, string);
    }

    public static Account getAccountBySid(String string, String string2) {
        return Advapi32Util.getAccountBySid(string, new WinNT.PSID(Advapi32Util.convertStringSidToSid(string2)));
    }

    public static Account[] getTokenGroups(WinNT.HANDLE hANDLE) {
        IntByReference intByReference = new IntByReference();
        if (Advapi32.INSTANCE.GetTokenInformation(hANDLE, 2, null, 0, intByReference)) {
            throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        int n = Kernel32.INSTANCE.GetLastError();
        if (n != 122) {
            throw new Win32Exception(n);
        }
        WinNT.TOKEN_GROUPS tOKEN_GROUPS = new WinNT.TOKEN_GROUPS(intByReference.getValue());
        if (!Advapi32.INSTANCE.GetTokenInformation(hANDLE, 2, tOKEN_GROUPS, intByReference.getValue(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        ArrayList<Account> arrayList = new ArrayList<Account>();
        for (WinNT.SID_AND_ATTRIBUTES sID_AND_ATTRIBUTES : tOKEN_GROUPS.getGroups()) {
            Account account = null;
            try {
                account = Advapi32Util.getAccountBySid(sID_AND_ATTRIBUTES.Sid);
            } catch (Exception exception) {
                account = new Account();
                account.sid = sID_AND_ATTRIBUTES.Sid.getBytes();
                account.name = account.sidString = Advapi32Util.convertSidToStringSid(sID_AND_ATTRIBUTES.Sid);
                account.fqn = account.sidString;
                account.accountType = 2;
            }
            arrayList.add(account);
        }
        return arrayList.toArray(new Account[0]);
    }

    public static Account getTokenAccount(WinNT.HANDLE hANDLE) {
        IntByReference intByReference = new IntByReference();
        if (Advapi32.INSTANCE.GetTokenInformation(hANDLE, 1, null, 0, intByReference)) {
            throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        int n = Kernel32.INSTANCE.GetLastError();
        if (n != 122) {
            throw new Win32Exception(n);
        }
        WinNT.TOKEN_USER tOKEN_USER = new WinNT.TOKEN_USER(intByReference.getValue());
        if (!Advapi32.INSTANCE.GetTokenInformation(hANDLE, 1, tOKEN_USER, intByReference.getValue(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Advapi32Util.getAccountBySid(tOKEN_USER.User.Sid);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Account[] getCurrentUserGroups() {
        WinNT.HANDLEByReference hANDLEByReference = new WinNT.HANDLEByReference();
        try {
            Object object;
            WinNT.HANDLE hANDLE = Kernel32.INSTANCE.GetCurrentThread();
            if (!Advapi32.INSTANCE.OpenThreadToken(hANDLE, 10, true, hANDLEByReference)) {
                if (1008 != Kernel32.INSTANCE.GetLastError()) {
                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                }
                object = Kernel32.INSTANCE.GetCurrentProcess();
                if (!Advapi32.INSTANCE.OpenProcessToken((WinNT.HANDLE)object, 10, hANDLEByReference)) {
                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                }
            }
            object = Advapi32Util.getTokenGroups(hANDLEByReference.getValue());
            return object;
        } finally {
            if (hANDLEByReference.getValue() != WinBase.INVALID_HANDLE_VALUE && !Kernel32.INSTANCE.CloseHandle(hANDLEByReference.getValue())) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
        }
    }

    public static boolean registryKeyExists(WinReg.HKEY hKEY, String string) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        switch (n) {
            case 0: {
                Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
                return false;
            }
            case 2: {
                return true;
            }
        }
        throw new Win32Exception(n);
    }

    public static boolean registryValueExists(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        try {
            switch (n) {
                case 0: {
                    break;
                }
                case 2: {
                    boolean bl = false;
                    return bl;
                }
                default: {
                    throw new Win32Exception(n);
                }
            }
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            switch (n) {
                case 0: 
                case 122: {
                    boolean bl = true;
                    return bl;
                }
                case 2: {
                    boolean bl = false;
                    return bl;
                }
            }
            throw new Win32Exception(n);
        } finally {
            if (hKEYByReference.getValue() != WinBase.INVALID_HANDLE_VALUE && (n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue())) != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String registryGetStringValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 1 && intByReference2.getValue() != 2) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ or REG_EXPAND_SZ");
            }
            char[] cArray = new char[intByReference.getValue()];
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, cArray, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            String string3 = Native.toString(cArray);
            return string3;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String registryGetExpandableStringValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 2) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ");
            }
            char[] cArray = new char[intByReference.getValue()];
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, cArray, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            String string3 = Native.toString(cArray);
            return string3;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String[] registryGetStringArray(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            String[] stringArray;
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 7) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ");
            }
            Memory memory = new Memory(intByReference.getValue());
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, memory, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            int n2 = 0;
            while ((long)n2 < memory.size()) {
                stringArray = memory.getString((long)n2, false);
                n2 += stringArray.length() * Native.WCHAR_SIZE;
                n2 += Native.WCHAR_SIZE;
                arrayList.add(stringArray);
            }
            stringArray = arrayList.toArray(new String[0]);
            return stringArray;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] registryGetBinaryValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 3) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_BINARY");
            }
            byte[] byArray = new byte[intByReference.getValue()];
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, byArray, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            byte[] byArray2 = byArray;
            return byArray2;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int registryGetIntValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 4) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_DWORD");
            }
            IntByReference intByReference3 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, intByReference3, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            int n2 = intByReference3.getValue();
            return n2;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long registryGetLongValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, (char[])null, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            if (intByReference2.getValue() != 11) {
                throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_QWORD");
            }
            LongByReference longByReference = new LongByReference();
            n = Advapi32.INSTANCE.RegQueryValueEx(hKEYByReference.getValue(), string2, 0, intByReference2, longByReference, intByReference);
            if (n != 0 && n != 122) {
                throw new Win32Exception(n);
            }
            long l = longByReference.getValue();
            return l;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static boolean registryCreateKey(WinReg.HKEY hKEY, String string) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        IntByReference intByReference = new IntByReference();
        int n = Advapi32.INSTANCE.RegCreateKeyEx(hKEY, string, 0, null, 0, 131097, null, hKEYByReference, intByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
        if (n != 0) {
            throw new Win32Exception(n);
        }
        return 1 == intByReference.getValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean registryCreateKey(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 4, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            boolean bl = Advapi32Util.registryCreateKey(hKEYByReference.getValue(), string2);
            return bl;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registrySetIntValue(WinReg.HKEY hKEY, String string, int n) {
        byte[] byArray = new byte[]{(byte)(n & 0xFF), (byte)(n >> 8 & 0xFF), (byte)(n >> 16 & 0xFF), (byte)(n >> 24 & 0xFF)};
        int n2 = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 4, byArray, 4);
        if (n2 != 0) {
            throw new Win32Exception(n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetIntValue(WinReg.HKEY hKEY, String string, String string2, int n) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n2 = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n2 != 0) {
            throw new Win32Exception(n2);
        }
        try {
            Advapi32Util.registrySetIntValue(hKEYByReference.getValue(), string2, n);
        } finally {
            n2 = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n2 != 0) {
                throw new Win32Exception(n2);
            }
        }
    }

    public static void registrySetLongValue(WinReg.HKEY hKEY, String string, long l) {
        byte[] byArray = new byte[]{(byte)(l & 0xFFL), (byte)(l >> 8 & 0xFFL), (byte)(l >> 16 & 0xFFL), (byte)(l >> 24 & 0xFFL), (byte)(l >> 32 & 0xFFL), (byte)(l >> 40 & 0xFFL), (byte)(l >> 48 & 0xFFL), (byte)(l >> 56 & 0xFFL)};
        int n = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 11, byArray, 8);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetLongValue(WinReg.HKEY hKEY, String string, String string2, long l) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registrySetLongValue(hKEYByReference.getValue(), string2, l);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registrySetStringValue(WinReg.HKEY hKEY, String string, String string2) {
        char[] cArray = Native.toCharArray(string2);
        int n = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 1, cArray, cArray.length * Native.WCHAR_SIZE);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetStringValue(WinReg.HKEY hKEY, String string, String string2, String string3) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registrySetStringValue(hKEYByReference.getValue(), string2, string3);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registrySetExpandableStringValue(WinReg.HKEY hKEY, String string, String string2) {
        char[] cArray = Native.toCharArray(string2);
        int n = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 2, cArray, cArray.length * Native.WCHAR_SIZE);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetExpandableStringValue(WinReg.HKEY hKEY, String string, String string2, String string3) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registrySetExpandableStringValue(hKEYByReference.getValue(), string2, string3);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registrySetStringArray(WinReg.HKEY hKEY, String string, String[] stringArray) {
        int n = 0;
        for (String string2 : stringArray) {
            n += string2.length() * Native.WCHAR_SIZE;
            n += Native.WCHAR_SIZE;
        }
        int n2 = 0;
        Memory memory = new Memory(n);
        for (String string3 : stringArray) {
            memory.setString((long)n2, string3, false);
            n2 += string3.length() * Native.WCHAR_SIZE;
            n2 += Native.WCHAR_SIZE;
        }
        int n3 = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 7, memory.getByteArray(0L, n), n);
        if (n3 != 0) {
            throw new Win32Exception(n3);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetStringArray(WinReg.HKEY hKEY, String string, String string2, String[] stringArray) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registrySetStringArray(hKEYByReference.getValue(), string2, stringArray);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registrySetBinaryValue(WinReg.HKEY hKEY, String string, byte[] byArray) {
        int n = Advapi32.INSTANCE.RegSetValueEx(hKEY, string, 0, 3, byArray, byArray.length);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registrySetBinaryValue(WinReg.HKEY hKEY, String string, String string2, byte[] byArray) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registrySetBinaryValue(hKEYByReference.getValue(), string2, byArray);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registryDeleteKey(WinReg.HKEY hKEY, String string) {
        int n = Advapi32.INSTANCE.RegDeleteKey(hKEY, string);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registryDeleteKey(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registryDeleteKey(hKEYByReference.getValue(), string2);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static void registryDeleteValue(WinReg.HKEY hKEY, String string) {
        int n = Advapi32.INSTANCE.RegDeleteValue(hKEY, string);
        if (n != 0) {
            throw new Win32Exception(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registryDeleteValue(WinReg.HKEY hKEY, String string, String string2) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131103, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            Advapi32Util.registryDeleteValue(hKEYByReference.getValue(), string2);
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static String[] registryGetKeys(WinReg.HKEY hKEY) {
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        int n = Advapi32.INSTANCE.RegQueryInfoKey(hKEY, null, null, null, intByReference, intByReference2, null, null, null, null, null, null);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        ArrayList<String> arrayList = new ArrayList<String>(intByReference.getValue());
        char[] cArray = new char[intByReference2.getValue() + 1];
        for (int i = 0; i < intByReference.getValue(); ++i) {
            IntByReference intByReference3 = new IntByReference(intByReference2.getValue() + 1);
            n = Advapi32.INSTANCE.RegEnumKeyEx(hKEY, i, cArray, intByReference3, null, null, null, null);
            if (n != 0) {
                throw new Win32Exception(n);
            }
            arrayList.add(Native.toString(cArray));
        }
        return arrayList.toArray(new String[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String[] registryGetKeys(WinReg.HKEY hKEY, String string) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            String[] stringArray = Advapi32Util.registryGetKeys(hKEYByReference.getValue());
            return stringArray;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static TreeMap<String, Object> registryGetValues(WinReg.HKEY hKEY) {
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        IntByReference intByReference3 = new IntByReference();
        int n = Advapi32.INSTANCE.RegQueryInfoKey(hKEY, null, null, null, null, null, null, intByReference, intByReference2, intByReference3, null, null);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        char[] cArray = new char[intByReference2.getValue() + 1];
        byte[] byArray = new byte[intByReference3.getValue()];
        block7: for (int i = 0; i < intByReference.getValue(); ++i) {
            IntByReference intByReference4;
            IntByReference intByReference5;
            IntByReference intByReference6 = new IntByReference(intByReference2.getValue() + 1);
            n = Advapi32.INSTANCE.RegEnumValue(hKEY, i, cArray, intByReference6, null, intByReference5 = new IntByReference(), byArray, intByReference4 = new IntByReference(intByReference3.getValue()));
            if (n != 0) {
                throw new Win32Exception(n);
            }
            String string = Native.toString(cArray);
            Memory memory = new Memory(intByReference4.getValue());
            memory.write(0L, byArray, 0, intByReference4.getValue());
            switch (intByReference5.getValue()) {
                case 11: {
                    treeMap.put(string, memory.getLong(0L));
                    continue block7;
                }
                case 4: {
                    treeMap.put(string, memory.getInt(0L));
                    continue block7;
                }
                case 1: 
                case 2: {
                    treeMap.put(string, memory.getString(0L, false));
                    continue block7;
                }
                case 3: {
                    treeMap.put(string, memory.getByteArray(0L, intByReference4.getValue()));
                    continue block7;
                }
                case 7: {
                    Memory memory2 = new Memory(intByReference4.getValue());
                    memory2.write(0L, byArray, 0, intByReference4.getValue());
                    ArrayList<String> arrayList = new ArrayList<String>();
                    int n2 = 0;
                    while ((long)n2 < memory2.size()) {
                        String string2 = memory2.getString((long)n2, false);
                        n2 += string2.length() * Native.WCHAR_SIZE;
                        n2 += Native.WCHAR_SIZE;
                        arrayList.add(string2);
                    }
                    treeMap.put(string, arrayList.toArray(new String[0]));
                    continue block7;
                }
                default: {
                    throw new RuntimeException("Unsupported type: " + intByReference5.getValue());
                }
            }
        }
        return treeMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TreeMap<String, Object> registryGetValues(WinReg.HKEY hKEY, String string) {
        WinReg.HKEYByReference hKEYByReference = new WinReg.HKEYByReference();
        int n = Advapi32.INSTANCE.RegOpenKeyEx(hKEY, string, 0, 131097, hKEYByReference);
        if (n != 0) {
            throw new Win32Exception(n);
        }
        try {
            TreeMap<String, Object> treeMap = Advapi32Util.registryGetValues(hKEYByReference.getValue());
            return treeMap;
        } finally {
            n = Advapi32.INSTANCE.RegCloseKey(hKEYByReference.getValue());
            if (n != 0) {
                throw new Win32Exception(n);
            }
        }
    }

    public static String getEnvironmentBlock(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() == null) continue;
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "\u0000");
        }
        return stringBuffer.toString() + "\u0000";
    }

    public static WinNT.ACCESS_ACEStructure[] getFileSecurity(String string, boolean bl) {
        Object object;
        int n = 4;
        int n2 = 1024;
        boolean bl2 = false;
        Memory memory = null;
        do {
            int n3;
            bl2 = false;
            memory = new Memory(n2);
            object = new IntByReference();
            boolean bl3 = Advapi32.INSTANCE.GetFileSecurity(new WString(string), n, memory, n2, (IntByReference)object);
            if (!bl3) {
                n3 = Kernel32.INSTANCE.GetLastError();
                memory.clear();
                if (122 != n3) {
                    throw new Win32Exception(n3);
                }
            }
            if (n2 >= (n3 = ((IntByReference)object).getValue())) continue;
            bl2 = true;
            n2 = n3;
            memory.clear();
        } while (bl2);
        object = new WinNT.SECURITY_DESCRIPTOR_RELATIVE(memory);
        memory.clear();
        WinNT.ACL aCL = ((WinNT.SECURITY_DESCRIPTOR_RELATIVE)object).getDiscretionaryACL();
        WinNT.ACCESS_ACEStructure[] aCCESS_ACEStructureArray = aCL.getACEStructures();
        if (bl) {
            HashMap<String, WinNT.ACCESS_ACEStructure> hashMap = new HashMap<String, WinNT.ACCESS_ACEStructure>();
            for (WinNT.ACCESS_ACEStructure aCCESS_ACEStructure : aCCESS_ACEStructureArray) {
                boolean bl4 = (aCCESS_ACEStructure.AceFlags & 0x1F) != 0;
                String string2 = aCCESS_ACEStructure.getSidString() + "/" + bl4 + "/" + aCCESS_ACEStructure.getClass().getName();
                WinNT.ACCESS_ACEStructure aCCESS_ACEStructure2 = (WinNT.ACCESS_ACEStructure)hashMap.get(string2);
                if (aCCESS_ACEStructure2 != null) {
                    int n4 = aCCESS_ACEStructure2.Mask;
                    aCCESS_ACEStructure2.Mask = n4 |= aCCESS_ACEStructure.Mask;
                    continue;
                }
                hashMap.put(string2, aCCESS_ACEStructure);
            }
            return hashMap.values().toArray(new WinNT.ACCESS_ACEStructure[hashMap.size()]);
        }
        return aCCESS_ACEStructureArray;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EventLogIterator
    implements Iterable<EventLogRecord>,
    Iterator<EventLogRecord> {
        private WinNT.HANDLE _h = null;
        private Memory _buffer = new Memory(65536L);
        private boolean _done = false;
        private int _dwRead = 0;
        private Pointer _pevlr = null;
        private int _flags = 4;

        public EventLogIterator(String string) {
            this(null, string, 4);
        }

        public EventLogIterator(String string, String string2, int n) {
            this._flags = n;
            this._h = Advapi32.INSTANCE.OpenEventLog(string, string2);
            if (this._h == null) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
        }

        private boolean read() {
            if (this._done || this._dwRead > 0) {
                return true;
            }
            IntByReference intByReference = new IntByReference();
            IntByReference intByReference2 = new IntByReference();
            if (!Advapi32.INSTANCE.ReadEventLog(this._h, 1 | this._flags, 0, this._buffer, (int)this._buffer.size(), intByReference, intByReference2)) {
                int n = Kernel32.INSTANCE.GetLastError();
                if (n == 122) {
                    this._buffer = new Memory(intByReference2.getValue());
                    if (!Advapi32.INSTANCE.ReadEventLog(this._h, 1 | this._flags, 0, this._buffer, (int)this._buffer.size(), intByReference, intByReference2)) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                } else {
                    this.close();
                    if (n != 38) {
                        throw new Win32Exception(n);
                    }
                    return true;
                }
            }
            this._dwRead = intByReference.getValue();
            this._pevlr = this._buffer;
            return false;
        }

        public void close() {
            this._done = true;
            if (this._h != null) {
                if (!Advapi32.INSTANCE.CloseEventLog(this._h)) {
                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                }
                this._h = null;
            }
        }

        @Override
        public Iterator<EventLogRecord> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            this.read();
            return !this._done;
        }

        @Override
        public EventLogRecord next() {
            this.read();
            EventLogRecord eventLogRecord = new EventLogRecord(this._pevlr);
            this._dwRead -= eventLogRecord.getLength();
            this._pevlr = this._pevlr.share(eventLogRecord.getLength());
            return eventLogRecord;
        }

        @Override
        public void remove() {
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    public static class EventLogRecord {
        private WinNT.EVENTLOGRECORD _record = null;
        private String _source;
        private byte[] _data;
        private String[] _strings;

        public WinNT.EVENTLOGRECORD getRecord() {
            return this._record;
        }

        public int getEventId() {
            return this._record.EventID.intValue();
        }

        public String getSource() {
            return this._source;
        }

        public int getStatusCode() {
            return this._record.EventID.intValue() & 0xFFFF;
        }

        public int getRecordNumber() {
            return this._record.RecordNumber.intValue();
        }

        public int getLength() {
            return this._record.Length.intValue();
        }

        public String[] getStrings() {
            return this._strings;
        }

        public EventLogType getType() {
            switch (this._record.EventType.intValue()) {
                case 0: 
                case 4: {
                    return EventLogType.Informational;
                }
                case 16: {
                    return EventLogType.AuditFailure;
                }
                case 8: {
                    return EventLogType.AuditSuccess;
                }
                case 1: {
                    return EventLogType.Error;
                }
                case 2: {
                    return EventLogType.Warning;
                }
            }
            throw new RuntimeException("Invalid type: " + this._record.EventType.intValue());
        }

        public byte[] getData() {
            return this._data;
        }

        public EventLogRecord(Pointer pointer) {
            this._record = new WinNT.EVENTLOGRECORD(pointer);
            this._source = pointer.getString((long)this._record.size(), false);
            if (this._record.DataLength.intValue() > 0) {
                this._data = pointer.getByteArray(this._record.DataOffset.intValue(), this._record.DataLength.intValue());
            }
            if (this._record.NumStrings.intValue() > 0) {
                ArrayList<String> arrayList = new ArrayList<String>();
                long l = this._record.StringOffset.intValue();
                for (int i = this._record.NumStrings.intValue(); i > 0; --i) {
                    String string = pointer.getString(l, false);
                    arrayList.add(string);
                    l += (long)(string.length() * Native.WCHAR_SIZE);
                    l += (long)Native.WCHAR_SIZE;
                }
                this._strings = arrayList.toArray(new String[0]);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum EventLogType {
        Error,
        Warning,
        Informational,
        AuditSuccess,
        AuditFailure;

    }

    public static class Account {
        public String name;
        public String domain;
        public byte[] sid;
        public String sidString;
        public int accountType;
        public String fqn;
    }
}

