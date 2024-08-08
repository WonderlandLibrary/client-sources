package net.futureclient.client;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.multiplayer.ServerData;

public class Ej extends XB
{
    public Ej() {
        super(new String[] { "Connect", AE.M("&") });
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length == 1) {
            final ServerData serverData = new ServerData("", array[0], false);
            final String s = "\u0006\t+\b \u00051\u000f+\u0001kHk";
            this.k.world.sendQuittingDisconnectingPacket();
            this.k.loadWorld((WorldClient)null);
            this.k.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), this.k, serverData));
            return AE.M(s);
        }
        return null;
    }
    
    @Override
    public String M() {
        return "&e[ip]";
    }
}
