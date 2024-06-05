/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Library
 *  com.sun.jna.Native
 */
package net.arikia.dev.drpc;

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.OSUtil;

public final class DiscordRPC {
    private static final String DLL_VERSION = "3.4.0";
    private static final String LIB_VERSION = "1.6.2";

    public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister) {
        DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, null);
    }

    public static void discordRegister(String applicationId, String command) {
        DLL.INSTANCE.Discord_Register(applicationId, command);
    }

    public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId) {
        DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, steamId);
    }

    public static void discordRegisterSteam(String applicationId, String steamId) {
        DLL.INSTANCE.Discord_RegisterSteamGame(applicationId, steamId);
    }

    public static void discordUpdateEventHandlers(DiscordEventHandlers handlers) {
        DLL.INSTANCE.Discord_UpdateHandlers(handlers);
    }

    public static void discordShutdown() {
        DLL.INSTANCE.Discord_Shutdown();
    }

    public static void discordRunCallbacks() {
        DLL.INSTANCE.Discord_RunCallbacks();
    }

    public static void discordUpdatePresence(DiscordRichPresence presence) {
        DLL.INSTANCE.Discord_UpdatePresence(presence);
    }

    public static void discordClearPresence() {
        DLL.INSTANCE.Discord_ClearPresence();
    }

    public static void discordRespond(String userId, DiscordReply reply) {
        DLL.INSTANCE.Discord_Respond(userId, reply.reply);
    }

    private static void loadDLL() {
        String tempPath;
        String dir;
        File homeDir;
        String name = System.mapLibraryName("discord-rpc");
        OSUtil osUtil = new OSUtil();
        if (osUtil.isMac()) {
            homeDir = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
            dir = "darwin";
            tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
        } else if (osUtil.isWindows()) {
            homeDir = new File(System.getenv("TEMP"));
            boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
            dir = is64bit ? "win-x64" : "win-x86";
            tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
        } else {
            homeDir = new File(System.getProperty("user.home"), ".discord-rpc");
            dir = "linux";
            tempPath = homeDir + File.separator + name;
        }
        String finalPath = "/" + dir + "/" + name;
        File f = new File(tempPath);
        try {
            InputStream in = DiscordRPC.class.getResourceAsStream(finalPath);
            try {
                FileOutputStream out = DiscordRPC.openOutputStream(f);
                try {
                    DiscordRPC.copyFile(in, out);
                    f.deleteOnExit();
                    if (out != null) {
                        ((OutputStream)out).close();
                    }
                }
                catch (Throwable throwable) {
                    if (out == null) throw throwable;
                    try {
                        ((OutputStream)out).close();
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (Throwable throwable3) {
                if (in == null) throw throwable3;
                try {
                    in.close();
                    throw throwable3;
                }
                catch (Throwable throwable4) {
                    throwable3.addSuppressed(throwable4);
                }
                throw throwable3;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.load(f.getAbsolutePath());
    }

    private static void copyFile(InputStream input, OutputStream output) throws IOException {
        int n;
        byte[] buffer = new byte[4096];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    private static FileOutputStream openOutputStream(File file) throws IOException {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent == null) return new FileOutputStream(file);
            if (parent.mkdirs()) return new FileOutputStream(file);
            if (parent.isDirectory()) return new FileOutputStream(file);
            throw new IOException("Directory '" + parent + "' could not be created");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (file.canWrite()) return new FileOutputStream(file);
        throw new IOException("File '" + file + "' cannot be written to");
    }

    static {
        DiscordRPC.loadDLL();
    }

    private static interface DLL
    extends Library {
        public static final DLL INSTANCE = (DLL)Native.loadLibrary((String)"discord-rpc", DLL.class);

        public void Discord_Initialize(String var1, DiscordEventHandlers var2, int var3, String var4);

        public void Discord_Register(String var1, String var2);

        public void Discord_RegisterSteamGame(String var1, String var2);

        public void Discord_UpdateHandlers(DiscordEventHandlers var1);

        public void Discord_Shutdown();

        public void Discord_RunCallbacks();

        public void Discord_UpdatePresence(DiscordRichPresence var1);

        public void Discord_ClearPresence();

        public void Discord_Respond(String var1, int var2);
    }

    public static enum DiscordReply {
        NO(0),
        YES(1),
        IGNORE(2);

        public final int reply;

        private DiscordReply(int reply) {
            this.reply = reply;
        }
    }
}

