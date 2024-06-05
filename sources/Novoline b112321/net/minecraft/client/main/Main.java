package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import java.io.File;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.af_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.main.GameConfiguration$DisplayInformation;
import net.minecraft.client.main.GameConfiguration$FolderInformation;
import net.minecraft.client.main.GameConfiguration$GameInformation;
import net.minecraft.client.main.GameConfiguration$ServerInformation;
import net.minecraft.client.main.GameConfiguration$UserInformation;
import net.minecraft.client.main.Main$1;
import net.minecraft.client.main.Main$2;
import net.minecraft.util.Session;

public class Main {
   public static String a;

   public static void a(File var0) {
      File var10000 = var0;

      try {
         URL var1 = var10000.toURI().toURL();
         URLClassLoader var2 = (URLClassLoader)ClassLoader.getSystemClassLoader();
         Method var3 = af_.a(URLClassLoader.class, "addURL", new Class[]{URL.class});
         var3.setAccessible(true);
         var3.invoke(var2, new Object[]{var1});
         System.out.println("Loaded library " + var0.getName());
      } catch (Throwable var4) {
         throw new RuntimeException("Unexpected exception", var4);
      }
   }

   public static void main(String[] var0) {
      System.setProperty("java.net.preferIPv4Stack", "true");
      OptionParser var1 = new OptionParser();
      var1.allowsUnrecognizedOptions();
      var1.accepts("demo");
      var1.accepts("fullscreen");
      var1.accepts("checkGlErrors");
      ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
      ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), new Integer[0]);
      ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
      ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
      ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
      ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
      ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
      ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
      ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo("Player", new String[0]);
      ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
      ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
      ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
      ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(854), new Integer[0]);
      ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(480), new Integer[0]);
      ArgumentAcceptingOptionSpec var17 = var1.accepts("userProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      ArgumentAcceptingOptionSpec var18 = var1.accepts("profileProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      ArgumentAcceptingOptionSpec var19 = var1.accepts("assetIndex").withRequiredArg();
      ArgumentAcceptingOptionSpec var20 = var1.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
      NonOptionArgumentSpec var21 = var1.nonOptions();
      OptionSet var22 = var1.parse(var0);
      List var23 = var22.valuesOf(var21);
      ArgumentAcceptingOptionSpec var24 = var1.accepts("intentApiKey").withOptionalArg().ofType(String.class).defaultsTo("", new String[0]);
      if(var22.valueOf(var24) != null && !((String)var22.valueOf(var24)).isEmpty()) {
         a = (String)var22.valueOf(var24);
      }

      if(!var23.isEmpty()) {
         System.out.println("Completely ignored arguments: " + var23);
      }

      String var25 = (String)var22.valueOf(var7);
      Proxy var26 = Proxy.NO_PROXY;

      try {
         var26 = new Proxy(Type.SOCKS, new InetSocketAddress(var25, ((Integer)var22.valueOf(var8)).intValue()));
      } catch (Exception var47) {
         ;
      }

      String var27 = (String)var22.valueOf(var9);
      String var28 = (String)var22.valueOf(var10);
      if(!var26.equals(Proxy.NO_PROXY) && isNullOrEmpty(var27) && isNullOrEmpty(var28)) {
         Authenticator.setDefault(new Main$1(var27, var28));
      }

      int var29 = ((Integer)var22.valueOf(var15)).intValue();
      int var30 = ((Integer)var22.valueOf(var16)).intValue();
      boolean var31 = var22.has("fullscreen");
      boolean var32 = var22.has("checkGlErrors");
      boolean var33 = var22.has("demo");
      String var34 = (String)var22.valueOf(var14);
      Gson var35 = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap var36 = (PropertyMap)var35.fromJson((String)var22.valueOf(var17), PropertyMap.class);
      PropertyMap var37 = (PropertyMap)var35.fromJson((String)var22.valueOf(var18), PropertyMap.class);
      File var38 = (File)var22.valueOf(var4);
      File var39 = var22.has(var5)?(File)var22.valueOf(var5):new File(var38, "assets/");
      File var40 = var22.has(var6)?(File)var22.valueOf(var6):new File(var38, "resourcepacks/");
      String var41 = var22.has(var12)?(String)var12.value(var22):(String)var11.value(var22);
      String var42 = var22.has(var19)?(String)var19.value(var22):null;
      String var43 = (String)var22.valueOf(var2);
      Integer var44 = (Integer)var22.valueOf(var3);
      Session var45 = new Session((String)var11.value(var22), var41, (String)var13.value(var22), (String)var20.value(var22));
      GameConfiguration var46 = new GameConfiguration(new GameConfiguration$UserInformation(var45, var36, var37, var26), new GameConfiguration$DisplayInformation(var29, var30, var31, var32), new GameConfiguration$FolderInformation(var38, var40, var39, var42), new GameConfiguration$GameInformation(var33, var34), new GameConfiguration$ServerInformation(var43, var44.intValue()));
      Runtime.getRuntime().addShutdownHook(new Main$2("Client Shutdown Thread"));
      Thread.currentThread().setName("Client thread");
      (new Minecraft(var46)).run();
   }

   private static boolean isNullOrEmpty(String var0) {
      return !var0.isEmpty();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
