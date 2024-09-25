/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelInitializer
 */
package us.myles.ViaVersion.bungee.platform;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.platform.ViaInjector;
import us.myles.ViaVersion.bungee.handlers.BungeeChannelInitializer;
import us.myles.ViaVersion.util.ReflectionUtil;
import us.myles.viaversion.libs.gson.JsonObject;

public class BungeeViaInjector
implements ViaInjector {
    @Override
    public void inject() throws Exception {
        try {
            Class<?> pipelineUtils = Class.forName("net.md_5.bungee.netty.PipelineUtils");
            Field field = pipelineUtils.getDeclaredField("SERVER_CHILD");
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers)) {
                try {
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, modifiers & 0xFFFFFFEF);
                }
                catch (NoSuchFieldException e) {
                    Field[] fields;
                    Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", Boolean.TYPE);
                    getDeclaredFields0.setAccessible(true);
                    for (Field classField : fields = (Field[])getDeclaredFields0.invoke(Field.class, false)) {
                        if (!"modifiers".equals(classField.getName())) continue;
                        classField.setAccessible(true);
                        classField.set(field, modifiers & 0xFFFFFFEF);
                        break;
                    }
                }
            }
            BungeeChannelInitializer newInit = new BungeeChannelInitializer((ChannelInitializer<Channel>)((ChannelInitializer)field.get(null)));
            field.set(null, (Object)newInit);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Unable to inject ViaVersion, please post these details on our GitHub and ensure you're using a compatible server version.");
            throw e;
        }
    }

    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Bungee without a reboot!");
    }

    @Override
    public int getServerProtocolVersion() throws Exception {
        return (Integer)ReflectionUtil.getStatic(Class.forName("net.md_5.bungee.protocol.ProtocolConstants"), "SUPPORTED_VERSION_IDS", List.class).get(0);
    }

    @Override
    public String getEncoderName() {
        return "via-encoder";
    }

    @Override
    public String getDecoderName() {
        return "via-decoder";
    }

    private ChannelInitializer<Channel> getChannelInitializer() throws Exception {
        Class<?> pipelineUtils = Class.forName("net.md_5.bungee.netty.PipelineUtils");
        Field field = pipelineUtils.getDeclaredField("SERVER_CHILD");
        field.setAccessible(true);
        return (ChannelInitializer)field.get(null);
    }

    @Override
    public JsonObject getDump() {
        JsonObject data = new JsonObject();
        try {
            ChannelInitializer<Channel> initializer = this.getChannelInitializer();
            data.addProperty("currentInitializer", initializer.getClass().getName());
            if (initializer instanceof BungeeChannelInitializer) {
                data.addProperty("originalInitializer", ((BungeeChannelInitializer)initializer).getOriginal().getClass().getName());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return data;
    }
}

