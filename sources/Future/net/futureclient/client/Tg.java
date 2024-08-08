package net.futureclient.client;

import java.lang.reflect.Method;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import net.futureclient.client.utils.Timer;
import org.lwjgl.LWJGLException;

public class Tg extends Ea
{
    public Tg() {
        super(Kh.M("\u001f\u001b@"), new String[] { "aX[", Kh.M("\u0017t!f\"p\u001f\u001b@") }, true, -16155218, Category.RENDER);
    }
    
    public static void C() throws LWJGLException {
        try {
            Class.forName(xH.M().getClass().getName(), false, Tg.class.getClassLoader());
        }
        catch (ClassNotFoundException ex) {
            throw new LWJGLException();
        }
        switch (Gi.k[xH.M().d.M().ordinal()]) {
            case 1:
                try {
                    final Method declaredMethod = Class.forName(Timer.M("X#K,\u0016$U*Xav=\n{a\u001aA(")).getDeclaredMethod(Kh.M("t1\\\u0019"), Integer.TYPE);
                    final Object o = null;
                    final Method method = declaredMethod;
                    method.setAccessible(true);
                    method.invoke(o, 0);
                }
                catch (Exception ex2) {
                    throw new LWJGLException();
                }
            case 2:
                Display.create(new PixelFormat().withDepthBits(24));
                break;
        }
    }
}
