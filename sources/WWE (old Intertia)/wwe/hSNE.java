package wwe;

import java.util.concurrent.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public final class hSNE
{
    private static final Map<Class<? extends HQrR>, List<wwe/viaT>> TCzO;
    
    private hSNE() {
        super();
    }
    
    public static void lyvR(final Object o) {
        final Method[] array = o.getClass().getDeclaredMethods();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final Method method = array[i];
            if (uVLU(method)) {
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            else {
                rvvL(method, o);
            }
            ++i;
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
    }
    
    public static void RQdB(final Object o, final Class<? extends HQrR> clazz) {
        final Method[] array = o.getClass().getDeclaredMethods();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final Method method = array[i];
            if (ZXyn(method, clazz)) {
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            else {
                rvvL(method, o);
            }
            ++i;
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
    }
    
    public static void kduK(final Object o) {
        for (final List<wwe/viaT> list : hSNE.TCzO.values()) {
            for (final wwe/viaT wwe/viaT : list) {
                if (wwe/viaT.DVVM().equals(o)) {
                    list.remove(wwe/viaT);
                }
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        wAuA(true);
    }
    
    public static void krQd(final Object o, final Class<? extends HQrR> clazz) {
        if (hSNE.TCzO.containsKey(clazz)) {
            for (final wwe/viaT wwe/viaT : hSNE.TCzO.get(clazz)) {
                if (wwe/viaT.DVVM().equals(o)) {
                    hSNE.TCzO.get(clazz).remove(wwe/viaT);
                }
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            wAuA(true);
        }
    }
    
    private static void rvvL(final Method method, final Object o) {
        final class voGo extends CopyOnWriteArrayList<wwe/viaT>
        {
            private static final long PzDV = 666L;
            final wwe/viaT chVw;
            
            voGo(final wwe/viaT chVw) {
                this.chVw = chVw;
                super();
                this.add(this.chVw);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: aload_0        
        //     5: invokevirtual   java/lang/reflect/Method.getParameterTypes:()[Ljava/lang/Class;
        //     8: checkcast       [Ljava/lang/Class;
        //    11: ldc             0
        //    13: aaload         
        //    14: astore_2       
        //    15: new             Lwwe/viaT;
        //    18: dup            
        //    19: aload_1        
        //    20: aload_0        
        //    21: aload_0        
        //    22: ldc             Lwwe/oqrr;.class
        //    24: invokevirtual   java/lang/reflect/Method.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    27: checkcast       Lwwe/oqrr;
        //    30: invokevirtual   wwe/oqrr.Hmpo:()B
        //    33: invokespecial   wwe/viaT.<init>:(Ljava/lang/Object;Ljava/lang/reflect/Method;B)V
        //    36: astore_3       
        //    37: aload_3        
        //    38: invokevirtual   wwe/viaT.Ypmz:()Ljava/lang/reflect/Method;
        //    41: invokevirtual   java/lang/reflect/Method.isAccessible:()Z
        //    44: ifne            56
        //    47: aload_3        
        //    48: invokevirtual   wwe/viaT.Ypmz:()Ljava/lang/reflect/Method;
        //    51: ldc             1
        //    53: invokevirtual   java/lang/reflect/Method.setAccessible:(Z)V
        //    56: getstatic       wwe/hSNE.TCzO:Ljava/util/Map;
        //    59: aload_2        
        //    60: invokevirtual   java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //    63: ifeq            115
        //    66: getstatic       wwe/hSNE.TCzO:Ljava/util/Map;
        //    69: aload_2        
        //    70: invokevirtual   java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    73: checkcast       Ljava/util/List;
        //    76: aload_3        
        //    77: invokevirtual   java/util/List.contains:(Ljava/lang/Object;)Z
        //    80: ifne            131
        //    83: getstatic       wwe/hSNE.TCzO:Ljava/util/Map;
        //    86: aload_2        
        //    87: invokevirtual   java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    90: checkcast       Ljava/util/List;
        //    93: aload_3        
        //    94: invokevirtual   java/util/List.add:(Ljava/lang/Object;)Z
        //    97: pop            
        //    98: aload_2        
        //    99: invokestatic    wwe/hSNE.FVhv:(Ljava/lang/Class;)V
        //   102: getstatic       wwe/hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005:Z
        //   105: ifeq            131
        //   108: aconst_null    
        //   109: athrow         
        //   110: nop            
        //   111: nop            
        //   112: nop            
        //   113: nop            
        //   114: athrow         
        //   115: getstatic       wwe/hSNE.TCzO:Ljava/util/Map;
        //   118: aload_2        
        //   119: new             Lwwe/voGo;
        //   122: dup            
        //   123: aload_3        
        //   124: invokespecial   wwe/voGo.<init>:(Lwwe/viaT;)V
        //   127: invokevirtual   java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   130: pop            
        //   131: return         
        //   132: nop            
        //   133: nop            
        //   134: nop            
        //   135: nop            
        //   136: athrow         
        //    StackMapTable: 00 07 FE 00 03 00 00 01 00 FF 00 33 00 05 07 00 2F 07 00 04 07 00 1E 07 00 06 01 00 00 FF 00 35 00 00 00 01 07 00 14 FF 00 04 00 05 07 00 2F 07 00 04 07 00 1E 07 00 06 01 00 00 0F FF 00 00 00 00 00 01 07 00 14
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void oHll(final Class<? extends HQrR> clazz) {
        final Iterator<Map.Entry<Class<? extends HQrR>, List<wwe/viaT>>> iterator = hSNE.TCzO.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(clazz)) {
                iterator.remove();
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
                break;
            }
        }
    }
    
    public static void wAuA(final boolean b) {
        final Iterator<Map.Entry<Class<? extends HQrR>, List<wwe/viaT>>> iterator = hSNE.TCzO.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!b || iterator.next().getValue().isEmpty()) {
                iterator.remove();
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    private static void FVhv(final Class<? extends HQrR> clazz) {
        final CopyOnWriteArrayList<wwe/viaT> list = new CopyOnWriteArrayList<wwe/viaT>();
        final byte[] jayi = RzdD.JAYI;
        final int length = jayi.length;
        int i = 0;
        while (i < length) {
            final byte b = jayi[i];
            for (final wwe/viaT wwe/viaT : hSNE.TCzO.get(clazz)) {
                if (wwe/viaT.SBmI() == b) {
                    list.add(wwe/viaT);
                }
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            ++i;
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        hSNE.TCzO.put(clazz, list);
    }
    
    private static boolean uVLU(final Method method) {
        boolean b;
        if (method.getParameterTypes().length != 1 || !method.isAnnotationPresent(oqrr.class)) {
            b = true;
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    private static boolean ZXyn(final Method method, final Class<? extends HQrR> clazz) {
        boolean b;
        if (uVLU(method) || !method.getParameterTypes()[0].equals(clazz)) {
            b = true;
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public static final HQrR Seqo(final HQrR hQrR) {
        final List<wwe/viaT> list = hSNE.TCzO.get(hQrR.getClass());
        if (list != null) {
            if (hQrR instanceof RplF) {
                final RplF rplF = (RplF)hQrR;
                final Iterator<wwe/viaT> iterator = list.iterator();
                while (iterator.hasNext()) {
                    AUJU(iterator.next(), hQrR);
                    if (rplF.PoKT()) {
                        if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                    throw null;
                }
            }
            else {
                final Iterator<wwe/viaT> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    AUJU(iterator2.next(), hQrR);
                    if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                        throw null;
                    }
                }
            }
        }
        return hQrR;
    }
    
    private static void AUJU(final wwe/viaT wwe/viaT, final HQrR hQrR) {
        try {
            wwe/viaT.Ypmz().invoke(wwe/viaT.DVVM(), hQrR);
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        catch (IllegalAccessException ex) {
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        catch (IllegalArgumentException ex2) {
            if (hSNE.\u2003\u200a\u2008\u2004\u200f\u2003\u2002\u2009\u2005) {
                throw null;
            }
        }
        catch (InvocationTargetException ex3) {}
    }
    
    static {
        TCzO = new HashMap<Class<? extends HQrR>, List<wwe/viaT>>();
    }
    
    private static final class wwe/viaT
    {
        private final Object MSfl;
        private final Method Qtgt;
        private final byte saxX;
        
        public wwe/viaT(final Object mSfl, final Method qtgt, final byte saxX) {
            super();
            this.MSfl = mSfl;
            this.Qtgt = qtgt;
            this.saxX = saxX;
        }
        
        public Object DVVM() {
            return this.MSfl;
        }
        
        public Method Ypmz() {
            return this.Qtgt;
        }
        
        public byte SBmI() {
            return this.saxX;
        }
    }
}
