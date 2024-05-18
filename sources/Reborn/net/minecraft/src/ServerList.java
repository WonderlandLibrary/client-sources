package net.minecraft.src;

import net.minecraft.client.*;
import java.io.*;
import java.util.*;

public class ServerList
{
    private final Minecraft mc;
    private final List servers;
    
    public ServerList(final Minecraft par1Minecraft) {
        this.servers = new ArrayList();
        this.mc = par1Minecraft;
        this.loadServerList();
    }
    
    public void loadServerList() {
        try {
            final NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
            final NBTTagList var2 = var1.getTagList("servers");
            this.servers.clear();
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.servers.add(ServerData.getServerDataFromNBTCompound((NBTTagCompound)var2.tagAt(var3)));
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    public void saveServerList() {
        try {
            final NBTTagList var1 = new NBTTagList();
            for (final ServerData var3 : this.servers) {
                var1.appendTag(var3.getNBTCompound());
            }
            final NBTTagCompound var4 = new NBTTagCompound();
            var4.setTag("servers", var1);
            CompressedStreamTools.safeWrite(var4, new File(this.mc.mcDataDir, "servers.dat"));
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }
    
    public ServerData getServerData(final int par1) {
        return this.servers.get(par1);
    }
    
    public void removeServerData(final int par1) {
        this.servers.remove(par1);
    }
    
    public void addServerData(final ServerData par1ServerData) {
        this.servers.add(par1ServerData);
    }
    
    public int countServers() {
        return this.servers.size();
    }
    
    public void swapServers(final int par1, final int par2) {
        final ServerData var3 = this.getServerData(par1);
        this.servers.set(par1, this.getServerData(par2));
        this.servers.set(par2, var3);
        this.saveServerList();
    }
    
    public void setServer(final int par1, final ServerData par2ServerData) {
        this.servers.set(par1, par2ServerData);
    }
    
    public static void func_78852_b(final ServerData par0ServerData) {
        final ServerList var1 = new ServerList(Minecraft.getMinecraft());
        var1.loadServerList();
        for (int var2 = 0; var2 < var1.countServers(); ++var2) {
            final ServerData var3 = var1.getServerData(var2);
            if (var3.serverName.equals(par0ServerData.serverName) && var3.serverIP.equals(par0ServerData.serverIP)) {
                var1.setServer(var2, par0ServerData);
                break;
            }
        }
        var1.saveServerList();
    }
}
