package com.client.glowclient.commands;

import com.google.common.util.concurrent.*;
import com.client.glowclient.*;

public class Namehistory extends Command
{
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("namehistory").toString();
    }
    
    public Namehistory() {
        super("namehistory");
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        r.M(array[1], (FutureCallback<R>)new Pf(this, qd.M()));
    }
}
