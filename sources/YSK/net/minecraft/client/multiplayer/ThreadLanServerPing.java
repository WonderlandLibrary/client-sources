package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import java.io.*;
import org.apache.logging.log4j.*;
import java.net.*;

public class ThreadLanServerPing extends Thread
{
    private final DatagramSocket socket;
    private static final AtomicInteger field_148658_a;
    private static final Logger logger;
    private static final String[] I;
    private final String address;
    private boolean isStopping;
    private final String motd;
    
    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = ("".length() != 0);
    }
    
    public static String getPingResponse(final String s, final String s2) {
        return ThreadLanServerPing.I["   ".length()] + s + ThreadLanServerPing.I[0xC3 ^ 0xC7] + s2 + ThreadLanServerPing.I[0x5D ^ 0x58];
    }
    
    public static String getMotdFromPingResponse(final String s) {
        final int index = s.indexOf(ThreadLanServerPing.I[0x69 ^ 0x6F]);
        if (index < 0) {
            return ThreadLanServerPing.I[0x64 ^ 0x63];
        }
        final int index2 = s.indexOf(ThreadLanServerPing.I[0xCE ^ 0xC6], index + ThreadLanServerPing.I[0x8F ^ 0x86].length());
        String substring;
        if (index2 < index) {
            substring = ThreadLanServerPing.I[0x4A ^ 0x40];
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            substring = s.substring(index + ThreadLanServerPing.I[0x82 ^ 0x89].length(), index2);
        }
        return substring;
    }
    
    public ThreadLanServerPing(final String motd, final String address) throws IOException {
        super(ThreadLanServerPing.I["".length()] + ThreadLanServerPing.field_148658_a.incrementAndGet());
        this.isStopping = (" ".length() != 0);
        this.motd = motd;
        this.address = address;
        this.setDaemon(" ".length() != 0);
        this.socket = new DatagramSocket();
    }
    
    private static void I() {
        (I = new String[0x51 ^ 0x45])["".length()] = I("<+\f%\u0002\u0002<\u0007\u00047\u0019$\u0005\u0013\u0015Pi", "pJbvg");
        ThreadLanServerPing.I[" ".length()] = I("Q|AiaM|[qa", "cNuGQ");
        ThreadLanServerPing.I["  ".length()] = I("=\u0019;>4\u0003\u000e0\u001f\u0001\u0018\u00162\b#KX", "qxUmQ");
        ThreadLanServerPing.I["   ".length()] = I("\b\"\n\"\u0001\u000e", "SoEvE");
        ThreadLanServerPing.I[0xB0 ^ 0xB4] = I(":e9>=%\u0017/0-<", "aJtqi");
        ThreadLanServerPing.I[0x54 ^ 0x51] = I("\bC\u0012\u0013$", "SlSWy");
        ThreadLanServerPing.I[0x68 ^ 0x6E] = I("\u0011\n\u00170(\u0017", "JGXdl");
        ThreadLanServerPing.I[0xBC ^ 0xBB] = I(":\u0013$\u0017\u00189\u001dw\n\u001e", "WzWdq");
        ThreadLanServerPing.I[0xA0 ^ 0xA8] = I("\bc7+>\u0017\u0011", "SLzdj");
        ThreadLanServerPing.I[0x54 ^ 0x5D] = I("2*\u001f\u001c)4", "igPHm");
        ThreadLanServerPing.I[0x75 ^ 0x7F] = I("\u000e!2\u0002<\r/a\u001f:", "cHAqU");
        ThreadLanServerPing.I[0xA8 ^ 0xA3] = I("\b\t\b\u0013<\u000e", "SDGGx");
        ThreadLanServerPing.I[0x22 ^ 0x2E] = I("\nL\n?\"\u0015>", "QcGpv");
        ThreadLanServerPing.I[0x2C ^ 0x21] = I("\u0014c('3\u000b\u0011", "OLehg");
        ThreadLanServerPing.I[0x20 ^ 0x2E] = I("#}\u0006\u000e7<\u000f", "xRKAc");
        ThreadLanServerPing.I[0x67 ^ 0x68] = I("\u0003)=\u000e", "XhySA");
        ThreadLanServerPing.I[0x29 ^ 0x39] = I(".J\u0019\u0019-18", "ueTVy");
        ThreadLanServerPing.I[0x31 ^ 0x20] = I("\u0001N8)\u001b", "ZaymF");
        ThreadLanServerPing.I[0x3C ^ 0x2E] = I("\u0015#\u0000\u0004", "NbDYa");
        ThreadLanServerPing.I[0xBA ^ 0xA9] = I("\u0014$7\u0010", "OesMj");
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        field_148658_a = new AtomicInteger("".length());
        logger = LogManager.getLogger();
    }
    
    public static String getAdFromPingResponse(final String s) {
        final int index = s.indexOf(ThreadLanServerPing.I[0x6 ^ 0xA]);
        if (index < 0) {
            return null;
        }
        if (s.indexOf(ThreadLanServerPing.I[0x14 ^ 0x19], index + ThreadLanServerPing.I[0x83 ^ 0x8D].length()) >= 0) {
            return null;
        }
        final int index2 = s.indexOf(ThreadLanServerPing.I[0xCE ^ 0xC1], index + ThreadLanServerPing.I[0x64 ^ 0x74].length());
        if (index2 < 0) {
            return null;
        }
        final int index3 = s.indexOf(ThreadLanServerPing.I[0x93 ^ 0x82], index2 + ThreadLanServerPing.I[0x55 ^ 0x47].length());
        String substring;
        if (index3 < index2) {
            substring = null;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            substring = s.substring(index2 + ThreadLanServerPing.I[0x5C ^ 0x4F].length(), index3);
        }
        return substring;
    }
    
    @Override
    public void run() {
        final byte[] bytes = getPingResponse(this.motd, this.address).getBytes();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (!this.isInterrupted() && this.isStopping) {
            try {
                this.socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName(ThreadLanServerPing.I[" ".length()]), 1918 + 2825 - 4461 + 4163));
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (IOException ex) {
                ThreadLanServerPing.logger.warn(ThreadLanServerPing.I["  ".length()] + ex.getMessage());
                "".length();
                if (-1 == 2) {
                    throw null;
                }
                break;
            }
            try {
                Thread.sleep(1500L);
                "".length();
                if (3 < 3) {
                    throw null;
                }
                continue;
            }
            catch (InterruptedException ex2) {}
        }
    }
}
