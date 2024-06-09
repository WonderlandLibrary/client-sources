package md;

import java.lang.reflect.*;
import net.minecraft.client.renderer.entity.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import java.util.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class h extends c
{
    Method n;
    RenderItem o;
    com.sun.jna.z.a.e.a.a.a.a.i p;
    com.sun.jna.z.a.e.a.a.a.a.i q;
    public static int r;
    public static boolean s;
    public static int t;
    public static int u;
    public static int v;
    public static boolean w;
    public static boolean x;
    public static int y;
    public static boolean z;
    public static boolean A;
    public static int B;
    public static boolean C;
    public static boolean D;
    public static int E;
    public static boolean F;
    public static int G;
    public static boolean H;
    public static boolean I;
    public static boolean J;
    private static final String[] K;
    
    public h() {
        final int j = h.J ? 1 : 0;
        final String[] a = h.K;
        super(a[1], com.sun.jna.z.a.d.b.Render, -16711681);
        final int a2 = j;
        this.p = new d(a[7]);
        this.q = new d(a[4]);
        try {
            final Method[] declaredMethods = RenderItem.class.getDeclaredMethods();
            final int a3 = declaredMethods.length;
            int a4 = 0;
            while (a4 < a3) {
                final Method a5 = declaredMethods[a4];
                Label_0125: {
                    Label_0122: {
                        try {
                            Label_0089: {
                                try {
                                    if (a2 != 0) {
                                        break;
                                    }
                                    final int n = a2;
                                    if (n == 0) {
                                        break Label_0089;
                                    }
                                    break Label_0125;
                                }
                                catch (Exception ex) {
                                    throw ex;
                                }
                                try {
                                    final int n = a2;
                                    if (n != 0) {
                                        break Label_0125;
                                    }
                                    if (!a5.getReturnType().equals(RenderItem.class)) {
                                        break Label_0122;
                                    }
                                }
                                catch (Exception ex2) {
                                    throw ex2;
                                }
                            }
                            (this.n = a5).setAccessible(true);
                        }
                        catch (Exception ex3) {
                            throw ex3;
                        }
                    }
                    ++a4;
                }
                if (a2 != 0) {
                    break;
                }
            }
        }
        catch (Exception a6) {
            a6.printStackTrace();
        }
    }
    
    @Override
    public void a(final float a) {
        final int j = h.J ? 1 : 0;
        final Iterator<Object> iterator = (Iterator<Object>)this.a.field_71441_e.field_73010_i.iterator();
        final int a2 = j;
        while (iterator.hasNext()) {
            final Object a3 = iterator.next();
            final EntityPlayer a4 = (EntityPlayer)a3;
            if (a2 != 0) {
                return;
            }
            if (!this.e((Entity)a4) && a2 == 0) {
                continue;
            }
            final double a5 = a4.field_70142_S + (a4.field_70165_t - a4.field_70142_S) * a - com.sun.jna.z.a.h.d.i.a();
            final double a6 = a4.field_70137_T + (a4.field_70163_u - a4.field_70137_T) * a - com.sun.jna.z.a.h.d.i.b();
            final double a7 = a4.field_70136_U + (a4.field_70161_v - a4.field_70136_U) * a - com.sun.jna.z.a.h.d.i.c();
            this.a(a4, a5, a6, a7, a, this.p.a(), this.q.a());
            if (a2 != 0) {
                break;
            }
        }
        GL11.glColor3d(1.0, 1.0, 1.0);
    }
    
    private boolean e(final Entity a) {
        final int a2 = h.J ? 1 : 0;
        Entity entity = a;
        Entity entity2 = a;
        final boolean func_70089_S;
        if (a2 == 0) {
            if (a == null) {
                return func_70089_S;
            }
            entity = a;
            entity2 = a;
        }
        if (a2 == 0) {
            if (entity2 == this.a.field_71439_g) {
                return func_70089_S;
            }
            entity = a;
        }
        func_70089_S = entity.func_70089_S();
        if (a2 == 0 && !func_70089_S) {}
        return func_70089_S;
    }
    
    public void a(final EntityPlayer a, final double a, final double a, final double a, final float a, final boolean a, final boolean a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          a
        //     5: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //     8: getfield        net/minecraft/client/Minecraft.field_71460_t:Lnet/minecraft/client/renderer/EntityRenderer;
        //    11: dconst_1       
        //    12: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_78483_a:(D)V
        //    15: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //    18: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityClientPlayerMP;
        //    21: aload_1         /* a */
        //    22: invokevirtual   net/minecraft/client/entity/EntityClientPlayerMP.func_70032_d:(Lnet/minecraft/entity/Entity;)F
        //    25: f2d            
        //    26: dstore          a
        //    28: getstatic       md/h.J:Z
        //    31: new             Ljava/text/DecimalFormat;
        //    34: dup            
        //    35: getstatic       md/h.K:[Ljava/lang/String;
        //    38: astore          a
        //    40: aload           a
        //    42: iconst_5       
        //    43: aaload         
        //    44: invokespecial   java/text/DecimalFormat.<init>:(Ljava/lang/String;)V
        //    47: astore          16
        //    49: istore          a
        //    51: aload_1         /* a */
        //    52: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_110143_aJ:()F
        //    55: f2i            
        //    56: istore          a
        //    58: iload           a
        //    60: iconst_5       
        //    61: iload           a
        //    63: ifne            78
        //    66: if_icmpgt       74
        //    69: ldc             "c"
        //    71: goto            88
        //    74: iload           a
        //    76: bipush          10
        //    78: if_icmpgt       86
        //    81: ldc             "e"
        //    83: goto            88
        //    86: ldc             "a"
        //    88: astore          a
        //    90: new             Ljava/lang/StringBuilder;
        //    93: dup            
        //    94: invokespecial   java/lang/StringBuilder.<init>:()V
        //    97: getstatic       md/h.K:[Ljava/lang/String;
        //   100: astore          a
        //   102: aload           a
        //   104: iconst_3       
        //   105: aaload         
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: dload           a
        //   111: d2i            
        //   112: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   115: aload           a
        //   117: iconst_0       
        //   118: aaload         
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: ldc             " "
        //   124: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   127: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   130: astore          a
        //   132: iload           a
        //   134: ifne            174
        //   137: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //   140: getfield        com/sun/jna/z/a/i.d:Lcom/sun/jna/z/a/f/f;
        //   143: aload_1         /* a */
        //   144: invokevirtual   com/sun/jna/z/a/f/f.a:(Lnet/minecraft/entity/Entity;)Z
        //   147: ifeq            192
        //   150: new             Ljava/lang/StringBuilder;
        //   153: dup            
        //   154: invokespecial   java/lang/StringBuilder.<init>:()V
        //   157: aload           a
        //   159: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   162: aload_1         /* a */
        //   163: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70005_c_:()Ljava/lang/String;
        //   166: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   169: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   172: astore          a
        //   174: iload           a
        //   176: ifeq            221
        //   179: getstatic       md/h.r:I
        //   182: istore          a
        //   184: iinc            a, 1
        //   187: iload           a
        //   189: putstatic       md/h.r:I
        //   192: new             Ljava/lang/StringBuilder;
        //   195: dup            
        //   196: invokespecial   java/lang/StringBuilder.<init>:()V
        //   199: aload           a
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   204: aload_1         /* a */
        //   205: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_145748_c_:()Lnet/minecraft/util/IChatComponent;
        //   208: invokeinterface net/minecraft/util/IChatComponent.func_150254_d:()Ljava/lang/String;
        //   213: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   216: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   219: astore          a
        //   221: iload           a
        //   223: iload           a
        //   225: ifne            290
        //   228: ifeq            286
        //   231: new             Ljava/lang/StringBuilder;
        //   234: dup            
        //   235: invokespecial   java/lang/StringBuilder.<init>:()V
        //   238: aload           a
        //   240: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   243: getstatic       md/h.K:[Ljava/lang/String;
        //   246: astore          a
        //   248: aload           a
        //   250: iconst_2       
        //   251: aaload         
        //   252: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   255: aload           a
        //   257: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   260: aload           a
        //   262: aload_1         /* a */
        //   263: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_110143_aJ:()F
        //   266: f2d            
        //   267: invokevirtual   java/text/DecimalFormat.format:(D)Ljava/lang/String;
        //   270: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   273: aload           a
        //   275: bipush          8
        //   277: aaload         
        //   278: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   281: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   284: astore          a
        //   286: aload_1         /* a */
        //   287: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_82150_aj:()Z
        //   290: ifeq            319
        //   293: new             Ljava/lang/StringBuilder;
        //   296: dup            
        //   297: invokespecial   java/lang/StringBuilder.<init>:()V
        //   300: aload           a
        //   302: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   305: getstatic       md/h.K:[Ljava/lang/String;
        //   308: bipush          6
        //   310: aaload         
        //   311: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   314: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   317: astore          a
        //   319: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   322: getfield        net/minecraft/client/Minecraft.field_71466_p:Lnet/minecraft/client/gui/FontRenderer;
        //   325: astore          a
        //   327: ldc             1.6
        //   329: fstore          a
        //   331: ldc             0.016666668
        //   333: fload           a
        //   335: fmul           
        //   336: f2d            
        //   337: aload_1         /* a */
        //   338: aload           a
        //   340: getfield        net/minecraft/client/renderer/entity/RenderManager.field_78734_h:Lnet/minecraft/entity/EntityLivingBase;
        //   343: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70032_d:(Lnet/minecraft/entity/Entity;)F
        //   346: ldc             10.0
        //   348: fcmpl          
        //   349: iflt            361
        //   352: dload           a
        //   354: ldc2_w          10.0
        //   357: ddiv           
        //   358: goto            362
        //   361: dconst_1       
        //   362: dmul           
        //   363: d2f            
        //   364: fstore          a
        //   366: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   369: dload_2         /* a */
        //   370: d2f            
        //   371: dload           a
        //   373: d2f            
        //   374: aload_1         /* a */
        //   375: getfield        net/minecraft/entity/player/EntityPlayer.field_70131_O:F
        //   378: fadd           
        //   379: ldc             0.5
        //   381: fadd           
        //   382: dload           a
        //   384: d2f            
        //   385: invokestatic    org/lwjgl/opengl/GL11.glTranslatef:(FFF)V
        //   388: fconst_0       
        //   389: fconst_1       
        //   390: fconst_0       
        //   391: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   394: aload           a
        //   396: getfield        net/minecraft/client/renderer/entity/RenderManager.field_78735_i:F
        //   399: fneg           
        //   400: fconst_0       
        //   401: fconst_1       
        //   402: fconst_0       
        //   403: invokestatic    org/lwjgl/opengl/GL11.glRotatef:(FFFF)V
        //   406: aload           a
        //   408: getfield        net/minecraft/client/renderer/entity/RenderManager.field_78732_j:F
        //   411: fconst_1       
        //   412: fconst_0       
        //   413: fconst_0       
        //   414: invokestatic    org/lwjgl/opengl/GL11.glRotatef:(FFFF)V
        //   417: fload           a
        //   419: fneg           
        //   420: fload           a
        //   422: fneg           
        //   423: fload           a
        //   425: invokestatic    org/lwjgl/opengl/GL11.glScalef:(FFF)V
        //   428: iconst_0       
        //   429: invokestatic    org/lwjgl/opengl/GL11.glDepthMask:(Z)V
        //   432: sipush          2929
        //   435: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   438: sipush          2896
        //   441: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   444: iload           a
        //   446: iload           a
        //   448: ifne            474
        //   451: ifeq            459
        //   454: aload_0         /* a */
        //   455: aload_1         /* a */
        //   456: invokevirtual   md/h.a:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   459: sipush          2896
        //   462: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   465: sipush          3042
        //   468: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   471: sipush          770
        //   474: sipush          771
        //   477: iconst_1       
        //   478: iconst_0       
        //   479: invokestatic    net/minecraft/client/renderer/OpenGlHelper.func_148821_a:(IIII)V
        //   482: getstatic       net/minecraft/client/renderer/Tessellator.field_78398_a:Lnet/minecraft/client/renderer/Tessellator;
        //   485: astore          a
        //   487: iconst_0       
        //   488: istore          a
        //   490: sipush          3553
        //   493: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   496: aload           a
        //   498: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78382_b:()V
        //   501: aload           a
        //   503: aload           a
        //   505: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78256_a:(Ljava/lang/String;)I
        //   508: iconst_2       
        //   509: idiv           
        //   510: istore          a
        //   512: aload           a
        //   514: aload_1         /* a */
        //   515: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70093_af:()Z
        //   518: ifeq            526
        //   521: ldc             0.5
        //   523: goto            527
        //   526: fconst_0       
        //   527: fconst_0       
        //   528: fconst_0       
        //   529: ldc             0.35
        //   531: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78369_a:(FFFF)V
        //   534: aload           a
        //   536: iload           a
        //   538: ineg           
        //   539: iconst_1       
        //   540: isub           
        //   541: i2d            
        //   542: iconst_m1      
        //   543: iload           a
        //   545: iadd           
        //   546: i2d            
        //   547: dconst_0       
        //   548: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78377_a:(DDD)V
        //   551: aload           a
        //   553: iload           a
        //   555: ineg           
        //   556: iconst_1       
        //   557: isub           
        //   558: i2d            
        //   559: bipush          8
        //   561: iload           a
        //   563: iadd           
        //   564: i2d            
        //   565: dconst_0       
        //   566: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78377_a:(DDD)V
        //   569: aload           a
        //   571: iload           a
        //   573: iconst_1       
        //   574: iadd           
        //   575: i2d            
        //   576: bipush          8
        //   578: iload           a
        //   580: iadd           
        //   581: i2d            
        //   582: dconst_0       
        //   583: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78377_a:(DDD)V
        //   586: aload           a
        //   588: iload           a
        //   590: iconst_1       
        //   591: iadd           
        //   592: i2d            
        //   593: iconst_m1      
        //   594: iload           a
        //   596: iadd           
        //   597: i2d            
        //   598: dconst_0       
        //   599: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78377_a:(DDD)V
        //   602: aload           a
        //   604: invokevirtual   net/minecraft/client/renderer/Tessellator.func_78381_a:()I
        //   607: pop            
        //   608: ldc             1.5
        //   610: invokestatic    org/lwjgl/opengl/GL11.glLineWidth:(F)V
        //   613: fconst_0       
        //   614: fconst_0       
        //   615: fconst_0       
        //   616: ldc             0.5
        //   618: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   621: iconst_2       
        //   622: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   625: iload           a
        //   627: ineg           
        //   628: iconst_1       
        //   629: isub           
        //   630: i2d            
        //   631: iconst_m1      
        //   632: iload           a
        //   634: iadd           
        //   635: i2d            
        //   636: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   639: iload           a
        //   641: ineg           
        //   642: iconst_1       
        //   643: isub           
        //   644: i2d            
        //   645: bipush          8
        //   647: iload           a
        //   649: iadd           
        //   650: i2d            
        //   651: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   654: iload           a
        //   656: iconst_1       
        //   657: iadd           
        //   658: i2d            
        //   659: bipush          8
        //   661: iload           a
        //   663: iadd           
        //   664: i2d            
        //   665: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   668: iload           a
        //   670: iconst_1       
        //   671: iadd           
        //   672: i2d            
        //   673: iconst_m1      
        //   674: iload           a
        //   676: iadd           
        //   677: i2d            
        //   678: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   681: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   684: sipush          3553
        //   687: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   690: iconst_m1      
        //   691: istore          a
        //   693: getstatic       com/sun/jna/z/a/i.f:Lcom/sun/jna/z/a/i;
        //   696: getfield        com/sun/jna/z/a/i.d:Lcom/sun/jna/z/a/f/f;
        //   699: aload_1         /* a */
        //   700: invokevirtual   com/sun/jna/z/a/f/f.a:(Lnet/minecraft/entity/Entity;)Z
        //   703: iload           a
        //   705: ifne            778
        //   708: ifeq            715
        //   711: ldc             -16711776
        //   713: istore          a
        //   715: aload           a
        //   717: aload           a
        //   719: aload           a
        //   721: aload           a
        //   723: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78256_a:(Ljava/lang/String;)I
        //   726: ineg           
        //   727: iconst_2       
        //   728: idiv           
        //   729: iload           a
        //   731: iload           a
        //   733: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78276_b:(Ljava/lang/String;III)I
        //   736: pop            
        //   737: aload           a
        //   739: aload           a
        //   741: aload           a
        //   743: aload           a
        //   745: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78256_a:(Ljava/lang/String;)I
        //   748: ineg           
        //   749: iconst_2       
        //   750: idiv           
        //   751: iload           a
        //   753: iload           a
        //   755: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78276_b:(Ljava/lang/String;III)I
        //   758: pop            
        //   759: sipush          2929
        //   762: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   765: iconst_1       
        //   766: invokestatic    org/lwjgl/opengl/GL11.glDepthMask:(Z)V
        //   769: sipush          2896
        //   772: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   775: sipush          3042
        //   778: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   781: fconst_1       
        //   782: fconst_1       
        //   783: fconst_1       
        //   784: fconst_1       
        //   785: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   788: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   791: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   794: getfield        net/minecraft/client/Minecraft.field_71460_t:Lnet/minecraft/client/renderer/EntityRenderer;
        //   797: dconst_1       
        //   798: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_78463_b:(D)V
        //   801: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
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
    
    public void a(final EntityPlayer a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: bipush          -5
        //     5: istore_3        /* a */
        //     6: istore_2        /* a */
        //     7: aload_1         /* a */
        //     8: getfield        net/minecraft/entity/player/EntityPlayer.field_71071_by:Lnet/minecraft/entity/player/InventoryPlayer;
        //    11: getfield        net/minecraft/entity/player/InventoryPlayer.field_70460_b:[Lnet/minecraft/item/ItemStack;
        //    14: astore          a
        //    16: aload           a
        //    18: astore          5
        //    20: aload           5
        //    22: arraylength    
        //    23: istore          6
        //    25: iconst_0       
        //    26: istore          a
        //    28: iload           a
        //    30: iload           6
        //    32: if_icmpge       77
        //    35: aload           5
        //    37: iload           a
        //    39: aaload         
        //    40: astore          a
        //    42: iload_2         /* a */
        //    43: ifne            73
        //    46: aload           a
        //    48: iload_2         /* a */
        //    49: ifne            118
        //    52: goto            56
        //    55: athrow         
        //    56: ifnull          70
        //    59: goto            63
        //    62: athrow         
        //    63: iinc            a, -10
        //    66: goto            70
        //    69: athrow         
        //    70: iinc            a, 1
        //    73: iload_2         /* a */
        //    74: ifeq            28
        //    77: aload_0         /* a */
        //    78: aload_0         /* a */
        //    79: getfield        md/h.n:Ljava/lang/reflect/Method;
        //    82: aconst_null    
        //    83: iconst_0       
        //    84: anewarray       Ljava/lang/Object;
        //    87: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //    90: checkcast       Lnet/minecraft/client/renderer/entity/RenderItem;
        //    93: putfield        md/h.o:Lnet/minecraft/client/renderer/entity/RenderItem;
        //    96: goto            106
        //    99: astore          a
        //   101: aload           a
        //   103: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   106: iload_2         /* a */
        //   107: ifne            300
        //   110: aload_1         /* a */
        //   111: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //   114: goto            118
        //   117: athrow         
        //   118: ifnull          297
        //   121: iinc            a, 5
        //   124: aload_0         /* a */
        //   125: getfield        md/h.o:Lnet/minecraft/client/renderer/entity/RenderItem;
        //   128: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   131: getfield        net/minecraft/client/Minecraft.field_71466_p:Lnet/minecraft/client/gui/FontRenderer;
        //   134: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   137: getfield        net/minecraft/client/Minecraft.field_71446_o:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   140: aload_1         /* a */
        //   141: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //   144: iload_3         /* a */
        //   145: bipush          -20
        //   147: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.func_77015_a:(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V
        //   150: aload_1         /* a */
        //   151: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //   154: getfield        net/minecraft/item/ItemStack.field_77994_a:I
        //   157: iconst_1       
        //   158: iload_2         /* a */
        //   159: ifne            304
        //   162: if_icmple       297
        //   165: goto            169
        //   168: athrow         
        //   169: goto            173
        //   172: athrow         
        //   173: sipush          2896
        //   176: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   179: sipush          3042
        //   182: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   185: ldc2_w          0.75
        //   188: ldc2_w          0.75
        //   191: ldc2_w          0.75
        //   194: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //   197: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   200: getfield        net/minecraft/client/Minecraft.field_71466_p:Lnet/minecraft/client/gui/FontRenderer;
        //   203: new             Ljava/lang/StringBuilder;
        //   206: dup            
        //   207: invokespecial   java/lang/StringBuilder.<init>:()V
        //   210: aload_1         /* a */
        //   211: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //   214: getfield        net/minecraft/item/ItemStack.field_77994_a:I
        //   217: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   220: ldc             ""
        //   222: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   225: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   228: iload_3         /* a */
        //   229: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   232: getfield        net/minecraft/client/Minecraft.field_71466_p:Lnet/minecraft/client/gui/FontRenderer;
        //   235: new             Ljava/lang/StringBuilder;
        //   238: dup            
        //   239: invokespecial   java/lang/StringBuilder.<init>:()V
        //   242: aload_1         /* a */
        //   243: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //   246: getfield        net/minecraft/item/ItemStack.field_77994_a:I
        //   249: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   252: ldc             ""
        //   254: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   257: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   260: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78256_a:(Ljava/lang/String;)I
        //   263: iconst_2       
        //   264: idiv           
        //   265: isub           
        //   266: iconst_3       
        //   267: iadd           
        //   268: bipush          -14
        //   270: iconst_m1      
        //   271: invokevirtual   net/minecraft/client/gui/FontRenderer.func_78261_a:(Ljava/lang/String;III)I
        //   274: pop            
        //   275: ldc2_w          1.5
        //   278: ldc2_w          1.5
        //   281: ldc2_w          1.5
        //   284: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //   287: sipush          2896
        //   290: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   293: goto            297
        //   296: athrow         
        //   297: iinc            a, 5
        //   300: aload           a
        //   302: arraylength    
        //   303: iconst_1       
        //   304: isub           
        //   305: istore          a
        //   307: iload           a
        //   309: iconst_m1      
        //   310: if_icmple       372
        //   313: aload           a
        //   315: iload           a
        //   317: aaload         
        //   318: astore          a
        //   320: iload_2         /* a */
        //   321: ifne            341
        //   324: aload           a
        //   326: ifnonnull       338
        //   329: goto            333
        //   332: athrow         
        //   333: goto            365
        //   336: athrow         
        //   337: athrow         
        //   338: iinc            a, 15
        //   341: aload_0         /* a */
        //   342: getfield        md/h.o:Lnet/minecraft/client/renderer/entity/RenderItem;
        //   345: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   348: getfield        net/minecraft/client/Minecraft.field_71466_p:Lnet/minecraft/client/gui/FontRenderer;
        //   351: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //   354: getfield        net/minecraft/client/Minecraft.field_71446_o:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   357: aload           a
        //   359: iload_3         /* a */
        //   360: bipush          -20
        //   362: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.func_77015_a:(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V
        //   365: iinc            a, -1
        //   368: iload_2         /* a */
        //   369: ifeq            307
        //   372: getstatic       md/h.r:I
        //   375: ifeq            395
        //   378: iload_2         /* a */
        //   379: ifeq            391
        //   382: goto            386
        //   385: athrow         
        //   386: iconst_0       
        //   387: goto            392
        //   390: athrow         
        //   391: iconst_1       
        //   392: putstatic       md/h.J:Z
        //   395: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  46     59     62     63     Ljava/lang/Exception;
        //  42     52     55     56     Ljava/lang/Exception;
        //  42     66     69     70     Ljava/lang/Exception;
        //  77     96     99     106    Ljava/lang/Exception;
        //  121    165    168    169    Ljava/lang/Exception;
        //  106    114    117    118    Ljava/lang/Exception;
        //  106    169    172    173    Ljava/lang/Exception;
        //  121    293    296    297    Ljava/lang/Exception;
        //  324    336    336    337    Ljava/lang/Exception;
        //  320    329    332    333    Ljava/lang/Exception;
        //  320    337    337    338    Ljava/lang/Exception;
        //  372    382    385    386    Ljava/lang/Exception;
        //  378    390    390    391    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0169:
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
    
    @Override
    public k[] k() {
        return new k[] { this.p, this.q };
    }
    
    static {
        final String[] k = new String[9];
        int n = 0;
        String s;
        int n2 = (s = "¡±k@¥\u00f3\b\u0082wcxV\u00e0\u00db¿\u0005\u00ec±kF¥\u0005ksUºd\t\u0089g{tr\u00ec\u00d9¢b\u0004\u00ef5 >\b\u00ecM©~K&\u00da\u0091").length();
        int n3 = 6;
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
                                        c2 = '\u00cc';
                                        break;
                                    }
                                    case 1: {
                                        c2 = '\u0016';
                                        break;
                                    }
                                    case 2: {
                                        c2 = '\u000e';
                                        break;
                                    }
                                    case 3: {
                                        c2 = '\u001d';
                                        break;
                                    }
                                    case 4: {
                                        c2 = '\u0002';
                                        break;
                                    }
                                    case 5: {
                                        c2 = '\u0081';
                                        break;
                                    }
                                    default: {
                                        c2 = '¼';
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
                            k[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "\u0084soqv\u00e9\u0005ksSºd").length();
                            n3 = 6;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            k[n++] = intern;
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
        K = k;
    }
}
