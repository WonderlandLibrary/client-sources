/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.mojang.realmsclient.client.RealmsClientConfig;
import com.mojang.realmsclient.client.RealmsError;
import com.mojang.realmsclient.client.Request;
import com.mojang.realmsclient.dto.BackupList;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.PendingInvitesList;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsDescriptionDto;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.dto.RealmsServerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.RealmsWorldResetDto;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsHttpException;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.realms.PersistenceSerializer;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsClient {
    public static Environment field_224944_a = Environment.PRODUCTION;
    private static boolean field_224945_b;
    private static final Logger field_224946_c;
    private final String field_224947_d;
    private final String field_224948_e;
    private final Minecraft field_244732_f;
    private static final PersistenceSerializer field_237691_f_;

    public static RealmsClient func_224911_a() {
        Minecraft minecraft = Minecraft.getInstance();
        String string = minecraft.getSession().getUsername();
        String string2 = minecraft.getSession().getSessionID();
        if (!field_224945_b) {
            field_224945_b = true;
            String string3 = System.getenv("realms.environment");
            if (string3 == null) {
                string3 = System.getProperty("realms.environment");
            }
            if (string3 != null) {
                if ("LOCAL".equals(string3)) {
                    RealmsClient.func_224941_d();
                } else if ("STAGE".equals(string3)) {
                    RealmsClient.func_224940_b();
                }
            }
        }
        return new RealmsClient(string2, string, minecraft);
    }

    public static void func_224940_b() {
        field_224944_a = Environment.STAGE;
    }

    public static void func_224921_c() {
        field_224944_a = Environment.PRODUCTION;
    }

    public static void func_224941_d() {
        field_224944_a = Environment.LOCAL;
    }

    public RealmsClient(String string, String string2, Minecraft minecraft) {
        this.field_224947_d = string;
        this.field_224948_e = string2;
        this.field_244732_f = minecraft;
        RealmsClientConfig.func_224896_a(minecraft.getProxy());
    }

    public RealmsServerList func_224902_e() throws RealmsServiceException {
        String string = this.func_224926_c("worlds");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return RealmsServerList.func_230783_a_(string2);
    }

    public RealmsServer func_224935_a(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$ID".replace("$ID", String.valueOf(l)));
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return RealmsServer.func_230779_c_(string2);
    }

    public RealmsServerPlayerLists func_224915_f() throws RealmsServiceException {
        String string = this.func_224926_c("activities/liveplayerlist");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return RealmsServerPlayerLists.func_230786_a_(string2);
    }

    public RealmsServerAddress func_224904_b(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + l));
        String string2 = this.func_224938_a(Request.func_224960_a(string, 5000, 30000));
        return RealmsServerAddress.func_230782_a_(string2);
    }

    public void func_224900_a(long l, String string, String string2) throws RealmsServiceException {
        RealmsDescriptionDto realmsDescriptionDto = new RealmsDescriptionDto(string, string2);
        String string3 = this.func_224926_c("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(l)));
        String string4 = field_237691_f_.func_237694_a_(realmsDescriptionDto);
        this.func_224938_a(Request.func_224959_a(string3, string4, 5000, 10000));
    }

    public Boolean func_224918_g() throws RealmsServiceException {
        String string = this.func_224926_c("mco/available");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return Boolean.valueOf(string2);
    }

    public Boolean func_224931_h() throws RealmsServiceException {
        String string = this.func_224926_c("mco/stageAvailable");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return Boolean.valueOf(string2);
    }

    public CompatibleVersionResponse func_224939_i() throws RealmsServiceException {
        String string = this.func_224926_c("mco/client/compatible");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        try {
            return CompatibleVersionResponse.valueOf(string2);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new RealmsServiceException(500, "Could not check compatible version, got response: " + string2, -1, "");
        }
    }

    public void func_224908_a(long l, String string) throws RealmsServiceException {
        String string2 = this.func_224926_c("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(l)).replace("$UUID", string));
        this.func_224938_a(Request.func_224952_b(string2));
    }

    public void func_224912_c(long l) throws RealmsServiceException {
        String string = this.func_224926_c("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(l)));
        this.func_224938_a(Request.func_224952_b(string));
    }

    public RealmsServer func_224910_b(long l, String string) throws RealmsServiceException {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.func_230758_a_(string);
        String string2 = this.func_224926_c("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(l)));
        String string3 = this.func_224938_a(Request.func_224951_b(string2, field_237691_f_.func_237694_a_(playerInfo)));
        return RealmsServer.func_230779_c_(string3);
    }

    public BackupList func_224923_d(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(l)));
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return BackupList.func_230753_a_(string2);
    }

    public void func_224922_b(long l, String string, String string2) throws RealmsServiceException {
        RealmsDescriptionDto realmsDescriptionDto = new RealmsDescriptionDto(string, string2);
        String string3 = this.func_224926_c("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(l)));
        this.func_224938_a(Request.func_224951_b(string3, field_237691_f_.func_237694_a_(realmsDescriptionDto)));
    }

    public void func_224925_a(long l, int n, RealmsWorldOptions realmsWorldOptions) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(l)).replace("$SLOT_ID", String.valueOf(n)));
        String string2 = realmsWorldOptions.func_230791_c_();
        this.func_224938_a(Request.func_224951_b(string, string2));
    }

    public boolean func_224927_a(long l, int n) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(l)).replace("$SLOT_ID", String.valueOf(n)));
        String string2 = this.func_224938_a(Request.func_224965_c(string, ""));
        return Boolean.valueOf(string2);
    }

    public void func_224928_c(long l, String string) throws RealmsServiceException {
        String string2 = this.func_224907_b("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(l)), "backupId=" + string);
        this.func_224938_a(Request.func_224966_b(string2, "", 40000, 600000));
    }

    public WorldTemplatePaginatedList func_224930_a(int n, int n2, RealmsServer.ServerType serverType) throws RealmsServiceException {
        String string = this.func_224907_b("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", serverType.toString()), String.format("page=%d&pageSize=%d", n, n2));
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return WorldTemplatePaginatedList.func_230804_a_(string2);
    }

    public Boolean func_224905_d(long l, String string) throws RealmsServiceException {
        String string2 = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", string).replace("$WORLD_ID", String.valueOf(l));
        String string3 = this.func_224926_c("worlds" + string2);
        return Boolean.valueOf(this.func_224938_a(Request.func_224965_c(string3, "")));
    }

    public Ops func_224906_e(long l, String string) throws RealmsServiceException {
        String string2 = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(l)).replace("$PROFILE_UUID", string);
        String string3 = this.func_224926_c("ops" + string2);
        return Ops.func_230754_a_(this.func_224938_a(Request.func_224951_b(string3, "")));
    }

    public Ops func_224929_f(long l, String string) throws RealmsServiceException {
        String string2 = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(l)).replace("$PROFILE_UUID", string);
        String string3 = this.func_224926_c("ops" + string2);
        return Ops.func_230754_a_(this.func_224938_a(Request.func_224952_b(string3)));
    }

    public Boolean func_224942_e(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(l)));
        String string2 = this.func_224938_a(Request.func_224965_c(string, ""));
        return Boolean.valueOf(string2);
    }

    public Boolean func_224932_f(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(l)));
        String string2 = this.func_224938_a(Request.func_224965_c(string, ""));
        return Boolean.valueOf(string2);
    }

    public Boolean func_224943_a(long l, String string, Integer n, boolean bl) throws RealmsServiceException {
        RealmsWorldResetDto realmsWorldResetDto = new RealmsWorldResetDto(string, -1L, n, bl);
        String string2 = this.func_224926_c("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(l)));
        String string3 = this.func_224938_a(Request.func_224959_a(string2, field_237691_f_.func_237694_a_(realmsWorldResetDto), 30000, 80000));
        return Boolean.valueOf(string3);
    }

    public Boolean func_224924_g(long l, String string) throws RealmsServiceException {
        RealmsWorldResetDto realmsWorldResetDto = new RealmsWorldResetDto(null, Long.valueOf(string), -1, false);
        String string2 = this.func_224926_c("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(l)));
        String string3 = this.func_224938_a(Request.func_224959_a(string2, field_237691_f_.func_237694_a_(realmsWorldResetDto), 30000, 80000));
        return Boolean.valueOf(string3);
    }

    public Subscription func_224933_g(long l) throws RealmsServiceException {
        String string = this.func_224926_c("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(l)));
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return Subscription.func_230793_a_(string2);
    }

    public int func_224909_j() throws RealmsServiceException {
        return this.func_224919_k().field_230569_a_.size();
    }

    public PendingInvitesList func_224919_k() throws RealmsServiceException {
        String string = this.func_224926_c("invites/pending");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        PendingInvitesList pendingInvitesList = PendingInvitesList.func_230756_a_(string2);
        pendingInvitesList.field_230569_a_.removeIf(this::func_244733_a);
        return pendingInvitesList;
    }

    private boolean func_244733_a(PendingInvite pendingInvite) {
        try {
            UUID uUID = UUID.fromString(pendingInvite.field_230566_d_);
            return this.field_244732_f.func_244599_aA().func_244757_e(uUID);
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }

    public void func_224901_a(String string) throws RealmsServiceException {
        String string2 = this.func_224926_c("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", string));
        this.func_224938_a(Request.func_224965_c(string2, ""));
    }

    public WorldDownload func_224917_b(long l, int n) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(l)).replace("$SLOT_ID", String.valueOf(n)));
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return WorldDownload.func_230802_a_(string2);
    }

    @Nullable
    public UploadInfo func_224934_h(long l, @Nullable String string) throws RealmsServiceException {
        String string2 = this.func_224926_c("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(l)));
        return UploadInfo.func_230796_a_(this.func_224938_a(Request.func_224965_c(string2, UploadInfo.func_243090_b(string))));
    }

    public void func_224913_b(String string) throws RealmsServiceException {
        String string2 = this.func_224926_c("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", string));
        this.func_224938_a(Request.func_224965_c(string2, ""));
    }

    public void func_224937_l() throws RealmsServiceException {
        String string = this.func_224926_c("mco/tos/agreed");
        this.func_224938_a(Request.func_224951_b(string, ""));
    }

    public RealmsNews func_224920_m() throws RealmsServiceException {
        String string = this.func_224926_c("mco/v1/news");
        String string2 = this.func_224938_a(Request.func_224960_a(string, 5000, 10000));
        return RealmsNews.func_230767_a_(string2);
    }

    public void func_224903_a(PingResult pingResult) throws RealmsServiceException {
        String string = this.func_224926_c("regions/ping/stat");
        this.func_224938_a(Request.func_224951_b(string, field_237691_f_.func_237694_a_(pingResult)));
    }

    public Boolean func_224914_n() throws RealmsServiceException {
        String string = this.func_224926_c("trial");
        String string2 = this.func_224938_a(Request.func_224953_a(string));
        return Boolean.valueOf(string2);
    }

    public void func_224916_h(long l) throws RealmsServiceException {
        String string = this.func_224926_c("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(l)));
        this.func_224938_a(Request.func_224952_b(string));
    }

    @Nullable
    private String func_224926_c(String string) {
        return this.func_224907_b(string, null);
    }

    @Nullable
    private String func_224907_b(String string, @Nullable String string2) {
        try {
            return new URI(RealmsClient.field_224944_a.field_224899_e, RealmsClient.field_224944_a.field_224898_d, "/" + string, string2, null).toASCIIString();
        } catch (URISyntaxException uRISyntaxException) {
            uRISyntaxException.printStackTrace();
            return null;
        }
    }

    private String func_224938_a(Request<?> request) throws RealmsServiceException {
        request.func_224962_a("sid", this.field_224947_d);
        request.func_224962_a("user", this.field_224948_e);
        request.func_224962_a("version", SharedConstants.getVersion().getName());
        try {
            int n = request.func_224958_b();
            if (n != 503 && n != 277) {
                String string = request.func_224963_c();
                if (n >= 200 && n < 300) {
                    return string;
                }
                if (n == 401) {
                    String string2 = request.func_224956_c("WWW-Authenticate");
                    field_224946_c.info("Could not authorize you against Realms server: " + string2);
                    throw new RealmsServiceException(n, string2, -1, string2);
                }
                if (string != null && string.length() != 0) {
                    RealmsError realmsError = RealmsError.func_241826_a_(string);
                    field_224946_c.error("Realms http code: " + n + " -  error code: " + realmsError.func_224974_b() + " -  message: " + realmsError.func_224973_a() + " - raw body: " + string);
                    throw new RealmsServiceException(n, string, realmsError);
                }
                field_224946_c.error("Realms error code: " + n + " message: " + string);
                throw new RealmsServiceException(n, string, n, "");
            }
            int n2 = request.func_224957_a();
            throw new RetryCallException(n2, n);
        } catch (RealmsHttpException realmsHttpException) {
            throw new RealmsServiceException(500, "Could not connect to Realms: " + realmsHttpException.getMessage(), -1, "");
        }
    }

    static {
        field_224946_c = LogManager.getLogger();
        field_237691_f_ = new PersistenceSerializer();
    }

    public static enum Environment {
        PRODUCTION("pc.realms.minecraft.net", "https"),
        STAGE("pc-stage.realms.minecraft.net", "https"),
        LOCAL("localhost:8080", "http");

        public String field_224898_d;
        public String field_224899_e;

        private Environment(String string2, String string3) {
            this.field_224898_d = string2;
            this.field_224899_e = string3;
        }
    }

    public static enum CompatibleVersionResponse {
        COMPATIBLE,
        OUTDATED,
        OTHER;

    }
}

