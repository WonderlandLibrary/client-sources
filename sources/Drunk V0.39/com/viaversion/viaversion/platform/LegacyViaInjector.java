/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
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
import java.util.Iterator;
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
        Object connection = this.getServerConnection();
        if (connection == null) {
            throw new RuntimeException("Failed to find the core component 'ServerConnection'");
        }
        Field[] fieldArray = connection.getClass().getDeclaredFields();
        int n = fieldArray.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = fieldArray[n2];
            if (List.class.isAssignableFrom(field.getType()) && field.getGenericType().getTypeName().contains(ChannelFuture.class.getName())) {
                field.setAccessible(true);
                List list = (List)field.get(connection);
                SynchronizedListWrapper<Object> wrappedList = new SynchronizedListWrapper<Object>(list, o -> {
                    try {
                        this.injectChannelFuture((ChannelFuture)o);
                        return;
                    }
                    catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                });
                List list2 = list;
                synchronized (list2) {
                    for (ChannelFuture future : list) {
                        this.injectChannelFuture(future);
                    }
                    field.set(connection, wrappedList);
                }
                this.injectedLists.add(new Pair<Field, Object>(field, connection));
            }
            ++n2;
        }
    }

    private void injectChannelFuture(ChannelFuture future) throws ReflectiveOperationException {
        List names = future.channel().pipeline().names();
        ChannelHandler bootstrapAcceptor = null;
        for (String name : names) {
            ChannelHandler handler = future.channel().pipeline().get(name);
            try {
                ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
                bootstrapAcceptor = handler;
                break;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
            }
        }
        if (bootstrapAcceptor == null) {
            bootstrapAcceptor = future.channel().pipeline().first();
        }
        try {
            ChannelInitializer oldInitializer = ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
            ReflectionUtil.set(bootstrapAcceptor, "childHandler", this.createChannelInitializer((ChannelInitializer<Channel>)oldInitializer));
            this.injectedFutures.add(future);
            return;
        }
        catch (NoSuchFieldException ignored) {
            this.blame(bootstrapAcceptor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void uninject() throws ReflectiveOperationException {
        Iterator<Object> iterator = this.injectedFutures.iterator();
        while (true) {
            if (!iterator.hasNext()) break;
            ChannelFuture future = iterator.next();
            List names = future.channel().pipeline().names();
            ChannelHandler bootstrapAcceptor = null;
            for (String name : names) {
                ChannelHandler handler = future.channel().pipeline().get(name);
                try {
                    if (!(ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class) instanceof WrappedChannelInitializer)) continue;
                    bootstrapAcceptor = handler;
                    break;
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                }
            }
            if (bootstrapAcceptor == null) {
                bootstrapAcceptor = future.channel().pipeline().first();
            }
            try {
                ChannelInitializer initializer = ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
                if (!(initializer instanceof WrappedChannelInitializer)) continue;
                ReflectionUtil.set(bootstrapAcceptor, "childHandler", ((WrappedChannelInitializer)initializer).original());
            }
            catch (Exception e) {
                Via.getPlatform().getLogger().severe("Failed to remove injection handler, reload won't work with connections, please reboot!");
                e.printStackTrace();
            }
        }
        this.injectedFutures.clear();
        iterator = this.injectedLists.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.injectedLists.clear();
                return;
            }
            Pair pair = (Pair)iterator.next();
            try {
                List originalList;
                Field field = (Field)pair.key();
                Object o = field.get(pair.value());
                if (!(o instanceof SynchronizedListWrapper)) continue;
                List list = originalList = ((SynchronizedListWrapper)o).originalList();
                synchronized (list) {
                    field.set(pair.value(), originalList);
                    continue;
                }
            }
            catch (ReflectiveOperationException e) {
                Via.getPlatform().getLogger().severe("Failed to remove injection, reload won't work with connections, please reboot!");
                continue;
            }
            break;
        }
    }

    @Override
    public boolean lateProtocolVersionSetting() {
        return true;
    }

    @Override
    public JsonObject getDump() {
        JsonObject data = new JsonObject();
        JsonArray injectedChannelInitializers = new JsonArray();
        data.add("injectedChannelInitializers", injectedChannelInitializers);
        Iterator<ChannelFuture> iterator = this.injectedFutures.iterator();
        block4: while (true) {
            if (!iterator.hasNext()) {
                JsonObject wrappedLists = new JsonObject();
                JsonObject currentLists = new JsonObject();
                try {
                    Iterator<Pair<Field, Object>> iterator2 = this.injectedLists.iterator();
                    while (true) {
                        if (!iterator2.hasNext()) {
                            data.add("wrappedLists", wrappedLists);
                            data.add("currentLists", currentLists);
                            return data;
                        }
                        Pair<Field, Object> pair = iterator2.next();
                        Field field = pair.key();
                        Object list = field.get(pair.value());
                        currentLists.addProperty(field.getName(), list.getClass().getName());
                        if (!(list instanceof SynchronizedListWrapper)) continue;
                        wrappedLists.addProperty(field.getName(), ((SynchronizedListWrapper)list).originalList().getClass().getName());
                    }
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    // empty catch block
                }
                return data;
            }
            ChannelFuture future = iterator.next();
            JsonObject futureInfo = new JsonObject();
            injectedChannelInitializers.add(futureInfo);
            futureInfo.addProperty("futureClass", future.getClass().getName());
            futureInfo.addProperty("channelClass", future.channel().getClass().getName());
            JsonArray pipeline = new JsonArray();
            futureInfo.add("pipeline", pipeline);
            Iterator iterator3 = future.channel().pipeline().names().iterator();
            while (true) {
                if (!iterator3.hasNext()) continue block4;
                String pipeName = (String)iterator3.next();
                JsonObject handlerInfo = new JsonObject();
                pipeline.add(handlerInfo);
                handlerInfo.addProperty("name", pipeName);
                ChannelHandler channelHandler = future.channel().pipeline().get(pipeName);
                if (channelHandler == null) {
                    handlerInfo.addProperty("status", "INVALID");
                    continue;
                }
                handlerInfo.addProperty("class", channelHandler.getClass().getName());
                try {
                    ChannelInitializer child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                    handlerInfo.addProperty("childClass", child.getClass().getName());
                    if (!(child instanceof WrappedChannelInitializer)) continue;
                    handlerInfo.addProperty("oldInit", ((WrappedChannelInitializer)child).original().getClass().getName());
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                }
            }
            break;
        }
    }

    @Override
    public String getEncoderName() {
        return "encoder";
    }

    @Override
    public String getDecoderName() {
        return "decoder";
    }

    protected abstract @Nullable Object getServerConnection() throws ReflectiveOperationException;

    protected abstract WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> var1);

    protected abstract void blame(ChannelHandler var1) throws ReflectiveOperationException;
}

