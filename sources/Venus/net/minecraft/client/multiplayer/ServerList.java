/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private final List<ServerData> servers = Lists.newArrayList();

    public ServerList(Minecraft minecraft) {
        this.mc = minecraft;
        this.loadServerList();
    }

    public void loadServerList() {
        try {
            this.servers.clear();
            CompoundNBT compoundNBT = CompressedStreamTools.read(new File(this.mc.gameDir, "servers.dat"));
            if (compoundNBT == null) {
                return;
            }
            ListNBT listNBT = compoundNBT.getList("servers", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(listNBT.getCompound(i)));
            }
        } catch (Exception exception) {
            LOGGER.error("Couldn't load server list", (Throwable)exception);
        }
    }

    public void saveServerList() {
        try {
            ListNBT listNBT = new ListNBT();
            for (ServerData object2 : this.servers) {
                listNBT.add(object2.getNBTCompound());
            }
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("servers", listNBT);
            File file = File.createTempFile("servers", ".dat", this.mc.gameDir);
            CompressedStreamTools.write(compoundNBT, file);
            File file2 = new File(this.mc.gameDir, "servers.dat_old");
            File file3 = new File(this.mc.gameDir, "servers.dat");
            Util.backupThenUpdate(file3, file, file2);
        } catch (Exception exception) {
            LOGGER.error("Couldn't save server list", (Throwable)exception);
        }
    }

    public ServerData getServerData(int n) {
        return this.servers.get(n);
    }

    public void func_217506_a(ServerData serverData) {
        this.servers.remove(serverData);
    }

    public void addServerData(ServerData serverData) {
        this.servers.add(serverData);
    }

    public int countServers() {
        return this.servers.size();
    }

    public void swapServers(int n, int n2) {
        ServerData serverData = this.getServerData(n);
        this.servers.set(n, this.getServerData(n2));
        this.servers.set(n2, serverData);
        this.saveServerList();
    }

    public void set(int n, ServerData serverData) {
        this.servers.set(n, serverData);
    }

    public static void saveSingleServer(ServerData serverData) {
        ServerList serverList = new ServerList(Minecraft.getInstance());
        serverList.loadServerList();
        for (int i = 0; i < serverList.countServers(); ++i) {
            ServerData serverData2 = serverList.getServerData(i);
            if (!serverData2.serverName.equals(serverData.serverName) || !serverData2.serverIP.equals(serverData.serverIP)) continue;
            serverList.set(i, serverData);
            break;
        }
        serverList.saveServerList();
    }
}

