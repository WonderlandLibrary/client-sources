/*   1:    */ package net.minecraft.client.main;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.Authenticator;
/*   6:    */ import java.net.InetSocketAddress;
/*   7:    */ import java.net.PasswordAuthentication;
/*   8:    */ import java.net.Proxy;
/*   9:    */ import java.net.Proxy.Type;
/*  10:    */ import java.util.List;
/*  11:    */ import joptsimple.ArgumentAcceptingOptionSpec;
/*  12:    */ import joptsimple.NonOptionArgumentSpec;
/*  13:    */ import joptsimple.OptionParser;
/*  14:    */ import joptsimple.OptionSet;
/*  15:    */ import joptsimple.OptionSpecBuilder;
/*  16:    */ import net.minecraft.client.Minecraft;
/*  17:    */ import net.minecraft.util.Session;
/*  18:    */ 
/*  19:    */ public class Main
/*  20:    */ {
/*  21:    */   private static final String __OBFID = "CL_00001461";
/*  22:    */   
/*  23:    */   public static void main(String[] par0ArrayOfStr)
/*  24:    */   {
/*  25: 23 */     System.setProperty("java.net.preferIPv4Stack", "true");
/*  26: 24 */     OptionParser var1 = new OptionParser();
/*  27: 25 */     var1.allowsUnrecognizedOptions();
/*  28: 26 */     var1.accepts("demo");
/*  29: 27 */     var1.accepts("fullscreen");
/*  30: 28 */     ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
/*  31: 29 */     ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), new Integer[0]);
/*  32: 30 */     ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
/*  33: 31 */     ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
/*  34: 32 */     ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
/*  35: 33 */     ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
/*  36: 34 */     ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
/*  37: 35 */     ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
/*  38: 36 */     ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
/*  39: 37 */     ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
/*  40: 38 */     ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
/*  41: 39 */     ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
/*  42: 40 */     ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
/*  43: 41 */     ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(854), new Integer[0]);
/*  44: 42 */     ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(480), new Integer[0]);
/*  45: 43 */     NonOptionArgumentSpec var17 = var1.nonOptions();
/*  46: 44 */     OptionSet var18 = var1.parse(par0ArrayOfStr);
/*  47: 45 */     List var19 = var18.valuesOf(var17);
/*  48: 46 */     String var20 = (String)var18.valueOf(var7);
/*  49: 47 */     Proxy var21 = Proxy.NO_PROXY;
/*  50: 49 */     if (var20 != null) {
/*  51:    */       try
/*  52:    */       {
/*  53: 53 */         var21 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(var20, ((Integer)var18.valueOf(var8)).intValue()));
/*  54:    */       }
/*  55:    */       catch (Exception localException) {}
/*  56:    */     }
/*  57: 61 */     String var22 = (String)var18.valueOf(var9);
/*  58: 62 */     final String var23 = (String)var18.valueOf(var10);
/*  59: 64 */     if ((!var21.equals(Proxy.NO_PROXY)) && (func_110121_a(var22)) && (func_110121_a(var23))) {
/*  60: 66 */       Authenticator.setDefault(new Authenticator()
/*  61:    */       {
/*  62:    */         private static final String __OBFID = "CL_00000828";
/*  63:    */         
/*  64:    */         protected PasswordAuthentication getPasswordAuthentication()
/*  65:    */         {
/*  66: 71 */           return new PasswordAuthentication(Main.this, var23.toCharArray());
/*  67:    */         }
/*  68:    */       });
/*  69:    */     }
/*  70: 76 */     int var24 = ((Integer)var18.valueOf(var15)).intValue();
/*  71: 77 */     int var25 = ((Integer)var18.valueOf(var16)).intValue();
/*  72: 78 */     boolean var26 = var18.has("fullscreen");
/*  73: 79 */     boolean var27 = var18.has("demo");
/*  74: 80 */     String var28 = (String)var18.valueOf(var14);
/*  75: 81 */     File var29 = (File)var18.valueOf(var4);
/*  76: 82 */     File var30 = var18.has(var5) ? (File)var18.valueOf(var5) : new File(var29, "assets/");
/*  77: 83 */     File var31 = var18.has(var6) ? (File)var18.valueOf(var6) : new File(var29, "resourcepacks/");
/*  78: 84 */     String var32 = var18.has(var12) ? (String)var12.value(var18) : (String)var11.value(var18);
/*  79: 85 */     Session var33 = new Session((String)var11.value(var18), var32, (String)var13.value(var18));
/*  80: 86 */     Minecraft var34 = new Minecraft(var33, var24, var25, var26, var27, var29, var30, var31, var21, var28);
/*  81: 87 */     String var35 = (String)var18.valueOf(var2);
/*  82: 89 */     if (var35 != null) {
/*  83: 91 */       var34.setServer(var35, ((Integer)var18.valueOf(var3)).intValue());
/*  84:    */     }
/*  85: 94 */     Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
/*  86:    */     {
/*  87:    */       private static final String __OBFID = "CL_00000829";
/*  88:    */       
/*  89:    */       public void run() {}
/*  90:    */     });
/*  91:103 */     if (!var19.isEmpty()) {
/*  92:105 */       System.out.println("Completely ignored arguments: " + var19);
/*  93:    */     }
/*  94:108 */     Thread.currentThread().setName("Client thread");
/*  95:109 */     var34.run();
/*  96:    */   }
/*  97:    */   
/*  98:    */   private static boolean func_110121_a(String par0Str)
/*  99:    */   {
/* 100:114 */     return (par0Str != null) && (!par0Str.isEmpty());
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.main.Main
 * JD-Core Version:    0.7.0.1
 */