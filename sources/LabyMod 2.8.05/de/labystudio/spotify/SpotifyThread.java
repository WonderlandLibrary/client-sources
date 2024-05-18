package de.labystudio.spotify;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

public class SpotifyThread extends Thread
{
    private SpotifyUser32 user32;
    private WNDENUMPROC wndeNumProc;
    private SpotifyCallback callBack;

    public SpotifyThread(SpotifyUser32 user32, WNDENUMPROC wndeNumProc, SpotifyCallback callBack)
    {
        this.user32 = user32;
        this.wndeNumProc = wndeNumProc;
        this.callBack = callBack;
    }

    public void run()
    {
        this.user32.EnumWindows(this.wndeNumProc, (Pointer)null);
        this.callBack.done();
    }
}
