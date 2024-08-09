/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.util.SynchronizedListWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyViaInjector
implements ViaInjector {
    protected final List<ChannelFuture> injectedFutures = new ArrayList<ChannelFuture>();
    protected final List<Pair<Field, Object>> injectedLists = new ArrayList<Pair<Field, Object>>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void inject() throws ReflectiveOperationException {
        Object object = this.getServerConnection();
        if (object == null) {
            throw new RuntimeException("Failed to find the core component 'ServerConnection'");
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!List.class.isAssignableFrom(field.getType()) || !field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) continue;
            field.setAccessible(false);
            List list = (List)field.get(object);
            SynchronizedListWrapper<Object> synchronizedListWrapper = new SynchronizedListWrapper<Object>(list, this::lambda$inject$0);
            List list2 = list;
            synchronized (list2) {
                for (ChannelFuture channelFuture : list) {
                    this.injectChannelFuture(channelFuture);
                }
                field.set(object, synchronizedListWrapper);
            }
            this.injectedLists.add(new Pair<Field, Object>(field, object));
        }
    }

    private void injectChannelFuture(ChannelFuture channelFuture) throws ReflectiveOperationException {
        List<String> list = channelFuture.channel().pipeline().names();
        ChannelHandler channelHandler = null;
        for (String string : list) {
            ChannelHandler channelHandler2 = channelFuture.channel().pipeline().get(string);
            try {
                ReflectionUtil.get(channelHandler2, "childHandler", ChannelInitializer.class);
                channelHandler = channelHandler2;
                break;
            } catch (ReflectiveOperationException reflectiveOperationException) {
            }
        }
        if (channelHandler == null) {
            channelHandler = channelFuture.channel().pipeline().first();
        }
        try {
            ChannelInitializer channelInitializer = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
            ReflectionUtil.set(channelHandler, "childHandler", this.createChannelInitializer(channelInitializer));
            this.injectedFutures.add(channelFuture);
        } catch (NoSuchFieldException noSuchFieldException) {
            this.blame(channelHandler);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void uninject() throws ReflectiveOperationException {
        Object object;
        Object object2;
        Object object3;
        for (ChannelFuture object4 : this.injectedFutures) {
            object3 = object4.channel().pipeline();
            object2 = object3.first();
            if (object2 == null) {
                Via.getPlatform().getLogger().info("Empty pipeline, nothing to uninject");
                continue;
            }
            object = object3.names().iterator();
            while (object.hasNext()) {
                String string = object.next();
                ChannelHandler channelHandler = object3.get(string);
                if (channelHandler == null) {
                    Via.getPlatform().getLogger().warning("Could not get handler " + string);
                    continue;
                }
                try {
                    if (!(ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer)) continue;
                    object2 = channelHandler;
                    break;
                } catch (ReflectiveOperationException reflectiveOperationException) {
                }
            }
            try {
                object = ReflectionUtil.get(object2, "childHandler", ChannelInitializer.class);
                if (!(object instanceof WrappedChannelInitializer)) continue;
                ReflectionUtil.set(object2, "childHandler", ((WrappedChannelInitializer)object).original());
            } catch (Exception exception) {
                Via.getPlatform().getLogger().severe("Failed to remove injection handler, reload won't work with connections, please reboot!");
                exception.printStackTrace();
            }
        }
        this.injectedFutures.clear();
        for (Pair pair : this.injectedLists) {
            try {
                object3 = (Field)pair.key();
                object2 = ((Field)object3).get(pair.value());
                if (!(object2 instanceof SynchronizedListWrapper)) continue;
                List list = object = ((SynchronizedListWrapper)object2).originalList();
                synchronized (list) {
                    ((Field)object3).set(pair.value(), object);
                }
            } catch (ReflectiveOperationException reflectiveOperationException) {
                Via.getPlatform().getLogger().severe("Failed to remove injection, reload won't work with connections, please reboot!");
            }
        }
        this.injectedLists.clear();
    }

    @Override
    public boolean lateProtocolVersionSetting() {
        return false;
    }

    @Override
    public JsonObject getDump() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonObject.add("injectedChannelInitializers", jsonArray);
        for (ChannelFuture object2 : this.injectedFutures) {
            JsonObject reflectiveOperationException = new JsonObject();
            jsonArray.add(reflectiveOperationException);
            reflectiveOperationException.addProperty("futureClass", object2.getClass().getName());
            reflectiveOperationException.addProperty("channelClass", object2.channel().getClass().getName());
            JsonArray jsonArray2 = new JsonArray();
            reflectiveOperationException.add("pipeline", jsonArray2);
            for (String string : object2.channel().pipeline().names()) {
                JsonObject jsonObject2 = new JsonObject();
                jsonArray2.add(jsonObject2);
                jsonObject2.addProperty("name", string);
                ChannelHandler channelHandler = object2.channel().pipeline().get(string);
                if (channelHandler == null) {
                    jsonObject2.addProperty("status", "INVALID");
                    continue;
                }
                jsonObject2.addProperty("class", channelHandler.getClass().getName());
                try {
                    ChannelInitializer channelInitializer = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                    jsonObject2.addProperty("childClass", channelInitializer.getClass().getName());
                    if (!(channelInitializer instanceof WrappedChannelInitializer)) continue;
                    jsonObject2.addProperty("oldInit", ((WrappedChannelInitializer)((Object)channelInitializer)).original().getClass().getName());
                } catch (ReflectiveOperationException reflectiveOperationException2) {}
            }
        }
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        try {
            for (Pair pair : this.injectedLists) {
                Field field = (Field)pair.key();
                Object object = field.get(pair.value());
                jsonObject3.addProperty(field.getName(), object.getClass().getName());
                if (!(object instanceof SynchronizedListWrapper)) continue;
                jsonObject4.addProperty(field.getName(), ((SynchronizedListWrapper)object).originalList().getClass().getName());
            }
            jsonObject.add("wrappedLists", jsonObject4);
            jsonObject.add("currentLists", jsonObject3);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            // empty catch block
        }
        return jsonObject;
    }

    protected abstract @Nullable Object getServerConnection() throws ReflectiveOperationException;

    protected abstract WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> var1);

    protected abstract void blame(ChannelHandler var1) throws ReflectiveOperationException;

    private void lambda$inject$0(Object object) {
        try {
            this.injectChannelFuture((ChannelFuture)object);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

