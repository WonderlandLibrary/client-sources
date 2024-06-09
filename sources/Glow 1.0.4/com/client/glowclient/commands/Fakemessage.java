package com.client.glowclient.commands;

import com.client.glowclient.*;

public class Fakemessage extends Command
{
    public Fakemessage() {
        super("fakemessage");
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("fakemessage").toString();
    }
    
    @Override
    public void M(String string, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        string = "";
        final int length = array.length;
        int n;
        int i = n = 0;
        while (i < length) {
            final String s;
            if (!(s = array[n]).equals(array[0])) {
                string = new StringBuilder().insert(0, string).append(s).append(" ").toString();
            }
            i = ++n;
        }
        qd.M(String.format(string, new Object[0]).replace("&", "§"));
    }
}
