package de.labystudio.utils;

public class ServiceStatus
{
    private String color;
    private long created;
    private String status;
    private String chatColor;

    public ServiceStatus(String color)
    {
        this.status = color;
        this.created = System.currentTimeMillis();
        this.color = color;

        if (color.equals("red"))
        {
            this.chatColor = "c";
            this.status = "Offline";
        }

        float f = 0.3F;

        if (color.equals("green"))
        {
            this.chatColor = "a";
            this.status = "Online";
        }

        float f1 = 0.3F;

        if (color.equals("yellow"))
        {
            this.chatColor = "e";
            this.status = "Slowly";
        }
    }

    public long getCreated()
    {
        return this.created;
    }

    public String getStatus()
    {
        return this.status;
    }

    public String getColor()
    {
        return this.color;
    }

    public String getChatColor()
    {
        return this.chatColor;
    }
}
