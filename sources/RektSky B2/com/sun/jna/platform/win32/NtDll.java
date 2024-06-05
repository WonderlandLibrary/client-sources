package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;
import com.sun.jna.*;
import java.util.*;

public interface NtDll extends StdCallLibrary
{
    public static final NtDll INSTANCE = Native.loadLibrary("NtDll", NtDll.class, W32APIOptions.UNICODE_OPTIONS);
    
    int ZwQueryKey(final WinNT.HANDLE p0, final int p1, final Structure p2, final int p3, final IntByReference p4);
}
