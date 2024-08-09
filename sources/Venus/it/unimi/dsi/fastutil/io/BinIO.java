/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.io.FastBufferedInputStream;
import it.unimi.dsi.fastutil.io.FastBufferedOutputStream;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortIterable;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;

public class BinIO {
    private static final int MAX_IO_LENGTH = 0x100000;

    private BinIO() {
    }

    public static void storeObject(Object object, File file) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }

    public static void storeObject(Object object, CharSequence charSequence) throws IOException {
        BinIO.storeObject(object, new File(charSequence.toString()));
    }

    public static Object loadObject(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FastBufferedInputStream(new FileInputStream(file)));
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

    public static Object loadObject(CharSequence charSequence) throws IOException, ClassNotFoundException {
        return BinIO.loadObject(new File(charSequence.toString()));
    }

    public static void storeObject(Object object, OutputStream outputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FastBufferedOutputStream(outputStream));
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    public static Object loadObject(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FastBufferedInputStream(inputStream));
        Object object = objectInputStream.readObject();
        return object;
    }

    private static int read(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (n2 == 0) {
            return 1;
        }
        int n4 = 0;
        do {
            if ((n3 = inputStream.read(byArray, n + n4, Math.min(n2 - n4, 0x100000))) >= 0) continue;
            return n4;
        } while ((n4 += n3) < n2);
        return n4;
    }

    private static void write(OutputStream outputStream, byte[] byArray, int n, int n2) throws IOException {
        for (int i = 0; i < n2; i += Math.min(n2 - i, 0x100000)) {
            outputStream.write(byArray, n + i, Math.min(n2 - i, 0x100000));
        }
    }

    private static void write(DataOutput dataOutput, byte[] byArray, int n, int n2) throws IOException {
        for (int i = 0; i < n2; i += Math.min(n2 - i, 0x100000)) {
            dataOutput.write(byArray, n + i, Math.min(n2 - i, 0x100000));
        }
    }

    public static int loadBytes(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        return BinIO.read(inputStream, byArray, n, n2);
    }

    public static int loadBytes(InputStream inputStream, byte[] byArray) throws IOException {
        return BinIO.read(inputStream, byArray, 0, byArray.length);
    }

    public static void storeBytes(byte[] byArray, int n, int n2, OutputStream outputStream) throws IOException {
        BinIO.write(outputStream, byArray, n, n2);
    }

    public static void storeBytes(byte[] byArray, OutputStream outputStream) throws IOException {
        BinIO.write(outputStream, byArray, 0, byArray.length);
    }

    private static long read(InputStream inputStream, byte[][] byArray, long l, long l2) throws IOException {
        if (l2 == 0L) {
            return 0L;
        }
        long l3 = 0L;
        int n = BigArrays.segment(l);
        int n2 = BigArrays.displacement(l);
        do {
            int n3;
            if ((n3 = inputStream.read(byArray[n], n2, (int)Math.min((long)(byArray[n].length - n2), Math.min(l2 - l3, 0x100000L)))) < 0) {
                return l3;
            }
            l3 += (long)n3;
            if ((n2 += n3) != byArray[n].length) continue;
            ++n;
            n2 = 0;
        } while (l3 < l2);
        return l3;
    }

    private static void write(OutputStream outputStream, byte[][] byArray, long l, long l2) throws IOException {
        if (l2 == 0L) {
            return;
        }
        long l3 = 0L;
        int n = BigArrays.segment(l);
        int n2 = BigArrays.displacement(l);
        do {
            int n3 = (int)Math.min((long)(byArray[n].length - n2), Math.min(l2 - l3, 0x100000L));
            outputStream.write(byArray[n], n2, n3);
            l3 += (long)n3;
            if ((n2 += n3) != byArray[n].length) continue;
            ++n;
            n2 = 0;
        } while (l3 < l2);
    }

    private static void write(DataOutput dataOutput, byte[][] byArray, long l, long l2) throws IOException {
        if (l2 == 0L) {
            return;
        }
        long l3 = 0L;
        int n = BigArrays.segment(l);
        int n2 = BigArrays.displacement(l);
        do {
            int n3 = (int)Math.min((long)(byArray[n].length - n2), Math.min(l2 - l3, 0x100000L));
            dataOutput.write(byArray[n], n2, n3);
            l3 += (long)n3;
            if ((n2 += n3) != byArray[n].length) continue;
            ++n;
            n2 = 0;
        } while (l3 < l2);
    }

    public static long loadBytes(InputStream inputStream, byte[][] byArray, long l, long l2) throws IOException {
        return BinIO.read(inputStream, byArray, l, l2);
    }

    public static long loadBytes(InputStream inputStream, byte[][] byArray) throws IOException {
        return BinIO.read(inputStream, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, OutputStream outputStream) throws IOException {
        BinIO.write(outputStream, byArray, l, l2);
    }

    public static void storeBytes(byte[][] byArray, OutputStream outputStream) throws IOException {
        BinIO.write(outputStream, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static int loadBytes(DataInput dataInput, byte[] byArray, int n, int n2) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                byArray[n3 + n] = dataInput.readByte();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadBytes(DataInput dataInput, byte[] byArray) throws IOException {
        int n = 0;
        try {
            int n2 = byArray.length;
            for (n = 0; n < n2; ++n) {
                byArray[n] = dataInput.readByte();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadBytes(File file, byte[] byArray, int n, int n2) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        int n3 = BinIO.read((InputStream)fileInputStream, byArray, n, n2);
        fileInputStream.close();
        return n3;
    }

    public static int loadBytes(CharSequence charSequence, byte[] byArray, int n, int n2) throws IOException {
        return BinIO.loadBytes(new File(charSequence.toString()), byArray, n, n2);
    }

    public static int loadBytes(File file, byte[] byArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int n = BinIO.read((InputStream)fileInputStream, byArray, 0, byArray.length);
        fileInputStream.close();
        return n;
    }

    public static int loadBytes(CharSequence charSequence, byte[] byArray) throws IOException {
        return BinIO.loadBytes(new File(charSequence.toString()), byArray);
    }

    public static byte[] loadBytes(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 1L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        byte[] byArray = new byte[(int)l];
        if ((long)BinIO.read((InputStream)fileInputStream, byArray, 0, (int)l) < l) {
            throw new EOFException();
        }
        fileInputStream.close();
        return byArray;
    }

    public static byte[] loadBytes(CharSequence charSequence) throws IOException {
        return BinIO.loadBytes(new File(charSequence.toString()));
    }

    public static void storeBytes(byte[] byArray, int n, int n2, DataOutput dataOutput) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        BinIO.write(dataOutput, byArray, n, n2);
    }

    public static void storeBytes(byte[] byArray, DataOutput dataOutput) throws IOException {
        BinIO.write(dataOutput, byArray, 0, byArray.length);
    }

    public static void storeBytes(byte[] byArray, int n, int n2, File file) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        FastBufferedOutputStream fastBufferedOutputStream = new FastBufferedOutputStream(new FileOutputStream(file));
        BinIO.write((OutputStream)fastBufferedOutputStream, byArray, n, n2);
        ((OutputStream)fastBufferedOutputStream).close();
    }

    public static void storeBytes(byte[] byArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeBytes(byArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeBytes(byte[] byArray, File file) throws IOException {
        FastBufferedOutputStream fastBufferedOutputStream = new FastBufferedOutputStream(new FileOutputStream(file));
        BinIO.write((OutputStream)fastBufferedOutputStream, byArray, 0, byArray.length);
        ((OutputStream)fastBufferedOutputStream).close();
    }

    public static void storeBytes(byte[] byArray, CharSequence charSequence) throws IOException {
        BinIO.storeBytes(byArray, new File(charSequence.toString()));
    }

    public static long loadBytes(DataInput dataInput, byte[][] byArray, long l, long l2) throws IOException {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                byte[] byArray2 = byArray[i];
                int n = (int)Math.min((long)byArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    byArray2[j] = dataInput.readByte();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadBytes(DataInput dataInput, byte[][] byArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < byArray.length; ++i) {
                byte[] byArray2 = byArray[i];
                int n = byArray2.length;
                for (int j = 0; j < n; ++j) {
                    byArray2[j] = dataInput.readByte();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadBytes(File file, byte[][] byArray, long l, long l2) throws IOException {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        long l3 = BinIO.read((InputStream)fileInputStream, byArray, l, l2);
        fileInputStream.close();
        return l3;
    }

    public static long loadBytes(CharSequence charSequence, byte[][] byArray, long l, long l2) throws IOException {
        return BinIO.loadBytes(new File(charSequence.toString()), byArray, l, l2);
    }

    public static long loadBytes(File file, byte[][] byArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = BinIO.read((InputStream)fileInputStream, byArray, 0L, ByteBigArrays.length(byArray));
        fileInputStream.close();
        return l;
    }

    public static long loadBytes(CharSequence charSequence, byte[][] byArray) throws IOException {
        return BinIO.loadBytes(new File(charSequence.toString()), byArray);
    }

    public static byte[][] loadBytesBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 1L;
        byte[][] byArray = ByteBigArrays.newBigArray(l);
        if (BinIO.read((InputStream)fileInputStream, byArray, 0L, l) < l) {
            throw new EOFException();
        }
        fileInputStream.close();
        return byArray;
    }

    public static byte[][] loadBytesBig(CharSequence charSequence) throws IOException {
        return BinIO.loadBytesBig(new File(charSequence.toString()));
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, DataOutput dataOutput) throws IOException {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        BinIO.write(dataOutput, byArray, l, l2);
    }

    public static void storeBytes(byte[][] byArray, DataOutput dataOutput) throws IOException {
        BinIO.write(dataOutput, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, File file) throws IOException {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        FastBufferedOutputStream fastBufferedOutputStream = new FastBufferedOutputStream(new FileOutputStream(file));
        BinIO.write((OutputStream)fastBufferedOutputStream, byArray, l, l2);
        ((OutputStream)fastBufferedOutputStream).close();
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeBytes(byArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeBytes(byte[][] byArray, File file) throws IOException {
        FastBufferedOutputStream fastBufferedOutputStream = new FastBufferedOutputStream(new FileOutputStream(file));
        BinIO.write((OutputStream)fastBufferedOutputStream, byArray, 0L, ByteBigArrays.length(byArray));
        ((OutputStream)fastBufferedOutputStream).close();
    }

    public static void storeBytes(byte[][] byArray, CharSequence charSequence) throws IOException {
        BinIO.storeBytes(byArray, new File(charSequence.toString()));
    }

    public static void storeBytes(ByteIterator byteIterator, DataOutput dataOutput) throws IOException {
        while (byteIterator.hasNext()) {
            dataOutput.writeByte(byteIterator.nextByte());
        }
    }

    public static void storeBytes(ByteIterator byteIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (byteIterator.hasNext()) {
            dataOutputStream.writeByte(byteIterator.nextByte());
        }
        dataOutputStream.close();
    }

    public static void storeBytes(ByteIterator byteIterator, CharSequence charSequence) throws IOException {
        BinIO.storeBytes(byteIterator, new File(charSequence.toString()));
    }

    public static ByteIterator asByteIterator(DataInput dataInput) {
        return new ByteDataInputWrapper(dataInput);
    }

    public static ByteIterator asByteIterator(File file) throws IOException {
        return new ByteDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static ByteIterator asByteIterator(CharSequence charSequence) throws IOException {
        return BinIO.asByteIterator(new File(charSequence.toString()));
    }

    public static ByteIterable asByteIterable(File file) {
        return () -> BinIO.lambda$asByteIterable$0(file);
    }

    public static ByteIterable asByteIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asByteIterable$1(charSequence);
    }

    public static int loadInts(DataInput dataInput, int[] nArray, int n, int n2) throws IOException {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                nArray[n3 + n] = dataInput.readInt();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadInts(DataInput dataInput, int[] nArray) throws IOException {
        int n = 0;
        try {
            int n2 = nArray.length;
            for (n = 0; n < n2; ++n) {
                nArray[n] = dataInput.readInt();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadInts(File file, int[] nArray, int n, int n2) throws IOException {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                nArray[n3 + n] = dataInputStream.readInt();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadInts(CharSequence charSequence, int[] nArray, int n, int n2) throws IOException {
        return BinIO.loadInts(new File(charSequence.toString()), nArray, n, n2);
    }

    public static int loadInts(File file, int[] nArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = nArray.length;
            for (n = 0; n < n2; ++n) {
                nArray[n] = dataInputStream.readInt();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadInts(CharSequence charSequence, int[] nArray) throws IOException {
        return BinIO.loadInts(new File(charSequence.toString()), nArray);
    }

    public static int[] loadInts(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 4L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        int[] nArray = new int[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            nArray[n] = dataInputStream.readInt();
            ++n;
        }
        dataInputStream.close();
        return nArray;
    }

    public static int[] loadInts(CharSequence charSequence) throws IOException {
        return BinIO.loadInts(new File(charSequence.toString()));
    }

    public static void storeInts(int[] nArray, int n, int n2, DataOutput dataOutput) throws IOException {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeInt(nArray[n + i]);
        }
    }

    public static void storeInts(int[] nArray, DataOutput dataOutput) throws IOException {
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeInt(nArray[i]);
        }
    }

    public static void storeInts(int[] nArray, int n, int n2, File file) throws IOException {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeInt(nArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeInts(int[] nArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeInts(nArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeInts(int[] nArray, File file) throws IOException {
        int n = nArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeInt(nArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeInts(int[] nArray, CharSequence charSequence) throws IOException {
        BinIO.storeInts(nArray, new File(charSequence.toString()));
    }

    public static long loadInts(DataInput dataInput, int[][] nArray, long l, long l2) throws IOException {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                int[] nArray2 = nArray[i];
                int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    nArray2[j] = dataInput.readInt();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadInts(DataInput dataInput, int[][] nArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < nArray.length; ++i) {
                int[] nArray2 = nArray[i];
                int n = nArray2.length;
                for (int j = 0; j < n; ++j) {
                    nArray2[j] = dataInput.readInt();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadInts(File file, int[][] nArray, long l, long l2) throws IOException {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                int[] nArray2 = nArray[i];
                int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    nArray2[j] = dataInputStream.readInt();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadInts(CharSequence charSequence, int[][] nArray, long l, long l2) throws IOException {
        return BinIO.loadInts(new File(charSequence.toString()), nArray, l, l2);
    }

    public static long loadInts(File file, int[][] nArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < nArray.length; ++i) {
                int[] nArray2 = nArray[i];
                int n = nArray2.length;
                for (int j = 0; j < n; ++j) {
                    nArray2[j] = dataInputStream.readInt();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadInts(CharSequence charSequence, int[][] nArray) throws IOException {
        return BinIO.loadInts(new File(charSequence.toString()), nArray);
    }

    public static int[][] loadIntsBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 4L;
        int[][] nArray = IntBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < nArray.length; ++i) {
            int[] nArray2 = nArray[i];
            int n = nArray2.length;
            for (int j = 0; j < n; ++j) {
                nArray2[j] = dataInputStream.readInt();
            }
        }
        dataInputStream.close();
        return nArray;
    }

    public static int[][] loadIntsBig(CharSequence charSequence) throws IOException {
        return BinIO.loadIntsBig(new File(charSequence.toString()));
    }

    public static void storeInts(int[][] nArray, long l, long l2, DataOutput dataOutput) throws IOException {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            int[] nArray2 = nArray[i];
            int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeInt(nArray2[j]);
            }
        }
    }

    public static void storeInts(int[][] nArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < nArray.length; ++i) {
            int[] nArray2 = nArray[i];
            int n = nArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeInt(nArray2[j]);
            }
        }
    }

    public static void storeInts(int[][] nArray, long l, long l2, File file) throws IOException {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            int[] nArray2 = nArray[i];
            int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeInt(nArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeInts(int[][] nArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeInts(nArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeInts(int[][] nArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < nArray.length; ++i) {
            int[] nArray2 = nArray[i];
            int n = nArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeInt(nArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeInts(int[][] nArray, CharSequence charSequence) throws IOException {
        BinIO.storeInts(nArray, new File(charSequence.toString()));
    }

    public static void storeInts(IntIterator intIterator, DataOutput dataOutput) throws IOException {
        while (intIterator.hasNext()) {
            dataOutput.writeInt(intIterator.nextInt());
        }
    }

    public static void storeInts(IntIterator intIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (intIterator.hasNext()) {
            dataOutputStream.writeInt(intIterator.nextInt());
        }
        dataOutputStream.close();
    }

    public static void storeInts(IntIterator intIterator, CharSequence charSequence) throws IOException {
        BinIO.storeInts(intIterator, new File(charSequence.toString()));
    }

    public static IntIterator asIntIterator(DataInput dataInput) {
        return new IntDataInputWrapper(dataInput);
    }

    public static IntIterator asIntIterator(File file) throws IOException {
        return new IntDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static IntIterator asIntIterator(CharSequence charSequence) throws IOException {
        return BinIO.asIntIterator(new File(charSequence.toString()));
    }

    public static IntIterable asIntIterable(File file) {
        return () -> BinIO.lambda$asIntIterable$2(file);
    }

    public static IntIterable asIntIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asIntIterable$3(charSequence);
    }

    public static int loadLongs(DataInput dataInput, long[] lArray, int n, int n2) throws IOException {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                lArray[n3 + n] = dataInput.readLong();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadLongs(DataInput dataInput, long[] lArray) throws IOException {
        int n = 0;
        try {
            int n2 = lArray.length;
            for (n = 0; n < n2; ++n) {
                lArray[n] = dataInput.readLong();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadLongs(File file, long[] lArray, int n, int n2) throws IOException {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                lArray[n3 + n] = dataInputStream.readLong();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadLongs(CharSequence charSequence, long[] lArray, int n, int n2) throws IOException {
        return BinIO.loadLongs(new File(charSequence.toString()), lArray, n, n2);
    }

    public static int loadLongs(File file, long[] lArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = lArray.length;
            for (n = 0; n < n2; ++n) {
                lArray[n] = dataInputStream.readLong();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadLongs(CharSequence charSequence, long[] lArray) throws IOException {
        return BinIO.loadLongs(new File(charSequence.toString()), lArray);
    }

    public static long[] loadLongs(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 8L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        long[] lArray = new long[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            lArray[n] = dataInputStream.readLong();
            ++n;
        }
        dataInputStream.close();
        return lArray;
    }

    public static long[] loadLongs(CharSequence charSequence) throws IOException {
        return BinIO.loadLongs(new File(charSequence.toString()));
    }

    public static void storeLongs(long[] lArray, int n, int n2, DataOutput dataOutput) throws IOException {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeLong(lArray[n + i]);
        }
    }

    public static void storeLongs(long[] lArray, DataOutput dataOutput) throws IOException {
        int n = lArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeLong(lArray[i]);
        }
    }

    public static void storeLongs(long[] lArray, int n, int n2, File file) throws IOException {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeLong(lArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeLongs(long[] lArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeLongs(lArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeLongs(long[] lArray, File file) throws IOException {
        int n = lArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeLong(lArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeLongs(long[] lArray, CharSequence charSequence) throws IOException {
        BinIO.storeLongs(lArray, new File(charSequence.toString()));
    }

    public static long loadLongs(DataInput dataInput, long[][] lArray, long l, long l2) throws IOException {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                long[] lArray2 = lArray[i];
                int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    lArray2[j] = dataInput.readLong();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadLongs(DataInput dataInput, long[][] lArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < lArray.length; ++i) {
                long[] lArray2 = lArray[i];
                int n = lArray2.length;
                for (int j = 0; j < n; ++j) {
                    lArray2[j] = dataInput.readLong();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadLongs(File file, long[][] lArray, long l, long l2) throws IOException {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                long[] lArray2 = lArray[i];
                int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    lArray2[j] = dataInputStream.readLong();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadLongs(CharSequence charSequence, long[][] lArray, long l, long l2) throws IOException {
        return BinIO.loadLongs(new File(charSequence.toString()), lArray, l, l2);
    }

    public static long loadLongs(File file, long[][] lArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < lArray.length; ++i) {
                long[] lArray2 = lArray[i];
                int n = lArray2.length;
                for (int j = 0; j < n; ++j) {
                    lArray2[j] = dataInputStream.readLong();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadLongs(CharSequence charSequence, long[][] lArray) throws IOException {
        return BinIO.loadLongs(new File(charSequence.toString()), lArray);
    }

    public static long[][] loadLongsBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 8L;
        long[][] lArray = LongBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < lArray.length; ++i) {
            long[] lArray2 = lArray[i];
            int n = lArray2.length;
            for (int j = 0; j < n; ++j) {
                lArray2[j] = dataInputStream.readLong();
            }
        }
        dataInputStream.close();
        return lArray;
    }

    public static long[][] loadLongsBig(CharSequence charSequence) throws IOException {
        return BinIO.loadLongsBig(new File(charSequence.toString()));
    }

    public static void storeLongs(long[][] lArray, long l, long l2, DataOutput dataOutput) throws IOException {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            long[] lArray2 = lArray[i];
            int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeLong(lArray2[j]);
            }
        }
    }

    public static void storeLongs(long[][] lArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < lArray.length; ++i) {
            long[] lArray2 = lArray[i];
            int n = lArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeLong(lArray2[j]);
            }
        }
    }

    public static void storeLongs(long[][] lArray, long l, long l2, File file) throws IOException {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            long[] lArray2 = lArray[i];
            int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeLong(lArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeLongs(long[][] lArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeLongs(lArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeLongs(long[][] lArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < lArray.length; ++i) {
            long[] lArray2 = lArray[i];
            int n = lArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeLong(lArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeLongs(long[][] lArray, CharSequence charSequence) throws IOException {
        BinIO.storeLongs(lArray, new File(charSequence.toString()));
    }

    public static void storeLongs(LongIterator longIterator, DataOutput dataOutput) throws IOException {
        while (longIterator.hasNext()) {
            dataOutput.writeLong(longIterator.nextLong());
        }
    }

    public static void storeLongs(LongIterator longIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (longIterator.hasNext()) {
            dataOutputStream.writeLong(longIterator.nextLong());
        }
        dataOutputStream.close();
    }

    public static void storeLongs(LongIterator longIterator, CharSequence charSequence) throws IOException {
        BinIO.storeLongs(longIterator, new File(charSequence.toString()));
    }

    public static LongIterator asLongIterator(DataInput dataInput) {
        return new LongDataInputWrapper(dataInput);
    }

    public static LongIterator asLongIterator(File file) throws IOException {
        return new LongDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static LongIterator asLongIterator(CharSequence charSequence) throws IOException {
        return BinIO.asLongIterator(new File(charSequence.toString()));
    }

    public static LongIterable asLongIterable(File file) {
        return () -> BinIO.lambda$asLongIterable$4(file);
    }

    public static LongIterable asLongIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asLongIterable$5(charSequence);
    }

    public static int loadDoubles(DataInput dataInput, double[] dArray, int n, int n2) throws IOException {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                dArray[n3 + n] = dataInput.readDouble();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadDoubles(DataInput dataInput, double[] dArray) throws IOException {
        int n = 0;
        try {
            int n2 = dArray.length;
            for (n = 0; n < n2; ++n) {
                dArray[n] = dataInput.readDouble();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadDoubles(File file, double[] dArray, int n, int n2) throws IOException {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                dArray[n3 + n] = dataInputStream.readDouble();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadDoubles(CharSequence charSequence, double[] dArray, int n, int n2) throws IOException {
        return BinIO.loadDoubles(new File(charSequence.toString()), dArray, n, n2);
    }

    public static int loadDoubles(File file, double[] dArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = dArray.length;
            for (n = 0; n < n2; ++n) {
                dArray[n] = dataInputStream.readDouble();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadDoubles(CharSequence charSequence, double[] dArray) throws IOException {
        return BinIO.loadDoubles(new File(charSequence.toString()), dArray);
    }

    public static double[] loadDoubles(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 8L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        double[] dArray = new double[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            dArray[n] = dataInputStream.readDouble();
            ++n;
        }
        dataInputStream.close();
        return dArray;
    }

    public static double[] loadDoubles(CharSequence charSequence) throws IOException {
        return BinIO.loadDoubles(new File(charSequence.toString()));
    }

    public static void storeDoubles(double[] dArray, int n, int n2, DataOutput dataOutput) throws IOException {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeDouble(dArray[n + i]);
        }
    }

    public static void storeDoubles(double[] dArray, DataOutput dataOutput) throws IOException {
        int n = dArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeDouble(dArray[i]);
        }
    }

    public static void storeDoubles(double[] dArray, int n, int n2, File file) throws IOException {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeDouble(dArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeDoubles(double[] dArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeDoubles(dArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeDoubles(double[] dArray, File file) throws IOException {
        int n = dArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeDouble(dArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeDoubles(double[] dArray, CharSequence charSequence) throws IOException {
        BinIO.storeDoubles(dArray, new File(charSequence.toString()));
    }

    public static long loadDoubles(DataInput dataInput, double[][] dArray, long l, long l2) throws IOException {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                double[] dArray2 = dArray[i];
                int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    dArray2[j] = dataInput.readDouble();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadDoubles(DataInput dataInput, double[][] dArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < dArray.length; ++i) {
                double[] dArray2 = dArray[i];
                int n = dArray2.length;
                for (int j = 0; j < n; ++j) {
                    dArray2[j] = dataInput.readDouble();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadDoubles(File file, double[][] dArray, long l, long l2) throws IOException {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                double[] dArray2 = dArray[i];
                int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    dArray2[j] = dataInputStream.readDouble();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadDoubles(CharSequence charSequence, double[][] dArray, long l, long l2) throws IOException {
        return BinIO.loadDoubles(new File(charSequence.toString()), dArray, l, l2);
    }

    public static long loadDoubles(File file, double[][] dArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < dArray.length; ++i) {
                double[] dArray2 = dArray[i];
                int n = dArray2.length;
                for (int j = 0; j < n; ++j) {
                    dArray2[j] = dataInputStream.readDouble();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadDoubles(CharSequence charSequence, double[][] dArray) throws IOException {
        return BinIO.loadDoubles(new File(charSequence.toString()), dArray);
    }

    public static double[][] loadDoublesBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 8L;
        double[][] dArray = DoubleBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < dArray.length; ++i) {
            double[] dArray2 = dArray[i];
            int n = dArray2.length;
            for (int j = 0; j < n; ++j) {
                dArray2[j] = dataInputStream.readDouble();
            }
        }
        dataInputStream.close();
        return dArray;
    }

    public static double[][] loadDoublesBig(CharSequence charSequence) throws IOException {
        return BinIO.loadDoublesBig(new File(charSequence.toString()));
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, DataOutput dataOutput) throws IOException {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            double[] dArray2 = dArray[i];
            int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeDouble(dArray2[j]);
            }
        }
    }

    public static void storeDoubles(double[][] dArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < dArray.length; ++i) {
            double[] dArray2 = dArray[i];
            int n = dArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeDouble(dArray2[j]);
            }
        }
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, File file) throws IOException {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            double[] dArray2 = dArray[i];
            int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeDouble(dArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeDoubles(dArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeDoubles(double[][] dArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < dArray.length; ++i) {
            double[] dArray2 = dArray[i];
            int n = dArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeDouble(dArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeDoubles(double[][] dArray, CharSequence charSequence) throws IOException {
        BinIO.storeDoubles(dArray, new File(charSequence.toString()));
    }

    public static void storeDoubles(DoubleIterator doubleIterator, DataOutput dataOutput) throws IOException {
        while (doubleIterator.hasNext()) {
            dataOutput.writeDouble(doubleIterator.nextDouble());
        }
    }

    public static void storeDoubles(DoubleIterator doubleIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (doubleIterator.hasNext()) {
            dataOutputStream.writeDouble(doubleIterator.nextDouble());
        }
        dataOutputStream.close();
    }

    public static void storeDoubles(DoubleIterator doubleIterator, CharSequence charSequence) throws IOException {
        BinIO.storeDoubles(doubleIterator, new File(charSequence.toString()));
    }

    public static DoubleIterator asDoubleIterator(DataInput dataInput) {
        return new DoubleDataInputWrapper(dataInput);
    }

    public static DoubleIterator asDoubleIterator(File file) throws IOException {
        return new DoubleDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static DoubleIterator asDoubleIterator(CharSequence charSequence) throws IOException {
        return BinIO.asDoubleIterator(new File(charSequence.toString()));
    }

    public static DoubleIterable asDoubleIterable(File file) {
        return () -> BinIO.lambda$asDoubleIterable$6(file);
    }

    public static DoubleIterable asDoubleIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asDoubleIterable$7(charSequence);
    }

    public static int loadBooleans(DataInput dataInput, boolean[] blArray, int n, int n2) throws IOException {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                blArray[n3 + n] = dataInput.readBoolean();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadBooleans(DataInput dataInput, boolean[] blArray) throws IOException {
        int n = 0;
        try {
            int n2 = blArray.length;
            for (n = 0; n < n2; ++n) {
                blArray[n] = dataInput.readBoolean();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadBooleans(File file, boolean[] blArray, int n, int n2) throws IOException {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                blArray[n3 + n] = dataInputStream.readBoolean();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadBooleans(CharSequence charSequence, boolean[] blArray, int n, int n2) throws IOException {
        return BinIO.loadBooleans(new File(charSequence.toString()), blArray, n, n2);
    }

    public static int loadBooleans(File file, boolean[] blArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = blArray.length;
            for (n = 0; n < n2; ++n) {
                blArray[n] = dataInputStream.readBoolean();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadBooleans(CharSequence charSequence, boolean[] blArray) throws IOException {
        return BinIO.loadBooleans(new File(charSequence.toString()), blArray);
    }

    public static boolean[] loadBooleans(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size();
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        boolean[] blArray = new boolean[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            blArray[n] = dataInputStream.readBoolean();
            ++n;
        }
        dataInputStream.close();
        return blArray;
    }

    public static boolean[] loadBooleans(CharSequence charSequence) throws IOException {
        return BinIO.loadBooleans(new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, DataOutput dataOutput) throws IOException {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeBoolean(blArray[n + i]);
        }
    }

    public static void storeBooleans(boolean[] blArray, DataOutput dataOutput) throws IOException {
        int n = blArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeBoolean(blArray[i]);
        }
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, File file) throws IOException {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeBoolean(blArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeBooleans(blArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[] blArray, File file) throws IOException {
        int n = blArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeBoolean(blArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeBooleans(boolean[] blArray, CharSequence charSequence) throws IOException {
        BinIO.storeBooleans(blArray, new File(charSequence.toString()));
    }

    public static long loadBooleans(DataInput dataInput, boolean[][] blArray, long l, long l2) throws IOException {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                boolean[] blArray2 = blArray[i];
                int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    blArray2[j] = dataInput.readBoolean();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadBooleans(DataInput dataInput, boolean[][] blArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < blArray.length; ++i) {
                boolean[] blArray2 = blArray[i];
                int n = blArray2.length;
                for (int j = 0; j < n; ++j) {
                    blArray2[j] = dataInput.readBoolean();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadBooleans(File file, boolean[][] blArray, long l, long l2) throws IOException {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                boolean[] blArray2 = blArray[i];
                int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    blArray2[j] = dataInputStream.readBoolean();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadBooleans(CharSequence charSequence, boolean[][] blArray, long l, long l2) throws IOException {
        return BinIO.loadBooleans(new File(charSequence.toString()), blArray, l, l2);
    }

    public static long loadBooleans(File file, boolean[][] blArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < blArray.length; ++i) {
                boolean[] blArray2 = blArray[i];
                int n = blArray2.length;
                for (int j = 0; j < n; ++j) {
                    blArray2[j] = dataInputStream.readBoolean();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadBooleans(CharSequence charSequence, boolean[][] blArray) throws IOException {
        return BinIO.loadBooleans(new File(charSequence.toString()), blArray);
    }

    public static boolean[][] loadBooleansBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size();
        boolean[][] blArray = BooleanBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < blArray.length; ++i) {
            boolean[] blArray2 = blArray[i];
            int n = blArray2.length;
            for (int j = 0; j < n; ++j) {
                blArray2[j] = dataInputStream.readBoolean();
            }
        }
        dataInputStream.close();
        return blArray;
    }

    public static boolean[][] loadBooleansBig(CharSequence charSequence) throws IOException {
        return BinIO.loadBooleansBig(new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, DataOutput dataOutput) throws IOException {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            boolean[] blArray2 = blArray[i];
            int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeBoolean(blArray2[j]);
            }
        }
    }

    public static void storeBooleans(boolean[][] blArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < blArray.length; ++i) {
            boolean[] blArray2 = blArray[i];
            int n = blArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeBoolean(blArray2[j]);
            }
        }
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, File file) throws IOException {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            boolean[] blArray2 = blArray[i];
            int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeBoolean(blArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeBooleans(blArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[][] blArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < blArray.length; ++i) {
            boolean[] blArray2 = blArray[i];
            int n = blArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeBoolean(blArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeBooleans(boolean[][] blArray, CharSequence charSequence) throws IOException {
        BinIO.storeBooleans(blArray, new File(charSequence.toString()));
    }

    public static void storeBooleans(BooleanIterator booleanIterator, DataOutput dataOutput) throws IOException {
        while (booleanIterator.hasNext()) {
            dataOutput.writeBoolean(booleanIterator.nextBoolean());
        }
    }

    public static void storeBooleans(BooleanIterator booleanIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (booleanIterator.hasNext()) {
            dataOutputStream.writeBoolean(booleanIterator.nextBoolean());
        }
        dataOutputStream.close();
    }

    public static void storeBooleans(BooleanIterator booleanIterator, CharSequence charSequence) throws IOException {
        BinIO.storeBooleans(booleanIterator, new File(charSequence.toString()));
    }

    public static BooleanIterator asBooleanIterator(DataInput dataInput) {
        return new BooleanDataInputWrapper(dataInput);
    }

    public static BooleanIterator asBooleanIterator(File file) throws IOException {
        return new BooleanDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static BooleanIterator asBooleanIterator(CharSequence charSequence) throws IOException {
        return BinIO.asBooleanIterator(new File(charSequence.toString()));
    }

    public static BooleanIterable asBooleanIterable(File file) {
        return () -> BinIO.lambda$asBooleanIterable$8(file);
    }

    public static BooleanIterable asBooleanIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asBooleanIterable$9(charSequence);
    }

    public static int loadShorts(DataInput dataInput, short[] sArray, int n, int n2) throws IOException {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                sArray[n3 + n] = dataInput.readShort();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadShorts(DataInput dataInput, short[] sArray) throws IOException {
        int n = 0;
        try {
            int n2 = sArray.length;
            for (n = 0; n < n2; ++n) {
                sArray[n] = dataInput.readShort();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadShorts(File file, short[] sArray, int n, int n2) throws IOException {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                sArray[n3 + n] = dataInputStream.readShort();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadShorts(CharSequence charSequence, short[] sArray, int n, int n2) throws IOException {
        return BinIO.loadShorts(new File(charSequence.toString()), sArray, n, n2);
    }

    public static int loadShorts(File file, short[] sArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = sArray.length;
            for (n = 0; n < n2; ++n) {
                sArray[n] = dataInputStream.readShort();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadShorts(CharSequence charSequence, short[] sArray) throws IOException {
        return BinIO.loadShorts(new File(charSequence.toString()), sArray);
    }

    public static short[] loadShorts(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 2L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        short[] sArray = new short[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            sArray[n] = dataInputStream.readShort();
            ++n;
        }
        dataInputStream.close();
        return sArray;
    }

    public static short[] loadShorts(CharSequence charSequence) throws IOException {
        return BinIO.loadShorts(new File(charSequence.toString()));
    }

    public static void storeShorts(short[] sArray, int n, int n2, DataOutput dataOutput) throws IOException {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeShort(sArray[n + i]);
        }
    }

    public static void storeShorts(short[] sArray, DataOutput dataOutput) throws IOException {
        int n = sArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeShort(sArray[i]);
        }
    }

    public static void storeShorts(short[] sArray, int n, int n2, File file) throws IOException {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeShort(sArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeShorts(short[] sArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeShorts(sArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeShorts(short[] sArray, File file) throws IOException {
        int n = sArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeShort(sArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeShorts(short[] sArray, CharSequence charSequence) throws IOException {
        BinIO.storeShorts(sArray, new File(charSequence.toString()));
    }

    public static long loadShorts(DataInput dataInput, short[][] sArray, long l, long l2) throws IOException {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                short[] sArray2 = sArray[i];
                int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    sArray2[j] = dataInput.readShort();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadShorts(DataInput dataInput, short[][] sArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < sArray.length; ++i) {
                short[] sArray2 = sArray[i];
                int n = sArray2.length;
                for (int j = 0; j < n; ++j) {
                    sArray2[j] = dataInput.readShort();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadShorts(File file, short[][] sArray, long l, long l2) throws IOException {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                short[] sArray2 = sArray[i];
                int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    sArray2[j] = dataInputStream.readShort();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadShorts(CharSequence charSequence, short[][] sArray, long l, long l2) throws IOException {
        return BinIO.loadShorts(new File(charSequence.toString()), sArray, l, l2);
    }

    public static long loadShorts(File file, short[][] sArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < sArray.length; ++i) {
                short[] sArray2 = sArray[i];
                int n = sArray2.length;
                for (int j = 0; j < n; ++j) {
                    sArray2[j] = dataInputStream.readShort();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadShorts(CharSequence charSequence, short[][] sArray) throws IOException {
        return BinIO.loadShorts(new File(charSequence.toString()), sArray);
    }

    public static short[][] loadShortsBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 2L;
        short[][] sArray = ShortBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < sArray.length; ++i) {
            short[] sArray2 = sArray[i];
            int n = sArray2.length;
            for (int j = 0; j < n; ++j) {
                sArray2[j] = dataInputStream.readShort();
            }
        }
        dataInputStream.close();
        return sArray;
    }

    public static short[][] loadShortsBig(CharSequence charSequence) throws IOException {
        return BinIO.loadShortsBig(new File(charSequence.toString()));
    }

    public static void storeShorts(short[][] sArray, long l, long l2, DataOutput dataOutput) throws IOException {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            short[] sArray2 = sArray[i];
            int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeShort(sArray2[j]);
            }
        }
    }

    public static void storeShorts(short[][] sArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < sArray.length; ++i) {
            short[] sArray2 = sArray[i];
            int n = sArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeShort(sArray2[j]);
            }
        }
    }

    public static void storeShorts(short[][] sArray, long l, long l2, File file) throws IOException {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            short[] sArray2 = sArray[i];
            int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeShort(sArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeShorts(short[][] sArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeShorts(sArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeShorts(short[][] sArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < sArray.length; ++i) {
            short[] sArray2 = sArray[i];
            int n = sArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeShort(sArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeShorts(short[][] sArray, CharSequence charSequence) throws IOException {
        BinIO.storeShorts(sArray, new File(charSequence.toString()));
    }

    public static void storeShorts(ShortIterator shortIterator, DataOutput dataOutput) throws IOException {
        while (shortIterator.hasNext()) {
            dataOutput.writeShort(shortIterator.nextShort());
        }
    }

    public static void storeShorts(ShortIterator shortIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (shortIterator.hasNext()) {
            dataOutputStream.writeShort(shortIterator.nextShort());
        }
        dataOutputStream.close();
    }

    public static void storeShorts(ShortIterator shortIterator, CharSequence charSequence) throws IOException {
        BinIO.storeShorts(shortIterator, new File(charSequence.toString()));
    }

    public static ShortIterator asShortIterator(DataInput dataInput) {
        return new ShortDataInputWrapper(dataInput);
    }

    public static ShortIterator asShortIterator(File file) throws IOException {
        return new ShortDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static ShortIterator asShortIterator(CharSequence charSequence) throws IOException {
        return BinIO.asShortIterator(new File(charSequence.toString()));
    }

    public static ShortIterable asShortIterable(File file) {
        return () -> BinIO.lambda$asShortIterable$10(file);
    }

    public static ShortIterable asShortIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asShortIterable$11(charSequence);
    }

    public static int loadChars(DataInput dataInput, char[] cArray, int n, int n2) throws IOException {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                cArray[n3 + n] = dataInput.readChar();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadChars(DataInput dataInput, char[] cArray) throws IOException {
        int n = 0;
        try {
            int n2 = cArray.length;
            for (n = 0; n < n2; ++n) {
                cArray[n] = dataInput.readChar();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadChars(File file, char[] cArray, int n, int n2) throws IOException {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                cArray[n3 + n] = dataInputStream.readChar();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadChars(CharSequence charSequence, char[] cArray, int n, int n2) throws IOException {
        return BinIO.loadChars(new File(charSequence.toString()), cArray, n, n2);
    }

    public static int loadChars(File file, char[] cArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = cArray.length;
            for (n = 0; n < n2; ++n) {
                cArray[n] = dataInputStream.readChar();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadChars(CharSequence charSequence, char[] cArray) throws IOException {
        return BinIO.loadChars(new File(charSequence.toString()), cArray);
    }

    public static char[] loadChars(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 2L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        char[] cArray = new char[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            cArray[n] = dataInputStream.readChar();
            ++n;
        }
        dataInputStream.close();
        return cArray;
    }

    public static char[] loadChars(CharSequence charSequence) throws IOException {
        return BinIO.loadChars(new File(charSequence.toString()));
    }

    public static void storeChars(char[] cArray, int n, int n2, DataOutput dataOutput) throws IOException {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeChar(cArray[n + i]);
        }
    }

    public static void storeChars(char[] cArray, DataOutput dataOutput) throws IOException {
        int n = cArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeChar(cArray[i]);
        }
    }

    public static void storeChars(char[] cArray, int n, int n2, File file) throws IOException {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeChar(cArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeChars(char[] cArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeChars(cArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeChars(char[] cArray, File file) throws IOException {
        int n = cArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeChar(cArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeChars(char[] cArray, CharSequence charSequence) throws IOException {
        BinIO.storeChars(cArray, new File(charSequence.toString()));
    }

    public static long loadChars(DataInput dataInput, char[][] cArray, long l, long l2) throws IOException {
        CharBigArrays.ensureOffsetLength(cArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                char[] cArray2 = cArray[i];
                int n = (int)Math.min((long)cArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    cArray2[j] = dataInput.readChar();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadChars(DataInput dataInput, char[][] cArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < cArray.length; ++i) {
                char[] cArray2 = cArray[i];
                int n = cArray2.length;
                for (int j = 0; j < n; ++j) {
                    cArray2[j] = dataInput.readChar();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadChars(File file, char[][] cArray, long l, long l2) throws IOException {
        CharBigArrays.ensureOffsetLength(cArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                char[] cArray2 = cArray[i];
                int n = (int)Math.min((long)cArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    cArray2[j] = dataInputStream.readChar();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadChars(CharSequence charSequence, char[][] cArray, long l, long l2) throws IOException {
        return BinIO.loadChars(new File(charSequence.toString()), cArray, l, l2);
    }

    public static long loadChars(File file, char[][] cArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < cArray.length; ++i) {
                char[] cArray2 = cArray[i];
                int n = cArray2.length;
                for (int j = 0; j < n; ++j) {
                    cArray2[j] = dataInputStream.readChar();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadChars(CharSequence charSequence, char[][] cArray) throws IOException {
        return BinIO.loadChars(new File(charSequence.toString()), cArray);
    }

    public static char[][] loadCharsBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 2L;
        char[][] cArray = CharBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < cArray.length; ++i) {
            char[] cArray2 = cArray[i];
            int n = cArray2.length;
            for (int j = 0; j < n; ++j) {
                cArray2[j] = dataInputStream.readChar();
            }
        }
        dataInputStream.close();
        return cArray;
    }

    public static char[][] loadCharsBig(CharSequence charSequence) throws IOException {
        return BinIO.loadCharsBig(new File(charSequence.toString()));
    }

    public static void storeChars(char[][] cArray, long l, long l2, DataOutput dataOutput) throws IOException {
        CharBigArrays.ensureOffsetLength(cArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            char[] cArray2 = cArray[i];
            int n = (int)Math.min((long)cArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeChar(cArray2[j]);
            }
        }
    }

    public static void storeChars(char[][] cArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < cArray.length; ++i) {
            char[] cArray2 = cArray[i];
            int n = cArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeChar(cArray2[j]);
            }
        }
    }

    public static void storeChars(char[][] cArray, long l, long l2, File file) throws IOException {
        CharBigArrays.ensureOffsetLength(cArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            char[] cArray2 = cArray[i];
            int n = (int)Math.min((long)cArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeChar(cArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeChars(char[][] cArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeChars(cArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeChars(char[][] cArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < cArray.length; ++i) {
            char[] cArray2 = cArray[i];
            int n = cArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeChar(cArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeChars(char[][] cArray, CharSequence charSequence) throws IOException {
        BinIO.storeChars(cArray, new File(charSequence.toString()));
    }

    public static void storeChars(CharIterator charIterator, DataOutput dataOutput) throws IOException {
        while (charIterator.hasNext()) {
            dataOutput.writeChar(charIterator.nextChar());
        }
    }

    public static void storeChars(CharIterator charIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (charIterator.hasNext()) {
            dataOutputStream.writeChar(charIterator.nextChar());
        }
        dataOutputStream.close();
    }

    public static void storeChars(CharIterator charIterator, CharSequence charSequence) throws IOException {
        BinIO.storeChars(charIterator, new File(charSequence.toString()));
    }

    public static CharIterator asCharIterator(DataInput dataInput) {
        return new CharDataInputWrapper(dataInput);
    }

    public static CharIterator asCharIterator(File file) throws IOException {
        return new CharDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static CharIterator asCharIterator(CharSequence charSequence) throws IOException {
        return BinIO.asCharIterator(new File(charSequence.toString()));
    }

    public static CharIterable asCharIterable(File file) {
        return () -> BinIO.lambda$asCharIterable$12(file);
    }

    public static CharIterable asCharIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asCharIterable$13(charSequence);
    }

    public static int loadFloats(DataInput dataInput, float[] fArray, int n, int n2) throws IOException {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                fArray[n3 + n] = dataInput.readFloat();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadFloats(DataInput dataInput, float[] fArray) throws IOException {
        int n = 0;
        try {
            int n2 = fArray.length;
            for (n = 0; n < n2; ++n) {
                fArray[n] = dataInput.readFloat();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n;
    }

    public static int loadFloats(File file, float[] fArray, int n, int n2) throws IOException {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n3 = 0;
        try {
            for (n3 = 0; n3 < n2; ++n3) {
                fArray[n3 + n] = dataInputStream.readFloat();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n3;
    }

    public static int loadFloats(CharSequence charSequence, float[] fArray, int n, int n2) throws IOException {
        return BinIO.loadFloats(new File(charSequence.toString()), fArray, n, n2);
    }

    public static int loadFloats(File file, float[] fArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        try {
            int n2 = fArray.length;
            for (n = 0; n < n2; ++n) {
                fArray[n] = dataInputStream.readFloat();
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return n;
    }

    public static int loadFloats(CharSequence charSequence, float[] fArray) throws IOException {
        return BinIO.loadFloats(new File(charSequence.toString()), fArray);
    }

    public static float[] loadFloats(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 4L;
        if (l > Integer.MAX_VALUE) {
            fileInputStream.close();
            throw new IllegalArgumentException("File too long: " + fileInputStream.getChannel().size() + " bytes (" + l + " elements)");
        }
        float[] fArray = new float[(int)l];
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        int n = 0;
        while ((long)n < l) {
            fArray[n] = dataInputStream.readFloat();
            ++n;
        }
        dataInputStream.close();
        return fArray;
    }

    public static float[] loadFloats(CharSequence charSequence) throws IOException {
        return BinIO.loadFloats(new File(charSequence.toString()));
    }

    public static void storeFloats(float[] fArray, int n, int n2, DataOutput dataOutput) throws IOException {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            dataOutput.writeFloat(fArray[n + i]);
        }
    }

    public static void storeFloats(float[] fArray, DataOutput dataOutput) throws IOException {
        int n = fArray.length;
        for (int i = 0; i < n; ++i) {
            dataOutput.writeFloat(fArray[i]);
        }
    }

    public static void storeFloats(float[] fArray, int n, int n2, File file) throws IOException {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n2; ++i) {
            dataOutputStream.writeFloat(fArray[n + i]);
        }
        dataOutputStream.close();
    }

    public static void storeFloats(float[] fArray, int n, int n2, CharSequence charSequence) throws IOException {
        BinIO.storeFloats(fArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeFloats(float[] fArray, File file) throws IOException {
        int n = fArray.length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < n; ++i) {
            dataOutputStream.writeFloat(fArray[i]);
        }
        dataOutputStream.close();
    }

    public static void storeFloats(float[] fArray, CharSequence charSequence) throws IOException {
        BinIO.storeFloats(fArray, new File(charSequence.toString()));
    }

    public static long loadFloats(DataInput dataInput, float[][] fArray, long l, long l2) throws IOException {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                float[] fArray2 = fArray[i];
                int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    fArray2[j] = dataInput.readFloat();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadFloats(DataInput dataInput, float[][] fArray) throws IOException {
        long l = 0L;
        try {
            for (int i = 0; i < fArray.length; ++i) {
                float[] fArray2 = fArray[i];
                int n = fArray2.length;
                for (int j = 0; j < n; ++j) {
                    fArray2[j] = dataInput.readFloat();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l;
    }

    public static long loadFloats(File file, float[][] fArray, long l, long l2) throws IOException {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                float[] fArray2 = fArray[i];
                int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    fArray2[j] = dataInputStream.readFloat();
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l3;
    }

    public static long loadFloats(CharSequence charSequence, float[][] fArray, long l, long l2) throws IOException {
        return BinIO.loadFloats(new File(charSequence.toString()), fArray, l, l2);
    }

    public static long loadFloats(File file, float[][] fArray) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        long l = 0L;
        try {
            for (int i = 0; i < fArray.length; ++i) {
                float[] fArray2 = fArray[i];
                int n = fArray2.length;
                for (int j = 0; j < n; ++j) {
                    fArray2[j] = dataInputStream.readFloat();
                    ++l;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        dataInputStream.close();
        return l;
    }

    public static long loadFloats(CharSequence charSequence, float[][] fArray) throws IOException {
        return BinIO.loadFloats(new File(charSequence.toString()), fArray);
    }

    public static float[][] loadFloatsBig(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        long l = fileInputStream.getChannel().size() / 4L;
        float[][] fArray = FloatBigArrays.newBigArray(l);
        DataInputStream dataInputStream = new DataInputStream(new FastBufferedInputStream(fileInputStream));
        for (int i = 0; i < fArray.length; ++i) {
            float[] fArray2 = fArray[i];
            int n = fArray2.length;
            for (int j = 0; j < n; ++j) {
                fArray2[j] = dataInputStream.readFloat();
            }
        }
        dataInputStream.close();
        return fArray;
    }

    public static float[][] loadFloatsBig(CharSequence charSequence) throws IOException {
        return BinIO.loadFloatsBig(new File(charSequence.toString()));
    }

    public static void storeFloats(float[][] fArray, long l, long l2, DataOutput dataOutput) throws IOException {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            float[] fArray2 = fArray[i];
            int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutput.writeFloat(fArray2[j]);
            }
        }
    }

    public static void storeFloats(float[][] fArray, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < fArray.length; ++i) {
            float[] fArray2 = fArray[i];
            int n = fArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutput.writeFloat(fArray2[j]);
            }
        }
    }

    public static void storeFloats(float[][] fArray, long l, long l2, File file) throws IOException {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            float[] fArray2 = fArray[i];
            int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                dataOutputStream.writeFloat(fArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeFloats(float[][] fArray, long l, long l2, CharSequence charSequence) throws IOException {
        BinIO.storeFloats(fArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeFloats(float[][] fArray, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < fArray.length; ++i) {
            float[] fArray2 = fArray[i];
            int n = fArray2.length;
            for (int j = 0; j < n; ++j) {
                dataOutputStream.writeFloat(fArray2[j]);
            }
        }
        dataOutputStream.close();
    }

    public static void storeFloats(float[][] fArray, CharSequence charSequence) throws IOException {
        BinIO.storeFloats(fArray, new File(charSequence.toString()));
    }

    public static void storeFloats(FloatIterator floatIterator, DataOutput dataOutput) throws IOException {
        while (floatIterator.hasNext()) {
            dataOutput.writeFloat(floatIterator.nextFloat());
        }
    }

    public static void storeFloats(FloatIterator floatIterator, File file) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        while (floatIterator.hasNext()) {
            dataOutputStream.writeFloat(floatIterator.nextFloat());
        }
        dataOutputStream.close();
    }

    public static void storeFloats(FloatIterator floatIterator, CharSequence charSequence) throws IOException {
        BinIO.storeFloats(floatIterator, new File(charSequence.toString()));
    }

    public static FloatIterator asFloatIterator(DataInput dataInput) {
        return new FloatDataInputWrapper(dataInput);
    }

    public static FloatIterator asFloatIterator(File file) throws IOException {
        return new FloatDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
    }

    public static FloatIterator asFloatIterator(CharSequence charSequence) throws IOException {
        return BinIO.asFloatIterator(new File(charSequence.toString()));
    }

    public static FloatIterable asFloatIterable(File file) {
        return () -> BinIO.lambda$asFloatIterable$14(file);
    }

    public static FloatIterable asFloatIterable(CharSequence charSequence) {
        return () -> BinIO.lambda$asFloatIterable$15(charSequence);
    }

    private static FloatIterator lambda$asFloatIterable$15(CharSequence charSequence) {
        try {
            return BinIO.asFloatIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static FloatIterator lambda$asFloatIterable$14(File file) {
        try {
            return BinIO.asFloatIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static CharIterator lambda$asCharIterable$13(CharSequence charSequence) {
        try {
            return BinIO.asCharIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static CharIterator lambda$asCharIterable$12(File file) {
        try {
            return BinIO.asCharIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ShortIterator lambda$asShortIterable$11(CharSequence charSequence) {
        try {
            return BinIO.asShortIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ShortIterator lambda$asShortIterable$10(File file) {
        try {
            return BinIO.asShortIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static BooleanIterator lambda$asBooleanIterable$9(CharSequence charSequence) {
        try {
            return BinIO.asBooleanIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static BooleanIterator lambda$asBooleanIterable$8(File file) {
        try {
            return BinIO.asBooleanIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static DoubleIterator lambda$asDoubleIterable$7(CharSequence charSequence) {
        try {
            return BinIO.asDoubleIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static DoubleIterator lambda$asDoubleIterable$6(File file) {
        try {
            return BinIO.asDoubleIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static LongIterator lambda$asLongIterable$5(CharSequence charSequence) {
        try {
            return BinIO.asLongIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static LongIterator lambda$asLongIterable$4(File file) {
        try {
            return BinIO.asLongIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static IntIterator lambda$asIntIterable$3(CharSequence charSequence) {
        try {
            return BinIO.asIntIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static IntIterator lambda$asIntIterable$2(File file) {
        try {
            return BinIO.asIntIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ByteIterator lambda$asByteIterable$1(CharSequence charSequence) {
        try {
            return BinIO.asByteIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ByteIterator lambda$asByteIterable$0(File file) {
        try {
            return BinIO.asByteIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static final class FloatDataInputWrapper
    implements FloatIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private float next;

        public FloatDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readFloat();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class CharDataInputWrapper
    implements CharIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private char next;

        public CharDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readChar();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class ShortDataInputWrapper
    implements ShortIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private short next;

        public ShortDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readShort();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class BooleanDataInputWrapper
    implements BooleanIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private boolean next;

        public BooleanDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readBoolean();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public boolean nextBoolean() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class DoubleDataInputWrapper
    implements DoubleIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private double next;

        public DoubleDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readDouble();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public double nextDouble() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class LongDataInputWrapper
    implements LongIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private long next;

        public LongDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readLong();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class IntDataInputWrapper
    implements IntIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private int next;

        public IntDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readInt();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }

    private static final class ByteDataInputWrapper
    implements ByteIterator {
        private final DataInput dataInput;
        private boolean toAdvance = true;
        private boolean endOfProcess = false;
        private byte next;

        public ByteDataInputWrapper(DataInput dataInput) {
            this.dataInput = dataInput;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return !this.endOfProcess;
            }
            this.toAdvance = false;
            try {
                this.next = this.dataInput.readByte();
            } catch (EOFException eOFException) {
                this.endOfProcess = true;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            return !this.endOfProcess;
        }

        @Override
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.toAdvance = true;
            return this.next;
        }
    }
}

