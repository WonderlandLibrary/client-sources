package md;

import java.util.concurrent.*;
import com.sun.jna.z.a.f.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import cpw.mods.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class e extends c
{
    int n;
    int o;
    CopyOnWriteArrayList<h> p;
    InventoryPlayer q;
    m r;
    boolean s;
    m t;
    t u;
    com.sun.jna.z.a.e.a.a.a.a.i v;
    com.sun.jna.z.a.e.a.a.a.a.i w;
    com.sun.jna.z.a.e.a.a.a.a.i x;
    public static int y;
    public static boolean z;
    public static int A;
    public static int B;
    public static int C;
    public static boolean D;
    public static boolean E;
    public static int F;
    public static boolean G;
    public static boolean H;
    public static int I;
    public static boolean J;
    public static boolean K;
    public static int L;
    public static boolean M;
    public static int N;
    public static boolean O;
    public static boolean P;
    public static boolean Q;
    private static final String[] R;
    
    public e() {
        final int q = e.Q ? 1 : 0;
        final String[] a = e.R;
        super(a[2], com.sun.jna.z.a.d.b.Utility, 0);
        final int a2 = q;
        this.n = 0;
        this.p = new CopyOnWriteArrayList<h>();
        this.r = new m();
        this.t = new m();
        this.u = new com.sun.jna.z.a.e.a.a.a.a.a.i(a[5], 100.0, 0.0, 200.0, 1);
        this.v = new d(a[3], true);
        this.w = new d(a[1]);
        this.x = new d(a[7], true);
        if (a2 != 0) {
            int a3 = e.y;
            e.y = ++a3;
        }
    }
    
    @Override
    public void a(final TickEvent$PlayerTickEvent a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_2        /* a */
        //     4: aload_0         /* a */
        //     5: getfield        md/e.t:Lcom/sun/jna/z/a/f/m;
        //     8: invokevirtual   com/sun/jna/z/a/f/m.d:()J
        //    11: ldc2_w          50
        //    14: lcmp           
        //    15: iload_2         /* a */
        //    16: ifne            40
        //    19: ifle            780
        //    22: goto            26
        //    25: athrow         
        //    26: aload_0         /* a */
        //    27: getfield        md/e.t:Lcom/sun/jna/z/a/f/m;
        //    30: invokevirtual   com/sun/jna/z/a/f/m.c:()V
        //    33: aload_0         /* a */
        //    34: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //    37: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.isEmpty:()Z
        //    40: iload_2         /* a */
        //    41: ifne            121
        //    44: ifne            117
        //    47: goto            51
        //    50: athrow         
        //    51: aload_0         /* a */
        //    52: getfield        md/e.r:Lcom/sun/jna/z/a/f/m;
        //    55: aload_0         /* a */
        //    56: getfield        md/e.u:Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //    59: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:()D
        //    64: d2l            
        //    65: invokevirtual   com/sun/jna/z/a/f/m.a:(J)Z
        //    68: iload_2         /* a */
        //    69: ifne            175
        //    72: goto            76
        //    75: athrow         
        //    76: ifeq            174
        //    79: goto            83
        //    82: athrow         
        //    83: aload_0         /* a */
        //    84: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //    87: iconst_0       
        //    88: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.get:(I)Ljava/lang/Object;
        //    91: checkcast       Lcom/sun/jna/z/a/d/h;
        //    94: astore_3        /* a */
        //    95: aload_0         /* a */
        //    96: aload_3         /* a */
        //    97: invokevirtual   md/e.a:(Lcom/sun/jna/z/a/d/h;)V
        //   100: aload_0         /* a */
        //   101: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   104: aload_3         /* a */
        //   105: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.remove:(Ljava/lang/Object;)Z
        //   108: pop            
        //   109: aload_0         /* a */
        //   110: getfield        md/e.r:Lcom/sun/jna/z/a/f/m;
        //   113: invokevirtual   com/sun/jna/z/a/f/m.c:()V
        //   116: return         
        //   117: aload_0         /* a */
        //   118: getfield        md/e.s:Z
        //   121: iload_2         /* a */
        //   122: ifne            175
        //   125: ifeq            174
        //   128: goto            132
        //   131: athrow         
        //   132: aload_0         /* a */
        //   133: getfield        md/e.r:Lcom/sun/jna/z/a/f/m;
        //   136: aload_0         /* a */
        //   137: getfield        md/e.u:Lcom/sun/jna/z/a/e/a/a/a/a/t;
        //   140: invokeinterface com/sun/jna/z/a/e/a/a/a/a/t.a:()D
        //   145: d2l            
        //   146: invokevirtual   com/sun/jna/z/a/f/m.a:(J)Z
        //   149: iload_2         /* a */
        //   150: ifne            175
        //   153: goto            157
        //   156: athrow         
        //   157: ifeq            174
        //   160: goto            164
        //   163: athrow         
        //   164: goto            168
        //   167: athrow         
        //   168: aload_0         /* a */
        //   169: iconst_0       
        //   170: invokevirtual   md/e.a:(Z)V
        //   173: return         
        //   174: iconst_0       
        //   175: istore_3        /* a */
        //   176: iload_3         /* a */
        //   177: bipush          10
        //   179: if_icmpge       744
        //   182: iload_3         /* a */
        //   183: iload_2         /* a */
        //   184: ifne            759
        //   187: bipush          9
        //   189: if_icmpne       231
        //   192: goto            196
        //   195: athrow         
        //   196: goto            200
        //   199: athrow         
        //   200: aload_0         /* a */
        //   201: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   204: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.isEmpty:()Z
        //   207: iload_2         /* a */
        //   208: ifne            759
        //   211: ifeq            744
        //   214: goto            218
        //   217: athrow         
        //   218: goto            222
        //   221: athrow         
        //   222: aload_0         /* a */
        //   223: iconst_0       
        //   224: invokevirtual   md/e.a:(Z)V
        //   227: goto            744
        //   230: athrow         
        //   231: aload_0         /* a */
        //   232: getfield        md/e.q:Lnet/minecraft/entity/player/InventoryPlayer;
        //   235: getfield        net/minecraft/entity/player/InventoryPlayer.field_70462_a:[Lnet/minecraft/item/ItemStack;
        //   238: iload_3         /* a */
        //   239: aaload         
        //   240: astore          a
        //   242: aload           a
        //   244: iload_2         /* a */
        //   245: ifne            261
        //   248: ifnonnull       259
        //   251: goto            255
        //   254: athrow         
        //   255: goto            737
        //   258: athrow         
        //   259: aload           a
        //   261: invokevirtual   net/minecraft/item/ItemStack.func_77973_b:()Lnet/minecraft/item/Item;
        //   264: astore          a
        //   266: aload           a
        //   268: iload_2         /* a */
        //   269: ifne            576
        //   272: ifnull          566
        //   275: goto            279
        //   278: athrow         
        //   279: aload           a
        //   281: iload_2         /* a */
        //   282: ifne            576
        //   285: instanceof      Lnet/minecraft/item/ItemPotion;
        //   288: ifeq            566
        //   291: goto            295
        //   294: athrow         
        //   295: goto            299
        //   298: athrow         
        //   299: aload_0         /* a */
        //   300: getfield        md/e.v:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   303: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   308: ifeq            566
        //   311: goto            315
        //   314: athrow         
        //   315: aload           a
        //   317: checkcast       Lnet/minecraft/item/ItemPotion;
        //   320: astore          a
        //   322: aload           a
        //   324: aload           a
        //   326: invokevirtual   net/minecraft/item/ItemPotion.func_77832_l:(Lnet/minecraft/item/ItemStack;)Ljava/util/List;
        //   329: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   332: getstatic       md/e.R:[Ljava/lang/String;
        //   335: astore          a
        //   337: aload           a
        //   339: iconst_0       
        //   340: aaload         
        //   341: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   344: iload_2         /* a */
        //   345: ifne            382
        //   348: ifeq            562
        //   351: goto            355
        //   354: athrow         
        //   355: aload           a
        //   357: aload           a
        //   359: invokevirtual   net/minecraft/item/ItemPotion.func_77832_l:(Lnet/minecraft/item/ItemStack;)Ljava/util/List;
        //   362: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   365: getstatic       md/e.R:[Ljava/lang/String;
        //   368: astore          a
        //   370: aload           a
        //   372: bipush          6
        //   374: aaload         
        //   375: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   378: goto            382
        //   381: athrow         
        //   382: ifeq            562
        //   385: goto            389
        //   388: athrow         
        //   389: iconst_0       
        //   390: istore          a
        //   392: aload           a
        //   394: aload           a
        //   396: invokevirtual   net/minecraft/item/ItemPotion.func_77832_l:(Lnet/minecraft/item/ItemStack;)Ljava/util/List;
        //   399: iconst_0       
        //   400: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   405: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   408: getstatic       md/e.R:[Ljava/lang/String;
        //   411: astore          a
        //   413: aload           a
        //   415: iconst_4       
        //   416: aaload         
        //   417: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   420: iload_2         /* a */
        //   421: ifne            476
        //   424: ifeq            482
        //   427: goto            431
        //   430: athrow         
        //   431: iload           a
        //   433: iconst_4       
        //   434: aload           a
        //   436: aload           a
        //   438: invokevirtual   net/minecraft/item/ItemPotion.func_77832_l:(Lnet/minecraft/item/ItemStack;)Ljava/util/List;
        //   441: iconst_0       
        //   442: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   447: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   450: getstatic       md/e.R:[Ljava/lang/String;
        //   453: iconst_4       
        //   454: aaload         
        //   455: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   458: iconst_1       
        //   459: aaload         
        //   460: ldc             ","
        //   462: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   465: iconst_0       
        //   466: aaload         
        //   467: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   470: imul           
        //   471: iadd           
        //   472: goto            476
        //   475: athrow         
        //   476: istore          a
        //   478: iload_2         /* a */
        //   479: ifeq            489
        //   482: iinc            a, 4
        //   485: goto            489
        //   488: athrow         
        //   489: aload_0         /* a */
        //   490: getfield        md/e.n:I
        //   493: iload           a
        //   495: iadd           
        //   496: i2d            
        //   497: aload_0         /* a */
        //   498: invokevirtual   md/e.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   501: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_110143_aJ:()F
        //   504: f2d            
        //   505: invokestatic    java/lang/Math.floor:(D)D
        //   508: dadd           
        //   509: ldc2_w          20.0
        //   512: dcmpl          
        //   513: iload_2         /* a */
        //   514: ifne            556
        //   517: ifle            528
        //   520: goto            524
        //   523: athrow         
        //   524: goto            737
        //   527: athrow         
        //   528: aload_0         /* a */
        //   529: dup            
        //   530: getfield        md/e.n:I
        //   533: iload           a
        //   535: iadd           
        //   536: putfield        md/e.n:I
        //   539: aload_0         /* a */
        //   540: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   543: new             Lcom/sun/jna/z/a/d/h;
        //   546: dup            
        //   547: iload_3         /* a */
        //   548: aload           a
        //   550: invokespecial   com/sun/jna/z/a/d/h.<init>:(ILnet/minecraft/item/ItemStack;)V
        //   553: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.add:(Ljava/lang/Object;)Z
        //   556: pop            
        //   557: aload_0         /* a */
        //   558: iconst_1       
        //   559: putfield        md/e.s:Z
        //   562: iload_2         /* a */
        //   563: ifeq            700
        //   566: iload_2         /* a */
        //   567: ifne            700
        //   570: aload           a
        //   572: goto            576
        //   575: athrow         
        //   576: ifnull          700
        //   579: aload           a
        //   581: instanceof      Lnet/minecraft/item/ItemSoup;
        //   584: iload_2         /* a */
        //   585: ifne            709
        //   588: ifeq            700
        //   591: goto            595
        //   594: athrow         
        //   595: goto            599
        //   598: athrow         
        //   599: aload_0         /* a */
        //   600: getfield        md/e.w:Lcom/sun/jna/z/a/e/a/a/a/a/i;
        //   603: invokeinterface com/sun/jna/z/a/e/a/a/a/a/i.a:()Z
        //   608: iload_2         /* a */
        //   609: ifne            709
        //   612: ifeq            700
        //   615: goto            619
        //   618: athrow         
        //   619: goto            623
        //   622: athrow         
        //   623: aload_0         /* a */
        //   624: getfield        md/e.n:I
        //   627: bipush          8
        //   629: iadd           
        //   630: i2d            
        //   631: aload_0         /* a */
        //   632: invokevirtual   md/e.c:()Lnet/minecraft/client/entity/EntityPlayerSP;
        //   635: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_110143_aJ:()F
        //   638: f2d            
        //   639: invokestatic    java/lang/Math.floor:(D)D
        //   642: dadd           
        //   643: ldc2_w          20.0
        //   646: dcmpg          
        //   647: iload_2         /* a */
        //   648: ifne            709
        //   651: ifgt            700
        //   654: goto            658
        //   657: athrow         
        //   658: goto            662
        //   661: athrow         
        //   662: aload_0         /* a */
        //   663: dup            
        //   664: getfield        md/e.n:I
        //   667: bipush          8
        //   669: iadd           
        //   670: putfield        md/e.n:I
        //   673: aload_0         /* a */
        //   674: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   677: new             Lcom/sun/jna/z/a/d/h;
        //   680: dup            
        //   681: iload_3         /* a */
        //   682: aload           a
        //   684: invokespecial   com/sun/jna/z/a/d/h.<init>:(ILnet/minecraft/item/ItemStack;)V
        //   687: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.add:(Ljava/lang/Object;)Z
        //   690: pop            
        //   691: aload_0         /* a */
        //   692: iconst_1       
        //   693: putfield        md/e.s:Z
        //   696: goto            700
        //   699: athrow         
        //   700: iload_2         /* a */
        //   701: ifne            740
        //   704: iload_3         /* a */
        //   705: goto            709
        //   708: athrow         
        //   709: bipush          8
        //   711: if_icmpne       737
        //   714: aload_0         /* a */
        //   715: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   718: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.isEmpty:()Z
        //   721: ifeq            737
        //   724: goto            728
        //   727: athrow         
        //   728: aload_0         /* a */
        //   729: iconst_0       
        //   730: invokevirtual   md/e.a:(Z)V
        //   733: goto            737
        //   736: athrow         
        //   737: iinc            a, 1
        //   740: iload_2         /* a */
        //   741: ifeq            176
        //   744: aload_0         /* a */
        //   745: iload_2         /* a */
        //   746: ifne            763
        //   749: getfield        md/e.p:Ljava/util/concurrent/CopyOnWriteArrayList;
        //   752: invokevirtual   java/util/concurrent/CopyOnWriteArrayList.isEmpty:()Z
        //   755: goto            759
        //   758: athrow         
        //   759: ifne            771
        //   762: aload_0         /* a */
        //   763: iconst_1       
        //   764: putfield        md/e.s:Z
        //   767: goto            771
        //   770: athrow         
        //   771: goto            780
        //   774: astore_3        /* a */
        //   775: aload_0         /* a */
        //   776: iconst_0       
        //   777: invokevirtual   md/e.a:(Z)V
        //   780: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  51     79     82     83     Ljava/lang/Exception;
        //  44     72     75     76     Ljava/lang/Exception;
        //  40     47     50     51     Ljava/lang/Exception;
        //  4      22     25     26     Ljava/lang/Exception;
        //  33     116    774    780    Ljava/lang/Exception;
        //  132    160    163    164    Ljava/lang/Exception;
        //  125    153    156    157    Ljava/lang/Exception;
        //  121    128    131    132    Ljava/lang/Exception;
        //  117    164    167    168    Ljava/lang/Exception;
        //  117    173    774    780    Ljava/lang/Exception;
        //  744    755    758    759    Ljava/lang/Exception;
        //  182    192    195    196    Ljava/lang/Exception;
        //  744    767    770    771    Ljava/lang/Exception;
        //  714    733    736    737    Ljava/lang/Exception;
        //  700    705    708    709    Ljava/lang/Exception;
        //  700    724    727    728    Ljava/lang/Exception;
        //  623    654    657    658    Ljava/lang/Exception;
        //  623    696    699    700    Ljava/lang/Exception;
        //  599    615    618    619    Ljava/lang/Exception;
        //  599    658    661    662    Ljava/lang/Exception;
        //  579    591    594    595    Ljava/lang/Exception;
        //  579    619    622    623    Ljava/lang/Exception;
        //  566    572    575    576    Ljava/lang/Exception;
        //  566    595    598    599    Ljava/lang/Exception;
        //  489    520    523    524    Ljava/lang/Exception;
        //  489    527    527    528    Ljava/lang/Exception;
        //  348    378    381    382    Ljava/lang/Exception;
        //  322    351    354    355    Ljava/lang/Exception;
        //  322    385    388    389    Ljava/lang/Exception;
        //  279    291    294    295    Ljava/lang/Exception;
        //  279    311    314    315    Ljava/lang/Exception;
        //  266    275    278    279    Ljava/lang/Exception;
        //  266    295    298    299    Ljava/lang/Exception;
        //  242    251    254    255    Ljava/lang/Exception;
        //  242    258    258    259    Ljava/lang/Exception;
        //  200    214    217    218    Ljava/lang/Exception;
        //  200    230    230    231    Ljava/lang/Exception;
        //  182    218    221    222    Ljava/lang/Exception;
        //  176    196    199    200    Ljava/lang/Exception;
        //  478    485    488    489    Ljava/lang/Exception;
        //  424    472    475    476    Ljava/lang/Exception;
        //  392    427    430    431    Ljava/lang/Exception;
        //  174    771    774    780    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0051:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2596)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void a(final h a) {
        final int q = e.Q ? 1 : 0;
        this.c().field_71071_by.field_70461_c = a.a;
        final int a2 = q;
        this.b().field_71442_b.func_78769_a((EntityPlayer)this.c(), (World)this.b().field_71441_e, a.b);
        final boolean b = a.b.func_77973_b() instanceof ItemSoup;
        Label_0089: {
            e e = null;
            Label_0078: {
                if (a2 == 0) {
                    if (!b) {
                        break Label_0089;
                    }
                    e = this;
                    if (a2 != 0) {
                        break Label_0078;
                    }
                    this.x.a();
                }
                if (!b) {
                    break Label_0089;
                }
                e = this;
            }
            e.b().field_71439_g.func_71040_bB(true);
        }
        if (e.y != 0) {
            e.Q = (a2 == 0);
        }
    }
    
    @Override
    public void e() {
        this.q = this.c().field_71071_by;
        this.o = this.q.field_70461_c;
    }
    
    @Override
    public void f() {
        final int a = e.Q ? 1 : 0;
        e e = this;
        if (a == 0) {
            if (this.s) {
                this.c().field_71071_by.field_70461_c = this.o;
            }
            this.p.clear();
            this.n = 0;
            e = this;
        }
        e.s = false;
    }
    
    @Override
    public k[] k() {
        return new k[] { this.u, this.v, this.x, this.w };
    }
    
    static {
        final String[] r = new String[8];
        int n = 0;
        String s;
        int n2 = (s = "o\u00cd\u00eeV\u000f<k\u001c\u00c9\u00f0B\u0019\u0004o\u00d2\u00f7G\bh\u00d5\u00f0X\u000b$>H\u0004l\u00d2\u00f6D\u0003\u001c\u00c5¢\u0005x\u00d8\u00eeV\u0005").length();
        int n3 = 12;
        int n4 = -1;
    Label_0023:
        while (true) {
            while (true) {
                ++n4;
                final String s2 = s;
                final int n5 = n4;
                String s3 = s2.substring(n5, n5 + n3);
                int n6 = -1;
                while (true) {
                    final char[] charArray = s3.toCharArray();
                    int length;
                    int n8;
                    final int n7 = n8 = (length = charArray.length);
                    int n9 = 0;
                    while (true) {
                        Label_0246: {
                            if (n7 > 1) {
                                break Label_0246;
                            }
                            length = (n8 = n9);
                            do {
                                final char c = charArray[n8];
                                char c2 = '\0';
                                switch (n9 % 7) {
                                    case 0: {
                                        c2 = '<';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '½';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u0082';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '7';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '|';
                                        break;
                                    }
                                    case 5: {
                                        c2 = 'T';
                                        break;
                                    }
                                    default: {
                                        c2 = 'Q';
                                        break;
                                    }
                                }
                                charArray[length] = (char)(c ^ c2);
                                ++n9;
                            } while (n7 == 0);
                        }
                        if (n7 > n9) {
                            continue;
                        }
                        break;
                    }
                    final String intern = new String(charArray).intern();
                    switch (n6) {
                        default: {
                            r[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "T\u00d8\u00e3[\u000bh\u00d5\u00f0X\u000bt3S\u00ca\u00eeD").length();
                            n3 = 4;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            r[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0023;
                        }
                    }
                    ++n4;
                    final String s4 = s;
                    final int n10 = n4;
                    s3 = s4.substring(n10, n10 + n3);
                    n6 = 0;
                }
            }
            break;
        }
        R = r;
    }
}
