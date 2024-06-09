package com.client.glowclient.commands;

import com.client.glowclient.*;

public class Prefix extends Command
{
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
            return;
        }
        Command.B.M(array[1]);
        qd.D(new StringBuilder().insert(0, "§bSet command prefix to: ").append(Command.B.e()).toString());
    }
    
    public Prefix() {
        super("prefix");
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("prefix").toString();
    }
}
