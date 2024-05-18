package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ServerList
{
    private final Minecraft mc;
    private static final String[] I;
    private final List<ServerData> servers;
    private static final Logger logger;
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public void removeServerData(final int n) {
        this.servers.remove(n);
    }
    
    public void addServerData(final ServerData serverData) {
        this.servers.add(serverData);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void swapServers(final int n, final int n2) {
        final ServerData serverData = this.getServerData(n);
        this.servers.set(n, this.getServerData(n2));
        this.servers.set(n2, serverData);
        this.saveServerList();
    }
    
    public void func_147413_a(final int n, final ServerData serverData) {
        this.servers.set(n, serverData);
    }
    
    private static void I() {
        (I = new String[0xA7 ^ 0xA1])["".length()] = I("+(\u001a8 *>F*$,", "XMhNE");
        ServerList.I[" ".length()] = I("+': \t*1", "XBHVl");
        ServerList.I["  ".length()] = I("\u0016\u001e\u001d:\u0007;V\u001cv\u000f:\u0010\fv\u00100\u0003\u001e3\u0011u\u001d\u0001%\u0017", "UqhVc");
        ServerList.I["   ".length()] = I("*\u0001&\u001a\u000f+\u0017", "YdTlj");
        ServerList.I[0x3E ^ 0x3A] = I(">\u000f1\u0018\u000f?\u0019m\n\u000b9", "MjCnj");
        ServerList.I[0x59 ^ 0x5C] = I("\",%9<\u000fd$u+\u000055u+\u00041&0*A/9&,", "aCPUX");
    }
    
    public int countServers() {
        return this.servers.size();
    }
    
    public ServerList(final Minecraft mc) {
        this.servers = (List<ServerData>)Lists.newArrayList();
        this.mc = mc;
        this.loadServerList();
    }
    
    public void saveServerList() {
        try {
            final NBTTagList list = new NBTTagList();
            final Iterator<ServerData> iterator = this.servers.iterator();
            "".length();
            if (3 == 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().getNBTCompound());
            }
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag(ServerList.I["   ".length()], list);
            CompressedStreamTools.safeWrite(nbtTagCompound, new File(this.mc.mcDataDir, ServerList.I[0x6 ^ 0x2]));
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        catch (Exception ex) {
            ServerList.logger.error(ServerList.I[0xC3 ^ 0xC6], (Throwable)ex);
        }
    }
    
    public ServerData getServerData(final int n) {
        return this.servers.get(n);
    }
    
    public void loadServerList() {
        try {
            this.servers.clear();
            final NBTTagCompound read = CompressedStreamTools.read(new File(this.mc.mcDataDir, ServerList.I["".length()]));
            if (read == null) {
                return;
            }
            final NBTTagList tagList = read.getTagList(ServerList.I[" ".length()], 0x13 ^ 0x19);
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(tagList.getCompoundTagAt(i)));
                ++i;
            }
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (Exception ex) {
            ServerList.logger.error(ServerList.I["  ".length()], (Throwable)ex);
        }
    }
    
    public static void func_147414_b(final ServerData serverData) {
        final ServerList list = new ServerList(Minecraft.getMinecraft());
        list.loadServerList();
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < list.countServers()) {
            final ServerData serverData2 = list.getServerData(i);
            if (serverData2.serverName.equals(serverData.serverName) && serverData2.serverIP.equals(serverData.serverIP)) {
                list.func_147413_a(i, serverData);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        list.saveServerList();
    }
}
