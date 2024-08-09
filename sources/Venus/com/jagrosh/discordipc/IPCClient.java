/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc;

import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.Pipe;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class IPCClient
implements Closeable {
    private final long clientId;
    private final HashMap<String, Callback> callbacks = new HashMap();
    private volatile Pipe pipe;
    private IPCListener listener = null;
    private Thread readThread = null;

    public IPCClient(long l) {
        this.clientId = l;
    }

    public void setListener(IPCListener iPCListener) {
        this.listener = iPCListener;
        if (this.pipe != null) {
            this.pipe.setListener(iPCListener);
        }
    }

    public void connect(DiscordBuild ... discordBuildArray) throws NoDiscordClientException {
        this.checkConnected(true);
        this.callbacks.clear();
        this.pipe = null;
        this.pipe = Pipe.openPipe(this, this.clientId, this.callbacks, discordBuildArray);
        if (this.listener != null) {
            this.listener.onReady(this);
        }
        this.startReading();
    }

    public void sendRichPresence(RichPresence richPresence) {
        this.sendRichPresence(richPresence, null);
    }

    public void sendRichPresence(RichPresence richPresence, Callback callback) {
        this.checkConnected(false);
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", "SET_ACTIVITY").put("args", new JSONObject().put("pid", IPCClient.getPID()).put("activity", richPresence == null ? null : richPresence.toJson())), callback);
    }

    public void subscribe(Event event) {
        this.subscribe(event, null);
    }

    public void subscribe(Event event, Callback callback) {
        this.checkConnected(false);
        if (!event.isSubscribable()) {
            throw new IllegalStateException("Cannot subscribe to " + event + " event!");
        }
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", "SUBSCRIBE").put("evt", event.name()), callback);
    }

    public PipeStatus getStatus() {
        if (this.pipe == null) {
            return PipeStatus.UNINITIALIZED;
        }
        return this.pipe.getStatus();
    }

    @Override
    public void close() {
        this.checkConnected(false);
        try {
            this.pipe.close();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    public DiscordBuild getDiscordBuild() {
        if (this.pipe == null) {
            return null;
        }
        return this.pipe.getDiscordBuild();
    }

    private void checkConnected(boolean bl) {
        if (bl && this.getStatus() != PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is not connected!", this.clientId));
        }
        if (!bl && this.getStatus() == PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is already connected!", this.clientId));
        }
    }

    private void startReading() {
        this.readThread = new Thread(this::lambda$startReading$0);
        this.readThread.start();
    }

    private static int getPID() {
        String string = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(string.substring(0, string.indexOf(64)));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lambda$startReading$0() {
        try {
            while (true) lbl-1000:
            // 5 sources

            {
                if ((var1_1 = this.pipe.read()).getOp() == Packet.OpCode.CLOSE) {
                    this.pipe.setStatus(PipeStatus.DISCONNECTED);
                    if (this.listener == null) return;
                    this.listener.onClose(this, var1_1.getJson());
                    return;
                }
                var2_3 = var1_1.getJson();
                var3_4 = Event.of(var2_3.optString("evt", null));
                var4_5 = var2_3.optString("nonce", null);
                switch (1.$SwitchMap$com$jagrosh$discordipc$IPCClient$Event[var3_4.ordinal()]) {
                    case 1: {
                        break;
                    }
                    case 2: {
                        if (var4_5 == null || !this.callbacks.containsKey(var4_5)) break;
                        this.callbacks.remove(var4_5).succeed(var1_1);
                        break;
                    }
                    case 3: {
                        if (var4_5 == null || !this.callbacks.containsKey(var4_5)) break;
                        this.callbacks.remove(var4_5).fail(var2_3.getJSONObject("data").optString("message", null));
                        break;
                    }
                    case 4: {
                        break;
                    }
                    case 5: {
                        break;
                    }
                    case 6: {
                        break;
                    }
                }
                if (this.listener == null || !var2_3.has("cmd") || !var2_3.getString("cmd").equals("DISPATCH")) continue;
                try {
                    var5_6 = var2_3.getJSONObject("data");
                    switch (1.$SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.of(var2_3.getString("evt")).ordinal()]) {
                        case 4: {
                            this.listener.onActivityJoin(this, var5_6.getString("secret"));
                            break;
                        }
                        case 5: {
                            this.listener.onActivitySpectate(this, var5_6.getString("secret"));
                            break;
                        }
                        case 6: {
                            var6_8 = var5_6.getJSONObject("user");
                            var7_9 = new User(var6_8.getString("username"), var6_8.getString("discriminator"), Long.parseLong(var6_8.getString("id")), var6_8.optString("avatar", null));
                            this.listener.onActivityJoinRequest(this, var5_6.optString("secret", null), var7_9);
                        }
                    }
                } catch (Exception var5_7) {
                    continue;
                }
                break;
            }
        } catch (IOException | JSONException var1_2) {
            this.pipe.setStatus(PipeStatus.DISCONNECTED);
            if (this.listener == null) return;
            this.listener.onDisconnect(this, var1_2);
        }
        ** GOTO lbl-1000
    }

    public static enum Event {
        NULL(false),
        READY(false),
        ERROR(false),
        ACTIVITY_JOIN(true),
        ACTIVITY_SPECTATE(true),
        ACTIVITY_JOIN_REQUEST(true),
        UNKNOWN(false);

        private final boolean subscribable;

        private Event(boolean bl) {
            this.subscribable = bl;
        }

        public boolean isSubscribable() {
            return this.subscribable;
        }

        static Event of(String string) {
            if (string == null) {
                return NULL;
            }
            for (Event event : Event.values()) {
                if (event == UNKNOWN || !event.name().equalsIgnoreCase(string)) continue;
                return event;
            }
            return UNKNOWN;
        }
    }
}

