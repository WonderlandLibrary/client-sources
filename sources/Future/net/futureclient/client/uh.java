package net.futureclient.client;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.OutputStream;
import java.io.DataOutputStream;

public class uh extends DataOutputStream
{
    private QH k;
    
    public uh(final OutputStream outputStream) {
        super(outputStream);
    }
    
    public uh(final OutputStream outputStream, final String s) {
        super(outputStream);
        this.k = new QH(s);
    }
    
    public void e(final ai ai) throws Exception {
        this.M(ai, true);
    }
    
    public static String M(String s) {
        final char c = '\n';
        final char c2 = '-';
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
    
    public void M(final ai ai, final boolean b) throws Exception {
        final ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeInt(ai.M());
        ai.M(dataOutput);
        final ByteArrayDataOutput dataOutput2 = ByteStreams.newDataOutput();
        byte[] array = this.M(dataOutput.toByteArray());
        if (b) {
            if (this.k == null) {
                throw new Exception("Crypto is null!");
            }
            array = this.M(this.k.M(array));
        }
        dataOutput2.writeInt(array.length + 1);
        final byte[] array2 = array;
        final ByteArrayDataOutput byteArrayDataOutput = dataOutput2;
        byteArrayDataOutput.writeBoolean(b);
        byteArrayDataOutput.write(array2);
        this.write(byteArrayDataOutput.toByteArray());
    }
    
    public byte[] M(final byte[] input) throws IOException {
        final Deflater deflater;
        (deflater = new Deflater()).setInput(input);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(input.length);
        final int n = 1024;
        Deflater deflater2;
        (deflater2 = deflater).finish();
        final byte[] array = new byte[n];
        while (!deflater2.finished()) {
            deflater2 = deflater;
            byteArrayOutputStream.write(array, 0, deflater.deflate(array));
        }
        final ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        byteArrayOutputStream2.close();
        return byteArrayOutputStream2.toByteArray();
    }
    
    public void M(final ai ai) throws Exception {
        this.M(ai, false);
    }
}
