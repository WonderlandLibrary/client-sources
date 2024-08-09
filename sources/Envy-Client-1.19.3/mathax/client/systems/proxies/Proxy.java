package mathax.client.systems.proxies;

import mathax.client.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

import java.net.InetSocketAddress;

public class Proxy implements ISerializable<Proxy> {
    public ProxyType type = ProxyType.Socks5;

    public String name = "";
    public String username = "";
    public String password = "";
    public String address = "";

    public boolean enabled = false;

    public int port = 0;

    public boolean resolveAddress() {
        if (port <= 0 || port > 65535 || address == null || address.isEmpty()) return false;
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        return !socketAddress.isUnresolved();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("type", type.name());

        tag.putString("name", name);
        tag.putString("username", username);
        tag.putString("password", password);
        tag.putString("ip", address);

        tag.putInt("port", port);

        tag.putBoolean("enabled", enabled);

        return tag;
    }

    @Override
    public Proxy fromTag(NbtCompound tag) {
        type = ProxyType.valueOf(tag.getString("type"));

        name = tag.getString("name");
        username = tag.getString("username");
        password = tag.getString("password");
        address = tag.getString("ip");

        port = tag.getInt("port");

        enabled = tag.getBoolean("enabled");

        return this;
    }
}
