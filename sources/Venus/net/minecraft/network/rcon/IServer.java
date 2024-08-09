/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import net.minecraft.server.dedicated.ServerProperties;

public interface IServer {
    public ServerProperties getServerProperties();

    public String getHostname();

    public int getPort();

    public String getMotd();

    public String getMinecraftVersion();

    public int getCurrentPlayerCount();

    public int getMaxPlayers();

    public String[] getOnlinePlayerNames();

    public String func_230542_k__();

    public String getPlugins();

    public String handleRConCommand(String var1);
}

