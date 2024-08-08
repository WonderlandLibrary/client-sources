package net.futureclient.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.io.IOException;
import com.google.common.io.ByteArrayDataOutput;
import java.io.DataInputStream;

public class og extends ai
{
    private int D;
    private DataInputStream k;
    
    public og(final int d, final DataInputStream k) {
        super();
        this.D = d;
        this.k = k;
    }
    
    @Override
    public int M() {
        return this.D;
    }
    
    @Override
    public void M(final ByteArrayDataOutput byteArrayDataOutput) throws IOException {
    }
    
    @Override
    public void M(final DataInputStream dataInputStream) throws IOException {
    }
    
    public <P extends ai> P M(final Class<P> clazz) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        final Constructor<P> constructor = clazz.getConstructor((Class<?>[])new Class[0]);
        final int n = 0;
        final Constructor<P> constructor2 = constructor;
        constructor2.setAccessible(true);
        final ai ai = constructor2.newInstance(new Object[n]);
        ai.M(this.k);
        return (P)ai;
    }
}
