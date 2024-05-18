package net.minecraft.server.management;

import java.io.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.mojang.authlib.*;

public class PreYggdrasilConverter
{
    public static final File OLD_OPS_FILE;
    private static final Logger LOGGER;
    public static final File OLD_PLAYERBAN_FILE;
    public static final File OLD_WHITELIST_FILE;
    public static final File OLD_IPBAN_FILE;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static String getStringUUIDFromName(final String s) {
        if (StringUtils.isNullOrEmpty(s) || s.length() > (0xBF ^ 0xAF)) {
            return s;
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(s);
        if (gameProfileForUsername != null && gameProfileForUsername.getId() != null) {
            return gameProfileForUsername.getId().toString();
        }
        if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
            final ArrayList arrayList = Lists.newArrayList();
            final ProfileLookupCallback profileLookupCallback = (ProfileLookupCallback)new ProfileLookupCallback(server, arrayList) {
                private static final String[] I;
                private final MinecraftServer val$minecraftserver;
                private final List val$list;
                
                public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                    this.val$minecraftserver.getPlayerProfileCache().addEntry(gameProfile);
                    this.val$list.add(gameProfile);
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
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                static {
                    I();
                }
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("\u000b+\u001e\u0004\"h*\u0004\u001cf$+\u0004\u000338d\u001e\u001b#:d\u001c\u0000/<!\u0007\u00015<d\u000e\u00062:=K\u000e):d", "HDkhF");
                }
                
                public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                    PreYggdrasilConverter.access$0().warn(PreYggdrasilConverter$2.I["".length()] + gameProfile.getName(), (Throwable)ex);
                }
            };
            final MinecraftServer minecraftServer = server;
            final String[] array = new String[" ".length()];
            array["".length()] = s;
            lookupNames(minecraftServer, Lists.newArrayList((Object[])array), (ProfileLookupCallback)profileLookupCallback);
            String string;
            if (arrayList.size() > 0 && ((GameProfile)arrayList.get("".length())).getId() != null) {
                string = arrayList.get("".length()).getId().toString();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                string = PreYggdrasilConverter.I[0x4 ^ 0x0];
            }
            return string;
        }
        return EntityPlayer.getUUID(new GameProfile((UUID)null, s)).toString();
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
        OLD_IPBAN_FILE = new File(PreYggdrasilConverter.I["".length()]);
        OLD_PLAYERBAN_FILE = new File(PreYggdrasilConverter.I[" ".length()]);
        OLD_OPS_FILE = new File(PreYggdrasilConverter.I["  ".length()]);
        OLD_WHITELIST_FILE = new File(PreYggdrasilConverter.I["   ".length()]);
    }
    
    static Logger access$0() {
        return PreYggdrasilConverter.LOGGER;
    }
    
    private static void I() {
        (I = new String[0xC0 ^ 0xC5])["".length()] = I("2(9$\u00024d>:\u0014~=/>", "PIWJg");
        PreYggdrasilConverter.I[" ".length()] = I("4\u0012()?2^6+;/\u001644t\"\u000b2", "VsFGZ");
        PreYggdrasilConverter.I["  ".length()] = I("#\"6|-4&", "LRERY");
        PreYggdrasilConverter.I["   ".length()] = I("\u0014\u001b.\u001d\u0010N\u001f.\u001a\u0001M\u0007?\u001d", "csGiu");
        PreYggdrasilConverter.I[0x4 ^ 0x0] = I("", "BwRnj");
    }
    
    private static void lookupNames(final MinecraftServer minecraftServer, final Collection<String> collection, final ProfileLookupCallback profileLookupCallback) {
        final String[] array = (String[])Iterators.toArray((Iterator)Iterators.filter((Iterator)collection.iterator(), (Predicate)new Predicate<String>() {
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final String s) {
                int n;
                if (StringUtils.isNullOrEmpty(s)) {
                    n = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n = " ".length();
                }
                return n != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((String)o);
            }
        }), (Class)String.class);
        if (minecraftServer.isServerInOnlineMode()) {
            minecraftServer.getGameProfileRepository().findProfilesByNames(array, Agent.MINECRAFT, profileLookupCallback);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            final String[] array2;
            final int length = (array2 = array).length;
            int i = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i < length) {
                final String s = array2[i];
                profileLookupCallback.onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile((UUID)null, s)), s));
                ++i;
            }
        }
    }
}
