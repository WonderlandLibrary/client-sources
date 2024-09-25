/*
 * Decompiled with CFR 0.150.
 */
package skizzle;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import skizzle.Client;

public class DiscordRP {
    public boolean running = true;
    public long created = (long)1058796340 ^ 0x3F1BF334L;

    public static boolean access$0(DiscordRP discordRP) {
        return discordRP.running;
    }

    public static {
        throw throwable;
    }

    public void shutdown() {
        Nigga.running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String Nigga, String Nigga2) {
        DiscordRP Nigga3;
        DiscordRichPresence.Builder Nigga4 = new DiscordRichPresence.Builder(Nigga2);
        Nigga4.setBigImage(Qprot0.0("\uae3a\u71ca\u9558\u9d58\ufbb7"), Qprot0.0("\uae05\u71c0\u9543\u9d45\ufba8\u2b80\u8c2a\uc233\u6de8\u50b6\ua1f9\uaf4c") + Client.version);
        Nigga4.setDetails(Nigga);
        Nigga4.setStartTimestamps(Nigga3.created);
        DiscordRPC.discordUpdatePresence(Nigga4.build());
    }

    public void start() {
        DiscordRP Nigga;
        Nigga.created = System.currentTimeMillis();
        DiscordEventHandlers Nigga2 = new DiscordEventHandlers.Builder().setReadyEventHandler(DiscordRP::lambda$0).build();
        DiscordRPC.discordInitialize(Qprot0.0("\uae6e\u7199\u9519\u61a1\u4f80\u2bd8\u8c7a\uc222\u9141\ue4cd\ua1f2\uaf55\u2cf9\ub409\u3bba\u3267\u42b0\u1806"), Nigga2, true);
        new Thread(Nigga, Qprot0.0("\uae12\u71c2\u9559\u61f7\u4fda\u2b9e\u8c2b\uc233\u9120\ue4af\ua182\uaf4c\u2c8b\ub45c\u3be0\u323f\u42eb\u1850\ud113\u509f")){
            public DiscordRP this$0;
            {
                1 Nigga2;
                Nigga2.this$0 = discordRP;
                super(Nigga);
            }

            @Override
            public void run() {
                1 Nigga;
                while (DiscordRP.access$0(Nigga.this$0)) {
                    DiscordRPC.discordRunCallbacks();
                }
            }

            public static {
                throw throwable;
            }
        }.start();
    }

    public static void lambda$0(DiscordUser Nigga) {
        Client.discordUser = Nigga;
    }

    public DiscordRP() {
        DiscordRP Nigga;
    }
}

