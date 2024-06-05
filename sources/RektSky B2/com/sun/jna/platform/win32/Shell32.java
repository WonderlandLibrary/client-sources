package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;
import com.sun.jna.*;
import java.util.*;

public interface Shell32 extends ShellAPI, StdCallLibrary
{
    public static final Shell32 INSTANCE = Native.loadLibrary("shell32", Shell32.class, W32APIOptions.UNICODE_OPTIONS);
    
    int SHFileOperation(final SHFILEOPSTRUCT p0);
    
    WinNT.HRESULT SHGetFolderPath(final WinDef.HWND p0, final int p1, final WinNT.HANDLE p2, final WinDef.DWORD p3, final char[] p4);
    
    WinNT.HRESULT SHGetDesktopFolder(final PointerByReference p0);
    
    WinDef.HINSTANCE ShellExecute(final WinDef.HWND p0, final String p1, final String p2, final String p3, final String p4, final int p5);
}
