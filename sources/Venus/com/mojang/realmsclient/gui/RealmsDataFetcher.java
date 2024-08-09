/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.util.RealmsPersistence;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsDataFetcher {
    private static final Logger field_225088_a = LogManager.getLogger();
    private final ScheduledExecutorService field_225089_b = Executors.newScheduledThreadPool(3);
    private volatile boolean field_225090_c = true;
    private final Runnable field_225091_d = new ServerListUpdateTask(this);
    private final Runnable field_225092_e = new PendingInviteUpdateTask(this);
    private final Runnable field_225093_f = new TrialAvailabilityTask(this);
    private final Runnable field_225094_g = new LiveStatsTask(this);
    private final Runnable field_225095_h = new UnreadNewsTask(this);
    private final Set<RealmsServer> field_225096_i = Sets.newHashSet();
    private List<RealmsServer> field_225097_j = Lists.newArrayList();
    private RealmsServerPlayerLists field_225098_k;
    private int field_225099_l;
    private boolean field_225100_m;
    private boolean field_225101_n;
    private String field_225102_o;
    private ScheduledFuture<?> field_225103_p;
    private ScheduledFuture<?> field_225104_q;
    private ScheduledFuture<?> field_225105_r;
    private ScheduledFuture<?> field_225106_s;
    private ScheduledFuture<?> field_225107_t;
    private final Map<Task, Boolean> field_225108_u = new ConcurrentHashMap<Task, Boolean>(Task.values().length);

    public boolean func_225065_a() {
        return this.field_225090_c;
    }

    public synchronized void func_225086_b() {
        if (this.field_225090_c) {
            this.field_225090_c = false;
            this.func_225084_n();
            this.func_225069_m();
        }
    }

    public synchronized void func_237710_c_() {
        if (this.field_225090_c) {
            this.field_225090_c = false;
            this.func_225084_n();
            this.field_225108_u.put(Task.PENDING_INVITE, false);
            this.field_225104_q = this.field_225089_b.scheduleAtFixedRate(this.field_225092_e, 0L, 10L, TimeUnit.SECONDS);
            this.field_225108_u.put(Task.TRIAL_AVAILABLE, false);
            this.field_225105_r = this.field_225089_b.scheduleAtFixedRate(this.field_225093_f, 0L, 60L, TimeUnit.SECONDS);
            this.field_225108_u.put(Task.UNREAD_NEWS, false);
            this.field_225107_t = this.field_225089_b.scheduleAtFixedRate(this.field_225095_h, 0L, 300L, TimeUnit.SECONDS);
        }
    }

    public boolean func_225083_a(Task task) {
        Boolean bl = this.field_225108_u.get((Object)task);
        return bl == null ? false : bl;
    }

    public void func_225072_c() {
        for (Task task : this.field_225108_u.keySet()) {
            this.field_225108_u.put(task, false);
        }
    }

    public synchronized void func_225087_d() {
        this.func_225070_k();
        this.func_225086_b();
    }

    public synchronized List<RealmsServer> func_225078_e() {
        return Lists.newArrayList(this.field_225097_j);
    }

    public synchronized int func_225081_f() {
        return this.field_225099_l;
    }

    public synchronized boolean func_225071_g() {
        return this.field_225100_m;
    }

    public synchronized RealmsServerPlayerLists func_225079_h() {
        return this.field_225098_k;
    }

    public synchronized boolean func_225059_i() {
        return this.field_225101_n;
    }

    public synchronized String func_225063_j() {
        return this.field_225102_o;
    }

    public synchronized void func_225070_k() {
        this.field_225090_c = true;
        this.func_225084_n();
    }

    private void func_225069_m() {
        for (Task task : Task.values()) {
            this.field_225108_u.put(task, false);
        }
        this.field_225103_p = this.field_225089_b.scheduleAtFixedRate(this.field_225091_d, 0L, 60L, TimeUnit.SECONDS);
        this.field_225104_q = this.field_225089_b.scheduleAtFixedRate(this.field_225092_e, 0L, 10L, TimeUnit.SECONDS);
        this.field_225105_r = this.field_225089_b.scheduleAtFixedRate(this.field_225093_f, 0L, 60L, TimeUnit.SECONDS);
        this.field_225106_s = this.field_225089_b.scheduleAtFixedRate(this.field_225094_g, 0L, 10L, TimeUnit.SECONDS);
        this.field_225107_t = this.field_225089_b.scheduleAtFixedRate(this.field_225095_h, 0L, 300L, TimeUnit.SECONDS);
    }

    private void func_225084_n() {
        try {
            if (this.field_225103_p != null) {
                this.field_225103_p.cancel(false);
            }
            if (this.field_225104_q != null) {
                this.field_225104_q.cancel(false);
            }
            if (this.field_225105_r != null) {
                this.field_225105_r.cancel(false);
            }
            if (this.field_225106_s != null) {
                this.field_225106_s.cancel(false);
            }
            if (this.field_225107_t != null) {
                this.field_225107_t.cancel(false);
            }
        } catch (Exception exception) {
            field_225088_a.error("Failed to cancel Realms tasks", (Throwable)exception);
        }
    }

    private synchronized void func_225080_b(List<RealmsServer> list) {
        int n = 0;
        for (RealmsServer realmsServer : this.field_225096_i) {
            if (!list.remove(realmsServer)) continue;
            ++n;
        }
        if (n == 0) {
            this.field_225096_i.clear();
        }
        this.field_225097_j = list;
    }

    public synchronized void func_225085_a(RealmsServer realmsServer) {
        this.field_225097_j.remove(realmsServer);
        this.field_225096_i.add(realmsServer);
    }

    private boolean func_225068_o() {
        return !this.field_225090_c;
    }

    class ServerListUpdateTask
    implements Runnable {
        final RealmsDataFetcher this$0;

        private ServerListUpdateTask(RealmsDataFetcher realmsDataFetcher) {
            this.this$0 = realmsDataFetcher;
        }

        @Override
        public void run() {
            if (this.this$0.func_225068_o()) {
                this.func_225053_a();
            }
        }

        private void func_225053_a() {
            try {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                List<RealmsServer> list = realmsClient.func_224902_e().field_230605_a_;
                if (list != null) {
                    list.sort(new RealmsServer.ServerComparator(Minecraft.getInstance().getSession().getUsername()));
                    this.this$0.func_225080_b(list);
                    this.this$0.field_225108_u.put(Task.SERVER_LIST, true);
                } else {
                    field_225088_a.warn("Realms server list was null or empty");
                }
            } catch (Exception exception) {
                this.this$0.field_225108_u.put(Task.SERVER_LIST, true);
                field_225088_a.error("Couldn't get server list", (Throwable)exception);
            }
        }
    }

    class PendingInviteUpdateTask
    implements Runnable {
        final RealmsDataFetcher this$0;

        private PendingInviteUpdateTask(RealmsDataFetcher realmsDataFetcher) {
            this.this$0 = realmsDataFetcher;
        }

        @Override
        public void run() {
            if (this.this$0.func_225068_o()) {
                this.func_225051_a();
            }
        }

        private void func_225051_a() {
            try {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                this.this$0.field_225099_l = realmsClient.func_224909_j();
                this.this$0.field_225108_u.put(Task.PENDING_INVITE, true);
            } catch (Exception exception) {
                field_225088_a.error("Couldn't get pending invite count", (Throwable)exception);
            }
        }
    }

    class TrialAvailabilityTask
    implements Runnable {
        final RealmsDataFetcher this$0;

        private TrialAvailabilityTask(RealmsDataFetcher realmsDataFetcher) {
            this.this$0 = realmsDataFetcher;
        }

        @Override
        public void run() {
            if (this.this$0.func_225068_o()) {
                this.func_225055_a();
            }
        }

        private void func_225055_a() {
            try {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                this.this$0.field_225100_m = realmsClient.func_224914_n();
                this.this$0.field_225108_u.put(Task.TRIAL_AVAILABLE, true);
            } catch (Exception exception) {
                field_225088_a.error("Couldn't get trial availability", (Throwable)exception);
            }
        }
    }

    class LiveStatsTask
    implements Runnable {
        final RealmsDataFetcher this$0;

        private LiveStatsTask(RealmsDataFetcher realmsDataFetcher) {
            this.this$0 = realmsDataFetcher;
        }

        @Override
        public void run() {
            if (this.this$0.func_225068_o()) {
                this.func_225048_a();
            }
        }

        private void func_225048_a() {
            try {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                this.this$0.field_225098_k = realmsClient.func_224915_f();
                this.this$0.field_225108_u.put(Task.LIVE_STATS, true);
            } catch (Exception exception) {
                field_225088_a.error("Couldn't get live stats", (Throwable)exception);
            }
        }
    }

    class UnreadNewsTask
    implements Runnable {
        final RealmsDataFetcher this$0;

        private UnreadNewsTask(RealmsDataFetcher realmsDataFetcher) {
            this.this$0 = realmsDataFetcher;
        }

        @Override
        public void run() {
            if (this.this$0.func_225068_o()) {
                this.func_225057_a();
            }
        }

        private void func_225057_a() {
            try {
                String string;
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                RealmsNews realmsNews = null;
                try {
                    realmsNews = realmsClient.func_224920_m();
                } catch (Exception exception) {
                    // empty catch block
                }
                RealmsPersistence.RealmsPersistenceData realmsPersistenceData = RealmsPersistence.func_225188_a();
                if (realmsNews != null && (string = realmsNews.field_230580_a_) != null && !string.equals(realmsPersistenceData.field_225185_a)) {
                    realmsPersistenceData.field_225186_b = true;
                    realmsPersistenceData.field_225185_a = string;
                    RealmsPersistence.func_225187_a(realmsPersistenceData);
                }
                this.this$0.field_225101_n = realmsPersistenceData.field_225186_b;
                this.this$0.field_225102_o = realmsPersistenceData.field_225185_a;
                this.this$0.field_225108_u.put(Task.UNREAD_NEWS, true);
            } catch (Exception exception) {
                field_225088_a.error("Couldn't get unread news", (Throwable)exception);
            }
        }
    }

    public static enum Task {
        SERVER_LIST,
        PENDING_INVITE,
        TRIAL_AVAILABLE,
        LIVE_STATS,
        UNREAD_NEWS;

    }
}

