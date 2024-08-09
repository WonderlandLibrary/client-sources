package xyz.mathax.mathaxclient.systems.proxies;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.settings.*;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Proxy implements ISerializable<Proxy> {
    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup optionalSettings = settings.createGroup("Optional");

    // General

    public Setting<String> nameSetting = generalSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the proxy.")
            .defaultValue("")
            .build()
    );

    public Setting<ProxyType> typeSetting = generalSettings.add(new EnumSetting.Builder<ProxyType>()
            .name("Type")
            .description("The type of proxy.")
            .defaultValue(ProxyType.Socks5)
            .build()
    );

    public Setting<String> addressSetting = generalSettings.add(new StringSetting.Builder()
            .name("Address")
            .description("The ip address of the proxy.")
            .defaultValue("")
            .build()
    );

    public Setting<Integer> portSetting = generalSettings.add(new IntSetting.Builder()
            .name("Port")
            .description("The port of the proxy.")
            .defaultValue(0)
            .range(0, 65535)
            .sliderRange(0, 65535)
            .build()
    );

    public Setting<Boolean> enabledSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Whether the proxy is enabled.")
            .defaultValue(true)
            .build()
    );

    // Optional

    public Setting<String> usernameSetting = optionalSettings.add(new StringSetting.Builder()
            .name("username")
            .description("The username of the proxy.")
            .defaultValue("")
            .build()
    );

    public Setting<String> passwordSetting = optionalSettings.add(new StringSetting.Builder()
            .name("password")
            .description("The password of the proxy.")
            .defaultValue("")
            .visible(() -> typeSetting.get().equals(ProxyType.Socks5))
            .build()
    );

    public boolean resolveAddress() {
        int port = portSetting.get();
        String address = addressSetting.get();
        if (port <= 0 || port > 65535 || address == null || address.isBlank()) {
            return false;
        }

        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        return !socketAddress.isUnresolved();
    }

    public static class Builder {
        protected ProxyType type = ProxyType.Socks5;

        protected String address = "";

        protected int port = 0;

        protected String name = "";

        protected String username = "";

        protected boolean enabled = false;

        public Builder type(ProxyType type) {
            this.type = type;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Proxy build() {
            Proxy proxy = new Proxy();
            if (!type.equals(proxy.typeSetting.getDefaultValue())) {
                proxy.typeSetting.set(type);
            }

            if (!address.equals(proxy.addressSetting.getDefaultValue())) {
                proxy.addressSetting.set(address);
            }

            if (port != proxy.portSetting.getDefaultValue()) {
                proxy.portSetting.set(port);
            }

            if (!name.equals(proxy.nameSetting.getDefaultValue())) {
                proxy.nameSetting.set(name);
            }

            if (!username.equals(proxy.usernameSetting.getDefaultValue())) {
                proxy.usernameSetting.set(username);
            }

            if (enabled != proxy.enabledSetting.getDefaultValue()) {
                proxy.enabledSetting.set(enabled);
            }

            return proxy;
        }
    }

    public void save(File folder) {
        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        File file = new File(folder, nameSetting.get() + ".json");
        JSONUtils.saveJSON(json, file);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        return json;
    }

    public void load(File folder) {
        File file = new File(folder, nameSetting.get() + ".json");
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);
    }

    @Override
    public Proxy fromJson(JSONObject json) {
        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Proxy proxy = (Proxy) object;
        return Objects.equals(proxy.addressSetting.get(), this.addressSetting.get()) && Objects.equals(proxy.portSetting.get(), this.portSetting.get());
    }
}
