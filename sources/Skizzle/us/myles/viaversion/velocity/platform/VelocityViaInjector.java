/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.network.ProtocolVersion
 *  io.netty.channel.ChannelInitializer
 */
package us.myles.ViaVersion.velocity.platform;

import com.velocitypowered.api.network.ProtocolVersion;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import us.myles.ViaVersion.VelocityPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.platform.ViaInjector;
import us.myles.ViaVersion.util.ReflectionUtil;
import us.myles.ViaVersion.velocity.handlers.VelocityChannelInitializer;
import us.myles.viaversion.libs.gson.JsonObject;

public class VelocityViaInjector
implements ViaInjector {
    public static Method getPlayerInfoForwardingMode;

    private ChannelInitializer getInitializer() throws Exception {
        Object connectionManager = ReflectionUtil.get((Object)VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
        return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
    }

    private ChannelInitializer getBackendInitializer() throws Exception {
        Object connectionManager = ReflectionUtil.get((Object)VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
        return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
    }

    @Override
    public void inject() throws Exception {
        Object connectionManager = ReflectionUtil.get((Object)VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
        ChannelInitializer originalInitializer = this.getInitializer();
        channelInitializerHolder.getClass().getMethod("set", ChannelInitializer.class).invoke(channelInitializerHolder, new Object[]{new VelocityChannelInitializer(originalInitializer, false)});
        Object backendInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
        ChannelInitializer backendInitializer = this.getBackendInitializer();
        backendInitializerHolder.getClass().getMethod("set", ChannelInitializer.class).invoke(backendInitializerHolder, new Object[]{new VelocityChannelInitializer(backendInitializer, true)});
    }

    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
    }

    @Override
    public int getServerProtocolVersion() throws Exception {
        return VelocityViaInjector.getLowestSupportedProtocolVersion();
    }

    public static int getLowestSupportedProtocolVersion() {
        try {
            if (getPlayerInfoForwardingMode != null && ((Enum)getPlayerInfoForwardingMode.invoke((Object)VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
                return us.myles.ViaVersion.api.protocol.ProtocolVersion.v1_13.getVersion();
            }
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
        return ProtocolVersion.MINIMUM_VERSION.getProtocol();
    }

    @Override
    public String getEncoderName() {
        return "via-encoder";
    }

    @Override
    public String getDecoderName() {
        return "via-decoder";
    }

    @Override
    public JsonObject getDump() {
        JsonObject data = new JsonObject();
        try {
            data.addProperty("currentInitializer", this.getInitializer().getClass().getName());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return data;
    }

    static {
        try {
            getPlayerInfoForwardingMode = Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode", new Class[0]);
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

