// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.util.Iterator;
import net.minecraft.util.StringUtils;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Ping extends Cmd
{
    public Ping() {
        super("ping", "Shows actual ping to server.", "ping");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
                this.addMessage("Your ping is §c" + mc.getCurrentServerData().pingToServer + "ms§f.");
    }
}
