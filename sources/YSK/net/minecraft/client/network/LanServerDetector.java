package net.minecraft.client.network;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import java.net.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;

public class LanServerDetector
{
    private static final Logger logger;
    private static final AtomicInteger field_148551_a;
    
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        field_148551_a = new AtomicInteger("".length());
        logger = LogManager.getLogger();
    }
    
    static Logger access$1() {
        return LanServerDetector.logger;
    }
    
    static AtomicInteger access$0() {
        return LanServerDetector.field_148551_a;
    }
    
    public static class LanServer
    {
        private String lanServerIpPort;
        private String lanServerMotd;
        private long timeLastSeen;
        
        public String getServerIpPort() {
            return this.lanServerIpPort;
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public LanServer(final String lanServerMotd, final String lanServerIpPort) {
            this.lanServerMotd = lanServerMotd;
            this.lanServerIpPort = lanServerIpPort;
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public String getServerMotd() {
            return this.lanServerMotd;
        }
    }
    
    public static class ThreadLanServerFind extends Thread
    {
        private final InetAddress broadcastAddress;
        private final LanServerList localServerList;
        private static final String[] I;
        private final MulticastSocket socket;
        
        @Override
        public void run() {
            final byte[] array = new byte[315 + 930 - 335 + 114];
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (!this.isInterrupted()) {
                final DatagramPacket datagramPacket = new DatagramPacket(array, array.length);
                try {
                    this.socket.receive(datagramPacket);
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                catch (SocketTimeoutException ex2) {
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    continue;
                }
                catch (IOException ex) {
                    LanServerDetector.access$1().error(ThreadLanServerFind.I["  ".length()], (Throwable)ex);
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    break;
                }
                final String s = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                LanServerDetector.access$1().debug(datagramPacket.getAddress() + ThreadLanServerFind.I["   ".length()] + s);
                this.localServerList.func_77551_a(s, datagramPacket.getAddress());
            }
            try {
                this.socket.leaveGroup(this.broadcastAddress);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            catch (IOException ex3) {}
            this.socket.close();
        }
        
        public ThreadLanServerFind(final LanServerList localServerList) throws IOException {
            super(ThreadLanServerFind.I["".length()] + LanServerDetector.access$0().incrementAndGet());
            this.localServerList = localServerList;
            this.setDaemon(" ".length() != 0);
            this.socket = new MulticastSocket(2787 + 2014 - 1389 + 1033);
            this.broadcastAddress = InetAddress.getByName(ThreadLanServerFind.I[" ".length()]);
            this.socket.setSoTimeout(3550 + 1723 - 3837 + 3564);
            this.socket.joinGroup(this.broadcastAddress);
        }
        
        private static void I() {
            (I = new String[0x41 ^ 0x45])["".length()] = I("%2+)=\u001b% \b\u001c\f' \u0019,\u0006!eY", "iSEzX");
            ThreadLanServerFind.I[" ".length()] = I("EzWwvYzMov", "wHcYF");
            ThreadLanServerFind.I["  ".length()] = I("\u0016&\u0000\u001d\u000f;n\u0001Q\u001b<'\u0012Q\u00180;\u0003\u0014\u0019", "UIuqk");
            ThreadLanServerFind.I["   ".length()] = I("Jk", "pKDYc");
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
    }
    
    public static class LanServerList
    {
        private List<LanServer> listOfLanServers;
        private static final String[] I;
        boolean wasUpdated;
        
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
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        public synchronized List<LanServer> getLanServers() {
            return Collections.unmodifiableList((List<? extends LanServer>)this.listOfLanServers);
        }
        
        public LanServerList() {
            this.listOfLanServers = (List<LanServer>)Lists.newArrayList();
        }
        
        public synchronized void func_77551_a(final String s, final InetAddress inetAddress) {
            final String motdFromPingResponse = ThreadLanServerPing.getMotdFromPingResponse(s);
            final String adFromPingResponse = ThreadLanServerPing.getAdFromPingResponse(s);
            if (adFromPingResponse != null) {
                final String string = String.valueOf(inetAddress.getHostAddress()) + LanServerList.I["".length()] + adFromPingResponse;
                int n = "".length();
                final Iterator<LanServer> iterator = this.listOfLanServers.iterator();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final LanServer lanServer = iterator.next();
                    if (lanServer.getServerIpPort().equals(string)) {
                        lanServer.updateLastSeen();
                        n = " ".length();
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                        break;
                    }
                }
                if (n == 0) {
                    this.listOfLanServers.add(new LanServer(motdFromPingResponse, string));
                    this.wasUpdated = (" ".length() != 0);
                }
            }
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("{", "AxrEH");
        }
        
        public synchronized void setWasNotUpdated() {
            this.wasUpdated = ("".length() != 0);
        }
        
        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }
    }
}
