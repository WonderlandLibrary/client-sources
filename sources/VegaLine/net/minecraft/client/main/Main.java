/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Session;

public class Main {
    public static void main(String[] p_main_0_) {
        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        optionparser.accepts("demo");
        optionparser.accepts("fullscreen");
        optionparser.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec<String> optionspec = optionparser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> optionspec1 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<File> optionspec2 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (File[])new File[0]);
        ArgumentAcceptingOptionSpec<File> optionspec3 = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<File> optionspec4 = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (String[])new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> optionspec9 = optionparser.accepts("username").withRequiredArg().defaultsTo("Tutorial" + Minecraft.getSystemTime() % 100L, (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> optionspec10 = optionparser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> optionspec11 = optionparser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<String> optionspec12 = optionparser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec<Integer> optionspec13 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<Integer> optionspec14 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, (Integer[])new Integer[0]);
        ArgumentAcceptingOptionSpec<String> optionspec15 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> optionspec16 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> optionspec17 = optionparser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec<String> optionspec18 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> optionspec19 = optionparser.accepts("versionType").withRequiredArg().defaultsTo("release", (String[])new String[0]);
        NonOptionArgumentSpec<String> optionspec20 = optionparser.nonOptions();
        OptionSet optionset = optionparser.parse(p_main_0_);
        List<String> list = optionset.valuesOf(optionspec20);
        if (!list.isEmpty()) {
            System.out.println("Completely ignored arguments: " + list);
        }
        String s = optionset.valueOf(optionspec5);
        Proxy proxy = Proxy.NO_PROXY;
        if (s != null) {
            try {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, (int)optionset.valueOf(optionspec6)));
            } catch (Exception exception) {
                // empty catch block
            }
        }
        final String s1 = optionset.valueOf(optionspec7);
        final String s2 = optionset.valueOf(optionspec8);
        if (!proxy.equals(Proxy.NO_PROXY) && Main.isNullOrEmpty(s1) && Main.isNullOrEmpty(s2)) {
            Authenticator.setDefault(new Authenticator(){

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(s1, s2.toCharArray());
                }
            });
        }
        int i = optionset.valueOf(optionspec13);
        int j = optionset.valueOf(optionspec14);
        boolean flag = optionset.has("fullscreen");
        boolean flag1 = optionset.has("checkGlErrors");
        boolean flag2 = optionset.has("demo");
        String s3 = optionset.valueOf(optionspec12);
        Gson gson = new GsonBuilder().registerTypeAdapter((Type)((Object)PropertyMap.class), new PropertyMap.Serializer()).create();
        PropertyMap propertymap = JsonUtils.gsonDeserialize(gson, optionset.valueOf(optionspec15), PropertyMap.class);
        PropertyMap propertymap1 = JsonUtils.gsonDeserialize(gson, optionset.valueOf(optionspec16), PropertyMap.class);
        String s4 = optionset.valueOf(optionspec19);
        File file1 = optionset.valueOf(optionspec2);
        File file2 = optionset.has(optionspec3) ? optionset.valueOf(optionspec3) : new File(file1, "assets/");
        File file3 = optionset.has(optionspec4) ? optionset.valueOf(optionspec4) : new File(file1, "resourcepacks/");
        String s5 = optionset.has(optionspec10) ? (String)optionspec10.value(optionset) : (String)optionspec9.value(optionset);
        String s6 = optionset.has(optionspec17) ? (String)optionspec17.value(optionset) : null;
        String s7 = optionset.valueOf(optionspec);
        Integer integer = optionset.valueOf(optionspec1);
        Session session = new Session((String)optionspec9.value(optionset), s5, (String)optionspec11.value(optionset), (String)optionspec18.value(optionset));
        GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s6), new GameConfiguration.GameInformation(flag2, s3, s4), new GameConfiguration.ServerInformation(s7, integer));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread"){

            @Override
            public void run() {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        new Minecraft(gameconfiguration).run();
    }

    private static boolean isNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}

