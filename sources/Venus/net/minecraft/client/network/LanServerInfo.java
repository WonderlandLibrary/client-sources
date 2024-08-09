/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network;

import net.minecraft.util.Util;

public class LanServerInfo {
    private final String lanServerMotd;
    private final String lanServerIpPort;
    private long timeLastSeen;

    public LanServerInfo(String string, String string2) {
        this.lanServerMotd = string;
        this.lanServerIpPort = string2;
        this.timeLastSeen = Util.milliTime();
    }

    public String getServerMotd() {
        return this.lanServerMotd;
    }

    public String getServerIpPort() {
        return this.lanServerIpPort;
    }

    public void updateLastSeen() {
        this.timeLastSeen = Util.milliTime();
    }
}

