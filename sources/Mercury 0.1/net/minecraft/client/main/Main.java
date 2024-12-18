/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.Session;

public class Main {
    private static final String __OBFID = "CL_00001461";

    public static void main(String[] p_main_0_) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser var1 = new OptionParser();
        var1.allowsUnrecognizedOptions();
        var1.accepts("demo");
        var1.accepts("fullscreen");
        var1.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec<String> var2 = var1.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, new Integer[0]);
        ArgumentAcceptingOptionSpec<File> var4 = var1.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
        ArgumentAcceptingOptionSpec<File> var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<File> var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<String> var7 = var1.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec<String> var9 = var1.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> var10 = var1.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> var11 = var1.accepts("username").withRequiredArg().defaultsTo("Mercury" + Minecraft.getSystemTime() % 1000L, new String[0]);
        ArgumentAcceptingOptionSpec<String> var12 = var1.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> var13 = var1.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<String> var14 = var1.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<Integer> var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, new Integer[0]);
        ArgumentAcceptingOptionSpec<Integer> var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, new Integer[0]);
        ArgumentAcceptingOptionSpec<String> var17 = var1.accepts("userProperties").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<String> var18 = var1.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> var19 = var1.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
        NonOptionArgumentSpec<String> var20 = var1.nonOptions();
        OptionSet var21 = var1.parse(p_main_0_);
        List<String> var22 = var21.valuesOf(var20);
        if (!var22.isEmpty()) {
            System.out.println("Completely ignored arguments: " + var22);
        }
        String var23 = var21.valueOf(var7);
        Proxy var24 = Proxy.NO_PROXY;
        if (var23 != null) {
            try {
                var24 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(var23, (int)var21.valueOf(var8)));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        final String var25 = var21.valueOf(var9);
        final String var26 = var21.valueOf(var10);
        if (!var24.equals(Proxy.NO_PROXY) && Main.func_110121_a(var25) && Main.func_110121_a(var26)) {
            Authenticator.setDefault(new Authenticator(){
                private static final String __OBFID = "CL_00000828";

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(var25, var26.toCharArray());
                }
            });
        }
        int var27 = var21.valueOf(var15);
        int var28 = var21.valueOf(var16);
        boolean var29 = var21.has("fullscreen");
        boolean var30 = var21.has("checkGlErrors");
        boolean var31 = var21.has("demo");
        String var32 = var21.valueOf(var14);
        PropertyMap var33 = new GsonBuilder().registerTypeAdapter((Type)((Object)PropertyMap.class), new PropertyMap.Serializer()).create().fromJson(var21.valueOf(var17), PropertyMap.class);
        File var34 = var21.valueOf(var4);
        File var35 = var21.has(var5) ? var21.valueOf(var5) : new File(var34, "assets/");
        File var36 = var21.has(var6) ? var21.valueOf(var6) : new File(var34, "resourcepacks/");
        String var37 = var21.has(var12) ? (String)var12.value(var21) : (String)var11.value(var21);
        String var38 = var21.has(var18) ? (String)var18.value(var21) : null;
        String var39 = var21.valueOf(var2);
        Integer var40 = var21.valueOf(var3);
        Session var41 = new Session((String)var11.value(var21), var37, (String)var13.value(var21), (String)var19.value(var21));
        GameConfiguration var42 = new GameConfiguration(new GameConfiguration.UserInformation(var41, var33, var24), new GameConfiguration.DisplayInformation(var27, var28, var29, var30), new GameConfiguration.FolderInformation(var34, var36, var35, var38), new GameConfiguration.GameInformation(var31, var32), new GameConfiguration.ServerInformation(var39, var40));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread"){
            private static final String __OBFID = "CL_00000829";

            @Override
            public void run() {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        new Minecraft(var42).run();
    }

    private static boolean func_110121_a(String p_110121_0_) {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }

}

