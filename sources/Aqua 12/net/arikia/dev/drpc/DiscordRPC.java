// 
// Decompiled by Procyon v0.5.36
// 

package net.arikia.dev.drpc;

import com.sun.jna.Native;
import com.sun.jna.Library;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;

public final class DiscordRPC
{
    private static final String DLL_VERSION = "3.4.0";
    private static final String LIB_VERSION = "1.6.2";
    
    public static void discordInitialize(final String applicationId, final DiscordEventHandlers handlers, final boolean autoRegister) {
        DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, null);
    }
    
    public static void discordRegister(final String applicationId, final String command) {
        DLL.INSTANCE.Discord_Register(applicationId, command);
    }
    
    public static void discordInitialize(final String applicationId, final DiscordEventHandlers handlers, final boolean autoRegister, final String steamId) {
        DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, steamId);
    }
    
    public static void discordRegisterSteam(final String applicationId, final String steamId) {
        DLL.INSTANCE.Discord_RegisterSteamGame(applicationId, steamId);
    }
    
    public static void discordUpdateEventHandlers(final DiscordEventHandlers handlers) {
        DLL.INSTANCE.Discord_UpdateHandlers(handlers);
    }
    
    public static void discordShutdown() {
        DLL.INSTANCE.Discord_Shutdown();
    }
    
    public static void discordRunCallbacks() {
        DLL.INSTANCE.Discord_RunCallbacks();
    }
    
    public static void discordUpdatePresence(final DiscordRichPresence presence) {
        DLL.INSTANCE.Discord_UpdatePresence(presence);
    }
    
    public static void discordClearPresence() {
        DLL.INSTANCE.Discord_ClearPresence();
    }
    
    public static void discordRespond(final String userId, final DiscordReply reply) {
        DLL.INSTANCE.Discord_Respond(userId, reply.reply);
    }
    
    private static void loadDLL() {
        final String name = System.mapLibraryName("discord-rpc");
        final OSUtil osUtil = new OSUtil();
        String dir;
        String tempPath;
        if (osUtil.isMac()) {
            final File homeDir = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
            dir = "darwin";
            tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
        }
        else if (osUtil.isWindows()) {
            final File homeDir = new File(System.getenv("TEMP"));
            final boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
            dir = (is64bit ? "win-x64" : "win-x86");
            tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
        }
        else {
            final File homeDir = new File(System.getProperty("user.home"), ".discord-rpc");
            dir = "linux";
            tempPath = homeDir + File.separator + name;
        }
        final String finalPath = "/" + dir + "/" + name;
        final File f = new File(tempPath);
        try {
            final InputStream in = DiscordRPC.class.getResourceAsStream(finalPath);
            try {
                final OutputStream out = openOutputStream(f);
                try {
                    copyFile(in, out);
                    f.deleteOnExit();
                    if (out != null) {
                        out.close();
                    }
                }
                catch (Throwable t) {
                    if (out != null) {
                        try {
                            out.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (Throwable t2) {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.load(f.getAbsolutePath());
    }
    
    private static void copyFile(final InputStream input, final OutputStream output) throws IOException {
        final byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }
    
    private static FileOutputStream openOutputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        else {
            final File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
        return new FileOutputStream(file);
    }
    
    static {
        loadDLL();
    }
    
    public enum DiscordReply
    {
        NO(0), 
        YES(1), 
        IGNORE(2);
        
        public final int reply;
        
        private DiscordReply(final int reply) {
            this.reply = reply;
        }
    }
    
    private interface DLL extends Library
    {
        public static final DLL INSTANCE = Native.loadLibrary("discord-rpc", DLL.class);
        
        void Discord_Initialize(final String p0, final DiscordEventHandlers p1, final int p2, final String p3);
        
        void Discord_Register(final String p0, final String p1);
        
        void Discord_RegisterSteamGame(final String p0, final String p1);
        
        void Discord_UpdateHandlers(final DiscordEventHandlers p0);
        
        void Discord_Shutdown();
        
        void Discord_RunCallbacks();
        
        void Discord_UpdatePresence(final DiscordRichPresence p0);
        
        void Discord_ClearPresence();
        
        void Discord_Respond(final String p0, final int p1);
    }
}
