/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final List servers = Lists.newArrayList();
    private static final String __OBFID = "CL_00000891";

    public ServerList(Minecraft mcIn) {
        this.mc = mcIn;
        this.loadServerList();
    }

    public void loadServerList() {
        try {
            this.servers.clear();
            NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
            if (var1 == null) {
                return;
            }
            NBTTagList var2 = var1.getTagList("servers", 10);
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(var2.getCompoundTagAt(var3)));
                ++var3;
            }
        }
        catch (Exception var4) {
            logger.error("Couldn't load server list", (Throwable)var4);
        }
    }

    public void saveServerList() {
        try {
            NBTTagList var1 = new NBTTagList();
            for (ServerData var3 : this.servers) {
                var1.appendTag(var3.getNBTCompound());
            }
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setTag("servers", var1);
            CompressedStreamTools.safeWrite(var5, new File(this.mc.mcDataDir, "servers.dat"));
        }
        catch (Exception var4) {
            logger.error("Couldn't save server list", (Throwable)var4);
        }
    }

    public ServerData getServerData(int p_78850_1_) {
        return (ServerData)this.servers.get(p_78850_1_);
    }

    public void removeServerData(int p_78851_1_) {
        this.servers.remove(p_78851_1_);
    }

    public void addServerData(ServerData p_78849_1_) {
        this.servers.add(p_78849_1_);
    }

    public int countServers() {
        return this.servers.size();
    }

    public void swapServers(int p_78857_1_, int p_78857_2_) {
        ServerData var3 = this.getServerData(p_78857_1_);
        this.servers.set(p_78857_1_, this.getServerData(p_78857_2_));
        this.servers.set(p_78857_2_, var3);
        this.saveServerList();
    }

    public void func_147413_a(int p_147413_1_, ServerData p_147413_2_) {
        this.servers.set(p_147413_1_, p_147413_2_);
    }

    public static void func_147414_b(ServerData p_147414_0_) {
        ServerList var1 = new ServerList(Minecraft.getMinecraft());
        var1.loadServerList();
        int var2 = 0;
        while (var2 < var1.countServers()) {
            ServerData var3 = var1.getServerData(var2);
            if (var3.serverName.equals(p_147414_0_.serverName) && var3.serverIP.equals(p_147414_0_.serverIP)) {
                var1.func_147413_a(var2, p_147414_0_);
                break;
            }
            ++var2;
        }
        var1.saveServerList();
    }
}

