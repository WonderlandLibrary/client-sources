package de.labystudio.spotify;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

public class SpotifyManager
{
    private String spotify;
    private String title;
    private boolean set;
    SpotifyUser32 spotifyUser32;
    private WNDENUMPROC wndeNumProc;
    private String trackName = null;
    private String artistName = null;
    private long displayTime;

    public SpotifyManager()
    {
        try
        {
            this.spotifyUser32 = (SpotifyUser32)Native.loadLibrary("User32", SpotifyUser32.class);
            this.spotify = null;
            this.title = "?";
            this.set = false;
            this.wndeNumProc = new WNDENUMPROC()
            {
                public boolean callback(HWND hWnd, Pointer arg1)
                {
                    byte[] abyte = new byte[512];
                    SpotifyManager.this.spotifyUser32.GetWindowTextA(hWnd, abyte, 512);
                    String s = Native.toString(abyte);

                    if (s.isEmpty())
                    {
                        return true;
                    }
                    else
                    {
                        boolean flag = s.equals("Spotify");

                        if (SpotifyManager.this.spotify == null && flag || flag || SpotifyManager.this.title.equals(s) && !hWnd.toString().equals(SpotifyManager.this.spotify))
                        {
                            SpotifyManager.this.spotify = hWnd.toString();
                        }

                        if (SpotifyManager.this.spotify != null && SpotifyManager.this.spotify.equals(hWnd.toString()))
                        {
                            boolean flag1 = !SpotifyManager.this.title.equals(s);

                            if (flag)
                            {
                                SpotifyManager.this.title = "No song playing";
                            }
                            else
                            {
                                SpotifyManager.this.title = s;
                                SpotifyManager.this.setDisplayTime(System.currentTimeMillis());
                            }

                            if (flag1)
                            {
                                SpotifyManager.this.newTitleIsPlaying();
                            }

                            SpotifyManager.this.set = true;
                        }

                        return true;
                    }
                }
            };
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        catch (Error error)
        {
            error.printStackTrace();
        }
    }

    public String getArtistName()
    {
        return this.artistName;
    }

    public String getTrackName()
    {
        return this.trackName;
    }

    private void newTitleIsPlaying()
    {
        if (this.getSpotifyTitle() != null && this.getSpotifyTitle().contains(" - "))
        {
            String[] astring = this.getSpotifyTitle().replaceFirst(" - ", "@@@").split("@@@");
            this.artistName = astring[0];
            this.trackName = astring[1];
        }
        else
        {
            this.artistName = null;
            this.trackName = null;
        }
    }

    public SpotifyUser32 getSpotifyUser32()
    {
        return this.spotifyUser32;
    }

    public String getSpotifyTitle()
    {
        return this.title;
    }

    public long getDisplayTime()
    {
        return this.displayTime;
    }

    public void setDisplayTime(long displayTime)
    {
        this.displayTime = displayTime;
    }

    public WNDENUMPROC getWndeNumProc()
    {
        return this.wndeNumProc;
    }

    public void updateTitle()
    {
        if (this.getSpotifyUser32() != null)
        {
            if (this.getWndeNumProc() != null)
            {
                this.set = false;
                (new SpotifyThread(this.getSpotifyUser32(), this.getWndeNumProc(), new SpotifyCallback()
                {
                    public void done()
                    {
                        if (SpotifyManager.this.spotify != null && !SpotifyManager.this.set)
                        {
                            SpotifyManager.this.spotify = null;
                        }
                    }
                })).start();
            }
        }
    }
}
