package cn.Hanabi.modules.World;

import java.net.*;
import cn.Hanabi.events.*;
import net.minecraft.client.gui.*;
import com.darkmagician6.eventapi.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.client.*;
import ClassSub.*;
import net.minecraft.util.*;
import cn.Hanabi.modules.*;
import java.util.*;
import java.io.*;

public class IRC_ extends Mod
{
    private String lastmessage;
    private boolean first;
    private static String[] IRC;
    static PrintWriter pw;
    BufferedReader br;
    Socket socket;
    public static List<String> FriendList;
    public static List<String> ModList;
    public static Map<String, String> UserMap;
    private boolean messageThread;
    private Class205 timer;
    private static Class205 irctimer;
    public static String DevName;
    
    
    public IRC_() {
        super("IRC", Category.WORLD);
        this.first = false;
        this.timer = new Class205();
        new Class288().start();
    }
    
    @EventTarget
    public void onKey(final EventKey eventKey) {
        if (eventKey.getKey() == 52) {
            IRC_.mc.displayGuiScreen((GuiScreen)new GuiChat());
        }
    }
    
    public static void processMessage(final String replace) {
        final class Class68 extends Thread
        {
            
            
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                    FMLCommonHandler.instance().exitJava(0, true);
                }
                catch (InterruptedException ex) {}
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //     7: astore_1       
        //     8: aload_0        
        //     9: ldc             "\ufffd"
        //    11: ldc             ""
        //    13: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //    16: astore_0       
        //    17: aload_0        
        //    18: ifnull          35
        //    21: aload_1        
        //    22: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    25: ifnull          35
        //    28: aload_1        
        //    29: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    32: ifnonnull       41
        //    35: return         
        //    36: nop            
        //    37: nop            
        //    38: nop            
        //    39: nop            
        //    40: athrow         
        //    41: aload_0        
        //    42: ldc             "\\|"
        //    44: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    47: checkcast       [Ljava/lang/String;
        //    50: astore_2       
        //    51: aload_2        
        //    52: ldc             3
        //    54: aaload         
        //    55: ldc             "USER"
        //    57: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    60: ifeq            124
        //    63: getstatic       ClassSub/Class334.isDebugMode:Z
        //    66: ifne            75
        //    69: getstatic       ClassSub/Class334.isMod:Z
        //    72: ifeq            118
        //    75: new             Ljava/lang/StringBuilder;
        //    78: dup            
        //    79: invokespecial   java/lang/StringBuilder.<init>:()V
        //    82: ldc             "§d[Margele-IRC]§a\u6e38\u620f\u540d\uff1a"
        //    84: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    87: aload_2        
        //    88: aload_2        
        //    89: arraylength    
        //    90: ldc             2
        //    92: isub           
        //    93: aaload         
        //    94: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    97: ldc             " \u6ce8\u518c\u540d\uff1a"
        //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   102: aload_2        
        //   103: aload_2        
        //   104: arraylength    
        //   105: ldc             1
        //   107: isub           
        //   108: aaload         
        //   109: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   112: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   115: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   118: return         
        //   119: nop            
        //   120: nop            
        //   121: nop            
        //   122: nop            
        //   123: athrow         
        //   124: aload_0        
        //   125: ldc             "COMMAND|GETUSER"
        //   127: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   130: ifeq            187
        //   133: aload_1        
        //   134: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   137: ifnull          187
        //   140: new             Ljava/lang/StringBuilder;
        //   143: dup            
        //   144: invokespecial   java/lang/StringBuilder.<init>:()V
        //   147: ldc             "USER|"
        //   149: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   152: aload_1        
        //   153: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   156: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getName:()Ljava/lang/String;
        //   159: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   162: ldc             "|"
        //   164: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: getstatic       cn/Hanabi/modules/World/IRC_.fuck:Ljava/lang/String;
        //   170: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   173: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   176: ldc             0
        //   178: invokestatic    cn/Hanabi/modules/World/IRC_.sendIRCMessage:(Ljava/lang/String;Z)V
        //   181: return         
        //   182: nop            
        //   183: nop            
        //   184: nop            
        //   185: nop            
        //   186: athrow         
        //   187: aload_1        
        //   188: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   191: ifnull          257
        //   194: aload_0        
        //   195: ldc             "COMMAND|FORCECOMMAND"
        //   197: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   200: ifeq            257
        //   203: aload_2        
        //   204: ldc             2
        //   206: aaload         
        //   207: astore_3       
        //   208: aload_3        
        //   209: aload_1        
        //   210: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   213: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getName:()Ljava/lang/String;
        //   216: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   219: ifne            232
        //   222: aload_3        
        //   223: getstatic       cn/Hanabi/modules/World/IRC_.fuck:Ljava/lang/String;
        //   226: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   229: ifeq            251
        //   232: ldc             "§c\u4f60\u88ab\u7ba1\u7406\u5458\u5f3a\u5236\u6267\u884c\u547d\u4ee4\uff01"
        //   234: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   237: aload_1        
        //   238: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   241: aload_2        
        //   242: aload_2        
        //   243: arraylength    
        //   244: ldc             1
        //   246: isub           
        //   247: aaload         
        //   248: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendChatMessage:(Ljava/lang/String;)V
        //   251: return         
        //   252: nop            
        //   253: nop            
        //   254: nop            
        //   255: nop            
        //   256: athrow         
        //   257: aload_1        
        //   258: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   261: ifnull          323
        //   264: aload_0        
        //   265: ldc             "|CrashCrashCrashCrashCrash"
        //   267: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   270: ifeq            323
        //   273: aload_2        
        //   274: ldc             3
        //   276: aaload         
        //   277: astore_3       
        //   278: aload_3        
        //   279: aload_1        
        //   280: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   283: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getName:()Ljava/lang/String;
        //   286: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   289: ifne            302
        //   292: aload_3        
        //   293: getstatic       cn/Hanabi/modules/World/IRC_.fuck:Ljava/lang/String;
        //   296: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   299: ifeq            317
        //   302: ldc             "§c\u8b66\u544a\uff0c\u4f60\u88ab\u7ba1\u7406\u5458\u5f3a\u5236\u9000\u51fa\u6e38\u620f\uff01\u6e38\u620f\u5c06\u5728\u4e09\u79d2\u540e\u81ea\u52a8\u7ed3\u675f\uff01"
        //   304: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   307: new             LClassSub/Class68;
        //   310: dup            
        //   311: invokespecial   ClassSub/Class68.<init>:()V
        //   314: invokevirtual   ClassSub/Class68.start:()V
        //   317: return         
        //   318: nop            
        //   319: nop            
        //   320: nop            
        //   321: nop            
        //   322: athrow         
        //   323: aload_0        
        //   324: ldc             "CLIENTFRIEND"
        //   326: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   329: ifne            359
        //   332: aload_0        
        //   333: ldc             "|DEV"
        //   335: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   338: ifne            359
        //   341: aload_0        
        //   342: ldc             "|MOD"
        //   344: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   347: ifne            359
        //   350: aload_0        
        //   351: ldc             "|WDR"
        //   353: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   356: ifeq            673
        //   359: aload_2        
        //   360: aload_2        
        //   361: arraylength    
        //   362: ldc             2
        //   364: isub           
        //   365: aaload         
        //   366: astore_3       
        //   367: aload_0        
        //   368: ldc             "|WDR"
        //   370: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   373: ifne            442
        //   376: getstatic       cn/Hanabi/modules/World/IRC_.UserMap:Ljava/util/Map;
        //   379: aload_3        
        //   380: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   385: ifne            442
        //   388: getstatic       cn/Hanabi/modules/World/IRC_.UserMap:Ljava/util/Map;
        //   391: aload_3        
        //   392: aload_2        
        //   393: ldc             2
        //   395: aaload         
        //   396: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   401: pop            
        //   402: getstatic       ClassSub/Class334.isMod:Z
        //   405: ifeq            442
        //   408: new             Ljava/lang/StringBuilder;
        //   411: dup            
        //   412: invokespecial   java/lang/StringBuilder.<init>:()V
        //   415: ldc             "§b[Hanabi]§a\u6dfb\u52a0\u7528\u6237! IGN:"
        //   417: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   420: aload_3        
        //   421: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   424: ldc             " \u7528\u6237\u540d:"
        //   426: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   429: aload_2        
        //   430: ldc             2
        //   432: aaload         
        //   433: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   436: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   439: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   442: aload_0        
        //   443: ldc             "|CLIENTFRIEND"
        //   445: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   448: ifne            460
        //   451: aload_0        
        //   452: ldc             "|MOD"
        //   454: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   457: ifeq            482
        //   460: getstatic       cn/Hanabi/modules/World/IRC_.FriendList:Ljava/util/List;
        //   463: aload_3        
        //   464: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   469: ifne            482
        //   472: getstatic       cn/Hanabi/modules/World/IRC_.FriendList:Ljava/util/List;
        //   475: aload_3        
        //   476: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   481: pop            
        //   482: aload_0        
        //   483: ldc             "|NOCLIENTFRIEND"
        //   485: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   488: ifeq            513
        //   491: getstatic       cn/Hanabi/modules/World/IRC_.FriendList:Ljava/util/List;
        //   494: aload_3        
        //   495: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   500: ifeq            513
        //   503: getstatic       cn/Hanabi/modules/World/IRC_.FriendList:Ljava/util/List;
        //   506: aload_3        
        //   507: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   512: pop            
        //   513: aload_0        
        //   514: ldc             "|MOD"
        //   516: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   519: ifeq            544
        //   522: getstatic       cn/Hanabi/modules/World/IRC_.ModList:Ljava/util/List;
        //   525: aload_3        
        //   526: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   531: ifne            544
        //   534: getstatic       cn/Hanabi/modules/World/IRC_.ModList:Ljava/util/List;
        //   537: aload_3        
        //   538: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   543: pop            
        //   544: aload_0        
        //   545: ldc             "|DEV"
        //   547: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   550: ifeq            557
        //   553: aload_3        
        //   554: putstatic       cn/Hanabi/modules/World/IRC_.DevName:Ljava/lang/String;
        //   557: aload_0        
        //   558: ldc             "|WDR"
        //   560: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   563: ifeq            667
        //   566: aload_1        
        //   567: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   570: ifnull          667
        //   573: getstatic       cn/Hanabi/modules/World/AutoL.wdred:Ljava/util/List;
        //   576: aload_3        
        //   577: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   582: ifne            667
        //   585: new             Ljava/lang/StringBuilder;
        //   588: dup            
        //   589: invokespecial   java/lang/StringBuilder.<init>:()V
        //   592: ldc             "§d[Margele-IRC]§a"
        //   594: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   597: aload_2        
        //   598: ldc             2
        //   600: aaload         
        //   601: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   604: ldc             " \u5bf9\u73a9\u5bb6 "
        //   606: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   609: aload_3        
        //   610: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   613: ldc             " \u8fdb\u884c\u96c6\u7fa4\u4e3e\u62a5\uff01"
        //   615: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   618: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   621: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   624: getstatic       cn/Hanabi/modules/World/AutoL.wdred:Ljava/util/List;
        //   627: aload_3        
        //   628: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   633: pop            
        //   634: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   637: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   640: new             Ljava/lang/StringBuilder;
        //   643: dup            
        //   644: invokespecial   java/lang/StringBuilder.<init>:()V
        //   647: ldc             "/wdr "
        //   649: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   652: aload_3        
        //   653: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   656: ldc             " ka fly reach nokb jesus ac"
        //   658: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   661: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   664: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendChatMessage:(Ljava/lang/String;)V
        //   667: return         
        //   668: nop            
        //   669: nop            
        //   670: nop            
        //   671: nop            
        //   672: athrow         
        //   673: aload_1        
        //   674: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   677: ifnull          789
        //   680: ldc             "IRC"
        //   682: invokestatic    cn/Hanabi/modules/ModManager.getModule:(Ljava/lang/String;)Lcn/Hanabi/modules/Mod;
        //   685: invokevirtual   cn/Hanabi/modules/Mod.isEnabled:()Z
        //   688: ifeq            783
        //   691: aload_2        
        //   692: arraylength    
        //   693: ldc_w           4
        //   696: if_icmpne       783
        //   699: aload_0        
        //   700: ldc_w           "CHAT|"
        //   703: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   706: ifeq            783
        //   709: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   712: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   715: new             Lnet/minecraft/util/ChatComponentText;
        //   718: dup            
        //   719: new             Ljava/lang/StringBuilder;
        //   722: dup            
        //   723: invokespecial   java/lang/StringBuilder.<init>:()V
        //   726: ldc_w           "§d[Margele-IRC]"
        //   729: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   732: aload_2        
        //   733: aload_2        
        //   734: arraylength    
        //   735: ldc             3
        //   737: isub           
        //   738: aaload         
        //   739: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   742: ldc_w           "§e"
        //   745: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   748: aload_2        
        //   749: aload_2        
        //   750: arraylength    
        //   751: ldc             2
        //   753: isub           
        //   754: aaload         
        //   755: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   758: ldc_w           "§f:"
        //   761: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   764: aload_2        
        //   765: aload_2        
        //   766: arraylength    
        //   767: ldc             1
        //   769: isub           
        //   770: aaload         
        //   771: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   774: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   777: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
        //   780: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.addChatComponentMessage:(Lnet/minecraft/util/IChatComponent;)V
        //   783: return         
        //   784: nop            
        //   785: nop            
        //   786: nop            
        //   787: nop            
        //   788: athrow         
        //   789: goto            795
        //   792: nop            
        //   793: athrow         
        //   794: astore_1       
        //   795: return         
        //   796: nop            
        //   797: nop            
        //   798: nop            
        //   799: nop            
        //   800: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      35     794    795    Ljava/lang/Throwable;
        //  41     118    794    795    Ljava/lang/Throwable;
        //  124    181    794    795    Ljava/lang/Throwable;
        //  187    251    794    795    Ljava/lang/Throwable;
        //  257    317    794    795    Ljava/lang/Throwable;
        //  323    667    794    795    Ljava/lang/Throwable;
        //  673    783    794    795    Ljava/lang/Throwable;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void sendIRCMessage(final String s, final boolean b) {
        if (!IRC_.irctimer.isDelayComplete(1500L) && !Class334.isDebugMode && !Class334.isMod && !s.contains("CLIENTFRIEND")) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText("§d[Margele-IRC]§c请勿刷屏�?"));
            return;
        }
        if (b) {
            if (!ModManager.getModule("IRC").isEnabled()) {
                return;
            }
            IRC_.pw.println(s);
            IRC_.irctimer.reset();
        }
        else {
            IRC_.pw.println(s);
        }
    }
    
    public void onEnable() {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText("§d[Margele-IRC]§cIRC已开启，使用-irc指令发�?�消息！"));
        super.onEnable();
    }
    
    static boolean access$002(final IRC_ irc_, final boolean messageThread) {
        return irc_.messageThread = messageThread;
    }
    
    static Class205 access$100(final IRC_ irc_) {
        return irc_.timer;
    }
    
    static boolean access$000(final IRC_ irc_) {
        return irc_.messageThread;
    }
    
    static {
        IRC_.FriendList = new ArrayList<String>();
        IRC_.ModList = new ArrayList<String>();
        IRC_.UserMap = new HashMap<String, String>();
        IRC_.irctimer = new Class205();
        IRC_.DevName = ".";
    }
    
    class Class288 extends Thread
    {
        final IRC_ this$0;
        
        
        Class288(final IRC_ this$0) {
            ((Class288)this).this$0 = this$0;
        }
        
        @Override
        public void run() {
            this.setName("Reconnect");
            new IRC_.Class10(((Class288)this).this$0).start();
        Label_0024_Outer:
            while (true) {
                while (true) {
                    try {
                        while (true) {
                            Thread.sleep(1000L);
                            ((Class288)this).this$0.socket.sendUrgentData(255);
                        }
                    }
                    catch (IOException ex3) {
                        if (IRC_.access$100(((Class288)this).this$0).isDelayComplete(2000L)) {
                            IRC_.access$100(((Class288)this).this$0).reset();
                            new IRC_.Class10(((Class288)this).this$0).start();
                        }
                        continue Label_0024_Outer;
                    }
                    catch (NullPointerException ex) {
                        ex.printStackTrace();
                        continue Label_0024_Outer;
                    }
                    catch (InterruptedException ex2) {
                        ex2.printStackTrace();
                        continue Label_0024_Outer;
                    }
                    continue;
                }
            }
        }
    }
}
