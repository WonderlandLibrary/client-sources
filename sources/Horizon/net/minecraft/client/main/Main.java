package net.minecraft.client.main;

import java.util.List;
import joptsimple.OptionSet;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import HORIZON-6-0-SKIDPROTECTION.Session;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import java.net.PasswordAuthentication;
import java.net.Authenticator;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import joptsimple.OptionSpec;
import HORIZON-6-0-SKIDPROTECTION.Minecraft;
import java.io.File;
import joptsimple.OptionParser;

public class Main
{
    private static final String HorizonCode_Horizon_È = "CL_00001461";
    
    public static void main(final String[] p_main_0_) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        final OptionParser var1 = new OptionParser();
        var1.allowsUnrecognizedOptions();
        var1.accepts("demo");
        var1.accepts("fullscreen");
        var1.accepts("checkGlErrors");
        final ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
        final ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)25565, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType((Class)File.class).defaultsTo((Object)new File("."), (Object[])new File[0]);
        final ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
        final ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo((Object)"8080", (Object[])new String[0]).ofType((Class)Integer.class);
        final ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
        final ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
        final ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo((Object)("Player" + Minecraft.áƒ() % 1000L), (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
        final ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)854, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)480, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var17 = var1.accepts("userProperties").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var18 = var1.accepts("assetIndex").withRequiredArg();
        final ArgumentAcceptingOptionSpec var19 = var1.accepts("userType").withRequiredArg().defaultsTo((Object)"legacy", (Object[])new String[0]);
        final NonOptionArgumentSpec var20 = var1.nonOptions();
        final OptionSet var21 = var1.parse(p_main_0_);
        final List var22 = var21.valuesOf((OptionSpec)var20);
        if (!var22.isEmpty()) {
            System.out.println("Completely ignored arguments: " + var22);
        }
        final String var23 = (String)var21.valueOf((OptionSpec)var7);
        Proxy var24 = Proxy.NO_PROXY;
        if (var23 != null) {
            try {
                var24 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(var23, (int)var21.valueOf((OptionSpec)var8)));
            }
            catch (Exception ex) {}
        }
        final String var25 = (String)var21.valueOf((OptionSpec)var9);
        final String var26 = (String)var21.valueOf((OptionSpec)var10);
        if (!var24.equals(Proxy.NO_PROXY) && HorizonCode_Horizon_È(var25) && HorizonCode_Horizon_È(var26)) {
            Authenticator.setDefault(new Authenticator() {
                private static final String HorizonCode_Horizon_È = "CL_00000828";
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(var25, var26.toCharArray());
                }
            });
        }
        final int var27 = (int)var21.valueOf((OptionSpec)var15);
        final int var28 = (int)var21.valueOf((OptionSpec)var16);
        final boolean var29 = var21.has("fullscreen");
        final boolean var30 = var21.has("checkGlErrors");
        final boolean var31 = var21.has("demo");
        final String var32 = (String)var21.valueOf((OptionSpec)var14);
        final PropertyMap var33 = (PropertyMap)new GsonBuilder().registerTypeAdapter((Type)PropertyMap.class, (Object)new PropertyMap.Serializer()).create().fromJson((String)var21.valueOf((OptionSpec)var17), (Class)PropertyMap.class);
        final File var34 = (File)var21.valueOf((OptionSpec)var4);
        final File var35 = (File)(var21.has((OptionSpec)var5) ? var21.valueOf((OptionSpec)var5) : new File(var34, "assets/"));
        final File var36 = (File)(var21.has((OptionSpec)var6) ? var21.valueOf((OptionSpec)var6) : new File(var34, "resourcepacks/"));
        final String var37 = (String)(var21.has((OptionSpec)var12) ? var12.value(var21) : ((String)var11.value(var21)));
        final String var38 = var21.has((OptionSpec)var18) ? ((String)var18.value(var21)) : null;
        final String var39 = (String)var21.valueOf((OptionSpec)var2);
        final Integer var40 = (Integer)var21.valueOf((OptionSpec)var3);
        final Session var41 = new Session((String)var11.value(var21), var37, (String)var13.value(var21), (String)var19.value(var21));
        final GameConfiguration var42 = new GameConfiguration(new GameConfiguration.Âµá€(var41, var33, var24), new GameConfiguration.HorizonCode_Horizon_È(var27, var28, var29, var30), new GameConfiguration.Â(var34, var36, var35, var38), new GameConfiguration.Ý(var31, var32), new GameConfiguration.Ø­áŒŠá(var39, var40));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
            private static final String HorizonCode_Horizon_È = "CL_00000829";
            
            @Override
            public void run() {
                Minecraft.ÇŽÕ();
            }
        });
        Thread.currentThread().setName("Client thread");
        new Minecraft(var42).HorizonCode_Horizon_È();
    }
    
    private static boolean HorizonCode_Horizon_È(final String p_110121_0_) {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }
}
