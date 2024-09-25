/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  io.netty.channel.EventLoop
 *  io.netty.channel.local.LocalEventLoopGroup
 *  org.apache.logging.log4j.LogManager
 */
package com.github.creeper123123321.viafabric;

import com.github.creeper123123321.viafabric.platform.VRInjector;
import com.github.creeper123123321.viafabric.platform.VRLoader;
import com.github.creeper123123321.viafabric.platform.VRPlatform;
import com.github.creeper123123321.viafabric.util.JLoggerToLog4j;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import viamcp.platform.ViaBackwardsPlatformImplementation;
import viamcp.platform.ViaRewindPlatformImplementation;

public class ViaFabric {
    public static int clientSideVersion = 47;
    public static final Logger JLOGGER = new JLoggerToLog4j(LogManager.getLogger((String)"ViaFabric"));
    public static final ExecutorService ASYNC_EXECUTOR;
    public static final EventLoop EVENT_LOOP;
    public static CompletableFuture<Void> INIT_FUTURE;

    static {
        INIT_FUTURE = new CompletableFuture();
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ViaFabric-%d").build();
        ASYNC_EXECUTOR = Executors.newFixedThreadPool(8, factory);
        EVENT_LOOP = new LocalEventLoopGroup(1, factory).next();
        EVENT_LOOP.submit(INIT_FUTURE::join);
    }

    public static String getVersion() {
        return "1.0";
    }

    public void onInitialize() throws IllegalAccessException, NoSuchFieldException, MalformedURLException {
        this.loadVia();
        Via.init(ViaManager.builder().injector(new VRInjector()).loader(new VRLoader()).platform(new VRPlatform()).build());
        MappingDataLoader.enableMappingsCache();
        new ViaBackwardsPlatformImplementation();
        new ViaRewindPlatformImplementation();
        Via.getManager().init();
        INIT_FUTURE.complete(null);
    }

    public void loadVia() throws NoSuchFieldException, IllegalAccessException, MalformedURLException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Field addUrl = loader.getClass().getDeclaredField("ucp");
        addUrl.setAccessible(true);
    }
}

