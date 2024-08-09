package xyz.mathax.mathaxclient.systems.accounts;

import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.mathax.AccountSwitchedEvent;
import xyz.mathax.mathaxclient.mixin.MinecraftClientAccessor;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import net.minecraft.client.util.Session;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Account<T extends Account<?>> implements ISerializable<T> {
    protected final AccountCache cache;

    protected AccountType type;

    protected String name;

    public Account(AccountType type, String name) {
        this.type = type;
        this.name = name;
        this.cache = new AccountCache();
    }

    public abstract boolean fetchInfo();

    public boolean login() {
        YggdrasilMinecraftSessionService service = (YggdrasilMinecraftSessionService) MatHax.mc.getSessionService();
        AccountUtils.setBaseUrl(service, YggdrasilEnvironment.PROD.getEnvironment().getSessionHost() + "/session/minecraft/");
        AccountUtils.setJoinUrl(service, YggdrasilEnvironment.PROD.getEnvironment().getSessionHost() + "/session/minecraft/join");
        AccountUtils.setCheckUrl(service, YggdrasilEnvironment.PROD.getEnvironment().getSessionHost() + "/session/minecraft/hasJoined");

        MatHax.EVENT_BUS.post(AccountSwitchedEvent.get());
        return true;
    }

    public String getUsername() {
        if (cache.username.isEmpty()) {
            return name;
        }

        return cache.username;
    }

    public AccountType getType() {
        return type;
    }

    public AccountCache getCache() {
        return cache;
    }

    protected void setSession(Session session) {
        ((MinecraftClientAccessor) MatHax.mc).setSession(session);
        MatHax.mc.getSessionProperties().clear();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type.name());
        json.put("name", name);
        json.put("cache", cache.toJson());
        return json;
    }

    @Override
    public T fromJson(JSONObject json) {
        if (json.has("name") && json.has("cache")) {
            name = json.getString("name");
            cache.fromJson(json.getJSONObject("cache"));
        } else {
            throw new JSONException("Account json didn't contain name or cache.");
        }

        return (T) this;
    }
}
