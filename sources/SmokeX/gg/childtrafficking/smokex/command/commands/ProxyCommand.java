// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import java.net.URLConnection;
import java.net.URL;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import java.util.Objects;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Proxy", description = "Changes your IP for this MC instance.", usage = ".proxy reset || <IP:PORT>", aliases = {})
public final class ProxyCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else if (Objects.equals(args[0], "reset")) {
            System.setProperty("java.net.useSystemProxies", "false");
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "");
            ChatUtils.addChatMessage("Reset IP");
        }
        else {
            final String[] ipPort = args[0].split(":");
            try {
                System.setProperty("http.proxyHost", ipPort[0]);
                System.setProperty("http.proxyPort", ipPort[1]);
                final URL url = new URL("https://hypixel.net");
                final URLConnection con = url.openConnection();
                ChatUtils.addChatMessage("Successfully proxied as " + ipPort[0] + " with port " + ipPort[1]);
            }
            catch (final Exception e) {
                ChatUtils.addChatMessage("ERROR");
                e.printStackTrace();
            }
        }
    }
    
    private boolean isNullOrEmpty(final String str) {
        return str != null && !str.isEmpty();
    }
}
