/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.entities.pipe.WindowsPipe;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Pipe {
    private static final int VERSION = 1;
    PipeStatus status = PipeStatus.CONNECTING;
    IPCListener listener;
    private DiscordBuild build;
    final IPCClient ipcClient;
    private final HashMap<String, Callback> callbacks;
    private static final String[] unixPaths = new String[]{"XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP"};

    Pipe(IPCClient iPCClient, HashMap<String, Callback> hashMap) {
        this.ipcClient = iPCClient;
        this.callbacks = hashMap;
    }

    public static Pipe openPipe(IPCClient iPCClient, long l, HashMap<String, Callback> hashMap, DiscordBuild ... discordBuildArray) throws NoDiscordClientException {
        Object object;
        int n;
        if (discordBuildArray == null || discordBuildArray.length == 0) {
            discordBuildArray = new DiscordBuild[]{DiscordBuild.ANY};
        }
        Pipe pipe = null;
        Pipe[] pipeArray = new Pipe[DiscordBuild.values().length];
        for (n = 0; n < 10; ++n) {
            try {
                object = Pipe.getPipeLocation(n);
                pipe = Pipe.createPipe(iPCClient, hashMap, object);
                pipe.send(Packet.OpCode.HANDSHAKE, new JSONObject().put("v", 1).put("client_id", Long.toString(l)), null);
                Packet packet = pipe.read();
                pipe.build = DiscordBuild.from(packet.getJson().getJSONObject("data").getJSONObject("config").getString("api_endpoint"));
                if (pipe.build == discordBuildArray[0] || DiscordBuild.ANY == discordBuildArray[0]) break;
                pipeArray[pipe.build.ordinal()] = pipe;
                pipeArray[DiscordBuild.ANY.ordinal()] = pipe;
                pipe.build = null;
                pipe = null;
                continue;
            } catch (IOException | JSONException exception) {
                pipe = null;
            }
        }
        if (pipe == null) {
            for (n = 1; n < discordBuildArray.length; ++n) {
                object = discordBuildArray[n];
                if (pipeArray[((Enum)object).ordinal()] == null) continue;
                pipe = pipeArray[((Enum)object).ordinal()];
                pipeArray[((Enum)object).ordinal()] = null;
                if (object == DiscordBuild.ANY) {
                    for (int i = 0; i < pipeArray.length; ++i) {
                        if (pipeArray[i] != pipe) continue;
                        pipe.build = DiscordBuild.values()[i];
                        pipeArray[i] = null;
                    }
                    break;
                }
                pipe.build = object;
                break;
            }
            if (pipe == null) {
                throw new NoDiscordClientException();
            }
        }
        for (n = 0; n < pipeArray.length; ++n) {
            if (n == DiscordBuild.ANY.ordinal() || pipeArray[n] == null) continue;
            try {
                pipeArray[n].close();
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        pipe.status = PipeStatus.CONNECTED;
        return pipe;
    }

    private static Pipe createPipe(IPCClient iPCClient, HashMap<String, Callback> hashMap, String string) {
        String string2 = System.getProperty("os.name").toLowerCase();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(string, "rw");
        } catch (FileNotFoundException fileNotFoundException) {
            return new Pipe(iPCClient, (HashMap)hashMap){

                @Override
                public Packet read() throws IOException, JSONException {
                    return new Packet(Packet.OpCode.CLOSE, new JSONObject());
                }

                @Override
                public void write(byte[] byArray) throws IOException {
                }

                @Override
                public void close() throws IOException {
                }
            };
        }
        if (string2.contains("win")) {
            return new WindowsPipe(iPCClient, hashMap, string);
        }
        throw new RuntimeException("Unsupported OS: " + string2);
    }

    public void send(Packet.OpCode opCode, JSONObject jSONObject, Callback callback) {
        try {
            String string = Pipe.generateNonce();
            Packet packet = new Packet(opCode, jSONObject.put("nonce", string));
            if (callback != null && !callback.isEmpty()) {
                this.callbacks.put(string, callback);
            }
            this.write(packet.toBytes());
            if (this.listener != null) {
                this.listener.onPacketSent(this.ipcClient, packet);
            }
        } catch (IOException iOException) {
            this.status = PipeStatus.DISCONNECTED;
        }
    }

    public abstract Packet read() throws IOException, JSONException;

    public abstract void write(byte[] var1) throws IOException;

    private static String generateNonce() {
        return UUID.randomUUID().toString();
    }

    public PipeStatus getStatus() {
        return this.status;
    }

    public void setStatus(PipeStatus pipeStatus) {
        this.status = pipeStatus;
    }

    public void setListener(IPCListener iPCListener) {
        this.listener = iPCListener;
    }

    public abstract void close() throws IOException;

    public DiscordBuild getDiscordBuild() {
        return this.build;
    }

    private static String getPipeLocation(int n) {
        String string;
        if (System.getProperty("os.name").contains("Win")) {
            return "\\\\?\\pipe\\discord-ipc-" + n;
        }
        String string2 = null;
        String[] stringArray = unixPaths;
        int n2 = stringArray.length;
        for (int i = 0; i < n2 && (string2 = System.getenv(string = stringArray[i])) == null; ++i) {
        }
        if (string2 == null) {
            string2 = "/tmp";
        }
        return string2 + "/discord-ipc-" + n;
    }
}

