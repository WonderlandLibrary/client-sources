package ClassSub;

import net.minecraft.client.*;
import com.google.common.base.*;
import net.minecraft.scoreboard.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Class287
{
    public float x;
    public float y;
    public int x2;
    public int y2;
    public float xSize;
    public boolean isDragging;
    public boolean isExtended;
    public boolean showNumber;
    private Class140 handler;
    public Minecraft mc;
    
    
    public Class287(final float x, final float y) {
        this.xSize = 65.0f;
        this.isDragging = false;
        this.isExtended = true;
        this.showNumber = true;
        this.handler = new Class140(0);
        this.mc = Minecraft.getMinecraft();
        this.x = x;
        this.y = y;
        this.isExtended = true;
    }
    
    public void passValue() {
        final Scoreboard getScoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective getObjectiveInDisplaySlot = null;
        final ScorePlayerTeam getPlayersTeam = getScoreboard.getPlayersTeam(this.mc.thePlayer.getName());
        if (getPlayersTeam != null) {
            final int getColorIndex = getPlayersTeam.getChatFormat().getColorIndex();
            if (getColorIndex >= 0) {
                getObjectiveInDisplaySlot = getScoreboard.getObjectiveInDisplaySlot(3 + getColorIndex);
            }
        }
        final ScoreObjective scoreObjective = (getObjectiveInDisplaySlot != null) ? getObjectiveInDisplaySlot : getScoreboard.getObjectiveInDisplaySlot(1);
        if (scoreObjective != null) {
            this.draw(scoreObjective);
        }
    }
    
    public void draw(final ScoreObjective scoreObjective) {
        class Class312 implements Predicate<Score>
        {
            final Class287 this$0;
            
            
            Class312(final Class287 this$0) {
                this.this$0 = this$0;
            }
            
            public boolean apply(final Score score) {
                return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
            }
            
            public boolean apply(final Object o) {
                return this.apply((Score)o);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: getfield        ClassSub/Class287.isExtended:Z
        //     8: ifeq            697
        //    11: aload_1        
        //    12: invokevirtual   net/minecraft/scoreboard/ScoreObjective.getScoreboard:()Lnet/minecraft/scoreboard/Scoreboard;
        //    15: astore_2       
        //    16: aload_2        
        //    17: aload_1        
        //    18: invokevirtual   net/minecraft/scoreboard/Scoreboard.getSortedScores:(Lnet/minecraft/scoreboard/ScoreObjective;)Ljava/util/Collection;
        //    21: astore_3       
        //    22: aload_3        
        //    23: new             LClassSub/Class312;
        //    26: dup            
        //    27: aload_0        
        //    28: invokespecial   ClassSub/Class312.<init>:(LClassSub/Class287;)V
        //    31: invokestatic    com/google/common/collect/Iterables.filter:(Ljava/lang/Iterable;Lcom/google/common/base/Predicate;)Ljava/lang/Iterable;
        //    34: invokestatic    com/google/common/collect/Lists.newArrayList:(Ljava/lang/Iterable;)Ljava/util/ArrayList;
        //    37: astore          4
        //    39: aload           4
        //    41: invokestatic    java/util/Collections.reverse:(Ljava/util/List;)V
        //    44: aload           4
        //    46: invokeinterface java/util/List.size:()I
        //    51: ldc             15
        //    53: if_icmple       84
        //    56: aload           4
        //    58: aload_3        
        //    59: invokeinterface java/util/Collection.size:()I
        //    64: ldc             15
        //    66: isub           
        //    67: invokestatic    com/google/common/collect/Iterables.skip:(Ljava/lang/Iterable;I)Ljava/lang/Iterable;
        //    70: invokestatic    com/google/common/collect/Lists.newArrayList:(Ljava/lang/Iterable;)Ljava/util/ArrayList;
        //    73: astore_3       
        //    74: goto            87
        //    77: nop            
        //    78: nop            
        //    79: nop            
        //    80: nop            
        //    81: nop            
        //    82: nop            
        //    83: athrow         
        //    84: aload           4
        //    86: astore_3       
        //    87: aload_0        
        //    88: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //    91: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //    94: aload_1        
        //    95: invokevirtual   net/minecraft/scoreboard/ScoreObjective.getDisplayName:()Ljava/lang/String;
        //    98: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   101: istore          5
        //   103: aload_3        
        //   104: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   109: astore          6
        //   111: aload           6
        //   113: invokeinterface java/util/Iterator.hasNext:()Z
        //   118: ifeq            217
        //   121: aload           6
        //   123: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   128: checkcast       Lnet/minecraft/scoreboard/Score;
        //   131: astore          7
        //   133: aload_2        
        //   134: aload           7
        //   136: invokevirtual   net/minecraft/scoreboard/Score.getPlayerName:()Ljava/lang/String;
        //   139: invokevirtual   net/minecraft/scoreboard/Scoreboard.getPlayersTeam:(Ljava/lang/String;)Lnet/minecraft/scoreboard/ScorePlayerTeam;
        //   142: astore          8
        //   144: new             Ljava/lang/StringBuilder;
        //   147: dup            
        //   148: invokespecial   java/lang/StringBuilder.<init>:()V
        //   151: aload           8
        //   153: aload           7
        //   155: invokevirtual   net/minecraft/scoreboard/Score.getPlayerName:()Ljava/lang/String;
        //   158: invokestatic    net/minecraft/scoreboard/ScorePlayerTeam.formatPlayerName:(Lnet/minecraft/scoreboard/Team;Ljava/lang/String;)Ljava/lang/String;
        //   161: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   164: ldc             ": "
        //   166: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   169: getstatic       net/minecraft/util/EnumChatFormatting.RED:Lnet/minecraft/util/EnumChatFormatting;
        //   172: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   175: aload           7
        //   177: invokevirtual   net/minecraft/scoreboard/Score.getScorePoints:()I
        //   180: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   183: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   186: astore          9
        //   188: iload           5
        //   190: aload_0        
        //   191: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   194: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   197: aload           9
        //   199: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   202: invokestatic    java/lang/Math.max:(II)I
        //   205: istore          5
        //   207: goto            111
        //   210: nop            
        //   211: nop            
        //   212: nop            
        //   213: nop            
        //   214: nop            
        //   215: nop            
        //   216: athrow         
        //   217: ldc             3
        //   219: istore          6
        //   221: aload_0        
        //   222: iload           5
        //   224: iload           6
        //   226: iadd           
        //   227: i2f            
        //   228: putfield        ClassSub/Class287.xSize:F
        //   231: ldc             0
        //   233: istore          7
        //   235: aload_3        
        //   236: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   241: astore          8
        //   243: aload           8
        //   245: invokeinterface java/util/Iterator.hasNext:()Z
        //   250: ifeq            278
        //   253: aload           8
        //   255: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   260: checkcast       Lnet/minecraft/scoreboard/Score;
        //   263: astore          9
        //   265: iinc            7, 1
        //   268: goto            243
        //   271: nop            
        //   272: nop            
        //   273: nop            
        //   274: nop            
        //   275: nop            
        //   276: nop            
        //   277: athrow         
        //   278: aload_0        
        //   279: getfield        ClassSub/Class287.x:F
        //   282: fconst_2       
        //   283: fsub           
        //   284: aload_0        
        //   285: getfield        ClassSub/Class287.y:F
        //   288: fconst_2       
        //   289: fsub           
        //   290: aload_0        
        //   291: getfield        ClassSub/Class287.x:F
        //   294: aload_0        
        //   295: getfield        ClassSub/Class287.xSize:F
        //   298: fadd           
        //   299: ldc             5.0
        //   301: fadd           
        //   302: aload_0        
        //   303: getfield        ClassSub/Class287.y:F
        //   306: aload_0        
        //   307: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   310: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   313: getfield        net/minecraft/client/gui/FontRenderer.FONT_HEIGHT:I
        //   316: i2f            
        //   317: fadd           
        //   318: getstatic       ClassSub/Class120.INSTANCE:LClassSub/Class120;
        //   321: pop            
        //   322: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //   325: getfield        ClassSub/Class15.c:I
        //   328: ldc             0.65
        //   330: invokestatic    ClassSub/Class120.reAlpha:(IF)I
        //   333: invokestatic    ClassSub/Class287.drawRect:(FFFFI)V
        //   336: aload_0        
        //   337: getfield        ClassSub/Class287.x:F
        //   340: fconst_2       
        //   341: fsub           
        //   342: aload_0        
        //   343: getfield        ClassSub/Class287.y:F
        //   346: aload_0        
        //   347: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   350: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   353: getfield        net/minecraft/client/gui/FontRenderer.FONT_HEIGHT:I
        //   356: i2f            
        //   357: fadd           
        //   358: aload_0        
        //   359: getfield        ClassSub/Class287.x:F
        //   362: aload_0        
        //   363: getfield        ClassSub/Class287.xSize:F
        //   366: fadd           
        //   367: ldc             5.0
        //   369: fadd           
        //   370: aload_0        
        //   371: getfield        ClassSub/Class287.y:F
        //   374: aload_0        
        //   375: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   378: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   381: getfield        net/minecraft/client/gui/FontRenderer.FONT_HEIGHT:I
        //   384: iload           7
        //   386: ldc             1
        //   388: iadd           
        //   389: imul           
        //   390: i2f            
        //   391: fadd           
        //   392: fconst_2       
        //   393: fadd           
        //   394: getstatic       ClassSub/Class120.INSTANCE:LClassSub/Class120;
        //   397: pop            
        //   398: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //   401: getfield        ClassSub/Class15.c:I
        //   404: ldc             0.45
        //   406: invokestatic    ClassSub/Class120.reAlpha:(IF)I
        //   409: invokestatic    ClassSub/Class287.drawRect:(FFFFI)V
        //   412: fconst_0       
        //   413: fstore          8
        //   415: aload_3        
        //   416: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   421: astore          9
        //   423: aload           9
        //   425: invokeinterface java/util/Iterator.hasNext:()Z
        //   430: ifeq            697
        //   433: aload           9
        //   435: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   440: checkcast       Lnet/minecraft/scoreboard/Score;
        //   443: astore          10
        //   445: aload_2        
        //   446: aload           10
        //   448: invokevirtual   net/minecraft/scoreboard/Score.getPlayerName:()Ljava/lang/String;
        //   451: invokevirtual   net/minecraft/scoreboard/Scoreboard.getPlayersTeam:(Ljava/lang/String;)Lnet/minecraft/scoreboard/ScorePlayerTeam;
        //   454: astore          11
        //   456: aload           11
        //   458: aload           10
        //   460: invokevirtual   net/minecraft/scoreboard/Score.getPlayerName:()Ljava/lang/String;
        //   463: invokestatic    net/minecraft/scoreboard/ScorePlayerTeam.formatPlayerName:(Lnet/minecraft/scoreboard/Team;Ljava/lang/String;)Ljava/lang/String;
        //   466: astore          12
        //   468: new             Ljava/lang/StringBuilder;
        //   471: dup            
        //   472: invokespecial   java/lang/StringBuilder.<init>:()V
        //   475: getstatic       net/minecraft/util/EnumChatFormatting.RED:Lnet/minecraft/util/EnumChatFormatting;
        //   478: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   481: ldc             ""
        //   483: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   486: aload           10
        //   488: invokevirtual   net/minecraft/scoreboard/Score.getScorePoints:()I
        //   491: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   494: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   497: astore          13
        //   499: aload_0        
        //   500: getfield        ClassSub/Class287.showNumber:Z
        //   503: ifeq            563
        //   506: aload_0        
        //   507: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   510: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   513: aload           13
        //   515: aload_0        
        //   516: getfield        ClassSub/Class287.x:F
        //   519: aload_0        
        //   520: getfield        ClassSub/Class287.xSize:F
        //   523: fadd           
        //   524: aload_0        
        //   525: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   528: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   531: aload           13
        //   533: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   536: i2f            
        //   537: fsub           
        //   538: ldc             4.0
        //   540: fadd           
        //   541: aload_0        
        //   542: getfield        ClassSub/Class287.y:F
        //   545: ldc             10.0
        //   547: fadd           
        //   548: fload           8
        //   550: fadd           
        //   551: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   554: getfield        ClassSub/Class15.c:I
        //   557: ldc             0
        //   559: invokevirtual   net/minecraft/client/gui/FontRenderer.drawString:(Ljava/lang/String;FFIZ)I
        //   562: pop            
        //   563: aload_0        
        //   564: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   567: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   570: aload           12
        //   572: aload_0        
        //   573: getfield        ClassSub/Class287.x:F
        //   576: aload_0        
        //   577: getfield        ClassSub/Class287.y:F
        //   580: ldc             10.0
        //   582: fadd           
        //   583: fload           8
        //   585: fadd           
        //   586: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   589: getfield        ClassSub/Class15.c:I
        //   592: ldc             0
        //   594: invokevirtual   net/minecraft/client/gui/FontRenderer.drawString:(Ljava/lang/String;FFIZ)I
        //   597: pop            
        //   598: iload           7
        //   600: aload_3        
        //   601: invokeinterface java/util/Collection.size:()I
        //   606: if_icmpne       676
        //   609: aload_0        
        //   610: aload_1        
        //   611: invokevirtual   net/minecraft/scoreboard/ScoreObjective.getDisplayName:()Ljava/lang/String;
        //   614: aload_0        
        //   615: getfield        ClassSub/Class287.x:F
        //   618: aload_0        
        //   619: getfield        ClassSub/Class287.xSize:F
        //   622: fconst_2       
        //   623: fdiv           
        //   624: fadd           
        //   625: aload_0        
        //   626: getfield        ClassSub/Class287.y:F
        //   629: aload_0        
        //   630: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   633: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   636: instanceof      Lnet/minecraft/client/gui/GuiChat;
        //   639: ifne            655
        //   642: aload_0        
        //   643: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   646: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   649: instanceof      LClassSub/Class208;
        //   652: ifeq            663
        //   655: ldc_w           0.5
        //   658: goto            666
        //   661: nop            
        //   662: athrow         
        //   663: ldc_w           -0.5
        //   666: fadd           
        //   667: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   670: getfield        ClassSub/Class15.c:I
        //   673: invokevirtual   ClassSub/Class287.drawCenteredString:(Ljava/lang/String;FFI)V
        //   676: fload           8
        //   678: aload_0        
        //   679: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   682: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   685: getfield        net/minecraft/client/gui/FontRenderer.FONT_HEIGHT:I
        //   688: i2f            
        //   689: fadd           
        //   690: fstore          8
        //   692: goto            423
        //   695: nop            
        //   696: athrow         
        //   697: aload_0        
        //   698: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   701: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   704: instanceof      Lnet/minecraft/client/gui/GuiChat;
        //   707: ifne            723
        //   710: aload_0        
        //   711: getfield        ClassSub/Class287.mc:Lnet/minecraft/client/Minecraft;
        //   714: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   717: instanceof      LClassSub/Class208;
        //   720: ifeq            962
        //   723: new             Ljava/awt/Color;
        //   726: dup            
        //   727: ldc_w           -14848033
        //   730: invokespecial   java/awt/Color.<init>:(I)V
        //   733: invokevirtual   java/awt/Color.brighter:()Ljava/awt/Color;
        //   736: invokevirtual   java/awt/Color.getRGB:()I
        //   739: istore_2       
        //   740: aload_0        
        //   741: aload_0        
        //   742: getfield        ClassSub/Class287.x:F
        //   745: fconst_2       
        //   746: fsub           
        //   747: aload_0        
        //   748: getfield        ClassSub/Class287.y:F
        //   751: ldc_w           12.0
        //   754: fsub           
        //   755: aload_0        
        //   756: getfield        ClassSub/Class287.x:F
        //   759: aload_0        
        //   760: getfield        ClassSub/Class287.xSize:F
        //   763: fadd           
        //   764: ldc             5.0
        //   766: fadd           
        //   767: aload_0        
        //   768: getfield        ClassSub/Class287.y:F
        //   771: iload_2        
        //   772: iload_2        
        //   773: invokevirtual   ClassSub/Class287.drawRoundedRect:(FFFFII)V
        //   776: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   779: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   782: getfield        cn/Hanabi/utils/fontmanager/FontManager.wqy18:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   785: ldc_w           "\u8ba1\u5206\u677f"
        //   788: aload_0        
        //   789: getfield        ClassSub/Class287.x:F
        //   792: aload_0        
        //   793: getfield        ClassSub/Class287.y:F
        //   796: ldc_w           11.0
        //   799: fsub           
        //   800: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   803: getfield        ClassSub/Class15.c:I
        //   806: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;FFI)I
        //   809: pop            
        //   810: aload_0        
        //   811: getfield        ClassSub/Class287.x:F
        //   814: aload_0        
        //   815: getfield        ClassSub/Class287.xSize:F
        //   818: fadd           
        //   819: fconst_2       
        //   820: fsub           
        //   821: aload_0        
        //   822: getfield        ClassSub/Class287.y:F
        //   825: ldc_w           6.0
        //   828: fsub           
        //   829: ldc2_w          3.0
        //   832: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   835: getfield        ClassSub/Class15.c:I
        //   838: ldc             0
        //   840: ldc2_w          360.0
        //   843: ldc             1
        //   845: invokestatic    ClassSub/Class246.drawArc:(FFDIIDI)V
        //   848: aload_0        
        //   849: getfield        ClassSub/Class287.x:F
        //   852: aload_0        
        //   853: getfield        ClassSub/Class287.xSize:F
        //   856: fadd           
        //   857: ldc_w           12.0
        //   860: fsub           
        //   861: aload_0        
        //   862: getfield        ClassSub/Class287.y:F
        //   865: ldc_w           6.0
        //   868: fsub           
        //   869: ldc2_w          3.0
        //   872: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   875: getfield        ClassSub/Class15.c:I
        //   878: ldc             0
        //   880: ldc2_w          360.0
        //   883: ldc             1
        //   885: invokestatic    ClassSub/Class246.drawArc:(FFDIIDI)V
        //   888: aload_0        
        //   889: getfield        ClassSub/Class287.showNumber:Z
        //   892: ifeq            926
        //   895: aload_0        
        //   896: getfield        ClassSub/Class287.x:F
        //   899: aload_0        
        //   900: getfield        ClassSub/Class287.xSize:F
        //   903: fadd           
        //   904: ldc_w           12.0
        //   907: fsub           
        //   908: aload_0        
        //   909: getfield        ClassSub/Class287.y:F
        //   912: ldc_w           6.0
        //   915: fsub           
        //   916: fconst_1       
        //   917: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   920: getfield        ClassSub/Class15.c:I
        //   923: invokestatic    ClassSub/Class246.circle:(FFFI)V
        //   926: aload_0        
        //   927: getfield        ClassSub/Class287.isExtended:Z
        //   930: ifeq            962
        //   933: aload_0        
        //   934: getfield        ClassSub/Class287.x:F
        //   937: aload_0        
        //   938: getfield        ClassSub/Class287.xSize:F
        //   941: fadd           
        //   942: fconst_2       
        //   943: fsub           
        //   944: aload_0        
        //   945: getfield        ClassSub/Class287.y:F
        //   948: ldc_w           6.0
        //   951: fsub           
        //   952: fconst_1       
        //   953: getstatic       ClassSub/Class15.WHITE:LClassSub/Class15;
        //   956: getfield        ClassSub/Class15.c:I
        //   959: invokestatic    ClassSub/Class246.circle:(FFFI)V
        //   962: fconst_1       
        //   963: fconst_1       
        //   964: fconst_1       
        //   965: fconst_1       
        //   966: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   969: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void drawCenteredString(final String s, final float n, final float n2, final int n3) {
        Minecraft.getMinecraft().fontRendererObj.drawString(s, n - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) / 2, n2, n3, false);
    }
    
    public void moveWindow(final int n, final int n2) {
        if (this.isHovering(n, n2, this.x + this.xSize - 6.0f, this.y - 10.0f, this.x + this.xSize + 2.0f, this.y - 2.0f)) {
            if (this.handler.canExcecute()) {
                this.isExtended = !this.isExtended;
            }
            return;
        }
        if (this.isHovering(n, n2, this.x + this.xSize - 16.0f, this.y - 10.0f, this.x + this.xSize - 8.0f, this.y - 2.0f)) {
            if (this.handler.canExcecute()) {
                this.showNumber = !this.showNumber;
            }
            return;
        }
        if (this.isHoveringWindow(n, n2) && this.handler.canExcecute()) {
            this.isDragging = true;
            this.x2 = (int)(n - this.x);
            this.y2 = (int)(n2 - this.y);
        }
        if (this.isDragging) {
            this.x = n - this.x2;
            this.y = n2 - this.y2;
        }
        if (!Mouse.isButtonDown(0) && this.isDragging) {
            this.isDragging = false;
        }
    }
    
    public void drawRoundedRect(float n, float n2, float n3, float n4, final int n5, final int n6) {
        this.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawVLine(n *= 2.0f, (n2 *= 2.0f) + 1.0f, (n4 *= 2.0f) - 2.0f, n5);
        this.drawVLine((n3 *= 2.0f) - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        this.drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        this.drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        this.drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        this.drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        this.drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        this.drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.disableGL2D();
    }
    
    public static void drawRect(float n, float n2, float n3, float n4, final int n5) {
        if (n < n3) {
            final float n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final float n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final WorldRenderer getWorldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n9, n10, n11, n8);
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos((double)n, (double)n4, 0.0).endVertex();
        getWorldRenderer.pos((double)n3, (double)n4, 0.0).endVertex();
        getWorldRenderer.pos((double)n3, (double)n2, 0.0).endVertex();
        getWorldRenderer.pos((double)n, (double)n2, 0.0).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void drawHLine(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        drawRect(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public void drawVLine(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        drawRect(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public void color(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    private boolean isHovering(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    private boolean isHoveringWindow(final int n, final int n2) {
        return n >= this.x - 2.0f && n <= this.x + this.xSize + 5.0f && n2 >= this.y - 10.0f && n2 <= this.y;
    }
}
