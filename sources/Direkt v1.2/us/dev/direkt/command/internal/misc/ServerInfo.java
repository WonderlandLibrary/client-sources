package us.dev.direkt.command.internal.misc;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

/**
 * @author BFCE
 */
public class ServerInfo extends Command {
    public ServerInfo() {
        super(Direkt.getInstance().getCommandManager(), "serverinfo", "si", "info");
    }

    @Executes
    public String run() {
       return "IP: " + Wrapper.getMinecraft().getCurrentServerData().serverIP + " Version: " + Wrapper.getMinecraft().getCurrentServerData().gameVersion + " Ping: " + Wrapper.getSendQueue().getPlayerInfo(Wrapper.getPlayer().getName()).getResponseTime() + " Spawn: X:" + Wrapper.getWorld().getSpawnPoint().getX() + " Y:" + Wrapper.getWorld().getSpawnPoint().getY() + " Z:" + Wrapper.getWorld().getSpawnPoint().getZ();
    }
}
