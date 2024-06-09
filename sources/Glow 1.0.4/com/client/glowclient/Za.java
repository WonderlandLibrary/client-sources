package com.client.glowclient;

import com.client.glowclient.commands.*;
import java.util.*;

public class zA
{
    private static HashMap<String, Command> B;
    private static final zA b;
    
    public static String M(final String s) {
        if (s.length() == 1) {
            return new StringBuilder().insert(0, Command.B.e()).append(zA.B.values().iterator().next().b).toString();
        }
        final String[] split = s.split(" ");
        try {
            final String substring = split[0].substring(1);
            final Command command;
            if ((command = zA.B.get(substring)) != null) {
                return command.M(s, split);
            }
            final Iterator<Map.Entry<String, Command>> iterator = zA.B.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<String, Command> entry;
                if ((entry = iterator.next()).getKey().startsWith(substring)) {
                    return new StringBuilder().insert(0, Command.B.e()).append(entry.getKey()).toString();
                }
            }
            return "";
        }
        catch (StringIndexOutOfBoundsException ex) {}
        return "";
    }
    
    static {
        b = new zA();
        zA.B = new HashMap<String, Command>();
    }
    
    public static void M(final Command command) {
        if (!zA.B.values().contains(command)) {
            final String[] m;
            final int length = (m = command.M()).length;
            int n;
            int i = n = 0;
            while (i < length) {
                final String s = m[n];
                final HashMap<String, Command> b = zA.B;
                ++n;
                b.put(s, command);
                i = n;
            }
        }
    }
    
    public static void M(final String s) {
        try {
            final String[] split;
            final String substring = (split = s.split(" "))[0].substring(1);
            final Iterator<Map.Entry<String, Command>> iterator = zA.B.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<String, Command> entry;
                if ((entry = iterator.next()).getKey().equalsIgnoreCase(substring)) {
                    entry.getValue().M(s, split);
                    return;
                }
            }
            qd.D(new StringBuilder().insert(0, "§cUnknown command \"").append(s.replace(Command.B.e(), "")).append("\" Use ").append(Command.B.e()).append("help for a list of commands!").toString());
        }
        catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ex) {}
    }
    
    public static zA M() {
        return zA.b;
    }
    
    public zA() {
        super();
    }
}
