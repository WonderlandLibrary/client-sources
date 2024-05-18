package net.minecraft.client.main;

import java.net.*;
import net.minecraft.util.*;
import com.mojang.authlib.properties.*;
import java.io.*;

public class GameConfiguration
{
    public final ServerInformation serverInfo;
    public final UserInformation userInfo;
    public final GameInformation gameInfo;
    public final DisplayInformation displayInfo;
    public final FolderInformation folderInfo;
    
    public GameConfiguration(final UserInformation userInfo, final DisplayInformation displayInfo, final FolderInformation folderInfo, final GameInformation gameInfo, final ServerInformation serverInfo) {
        this.userInfo = userInfo;
        this.displayInfo = displayInfo;
        this.folderInfo = folderInfo;
        this.gameInfo = gameInfo;
        this.serverInfo = serverInfo;
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class DisplayInformation
    {
        public final boolean checkGlErrors;
        public final boolean fullscreen;
        public final int width;
        public final int height;
        
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public DisplayInformation(final int width, final int height, final boolean fullscreen, final boolean checkGlErrors) {
            this.width = width;
            this.height = height;
            this.fullscreen = fullscreen;
            this.checkGlErrors = checkGlErrors;
        }
    }
    
    public static class ServerInformation
    {
        public final String serverName;
        public final int serverPort;
        
        public ServerInformation(final String serverName, final int serverPort) {
            this.serverName = serverName;
            this.serverPort = serverPort;
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class GameInformation
    {
        public final String version;
        public final boolean isDemo;
        
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
        
        public GameInformation(final boolean isDemo, final String version) {
            this.isDemo = isDemo;
            this.version = version;
        }
    }
    
    public static class UserInformation
    {
        public final Proxy proxy;
        public final Session session;
        public final PropertyMap userProperties;
        public final PropertyMap field_181172_c;
        
        public UserInformation(final Session session, final PropertyMap userProperties, final PropertyMap field_181172_c, final Proxy proxy) {
            this.session = session;
            this.userProperties = userProperties;
            this.field_181172_c = field_181172_c;
            this.proxy = proxy;
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class FolderInformation
    {
        public final File assetsDir;
        public final File mcDataDir;
        public final String assetIndex;
        public final File resourcePacksDir;
        
        public FolderInformation(final File mcDataDir, final File resourcePacksDir, final File assetsDir, final String assetIndex) {
            this.mcDataDir = mcDataDir;
            this.resourcePacksDir = resourcePacksDir;
            this.assetsDir = assetsDir;
            this.assetIndex = assetIndex;
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
