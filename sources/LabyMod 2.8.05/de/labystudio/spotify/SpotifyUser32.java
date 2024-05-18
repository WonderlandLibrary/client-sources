package de.labystudio.spotify;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;

interface SpotifyUser32 extends StdCallLibrary
{
    boolean EnumWindows(WNDENUMPROC var1, Pointer var2);

    int GetWindowTextA(HWND var1, byte[] var2, int var3);
}
