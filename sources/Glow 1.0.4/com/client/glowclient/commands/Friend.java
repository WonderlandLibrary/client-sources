package com.client.glowclient.commands;

import com.client.glowclient.utils.*;
import com.client.glowclient.*;

public class Friend extends Command
{
    public Friend() {
        super("friend");
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        if (Va.M().M(array[1])) {
            Va.M().M(array[1]);
            qd.D(String.format("§cRemoved %s from friends list", array[1]));
            ConfigUtils.M().e();
            return;
        }
        if (wa.M().M(array[1])) {
            qd.D(String.format("§c%s is already an enemy", array[1]));
            return;
        }
        Va.M().D(array[1]);
        qd.D(String.format("§bAdded %s to friends list", array[1]));
        ConfigUtils.M().e();
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return "";
    }
}
