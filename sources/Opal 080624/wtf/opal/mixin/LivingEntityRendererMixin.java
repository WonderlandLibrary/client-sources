package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1921;
import net.minecraft.class_3545;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5617;
import net.minecraft.class_583;
import net.minecraft.class_897;
import net.minecraft.class_922;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.bc;
import wtf.opal.d1;
import wtf.opal.jy;
import wtf.opal.l;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin(value = {class_922.class}, priority = 500)
public abstract class LivingEntityRendererMixin<T extends class_1309, M extends class_583<T>> extends class_897<T> implements class_3883<T, M> {
  @Shadow
  protected M field_4737;
  
  @Shadow
  @Final
  protected List<class_3887<T, M>> field_4738;
  
  @Unique
  private static final Map<class_1309, class_3545<Float, Float>> ROTATION_MAP;
  
  private static final long a = on.a(8140633399790655657L, 8285597375409775161L, MethodHandles.lookup().lookupClass()).a(241444170744604L);
  
  protected LivingEntityRendererMixin(class_5617.class_5618 paramclass_5618) {
    super(paramclass_5618);
  }
  
  @Shadow
  protected abstract float method_4044(T paramT, float paramFloat);
  
  @Shadow
  protected abstract float method_4045(T paramT, float paramFloat);
  
  @Shadow
  protected abstract void method_4058(T paramT, class_4587 paramclass_4587, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  @Shadow
  protected abstract void method_4042(T paramT, class_4587 paramclass_4587, float paramFloat);
  
  @Shadow
  protected abstract boolean method_4056(T paramT);
  
  @Shadow
  @Nullable
  protected abstract class_1921 method_24302(T paramT, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  @Shadow
  protected abstract float method_23185(T paramT, float paramFloat);
  
  @Overwrite
  public void method_4054(T paramT, float paramFloat1, float paramFloat2, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/LivingEntityRendererMixin.a : J
    //   3: ldc2_w 87755594228534
    //   6: lxor
    //   7: lstore #7
    //   9: lload #7
    //   11: dup2
    //   12: ldc2_w 34166548295397
    //   15: lxor
    //   16: lstore #9
    //   18: pop2
    //   19: getstatic wtf/opal/mixin/LivingEntityRendererMixin.ROTATION_MAP : Ljava/util/Map;
    //   22: invokeinterface clear : ()V
    //   27: iconst_0
    //   28: anewarray java/lang/Object
    //   31: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   34: iconst_0
    //   35: anewarray java/lang/Object
    //   38: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual E : ([Ljava/lang/Object;)Lnet/minecraft/class_3545;
    //   48: astore #11
    //   50: getstatic wtf/opal/mixin/LivingEntityRendererMixin.ROTATION_MAP : Ljava/util/Map;
    //   53: aload_1
    //   54: aconst_null
    //   55: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   60: pop
    //   61: aload_1
    //   62: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   65: getfield field_1724 : Lnet/minecraft/class_746;
    //   68: if_acmpne -> 142
    //   71: iconst_0
    //   72: anewarray java/lang/Object
    //   75: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   78: iconst_0
    //   79: anewarray java/lang/Object
    //   82: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   85: iconst_0
    //   86: anewarray java/lang/Object
    //   89: invokevirtual U : ([Ljava/lang/Object;)Z
    //   92: ifeq -> 142
    //   95: goto -> 102
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: getstatic wtf/opal/mixin/LivingEntityRendererMixin.ROTATION_MAP : Ljava/util/Map;
    //   105: aload_1
    //   106: new net/minecraft/class_3545
    //   109: dup
    //   110: aload #11
    //   112: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   115: checkcast java/lang/Float
    //   118: aload #11
    //   120: invokevirtual method_15441 : ()Ljava/lang/Object;
    //   123: checkcast java/lang/Float
    //   126: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   129: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   134: pop
    //   135: goto -> 142
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: new wtf/opal/lm
    //   145: dup
    //   146: aload_1
    //   147: iconst_1
    //   148: invokespecial <init> : (Lnet/minecraft/class_1309;Z)V
    //   151: astore #12
    //   153: iconst_0
    //   154: anewarray java/lang/Object
    //   157: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   160: iconst_0
    //   161: anewarray java/lang/Object
    //   164: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/dm;
    //   167: lload #9
    //   169: aload #12
    //   171: iconst_2
    //   172: anewarray java/lang/Object
    //   175: dup_x1
    //   176: swap
    //   177: iconst_1
    //   178: swap
    //   179: aastore
    //   180: dup_x2
    //   181: dup_x2
    //   182: pop
    //   183: invokestatic valueOf : (J)Ljava/lang/Long;
    //   186: iconst_0
    //   187: swap
    //   188: aastore
    //   189: invokevirtual N : ([Ljava/lang/Object;)V
    //   192: aload #4
    //   194: invokevirtual method_22903 : ()V
    //   197: aload_0
    //   198: getfield field_4737 : Lnet/minecraft/class_583;
    //   201: aload_0
    //   202: aload_1
    //   203: fload_3
    //   204: invokevirtual method_4044 : (Lnet/minecraft/class_1309;F)F
    //   207: putfield field_3447 : F
    //   210: aload_0
    //   211: getfield field_4737 : Lnet/minecraft/class_583;
    //   214: aload_1
    //   215: invokevirtual method_5765 : ()Z
    //   218: putfield field_3449 : Z
    //   221: aload_0
    //   222: getfield field_4737 : Lnet/minecraft/class_583;
    //   225: aload_1
    //   226: invokevirtual method_6109 : ()Z
    //   229: putfield field_3448 : Z
    //   232: fload_3
    //   233: aload_1
    //   234: getfield field_6220 : F
    //   237: aload_1
    //   238: getfield field_6283 : F
    //   241: invokestatic method_17821 : (FFF)F
    //   244: fstore #16
    //   246: fload_3
    //   247: aload_1
    //   248: getfield field_6259 : F
    //   251: aload_1
    //   252: getfield field_6241 : F
    //   255: invokestatic method_17821 : (FFF)F
    //   258: fstore #17
    //   260: fload #17
    //   262: fload #16
    //   264: fsub
    //   265: fstore #18
    //   267: aload_1
    //   268: invokevirtual method_5765 : ()Z
    //   271: ifeq -> 383
    //   274: aload_1
    //   275: invokevirtual method_5854 : ()Lnet/minecraft/class_1297;
    //   278: dup
    //   279: astore #15
    //   281: instanceof net/minecraft/class_1309
    //   284: ifeq -> 383
    //   287: aload #15
    //   289: checkcast net/minecraft/class_1309
    //   292: astore #19
    //   294: fload_3
    //   295: aload #19
    //   297: getfield field_6220 : F
    //   300: aload #19
    //   302: getfield field_6283 : F
    //   305: invokestatic method_17821 : (FFF)F
    //   308: fstore #16
    //   310: fload #17
    //   312: fload #16
    //   314: fsub
    //   315: fstore #18
    //   317: fload #18
    //   319: invokestatic method_15393 : (F)F
    //   322: fstore #20
    //   324: fload #20
    //   326: ldc -85.0
    //   328: fcmpg
    //   329: ifge -> 336
    //   332: ldc -85.0
    //   334: fstore #20
    //   336: fload #20
    //   338: ldc 85.0
    //   340: fcmpl
    //   341: iflt -> 348
    //   344: ldc 85.0
    //   346: fstore #20
    //   348: fload #17
    //   350: fload #20
    //   352: fsub
    //   353: fstore #16
    //   355: fload #20
    //   357: fload #20
    //   359: fmul
    //   360: ldc 2500.0
    //   362: fcmpl
    //   363: ifle -> 376
    //   366: fload #16
    //   368: fload #20
    //   370: ldc 0.2
    //   372: fmul
    //   373: fadd
    //   374: fstore #16
    //   376: fload #17
    //   378: fload #16
    //   380: fsub
    //   381: fstore #18
    //   383: fload_3
    //   384: aload_1
    //   385: getfield field_6004 : F
    //   388: aload_1
    //   389: invokevirtual method_36455 : ()F
    //   392: invokestatic method_16439 : (FFF)F
    //   395: fstore #19
    //   397: getstatic wtf/opal/mixin/LivingEntityRendererMixin.ROTATION_MAP : Ljava/util/Map;
    //   400: aload_1
    //   401: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   406: checkcast net/minecraft/class_3545
    //   409: astore #20
    //   411: aload #20
    //   413: ifnull -> 444
    //   416: fload_3
    //   417: aload #20
    //   419: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   422: checkcast java/lang/Float
    //   425: invokevirtual floatValue : ()F
    //   428: aload #20
    //   430: invokevirtual method_15441 : ()Ljava/lang/Object;
    //   433: checkcast java/lang/Float
    //   436: invokevirtual floatValue : ()F
    //   439: invokestatic method_16439 : (FFF)F
    //   442: fstore #19
    //   444: aload_1
    //   445: invokestatic method_38563 : (Lnet/minecraft/class_1309;)Z
    //   448: ifeq -> 465
    //   451: fload #19
    //   453: ldc -1.0
    //   455: fmul
    //   456: fstore #19
    //   458: fload #18
    //   460: ldc -1.0
    //   462: fmul
    //   463: fstore #18
    //   465: fload #18
    //   467: invokestatic method_15393 : (F)F
    //   470: fstore #18
    //   472: aload_1
    //   473: getstatic net/minecraft/class_4050.field_18078 : Lnet/minecraft/class_4050;
    //   476: invokevirtual method_41328 : (Lnet/minecraft/class_4050;)Z
    //   479: ifeq -> 530
    //   482: aload_1
    //   483: invokevirtual method_18401 : ()Lnet/minecraft/class_2350;
    //   486: dup
    //   487: astore #14
    //   489: ifnull -> 530
    //   492: aload_1
    //   493: getstatic net/minecraft/class_4050.field_18076 : Lnet/minecraft/class_4050;
    //   496: invokevirtual method_18381 : (Lnet/minecraft/class_4050;)F
    //   499: ldc 0.1
    //   501: fsub
    //   502: fstore #13
    //   504: aload #4
    //   506: aload #14
    //   508: invokevirtual method_10148 : ()I
    //   511: ineg
    //   512: i2f
    //   513: fload #13
    //   515: fmul
    //   516: fconst_0
    //   517: aload #14
    //   519: invokevirtual method_10165 : ()I
    //   522: ineg
    //   523: i2f
    //   524: fload #13
    //   526: fmul
    //   527: invokevirtual method_46416 : (FFF)V
    //   530: aload_1
    //   531: invokevirtual method_55693 : ()F
    //   534: fstore #21
    //   536: aload #4
    //   538: fload #21
    //   540: fload #21
    //   542: fload #21
    //   544: invokevirtual method_22905 : (FFF)V
    //   547: aload_0
    //   548: aload_1
    //   549: fload_3
    //   550: invokevirtual method_4045 : (Lnet/minecraft/class_1309;F)F
    //   553: fstore #13
    //   555: aload_0
    //   556: aload_1
    //   557: aload #4
    //   559: fload #13
    //   561: fload #16
    //   563: fload_3
    //   564: fload #21
    //   566: invokevirtual method_4058 : (Lnet/minecraft/class_1309;Lnet/minecraft/class_4587;FFFF)V
    //   569: aload #4
    //   571: ldc -1.0
    //   573: ldc -1.0
    //   575: fconst_1
    //   576: invokevirtual method_22905 : (FFF)V
    //   579: aload_0
    //   580: aload_1
    //   581: aload #4
    //   583: fload_3
    //   584: invokevirtual method_4042 : (Lnet/minecraft/class_1309;Lnet/minecraft/class_4587;F)V
    //   587: aload #4
    //   589: fconst_0
    //   590: ldc_w -1.501
    //   593: fconst_0
    //   594: invokevirtual method_46416 : (FFF)V
    //   597: fconst_0
    //   598: fstore #22
    //   600: fconst_0
    //   601: fstore #23
    //   603: aload_1
    //   604: invokevirtual method_5765 : ()Z
    //   607: ifne -> 669
    //   610: aload_1
    //   611: invokevirtual method_5805 : ()Z
    //   614: ifeq -> 669
    //   617: goto -> 624
    //   620: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   623: athrow
    //   624: aload_1
    //   625: getfield field_42108 : Lnet/minecraft/class_8080;
    //   628: fload_3
    //   629: invokevirtual method_48570 : (F)F
    //   632: fstore #22
    //   634: aload_1
    //   635: getfield field_42108 : Lnet/minecraft/class_8080;
    //   638: fload_3
    //   639: invokevirtual method_48572 : (F)F
    //   642: fstore #23
    //   644: aload_1
    //   645: invokevirtual method_6109 : ()Z
    //   648: ifeq -> 659
    //   651: fload #23
    //   653: ldc_w 3.0
    //   656: fmul
    //   657: fstore #23
    //   659: fload #22
    //   661: fconst_1
    //   662: fcmpl
    //   663: ifle -> 669
    //   666: fconst_1
    //   667: fstore #22
    //   669: aload_0
    //   670: getfield field_4737 : Lnet/minecraft/class_583;
    //   673: aload_1
    //   674: fload #23
    //   676: fload #22
    //   678: fload_3
    //   679: invokevirtual method_2816 : (Lnet/minecraft/class_1297;FFF)V
    //   682: aload_0
    //   683: getfield field_4737 : Lnet/minecraft/class_583;
    //   686: aload_1
    //   687: fload #23
    //   689: fload #22
    //   691: fload #13
    //   693: fload #18
    //   695: fload #19
    //   697: invokevirtual method_2819 : (Lnet/minecraft/class_1297;FFFFF)V
    //   700: invokestatic method_1551 : ()Lnet/minecraft/class_310;
    //   703: astore #24
    //   705: aload_0
    //   706: aload_1
    //   707: invokevirtual method_4056 : (Lnet/minecraft/class_1309;)Z
    //   710: istore #25
    //   712: iload #25
    //   714: ifne -> 744
    //   717: aload_1
    //   718: aload #24
    //   720: getfield field_1724 : Lnet/minecraft/class_746;
    //   723: invokevirtual method_5756 : (Lnet/minecraft/class_1657;)Z
    //   726: ifne -> 744
    //   729: goto -> 736
    //   732: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   735: athrow
    //   736: iconst_1
    //   737: goto -> 745
    //   740: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   743: athrow
    //   744: iconst_0
    //   745: istore #26
    //   747: aload #24
    //   749: aload_1
    //   750: invokevirtual method_27022 : (Lnet/minecraft/class_1297;)Z
    //   753: istore #27
    //   755: aload_0
    //   756: aload_1
    //   757: iload #25
    //   759: iload #26
    //   761: iload #27
    //   763: invokevirtual method_24302 : (Lnet/minecraft/class_1309;ZZZ)Lnet/minecraft/class_1921;
    //   766: astore #28
    //   768: aload #28
    //   770: ifnull -> 830
    //   773: aload #5
    //   775: aload #28
    //   777: invokeinterface getBuffer : (Lnet/minecraft/class_1921;)Lnet/minecraft/class_4588;
    //   782: astore #29
    //   784: aload_1
    //   785: aload_0
    //   786: aload_1
    //   787: fload_3
    //   788: invokevirtual method_23185 : (Lnet/minecraft/class_1309;F)F
    //   791: invokestatic method_23622 : (Lnet/minecraft/class_1309;F)I
    //   794: istore #30
    //   796: aload_0
    //   797: getfield field_4737 : Lnet/minecraft/class_583;
    //   800: aload #4
    //   802: aload #29
    //   804: iload #6
    //   806: iload #30
    //   808: fconst_1
    //   809: fconst_1
    //   810: fconst_1
    //   811: iload #26
    //   813: ifeq -> 826
    //   816: ldc_w 0.15
    //   819: goto -> 827
    //   822: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   825: athrow
    //   826: fconst_1
    //   827: invokevirtual method_2828 : (Lnet/minecraft/class_4587;Lnet/minecraft/class_4588;IIFFFF)V
    //   830: aload_1
    //   831: invokevirtual method_7325 : ()Z
    //   834: ifne -> 896
    //   837: aload_0
    //   838: getfield field_4738 : Ljava/util/List;
    //   841: invokeinterface iterator : ()Ljava/util/Iterator;
    //   846: astore #29
    //   848: aload #29
    //   850: invokeinterface hasNext : ()Z
    //   855: ifeq -> 896
    //   858: aload #29
    //   860: invokeinterface next : ()Ljava/lang/Object;
    //   865: checkcast net/minecraft/class_3887
    //   868: astore #30
    //   870: aload #30
    //   872: aload #4
    //   874: aload #5
    //   876: iload #6
    //   878: aload_1
    //   879: fload #23
    //   881: fload #22
    //   883: fload_3
    //   884: fload #13
    //   886: fload #18
    //   888: fload #19
    //   890: invokevirtual method_4199 : (Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;ILnet/minecraft/class_1297;FFFFFF)V
    //   893: goto -> 848
    //   896: aload #4
    //   898: invokevirtual method_22909 : ()V
    //   901: aload_0
    //   902: aload_1
    //   903: fload_2
    //   904: fload_3
    //   905: aload #4
    //   907: aload #5
    //   909: iload #6
    //   911: invokespecial method_3936 : (Lnet/minecraft/class_1297;FFLnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V
    //   914: new wtf/opal/lm
    //   917: dup
    //   918: aload_1
    //   919: iconst_0
    //   920: invokespecial <init> : (Lnet/minecraft/class_1309;Z)V
    //   923: astore #29
    //   925: iconst_0
    //   926: anewarray java/lang/Object
    //   929: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   932: iconst_0
    //   933: anewarray java/lang/Object
    //   936: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/dm;
    //   939: lload #9
    //   941: aload #29
    //   943: iconst_2
    //   944: anewarray java/lang/Object
    //   947: dup_x1
    //   948: swap
    //   949: iconst_1
    //   950: swap
    //   951: aastore
    //   952: dup_x2
    //   953: dup_x2
    //   954: pop
    //   955: invokestatic valueOf : (J)Ljava/lang/Long;
    //   958: iconst_0
    //   959: swap
    //   960: aastore
    //   961: invokevirtual N : ([Ljava/lang/Object;)V
    //   964: return
    // Exception table:
    //   from	to	target	type
    //   50	95	98	wtf/opal/x5
    //   71	135	138	wtf/opal/x5
    //   603	617	620	wtf/opal/x5
    //   712	729	732	wtf/opal/x5
    //   717	740	740	wtf/opal/x5
    //   796	822	822	wtf/opal/x5
  }
  
  @Inject(method = {"hasLabel(Lnet/minecraft/entity/LivingEntity;)Z"}, at = {@At("HEAD")}, cancellable = true)
  private void hookHasLabel(T paramT, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    long l1 = a ^ 0x6C95E7AE0779L;
    long l2 = (l1 ^ 0x2CDB2C221FCEL) >>> 16L;
    int i = (int)((l1 ^ 0x2CDB2C221FCEL) << 48L >>> 48L);
    l1 ^ 0x2CDB2C221FCEL;
    long l3 = l1 ^ 0x7A38C7A48C3CL;
    try {
      if (bc.L) {
        try {
          if (paramT instanceof net.minecraft.class_1657)
            try {
              (new Object[2])[1] = Integer.valueOf((char)i);
              new Object[2];
              if (((jy)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jy.class })).V(new Object[] { Long.valueOf(l2) }))
                try {
                  new Object[2];
                  if (!l.K(new Object[] { null, Long.valueOf(l3), paramT })) {
                    paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
                    return;
                  } 
                } catch (x5 x5) {
                  throw a(null);
                }  
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        } 
        return;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
  }
  
  static {
    ROTATION_MAP = new HashMap<>();
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\LivingEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */