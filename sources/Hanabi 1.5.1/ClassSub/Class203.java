package ClassSub;

import java.util.*;
import net.minecraftforge.fml.common.*;

public class Class203
{
    public static String username;
    public static String password;
    public static Class11 heartBeatTimer;
    public static final String SERVER_IP = "dx.pfcraft.cn";
    public static final int SERVER_PORT = 7577;
    public static Class124 socket;
    public static Class59 type;
    public static Class326 loginPacket;
    public static HashMap<String, String> ignMap;
    
    
    public void start(final String username, final String password) {
        Class203.username = username;
        Class203.password = password;
        (Class203.socket = new Class124()).start();
        Class203.loginPacket = new Class326(Class203.type, username, password);
    }
    
    public static void output(final String s) {
        if (s != null) {
            Class200.tellPlayer(s);
        }
    }
    
    public static void processMessage(final String replace) {
        final class Class13 extends Thread
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
        //     4: aload_0        
        //     5: ldc             "\ufffd"
        //     7: ldc             ""
        //     9: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //    12: astore_0       
        //    13: aload_0        
        //    14: ifnull          553
        //    17: aload_0        
        //    18: ldc             "::"
        //    20: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    23: checkcast       [Ljava/lang/String;
        //    26: astore_1       
        //    27: aload_1        
        //    28: ldc             0
        //    30: aaload         
        //    31: ldc             "CHAT"
        //    33: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    36: ifeq            155
        //    39: new             LClassSub/Class199;
        //    42: dup            
        //    43: aload_0        
        //    44: invokespecial   ClassSub/Class199.<init>:(Ljava/lang/String;)V
        //    47: astore_2       
        //    48: new             Ljava/lang/StringBuilder;
        //    51: dup            
        //    52: invokespecial   java/lang/StringBuilder.<init>:()V
        //    55: ldc             "§a["
        //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    60: aload_2        
        //    61: getfield        ClassSub/Class199.client:LClassSub/Class59;
        //    64: invokevirtual   ClassSub/Class59.getClientName:()Ljava/lang/String;
        //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    70: ldc             "]"
        //    72: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    75: aload_2        
        //    76: getfield        ClassSub/Class199.user:LClassSub/Class194;
        //    79: invokevirtual   ClassSub/Class194.getPrefix:()Ljava/lang/String;
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: ldc             "&r"
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: aload_2        
        //    91: getfield        ClassSub/Class199.user:LClassSub/Class194;
        //    94: invokevirtual   ClassSub/Class194.getUsername:()Ljava/lang/String;
        //    97: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   100: ldc             ":"
        //   102: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   105: aload_2        
        //   106: getfield        ClassSub/Class199.chatMessage:Ljava/lang/String;
        //   109: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   112: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   115: astore_3       
        //   116: new             Ljava/lang/StringBuilder;
        //   119: dup            
        //   120: invokespecial   java/lang/StringBuilder.<init>:()V
        //   123: ldc             "§b[IRC]"
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: aload_3        
        //   129: ldc             "&"
        //   131: ldc             "§"
        //   133: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //   136: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   139: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   142: invokestatic    ClassSub/Class203.output:(Ljava/lang/String;)V
        //   145: goto            553
        //   148: nop            
        //   149: athrow         
        //   150: nop            
        //   151: nop            
        //   152: nop            
        //   153: nop            
        //   154: athrow         
        //   155: aload_1        
        //   156: ldc             0
        //   158: aaload         
        //   159: ldc             "SERVERCHAT"
        //   161: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   164: ifeq            213
        //   167: new             LClassSub/Class238;
        //   170: dup            
        //   171: aload_0        
        //   172: invokespecial   ClassSub/Class238.<init>:(Ljava/lang/String;)V
        //   175: astore_2       
        //   176: aload_2        
        //   177: getfield        ClassSub/Class238.message:Ljava/lang/String;
        //   180: astore_3       
        //   181: new             Ljava/lang/StringBuilder;
        //   184: dup            
        //   185: invokespecial   java/lang/StringBuilder.<init>:()V
        //   188: ldc             "§b[IRC]§d[Server]§r"
        //   190: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   193: aload_3        
        //   194: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   197: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   200: invokestatic    ClassSub/Class203.output:(Ljava/lang/String;)V
        //   203: goto            553
        //   206: nop            
        //   207: athrow         
        //   208: nop            
        //   209: nop            
        //   210: nop            
        //   211: nop            
        //   212: athrow         
        //   213: aload_1        
        //   214: ldc             0
        //   216: aaload         
        //   217: ldc             "CRASH"
        //   219: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   222: ifeq            250
        //   225: ldc             "§c\ufffd\ufffd\ufffd\u68ec\ufffd\u3c7b\ufffd\ufffd\ufffd\ufffd\u0531\u01ff\ufffd\ufffd\ufffd\u02f3\ufffd\ufffd\ufffd\u03f7\ufffd\ufffd\ufffd\ufffd\u03f7\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0536\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd"
        //   227: invokestatic    ClassSub/Class200.tellPlayer:(Ljava/lang/String;)V
        //   230: new             LClassSub/Class13;
        //   233: dup            
        //   234: invokespecial   ClassSub/Class13.<init>:()V
        //   237: invokevirtual   ClassSub/Class13.start:()V
        //   240: goto            553
        //   243: nop            
        //   244: athrow         
        //   245: nop            
        //   246: nop            
        //   247: nop            
        //   248: nop            
        //   249: athrow         
        //   250: aload_1        
        //   251: ldc             0
        //   253: aaload         
        //   254: ldc             "ONLINEUSER"
        //   256: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   259: ifeq            368
        //   262: new             LClassSub/Class104;
        //   265: dup            
        //   266: aload_0        
        //   267: invokespecial   ClassSub/Class104.<init>:(Ljava/lang/String;)V
        //   270: astore_2       
        //   271: new             Ljava/lang/StringBuilder;
        //   274: dup            
        //   275: invokespecial   java/lang/StringBuilder.<init>:()V
        //   278: aload_2        
        //   279: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   282: invokevirtual   ClassSub/Class194.getUsername:()Ljava/lang/String;
        //   285: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   288: ldc             " "
        //   290: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   293: aload_2        
        //   294: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   297: invokevirtual   ClassSub/Class194.getInGamename:()Ljava/lang/String;
        //   300: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   303: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   306: astore_3       
        //   307: getstatic       ClassSub/Class203.ignMap:Ljava/util/HashMap;
        //   310: aload_2        
        //   311: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   314: invokevirtual   ClassSub/Class194.getInGamename:()Ljava/lang/String;
        //   317: invokevirtual   java/util/HashMap.containsKey:(Ljava/lang/Object;)Z
        //   320: ifeq            337
        //   323: getstatic       ClassSub/Class203.ignMap:Ljava/util/HashMap;
        //   326: aload_2        
        //   327: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   330: invokevirtual   ClassSub/Class194.getInGamename:()Ljava/lang/String;
        //   333: invokevirtual   java/util/HashMap.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   336: pop            
        //   337: getstatic       ClassSub/Class203.ignMap:Ljava/util/HashMap;
        //   340: aload_2        
        //   341: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   344: invokevirtual   ClassSub/Class194.getInGamename:()Ljava/lang/String;
        //   347: aload_2        
        //   348: getfield        ClassSub/Class104.user:LClassSub/Class194;
        //   351: invokevirtual   ClassSub/Class194.getUsername:()Ljava/lang/String;
        //   354: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   357: pop            
        //   358: goto            553
        //   361: nop            
        //   362: athrow         
        //   363: nop            
        //   364: nop            
        //   365: nop            
        //   366: nop            
        //   367: athrow         
        //   368: aload_1        
        //   369: ldc             0
        //   371: aaload         
        //   372: ldc             "SUDO"
        //   374: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   377: ifeq            414
        //   380: new             LClassSub/Class75;
        //   383: dup            
        //   384: aload_0        
        //   385: invokespecial   ClassSub/Class75.<init>:(Ljava/lang/String;)V
        //   388: astore_2       
        //   389: aload_2        
        //   390: getfield        ClassSub/Class75.command:Ljava/lang/String;
        //   393: astore_3       
        //   394: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   397: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   400: aload_3        
        //   401: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendChatMessage:(Ljava/lang/String;)V
        //   404: goto            553
        //   407: nop            
        //   408: athrow         
        //   409: nop            
        //   410: nop            
        //   411: nop            
        //   412: nop            
        //   413: athrow         
        //   414: aload_1        
        //   415: ldc             0
        //   417: aaload         
        //   418: ldc             "CLEARUSER"
        //   420: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   423: ifeq            456
        //   426: new             LClassSub/Class175;
        //   429: dup            
        //   430: aload_0        
        //   431: invokespecial   ClassSub/Class175.<init>:(Ljava/lang/String;)V
        //   434: astore_2       
        //   435: getstatic       ClassSub/Class194.userList:Ljava/util/ArrayList;
        //   438: aload_2        
        //   439: getfield        ClassSub/Class175.user:LClassSub/Class194;
        //   442: invokevirtual   java/util/ArrayList.remove:(Ljava/lang/Object;)Z
        //   445: pop            
        //   446: goto            553
        //   449: nop            
        //   450: athrow         
        //   451: nop            
        //   452: nop            
        //   453: nop            
        //   454: nop            
        //   455: athrow         
        //   456: aload_1        
        //   457: ldc             0
        //   459: aaload         
        //   460: ldc             "HB"
        //   462: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   465: ifeq            553
        //   468: new             LClassSub/Class347;
        //   471: dup            
        //   472: aload_0        
        //   473: invokespecial   ClassSub/Class347.<init>:(Ljava/lang/String;)V
        //   476: astore_2       
        //   477: getstatic       ClassSub/Class203.heartBeatTimer:LClassSub/Class11;
        //   480: invokevirtual   ClassSub/Class11.reset:()V
        //   483: aload_2        
        //   484: getfield        ClassSub/Class347.needIGN:Z
        //   487: ifeq            553
        //   490: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   493: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   496: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getName:()Ljava/lang/String;
        //   499: ifnull          553
        //   502: new             LClassSub/Class121;
        //   505: dup            
        //   506: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   509: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   512: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getName:()Ljava/lang/String;
        //   515: getstatic       cn/Hanabi/modules/Player/Teams.clientfriend:Lcn/Hanabi/value/Value;
        //   518: invokevirtual   cn/Hanabi/value/Value.getValueState:()Ljava/lang/Object;
        //   521: checkcast       Ljava/lang/Boolean;
        //   524: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   527: ifeq            538
        //   530: ldc_w           "true"
        //   533: goto            541
        //   536: nop            
        //   537: athrow         
        //   538: ldc_w           "false"
        //   541: invokespecial   ClassSub/Class121.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   544: getstatic       ClassSub/Class203.socket:LClassSub/Class124;
        //   547: getfield        ClassSub/Class124.writer:Ljava/io/PrintWriter;
        //   550: invokevirtual   ClassSub/Class121.sendPacketToServer:(Ljava/io/PrintWriter;)V
        //   553: goto            563
        //   556: nop            
        //   557: athrow         
        //   558: astore_1       
        //   559: aload_1        
        //   560: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   563: return         
        //   564: nop            
        //   565: nop            
        //   566: nop            
        //   567: nop            
        //   568: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  4      148    558    563    Ljava/lang/Exception;
        //  155    206    558    563    Ljava/lang/Exception;
        //  213    243    558    563    Ljava/lang/Exception;
        //  250    361    558    563    Ljava/lang/Exception;
        //  368    407    558    563    Ljava/lang/Exception;
        //  414    449    558    563    Ljava/lang/Exception;
        //  456    536    558    563    Ljava/lang/Exception;
        //  538    553    558    563    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        Class203.heartBeatTimer = new Class11();
        Class203.type = Class294.getTypeByName("Hanabi");
        Class203.ignMap = new HashMap<String, String>();
    }
}
