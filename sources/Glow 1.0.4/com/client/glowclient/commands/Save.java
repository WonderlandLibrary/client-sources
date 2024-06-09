package com.client.glowclient.commands;

import com.client.glowclient.*;

public class Save extends Command
{
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("save").toString();
    }
    
    @Override
    public void M(final String s, final String[] array) {
        Ob.M().M();
        qd.D("§bConfig Saved");
    }
    
    public Save() {
        super("save");
    }
}
