package com.client.glowclient.commands;

import com.client.glowclient.modules.*;
import java.util.*;
import com.client.glowclient.*;

public class Bind extends Command
{
    @Override
    public String M(final String s, final String[] array) {
        switch (array.length) {
            case 2: {
                final Collection<NF> m = ModuleManager.M();
                while (false) {}
                final Iterator<NF> iterator = m.iterator();
                while (iterator.hasNext()) {
                    final Module module;
                    if ((module = (Module)iterator.next()) instanceof ModuleContainer && module.k().startsWith(array[1])) {
                        return new StringBuilder().insert(0, Command.B.e()).append("bind ").append(module.k()).toString();
                    }
                }
                return new StringBuilder().insert(0, Command.B.e()).append("bind").toString();
            }
            default: {
                return new StringBuilder().insert(0, Command.B.e()).append("bind").toString();
            }
        }
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 3) {
            qd.D("§cNot enough data given");
        }
        final Module m;
        if ((m = ModuleManager.M(array[1])) == null) {
            qd.D(new StringBuilder().insert(0, "§cCould not find module: ").append(array[1]).toString());
            return;
        }
        if (!(ModuleManager.M(array[1]) instanceof rF) && sd.M(array[2])) {
            m.c.M(Integer.parseInt(array[2]));
            qd.D(new StringBuilder().insert(0, "§bBound ").append(m.k()).append(" to key: ").append(array[2]).toString());
        }
    }
    
    public Bind() {
        super("bind");
    }
}
