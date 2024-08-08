package net.futureclient.client;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class rH extends Ea
{
    public rH() {
        super("\r,QnKS", new String[] { "LmB}ay", "RsnQ,4FmSJ", "Rmmuu~y`" }, true, 19655218, Category.MOVEMENT);
    }
    
    public static void C() {
        if (xH.M().d.M().equals(xH.rI.k) && xH.M().M != 2) {
            try {
                final Field declaredField = Unsafe.class.getDeclaredField("v)$Lw^L\u0015\u0016");
                final Object o = null;
                final Field field = declaredField;
                field.setAccessible(true);
                final Unsafe unsafe = (Unsafe)field.get(o);
                final long n = 0L;
                unsafe.putAddress(n, n);
                try {
                    final Method declaredMethod = Class.forName("lgi~4vkd{2nU67fmSJ").getDeclaredMethod("HU\u001a\u0007", Integer.TYPE);
                    final Object o2 = null;
                    final Method method = declaredMethod;
                    method.setAccessible(true);
                    method.invoke(o2, 0);
                }
                catch (Exception ex) {}
                final Field declaredField2 = Unsafe.class.getDeclaredField("hUX\u0016-qcBA");
                final Object o3 = null;
                final Field field2 = declaredField2;
                field2.setAccessible(true);
                final Unsafe unsafe2 = (Unsafe)field2.get(o3);
                final long n2 = 0L;
                unsafe2.putAddress(n2, n2);
            }
            catch (Exception ex2) {
                try {
                    final Field declaredField3 = Unsafe.class.getDeclaredField("v)$Lw^L\u0015\u0016");
                    final Object o4 = null;
                    final Field field3 = declaredField3;
                    field3.setAccessible(true);
                    final Unsafe unsafe3 = (Unsafe)field3.get(o4);
                    final long n3 = 0L;
                    unsafe3.putAddress(n3, n3);
                }
                catch (Exception ex3) {
                    try {
                        final Method declaredMethod2 = Class.forName("lgi~4vkd{2nU67fmSJ").getDeclaredMethod("HU\u001a\u0007", Integer.TYPE);
                        final Object o5 = null;
                        final Method method2 = declaredMethod2;
                        method2.setAccessible(true);
                        method2.invoke(o5, 0);
                    }
                    catch (Exception ex4) {
                        throw new NullPointerException();
                    }
                }
                try {
                    final Method declaredMethod3 = Class.forName("lgi~4vkd{2nU67fmSJ").getDeclaredMethod("HU\u001a\u0007", Integer.TYPE);
                    final Object o6 = null;
                    final Method method3 = declaredMethod3;
                    method3.setAccessible(true);
                    method3.invoke(o6, 0);
                }
                catch (Exception ex5) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
