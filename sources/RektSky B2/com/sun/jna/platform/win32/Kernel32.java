package com.sun.jna.platform.win32;

import java.nio.*;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;
import com.sun.jna.*;
import java.util.*;

public interface Kernel32 extends WinNT
{
    public static final Kernel32 INSTANCE = Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
    
    int FormatMessage(final int p0, final Pointer p1, final int p2, final int p3, final Buffer p4, final int p5, final Pointer p6);
    
    boolean ReadFile(final HANDLE p0, final Buffer p1, final int p2, final IntByReference p3, final WinBase.OVERLAPPED p4);
}
