package com.client.glowclient.commands;

public class Goto extends Command
{
    public Goto() {
        super("goto");
    }
    
    @Override
    public void M(final String s, final String[] array) {
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("gcprefix").toString();
    }
}
