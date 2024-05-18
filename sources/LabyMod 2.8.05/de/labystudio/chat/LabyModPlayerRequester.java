package de.labystudio.chat;

import java.util.UUID;

public class LabyModPlayerRequester extends LabyModPlayer
{
    public LabyModPlayerRequester(String name, UUID id)
    {
        super(name, id, "* Offline *", LabyModPlayer.OnlineStatus.OFFLINE);
    }

    public boolean isOnline()
    {
        return false;
    }

    public String getMotd()
    {
        return "* Offline *";
    }

    public boolean isRequest()
    {
        return true;
    }
}
