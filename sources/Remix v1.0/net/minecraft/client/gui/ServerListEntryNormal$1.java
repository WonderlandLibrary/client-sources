package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.net.*;

class ServerListEntryNormal$1 implements Runnable {
    @Override
    public void run() {
        try {
            ServerListEntryNormal.access$100(ServerListEntryNormal.this).getOldServerPinger().ping(ServerListEntryNormal.access$000(ServerListEntryNormal.this));
        }
        catch (UnknownHostException var2) {
            ServerListEntryNormal.access$000(ServerListEntryNormal.this).pingToServer = -1L;
            ServerListEntryNormal.access$000(ServerListEntryNormal.this).serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
        }
        catch (Exception var3) {
            ServerListEntryNormal.access$000(ServerListEntryNormal.this).pingToServer = -1L;
            ServerListEntryNormal.access$000(ServerListEntryNormal.this).serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
        }
    }
}