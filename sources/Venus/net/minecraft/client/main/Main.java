/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.Empty3i;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.util.UndeclaredException;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.Session;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] stringArray) {
        Thread thread2;
        Minecraft minecraft;
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("disableMultiplayer");
        optionParser.accepts("disableChat");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec = optionParser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec2 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec3 = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (File[])new File[0]);
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec4 = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec5 = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec6 = optionParser.accepts("dataPackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec7 = optionParser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec8 = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (String[])new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec9 = optionParser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec10 = optionParser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec11 = optionParser.accepts("username").withRequiredArg().defaultsTo("fgst_" + Util.milliTime() % 1000L, (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec12 = optionParser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec13 = optionParser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec14 = optionParser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec15 = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec16 = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec17 = optionParser.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec<Integer> argumentAcceptingOptionSpec18 = optionParser.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec19 = optionParser.accepts("userProperties").withRequiredArg().defaultsTo("{}", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec20 = optionParser.accepts("profileProperties").withRequiredArg().defaultsTo("{}", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec21 = optionParser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec22 = optionParser.accepts("userType").withRequiredArg().defaultsTo("legacy", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec23 = optionParser.accepts("versionType").withRequiredArg().defaultsTo("release", (String[])new String[0]);
        NonOptionArgumentSpec<String> nonOptionArgumentSpec = optionParser.nonOptions();
        OptionSet optionSet = optionParser.parse(stringArray);
        List<String> list = optionSet.valuesOf(nonOptionArgumentSpec);
        if (!list.isEmpty()) {
            System.out.println("Completely ignored arguments: " + list);
        }
        String string = Main.getValue(optionSet, argumentAcceptingOptionSpec7);
        Proxy proxy = Proxy.NO_PROXY;
        if (string != null) {
            try {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(string, (int)Main.getValue(optionSet, argumentAcceptingOptionSpec8)));
            } catch (Exception exception) {
                // empty catch block
            }
        }
        String string2 = Main.getValue(optionSet, argumentAcceptingOptionSpec9);
        String string3 = Main.getValue(optionSet, argumentAcceptingOptionSpec10);
        if (!proxy.equals(Proxy.NO_PROXY) && Main.isNotEmpty(string2) && Main.isNotEmpty(string3)) {
            Authenticator.setDefault(new Authenticator(string2, string3){
                final String val$s1;
                final String val$s2;
                {
                    this.val$s1 = string;
                    this.val$s2 = string2;
                }

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.val$s1, this.val$s2.toCharArray());
                }
            });
        }
        int n = Main.getValue(optionSet, argumentAcceptingOptionSpec15);
        int n2 = Main.getValue(optionSet, argumentAcceptingOptionSpec16);
        OptionalInt optionalInt = Main.toOptionalInt(Main.getValue(optionSet, argumentAcceptingOptionSpec17));
        OptionalInt optionalInt2 = Main.toOptionalInt(Main.getValue(optionSet, argumentAcceptingOptionSpec18));
        boolean bl = optionSet.has("fullscreen");
        boolean bl2 = optionSet.has("demo");
        boolean bl3 = optionSet.has("disableMultiplayer");
        boolean bl4 = optionSet.has("disableChat");
        String string4 = Main.getValue(optionSet, argumentAcceptingOptionSpec14);
        Gson gson = new GsonBuilder().registerTypeAdapter((Type)((Object)PropertyMap.class), new PropertyMap.Serializer()).create();
        PropertyMap propertyMap = JSONUtils.fromJson(gson, Main.getValue(optionSet, argumentAcceptingOptionSpec19), PropertyMap.class);
        PropertyMap propertyMap2 = JSONUtils.fromJson(gson, Main.getValue(optionSet, argumentAcceptingOptionSpec20), PropertyMap.class);
        String string5 = Main.getValue(optionSet, argumentAcceptingOptionSpec23);
        File file = Main.getValue(optionSet, argumentAcceptingOptionSpec3);
        File file2 = optionSet.has(argumentAcceptingOptionSpec4) ? Main.getValue(optionSet, argumentAcceptingOptionSpec4) : new File(file, "assets/");
        File file3 = optionSet.has(argumentAcceptingOptionSpec5) ? Main.getValue(optionSet, argumentAcceptingOptionSpec5) : new File(file, "resourcepacks/");
        String string6 = optionSet.has(argumentAcceptingOptionSpec12) ? (String)argumentAcceptingOptionSpec12.value(optionSet) : PlayerEntity.getOfflineUUID((String)argumentAcceptingOptionSpec11.value(optionSet)).toString();
        String string7 = optionSet.has(argumentAcceptingOptionSpec21) ? (String)argumentAcceptingOptionSpec21.value(optionSet) : null;
        String string8 = Main.getValue(optionSet, argumentAcceptingOptionSpec);
        Integer n3 = Main.getValue(optionSet, argumentAcceptingOptionSpec2);
        CrashReport.crash();
        Bootstrap.register();
        Bootstrap.checkTranslations();
        Util.func_240994_l_();
        Session session = new Session((String)argumentAcceptingOptionSpec11.value(optionSet), string6, (String)argumentAcceptingOptionSpec13.value(optionSet), (String)argumentAcceptingOptionSpec22.value(optionSet));
        GameConfiguration gameConfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertyMap, propertyMap2, proxy), new ScreenSize(n, n2, optionalInt, optionalInt2, bl), new GameConfiguration.FolderInformation(file, file3, file2, string7), new GameConfiguration.GameInformation(bl2, string4, string5, bl3, bl4), new GameConfiguration.ServerInformation(string8, n3));
        Thread thread3 = new Thread("Client Shutdown Thread"){

            @Override
            public void run() {
                IntegratedServer integratedServer;
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft != null && (integratedServer = minecraft.getIntegratedServer()) != null) {
                    integratedServer.initiateShutdown(false);
                }
            }
        };
        thread3.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        Runtime.getRuntime().addShutdownHook(thread3);
        new Empty3i();
        try {
            Thread.currentThread().setName("Render thread");
            RenderSystem.initRenderThread();
            RenderSystem.beginInitialization();
            minecraft = new Minecraft(gameConfiguration);
            RenderSystem.finishInitialization();
        } catch (UndeclaredException undeclaredException) {
            LOGGER.warn("Failed to create window: ", (Throwable)undeclaredException);
            return;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Initializing game");
            crashReport.makeCategory("Initialization");
            Minecraft.fillCrashReport(null, gameConfiguration.gameInfo.version, null, crashReport);
            Minecraft.displayCrashReport(crashReport);
            return;
        }
        if (minecraft.isRenderOnThread()) {
            thread2 = new Thread("Game thread", minecraft){
                final Minecraft val$minecraft;
                {
                    this.val$minecraft = minecraft;
                    super(string);
                }

                @Override
                public void run() {
                    try {
                        RenderSystem.initGameThread(true);
                        this.val$minecraft.run();
                    } catch (Throwable throwable) {
                        LOGGER.error("Exception in client thread", throwable);
                    }
                }
            };
            thread2.start();
            while (minecraft.isRunning()) {
            }
        } else {
            thread2 = null;
            try {
                RenderSystem.initGameThread(false);
                minecraft.run();
            } catch (Throwable throwable) {
                LOGGER.error("Unhandled game exception", throwable);
            }
        }
        try {
            minecraft.shutdown();
            if (thread2 != null) {
                thread2.join();
            }
        } catch (InterruptedException interruptedException) {
            LOGGER.error("Exception during client thread shutdown", (Throwable)interruptedException);
        } finally {
            minecraft.shutdownMinecraftApplet();
        }
    }

    private static OptionalInt toOptionalInt(@Nullable Integer n) {
        return n != null ? OptionalInt.of(n) : OptionalInt.empty();
    }

    @Nullable
    private static <T> T getValue(OptionSet optionSet, OptionSpec<T> optionSpec) {
        try {
            return optionSet.valueOf(optionSpec);
        } catch (Throwable throwable) {
            ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec;
            List list;
            if (optionSpec instanceof ArgumentAcceptingOptionSpec && !(list = (argumentAcceptingOptionSpec = (ArgumentAcceptingOptionSpec)optionSpec).defaultValues()).isEmpty()) {
                return (T)list.get(0);
            }
            throw throwable;
        }
    }

    private static boolean isNotEmpty(@Nullable String string) {
        return string != null && !string.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}

