package me.mees.remix.irc;

import me.satisfactory.base.utils.*;
import java.io.*;
import org.jibble.pircbot.*;

public class IrcManager extends PircBot
{
    private static String username;
    private final String IRC_HostName = "irc.freenode.net";
    private final int IRC_HostPort = 6667;
    private final String IRC_ChannelName = "#RemixClient";
    
    public IrcManager(final String username) {
        IrcManager.username = username;
    }
    
    public void connect() {
        this.setAutoNickChange(true);
        this.setName(IrcManager.username);
        this.changeNick(IrcManager.username);
        MiscellaneousUtil.sendInfo("[IRC] Connecting");
        try {
            this.connect("irc.freenode.net", 6667);
        }
        catch (IOException | IrcException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        MiscellaneousUtil.sendInfo("Joing Room");
        this.joinChannel("#RemixClient");
        MiscellaneousUtil.sendInfo("Logged In");
    }
}
