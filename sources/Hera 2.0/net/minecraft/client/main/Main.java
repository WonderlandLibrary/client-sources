/*     */ package net.minecraft.client.main;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.io.File;
/*     */ import java.net.Authenticator;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.Proxy;
/*     */ import java.util.List;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.NonOptionArgumentSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.Session;
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   public static void main(String[] p_main_0_) {
/*  24 */     System.setProperty("java.net.preferIPv4Stack", "true");
/*  25 */     OptionParser optionparser = new OptionParser();
/*  26 */     optionparser.allowsUnrecognizedOptions();
/*  27 */     optionparser.accepts("demo");
/*  28 */     optionparser.accepts("fullscreen");
/*  29 */     optionparser.accepts("checkGlErrors");
/*  30 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = optionparser.accepts("server").withRequiredArg();
/*  31 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), (Object[])new Integer[0]);
/*  32 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec3 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (Object[])new File[0]);
/*  33 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec4 = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
/*  34 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec5 = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
/*  35 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec6 = optionparser.accepts("proxyHost").withRequiredArg();
/*  36 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec7 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (Object[])new String[0]).ofType(Integer.class);
/*  37 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec8 = optionparser.accepts("proxyUser").withRequiredArg();
/*  38 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec9 = optionparser.accepts("proxyPass").withRequiredArg();
/*  39 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec10 = optionparser.accepts("username").withRequiredArg().defaultsTo("Hera" + (Minecraft.getSystemTime() % 1000L), (Object[])new String[0]);
/*  40 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec11 = optionparser.accepts("uuid").withRequiredArg();
/*  41 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec12 = optionparser.accepts("accessToken").withRequiredArg().required();
/*  42 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec13 = optionparser.accepts("version").withRequiredArg().required();
/*  43 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec14 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(1280), (Object[])new Integer[0]);
/*  44 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec15 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(720), (Object[])new Integer[0]);
/*  45 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec16 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  46 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec17 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  47 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec18 = optionparser.accepts("assetIndex").withRequiredArg();
/*  48 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec19 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy", (Object[])new String[0]);
/*  49 */     NonOptionArgumentSpec nonOptionArgumentSpec = optionparser.nonOptions();
/*  50 */     OptionSet optionset = optionparser.parse(p_main_0_);
/*  51 */     List<String> list = optionset.valuesOf((OptionSpec)nonOptionArgumentSpec);
/*     */     
/*  53 */     if (!list.isEmpty())
/*     */     {
/*  55 */       System.out.println("Completely ignored arguments: " + list);
/*     */     }
/*     */     
/*  58 */     String s = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec6);
/*  59 */     Proxy proxy = Proxy.NO_PROXY;
/*     */     
/*  61 */     if (s != null) {
/*     */       
/*     */       try {
/*     */         
/*  65 */         proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec7)).intValue()));
/*     */       }
/*  67 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     final String s1 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec8);
/*  74 */     final String s2 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec9);
/*     */     
/*  76 */     if (!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(s1) && isNullOrEmpty(s2))
/*     */     {
/*  78 */       Authenticator.setDefault(new Authenticator()
/*     */           {
/*     */             protected PasswordAuthentication getPasswordAuthentication()
/*     */             {
/*  82 */               return new PasswordAuthentication(s1, s2.toCharArray());
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*  87 */     int i = ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec14)).intValue();
/*  88 */     int j = ((Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec15)).intValue();
/*  89 */     boolean flag = optionset.has("fullscreen");
/*  90 */     boolean flag1 = optionset.has("checkGlErrors");
/*  91 */     boolean flag2 = optionset.has("demo");
/*  92 */     String s3 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec13);
/*  93 */     Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();
/*  94 */     PropertyMap propertymap = (PropertyMap)gson.fromJson((String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec16), PropertyMap.class);
/*  95 */     PropertyMap propertymap1 = (PropertyMap)gson.fromJson((String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec17), PropertyMap.class);
/*  96 */     File file1 = (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec3);
/*  97 */     File file2 = optionset.has((OptionSpec)argumentAcceptingOptionSpec4) ? (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec4) : new File(file1, "assets/");
/*  98 */     File file3 = optionset.has((OptionSpec)argumentAcceptingOptionSpec5) ? (File)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec5) : new File(file1, "resourcepacks/");
/*  99 */     String s4 = optionset.has((OptionSpec)argumentAcceptingOptionSpec11) ? (String)argumentAcceptingOptionSpec11.value(optionset) : (String)argumentAcceptingOptionSpec10.value(optionset);
/* 100 */     String s5 = optionset.has((OptionSpec)argumentAcceptingOptionSpec18) ? (String)argumentAcceptingOptionSpec18.value(optionset) : null;
/* 101 */     String s6 = (String)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec1);
/* 102 */     Integer integer = (Integer)optionset.valueOf((OptionSpec)argumentAcceptingOptionSpec2);
/* 103 */     Session session = new Session((String)argumentAcceptingOptionSpec10.value(optionset), s4, (String)argumentAcceptingOptionSpec12.value(optionset), (String)argumentAcceptingOptionSpec19.value(optionset));
/* 104 */     GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s5), new GameConfiguration.GameInformation(flag2, s3), new GameConfiguration.ServerInformation(s6, integer.intValue()));
/* 105 */     Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
/*     */         {
/*     */           public void run()
/*     */           {
/* 109 */             Minecraft.stopIntegratedServer();
/*     */           }
/*     */         });
/* 112 */     Thread.currentThread().setName("Client thread");
/* 113 */     (new Minecraft(gameconfiguration)).run();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isNullOrEmpty(String str) {
/* 118 */     return (str != null && !str.isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\main\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */