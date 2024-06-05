package ClassSub;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import net.minecraft.client.*;
import cn.Hanabi.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Class77
{
    public static int x;
    public static int y;
    public static int windowWidth;
    public static int windowHeight;
    public static int wheelStateMod;
    public static int wheelStateValue;
    public int tX;
    public int tY;
    public int dragX;
    public int dragY;
    public static double wheelSmoothMod;
    public static double wheelSmoothValue;
    public static boolean isDraging;
    public boolean clickNotDraging;
    public Category category;
    public List<Class281> mods;
    public static Map<Value, Class204> booleanValueMap;
    public static Map<Value, Class88> doubleValueMap;
    public static Map<Value, Class137> modeValueMap;
    
    
    public Class77(final Category category) {
        this.clickNotDraging = false;
        this.mods = new ArrayList<Class281>();
        this.category = category;
        Class77.x = -1;
        Class77.y = -1;
    }
    
    public void createModUI() {
        this.mods.clear();
        for (final Mod mod : ModManager.getModList()) {
            if (mod.getCategory() == this.category) {
                this.mods.add(new Class281(mod));
            }
        }
    }
    
    public void draw(final int n, final int n2) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        Class77.windowWidth = (int)(scaledResolution.getScaledWidth() * 0.5);
        Class77.windowHeight = (int)(scaledResolution.getScaledHeight() * 0.6);
        if (Class77.windowWidth < 370) {
            Class77.windowWidth = 370;
        }
        if (Class77.windowHeight < 240) {
            Class77.windowHeight = 240;
        }
        if (Class77.x == -1 || Class77.y == -1) {
            Class77.x = scaledResolution.getScaledWidth() / 2 - Class77.windowWidth / 2;
            Class77.y = scaledResolution.getScaledHeight() / 2 - Class77.windowHeight / 2;
        }
        this.processDrag(n, n2);
        this.processWheel(n, n2);
        this.drawBackground();
        this.drawModList();
        this.drawValueList(n, n2);
    }
    
    private void drawValueList(final int n, final int n2) {
        class Class322 extends Class204
        {
            final Value val$value;
            final Class77 this$0;
            
            
            Class322(final Class77 this$0, final String s, final boolean b, final Value val$value) {
                this.this$0 = this$0;
                this.val$value = val$value;
                super(s, b);
            }
            
            @Override
            public void onPress() {
                if (!this.parent.equals(Class281.selectedMod.getName())) {
                    return;
                }
                this.val$value.setValueState(!this.val$value.getValueState());
                super.onPress();
            }
        }
        class Class78 extends Class204
        {
            final Mod val$selectedMod;
            final Class77 this$0;
            
            
            Class78(final Class77 this$0, final String s, final boolean b, final Mod val$selectedMod) {
                this.this$0 = this$0;
                this.val$selectedMod = val$selectedMod;
                super(s, b);
            }
            
            @Override
            public void onPress() {
                this.val$selectedMod.set(!this.val$selectedMod.isEnabled());
                for (final Class281 class281 : this.this$0.mods) {
                    if (class281.mod == this.val$selectedMod) {
                        class281.button.toggle();
                        break;
                    }
                }
                super.onPress();
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: getstatic       ClassSub/Class281.selectedMod:Lcn/Hanabi/modules/Mod;
        //     7: ifnull          1178
        //    10: getstatic       ClassSub/Class281.selectedMod:Lcn/Hanabi/modules/Mod;
        //    13: astore_3       
        //    14: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //    17: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //    20: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans18:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //    23: aload_3        
        //    24: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //    27: getstatic       ClassSub/Class77.x:I
        //    30: ldc             120
        //    32: iadd           
        //    33: getstatic       ClassSub/Class77.y:I
        //    36: ldc             30
        //    38: iadd           
        //    39: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //    42: ldc             "Light"
        //    44: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //    47: ifeq            61
        //    50: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //    53: getfield        ClassSub/Class15.c:I
        //    56: goto            77
        //    59: nop            
        //    60: athrow         
        //    61: new             Ljava/awt/Color;
        //    64: dup            
        //    65: ldc             167
        //    67: ldc             167
        //    69: ldc             167
        //    71: invokespecial   java/awt/Color.<init>:(III)V
        //    74: invokevirtual   java/awt/Color.getRGB:()I
        //    77: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;III)I
        //    80: pop            
        //    81: aload_3        
        //    82: getfield        cn/Hanabi/modules/Mod.modButton:LClassSub/Class204;
        //    85: ifnonnull       109
        //    88: aload_3        
        //    89: new             LClassSub/Class78;
        //    92: dup            
        //    93: aload_0        
        //    94: aload_3        
        //    95: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //    98: aload_3        
        //    99: invokevirtual   cn/Hanabi/modules/Mod.isEnabled:()Z
        //   102: aload_3        
        //   103: invokespecial   ClassSub/Class78.<init>:(LClassSub/Class77;Ljava/lang/String;ZLcn/Hanabi/modules/Mod;)V
        //   106: putfield        cn/Hanabi/modules/Mod.modButton:LClassSub/Class204;
        //   109: aload_3        
        //   110: getfield        cn/Hanabi/modules/Mod.modButton:LClassSub/Class204;
        //   113: getstatic       ClassSub/Class77.x:I
        //   116: getstatic       ClassSub/Class77.windowWidth:I
        //   119: iadd           
        //   120: ldc             35
        //   122: isub           
        //   123: i2f            
        //   124: getstatic       ClassSub/Class77.y:I
        //   127: ldc             36
        //   129: iadd           
        //   130: i2f            
        //   131: invokevirtual   ClassSub/Class204.draw:(FF)V
        //   134: aload_3        
        //   135: getfield        cn/Hanabi/modules/Mod.modButton:LClassSub/Class204;
        //   138: aload_3        
        //   139: invokevirtual   cn/Hanabi/modules/Mod.isEnabled:()Z
        //   142: putfield        ClassSub/Class204.state:Z
        //   145: getstatic       ClassSub/Class77.x:I
        //   148: ldc             115
        //   150: iadd           
        //   151: i2f            
        //   152: getstatic       ClassSub/Class77.y:I
        //   155: ldc             45
        //   157: iadd           
        //   158: i2f            
        //   159: getstatic       ClassSub/Class77.x:I
        //   162: getstatic       ClassSub/Class77.windowWidth:I
        //   165: iadd           
        //   166: ldc             15
        //   168: isub           
        //   169: i2f            
        //   170: getstatic       ClassSub/Class77.y:I
        //   173: i2f            
        //   174: ldc             45.3
        //   176: fadd           
        //   177: new             Ljava/awt/Color;
        //   180: dup            
        //   181: ldc             40
        //   183: ldc             40
        //   185: ldc             40
        //   187: invokespecial   java/awt/Color.<init>:(III)V
        //   190: invokevirtual   java/awt/Color.getRGB:()I
        //   193: invokestatic    ClassSub/Class246.drawRect:(FFFFI)V
        //   196: new             Ljava/util/ArrayList;
        //   199: dup            
        //   200: invokespecial   java/util/ArrayList.<init>:()V
        //   203: astore          4
        //   205: new             Ljava/util/ArrayList;
        //   208: dup            
        //   209: invokespecial   java/util/ArrayList.<init>:()V
        //   212: astore          5
        //   214: new             Ljava/util/ArrayList;
        //   217: dup            
        //   218: invokespecial   java/util/ArrayList.<init>:()V
        //   221: astore          6
        //   223: aload           4
        //   225: invokeinterface java/util/List.clear:()V
        //   230: aload           5
        //   232: invokeinterface java/util/List.clear:()V
        //   237: aload           6
        //   239: invokeinterface java/util/List.clear:()V
        //   244: aload_3        
        //   245: ldc             0
        //   247: putfield        cn/Hanabi/modules/Mod.valueSize:I
        //   250: getstatic       cn/Hanabi/value/Value.list:Ljava/util/List;
        //   253: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   258: astore          7
        //   260: aload           7
        //   262: invokeinterface java/util/Iterator.hasNext:()Z
        //   267: ifeq            384
        //   270: aload           7
        //   272: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   277: checkcast       Lcn/Hanabi/value/Value;
        //   280: astore          8
        //   282: aload           8
        //   284: invokevirtual   cn/Hanabi/value/Value.getValueName:()Ljava/lang/String;
        //   287: ldc_w           "_"
        //   290: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   293: checkcast       [Ljava/lang/String;
        //   296: ldc             0
        //   298: aaload         
        //   299: astore          9
        //   301: aload           9
        //   303: aload_3        
        //   304: invokevirtual   cn/Hanabi/modules/Mod.getName:()Ljava/lang/String;
        //   307: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   310: ifeq            379
        //   313: aload_3        
        //   314: dup            
        //   315: getfield        cn/Hanabi/modules/Mod.valueSize:I
        //   318: ldc_w           1
        //   321: iadd           
        //   322: putfield        cn/Hanabi/modules/Mod.valueSize:I
        //   325: aload           8
        //   327: getfield        cn/Hanabi/value/Value.isValueDouble:Z
        //   330: ifeq            343
        //   333: aload           6
        //   335: aload           8
        //   337: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   342: pop            
        //   343: aload           8
        //   345: getfield        cn/Hanabi/value/Value.isValueMode:Z
        //   348: ifeq            361
        //   351: aload           5
        //   353: aload           8
        //   355: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   360: pop            
        //   361: aload           8
        //   363: getfield        cn/Hanabi/value/Value.isValueBoolean:Z
        //   366: ifeq            379
        //   369: aload           4
        //   371: aload           8
        //   373: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   378: pop            
        //   379: goto            260
        //   382: nop            
        //   383: athrow         
        //   384: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   387: ldc_w           3089
        //   390: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   393: getstatic       ClassSub/Class77.x:I
        //   396: ldc_w           110
        //   399: iadd           
        //   400: getstatic       ClassSub/Class77.y:I
        //   403: ldc             45
        //   405: iadd           
        //   406: getstatic       ClassSub/Class77.windowWidth:I
        //   409: getstatic       ClassSub/Class77.windowHeight:I
        //   412: ldc_w           55
        //   415: isub           
        //   416: invokestatic    ClassSub/Class246.doGlScissor:(IIII)V
        //   419: getstatic       ClassSub/Class77.wheelSmoothValue:D
        //   422: getstatic       ClassSub/Class77.wheelStateValue:I
        //   425: ldc             30
        //   427: imul           
        //   428: i2d            
        //   429: ldc2_w          400.0
        //   432: invokestatic    ClassSub/Class246.getAnimationState:(DDD)D
        //   435: putstatic       ClassSub/Class77.wheelSmoothValue:D
        //   438: getstatic       ClassSub/Class77.y:I
        //   441: i2d            
        //   442: getstatic       ClassSub/Class77.wheelSmoothValue:D
        //   445: dadd           
        //   446: d2i            
        //   447: istore          7
        //   449: iload           7
        //   451: ldc_w           60
        //   454: iadd           
        //   455: istore          8
        //   457: aload           4
        //   459: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   464: astore          9
        //   466: aload           9
        //   468: invokeinterface java/util/Iterator.hasNext:()Z
        //   473: ifeq            693
        //   476: aload           9
        //   478: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   483: checkcast       Lcn/Hanabi/value/Value;
        //   486: astore          10
        //   488: aload           10
        //   490: invokevirtual   cn/Hanabi/value/Value.getValueName:()Ljava/lang/String;
        //   493: ldc_w           "_"
        //   496: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   499: checkcast       [Ljava/lang/String;
        //   502: ldc             0
        //   504: aaload         
        //   505: astore          11
        //   507: aload           10
        //   509: invokevirtual   cn/Hanabi/value/Value.getValueName:()Ljava/lang/String;
        //   512: ldc_w           "_"
        //   515: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   518: checkcast       [Ljava/lang/String;
        //   521: ldc_w           1
        //   524: aaload         
        //   525: astore          12
        //   527: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   530: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   533: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans16:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   536: aload           12
        //   538: getstatic       ClassSub/Class77.x:I
        //   541: ldc             120
        //   543: iadd           
        //   544: iload           8
        //   546: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //   549: ldc             "Light"
        //   551: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   554: ifeq            568
        //   557: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //   560: getfield        ClassSub/Class15.c:I
        //   563: goto            584
        //   566: nop            
        //   567: athrow         
        //   568: new             Ljava/awt/Color;
        //   571: dup            
        //   572: ldc             167
        //   574: ldc             167
        //   576: ldc             167
        //   578: invokespecial   java/awt/Color.<init>:(III)V
        //   581: invokevirtual   java/awt/Color.getRGB:()I
        //   584: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;III)I
        //   587: pop            
        //   588: aconst_null    
        //   589: astore          13
        //   591: getstatic       ClassSub/Class77.booleanValueMap:Ljava/util/Map;
        //   594: aload           10
        //   596: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   601: ifeq            624
        //   604: getstatic       ClassSub/Class77.booleanValueMap:Ljava/util/Map;
        //   607: aload           10
        //   609: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   614: checkcast       LClassSub/Class204;
        //   617: astore          13
        //   619: goto            662
        //   622: nop            
        //   623: athrow         
        //   624: new             LClassSub/Class322;
        //   627: dup            
        //   628: aload_0        
        //   629: aload           11
        //   631: aload           10
        //   633: invokevirtual   cn/Hanabi/value/Value.getValueState:()Ljava/lang/Object;
        //   636: checkcast       Ljava/lang/Boolean;
        //   639: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   642: aload           10
        //   644: invokespecial   ClassSub/Class322.<init>:(LClassSub/Class77;Ljava/lang/String;ZLcn/Hanabi/value/Value;)V
        //   647: astore          13
        //   649: getstatic       ClassSub/Class77.booleanValueMap:Ljava/util/Map;
        //   652: aload           10
        //   654: aload           13
        //   656: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   661: pop            
        //   662: aload           13
        //   664: getstatic       ClassSub/Class77.x:I
        //   667: getstatic       ClassSub/Class77.windowWidth:I
        //   670: iadd           
        //   671: ldc             35
        //   673: isub           
        //   674: i2f            
        //   675: iload           8
        //   677: i2f            
        //   678: ldc_w           5.5
        //   681: fadd           
        //   682: invokevirtual   ClassSub/Class204.draw:(FF)V
        //   685: iinc            8, 30
        //   688: goto            466
        //   691: nop            
        //   692: athrow         
        //   693: aload           6
        //   695: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   700: astore          9
        //   702: aload           9
        //   704: invokeinterface java/util/Iterator.hasNext:()Z
        //   709: ifeq            896
        //   712: aload           9
        //   714: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   719: checkcast       Lcn/Hanabi/value/Value;
        //   722: astore          10
        //   724: aload           10
        //   726: invokevirtual   cn/Hanabi/value/Value.getValueName:()Ljava/lang/String;
        //   729: ldc_w           "_"
        //   732: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   735: checkcast       [Ljava/lang/String;
        //   738: ldc_w           1
        //   741: aaload         
        //   742: astore          11
        //   744: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   747: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   750: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans16:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   753: aload           11
        //   755: getstatic       ClassSub/Class77.x:I
        //   758: ldc             120
        //   760: iadd           
        //   761: iload           8
        //   763: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //   766: ldc             "Light"
        //   768: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   771: ifeq            785
        //   774: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //   777: getfield        ClassSub/Class15.c:I
        //   780: goto            801
        //   783: nop            
        //   784: athrow         
        //   785: new             Ljava/awt/Color;
        //   788: dup            
        //   789: ldc             167
        //   791: ldc             167
        //   793: ldc             167
        //   795: invokespecial   java/awt/Color.<init>:(III)V
        //   798: invokevirtual   java/awt/Color.getRGB:()I
        //   801: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;III)I
        //   804: pop            
        //   805: aconst_null    
        //   806: astore          12
        //   808: getstatic       ClassSub/Class77.doubleValueMap:Ljava/util/Map;
        //   811: aload           10
        //   813: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   818: ifeq            841
        //   821: getstatic       ClassSub/Class77.doubleValueMap:Ljava/util/Map;
        //   824: aload           10
        //   826: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   831: checkcast       LClassSub/Class88;
        //   834: astore          12
        //   836: goto            865
        //   839: nop            
        //   840: athrow         
        //   841: new             LClassSub/Class88;
        //   844: dup            
        //   845: aload           10
        //   847: invokespecial   ClassSub/Class88.<init>:(Lcn/Hanabi/value/Value;)V
        //   850: astore          12
        //   852: getstatic       ClassSub/Class77.doubleValueMap:Ljava/util/Map;
        //   855: aload           10
        //   857: aload           12
        //   859: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   864: pop            
        //   865: aload           12
        //   867: getstatic       ClassSub/Class77.x:I
        //   870: getstatic       ClassSub/Class77.windowWidth:I
        //   873: iadd           
        //   874: i2f            
        //   875: iload           8
        //   877: i2f            
        //   878: invokevirtual   ClassSub/Class88.draw:(FF)V
        //   881: aload           12
        //   883: iload_1        
        //   884: iload_2        
        //   885: invokevirtual   ClassSub/Class88.onPress:(II)V
        //   888: iinc            8, 30
        //   891: goto            702
        //   894: nop            
        //   895: athrow         
        //   896: aload           5
        //   898: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   903: astore          9
        //   905: aload           9
        //   907: invokeinterface java/util/Iterator.hasNext:()Z
        //   912: ifeq            1079
        //   915: aload           9
        //   917: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   922: checkcast       Lcn/Hanabi/value/Value;
        //   925: astore          10
        //   927: aload           10
        //   929: invokevirtual   cn/Hanabi/value/Value.getDisplayTitle:()Ljava/lang/String;
        //   932: astore          11
        //   934: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   937: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   940: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans16:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   943: aload           11
        //   945: getstatic       ClassSub/Class77.x:I
        //   948: ldc             120
        //   950: iadd           
        //   951: iload           8
        //   953: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //   956: ldc             "Light"
        //   958: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //   961: ifeq            975
        //   964: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //   967: getfield        ClassSub/Class15.c:I
        //   970: goto            991
        //   973: nop            
        //   974: athrow         
        //   975: new             Ljava/awt/Color;
        //   978: dup            
        //   979: ldc             167
        //   981: ldc             167
        //   983: ldc             167
        //   985: invokespecial   java/awt/Color.<init>:(III)V
        //   988: invokevirtual   java/awt/Color.getRGB:()I
        //   991: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;III)I
        //   994: pop            
        //   995: aconst_null    
        //   996: astore          12
        //   998: getstatic       ClassSub/Class77.modeValueMap:Ljava/util/Map;
        //  1001: aload           10
        //  1003: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //  1008: ifeq            1031
        //  1011: getstatic       ClassSub/Class77.modeValueMap:Ljava/util/Map;
        //  1014: aload           10
        //  1016: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1021: checkcast       LClassSub/Class137;
        //  1024: astore          12
        //  1026: goto            1055
        //  1029: nop            
        //  1030: athrow         
        //  1031: new             LClassSub/Class137;
        //  1034: dup            
        //  1035: aload           10
        //  1037: invokespecial   ClassSub/Class137.<init>:(Lcn/Hanabi/value/Value;)V
        //  1040: astore          12
        //  1042: getstatic       ClassSub/Class77.modeValueMap:Ljava/util/Map;
        //  1045: aload           10
        //  1047: aload           12
        //  1049: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //  1054: pop            
        //  1055: aload           12
        //  1057: getstatic       ClassSub/Class77.x:I
        //  1060: getstatic       ClassSub/Class77.windowWidth:I
        //  1063: iadd           
        //  1064: iload           8
        //  1066: iload_1        
        //  1067: iload_2        
        //  1068: invokevirtual   ClassSub/Class137.draw:(IIII)V
        //  1071: iinc            8, 30
        //  1074: goto            905
        //  1077: nop            
        //  1078: athrow         
        //  1079: aload_3        
        //  1080: getfield        cn/Hanabi/modules/Mod.valueSize:I
        //  1083: ifne            1169
        //  1086: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //  1089: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //  1092: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans16:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //  1095: ldc_w           "No setting here."
        //  1098: getstatic       ClassSub/Class77.x:I
        //  1101: ldc             120
        //  1103: iadd           
        //  1104: getstatic       ClassSub/Class77.y:I
        //  1107: ldc_w           60
        //  1110: iadd           
        //  1111: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //  1114: ldc             "Light"
        //  1116: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //  1119: ifeq            1149
        //  1122: new             Ljava/awt/Color;
        //  1125: dup            
        //  1126: getstatic       ClassSub/Class15.BLACK:LClassSub/Class15;
        //  1129: getfield        ClassSub/Class15.c:I
        //  1132: invokespecial   java/awt/Color.<init>:(I)V
        //  1135: invokevirtual   java/awt/Color.brighter:()Ljava/awt/Color;
        //  1138: invokevirtual   java/awt/Color.brighter:()Ljava/awt/Color;
        //  1141: invokevirtual   java/awt/Color.getRGB:()I
        //  1144: goto            1165
        //  1147: nop            
        //  1148: athrow         
        //  1149: new             Ljava/awt/Color;
        //  1152: dup            
        //  1153: ldc             167
        //  1155: ldc             167
        //  1157: ldc             167
        //  1159: invokespecial   java/awt/Color.<init>:(III)V
        //  1162: invokevirtual   java/awt/Color.getRGB:()I
        //  1165: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;III)I
        //  1168: pop            
        //  1169: ldc_w           3089
        //  1172: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //  1175: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //  1178: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void drawModList() {
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        Class246.doGlScissor(Class77.x + 10, Class77.y + 25, 100, Class77.windowHeight - 35);
        Class77.wheelSmoothMod = Class246.getAnimationState(Class77.wheelSmoothMod, Class77.wheelStateMod * 30, 400.0);
        int n = (int)(Class77.y + Class77.wheelSmoothMod) + 25;
        final Iterator<Class281> iterator = this.mods.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw(Class77.x + 10, n);
            n += 35;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
    
    private void drawBackground() {
        final int n = Class135.theme.isCurrentMode("Light") ? -921615 : new Color(27, 27, 27).getRGB();
        final int n2 = Class135.theme.isCurrentMode("Light") ? Class15.RED.c : new Color(47, 116, 253).getRGB();
        if (!Class135.theme.isCurrentMode("Light")) {
            Class246.drawRoundedRect(Class77.x - 0.5f, Class77.y - 0.5f, Class77.x + Class77.windowWidth + 0.5f, Class77.y + Class77.windowHeight + 0.5f, 6.0f, new Color(16, 76, 182).getRGB());
        }
        else {
            Class246.drawRoundedRect(Class77.x - 1.0f, Class77.y - 1.0f, Class77.x + Class77.windowWidth + 1.0f, Class77.y + Class77.windowHeight + 1.0f, 6.0f, n);
        }
        Class246.drawRoundedRect(Class77.x, Class77.y, Class77.x + Class77.windowWidth, Class77.y + Class77.windowHeight, 6.0f, n);
        Class246.drawRoundedRect(Class77.x + 2, Class77.y + 14, Class77.x + Class77.windowWidth - 2, Class77.y + Class77.windowHeight - 2, 6.0f, Class135.theme.isCurrentMode("Light") ? new Color(Class15.GREY.c).brighter().brighter().getRGB() : new Color(13, 13, 13).getRGB());
        Class246.circle(Class77.x + Class77.windowWidth - 10, Class77.y + 8, 2.5f, n2);
        Hanabi.INSTANCE.fontManager.raleway16.drawString(this.category.toString(), Class77.x + 8, Class77.y + 3, Class135.theme.isCurrentMode("Light") ? Class15.BLACK.c : new Color(167, 167, 167).getRGB());
    }
    
    private void processWheel(final int n, final int n2) {
        final int dWheel = Mouse.getDWheel();
        if (this.isHover(Class77.x + 10, Class77.y + 25, 100, Class77.windowHeight - 35, n, n2)) {
            if (dWheel > 0) {
                if (Class77.wheelStateMod < 0) {
                    ++Class77.wheelStateMod;
                }
            }
            else if (dWheel < 0 && Class77.wheelStateMod * 30 > this.mods.size() * -30) {
                --Class77.wheelStateMod;
            }
        }
        else if (this.isHover(Class77.x + 110, Class77.y + 25, Class77.windowWidth, Class77.windowHeight - 35, n, n2)) {
            if (dWheel > 0) {
                if (Class77.wheelStateValue < 0) {
                    ++Class77.wheelStateValue;
                }
            }
            else if (dWheel < 0 && Class281.selectedMod != null && Class77.wheelStateValue * 50 > Class281.selectedMod.valueSize * -40) {
                --Class77.wheelStateValue;
            }
        }
    }
    
    private void processDrag(final int tx, final int ty) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (Mouse.isButtonDown(0)) {
            if (this.isHover(Class77.x + Class77.windowWidth - 17, Class77.y, 10, 10, tx, ty)) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                return;
            }
            if (this.isHover(Class77.x, Class77.y, Class77.windowWidth, 15, tx, ty) || Class77.isDraging) {
                Class77.isDraging = true;
            }
            else {
                this.clickNotDraging = true;
            }
            if (Class77.isDraging && !this.clickNotDraging) {
                this.dragX = tx - this.tX;
                this.dragY = ty - this.tY;
                if (Class77.x < scaledResolution.getScaledWidth() * 0.155) {
                    Class77.x = (int)(scaledResolution.getScaledWidth() * 0.155) + 1;
                }
                else if (Class77.x > scaledResolution.getScaledWidth() - Class77.windowWidth) {
                    Class77.x = scaledResolution.getScaledWidth() - Class77.windowWidth - 1;
                }
                else {
                    Class77.x += this.dragX;
                }
                if (Class77.y < 0) {
                    Class77.y = 1;
                }
                else if (Class77.y > scaledResolution.getScaledHeight() - Class77.windowHeight) {
                    Class77.y = scaledResolution.getScaledHeight() - Class77.windowHeight - 1;
                }
                else {
                    Class77.y += this.dragY;
                }
            }
        }
        else {
            Class77.isDraging = false;
            this.clickNotDraging = false;
        }
        this.tX = tx;
        this.tY = ty;
    }
    
    public boolean isHover(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n5 >= n && n5 <= n + n3 && n6 >= n2 && n6 <= n2 + n4;
    }
    
    public void onMouseClick(final int n, final int n2) {
        for (final Class281 class281 : this.mods) {
            if (this.isHover(Class77.x + 10, Class77.y + 25, 100, Class77.windowHeight - 35, n, n2) && class281.isPressed(n, n2)) {
                final Iterator<Class281> iterator2 = this.mods.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().selected = false;
                }
                class281.selected = true;
                Class77.wheelStateValue = 0;
                break;
            }
        }
        final Iterator<Class204> iterator3 = Class77.booleanValueMap.values().iterator();
        while (iterator3.hasNext()) {
            iterator3.next().isPressed(n, n2);
        }
        if (Class281.selectedMod != null && Class281.selectedMod.modButton != null) {
            Class281.selectedMod.modButton.isPressed(n, n2);
        }
    }
    
    static {
        Class77.isDraging = false;
        Class77.booleanValueMap = new HashMap<Value, Class204>();
        Class77.doubleValueMap = new HashMap<Value, Class88>();
        Class77.modeValueMap = new HashMap<Value, Class137>();
    }
}
