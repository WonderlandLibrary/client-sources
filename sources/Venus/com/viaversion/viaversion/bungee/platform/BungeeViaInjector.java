/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 */
package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.bungee.handlers.BungeeChannelInitializer;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.util.SetWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ProxyServer;

public class BungeeViaInjector
implements ViaInjector {
    private static final Field LISTENERS_FIELD;
    private final List<Channel> injectedChannels = new ArrayList<Channel>();

    @Override
    public void inject() throws ReflectiveOperationException {
        Set set = (Set)LISTENERS_FIELD.get(ProxyServer.getInstance());
        SetWrapper<Channel> setWrapper = new SetWrapper<Channel>(set, this::lambda$inject$0);
        LISTENERS_FIELD.set(ProxyServer.getInstance(), setWrapper);
        for (Channel channel : set) {
            this.injectChannel(channel);
        }
    }

    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Bungee without a reboot!");
    }

    private void injectChannel(Channel channel) throws ReflectiveOperationException {
        List<String> list = channel.pipeline().names();
        Object object = null;
        for (String object2 : list) {
            ChannelHandler channelHandler = channel.pipeline().get(object2);
            try {
                ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                object = channelHandler;
            } catch (Exception exception) {}
        }
        if (object == null) {
            object = channel.pipeline().first();
        }
        if (object.getClass().getName().equals("net.md_5.bungee.query.QueryHandler")) {
            return;
        }
        try {
            ChannelInitializer channelInitializer = ReflectionUtil.get(object, "childHandler", ChannelInitializer.class);
            BungeeChannelInitializer bungeeChannelInitializer = new BungeeChannelInitializer(channelInitializer);
            ReflectionUtil.set(object, "childHandler", bungeeChannelInitializer);
            this.injectedChannels.add(channel);
        } catch (NoSuchFieldException noSuchFieldException) {
            throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + object.getClass().getName());
        }
    }

    @Override
    public int getServerProtocolVersion() throws Exception {
        return this.getBungeeSupportedVersions().get(0);
    }

    @Override
    public IntSortedSet getServerProtocolVersions() throws Exception {
        return new IntLinkedOpenHashSet(this.getBungeeSupportedVersions());
    }

    private List<Integer> getBungeeSupportedVersions() throws Exception {
        return ReflectionUtil.getStatic(Class.forName("net.md_5.bungee.protocol.ProtocolConstants"), "SUPPORTED_VERSION_IDS", List.class);
    }

    @Override
    public JsonObject getDump() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (Channel channel : this.injectedChannels) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("channelClass", channel.getClass().getName());
            JsonArray jsonArray2 = new JsonArray();
            for (String string : channel.pipeline().names()) {
                JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("name", string);
                ChannelHandler channelHandler = channel.pipeline().get(string);
                if (channelHandler == null) {
                    jsonObject3.addProperty("status", "INVALID");
                    continue;
                }
                jsonObject3.addProperty("class", channelHandler.getClass().getName());
                try {
                    ChannelInitializer channelInitializer = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                    jsonObject3.addProperty("childClass", channelInitializer.getClass().getName());
                    if (channelInitializer instanceof BungeeChannelInitializer) {
                        jsonObject3.addProperty("oldInit", ((BungeeChannelInitializer)channelInitializer).getOriginal().getClass().getName());
                    }
                } catch (ReflectiveOperationException reflectiveOperationException) {
                    // empty catch block
                }
                jsonArray2.add(jsonObject3);
            }
            jsonObject2.add("pipeline", jsonArray2);
            jsonArray.add(jsonObject2);
        }
        jsonObject.add("injectedChannelInitializers", jsonArray);
        try {
            Object object = LISTENERS_FIELD.get(ProxyServer.getInstance());
            jsonObject.addProperty("currentList", object.getClass().getName());
            if (object instanceof SetWrapper) {
                jsonObject.addProperty("wrappedList", ((SetWrapper)object).originalSet().getClass().getName());
            }
        } catch (ReflectiveOperationException reflectiveOperationException) {
            // empty catch block
        }
        return jsonObject;
    }

    private void lambda$inject$0(Channel channel) {
        try {
            this.injectChannel(channel);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    static {
        try {
            LISTENERS_FIELD = ProxyServer.getInstance().getClass().getDeclaredField("listeners");
            LISTENERS_FIELD.setAccessible(false);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException("Unable to access listeners field.", reflectiveOperationException);
        }
    }
}

