package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;
import com.sun.jna.*;
import java.util.*;

public interface Crypt32 extends StdCallLibrary
{
    public static final Crypt32 INSTANCE = Native.loadLibrary("Crypt32", Crypt32.class, W32APIOptions.UNICODE_OPTIONS);
    
    boolean CryptProtectData(final WinCrypt.DATA_BLOB p0, final String p1, final WinCrypt.DATA_BLOB p2, final Pointer p3, final WinCrypt.CRYPTPROTECT_PROMPTSTRUCT p4, final int p5, final WinCrypt.DATA_BLOB p6);
    
    boolean CryptUnprotectData(final WinCrypt.DATA_BLOB p0, final PointerByReference p1, final WinCrypt.DATA_BLOB p2, final Pointer p3, final WinCrypt.CRYPTPROTECT_PROMPTSTRUCT p4, final int p5, final WinCrypt.DATA_BLOB p6);
}
