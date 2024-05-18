package net.minecraft.profiler;

import java.lang.management.*;
import com.google.common.collect.*;
import java.net.*;
import net.minecraft.util.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private final long minecraftStartTimeMilis;
    private final Timer threadTrigger;
    private int selfCounter;
    private boolean isRunning;
    private static final String[] I;
    private final URL serverUrl;
    private final String uniqueID;
    private final Map<String, Object> field_152774_b;
    private final Object syncLock;
    private final IPlayerUsage playerStatsCollector;
    private final Map<String, Object> field_152773_a;
    
    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper(PlayerUsageSnooper.I[0x22 ^ 0x31], Runtime.getRuntime().totalMemory());
        this.addStatToSnooper(PlayerUsageSnooper.I[0x92 ^ 0x86], Runtime.getRuntime().maxMemory());
        this.addStatToSnooper(PlayerUsageSnooper.I[0x23 ^ 0x36], Runtime.getRuntime().freeMemory());
        this.addStatToSnooper(PlayerUsageSnooper.I[0x5 ^ 0x13], Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    private void addJvmArgsToSnooper() {
        final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        int length = "".length();
        final Iterator<String> iterator = inputArguments.iterator();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            if (s.startsWith(PlayerUsageSnooper.I[0xCD ^ 0xC2])) {
                this.addClientStat(PlayerUsageSnooper.I[0x4C ^ 0x5C] + length++ + PlayerUsageSnooper.I[0xB4 ^ 0xA5], s);
            }
        }
        this.addClientStat(PlayerUsageSnooper.I[0xA5 ^ 0xB7], length);
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
    
    public PlayerUsageSnooper(final String s, final IPlayerUsage playerStatsCollector, final long minecraftStartTimeMilis) {
        this.field_152773_a = (Map<String, Object>)Maps.newHashMap();
        this.field_152774_b = (Map<String, Object>)Maps.newHashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer(PlayerUsageSnooper.I["".length()], " ".length() != 0);
        this.syncLock = new Object();
        try {
            this.serverUrl = new URL(PlayerUsageSnooper.I[" ".length()] + s + PlayerUsageSnooper.I["  ".length()] + "  ".length());
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = playerStatsCollector;
        this.minecraftStartTimeMilis = minecraftStartTimeMilis;
    }
    
    public void addStatToSnooper(final String s, final Object o) {
        synchronized (this.syncLock) {
            this.field_152773_a.put(s, o);
            // monitorexit(this.syncLock)
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
    }
    
    static int access$3(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter;
    }
    
    static URL access$7(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.serverUrl;
    }
    
    static String access$6(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.uniqueID;
    }
    
    static Map access$4(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152773_a;
    }
    
    public boolean isSnooperRunning() {
        return this.isRunning;
    }
    
    private static void I() {
        (I = new String[0xA2 ^ 0xB5])["".length()] = I("\u0018>\u0000*&.\"O\u0011?&5\u001d", "KPoEV");
        PlayerUsageSnooper.I[" ".length()] = I("\u0004\u0004\u00166^C_\u0011(\u000b\u0003\u0000L+\r\u0002\u0015\u00014\u0005\n\u0004L(\u0001\u0018_", "lpbFd");
        PlayerUsageSnooper.I["  ".length()] = I("K4\u0016\n6\u001d-\u001dE", "tBsxE");
        PlayerUsageSnooper.I["   ".length()] = I("\u0004/\t\u0015\u0003\u001239\u000e\u001c\u001c$\b", "wAfzs");
        PlayerUsageSnooper.I[0x3D ^ 0x39] = I("\u001b?:\u0018?\r#\n\u0003 \u00034;", "hQUwO");
        PlayerUsageSnooper.I[0xA0 ^ 0xA5] = I("\u001a:6\u0005-\u0018,", "uIikL");
        PlayerUsageSnooper.I[0x19 ^ 0x1F] = I("\b\u0003b*\u0013\n\u0015", "gpLDr");
        PlayerUsageSnooper.I[0x91 ^ 0x96] = I(" \u0010\u001c\u0013\u0017=\u0010*\n\u001c", "OcCer");
        PlayerUsageSnooper.I[0x21 ^ 0x29] = I("\u0000\u0019z\u00022\u001d\u0019=\u001b9", "ojTtW");
        PlayerUsageSnooper.I[0x40 ^ 0x49] = I("=\"(\u0006\u001e19\u001e\u0013\t1%\u0002\u0015\t", "RQwgl");
        PlayerUsageSnooper.I[0x72 ^ 0x78] = I("\b=Y++\u0004&", "gNwJY");
        PlayerUsageSnooper.I[0x3F ^ 0x34] = I("\f6?\u0016\u000f\u00102;\u00049\t9", "fWIwP");
        PlayerUsageSnooper.I[0xCB ^ 0xC7] = I("\u0004\u001889c\u0018\u001c<+$\u0001\u0017", "nyNXM");
        PlayerUsageSnooper.I[0x11 ^ 0x1C] = I(":=:%\u001d#6", "LXHVt");
        PlayerUsageSnooper.I[0x7C ^ 0x72] = I("dwjLk", "UYRbS");
        PlayerUsageSnooper.I[0x41 ^ 0x4E] = I("x)", "Uqdbe");
        PlayerUsageSnooper.I[0x5D ^ 0x4D] = I("\u0004$\u0007\u0014-\u001c51", "nRjKL");
        PlayerUsageSnooper.I[0xBC ^ 0xAD] = I("+", "vgiaq");
        PlayerUsageSnooper.I[0x5E ^ 0x4C] = I("\u001c\u0007>\u0012\u0018\u0004\u0016 ", "vqSMy");
        PlayerUsageSnooper.I[0x8D ^ 0x9E] = I("*\u00004\n9>:-\n?&\t", "GeYeK");
        PlayerUsageSnooper.I[0x57 ^ 0x43] = I("\u0007\u0007\u001c\u00073\u0013=\u001c\t9", "jbqhA");
        PlayerUsageSnooper.I[0x27 ^ 0x32] = I(",!\u000b\u001b 8\u001b\u0000\u00067$", "ADftR");
        PlayerUsageSnooper.I[0x2D ^ 0x3B] = I("\u0005'\"\u00065\t%2*", "fWWYV");
    }
    
    public void stopSnooper() {
        this.threadTrigger.cancel();
    }
    
    public void addClientStat(final String s, final Object o) {
        synchronized (this.syncLock) {
            this.field_152774_b.put(s, o);
            // monitorexit(this.syncLock)
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
    }
    
    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.addClientStat(PlayerUsageSnooper.I["   ".length()], this.uniqueID);
        this.addStatToSnooper(PlayerUsageSnooper.I[0xC1 ^ 0xC5], this.uniqueID);
        this.addStatToSnooper(PlayerUsageSnooper.I[0x18 ^ 0x1D], System.getProperty(PlayerUsageSnooper.I[0x4C ^ 0x4A]));
        this.addStatToSnooper(PlayerUsageSnooper.I[0x16 ^ 0x11], System.getProperty(PlayerUsageSnooper.I[0x20 ^ 0x28]));
        this.addStatToSnooper(PlayerUsageSnooper.I[0xA ^ 0x3], System.getProperty(PlayerUsageSnooper.I[0x80 ^ 0x8A]));
        this.addStatToSnooper(PlayerUsageSnooper.I[0x6A ^ 0x61], System.getProperty(PlayerUsageSnooper.I[0x8E ^ 0x82]));
        this.addClientStat(PlayerUsageSnooper.I[0x81 ^ 0x8C], PlayerUsageSnooper.I[0x48 ^ 0x46]);
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    static IPlayerUsage access$0(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.playerStatsCollector;
    }
    
    static Object access$1(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.syncLock;
    }
    
    static {
        I();
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = (" ".length() != 0);
            this.func_152766_h();
            this.threadTrigger.schedule(new TimerTask(this) {
                final PlayerUsageSnooper this$0;
                private static final String[] I;
                
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
                        if (true != true) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                static {
                    I();
                }
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("4*\r\u000e\u0012\"6=\u0002\r2*\u0016", "GDbab");
                    PlayerUsageSnooper$1.I[" ".length()] = I("+\u001b5;\u0006=\u0007\u0005 \u00193\u00104", "XuZTv");
                }
                
                @Override
                public void run() {
                    if (PlayerUsageSnooper.access$0(this.this$0).isSnooperEnabled()) {
                        final HashMap hashMap;
                        synchronized (PlayerUsageSnooper.access$1(this.this$0)) {
                            hashMap = Maps.newHashMap(PlayerUsageSnooper.access$2(this.this$0));
                            if (PlayerUsageSnooper.access$3(this.this$0) == 0) {
                                hashMap.putAll(PlayerUsageSnooper.access$4(this.this$0));
                            }
                            final HashMap<Object, Object> hashMap2 = (HashMap<Object, Object>)hashMap;
                            final String s = PlayerUsageSnooper$1.I["".length()];
                            final PlayerUsageSnooper this$0 = this.this$0;
                            final int access$3 = PlayerUsageSnooper.access$3(this$0);
                            PlayerUsageSnooper.access$5(this$0, access$3 + " ".length());
                            hashMap2.put(s, access$3);
                            hashMap.put(PlayerUsageSnooper$1.I[" ".length()], PlayerUsageSnooper.access$6(this.this$0));
                            // monitorexit(PlayerUsageSnooper.access$1(this.this$0))
                            "".length();
                            if (-1 == 4) {
                                throw null;
                            }
                        }
                        HttpUtil.postMap(PlayerUsageSnooper.access$7(this.this$0), hashMap, " ".length() != 0);
                    }
                }
            }, 0L, 900000L);
        }
    }
    
    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
    
    static void access$5(final PlayerUsageSnooper playerUsageSnooper, final int selfCounter) {
        playerUsageSnooper.selfCounter = selfCounter;
    }
    
    public String getUniqueID() {
        return this.uniqueID;
    }
    
    static Map access$2(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152774_b;
    }
    
    public Map<String, String> getCurrentStats() {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        synchronized (this.syncLock) {
            this.addMemoryStatsToSnooper();
            final Iterator<Map.Entry<String, Object>> iterator = this.field_152773_a.entrySet().iterator();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Map.Entry<String, Object> entry = iterator.next();
                linkedHashMap.put(entry.getKey(), entry.getValue().toString());
            }
            final Iterator<Map.Entry<String, Object>> iterator2 = this.field_152774_b.entrySet().iterator();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final Map.Entry<String, Object> entry2 = iterator2.next();
                linkedHashMap.put(entry2.getKey(), entry2.getValue().toString());
            }
            // monitorexit(this.syncLock)
            return (LinkedHashMap<String, String>)linkedHashMap;
        }
    }
}
