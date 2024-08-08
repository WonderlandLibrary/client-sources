package net.futureclient.client;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.util.zip.DataFormatException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;

public class VG extends DataInputStream
{
    private QH k;
    
    public VG(final InputStream inputStream) {
        super(inputStream);
    }
    
    public VG(final InputStream inputStream, final String s) {
        super(inputStream);
        this.k = new QH(s);
    }
    
    public og M() throws IOException, DataFormatException {
        final int int1;
        if ((int1 = this.readInt()) > 2500) {
            System.err.println("Versuch: " + int1);
            throw new IOException("Packet too long: " + int1);
        }
        final byte[] array = new byte[int1];
        this.readFully(array);
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(array));
        final boolean boolean1 = dataInputStream.readBoolean();
        final byte[] array2;
        dataInputStream.readFully(array2 = new byte[int1 - 1]);
        byte[] array3 = this.M(array2);
        if (boolean1) {
            if (this.k == null) {
                throw new IOException("This packet is encrypted");
            }
            byte[] e;
            try {
                e = this.k.e(array3);
            }
            catch (Exception ex) {
                throw new IOException("Wrong password");
            }
            array3 = this.M(e);
        }
        final DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(array3));
        return new og(dataInputStream2.readInt(), dataInputStream2);
    }
    
    public <P extends ai> P M(final Class<P> clazz) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, DataFormatException {
        final int int1;
        if ((int1 = this.readInt()) > 2500) {
            System.err.println("Versuch: " + int1);
            throw new IOException("Packet too long: " + int1);
        }
        final byte[] array = new byte[int1];
        this.readFully(array);
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(array));
        final boolean boolean1 = dataInputStream.readBoolean();
        final byte[] array2;
        dataInputStream.readFully(array2 = new byte[int1 - 1]);
        byte[] array3 = this.M(array2);
        if (boolean1) {
            if (this.k == null) {
                throw new IOException("This packet is encrypted");
            }
            byte[] e;
            try {
                e = this.k.e(array3);
            }
            catch (Exception ex) {
                throw new IOException("Wrong password");
            }
            array3 = this.M(e);
        }
        final DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(array3));
        final int n = 0;
        dataInputStream2.readInt();
        final Constructor<P> constructor = clazz.getConstructor((Class<?>[])new Class[n]);
        final int n2 = 0;
        final Constructor<P> constructor2 = constructor;
        constructor2.setAccessible(true);
        final ai ai = constructor2.newInstance(new Object[n2]);
        ai.M(dataInputStream2);
        return (P)ai;
    }
    
    public static String M(String s) {
        final char c = '\u001f';
        final char c2 = 'I';
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n;
        int i = n = length - 1;
        final char[] array2 = array;
        final char c3 = c2;
        final char c4 = c;
        while (i >= 0) {
            final char[] array3 = array2;
            final String s2 = s;
            final int n2 = n;
            final char char1 = s2.charAt(n2);
            --n;
            array3[n2] = (char)(char1 ^ c4);
            if (n < 0) {
                break;
            }
            final char[] array4 = array2;
            final String s3 = s;
            final int n3 = n--;
            array4[n3] = (char)(s3.charAt(n3) ^ c3);
            i = n;
        }
        return new String(array2);
    }
    
    public byte[] M(final byte[] input) throws IOException, DataFormatException {
        final Inflater inflater;
        (inflater = new Inflater()).setInput(input);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(input.length);
        final byte[] array = new byte[1024];
        Inflater inflater2 = inflater;
        while (!inflater2.finished()) {
            inflater2 = inflater;
            byteArrayOutputStream.write(array, 0, inflater.inflate(array));
        }
        final ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        byteArrayOutputStream2.close();
        return byteArrayOutputStream2.toByteArray();
    }
}
