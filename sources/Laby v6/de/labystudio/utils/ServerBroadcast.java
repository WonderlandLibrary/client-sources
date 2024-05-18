package de.labystudio.utils;

public class ServerBroadcast
{
    private String line1 = "";
    private String line2 = "";
    private String url = "";

    public ServerBroadcast(String line1, String line2, String url)
    {
        this.line1 = line1.replaceAll("&", Color.c);
        this.line2 = line2.replaceAll("&", Color.c);
        this.url = url;
    }

    public String getLine1()
    {
        return this.line1;
    }

    public String getLine2()
    {
        return this.line2;
    }

    public String getUrl()
    {
        return this.url;
    }
}
