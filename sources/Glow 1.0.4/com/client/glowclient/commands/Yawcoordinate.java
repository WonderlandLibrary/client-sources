package com.client.glowclient.commands;

import com.client.glowclient.*;
import com.client.glowclient.modules.player.*;

public class Yawcoordinate extends Command
{
    @Override
    public void M(String s, final String[] array) {
        if (array.length < 3) {
            qd.D("§cNot enough data given");
        }
        s = array[1];
        final String s2 = array[2];
        Yaw.A = Integer.parseInt(s);
        Yaw.d = Integer.parseInt(s2);
        qd.D(new StringBuilder().insert(0, "§bSet YawCoordinate for Yaw to ").append(s).append(", ").append(s2).toString());
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("yawcoordinate").toString();
    }
    
    public Yawcoordinate() {
        super("yawcoordinate");
    }
}
