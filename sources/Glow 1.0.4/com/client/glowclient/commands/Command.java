package com.client.glowclient.commands;

import com.client.glowclient.utils.*;

public abstract class Command
{
    public static StringValue B;
    public String b;
    
    public abstract String M(final String p0, final String[] p1);
    
    public String[] M() {
        return new String[] { this.b };
    }
    
    public abstract void M(final String p0, final String[] p1);
    
    public Command(final String b) {
        super();
        this.b = b;
    }
    
    static {
        Command.B = ValueFactory.M("Prefix", "Set the command prefix", ",");
    }
}
