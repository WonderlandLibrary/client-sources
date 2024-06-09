package com.client.glowclient.commands;

import com.client.glowclient.utils.*;
import com.client.glowclient.*;

public class Enemy extends Command
{
    public Enemy() {
        super("enemy");
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        if (wa.M().M(array[1])) {
            wa.M().M(array[1]);
            qd.D(String.format("§cRemoved %s from enemy list", array[1]));
            ConfigUtils.M().E();
            return;
        }
        if (Va.M().M(array[1])) {
            qd.D(String.format("§c%s is already a friend", array[1]));
            return;
        }
        wa.M().D(array[1]);
        qd.D(String.format("§bAdded %s to enemy list", array[1]));
        ConfigUtils.M().E();
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("enemy").toString();
    }
}
