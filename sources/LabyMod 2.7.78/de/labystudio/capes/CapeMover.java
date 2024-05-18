package de.labystudio.capes;

import de.labystudio.chat.ClientConnection;
import de.labystudio.chat.EnumAlertType;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.utils.Color;
import de.labystudio.utils.Utils;

public class CapeMover extends Thread
{
    String move;
    CapeCallback callback;
    public static boolean moving = false;

    public CapeMover(String move, CapeCallback callback)
    {
        moving = true;
        this.move = move;
        this.callback = callback;
    }

    public void run()
    {
        StringBuilder stringbuilder = (new StringBuilder()).append(Source.url_moveCape).append("?username=").append(LabyMod.getInstance().getPlayerName()).append("&capeKey=");
        LabyMod.getInstance().getClient().getClientConnection();
        String s = Utils.getContentString(stringbuilder.append(ClientConnection.getCapeKey()).append("&move=").append(this.move).toString());

        if (s.equalsIgnoreCase("OK"))
        {
            LabyMod.getInstance().achievementGui.displayBroadcast("CapeManager", Color.cl("a") + "Cape moved to " + this.move + "!", EnumAlertType.LABYMOD);
            LabyMod.getInstance().getCapeManager().refresh();
            this.callback.done();
        }
        else
        {
            LabyMod.getInstance().achievementGui.displayBroadcast("CapeManager", Color.cl("c") + "Error: " + s, EnumAlertType.LABYMOD);
            this.callback.failed(s);
        }

        moving = false;
    }
}
