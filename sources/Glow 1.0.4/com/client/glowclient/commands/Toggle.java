package com.client.glowclient.commands;

import com.client.glowclient.modules.*;
import com.client.glowclient.*;
import java.util.*;

public class Toggle extends Command
{
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        final Module m;
        if ((m = ModuleManager.M(array[1])) == null) {
            qd.D(new StringBuilder().insert(0, "§cCould not find module: ").append(array[1]).toString());
            return;
        }
        if (!(ModuleManager.M(array[1]) instanceof rF)) {
            ((ModuleContainer)ModuleManager.M(array[1])).B();
            qd.D(new StringBuilder().insert(0, "§bToggled ").append(m.k()).append(" to state: ").append(m.k() ? "On" : "Off").toString());
        }
    }
    
    public Toggle() {
        super("toggle");
    }
    
    @Override
    public String M(final String s, final String[] array) {
        switch (array.length) {
            case 2: {
                final Collection<NF> m = ModuleManager.M();
                while (false) {}
                final Iterator<NF> iterator = m.iterator();
                while (iterator.hasNext()) {
                    final Module module;
                    if (!((module = (Module)iterator.next()) instanceof rF) && module.k().startsWith(array[1])) {
                        return new StringBuilder().insert(0, Command.B.e()).append("toggle ").append(module.k()).toString();
                    }
                }
                return "";
            }
            default: {
                return new StringBuilder().insert(0, Command.B.e()).append("toggle").toString();
            }
        }
    }
}
