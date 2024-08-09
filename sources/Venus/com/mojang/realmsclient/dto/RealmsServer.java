/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServerPing;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RealmsServer
extends ValueObject {
    private static final Logger field_230600_s_ = LogManager.getLogger();
    public long field_230582_a_;
    public String field_230583_b_;
    public String field_230584_c_;
    public String field_230585_d_;
    public Status field_230586_e_;
    public String field_230587_f_;
    public String field_230588_g_;
    public List<PlayerInfo> field_230589_h_;
    public Map<Integer, RealmsWorldOptions> field_230590_i_;
    public boolean field_230591_j_;
    public boolean field_230592_k_;
    public int field_230593_l_;
    public ServerType field_230594_m_;
    public int field_230595_n_;
    public String field_230596_o_;
    public int field_230597_p_;
    public String field_230598_q_;
    public RealmsServerPing field_230599_r_ = new RealmsServerPing();

    public String func_230768_a_() {
        return this.field_230585_d_;
    }

    public String func_230775_b_() {
        return this.field_230584_c_;
    }

    public String func_230778_c_() {
        return this.field_230596_o_;
    }

    public void func_230773_a_(String string) {
        this.field_230584_c_ = string;
    }

    public void func_230777_b_(String string) {
        this.field_230585_d_ = string;
    }

    public void func_230772_a_(RealmsServerPlayerList realmsServerPlayerList) {
        ArrayList<String> arrayList = Lists.newArrayList();
        int n = 0;
        for (String string : realmsServerPlayerList.field_230610_b_) {
            if (string.equals(Minecraft.getInstance().getSession().getPlayerID())) continue;
            String string2 = "";
            try {
                string2 = RealmsUtil.func_225193_a(string);
            } catch (Exception exception) {
                field_230600_s_.error("Could not get name for " + string, (Throwable)exception);
                continue;
            }
            arrayList.add(string2);
            ++n;
        }
        this.field_230599_r_.field_230607_a_ = String.valueOf(n);
        this.field_230599_r_.field_230608_b_ = Joiner.on('\n').join(arrayList);
    }

    public static RealmsServer func_230770_a_(JsonObject jsonObject) {
        RealmsServer realmsServer = new RealmsServer();
        try {
            realmsServer.field_230582_a_ = JsonUtils.func_225169_a("id", jsonObject, -1L);
            realmsServer.field_230583_b_ = JsonUtils.func_225171_a("remoteSubscriptionId", jsonObject, null);
            realmsServer.field_230584_c_ = JsonUtils.func_225171_a("name", jsonObject, null);
            realmsServer.field_230585_d_ = JsonUtils.func_225171_a("motd", jsonObject, null);
            realmsServer.field_230586_e_ = RealmsServer.func_230780_d_(JsonUtils.func_225171_a("state", jsonObject, Status.CLOSED.name()));
            realmsServer.field_230587_f_ = JsonUtils.func_225171_a("owner", jsonObject, null);
            if (jsonObject.get("players") != null && jsonObject.get("players").isJsonArray()) {
                realmsServer.field_230589_h_ = RealmsServer.func_230769_a_(jsonObject.get("players").getAsJsonArray());
                RealmsServer.func_230771_a_(realmsServer);
            } else {
                realmsServer.field_230589_h_ = Lists.newArrayList();
            }
            realmsServer.field_230593_l_ = JsonUtils.func_225172_a("daysLeft", jsonObject, 0);
            realmsServer.field_230591_j_ = JsonUtils.func_225170_a("expired", jsonObject, false);
            realmsServer.field_230592_k_ = JsonUtils.func_225170_a("expiredTrial", jsonObject, false);
            realmsServer.field_230594_m_ = RealmsServer.func_230781_e_(JsonUtils.func_225171_a("worldType", jsonObject, ServerType.NORMAL.name()));
            realmsServer.field_230588_g_ = JsonUtils.func_225171_a("ownerUUID", jsonObject, "");
            realmsServer.field_230590_i_ = jsonObject.get("slots") != null && jsonObject.get("slots").isJsonArray() ? RealmsServer.func_230776_b_(jsonObject.get("slots").getAsJsonArray()) : RealmsServer.func_237697_e_();
            realmsServer.field_230596_o_ = JsonUtils.func_225171_a("minigameName", jsonObject, null);
            realmsServer.field_230595_n_ = JsonUtils.func_225172_a("activeSlot", jsonObject, -1);
            realmsServer.field_230597_p_ = JsonUtils.func_225172_a("minigameId", jsonObject, -1);
            realmsServer.field_230598_q_ = JsonUtils.func_225171_a("minigameImage", jsonObject, null);
        } catch (Exception exception) {
            field_230600_s_.error("Could not parse McoServer: " + exception.getMessage());
        }
        return realmsServer;
    }

    private static void func_230771_a_(RealmsServer realmsServer) {
        realmsServer.field_230589_h_.sort(RealmsServer::lambda$func_230771_a_$0);
    }

    private static List<PlayerInfo> func_230769_a_(JsonArray jsonArray) {
        ArrayList<PlayerInfo> arrayList = Lists.newArrayList();
        for (JsonElement jsonElement : jsonArray) {
            try {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                PlayerInfo playerInfo = new PlayerInfo();
                playerInfo.func_230758_a_(JsonUtils.func_225171_a("name", jsonObject, null));
                playerInfo.func_230761_b_(JsonUtils.func_225171_a("uuid", jsonObject, null));
                playerInfo.func_230759_a_(JsonUtils.func_225170_a("operator", jsonObject, false));
                playerInfo.func_230762_b_(JsonUtils.func_225170_a("accepted", jsonObject, false));
                playerInfo.func_230764_c_(JsonUtils.func_225170_a("online", jsonObject, false));
                arrayList.add(playerInfo);
            } catch (Exception exception) {}
        }
        return arrayList;
    }

    private static Map<Integer, RealmsWorldOptions> func_230776_b_(JsonArray jsonArray) {
        HashMap<Integer, RealmsWorldOptions> hashMap = Maps.newHashMap();
        for (JsonElement jsonElement : jsonArray) {
            try {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement2 = jsonParser.parse(jsonObject.get("options").getAsString());
                RealmsWorldOptions realmsWorldOptions = jsonElement2 == null ? RealmsWorldOptions.func_237700_a_() : RealmsWorldOptions.func_230788_a_(jsonElement2.getAsJsonObject());
                int n = JsonUtils.func_225172_a("slotId", jsonObject, -1);
                hashMap.put(n, realmsWorldOptions);
            } catch (Exception exception) {}
        }
        for (int i = 1; i <= 3; ++i) {
            if (hashMap.containsKey(i)) continue;
            hashMap.put(i, RealmsWorldOptions.func_237701_b_());
        }
        return hashMap;
    }

    private static Map<Integer, RealmsWorldOptions> func_237697_e_() {
        HashMap<Integer, RealmsWorldOptions> hashMap = Maps.newHashMap();
        hashMap.put(1, RealmsWorldOptions.func_237701_b_());
        hashMap.put(2, RealmsWorldOptions.func_237701_b_());
        hashMap.put(3, RealmsWorldOptions.func_237701_b_());
        return hashMap;
    }

    public static RealmsServer func_230779_c_(String string) {
        try {
            return RealmsServer.func_230770_a_(new JsonParser().parse(string).getAsJsonObject());
        } catch (Exception exception) {
            field_230600_s_.error("Could not parse McoServer: " + exception.getMessage());
            return new RealmsServer();
        }
    }

    private static Status func_230780_d_(String string) {
        try {
            return Status.valueOf(string);
        } catch (Exception exception) {
            return Status.CLOSED;
        }
    }

    private static ServerType func_230781_e_(String string) {
        try {
            return ServerType.valueOf(string);
        } catch (Exception exception) {
            return ServerType.NORMAL;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.field_230582_a_, this.field_230584_c_, this.field_230585_d_, this.field_230586_e_, this.field_230587_f_, this.field_230591_j_});
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object == this) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return true;
        }
        RealmsServer realmsServer = (RealmsServer)object;
        return new EqualsBuilder().append(this.field_230582_a_, realmsServer.field_230582_a_).append(this.field_230584_c_, realmsServer.field_230584_c_).append(this.field_230585_d_, realmsServer.field_230585_d_).append((Object)this.field_230586_e_, (Object)realmsServer.field_230586_e_).append(this.field_230587_f_, realmsServer.field_230587_f_).append(this.field_230591_j_, realmsServer.field_230591_j_).append((Object)this.field_230594_m_, (Object)this.field_230594_m_).isEquals();
    }

    public RealmsServer clone() {
        RealmsServer realmsServer = new RealmsServer();
        realmsServer.field_230582_a_ = this.field_230582_a_;
        realmsServer.field_230583_b_ = this.field_230583_b_;
        realmsServer.field_230584_c_ = this.field_230584_c_;
        realmsServer.field_230585_d_ = this.field_230585_d_;
        realmsServer.field_230586_e_ = this.field_230586_e_;
        realmsServer.field_230587_f_ = this.field_230587_f_;
        realmsServer.field_230589_h_ = this.field_230589_h_;
        realmsServer.field_230590_i_ = this.func_230774_a_(this.field_230590_i_);
        realmsServer.field_230591_j_ = this.field_230591_j_;
        realmsServer.field_230592_k_ = this.field_230592_k_;
        realmsServer.field_230593_l_ = this.field_230593_l_;
        realmsServer.field_230599_r_ = new RealmsServerPing();
        realmsServer.field_230599_r_.field_230607_a_ = this.field_230599_r_.field_230607_a_;
        realmsServer.field_230599_r_.field_230608_b_ = this.field_230599_r_.field_230608_b_;
        realmsServer.field_230594_m_ = this.field_230594_m_;
        realmsServer.field_230588_g_ = this.field_230588_g_;
        realmsServer.field_230596_o_ = this.field_230596_o_;
        realmsServer.field_230595_n_ = this.field_230595_n_;
        realmsServer.field_230597_p_ = this.field_230597_p_;
        realmsServer.field_230598_q_ = this.field_230598_q_;
        return realmsServer;
    }

    public Map<Integer, RealmsWorldOptions> func_230774_a_(Map<Integer, RealmsWorldOptions> map) {
        HashMap<Integer, RealmsWorldOptions> hashMap = Maps.newHashMap();
        for (Map.Entry<Integer, RealmsWorldOptions> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().clone());
        }
        return hashMap;
    }

    public String func_237696_a_(int n) {
        return this.field_230584_c_ + " (" + this.field_230590_i_.get(n).func_230787_a_(n) + ")";
    }

    public ServerData func_244783_d(String string) {
        return new ServerData(this.field_230584_c_, string, false);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static int lambda$func_230771_a_$0(PlayerInfo playerInfo, PlayerInfo playerInfo2) {
        return ComparisonChain.start().compareFalseFirst(playerInfo2.func_230765_d_(), playerInfo.func_230765_d_()).compare((Comparable<?>)((Object)playerInfo.func_230757_a_().toLowerCase(Locale.ROOT)), (Comparable<?>)((Object)playerInfo2.func_230757_a_().toLowerCase(Locale.ROOT))).result();
    }

    public static enum Status {
        CLOSED,
        OPEN,
        UNINITIALIZED;

    }

    public static enum ServerType {
        NORMAL,
        MINIGAME,
        ADVENTUREMAP,
        EXPERIENCE,
        INSPIRATION;

    }

    public static class ServerComparator
    implements Comparator<RealmsServer> {
        private final String field_223701_a;

        public ServerComparator(String string) {
            this.field_223701_a = string;
        }

        @Override
        public int compare(RealmsServer realmsServer, RealmsServer realmsServer2) {
            return ComparisonChain.start().compareTrueFirst(realmsServer.field_230586_e_ == Status.UNINITIALIZED, realmsServer2.field_230586_e_ == Status.UNINITIALIZED).compareTrueFirst(realmsServer.field_230592_k_, realmsServer2.field_230592_k_).compareTrueFirst(realmsServer.field_230587_f_.equals(this.field_223701_a), realmsServer2.field_230587_f_.equals(this.field_223701_a)).compareFalseFirst(realmsServer.field_230591_j_, realmsServer2.field_230591_j_).compareTrueFirst(realmsServer.field_230586_e_ == Status.OPEN, realmsServer2.field_230586_e_ == Status.OPEN).compare(realmsServer.field_230582_a_, realmsServer2.field_230582_a_).result();
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((RealmsServer)object, (RealmsServer)object2);
        }
    }
}

