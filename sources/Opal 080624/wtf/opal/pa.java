package wtf.opal;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import net.minecraft.class_1041;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.NativeResource;

public final class pa {
  private final Queue<Runnable> L = new LinkedList<>();
  
  private final List<NativeResource> u = new ArrayList<>();
  
  private final long V = NanoVGGL3.nvgCreate(3);
  
  private static boolean G;
  
  private static final long a = on.a(2513554244315934945L, 7074840824937933976L, MethodHandles.lookup().lookupClass()).a(158448159813845L);
  
  public void l(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Boolean
    //   17: invokevirtual booleanValue : ()Z
    //   20: istore_2
    //   21: pop
    //   22: getstatic wtf/opal/pa.a : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 19481599604834
    //   33: lxor
    //   34: lstore #5
    //   36: dup2
    //   37: ldc2_w 102995422019749
    //   40: lxor
    //   41: lstore #7
    //   43: pop2
    //   44: invokestatic d : ()Z
    //   47: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   50: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   53: astore #10
    //   55: aload #10
    //   57: invokevirtual method_4495 : ()D
    //   60: d2f
    //   61: fstore #11
    //   63: istore #9
    //   65: iload #9
    //   67: ifeq -> 127
    //   70: iload_2
    //   71: ifeq -> 106
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: lload #7
    //   83: iconst_1
    //   84: anewarray java/lang/Object
    //   87: dup_x2
    //   88: dup_x2
    //   89: pop
    //   90: invokestatic valueOf : (J)Ljava/lang/Long;
    //   93: iconst_0
    //   94: swap
    //   95: aastore
    //   96: invokestatic S : ([Ljava/lang/Object;)V
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: aload_0
    //   107: getfield V : J
    //   110: aload #10
    //   112: invokevirtual method_4480 : ()I
    //   115: i2f
    //   116: aload #10
    //   118: invokevirtual method_4507 : ()I
    //   121: i2f
    //   122: fload #11
    //   124: invokestatic nvgBeginFrame : (JFFF)V
    //   127: aload_0
    //   128: getfield V : J
    //   131: fload #11
    //   133: fload #11
    //   135: invokestatic nvgScale : (JFF)V
    //   138: aload_0
    //   139: getfield L : Ljava/util/Queue;
    //   142: invokeinterface isEmpty : ()Z
    //   147: ifne -> 196
    //   150: aload_0
    //   151: getfield L : Ljava/util/Queue;
    //   154: invokeinterface poll : ()Ljava/lang/Object;
    //   159: checkcast java/lang/Runnable
    //   162: invokeinterface run : ()V
    //   167: iload #9
    //   169: lload_3
    //   170: lconst_0
    //   171: lcmp
    //   172: iflt -> 218
    //   175: ifeq -> 211
    //   178: iload #9
    //   180: ifne -> 138
    //   183: lload_3
    //   184: lconst_0
    //   185: lcmp
    //   186: ifle -> 167
    //   189: goto -> 196
    //   192: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   195: athrow
    //   196: aload_0
    //   197: iconst_0
    //   198: anewarray java/lang/Object
    //   201: invokevirtual B : ([Ljava/lang/Object;)V
    //   204: aload_0
    //   205: getfield V : J
    //   208: invokestatic nvgEndFrame : (J)V
    //   211: lload_3
    //   212: lconst_0
    //   213: lcmp
    //   214: ifle -> 239
    //   217: iload_2
    //   218: ifeq -> 246
    //   221: lload #5
    //   223: iconst_1
    //   224: anewarray java/lang/Object
    //   227: dup_x2
    //   228: dup_x2
    //   229: pop
    //   230: invokestatic valueOf : (J)Ljava/lang/Long;
    //   233: iconst_0
    //   234: swap
    //   235: aastore
    //   236: invokestatic I : ([Ljava/lang/Object;)V
    //   239: goto -> 246
    //   242: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   245: athrow
    //   246: return
    // Exception table:
    //   from	to	target	type
    //   65	74	77	wtf/opal/x5
    //   70	99	102	wtf/opal/x5
    //   150	183	192	wtf/opal/x5
    //   211	239	242	wtf/opal/x5
  }
  
  public void J(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x23D998D4E3D0L;
    (new Object[2])[1] = Boolean.valueOf(true);
    new Object[2];
    l(new Object[] { Long.valueOf(l2) });
  }
  
  public void F(Object[] paramArrayOfObject) {
    Runnable runnable = (Runnable)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x74B3571D89F3L;
    long l3 = l1 ^ 0x38A8C3447134L;
    class_1041 class_1041 = b9.c.method_22683();
    float f = (float)class_1041.method_4495();
    new Object[1];
    kc.S(new Object[] { Long.valueOf(l3) });
    NanoVG.nvgBeginFrame(this.V, class_1041.method_4480(), class_1041.method_4507(), f);
    NanoVG.nvgScale(this.V, f, f);
    runnable.run();
    B(new Object[0]);
    NanoVG.nvgEndFrame(this.V);
    new Object[1];
    kc.I(new Object[] { Long.valueOf(l2) });
  }
  
  private void B(Object[] paramArrayOfObject) {
    this.u.forEach(NativeResource::free);
    this.u.clear();
  }
  
  public void T(Object[] paramArrayOfObject) {
    Runnable runnable = (Runnable)paramArrayOfObject[0];
    this.L.add(runnable);
  }
  
  public void k(Object[] paramArrayOfObject) {
    float f1 = ((Float)paramArrayOfObject[0]).floatValue();
    float f3 = ((Float)paramArrayOfObject[1]).floatValue();
    float f4 = ((Float)paramArrayOfObject[2]).floatValue();
    float f2 = ((Float)paramArrayOfObject[3]).floatValue();
    int i = ((Integer)paramArrayOfObject[4]).intValue();
    long l1 = ((Long)paramArrayOfObject[5]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x40E2F90783B2L;
    new Object[2];
    NVGColor nVGColor = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgFillColor(this.V, nVGColor);
    NanoVG.nvgRect(this.V, f1, f3, f4, f2);
    NanoVG.nvgFill(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void n(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f5 = ((Float)paramArrayOfObject[1]).floatValue();
    float f4 = ((Float)paramArrayOfObject[2]).floatValue();
    long l1 = ((Long)paramArrayOfObject[3]).longValue();
    float f3 = ((Float)paramArrayOfObject[4]).floatValue();
    int i = ((Integer)paramArrayOfObject[5]).intValue();
    int j = ((Integer)paramArrayOfObject[6]).intValue();
    float f1 = ((Float)paramArrayOfObject[7]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x18A1DADA116DL;
    new Object[2];
    NVGColor nVGColor1 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    new Object[2];
    NVGColor nVGColor2 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(j) });
    NVGPaint nVGPaint = N(new Object[0]);
    float f6 = f2 + f4 * 0.5F;
    float f7 = f5 + f3 * 0.5F;
    float f8 = 0.0F;
    float f9 = f1 * Math.max(f4, f3);
    NanoVG.nvgRadialGradient(this.V, f6, f7, 0.0F, f9, nVGColor1, nVGColor2, nVGPaint);
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgFillPaint(this.V, nVGPaint);
    NanoVG.nvgRect(this.V, f2, f5, f4, f3);
    NanoVG.nvgFill(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void Q(Object[] paramArrayOfObject) {
    float f5 = ((Float)paramArrayOfObject[0]).floatValue();
    float f2 = ((Float)paramArrayOfObject[1]).floatValue();
    float f3 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    int i = ((Integer)paramArrayOfObject[4]).intValue();
    long l1 = ((Long)paramArrayOfObject[5]).longValue();
    int j = ((Integer)paramArrayOfObject[6]).intValue();
    float f4 = ((Float)paramArrayOfObject[7]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x40196FA530B0L;
    new Object[2];
    NVGColor nVGColor1 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    new Object[2];
    NVGColor nVGColor2 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(j) });
    NVGPaint nVGPaint = N(new Object[0]);
    float f6 = (float)Math.toRadians(f4);
    float f7 = (float)Math.cos(f6);
    float f8 = (float)Math.sin(f6);
    NanoVG.nvgLinearGradient(this.V, f5 + f3 * 0.5F - f7 * f3 * 0.5F, f2 + f1 * 0.5F - f8 * f1 * 0.5F, f5 + f3 * 0.5F + f7 * f3 * 0.5F, f2 + f1 * 0.5F + f8 * f1 * 0.5F, nVGColor1, nVGColor2, nVGPaint);
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgFillPaint(this.V, nVGPaint);
    NanoVG.nvgRect(this.V, f5, f2, f3, f1);
    NanoVG.nvgFill(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void v(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f3 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = ((Float)paramArrayOfObject[3]).floatValue();
    float f2 = ((Float)paramArrayOfObject[4]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x7B37A7E5235AL;
    float f5 = f1;
    boolean bool = e();
    while (f5 < f1 + f4) {
      float f6 = (f5 - f1) / f4;
      int i = Color.HSBtoRGB(f6, 1.0F, 1.0F);
      float f7 = Math.min(0.5F, f1 + f4 - f5);
      new Object[6];
      k(new Object[] { 
            null, null, null, null, null, Long.valueOf(l2), Integer.valueOf(i), Float.valueOf(f2), Float.valueOf(f7), Float.valueOf(f3), 
            Float.valueOf(f5) });
      f5 += 0.5F;
      if (bool)
        break; 
    } 
  }
  
  public void M(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f3 = ((Float)paramArrayOfObject[1]).floatValue();
    float f5 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = ((Float)paramArrayOfObject[3]).floatValue();
    float f1 = ((Float)paramArrayOfObject[4]).floatValue();
    int i = ((Integer)paramArrayOfObject[5]).intValue();
    long l1 = ((Long)paramArrayOfObject[6]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x296E90962847L;
    new Object[2];
    NVGColor nVGColor = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgFillColor(this.V, nVGColor);
    NanoVG.nvgRoundedRect(this.V, f2, f3, f5, f4, f1);
    NanoVG.nvgFill(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void x(Object[] paramArrayOfObject) {
    float f3 = ((Float)paramArrayOfObject[0]).floatValue();
    float f4 = ((Float)paramArrayOfObject[1]).floatValue();
    float f6 = ((Float)paramArrayOfObject[2]).floatValue();
    float f5 = ((Float)paramArrayOfObject[3]).floatValue();
    float f8 = ((Float)paramArrayOfObject[4]).floatValue();
    float f1 = ((Float)paramArrayOfObject[5]).floatValue();
    float f7 = ((Float)paramArrayOfObject[6]).floatValue();
    long l1 = ((Long)paramArrayOfObject[7]).longValue();
    float f2 = ((Float)paramArrayOfObject[8]).floatValue();
    int i = ((Integer)paramArrayOfObject[9]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4635C28B4AEBL;
    new Object[2];
    NVGColor nVGColor = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgFillColor(this.V, nVGColor);
    NanoVG.nvgRoundedRectVarying(this.V, f3, f4, f6, f5, f8, f1, f7, f2);
    NanoVG.nvgFill(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void e(Object[] paramArrayOfObject) {
    float f3 = ((Float)paramArrayOfObject[0]).floatValue();
    float f6 = ((Float)paramArrayOfObject[1]).floatValue();
    float f5 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = ((Float)paramArrayOfObject[3]).floatValue();
    long l1 = ((Long)paramArrayOfObject[4]).longValue();
    float f1 = ((Float)paramArrayOfObject[5]).floatValue();
    int j = ((Integer)paramArrayOfObject[6]).intValue();
    int i = ((Integer)paramArrayOfObject[7]).intValue();
    float f2 = ((Float)paramArrayOfObject[8]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x107C98766E3L;
    new Object[2];
    NVGColor nVGColor1 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(j) });
    new Object[2];
    NVGColor nVGColor2 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NVGPaint nVGPaint = N(new Object[0]);
    boolean bool = d();
    float f7 = (float)Math.toRadians(f2);
    float f8 = (float)Math.cos(f7);
    float f9 = (float)Math.sin(f7);
    try {
      NanoVG.nvgLinearGradient(this.V, f3 + f5 * 0.5F - f8 * f5 * 0.5F, f6 + f4 * 0.5F - f9 * f4 * 0.5F, f3 + f5 * 0.5F + f8 * f5 * 0.5F, f6 + f4 * 0.5F + f9 * f4 * 0.5F, nVGColor1, nVGColor2, nVGPaint);
      NanoVG.nvgBeginPath(this.V);
      NanoVG.nvgFillPaint(this.V, nVGPaint);
      NanoVG.nvgRoundedRect(this.V, f3, f6, f5, f4, f1);
      NanoVG.nvgFill(this.V);
      NanoVG.nvgClosePath(this.V);
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw a(null);
        } 
        y(!bool);
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void H(Object[] paramArrayOfObject) {
    float f5 = ((Float)paramArrayOfObject[0]).floatValue();
    float f9 = ((Float)paramArrayOfObject[1]).floatValue();
    float f7 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = ((Float)paramArrayOfObject[3]).floatValue();
    float f6 = ((Float)paramArrayOfObject[4]).floatValue();
    float f3 = ((Float)paramArrayOfObject[5]).floatValue();
    float f1 = ((Float)paramArrayOfObject[6]).floatValue();
    float f8 = ((Float)paramArrayOfObject[7]).floatValue();
    long l1 = ((Long)paramArrayOfObject[8]).longValue();
    int j = ((Integer)paramArrayOfObject[9]).intValue();
    int i = ((Integer)paramArrayOfObject[10]).intValue();
    float f2 = ((Float)paramArrayOfObject[11]).floatValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4FD4E175D0C8L;
    new Object[2];
    NVGColor nVGColor1 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(j) });
    new Object[2];
    NVGColor nVGColor2 = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NVGPaint nVGPaint = N(new Object[0]);
    float f10 = (float)Math.toRadians(f2);
    float f11 = (float)Math.cos(f10);
    float f12 = (float)Math.sin(f10);
    NanoVG.nvgLinearGradient(this.V, f5 + f7 * 0.5F - f11 * f7 * 0.5F, f9 + f4 * 0.5F - f12 * f4 * 0.5F, f5 + f7 * 0.5F + f11 * f7 * 0.5F, f9 + f4 * 0.5F + f12 * f4 * 0.5F, nVGColor1, nVGColor2, nVGPaint);
    NanoVG.nvgBeginPath(this.V);
    boolean bool = e();
    try {
      NanoVG.nvgFillPaint(this.V, nVGPaint);
      NanoVG.nvgRoundedRectVarying(this.V, f5, f9, f7, f4, f6, f3, f1, f8);
      NanoVG.nvgFill(this.V);
      NanoVG.nvgClosePath(this.V);
      if (bool)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void P(Object[] paramArrayOfObject) {
    float f5 = ((Float)paramArrayOfObject[0]).floatValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f2 = ((Float)paramArrayOfObject[2]).floatValue();
    long l1 = ((Long)paramArrayOfObject[3]).longValue();
    float f3 = ((Float)paramArrayOfObject[4]).floatValue();
    float f4 = ((Float)paramArrayOfObject[5]).floatValue();
    int i = ((Integer)paramArrayOfObject[6]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x3569798F3942L;
    new Object[2];
    NVGColor nVGColor = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgStrokeColor(this.V, nVGColor);
    NanoVG.nvgStrokeWidth(this.V, f4);
    NanoVG.nvgRect(this.V, f5, f1, f2, f3);
    NanoVG.nvgStroke(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public void I(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f3 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = ((Float)paramArrayOfObject[3]).floatValue();
    float f2 = ((Float)paramArrayOfObject[4]).floatValue();
    Runnable runnable = (Runnable)paramArrayOfObject[5];
    float f5 = f1 + f4 / 2.0F;
    float f6 = f3 + f2 / 2.0F;
    NanoVG.nvgSave(this.V);
    NanoVG.nvgTranslate(this.V, f5, f6);
    NanoVG.nvgRotate(this.V, (float)Math.toRadians(d));
    runnable.run();
    NanoVG.nvgRestore(this.V);
  }
  
  public void r(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f3 = ((Float)paramArrayOfObject[1]).floatValue();
    float f4 = ((Float)paramArrayOfObject[2]).floatValue();
    float f5 = ((Float)paramArrayOfObject[3]).floatValue();
    float f1 = ((Float)paramArrayOfObject[4]).floatValue();
    Runnable runnable = (Runnable)paramArrayOfObject[5];
    float f6 = f3 + f5 / 2.0F;
    float f7 = f4 + f1 / 2.0F;
    NanoVG.nvgSave(this.V);
    NanoVG.nvgTranslate(this.V, f6, f7);
    NanoVG.nvgScale(this.V, f2, f2);
    NanoVG.nvgTranslate(this.V, -f6, -f7);
    runnable.run();
    NanoVG.nvgRestore(this.V);
  }
  
  public void t(Object[] paramArrayOfObject) {
    float f4 = ((Float)paramArrayOfObject[0]).floatValue();
    float f2 = ((Float)paramArrayOfObject[1]).floatValue();
    float f3 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    Runnable runnable = (Runnable)paramArrayOfObject[4];
    NanoVG.nvgScissor(this.V, f4, f2, f3, f1);
    runnable.run();
    NanoVG.nvgResetScissor(this.V);
  }
  
  public void G(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f5 = ((Float)paramArrayOfObject[1]).floatValue();
    float f6 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    long l1 = ((Long)paramArrayOfObject[4]).longValue();
    float f4 = ((Float)paramArrayOfObject[5]).floatValue();
    float f3 = ((Float)paramArrayOfObject[6]).floatValue();
    int i = ((Integer)paramArrayOfObject[7]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x583B74C6A6AAL;
    new Object[2];
    NVGColor nVGColor = Y(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NanoVG.nvgBeginPath(this.V);
    NanoVG.nvgStrokeColor(this.V, nVGColor);
    NanoVG.nvgStrokeWidth(this.V, f3);
    NanoVG.nvgRoundedRect(this.V, f2, f5, f6, f1, f4);
    NanoVG.nvgStroke(this.V);
    NanoVG.nvgClosePath(this.V);
  }
  
  public NVGPaint v(Object[] paramArrayOfObject) {
    int m = ((Integer)paramArrayOfObject[0]).intValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f2 = ((Float)paramArrayOfObject[2]).floatValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    int j = ((Integer)paramArrayOfObject[4]).intValue();
    int i1 = ((Integer)paramArrayOfObject[5]).intValue();
    int k = ((Integer)paramArrayOfObject[6]).intValue();
    int n = ((Integer)paramArrayOfObject[7]).intValue();
    NVGPaint nVGPaint = N(new Object[0]);
    int i2 = NanoVGGL3.nvglCreateImageFromHandle(this.V, m, i, j, 0);
    float f3 = f1 - (i / i1);
    float f4 = f2 - (j / i1);
    i *= i1;
    j *= i1;
    NanoVG.nvgImagePattern(this.V, f3, f4, i, j, k, i2, n, nVGPaint);
    return nVGPaint;
  }
  
  public NVGColor Y(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x5E4831896F6L;
    new Object[2];
    int[] arrayOfInt = pu.J(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
    NVGColor nVGColor = NVGColor.calloc().r(arrayOfInt[0] / 255.0F).g(arrayOfInt[1] / 255.0F).b(arrayOfInt[2] / 255.0F).a(arrayOfInt[3] / 255.0F);
    this.u.add(nVGColor);
    return nVGColor;
  }
  
  public NVGPaint N(Object[] paramArrayOfObject) {
    NVGPaint nVGPaint = NVGPaint.calloc();
    this.u.add(nVGPaint);
    return nVGPaint;
  }
  
  public long y(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public static void y(boolean paramBoolean) {
    G = paramBoolean;
  }
  
  public static boolean e() {
    return G;
  }
  
  public static boolean d() {
    boolean bool = e();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    if (e())
      y(true); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pa.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */