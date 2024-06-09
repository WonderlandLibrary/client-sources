package com.client.glowclient.commands;

import com.client.glowclient.*;

public class Help extends Command
{
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("help").toString();
    }
    
    public Help() {
        super("help");
    }
    
    @Override
    public void M(String s, final String[] array) {
        if (Glow.B) {
            s = "§r§7Bind, Enemy, FakeMessage, Friend, Help, NameHistory, Peek, Prefix, Save, Shutdown, Toggle, TP, Unbind, VClip, YawCoordinate";
        }
        else {
            s = "§r§7Bind, Enemy, Friend, Help, NameHistory, Peek, Prefix, Save, Shutdown, Toggle, TP, Unbind, VClip, YawCoordinate";
        }
        qd.D(new StringBuilder().insert(0, "§lCommands: ").append(s).append("\n§r§7Open GUI with RSHIFT").toString());
    }
}
