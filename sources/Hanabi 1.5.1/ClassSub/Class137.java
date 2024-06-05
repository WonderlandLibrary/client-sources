package ClassSub;

import cn.Hanabi.value.*;
import java.awt.*;
import cn.Hanabi.*;
import cn.Hanabi.utils.fontmanager.*;
import org.lwjgl.input.*;

public class Class137
{
    Value<String> value;
    int x;
    int y;
    private boolean readySelect;
    private boolean isSelectingMode;
    double ani;
    public static Value<String> renderingValue;
    
    
    public Class137(final Value value) {
        this.value = (Value<String>)value;
    }
    
    public void draw(final int n, final int n2, final int n3, final int n4) {
        class Class76 extends Thread
        {
            final Class137 this$0;
            
            
            Class76(final Class137 this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Class137.renderingValue = null;
            }
        }
        class Class196 extends Thread
        {
            final Class137 val$selector;
            final Class137 this$0;
            
            
            Class196(final Class137 this$0, final Class137 val$selector) {
                this.this$0 = this$0;
                this.val$selector = val$selector;
            }
            
            @Override
            public void run() {
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Class137.access$002(this.val$selector, false);
                Class137.access$102(this.val$selector, false);
                Class137.renderingValue = null;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: getfield        ClassSub/Class137.isSelectingMode:Z
        //     8: ifeq            18
        //    11: aload_0        
        //    12: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //    15: putstatic       ClassSub/Class137.renderingValue:Lcn/Hanabi/value/Value;
        //    18: getstatic       ClassSub/Class137.renderingValue:Lcn/Hanabi/value/Value;
        //    21: ifnull          40
        //    24: getstatic       ClassSub/Class137.renderingValue:Lcn/Hanabi/value/Value;
        //    27: aload_0        
        //    28: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //    31: if_acmpeq       40
        //    34: return         
        //    35: nop            
        //    36: nop            
        //    37: nop            
        //    38: nop            
        //    39: athrow         
        //    40: getstatic       ClassSub/Class135.theme:Lcn/Hanabi/value/Value;
        //    43: ldc             "Light"
        //    45: invokevirtual   cn/Hanabi/value/Value.isCurrentMode:(Ljava/lang/String;)Z
        //    48: istore          5
        //    50: iload_2        
        //    51: ldc             5
        //    53: isub           
        //    54: i2f            
        //    55: fstore          6
        //    57: fload           6
        //    59: ldc             7.0
        //    61: fadd           
        //    62: fstore          7
        //    64: aload_0        
        //    65: aload_0        
        //    66: getfield        ClassSub/Class137.ani:D
        //    69: aload_0        
        //    70: getfield        ClassSub/Class137.isSelectingMode:Z
        //    73: ifeq            94
        //    76: ldc             20
        //    78: aload_0        
        //    79: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //    82: getfield        cn/Hanabi/value/Value.mode:Ljava/util/ArrayList;
        //    85: invokevirtual   java/util/ArrayList.size:()I
        //    88: imul           
        //    89: goto            96
        //    92: nop            
        //    93: athrow         
        //    94: ldc             20
        //    96: i2d            
        //    97: ldc2_w          400.0
        //   100: invokestatic    ClassSub/Class246.getAnimationState:(DDD)D
        //   103: putfield        ClassSub/Class137.ani:D
        //   106: iload_1        
        //   107: ldc             80
        //   109: isub           
        //   110: i2f            
        //   111: fload           6
        //   113: iload_1        
        //   114: ldc             20
        //   116: isub           
        //   117: i2f            
        //   118: fload           6
        //   120: f2d            
        //   121: aload_0        
        //   122: getfield        ClassSub/Class137.ani:D
        //   125: dadd           
        //   126: d2f            
        //   127: fconst_2       
        //   128: iload           5
        //   130: ifeq            157
        //   133: new             Ljava/awt/Color;
        //   136: dup            
        //   137: getstatic       ClassSub/Class15.GREY:LClassSub/Class15;
        //   140: getfield        ClassSub/Class15.c:I
        //   143: invokespecial   java/awt/Color.<init>:(I)V
        //   146: invokevirtual   java/awt/Color.brighter:()Ljava/awt/Color;
        //   149: invokevirtual   java/awt/Color.getRGB:()I
        //   152: goto            173
        //   155: nop            
        //   156: athrow         
        //   157: new             Ljava/awt/Color;
        //   160: dup            
        //   161: ldc             15
        //   163: ldc             15
        //   165: ldc             15
        //   167: invokespecial   java/awt/Color.<init>:(III)V
        //   170: invokevirtual   java/awt/Color.getRGB:()I
        //   173: invokestatic    ClassSub/Class246.drawRoundedRect:(FFFFFI)V
        //   176: iload_1        
        //   177: ldc             80
        //   179: isub           
        //   180: i2f            
        //   181: fload           6
        //   183: iload_1        
        //   184: ldc             20
        //   186: isub           
        //   187: i2f            
        //   188: fload           6
        //   190: ldc             20.0
        //   192: fadd           
        //   193: fconst_2       
        //   194: iload           5
        //   196: ifeq            223
        //   199: new             Ljava/awt/Color;
        //   202: dup            
        //   203: getstatic       ClassSub/Class15.GREY:LClassSub/Class15;
        //   206: getfield        ClassSub/Class15.c:I
        //   209: invokespecial   java/awt/Color.<init>:(I)V
        //   212: invokevirtual   java/awt/Color.brighter:()Ljava/awt/Color;
        //   215: invokevirtual   java/awt/Color.getRGB:()I
        //   218: goto            239
        //   221: nop            
        //   222: athrow         
        //   223: new             Ljava/awt/Color;
        //   226: dup            
        //   227: ldc             21
        //   229: ldc             21
        //   231: ldc             21
        //   233: invokespecial   java/awt/Color.<init>:(III)V
        //   236: invokevirtual   java/awt/Color.getRGB:()I
        //   239: invokestatic    ClassSub/Class246.drawRoundedRect:(FFFFFI)V
        //   242: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   245: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   248: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans15:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   251: aload_0        
        //   252: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   255: getfield        cn/Hanabi/value/Value.mode:Ljava/util/ArrayList;
        //   258: aload_0        
        //   259: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   262: invokevirtual   cn/Hanabi/value/Value.getCurrentMode:()I
        //   265: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   268: checkcast       Ljava/lang/String;
        //   271: iload_1        
        //   272: ldc             75
        //   274: isub           
        //   275: i2f            
        //   276: fload           7
        //   278: ldc             -1
        //   280: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;FFI)I
        //   283: pop            
        //   284: aload_0        
        //   285: getfield        ClassSub/Class137.isSelectingMode:Z
        //   288: ifne            393
        //   291: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   294: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   297: getfield        cn/Hanabi/utils/fontmanager/FontManager.icon15:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   300: getstatic       cn/Hanabi/utils/fontmanager/HanabiFonts.ICON_CLICKGUI_ARROW_UP:Ljava/lang/String;
        //   303: iload_1        
        //   304: ldc             32
        //   306: isub           
        //   307: iload_2        
        //   308: ldc             1
        //   310: iadd           
        //   311: ldc             -1
        //   313: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.func_78276_b:(Ljava/lang/String;III)I
        //   316: pop            
        //   317: ldc             0
        //   319: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //   322: ifeq            367
        //   325: aload_0        
        //   326: iload_3        
        //   327: iload           4
        //   329: iload_1        
        //   330: ldc             80
        //   332: isub           
        //   333: i2d            
        //   334: fload           6
        //   336: f2d            
        //   337: iload_1        
        //   338: ldc             20
        //   340: isub           
        //   341: i2d            
        //   342: fload           6
        //   344: ldc             20.0
        //   346: fadd           
        //   347: f2d            
        //   348: invokespecial   ClassSub/Class137.isHovering:(IIDDDD)Z
        //   351: ifeq            367
        //   354: aload_0        
        //   355: getfield        ClassSub/Class137.readySelect:Z
        //   358: ifne            367
        //   361: aload_0        
        //   362: ldc             1
        //   364: putfield        ClassSub/Class137.readySelect:Z
        //   367: ldc             0
        //   369: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //   372: ifne            682
        //   375: aload_0        
        //   376: getfield        ClassSub/Class137.readySelect:Z
        //   379: ifeq            682
        //   382: aload_0        
        //   383: ldc             1
        //   385: putfield        ClassSub/Class137.isSelectingMode:Z
        //   388: goto            682
        //   391: nop            
        //   392: athrow         
        //   393: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   396: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   399: getfield        cn/Hanabi/utils/fontmanager/FontManager.icon15:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   402: getstatic       cn/Hanabi/utils/fontmanager/HanabiFonts.ICON_CLICKGUI_ARROW_DOWN:Ljava/lang/String;
        //   405: iload_1        
        //   406: ldc             32
        //   408: isub           
        //   409: iload_2        
        //   410: ldc             1
        //   412: iadd           
        //   413: ldc             -1
        //   415: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.func_78276_b:(Ljava/lang/String;III)I
        //   418: pop            
        //   419: aload_0        
        //   420: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   423: getfield        cn/Hanabi/value/Value.mode:Ljava/util/ArrayList;
        //   426: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   429: astore          8
        //   431: aload           8
        //   433: invokeinterface java/util/Iterator.hasNext:()Z
        //   438: ifeq            682
        //   441: aload           8
        //   443: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   448: checkcast       Ljava/lang/String;
        //   451: astore          9
        //   453: aload           9
        //   455: aload_0        
        //   456: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   459: getfield        cn/Hanabi/value/Value.mode:Ljava/util/ArrayList;
        //   462: aload_0        
        //   463: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   466: invokevirtual   cn/Hanabi/value/Value.getCurrentMode:()I
        //   469: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   472: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   475: ifeq            483
        //   478: goto            431
        //   481: nop            
        //   482: athrow         
        //   483: fload           7
        //   485: ldc             20.0
        //   487: fadd           
        //   488: fstore          7
        //   490: fload           6
        //   492: ldc             20.0
        //   494: fadd           
        //   495: fstore          6
        //   497: aload_0        
        //   498: getfield        ClassSub/Class137.ani:D
        //   501: fload           6
        //   503: iload_2        
        //   504: i2f            
        //   505: fsub           
        //   506: ldc             30.0
        //   508: fsub           
        //   509: f2d            
        //   510: dcmpl          
        //   511: ifle            677
        //   514: ldc             0
        //   516: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //   519: ifeq            653
        //   522: aload_0        
        //   523: iload_3        
        //   524: iload           4
        //   526: iload_1        
        //   527: ldc             80
        //   529: isub           
        //   530: i2d            
        //   531: fload           6
        //   533: f2d            
        //   534: iload_1        
        //   535: ldc             20
        //   537: isub           
        //   538: i2d            
        //   539: fload           6
        //   541: ldc             20.0
        //   543: fadd           
        //   544: f2d            
        //   545: invokespecial   ClassSub/Class137.isHovering:(IIDDDD)Z
        //   548: ifeq            598
        //   551: aload_0        
        //   552: ldc             0
        //   554: putfield        ClassSub/Class137.isSelectingMode:Z
        //   557: aload_0        
        //   558: ldc             0
        //   560: putfield        ClassSub/Class137.readySelect:Z
        //   563: new             LClassSub/Class76;
        //   566: dup            
        //   567: aload_0        
        //   568: invokespecial   ClassSub/Class76.<init>:(LClassSub/Class137;)V
        //   571: invokevirtual   ClassSub/Class76.start:()V
        //   574: aload_0        
        //   575: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   578: aload_0        
        //   579: getfield        ClassSub/Class137.value:Lcn/Hanabi/value/Value;
        //   582: getfield        cn/Hanabi/value/Value.mode:Ljava/util/ArrayList;
        //   585: aload           9
        //   587: invokevirtual   java/util/ArrayList.indexOf:(Ljava/lang/Object;)I
        //   590: invokevirtual   cn/Hanabi/value/Value.setCurrentMode:(I)V
        //   593: goto            653
        //   596: nop            
        //   597: athrow         
        //   598: getstatic       ClassSub/Class77.modeValueMap:Ljava/util/Map;
        //   601: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   606: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   611: astore          10
        //   613: aload           10
        //   615: invokeinterface java/util/Iterator.hasNext:()Z
        //   620: ifeq            653
        //   623: aload           10
        //   625: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   630: checkcast       LClassSub/Class137;
        //   633: astore          11
        //   635: new             LClassSub/Class196;
        //   638: dup            
        //   639: aload_0        
        //   640: aload           11
        //   642: invokespecial   ClassSub/Class196.<init>:(LClassSub/Class137;LClassSub/Class137;)V
        //   645: invokevirtual   ClassSub/Class196.start:()V
        //   648: goto            613
        //   651: nop            
        //   652: athrow         
        //   653: getstatic       cn/Hanabi/Hanabi.INSTANCE:Lcn/Hanabi/Hanabi;
        //   656: getfield        cn/Hanabi/Hanabi.fontManager:Lcn/Hanabi/utils/fontmanager/FontManager;
        //   659: getfield        cn/Hanabi/utils/fontmanager/FontManager.usans15:Lcn/Hanabi/utils/fontmanager/UnicodeFontRenderer;
        //   662: aload           9
        //   664: iload_1        
        //   665: ldc             75
        //   667: isub           
        //   668: i2f            
        //   669: fload           7
        //   671: ldc             -1
        //   673: invokevirtual   cn/Hanabi/utils/fontmanager/UnicodeFontRenderer.drawString:(Ljava/lang/String;FFI)I
        //   676: pop            
        //   677: goto            431
        //   680: nop            
        //   681: athrow         
        //   682: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean isHovering(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    static boolean access$002(final Class137 class137, final boolean isSelectingMode) {
        return class137.isSelectingMode = isSelectingMode;
    }
    
    static boolean access$102(final Class137 class137, final boolean readySelect) {
        return class137.readySelect = readySelect;
    }
}
