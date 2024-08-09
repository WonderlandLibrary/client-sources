/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.screens.RealmsClientOutdatedScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsParentalConsentScreen;
import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
import com.mojang.realmsclient.util.RealmsPersistence;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.KeyCombo;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.ConnectingToRealmsAction;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsMainScreen
extends RealmsScreen {
    private static final Logger field_224012_a = LogManager.getLogger();
    private static final ResourceLocation field_237540_b_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
    private static final ResourceLocation field_237541_c_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
    private static final ResourceLocation field_237542_p_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
    private static final ResourceLocation field_237543_q_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
    private static final ResourceLocation field_237544_r_ = new ResourceLocation("realms", "textures/gui/realms/leave_icon.png");
    private static final ResourceLocation field_237545_s_ = new ResourceLocation("realms", "textures/gui/realms/invitation_icons.png");
    private static final ResourceLocation field_237546_t_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
    private static final ResourceLocation field_237547_u_ = new ResourceLocation("realms", "textures/gui/realms/world_icon.png");
    private static final ResourceLocation field_237548_v_ = new ResourceLocation("realms", "textures/gui/title/realms.png");
    private static final ResourceLocation field_237549_w_ = new ResourceLocation("realms", "textures/gui/realms/configure_icon.png");
    private static final ResourceLocation field_237550_x_ = new ResourceLocation("realms", "textures/gui/realms/questionmark.png");
    private static final ResourceLocation field_237551_y_ = new ResourceLocation("realms", "textures/gui/realms/news_icon.png");
    private static final ResourceLocation field_237552_z_ = new ResourceLocation("realms", "textures/gui/realms/popup.png");
    private static final ResourceLocation field_237534_A_ = new ResourceLocation("realms", "textures/gui/realms/darken.png");
    private static final ResourceLocation field_237535_B_ = new ResourceLocation("realms", "textures/gui/realms/cross_icon.png");
    private static final ResourceLocation field_237536_C_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
    private static final ResourceLocation field_237537_D_ = new ResourceLocation("minecraft", "textures/gui/widgets.png");
    private static final ITextComponent field_243000_E = new TranslationTextComponent("mco.invites.nopending");
    private static final ITextComponent field_243001_F = new TranslationTextComponent("mco.invites.pending");
    private static final List<ITextComponent> field_243002_G = ImmutableList.of(new TranslationTextComponent("mco.trial.message.line1"), new TranslationTextComponent("mco.trial.message.line2"));
    private static final ITextComponent field_243003_H = new TranslationTextComponent("mco.selectServer.uninitialized");
    private static final ITextComponent field_243004_I = new TranslationTextComponent("mco.selectServer.expiredList");
    private static final ITextComponent field_243005_J = new TranslationTextComponent("mco.selectServer.expiredRenew");
    private static final ITextComponent field_243006_K = new TranslationTextComponent("mco.selectServer.expiredTrial");
    private static final ITextComponent field_243007_L = new TranslationTextComponent("mco.selectServer.expiredSubscribe");
    private static final ITextComponent field_243008_M = new TranslationTextComponent("mco.selectServer.minigame").appendString(" ");
    private static final ITextComponent field_243009_N = new TranslationTextComponent("mco.selectServer.popup");
    private static final ITextComponent field_243010_O = new TranslationTextComponent("mco.selectServer.expired");
    private static final ITextComponent field_243011_P = new TranslationTextComponent("mco.selectServer.expires.soon");
    private static final ITextComponent field_243012_Q = new TranslationTextComponent("mco.selectServer.expires.day");
    private static final ITextComponent field_243013_R = new TranslationTextComponent("mco.selectServer.open");
    private static final ITextComponent field_243014_S = new TranslationTextComponent("mco.selectServer.closed");
    private static final ITextComponent field_243015_T = new TranslationTextComponent("mco.selectServer.leave");
    private static final ITextComponent field_243016_U = new TranslationTextComponent("mco.selectServer.configure");
    private static final ITextComponent field_243017_V = new TranslationTextComponent("mco.selectServer.info");
    private static final ITextComponent field_243018_W = new TranslationTextComponent("mco.news");
    private static List<ResourceLocation> field_227918_e_ = ImmutableList.of();
    private static final RealmsDataFetcher field_224017_f = new RealmsDataFetcher();
    private static boolean field_224013_b;
    private static int field_224018_g;
    private static volatile boolean field_224031_t;
    private static volatile boolean field_224032_u;
    private static volatile boolean field_224033_v;
    private static Screen field_224000_H;
    private static boolean field_224001_I;
    private final RateLimiter field_224014_c;
    private boolean field_224015_d;
    private final Screen field_224019_h;
    private volatile ServerList field_224020_i;
    private long field_224021_j = -1L;
    private Button field_224022_k;
    private Button field_224023_l;
    private Button field_224024_m;
    private Button field_224025_n;
    private Button field_224026_o;
    private List<ITextComponent> field_224027_p;
    private List<RealmsServer> field_224028_q = Lists.newArrayList();
    private volatile int field_224029_r;
    private int field_224030_s;
    private boolean field_224034_w;
    private boolean field_224035_x;
    private boolean field_224036_y;
    private volatile boolean field_224037_z;
    private volatile boolean field_223993_A;
    private volatile boolean field_223994_B;
    private volatile boolean field_223995_C;
    private volatile String field_223996_D;
    private int field_223997_E;
    private int field_223998_F;
    private boolean field_223999_G;
    private List<KeyCombo> field_224002_J;
    private int field_224003_K;
    private ReentrantLock field_224004_L = new ReentrantLock();
    private IBidiRenderer field_243019_aI = IBidiRenderer.field_243257_a;
    private ServerState field_237539_ap_;
    private Button field_224006_N;
    private Button field_224007_O;
    private Button field_224008_P;
    private Button field_224009_Q;
    private Button field_224010_R;
    private Button field_224011_S;

    public RealmsMainScreen(Screen screen) {
        this.field_224019_h = screen;
        this.field_224014_c = RateLimiter.create(0.01666666753590107);
    }

    private boolean func_223928_a() {
        if (RealmsMainScreen.func_223968_l() && this.field_224034_w) {
            if (this.field_224037_z && !this.field_223993_A) {
                return false;
            }
            for (RealmsServer realmsServer : this.field_224028_q) {
                if (!realmsServer.field_230588_g_.equals(this.minecraft.getSession().getPlayerID())) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean func_223990_b() {
        if (RealmsMainScreen.func_223968_l() && this.field_224034_w) {
            if (this.field_224035_x) {
                return false;
            }
            return this.field_224037_z && !this.field_223993_A && this.field_224028_q.isEmpty() ? true : this.field_224028_q.isEmpty();
        }
        return true;
    }

    @Override
    public void init() {
        this.field_224002_J = Lists.newArrayList(new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, RealmsMainScreen::lambda$init$0), new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, this::lambda$init$1), new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, this::lambda$init$2));
        if (field_224000_H != null) {
            this.minecraft.displayGuiScreen(field_224000_H);
        } else {
            this.field_224004_L = new ReentrantLock();
            if (field_224033_v && !RealmsMainScreen.func_223968_l()) {
                this.func_223975_u();
            }
            this.func_223895_s();
            this.func_223965_t();
            if (!this.field_224015_d) {
                this.minecraft.setConnectedToRealms(true);
            }
            this.minecraft.keyboardListener.enableRepeatEvents(false);
            if (RealmsMainScreen.func_223968_l()) {
                field_224017_f.func_225087_d();
            }
            this.field_223994_B = false;
            if (RealmsMainScreen.func_223968_l() && this.field_224034_w) {
                this.func_223901_c();
            }
            this.field_224020_i = new ServerList(this);
            if (field_224018_g != -1) {
                this.field_224020_i.setScrollAmount(field_224018_g);
            }
            this.addListener(this.field_224020_i);
            this.setListenerDefault(this.field_224020_i);
            this.field_243019_aI = IBidiRenderer.func_243258_a(this.font, field_243009_N, 100);
        }
    }

    private static boolean func_223968_l() {
        return field_224032_u && field_224031_t;
    }

    public void func_223901_c() {
        this.field_224026_o = this.addButton(new Button(this.width / 2 - 202, this.height - 32, 90, 20, new TranslationTextComponent("mco.selectServer.leave"), this::lambda$func_223901_c$3));
        this.field_224025_n = this.addButton(new Button(this.width / 2 - 190, this.height - 32, 90, 20, new TranslationTextComponent("mco.selectServer.configure"), this::lambda$func_223901_c$4));
        this.field_224022_k = this.addButton(new Button(this.width / 2 - 93, this.height - 32, 90, 20, new TranslationTextComponent("mco.selectServer.play"), this::lambda$func_223901_c$5));
        this.field_224023_l = this.addButton(new Button(this.width / 2 + 4, this.height - 32, 90, 20, DialogTexts.GUI_BACK, this::lambda$func_223901_c$6));
        this.field_224024_m = this.addButton(new Button(this.width / 2 + 100, this.height - 32, 90, 20, new TranslationTextComponent("mco.selectServer.expiredRenew"), this::lambda$func_223901_c$7));
        this.field_224007_O = this.addButton(new PendingInvitesButton(this));
        this.field_224008_P = this.addButton(new NewsButton(this));
        this.field_224006_N = this.addButton(new InfoButton(this));
        this.field_224011_S = this.addButton(new CloseButton(this));
        this.field_224009_Q = this.addButton(new Button(this.width / 2 + 52, this.func_223932_C() + 137 - 20, 98, 20, new TranslationTextComponent("mco.selectServer.trial"), this::lambda$func_223901_c$8));
        this.field_224010_R = this.addButton(new Button(this.width / 2 + 52, this.func_223932_C() + 160 - 20, 98, 20, new TranslationTextComponent("mco.selectServer.buy"), RealmsMainScreen::lambda$func_223901_c$9));
        RealmsServer realmsServer = this.func_223967_a(this.field_224021_j);
        this.func_223915_a(realmsServer);
    }

    private void func_223915_a(@Nullable RealmsServer realmsServer) {
        boolean bl;
        this.field_224022_k.active = this.func_223897_b(realmsServer) && !this.func_223990_b();
        this.field_224024_m.visible = this.func_223920_c(realmsServer);
        this.field_224025_n.visible = this.func_223941_d(realmsServer);
        this.field_224026_o.visible = this.func_223959_e(realmsServer);
        this.field_224009_Q.visible = bl = this.func_223990_b() && this.field_224037_z && !this.field_223993_A;
        this.field_224009_Q.active = bl;
        this.field_224010_R.visible = this.func_223990_b();
        this.field_224011_S.visible = this.func_223990_b() && this.field_224035_x;
        this.field_224024_m.active = !this.func_223990_b();
        this.field_224025_n.active = !this.func_223990_b();
        this.field_224026_o.active = !this.func_223990_b();
        this.field_224008_P.active = true;
        this.field_224007_O.active = true;
        this.field_224023_l.active = true;
        this.field_224006_N.active = !this.func_223990_b();
    }

    private boolean func_223977_m() {
        return (!this.func_223990_b() || this.field_224035_x) && RealmsMainScreen.func_223968_l() && this.field_224034_w;
    }

    private boolean func_223897_b(@Nullable RealmsServer realmsServer) {
        return realmsServer != null && !realmsServer.field_230591_j_ && realmsServer.field_230586_e_ == RealmsServer.Status.OPEN;
    }

    private boolean func_223920_c(@Nullable RealmsServer realmsServer) {
        return realmsServer != null && realmsServer.field_230591_j_ && this.func_223885_h(realmsServer);
    }

    private boolean func_223941_d(@Nullable RealmsServer realmsServer) {
        return realmsServer != null && this.func_223885_h(realmsServer);
    }

    private boolean func_223959_e(@Nullable RealmsServer realmsServer) {
        return realmsServer != null && !this.func_223885_h(realmsServer);
    }

    @Override
    public void tick() {
        super.tick();
        this.field_224036_y = false;
        ++this.field_224030_s;
        --this.field_224003_K;
        if (this.field_224003_K < 0) {
            this.field_224003_K = 0;
        }
        if (RealmsMainScreen.func_223968_l()) {
            Object object;
            field_224017_f.func_225086_b();
            if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.SERVER_LIST)) {
                boolean bl;
                object = field_224017_f.func_225078_e();
                this.field_224020_i.func_231409_q_();
                boolean bl2 = bl = !this.field_224034_w;
                if (bl) {
                    this.field_224034_w = true;
                }
                if (object != null) {
                    boolean bl3 = false;
                    Iterator<Object> iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        RealmsServer realmsServer = (RealmsServer)iterator2.next();
                        if (!this.func_223991_i(realmsServer)) continue;
                        bl3 = true;
                    }
                    this.field_224028_q = object;
                    if (this.func_223928_a()) {
                        this.field_224020_i.func_241825_a_(new TrialServerEntry(this));
                    }
                    for (RealmsServer realmsServer : this.field_224028_q) {
                        this.field_224020_i.addEntry(new ServerEntry(this, realmsServer));
                    }
                    if (!field_224001_I && bl3) {
                        field_224001_I = true;
                        this.func_223944_n();
                    }
                }
                if (bl) {
                    this.func_223901_c();
                }
            }
            if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.PENDING_INVITE)) {
                this.field_224029_r = field_224017_f.func_225081_f();
                if (this.field_224029_r > 0 && this.field_224014_c.tryAcquire(0)) {
                    RealmsNarratorHelper.func_239550_a_(I18n.format("mco.configure.world.invite.narration", this.field_224029_r));
                }
            }
            if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !this.field_223993_A) {
                boolean bl = field_224017_f.func_225071_g();
                if (bl != this.field_224037_z && this.func_223990_b()) {
                    this.field_224037_z = bl;
                    this.field_223994_B = false;
                } else {
                    this.field_224037_z = bl;
                }
            }
            if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.LIVE_STATS)) {
                object = field_224017_f.func_225079_h();
                block2: for (RealmsServerPlayerList realmsServerPlayerList : ((RealmsServerPlayerLists)object).field_230612_a_) {
                    for (RealmsServer realmsServer : this.field_224028_q) {
                        if (realmsServer.field_230582_a_ != realmsServerPlayerList.field_230609_a_) continue;
                        realmsServer.func_230772_a_(realmsServerPlayerList);
                        continue block2;
                    }
                }
            }
            if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.UNREAD_NEWS)) {
                this.field_223995_C = field_224017_f.func_225059_i();
                this.field_223996_D = field_224017_f.func_225063_j();
            }
            field_224017_f.func_225072_c();
            if (this.func_223990_b()) {
                ++this.field_223998_F;
            }
            if (this.field_224006_N != null) {
                this.field_224006_N.visible = this.func_223977_m();
            }
        }
    }

    private void func_223944_n() {
        new Thread(this::lambda$func_223944_n$10).start();
    }

    private List<Long> func_223952_o() {
        ArrayList<Long> arrayList = Lists.newArrayList();
        for (RealmsServer realmsServer : this.field_224028_q) {
            if (!this.func_223991_i(realmsServer)) continue;
            arrayList.add(realmsServer.field_230582_a_);
        }
        return arrayList;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.func_223939_y();
    }

    private void func_223930_q() {
        RealmsServer realmsServer = this.func_223967_a(this.field_224021_j);
        if (realmsServer != null) {
            String string = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + realmsServer.field_230583_b_ + "&profileId=" + this.minecraft.getSession().getPlayerID() + "&ref=" + (realmsServer.field_230592_k_ ? "expiredTrial" : "expiredRealm");
            this.minecraft.keyboardListener.setClipboardString(string);
            Util.getOSType().openURI(string);
        }
    }

    private void func_223895_s() {
        if (!field_224033_v) {
            field_224033_v = true;
            new Thread(this, "MCO Compatability Checker #1"){
                final RealmsMainScreen this$0;
                {
                    this.this$0 = realmsMainScreen;
                    super(string);
                }

                @Override
                public void run() {
                    RealmsClient realmsClient = RealmsClient.func_224911_a();
                    try {
                        RealmsClient.CompatibleVersionResponse compatibleVersionResponse = realmsClient.func_224939_i();
                        if (compatibleVersionResponse == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                            field_224000_H = new RealmsClientOutdatedScreen(this.this$0.field_224019_h, true);
                            RealmsMainScreen.access$000(this.this$0).execute(this::lambda$run$0);
                            return;
                        }
                        if (compatibleVersionResponse == RealmsClient.CompatibleVersionResponse.OTHER) {
                            field_224000_H = new RealmsClientOutdatedScreen(this.this$0.field_224019_h, false);
                            RealmsMainScreen.access$100(this.this$0).execute(this::lambda$run$1);
                            return;
                        }
                        this.this$0.func_223975_u();
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224033_v = false;
                        field_224012_a.error("Couldn't connect to realms", (Throwable)realmsServiceException);
                        if (realmsServiceException.field_224981_a == 401) {
                            field_224000_H = new RealmsGenericErrorScreen(new TranslationTextComponent("mco.error.invalid.session.title"), new TranslationTextComponent("mco.error.invalid.session.message"), this.this$0.field_224019_h);
                            RealmsMainScreen.access$200(this.this$0).execute(this::lambda$run$2);
                        }
                        RealmsMainScreen.access$300(this.this$0).execute(() -> this.lambda$run$3(realmsServiceException));
                    }
                }

                private void lambda$run$3(RealmsServiceException realmsServiceException) {
                    RealmsMainScreen.access$400(this.this$0).displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, this.this$0.field_224019_h));
                }

                private void lambda$run$2() {
                    RealmsMainScreen.access$500(this.this$0).displayGuiScreen(field_224000_H);
                }

                private void lambda$run$1() {
                    RealmsMainScreen.access$600(this.this$0).displayGuiScreen(field_224000_H);
                }

                private void lambda$run$0() {
                    RealmsMainScreen.access$700(this.this$0).displayGuiScreen(field_224000_H);
                }
            }.start();
        }
    }

    private void func_223965_t() {
    }

    private void func_223975_u() {
        new Thread(this, "MCO Compatability Checker #1"){
            final RealmsMainScreen this$0;
            {
                this.this$0 = realmsMainScreen;
                super(string);
            }

            @Override
            public void run() {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                try {
                    Boolean bl = realmsClient.func_224918_g();
                    if (bl.booleanValue()) {
                        field_224012_a.info("Realms is available for this user");
                        field_224031_t = true;
                    } else {
                        field_224012_a.info("Realms is not available for this user");
                        field_224031_t = false;
                        RealmsMainScreen.access$800(this.this$0).execute(this::lambda$run$0);
                    }
                    field_224032_u = true;
                } catch (RealmsServiceException realmsServiceException) {
                    field_224012_a.error("Couldn't connect to realms", (Throwable)realmsServiceException);
                    RealmsMainScreen.access$900(this.this$0).execute(() -> this.lambda$run$1(realmsServiceException));
                }
            }

            private void lambda$run$1(RealmsServiceException realmsServiceException) {
                RealmsMainScreen.access$1000(this.this$0).displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, this.this$0.field_224019_h));
            }

            private void lambda$run$0() {
                RealmsMainScreen.access$1100(this.this$0).displayGuiScreen(new RealmsParentalConsentScreen(this.this$0.field_224019_h));
            }
        }.start();
    }

    private void func_223884_v() {
        if (RealmsClient.field_224944_a != RealmsClient.Environment.STAGE) {
            new Thread(this, "MCO Stage Availability Checker #1"){
                final RealmsMainScreen this$0;
                {
                    this.this$0 = realmsMainScreen;
                    super(string);
                }

                @Override
                public void run() {
                    RealmsClient realmsClient = RealmsClient.func_224911_a();
                    try {
                        Boolean bl = realmsClient.func_224931_h();
                        if (bl.booleanValue()) {
                            RealmsClient.func_224940_b();
                            field_224012_a.info("Switched to stage");
                            field_224017_f.func_225087_d();
                        }
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224012_a.error("Couldn't connect to Realms: " + realmsServiceException);
                    }
                }
            }.start();
        }
    }

    private void func_223962_w() {
        if (RealmsClient.field_224944_a != RealmsClient.Environment.LOCAL) {
            new Thread(this, "MCO Local Availability Checker #1"){
                final RealmsMainScreen this$0;
                {
                    this.this$0 = realmsMainScreen;
                    super(string);
                }

                @Override
                public void run() {
                    RealmsClient realmsClient = RealmsClient.func_224911_a();
                    try {
                        Boolean bl = realmsClient.func_224931_h();
                        if (bl.booleanValue()) {
                            RealmsClient.func_224941_d();
                            field_224012_a.info("Switched to local");
                            field_224017_f.func_225087_d();
                        }
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224012_a.error("Couldn't connect to Realms: " + realmsServiceException);
                    }
                }
            }.start();
        }
    }

    private void func_223973_x() {
        RealmsClient.func_224921_c();
        field_224017_f.func_225087_d();
    }

    private void func_223939_y() {
        field_224017_f.func_225070_k();
    }

    private void func_223966_f(RealmsServer realmsServer) {
        if (this.minecraft.getSession().getPlayerID().equals(realmsServer.field_230588_g_) || field_224013_b) {
            this.func_223949_z();
            this.minecraft.displayGuiScreen(new RealmsConfigureWorldScreen(this, realmsServer.field_230582_a_));
        }
    }

    private void func_223906_g(@Nullable RealmsServer realmsServer) {
        if (realmsServer != null && !this.minecraft.getSession().getPlayerID().equals(realmsServer.field_230588_g_)) {
            this.func_223949_z();
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.leave.question.line1");
            TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.leave.question.line2");
            this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(this::func_237625_d_, RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
        }
    }

    private void func_223949_z() {
        field_224018_g = (int)this.field_224020_i.getScrollAmount();
    }

    @Nullable
    private RealmsServer func_223967_a(long l) {
        for (RealmsServer realmsServer : this.field_224028_q) {
            if (realmsServer.field_230582_a_ != l) continue;
            return realmsServer;
        }
        return null;
    }

    private void func_237625_d_(boolean bl) {
        if (bl) {
            new Thread(this, "Realms-leave-server"){
                final RealmsMainScreen this$0;
                {
                    this.this$0 = realmsMainScreen;
                    super(string);
                }

                @Override
                public void run() {
                    try {
                        RealmsServer realmsServer = this.this$0.func_223967_a(this.this$0.field_224021_j);
                        if (realmsServer != null) {
                            RealmsClient realmsClient = RealmsClient.func_224911_a();
                            realmsClient.func_224912_c(realmsServer.field_230582_a_);
                            RealmsMainScreen.access$1200(this.this$0).execute(() -> this.lambda$run$0(realmsServer));
                        }
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224012_a.error("Couldn't configure world");
                        RealmsMainScreen.access$1300(this.this$0).execute(() -> this.lambda$run$1(realmsServiceException));
                    }
                }

                private void lambda$run$1(RealmsServiceException realmsServiceException) {
                    RealmsMainScreen.access$1400(this.this$0).displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this.this$0));
                }

                private void lambda$run$0(RealmsServer realmsServer) {
                    this.this$0.func_243059_h(realmsServer);
                }
            }.start();
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void func_243059_h(RealmsServer realmsServer) {
        field_224017_f.func_225085_a(realmsServer);
        this.field_224028_q.remove(realmsServer);
        this.field_224020_i.getEventListeners().removeIf(this::lambda$func_243059_h$11);
        this.field_224020_i.setSelected((ListEntry)null);
        this.func_223915_a(null);
        this.field_224021_j = -1L;
        this.field_224022_k.active = false;
    }

    public void func_223978_e() {
        this.field_224021_j = -1L;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.field_224002_J.forEach(KeyCombo::func_224800_a);
            this.func_223955_A();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_223955_A() {
        if (this.func_223990_b() && this.field_224035_x) {
            this.field_224035_x = false;
        } else {
            this.minecraft.displayGuiScreen(this.field_224019_h);
        }
    }

    @Override
    public boolean charTyped(char c, int n) {
        this.field_224002_J.forEach(arg_0 -> RealmsMainScreen.lambda$charTyped$12(c, arg_0));
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_237539_ap_ = ServerState.NONE;
        this.field_224027_p = null;
        this.renderBackground(matrixStack);
        this.field_224020_i.render(matrixStack, n, n2, f);
        this.func_237579_a_(matrixStack, this.width / 2 - 50, 7);
        if (RealmsClient.field_224944_a == RealmsClient.Environment.STAGE) {
            this.func_237613_c_(matrixStack);
        }
        if (RealmsClient.field_224944_a == RealmsClient.Environment.LOCAL) {
            this.func_237604_b_(matrixStack);
        }
        if (this.func_223990_b()) {
            this.func_237605_b_(matrixStack, n, n2);
        } else {
            if (this.field_223994_B) {
                this.func_223915_a(null);
                if (!this.children.contains(this.field_224020_i)) {
                    this.children.add(this.field_224020_i);
                }
                RealmsServer realmsServer = this.func_223967_a(this.field_224021_j);
                this.field_224022_k.active = this.func_223897_b(realmsServer);
            }
            this.field_223994_B = false;
        }
        super.render(matrixStack, n, n2, f);
        if (this.field_224027_p != null) {
            this.func_237583_a_(matrixStack, this.field_224027_p, n, n2);
        }
        if (this.field_224037_z && !this.field_223993_A && this.func_223990_b()) {
            this.minecraft.getTextureManager().bindTexture(field_237536_C_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n3 = 8;
            int n4 = 8;
            int n5 = 0;
            if ((Util.milliTime() / 800L & 1L) == 1L) {
                n5 = 8;
            }
            RealmsMainScreen.blit(matrixStack, this.field_224009_Q.x + this.field_224009_Q.getWidth() - 8 - 4, this.field_224009_Q.y + this.field_224009_Q.getHeightRealms() / 2 - 4, 0.0f, n5, 8, 8, 8, 16);
        }
    }

    private void func_237579_a_(MatrixStack matrixStack, int n, int n2) {
        this.minecraft.getTextureManager().bindTexture(field_237548_v_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.5f, 0.5f, 0.5f);
        RealmsMainScreen.blit(matrixStack, n * 2, n2 * 2 - 5, 0.0f, 0.0f, 200, 50, 200, 50);
        RenderSystem.popMatrix();
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.func_223979_a(d, d2) && this.field_224035_x) {
            this.field_224035_x = false;
            this.field_224036_y = true;
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    private boolean func_223979_a(double d, double d2) {
        int n = this.func_223989_B();
        int n2 = this.func_223932_C();
        return d < (double)(n - 5) || d > (double)(n + 315) || d2 < (double)(n2 - 5) || d2 > (double)(n2 + 171);
    }

    private void func_237605_b_(MatrixStack matrixStack, int n, int n2) {
        int n3 = this.func_223989_B();
        int n4 = this.func_223932_C();
        if (!this.field_223994_B) {
            ServerList serverList;
            this.field_223997_E = 0;
            this.field_223998_F = 0;
            this.field_223999_G = true;
            this.func_223915_a(null);
            if (this.children.contains(this.field_224020_i) && !this.children.remove(serverList = this.field_224020_i)) {
                field_224012_a.error("Unable to remove widget: " + serverList);
            }
            RealmsNarratorHelper.func_239550_a_(field_243009_N.getString());
        }
        if (this.field_224034_w) {
            this.field_223994_B = true;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.7f);
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(field_237534_A_);
        boolean bl = false;
        int n5 = 32;
        RealmsMainScreen.blit(matrixStack, 0, 32, 0.0f, 0.0f, this.width, this.height - 40 - 32, 310, 166);
        RenderSystem.disableBlend();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(field_237552_z_);
        RealmsMainScreen.blit(matrixStack, n3, n4, 0.0f, 0.0f, 310, 166, 310, 166);
        if (!field_227918_e_.isEmpty()) {
            this.minecraft.getTextureManager().bindTexture(field_227918_e_.get(this.field_223997_E));
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsMainScreen.blit(matrixStack, n3 + 7, n4 + 7, 0.0f, 0.0f, 195, 152, 195, 152);
            if (this.field_223998_F % 95 < 5) {
                if (!this.field_223999_G) {
                    this.field_223997_E = (this.field_223997_E + 1) % field_227918_e_.size();
                    this.field_223999_G = true;
                }
            } else {
                this.field_223999_G = false;
            }
        }
        this.field_243019_aI.func_241866_c(matrixStack, this.width / 2 + 52, n4 + 7, 10, 0x4C4C4C);
    }

    private int func_223989_B() {
        return (this.width - 310) / 2;
    }

    private int func_223932_C() {
        return this.height / 2 - 80;
    }

    private void func_237581_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        int n5;
        int n6;
        boolean bl3;
        boolean bl4;
        int n7 = this.field_224029_r;
        boolean bl5 = this.func_223931_b(n, n2);
        boolean bl6 = bl4 = bl2 && bl;
        if (bl4) {
            float f = 0.25f + (1.0f + MathHelper.sin((float)this.field_224030_s * 0.5f)) * 0.25f;
            int n8 = 0xFF000000 | (int)(f * 64.0f) << 16 | (int)(f * 64.0f) << 8 | (int)(f * 64.0f) << 0;
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 + 18, n4 + 18, n8, n8);
            n8 = 0xFF000000 | (int)(f * 255.0f) << 16 | (int)(f * 255.0f) << 8 | (int)(f * 255.0f) << 0;
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 + 18, n4 - 1, n8, n8);
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 - 1, n4 + 18, n8, n8);
            this.fillGradient(matrixStack, n3 + 17, n4 - 2, n3 + 18, n4 + 18, n8, n8);
            this.fillGradient(matrixStack, n3 - 2, n4 + 17, n3 + 18, n4 + 18, n8, n8);
        }
        this.minecraft.getTextureManager().bindTexture(field_237546_t_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        boolean bl7 = bl2 && bl;
        float f = bl7 ? 16.0f : 0.0f;
        RealmsMainScreen.blit(matrixStack, n3, n4 - 6, f, 0.0f, 15, 25, 31, 25);
        boolean bl8 = bl3 = bl2 && n7 != 0;
        if (bl3) {
            n6 = (Math.min(n7, 6) - 1) * 8;
            n5 = (int)(Math.max(0.0f, Math.max(MathHelper.sin((float)(10 + this.field_224030_s) * 0.57f), MathHelper.cos((float)this.field_224030_s * 0.35f))) * -6.0f);
            this.minecraft.getTextureManager().bindTexture(field_237545_s_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f2 = bl5 ? 8.0f : 0.0f;
            RealmsMainScreen.blit(matrixStack, n3 + 4, n4 + 4 + n5, n6, f2, 8, 8, 48, 16);
        }
        n6 = n + 12;
        int n9 = n5 = bl2 && bl5 ? 1 : 0;
        if (n5 != 0) {
            ITextComponent iTextComponent = n7 == 0 ? field_243000_E : field_243001_F;
            int n10 = this.font.getStringPropertyWidth(iTextComponent);
            this.fillGradient(matrixStack, n6 - 3, n2 - 3, n6 + n10 + 3, n2 + 8 + 3, -1073741824, -1073741824);
            this.font.func_243246_a(matrixStack, iTextComponent, n6, n2, -1);
        }
    }

    private boolean func_223931_b(double d, double d2) {
        int n = this.width / 2 + 50;
        int n2 = this.width / 2 + 66;
        int n3 = 11;
        int n4 = 23;
        if (this.field_224029_r != 0) {
            n -= 3;
            n2 += 3;
            n3 -= 5;
            n4 += 5;
        }
        return (double)n <= d && d <= (double)n2 && (double)n3 <= d2 && d2 <= (double)n4;
    }

    public void func_223911_a(RealmsServer realmsServer, Screen screen) {
        if (realmsServer != null) {
            try {
                if (!this.field_224004_L.tryLock(1L, TimeUnit.SECONDS)) {
                    return;
                }
                if (this.field_224004_L.getHoldCount() > 1) {
                    return;
                }
            } catch (InterruptedException interruptedException) {
                return;
            }
            this.field_224015_d = true;
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(screen, new ConnectingToRealmsAction(this, screen, realmsServer, this.field_224004_L)));
        }
    }

    private boolean func_223885_h(RealmsServer realmsServer) {
        return realmsServer.field_230588_g_ != null && realmsServer.field_230588_g_.equals(this.minecraft.getSession().getPlayerID());
    }

    private boolean func_223991_i(RealmsServer realmsServer) {
        return this.func_223885_h(realmsServer) && !realmsServer.field_230591_j_;
    }

    private void func_237614_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237542_p_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsMainScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            this.func_237603_a_(field_243010_O);
        }
    }

    private void func_237606_b_(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        this.minecraft.getTextureManager().bindTexture(field_237543_q_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.field_224030_s % 20 < 10) {
            RealmsMainScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 20, 28);
        } else {
            RealmsMainScreen.blit(matrixStack, n, n2, 10.0f, 0.0f, 10, 28, 20, 28);
        }
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            if (n5 <= 0) {
                this.func_237603_a_(field_243011_P);
            } else if (n5 == 1) {
                this.func_237603_a_(field_243012_Q);
            } else {
                this.func_237603_a_(new TranslationTextComponent("mco.selectServer.expires.days", n5));
            }
        }
    }

    private void func_237620_d_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237540_b_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsMainScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            this.func_237603_a_(field_243013_R);
        }
    }

    private void func_237626_e_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237541_c_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsMainScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            this.func_237603_a_(field_243014_S);
        }
    }

    private void func_237630_f_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = false;
        if (n3 >= n && n3 <= n + 28 && n4 >= n2 && n4 <= n2 + 28 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            bl = true;
        }
        this.minecraft.getTextureManager().bindTexture(field_237544_r_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 28.0f : 0.0f;
        RealmsMainScreen.blit(matrixStack, n, n2, f, 0.0f, 28, 28, 56, 28);
        if (bl) {
            this.func_237603_a_(field_243015_T);
            this.field_237539_ap_ = ServerState.LEAVE;
        }
    }

    private void func_237633_g_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = false;
        if (n3 >= n && n3 <= n + 28 && n4 >= n2 && n4 <= n2 + 28 && n4 < this.height - 40 && n4 > 32 && !this.func_223990_b()) {
            bl = true;
        }
        this.minecraft.getTextureManager().bindTexture(field_237549_w_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 28.0f : 0.0f;
        RealmsMainScreen.blit(matrixStack, n, n2, f, 0.0f, 28, 28, 56, 28);
        if (bl) {
            this.func_237603_a_(field_243016_U);
            this.field_237539_ap_ = ServerState.CONFIGURE;
        }
    }

    protected void func_237583_a_(MatrixStack matrixStack, List<ITextComponent> list, int n, int n2) {
        if (!list.isEmpty()) {
            int n3 = 0;
            int n4 = 0;
            for (ITextComponent iTextComponent : list) {
                int n5 = this.font.getStringPropertyWidth(iTextComponent);
                if (n5 <= n4) continue;
                n4 = n5;
            }
            int n6 = n - n4 - 5;
            int n7 = n2;
            if (n6 < 0) {
                n6 = n + 12;
            }
            for (ITextComponent iTextComponent : list) {
                int n8 = n7 - (n3 == 0 ? 3 : 0) + n3;
                this.fillGradient(matrixStack, n6 - 3, n8, n6 + n4 + 3, n7 + 8 + 3 + n3, -1073741824, -1073741824);
                this.font.func_243246_a(matrixStack, iTextComponent, n6, n7 + n3, 0xFFFFFF);
                n3 += 10;
            }
        }
    }

    private void func_237580_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl) {
        boolean bl2 = false;
        if (n >= n3 && n <= n3 + 20 && n2 >= n4 && n2 <= n4 + 20) {
            bl2 = true;
        }
        this.minecraft.getTextureManager().bindTexture(field_237550_x_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 20.0f : 0.0f;
        RealmsMainScreen.blit(matrixStack, n3, n4, f, 0.0f, 20, 20, 40, 20);
        if (bl2) {
            this.func_237603_a_(field_243017_V);
        }
    }

    private void func_237582_a_(MatrixStack matrixStack, int n, int n2, boolean bl, int n3, int n4, boolean bl2, boolean bl3) {
        boolean bl4 = false;
        if (n >= n3 && n <= n3 + 20 && n2 >= n4 && n2 <= n4 + 20) {
            bl4 = true;
        }
        this.minecraft.getTextureManager().bindTexture(field_237551_y_);
        if (bl3) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.color4f(0.5f, 0.5f, 0.5f, 1.0f);
        }
        boolean bl5 = bl3 && bl2;
        float f = bl5 ? 20.0f : 0.0f;
        RealmsMainScreen.blit(matrixStack, n3, n4, f, 0.0f, 20, 20, 40, 20);
        if (bl4 && bl3) {
            this.func_237603_a_(field_243018_W);
        }
        if (bl && bl3) {
            int n5 = bl4 ? 0 : (int)(Math.max(0.0f, Math.max(MathHelper.sin((float)(10 + this.field_224030_s) * 0.57f), MathHelper.cos((float)this.field_224030_s * 0.35f))) * -6.0f);
            this.minecraft.getTextureManager().bindTexture(field_237545_s_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsMainScreen.blit(matrixStack, n3 + 10, n4 + 2 + n5, 40.0f, 0.0f, 8, 8, 48, 16);
        }
    }

    private void func_237604_b_(MatrixStack matrixStack) {
        String string = "LOCAL!";
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.width / 2 - 25, 20.0f, 0.0f);
        RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.scalef(1.5f, 1.5f, 1.5f);
        this.font.drawString(matrixStack, "LOCAL!", 0.0f, 0.0f, 0x7FFF7F);
        RenderSystem.popMatrix();
    }

    private void func_237613_c_(MatrixStack matrixStack) {
        String string = "STAGE!";
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.width / 2 - 25, 20.0f, 0.0f);
        RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.scalef(1.5f, 1.5f, 1.5f);
        this.font.drawString(matrixStack, "STAGE!", 0.0f, 0.0f, -256);
        RenderSystem.popMatrix();
    }

    public RealmsMainScreen func_223942_f() {
        RealmsMainScreen realmsMainScreen = new RealmsMainScreen(this.field_224019_h);
        realmsMainScreen.init(this.minecraft, this.width, this.height);
        return realmsMainScreen;
    }

    public static void func_227932_a_(IResourceManager iResourceManager) {
        Collection<ResourceLocation> collection = iResourceManager.getAllResourceLocations("textures/gui/images", RealmsMainScreen::lambda$func_227932_a_$13);
        field_227918_e_ = collection.stream().filter(RealmsMainScreen::lambda$func_227932_a_$14).collect(ImmutableList.toImmutableList());
    }

    private void func_237603_a_(ITextComponent ... iTextComponentArray) {
        this.field_224027_p = Arrays.asList(iTextComponentArray);
    }

    private void func_237598_a_(Button button) {
        this.minecraft.displayGuiScreen(new RealmsPendingInvitesScreen(this.field_224019_h));
    }

    private static boolean lambda$func_227932_a_$14(ResourceLocation resourceLocation) {
        return resourceLocation.getNamespace().equals("realms");
    }

    private static boolean lambda$func_227932_a_$13(String string) {
        return string.endsWith(".png");
    }

    private static void lambda$charTyped$12(char c, KeyCombo keyCombo) {
        keyCombo.func_224799_a(c);
    }

    private boolean lambda$func_243059_h$11(ListEntry listEntry) {
        return listEntry instanceof ServerEntry && ((ServerEntry)listEntry).field_223734_a.field_230582_a_ == this.field_224021_j;
    }

    private void lambda$func_223944_n$10() {
        List<RegionPingResult> list = Ping.func_224864_a();
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        PingResult pingResult = new PingResult();
        pingResult.field_230571_a_ = list;
        pingResult.field_230572_b_ = this.func_223952_o();
        try {
            realmsClient.func_224903_a(pingResult);
        } catch (Throwable throwable) {
            field_224012_a.warn("Could not send ping result to Realms: ", throwable);
        }
    }

    private static void lambda$func_223901_c$9(Button button) {
        Util.getOSType().openURI("https://aka.ms/BuyJavaRealms");
    }

    private void lambda$func_223901_c$8(Button button) {
        if (this.field_224037_z && !this.field_223993_A) {
            Util.getOSType().openURI("https://aka.ms/startjavarealmstrial");
            this.minecraft.displayGuiScreen(this.field_224019_h);
        }
    }

    private void lambda$func_223901_c$7(Button button) {
        this.func_223930_q();
    }

    private void lambda$func_223901_c$6(Button button) {
        if (!this.field_224036_y) {
            this.minecraft.displayGuiScreen(this.field_224019_h);
        }
    }

    private void lambda$func_223901_c$5(Button button) {
        RealmsServer realmsServer = this.func_223967_a(this.field_224021_j);
        if (realmsServer != null) {
            this.func_223911_a(realmsServer, this);
        }
    }

    private void lambda$func_223901_c$4(Button button) {
        this.func_223966_f(this.func_223967_a(this.field_224021_j));
    }

    private void lambda$func_223901_c$3(Button button) {
        this.func_223906_g(this.func_223967_a(this.field_224021_j));
    }

    private void lambda$init$2() {
        if (RealmsClient.field_224944_a == RealmsClient.Environment.LOCAL) {
            this.func_223973_x();
        } else {
            this.func_223962_w();
        }
    }

    private void lambda$init$1() {
        if (RealmsClient.field_224944_a == RealmsClient.Environment.STAGE) {
            this.func_223973_x();
        } else {
            this.func_223884_v();
        }
    }

    private static void lambda$init$0() {
        field_224013_b = !field_224013_b;
    }

    static Minecraft access$000(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$100(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$200(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$300(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$400(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$500(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$600(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$700(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$800(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$900(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1000(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1100(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1200(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1300(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1400(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1500(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1600(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static Minecraft access$1700(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static FontRenderer access$1800(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$1900(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2000(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2100(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static Minecraft access$2200(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.minecraft;
    }

    static FontRenderer access$2300(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2400(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2500(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2600(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2700(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2800(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$2900(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$3000(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$3100(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$3200(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static FontRenderer access$3300(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.font;
    }

    static {
        field_224018_g = -1;
    }

    class ServerList
    extends RealmsObjectSelectionList<ListEntry> {
        private boolean field_241824_o_;
        final RealmsMainScreen this$0;

        public ServerList(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen.width, realmsMainScreen.height, 32, realmsMainScreen.height - 40, 36);
        }

        @Override
        public void func_231409_q_() {
            super.func_231409_q_();
            this.field_241824_o_ = false;
        }

        public int func_241825_a_(ListEntry listEntry) {
            this.field_241824_o_ = true;
            return this.addEntry(listEntry);
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public boolean keyPressed(int n, int n2, int n3) {
            if (n != 257 && n != 32 && n != 335) {
                return super.keyPressed(n, n2, n3);
            }
            ExtendedList.AbstractListEntry abstractListEntry = (ExtendedList.AbstractListEntry)this.getSelected();
            return abstractListEntry == null ? super.keyPressed(n, n2, n3) : abstractListEntry.mouseClicked(0.0, 0.0, 1);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (n == 0 && d < (double)this.getScrollbarPosition() && d2 >= (double)this.y0 && d2 <= (double)this.y1) {
                int n2 = this.this$0.field_224020_i.getRowLeft();
                int n3 = this.getScrollbarPosition();
                int n4 = (int)Math.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
                int n5 = n4 / this.itemHeight;
                if (d >= (double)n2 && d <= (double)n3 && n5 >= 0 && n4 >= 0 && n5 < this.getItemCount()) {
                    this.func_231401_a_(n4, n5, d, d2, this.width);
                    this.this$0.field_224003_K += 7;
                    this.func_231400_a_(n5);
                }
                return false;
            }
            return super.mouseClicked(d, d2, n);
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                RealmsServer realmsServer;
                if (this.field_241824_o_) {
                    if (n == 0) {
                        realmsServer = null;
                    } else {
                        if (n - 1 >= this.this$0.field_224028_q.size()) {
                            this.this$0.field_224021_j = -1L;
                            return;
                        }
                        realmsServer = this.this$0.field_224028_q.get(n - 1);
                    }
                } else {
                    if (n >= this.this$0.field_224028_q.size()) {
                        this.this$0.field_224021_j = -1L;
                        return;
                    }
                    realmsServer = this.this$0.field_224028_q.get(n);
                }
                this.this$0.func_223915_a(realmsServer);
                if (realmsServer == null) {
                    this.this$0.field_224021_j = -1L;
                } else if (realmsServer.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                    this.this$0.field_224021_j = -1L;
                } else {
                    this.this$0.field_224021_j = realmsServer.field_230582_a_;
                    if (this.this$0.field_224003_K >= 10 && this.this$0.field_224022_k.active) {
                        this.this$0.func_223911_a(this.this$0.func_223967_a(this.this$0.field_224021_j), this.this$0);
                    }
                }
            }
        }

        @Override
        public void setSelected(@Nullable ListEntry listEntry) {
            super.setSelected(listEntry);
            int n = this.getEventListeners().indexOf(listEntry);
            if (this.field_241824_o_ && n == 0) {
                RealmsNarratorHelper.func_239551_a_(I18n.format("mco.trial.message.line1", new Object[0]), I18n.format("mco.trial.message.line2", new Object[0]));
            } else if (!this.field_241824_o_ || n > 0) {
                RealmsServer realmsServer = this.this$0.field_224028_q.get(n - (this.field_241824_o_ ? 1 : 0));
                this.this$0.field_224021_j = realmsServer.field_230582_a_;
                this.this$0.func_223915_a(realmsServer);
                if (realmsServer.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                    RealmsNarratorHelper.func_239550_a_(I18n.format("mco.selectServer.uninitialized", new Object[0]) + I18n.format("mco.gui.button", new Object[0]));
                } else {
                    RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", realmsServer.field_230584_c_));
                }
            }
        }

        @Override
        public void func_231401_a_(int n, int n2, double d, double d2, int n3) {
            RealmsServer realmsServer;
            if (this.field_241824_o_) {
                if (n2 == 0) {
                    this.this$0.field_224035_x = true;
                    return;
                }
                --n2;
            }
            if (n2 < this.this$0.field_224028_q.size() && (realmsServer = this.this$0.field_224028_q.get(n2)) != null) {
                if (realmsServer.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                    this.this$0.field_224021_j = -1L;
                    Minecraft.getInstance().displayGuiScreen(new RealmsCreateRealmScreen(realmsServer, this.this$0));
                } else {
                    this.this$0.field_224021_j = realmsServer.field_230582_a_;
                }
                if (this.this$0.field_237539_ap_ == ServerState.CONFIGURE) {
                    this.this$0.field_224021_j = realmsServer.field_230582_a_;
                    this.this$0.func_223966_f(realmsServer);
                } else if (this.this$0.field_237539_ap_ == ServerState.LEAVE) {
                    this.this$0.field_224021_j = realmsServer.field_230582_a_;
                    this.this$0.func_223906_g(realmsServer);
                } else if (this.this$0.field_237539_ap_ == ServerState.EXPIRED) {
                    this.this$0.func_223930_q();
                }
            }
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }

        @Override
        public int getRowWidth() {
            return 1;
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((ListEntry)abstractListEntry);
        }
    }

    class PendingInvitesButton
    extends Button
    implements IScreen {
        final RealmsMainScreen this$0;

        public PendingInvitesButton(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen.width / 2 + 47, 6, 22, 22, StringTextComponent.EMPTY, null);
        }

        @Override
        public void onPress() {
            this.this$0.func_237598_a_(this);
        }

        @Override
        public void tick() {
            this.setMessage(new TranslationTextComponent(this.this$0.field_224029_r == 0 ? "mco.invites.nopending" : "mco.invites.pending"));
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            this.this$0.func_237581_a_(matrixStack, n, n2, this.x, this.y, this.isHovered(), this.active);
        }
    }

    class NewsButton
    extends Button {
        final RealmsMainScreen this$0;

        public NewsButton(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen.width - 62, 6, 20, 20, StringTextComponent.EMPTY, null);
            this.setMessage(new TranslationTextComponent("mco.news"));
        }

        @Override
        public void onPress() {
            if (this.this$0.field_223996_D != null) {
                Util.getOSType().openURI(this.this$0.field_223996_D);
                if (this.this$0.field_223995_C) {
                    RealmsPersistence.RealmsPersistenceData realmsPersistenceData = RealmsPersistence.func_225188_a();
                    realmsPersistenceData.field_225186_b = false;
                    this.this$0.field_223995_C = false;
                    RealmsPersistence.func_225187_a(realmsPersistenceData);
                }
            }
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            this.this$0.func_237582_a_(matrixStack, n, n2, this.this$0.field_223995_C, this.x, this.y, this.isHovered(), this.active);
        }
    }

    class InfoButton
    extends Button {
        final RealmsMainScreen this$0;

        public InfoButton(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen.width - 37, 6, 20, 20, new TranslationTextComponent("mco.selectServer.info"), null);
        }

        @Override
        public void onPress() {
            this.this$0.field_224035_x = !this.this$0.field_224035_x;
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            this.this$0.func_237580_a_(matrixStack, n, n2, this.x, this.y, this.isHovered());
        }
    }

    class CloseButton
    extends Button {
        final RealmsMainScreen this$0;

        public CloseButton(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen.func_223989_B() + 4, realmsMainScreen.func_223932_C() + 4, 12, 12, new TranslationTextComponent("mco.selectServer.close"), null);
        }

        @Override
        public void onPress() {
            this.this$0.func_223955_A();
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            RealmsMainScreen.access$1500(this.this$0).getTextureManager().bindTexture(field_237535_B_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f2 = this.isHovered() ? 12.0f : 0.0f;
            CloseButton.blit(matrixStack, this.x, this.y, 0.0f, f2, 12, 12, 12, 24);
            if (this.isMouseOver(n, n2)) {
                this.this$0.func_237603_a_(this.getMessage());
            }
        }
    }

    class TrialServerEntry
    extends ListEntry {
        final RealmsMainScreen this$0;

        private TrialServerEntry(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237681_a_(matrixStack, n, n3, n2, n6, n7);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            this.this$0.field_224035_x = true;
            return false;
        }

        private void func_237681_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
            int n6 = n3 + 8;
            int n7 = 0;
            boolean bl = false;
            if (n2 <= n4 && n4 <= (int)this.this$0.field_224020_i.getScrollAmount() && n3 <= n5 && n5 <= n3 + 32) {
                bl = true;
            }
            int n8 = 0x7FFF7F;
            if (bl && !this.this$0.func_223990_b()) {
                n8 = 6077788;
            }
            for (ITextComponent iTextComponent : field_243002_G) {
                AbstractGui.drawCenteredString(matrixStack, RealmsMainScreen.access$3300(this.this$0), iTextComponent, this.this$0.width / 2, n6 + n7, n8);
                n7 += 10;
            }
        }
    }

    abstract class ListEntry
    extends ExtendedList.AbstractListEntry<ListEntry> {
        final RealmsMainScreen this$0;

        private ListEntry(RealmsMainScreen realmsMainScreen) {
            this.this$0 = realmsMainScreen;
        }
    }

    class ServerEntry
    extends ListEntry {
        private final RealmsServer field_223734_a;
        final RealmsMainScreen this$0;

        public ServerEntry(RealmsMainScreen realmsMainScreen, RealmsServer realmsServer) {
            this.this$0 = realmsMainScreen;
            super(realmsMainScreen);
            this.field_223734_a = realmsServer;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237678_a_(this.field_223734_a, matrixStack, n3, n2, n6, n7);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (this.field_223734_a.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                this.this$0.field_224021_j = -1L;
                RealmsMainScreen.access$1600(this.this$0).displayGuiScreen(new RealmsCreateRealmScreen(this.field_223734_a, this.this$0));
            } else {
                this.this$0.field_224021_j = this.field_223734_a.field_230582_a_;
            }
            return false;
        }

        private void func_237678_a_(RealmsServer realmsServer, MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            this.func_237679_b_(realmsServer, matrixStack, n + 36, n2, n3, n4);
        }

        private void func_237679_b_(RealmsServer realmsServer, MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            if (realmsServer.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                RealmsMainScreen.access$1700(this.this$0).getTextureManager().bindTexture(field_237547_u_);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.enableAlphaTest();
                AbstractGui.blit(matrixStack, n + 10, n2 + 6, 0.0f, 0.0f, 40, 20, 40, 20);
                float f = 0.5f + (1.0f + MathHelper.sin((float)this.this$0.field_224030_s * 0.25f)) * 0.25f;
                int n5 = 0xFF000000 | (int)(127.0f * f) << 16 | (int)(255.0f * f) << 8 | (int)(127.0f * f);
                AbstractGui.drawCenteredString(matrixStack, RealmsMainScreen.access$1800(this.this$0), field_243003_H, n + 10 + 40 + 75, n2 + 12, n5);
            } else {
                Object object;
                int n6 = 225;
                int n7 = 2;
                if (realmsServer.field_230591_j_) {
                    this.this$0.func_237614_c_(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
                } else if (realmsServer.field_230586_e_ == RealmsServer.Status.CLOSED) {
                    this.this$0.func_237626_e_(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
                } else if (this.this$0.func_223885_h(realmsServer) && realmsServer.field_230593_l_ < 7) {
                    this.this$0.func_237606_b_(matrixStack, n + 225 - 14, n2 + 2, n3, n4, realmsServer.field_230593_l_);
                } else if (realmsServer.field_230586_e_ == RealmsServer.Status.OPEN) {
                    this.this$0.func_237620_d_(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
                }
                if (!this.this$0.func_223885_h(realmsServer) && !field_224013_b) {
                    this.this$0.func_237630_f_(matrixStack, n + 225, n2 + 2, n3, n4);
                } else {
                    this.this$0.func_237633_g_(matrixStack, n + 225, n2 + 2, n3, n4);
                }
                if (!"0".equals(realmsServer.field_230599_r_.field_230607_a_)) {
                    object = TextFormatting.GRAY + realmsServer.field_230599_r_.field_230607_a_;
                    RealmsMainScreen.access$2000(this.this$0).drawString(matrixStack, (String)object, n + 207 - RealmsMainScreen.access$1900(this.this$0).getStringWidth((String)object), n2 + 3, 0x808080);
                    if (n3 >= n + 207 - RealmsMainScreen.access$2100(this.this$0).getStringWidth((String)object) && n3 <= n + 207 && n4 >= n2 + 1 && n4 <= n2 + 10 && n4 < this.this$0.height - 40 && n4 > 32 && !this.this$0.func_223990_b()) {
                        this.this$0.func_237603_a_(new StringTextComponent(realmsServer.field_230599_r_.field_230608_b_));
                    }
                }
                if (this.this$0.func_223885_h(realmsServer) && realmsServer.field_230591_j_) {
                    ITextComponent iTextComponent;
                    RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderSystem.enableBlend();
                    RealmsMainScreen.access$2200(this.this$0).getTextureManager().bindTexture(field_237537_D_);
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    if (realmsServer.field_230592_k_) {
                        object = field_243006_K;
                        iTextComponent = field_243007_L;
                    } else {
                        object = field_243004_I;
                        iTextComponent = field_243005_J;
                    }
                    int n8 = RealmsMainScreen.access$2300(this.this$0).getStringPropertyWidth(iTextComponent) + 17;
                    int n9 = 16;
                    int n10 = n + RealmsMainScreen.access$2400(this.this$0).getStringPropertyWidth((ITextProperties)object) + 8;
                    int n11 = n2 + 13;
                    boolean bl = false;
                    if (n3 >= n10 && n3 < n10 + n8 && n4 > n11 && n4 <= n11 + 16 & n4 < this.this$0.height - 40 && n4 > 32 && !this.this$0.func_223990_b()) {
                        bl = true;
                        this.this$0.field_237539_ap_ = ServerState.EXPIRED;
                    }
                    int n12 = bl ? 2 : 1;
                    AbstractGui.blit(matrixStack, n10, n11, 0.0f, 46 + n12 * 20, n8 / 2, 8, 256, 256);
                    AbstractGui.blit(matrixStack, n10 + n8 / 2, n11, 200 - n8 / 2, 46 + n12 * 20, n8 / 2, 8, 256, 256);
                    AbstractGui.blit(matrixStack, n10, n11 + 8, 0.0f, 46 + n12 * 20 + 12, n8 / 2, 8, 256, 256);
                    AbstractGui.blit(matrixStack, n10 + n8 / 2, n11 + 8, 200 - n8 / 2, 46 + n12 * 20 + 12, n8 / 2, 8, 256, 256);
                    RenderSystem.disableBlend();
                    int n13 = n2 + 11 + 5;
                    int n14 = bl ? 0xFFFFA0 : 0xFFFFFF;
                    RealmsMainScreen.access$2500(this.this$0).func_243248_b(matrixStack, (ITextComponent)object, n + 2, n13 + 1, 15553363);
                    AbstractGui.drawCenteredString(matrixStack, RealmsMainScreen.access$2600(this.this$0), iTextComponent, n10 + n8 / 2, n13 + 1, n14);
                } else {
                    if (realmsServer.field_230594_m_ == RealmsServer.ServerType.MINIGAME) {
                        int n15 = 0xCCAC5C;
                        int n16 = RealmsMainScreen.access$2700(this.this$0).getStringPropertyWidth(field_243008_M);
                        RealmsMainScreen.access$2800(this.this$0).func_243248_b(matrixStack, field_243008_M, n + 2, n2 + 12, 0xCCAC5C);
                        RealmsMainScreen.access$2900(this.this$0).drawString(matrixStack, realmsServer.func_230778_c_(), n + 2 + n16, n2 + 12, 0x6C6C6C);
                    } else {
                        RealmsMainScreen.access$3000(this.this$0).drawString(matrixStack, realmsServer.func_230768_a_(), n + 2, n2 + 12, 0x6C6C6C);
                    }
                    if (!this.this$0.func_223885_h(realmsServer)) {
                        RealmsMainScreen.access$3100(this.this$0).drawString(matrixStack, realmsServer.field_230587_f_, n + 2, n2 + 12 + 11, 0x4C4C4C);
                    }
                }
                RealmsMainScreen.access$3200(this.this$0).drawString(matrixStack, realmsServer.func_230775_b_(), n + 2, n2 + 1, 0xFFFFFF);
                RealmsTextureManager.func_225205_a(realmsServer.field_230588_g_, () -> ServerEntry.lambda$func_237679_b_$0(matrixStack, n, n2));
            }
        }

        private static void lambda$func_237679_b_$0(MatrixStack matrixStack, int n, int n2) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, n - 36, n2, 32, 32, 8.0f, 8.0f, 8, 8, 64, 64);
            AbstractGui.blit(matrixStack, n - 36, n2, 32, 32, 40.0f, 8.0f, 8, 8, 64, 64);
        }
    }

    static enum ServerState {
        NONE,
        EXPIRED,
        LEAVE,
        CONFIGURE;

    }
}

