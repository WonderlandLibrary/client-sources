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
import it.unimi.dsi.fastutil.io.FastBufferedOutputStream;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortIterable;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class TextIO {
    public static final int BUFFER_SIZE = 8192;

    private TextIO() {
    }

    public static int loadInts(BufferedReader bufferedReader, int[] nArray, int n, int n2) throws IOException {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                nArray[n3 + n] = Integer.parseInt(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadInts(BufferedReader bufferedReader, int[] nArray) throws IOException {
        return TextIO.loadInts(bufferedReader, nArray, 0, nArray.length);
    }

    public static int loadInts(File file, int[] nArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadInts(bufferedReader, nArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadInts(CharSequence charSequence, int[] nArray, int n, int n2) throws IOException {
        return TextIO.loadInts(new File(charSequence.toString()), nArray, n, n2);
    }

    public static int loadInts(File file, int[] nArray) throws IOException {
        return TextIO.loadInts(file, nArray, 0, nArray.length);
    }

    public static int loadInts(CharSequence charSequence, int[] nArray) throws IOException {
        return TextIO.loadInts(charSequence, nArray, 0, nArray.length);
    }

    public static void storeInts(int[] nArray, int n, int n2, PrintStream printStream) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(nArray[n + i]);
        }
    }

    public static void storeInts(int[] nArray, PrintStream printStream) {
        TextIO.storeInts(nArray, 0, nArray.length, printStream);
    }

    public static void storeInts(int[] nArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeInts(nArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeInts(int[] nArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeInts(nArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeInts(int[] nArray, File file) throws IOException {
        TextIO.storeInts(nArray, 0, nArray.length, file);
    }

    public static void storeInts(int[] nArray, CharSequence charSequence) throws IOException {
        TextIO.storeInts(nArray, 0, nArray.length, charSequence);
    }

    public static void storeInts(IntIterator intIterator, PrintStream printStream) {
        while (intIterator.hasNext()) {
            printStream.println(intIterator.nextInt());
        }
    }

    public static void storeInts(IntIterator intIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeInts(intIterator, printStream);
        printStream.close();
    }

    public static void storeInts(IntIterator intIterator, CharSequence charSequence) throws IOException {
        TextIO.storeInts(intIterator, new File(charSequence.toString()));
    }

    public static long loadInts(BufferedReader bufferedReader, int[][] nArray, long l, long l2) throws IOException {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                int[] nArray2 = nArray[i];
                int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    nArray2[j] = Integer.parseInt(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadInts(BufferedReader bufferedReader, int[][] nArray) throws IOException {
        return TextIO.loadInts(bufferedReader, nArray, 0L, IntBigArrays.length(nArray));
    }

    public static long loadInts(File file, int[][] nArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadInts(bufferedReader, nArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadInts(CharSequence charSequence, int[][] nArray, long l, long l2) throws IOException {
        return TextIO.loadInts(new File(charSequence.toString()), nArray, l, l2);
    }

    public static long loadInts(File file, int[][] nArray) throws IOException {
        return TextIO.loadInts(file, nArray, 0L, IntBigArrays.length(nArray));
    }

    public static long loadInts(CharSequence charSequence, int[][] nArray) throws IOException {
        return TextIO.loadInts(charSequence, nArray, 0L, IntBigArrays.length(nArray));
    }

    public static void storeInts(int[][] nArray, long l, long l2, PrintStream printStream) {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            int[] nArray2 = nArray[i];
            int n = (int)Math.min((long)nArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(nArray2[j]);
            }
        }
    }

    public static void storeInts(int[][] nArray, PrintStream printStream) {
        TextIO.storeInts(nArray, 0L, IntBigArrays.length(nArray), printStream);
    }

    public static void storeInts(int[][] nArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeInts(nArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeInts(int[][] nArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeInts(nArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeInts(int[][] nArray, File file) throws IOException {
        TextIO.storeInts(nArray, 0L, IntBigArrays.length(nArray), file);
    }

    public static void storeInts(int[][] nArray, CharSequence charSequence) throws IOException {
        TextIO.storeInts(nArray, 0L, IntBigArrays.length(nArray), charSequence);
    }

    public static IntIterator asIntIterator(BufferedReader bufferedReader) {
        return new IntReaderWrapper(bufferedReader);
    }

    public static IntIterator asIntIterator(File file) throws IOException {
        return new IntReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static IntIterator asIntIterator(CharSequence charSequence) throws IOException {
        return TextIO.asIntIterator(new File(charSequence.toString()));
    }

    public static IntIterable asIntIterable(File file) {
        return () -> TextIO.lambda$asIntIterable$0(file);
    }

    public static IntIterable asIntIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asIntIterable$1(charSequence);
    }

    public static int loadLongs(BufferedReader bufferedReader, long[] lArray, int n, int n2) throws IOException {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                lArray[n3 + n] = Long.parseLong(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadLongs(BufferedReader bufferedReader, long[] lArray) throws IOException {
        return TextIO.loadLongs(bufferedReader, lArray, 0, lArray.length);
    }

    public static int loadLongs(File file, long[] lArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadLongs(bufferedReader, lArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadLongs(CharSequence charSequence, long[] lArray, int n, int n2) throws IOException {
        return TextIO.loadLongs(new File(charSequence.toString()), lArray, n, n2);
    }

    public static int loadLongs(File file, long[] lArray) throws IOException {
        return TextIO.loadLongs(file, lArray, 0, lArray.length);
    }

    public static int loadLongs(CharSequence charSequence, long[] lArray) throws IOException {
        return TextIO.loadLongs(charSequence, lArray, 0, lArray.length);
    }

    public static void storeLongs(long[] lArray, int n, int n2, PrintStream printStream) {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(lArray[n + i]);
        }
    }

    public static void storeLongs(long[] lArray, PrintStream printStream) {
        TextIO.storeLongs(lArray, 0, lArray.length, printStream);
    }

    public static void storeLongs(long[] lArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeLongs(lArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeLongs(long[] lArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeLongs(lArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeLongs(long[] lArray, File file) throws IOException {
        TextIO.storeLongs(lArray, 0, lArray.length, file);
    }

    public static void storeLongs(long[] lArray, CharSequence charSequence) throws IOException {
        TextIO.storeLongs(lArray, 0, lArray.length, charSequence);
    }

    public static void storeLongs(LongIterator longIterator, PrintStream printStream) {
        while (longIterator.hasNext()) {
            printStream.println(longIterator.nextLong());
        }
    }

    public static void storeLongs(LongIterator longIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeLongs(longIterator, printStream);
        printStream.close();
    }

    public static void storeLongs(LongIterator longIterator, CharSequence charSequence) throws IOException {
        TextIO.storeLongs(longIterator, new File(charSequence.toString()));
    }

    public static long loadLongs(BufferedReader bufferedReader, long[][] lArray, long l, long l2) throws IOException {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                long[] lArray2 = lArray[i];
                int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    lArray2[j] = Long.parseLong(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadLongs(BufferedReader bufferedReader, long[][] lArray) throws IOException {
        return TextIO.loadLongs(bufferedReader, lArray, 0L, LongBigArrays.length(lArray));
    }

    public static long loadLongs(File file, long[][] lArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadLongs(bufferedReader, lArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadLongs(CharSequence charSequence, long[][] lArray, long l, long l2) throws IOException {
        return TextIO.loadLongs(new File(charSequence.toString()), lArray, l, l2);
    }

    public static long loadLongs(File file, long[][] lArray) throws IOException {
        return TextIO.loadLongs(file, lArray, 0L, LongBigArrays.length(lArray));
    }

    public static long loadLongs(CharSequence charSequence, long[][] lArray) throws IOException {
        return TextIO.loadLongs(charSequence, lArray, 0L, LongBigArrays.length(lArray));
    }

    public static void storeLongs(long[][] lArray, long l, long l2, PrintStream printStream) {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            long[] lArray2 = lArray[i];
            int n = (int)Math.min((long)lArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(lArray2[j]);
            }
        }
    }

    public static void storeLongs(long[][] lArray, PrintStream printStream) {
        TextIO.storeLongs(lArray, 0L, LongBigArrays.length(lArray), printStream);
    }

    public static void storeLongs(long[][] lArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeLongs(lArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeLongs(long[][] lArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeLongs(lArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeLongs(long[][] lArray, File file) throws IOException {
        TextIO.storeLongs(lArray, 0L, LongBigArrays.length(lArray), file);
    }

    public static void storeLongs(long[][] lArray, CharSequence charSequence) throws IOException {
        TextIO.storeLongs(lArray, 0L, LongBigArrays.length(lArray), charSequence);
    }

    public static LongIterator asLongIterator(BufferedReader bufferedReader) {
        return new LongReaderWrapper(bufferedReader);
    }

    public static LongIterator asLongIterator(File file) throws IOException {
        return new LongReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static LongIterator asLongIterator(CharSequence charSequence) throws IOException {
        return TextIO.asLongIterator(new File(charSequence.toString()));
    }

    public static LongIterable asLongIterable(File file) {
        return () -> TextIO.lambda$asLongIterable$2(file);
    }

    public static LongIterable asLongIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asLongIterable$3(charSequence);
    }

    public static int loadDoubles(BufferedReader bufferedReader, double[] dArray, int n, int n2) throws IOException {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                dArray[n3 + n] = Double.parseDouble(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadDoubles(BufferedReader bufferedReader, double[] dArray) throws IOException {
        return TextIO.loadDoubles(bufferedReader, dArray, 0, dArray.length);
    }

    public static int loadDoubles(File file, double[] dArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadDoubles(bufferedReader, dArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadDoubles(CharSequence charSequence, double[] dArray, int n, int n2) throws IOException {
        return TextIO.loadDoubles(new File(charSequence.toString()), dArray, n, n2);
    }

    public static int loadDoubles(File file, double[] dArray) throws IOException {
        return TextIO.loadDoubles(file, dArray, 0, dArray.length);
    }

    public static int loadDoubles(CharSequence charSequence, double[] dArray) throws IOException {
        return TextIO.loadDoubles(charSequence, dArray, 0, dArray.length);
    }

    public static void storeDoubles(double[] dArray, int n, int n2, PrintStream printStream) {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(dArray[n + i]);
        }
    }

    public static void storeDoubles(double[] dArray, PrintStream printStream) {
        TextIO.storeDoubles(dArray, 0, dArray.length, printStream);
    }

    public static void storeDoubles(double[] dArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeDoubles(dArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeDoubles(double[] dArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeDoubles(dArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeDoubles(double[] dArray, File file) throws IOException {
        TextIO.storeDoubles(dArray, 0, dArray.length, file);
    }

    public static void storeDoubles(double[] dArray, CharSequence charSequence) throws IOException {
        TextIO.storeDoubles(dArray, 0, dArray.length, charSequence);
    }

    public static void storeDoubles(DoubleIterator doubleIterator, PrintStream printStream) {
        while (doubleIterator.hasNext()) {
            printStream.println(doubleIterator.nextDouble());
        }
    }

    public static void storeDoubles(DoubleIterator doubleIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeDoubles(doubleIterator, printStream);
        printStream.close();
    }

    public static void storeDoubles(DoubleIterator doubleIterator, CharSequence charSequence) throws IOException {
        TextIO.storeDoubles(doubleIterator, new File(charSequence.toString()));
    }

    public static long loadDoubles(BufferedReader bufferedReader, double[][] dArray, long l, long l2) throws IOException {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                double[] dArray2 = dArray[i];
                int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    dArray2[j] = Double.parseDouble(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadDoubles(BufferedReader bufferedReader, double[][] dArray) throws IOException {
        return TextIO.loadDoubles(bufferedReader, dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public static long loadDoubles(File file, double[][] dArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadDoubles(bufferedReader, dArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadDoubles(CharSequence charSequence, double[][] dArray, long l, long l2) throws IOException {
        return TextIO.loadDoubles(new File(charSequence.toString()), dArray, l, l2);
    }

    public static long loadDoubles(File file, double[][] dArray) throws IOException {
        return TextIO.loadDoubles(file, dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public static long loadDoubles(CharSequence charSequence, double[][] dArray) throws IOException {
        return TextIO.loadDoubles(charSequence, dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, PrintStream printStream) {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            double[] dArray2 = dArray[i];
            int n = (int)Math.min((long)dArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(dArray2[j]);
            }
        }
    }

    public static void storeDoubles(double[][] dArray, PrintStream printStream) {
        TextIO.storeDoubles(dArray, 0L, DoubleBigArrays.length(dArray), printStream);
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeDoubles(dArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeDoubles(double[][] dArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeDoubles(dArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeDoubles(double[][] dArray, File file) throws IOException {
        TextIO.storeDoubles(dArray, 0L, DoubleBigArrays.length(dArray), file);
    }

    public static void storeDoubles(double[][] dArray, CharSequence charSequence) throws IOException {
        TextIO.storeDoubles(dArray, 0L, DoubleBigArrays.length(dArray), charSequence);
    }

    public static DoubleIterator asDoubleIterator(BufferedReader bufferedReader) {
        return new DoubleReaderWrapper(bufferedReader);
    }

    public static DoubleIterator asDoubleIterator(File file) throws IOException {
        return new DoubleReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static DoubleIterator asDoubleIterator(CharSequence charSequence) throws IOException {
        return TextIO.asDoubleIterator(new File(charSequence.toString()));
    }

    public static DoubleIterable asDoubleIterable(File file) {
        return () -> TextIO.lambda$asDoubleIterable$4(file);
    }

    public static DoubleIterable asDoubleIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asDoubleIterable$5(charSequence);
    }

    public static int loadBooleans(BufferedReader bufferedReader, boolean[] blArray, int n, int n2) throws IOException {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                blArray[n3 + n] = Boolean.parseBoolean(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadBooleans(BufferedReader bufferedReader, boolean[] blArray) throws IOException {
        return TextIO.loadBooleans(bufferedReader, blArray, 0, blArray.length);
    }

    public static int loadBooleans(File file, boolean[] blArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadBooleans(bufferedReader, blArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadBooleans(CharSequence charSequence, boolean[] blArray, int n, int n2) throws IOException {
        return TextIO.loadBooleans(new File(charSequence.toString()), blArray, n, n2);
    }

    public static int loadBooleans(File file, boolean[] blArray) throws IOException {
        return TextIO.loadBooleans(file, blArray, 0, blArray.length);
    }

    public static int loadBooleans(CharSequence charSequence, boolean[] blArray) throws IOException {
        return TextIO.loadBooleans(charSequence, blArray, 0, blArray.length);
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, PrintStream printStream) {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(blArray[n + i]);
        }
    }

    public static void storeBooleans(boolean[] blArray, PrintStream printStream) {
        TextIO.storeBooleans(blArray, 0, blArray.length, printStream);
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBooleans(blArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeBooleans(boolean[] blArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeBooleans(blArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[] blArray, File file) throws IOException {
        TextIO.storeBooleans(blArray, 0, blArray.length, file);
    }

    public static void storeBooleans(boolean[] blArray, CharSequence charSequence) throws IOException {
        TextIO.storeBooleans(blArray, 0, blArray.length, charSequence);
    }

    public static void storeBooleans(BooleanIterator booleanIterator, PrintStream printStream) {
        while (booleanIterator.hasNext()) {
            printStream.println(booleanIterator.nextBoolean());
        }
    }

    public static void storeBooleans(BooleanIterator booleanIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBooleans(booleanIterator, printStream);
        printStream.close();
    }

    public static void storeBooleans(BooleanIterator booleanIterator, CharSequence charSequence) throws IOException {
        TextIO.storeBooleans(booleanIterator, new File(charSequence.toString()));
    }

    public static long loadBooleans(BufferedReader bufferedReader, boolean[][] blArray, long l, long l2) throws IOException {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                boolean[] blArray2 = blArray[i];
                int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    blArray2[j] = Boolean.parseBoolean(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadBooleans(BufferedReader bufferedReader, boolean[][] blArray) throws IOException {
        return TextIO.loadBooleans(bufferedReader, blArray, 0L, BooleanBigArrays.length(blArray));
    }

    public static long loadBooleans(File file, boolean[][] blArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadBooleans(bufferedReader, blArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadBooleans(CharSequence charSequence, boolean[][] blArray, long l, long l2) throws IOException {
        return TextIO.loadBooleans(new File(charSequence.toString()), blArray, l, l2);
    }

    public static long loadBooleans(File file, boolean[][] blArray) throws IOException {
        return TextIO.loadBooleans(file, blArray, 0L, BooleanBigArrays.length(blArray));
    }

    public static long loadBooleans(CharSequence charSequence, boolean[][] blArray) throws IOException {
        return TextIO.loadBooleans(charSequence, blArray, 0L, BooleanBigArrays.length(blArray));
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, PrintStream printStream) {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            boolean[] blArray2 = blArray[i];
            int n = (int)Math.min((long)blArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(blArray2[j]);
            }
        }
    }

    public static void storeBooleans(boolean[][] blArray, PrintStream printStream) {
        TextIO.storeBooleans(blArray, 0L, BooleanBigArrays.length(blArray), printStream);
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBooleans(blArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeBooleans(boolean[][] blArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeBooleans(blArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeBooleans(boolean[][] blArray, File file) throws IOException {
        TextIO.storeBooleans(blArray, 0L, BooleanBigArrays.length(blArray), file);
    }

    public static void storeBooleans(boolean[][] blArray, CharSequence charSequence) throws IOException {
        TextIO.storeBooleans(blArray, 0L, BooleanBigArrays.length(blArray), charSequence);
    }

    public static BooleanIterator asBooleanIterator(BufferedReader bufferedReader) {
        return new BooleanReaderWrapper(bufferedReader);
    }

    public static BooleanIterator asBooleanIterator(File file) throws IOException {
        return new BooleanReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static BooleanIterator asBooleanIterator(CharSequence charSequence) throws IOException {
        return TextIO.asBooleanIterator(new File(charSequence.toString()));
    }

    public static BooleanIterable asBooleanIterable(File file) {
        return () -> TextIO.lambda$asBooleanIterable$6(file);
    }

    public static BooleanIterable asBooleanIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asBooleanIterable$7(charSequence);
    }

    public static int loadBytes(BufferedReader bufferedReader, byte[] byArray, int n, int n2) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                byArray[n3 + n] = Byte.parseByte(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadBytes(BufferedReader bufferedReader, byte[] byArray) throws IOException {
        return TextIO.loadBytes(bufferedReader, byArray, 0, byArray.length);
    }

    public static int loadBytes(File file, byte[] byArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadBytes(bufferedReader, byArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadBytes(CharSequence charSequence, byte[] byArray, int n, int n2) throws IOException {
        return TextIO.loadBytes(new File(charSequence.toString()), byArray, n, n2);
    }

    public static int loadBytes(File file, byte[] byArray) throws IOException {
        return TextIO.loadBytes(file, byArray, 0, byArray.length);
    }

    public static int loadBytes(CharSequence charSequence, byte[] byArray) throws IOException {
        return TextIO.loadBytes(charSequence, byArray, 0, byArray.length);
    }

    public static void storeBytes(byte[] byArray, int n, int n2, PrintStream printStream) {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(byArray[n + i]);
        }
    }

    public static void storeBytes(byte[] byArray, PrintStream printStream) {
        TextIO.storeBytes(byArray, 0, byArray.length, printStream);
    }

    public static void storeBytes(byte[] byArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBytes(byArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeBytes(byte[] byArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeBytes(byArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeBytes(byte[] byArray, File file) throws IOException {
        TextIO.storeBytes(byArray, 0, byArray.length, file);
    }

    public static void storeBytes(byte[] byArray, CharSequence charSequence) throws IOException {
        TextIO.storeBytes(byArray, 0, byArray.length, charSequence);
    }

    public static void storeBytes(ByteIterator byteIterator, PrintStream printStream) {
        while (byteIterator.hasNext()) {
            printStream.println(byteIterator.nextByte());
        }
    }

    public static void storeBytes(ByteIterator byteIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBytes(byteIterator, printStream);
        printStream.close();
    }

    public static void storeBytes(ByteIterator byteIterator, CharSequence charSequence) throws IOException {
        TextIO.storeBytes(byteIterator, new File(charSequence.toString()));
    }

    public static long loadBytes(BufferedReader bufferedReader, byte[][] byArray, long l, long l2) throws IOException {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                byte[] byArray2 = byArray[i];
                int n = (int)Math.min((long)byArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    byArray2[j] = Byte.parseByte(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadBytes(BufferedReader bufferedReader, byte[][] byArray) throws IOException {
        return TextIO.loadBytes(bufferedReader, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static long loadBytes(File file, byte[][] byArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadBytes(bufferedReader, byArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadBytes(CharSequence charSequence, byte[][] byArray, long l, long l2) throws IOException {
        return TextIO.loadBytes(new File(charSequence.toString()), byArray, l, l2);
    }

    public static long loadBytes(File file, byte[][] byArray) throws IOException {
        return TextIO.loadBytes(file, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static long loadBytes(CharSequence charSequence, byte[][] byArray) throws IOException {
        return TextIO.loadBytes(charSequence, byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, PrintStream printStream) {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            byte[] byArray2 = byArray[i];
            int n = (int)Math.min((long)byArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(byArray2[j]);
            }
        }
    }

    public static void storeBytes(byte[][] byArray, PrintStream printStream) {
        TextIO.storeBytes(byArray, 0L, ByteBigArrays.length(byArray), printStream);
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeBytes(byArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeBytes(byte[][] byArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeBytes(byArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeBytes(byte[][] byArray, File file) throws IOException {
        TextIO.storeBytes(byArray, 0L, ByteBigArrays.length(byArray), file);
    }

    public static void storeBytes(byte[][] byArray, CharSequence charSequence) throws IOException {
        TextIO.storeBytes(byArray, 0L, ByteBigArrays.length(byArray), charSequence);
    }

    public static ByteIterator asByteIterator(BufferedReader bufferedReader) {
        return new ByteReaderWrapper(bufferedReader);
    }

    public static ByteIterator asByteIterator(File file) throws IOException {
        return new ByteReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static ByteIterator asByteIterator(CharSequence charSequence) throws IOException {
        return TextIO.asByteIterator(new File(charSequence.toString()));
    }

    public static ByteIterable asByteIterable(File file) {
        return () -> TextIO.lambda$asByteIterable$8(file);
    }

    public static ByteIterable asByteIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asByteIterable$9(charSequence);
    }

    public static int loadShorts(BufferedReader bufferedReader, short[] sArray, int n, int n2) throws IOException {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                sArray[n3 + n] = Short.parseShort(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadShorts(BufferedReader bufferedReader, short[] sArray) throws IOException {
        return TextIO.loadShorts(bufferedReader, sArray, 0, sArray.length);
    }

    public static int loadShorts(File file, short[] sArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadShorts(bufferedReader, sArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadShorts(CharSequence charSequence, short[] sArray, int n, int n2) throws IOException {
        return TextIO.loadShorts(new File(charSequence.toString()), sArray, n, n2);
    }

    public static int loadShorts(File file, short[] sArray) throws IOException {
        return TextIO.loadShorts(file, sArray, 0, sArray.length);
    }

    public static int loadShorts(CharSequence charSequence, short[] sArray) throws IOException {
        return TextIO.loadShorts(charSequence, sArray, 0, sArray.length);
    }

    public static void storeShorts(short[] sArray, int n, int n2, PrintStream printStream) {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(sArray[n + i]);
        }
    }

    public static void storeShorts(short[] sArray, PrintStream printStream) {
        TextIO.storeShorts(sArray, 0, sArray.length, printStream);
    }

    public static void storeShorts(short[] sArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeShorts(sArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeShorts(short[] sArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeShorts(sArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeShorts(short[] sArray, File file) throws IOException {
        TextIO.storeShorts(sArray, 0, sArray.length, file);
    }

    public static void storeShorts(short[] sArray, CharSequence charSequence) throws IOException {
        TextIO.storeShorts(sArray, 0, sArray.length, charSequence);
    }

    public static void storeShorts(ShortIterator shortIterator, PrintStream printStream) {
        while (shortIterator.hasNext()) {
            printStream.println(shortIterator.nextShort());
        }
    }

    public static void storeShorts(ShortIterator shortIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeShorts(shortIterator, printStream);
        printStream.close();
    }

    public static void storeShorts(ShortIterator shortIterator, CharSequence charSequence) throws IOException {
        TextIO.storeShorts(shortIterator, new File(charSequence.toString()));
    }

    public static long loadShorts(BufferedReader bufferedReader, short[][] sArray, long l, long l2) throws IOException {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                short[] sArray2 = sArray[i];
                int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    sArray2[j] = Short.parseShort(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadShorts(BufferedReader bufferedReader, short[][] sArray) throws IOException {
        return TextIO.loadShorts(bufferedReader, sArray, 0L, ShortBigArrays.length(sArray));
    }

    public static long loadShorts(File file, short[][] sArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadShorts(bufferedReader, sArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadShorts(CharSequence charSequence, short[][] sArray, long l, long l2) throws IOException {
        return TextIO.loadShorts(new File(charSequence.toString()), sArray, l, l2);
    }

    public static long loadShorts(File file, short[][] sArray) throws IOException {
        return TextIO.loadShorts(file, sArray, 0L, ShortBigArrays.length(sArray));
    }

    public static long loadShorts(CharSequence charSequence, short[][] sArray) throws IOException {
        return TextIO.loadShorts(charSequence, sArray, 0L, ShortBigArrays.length(sArray));
    }

    public static void storeShorts(short[][] sArray, long l, long l2, PrintStream printStream) {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            short[] sArray2 = sArray[i];
            int n = (int)Math.min((long)sArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(sArray2[j]);
            }
        }
    }

    public static void storeShorts(short[][] sArray, PrintStream printStream) {
        TextIO.storeShorts(sArray, 0L, ShortBigArrays.length(sArray), printStream);
    }

    public static void storeShorts(short[][] sArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeShorts(sArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeShorts(short[][] sArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeShorts(sArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeShorts(short[][] sArray, File file) throws IOException {
        TextIO.storeShorts(sArray, 0L, ShortBigArrays.length(sArray), file);
    }

    public static void storeShorts(short[][] sArray, CharSequence charSequence) throws IOException {
        TextIO.storeShorts(sArray, 0L, ShortBigArrays.length(sArray), charSequence);
    }

    public static ShortIterator asShortIterator(BufferedReader bufferedReader) {
        return new ShortReaderWrapper(bufferedReader);
    }

    public static ShortIterator asShortIterator(File file) throws IOException {
        return new ShortReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static ShortIterator asShortIterator(CharSequence charSequence) throws IOException {
        return TextIO.asShortIterator(new File(charSequence.toString()));
    }

    public static ShortIterable asShortIterable(File file) {
        return () -> TextIO.lambda$asShortIterable$10(file);
    }

    public static ShortIterable asShortIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asShortIterable$11(charSequence);
    }

    public static int loadFloats(BufferedReader bufferedReader, float[] fArray, int n, int n2) throws IOException {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        int n3 = 0;
        try {
            String string;
            for (n3 = 0; n3 < n2 && (string = bufferedReader.readLine()) != null; ++n3) {
                fArray[n3 + n] = Float.parseFloat(string.trim());
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return n3;
    }

    public static int loadFloats(BufferedReader bufferedReader, float[] fArray) throws IOException {
        return TextIO.loadFloats(bufferedReader, fArray, 0, fArray.length);
    }

    public static int loadFloats(File file, float[] fArray, int n, int n2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int n3 = TextIO.loadFloats(bufferedReader, fArray, n, n2);
        bufferedReader.close();
        return n3;
    }

    public static int loadFloats(CharSequence charSequence, float[] fArray, int n, int n2) throws IOException {
        return TextIO.loadFloats(new File(charSequence.toString()), fArray, n, n2);
    }

    public static int loadFloats(File file, float[] fArray) throws IOException {
        return TextIO.loadFloats(file, fArray, 0, fArray.length);
    }

    public static int loadFloats(CharSequence charSequence, float[] fArray) throws IOException {
        return TextIO.loadFloats(charSequence, fArray, 0, fArray.length);
    }

    public static void storeFloats(float[] fArray, int n, int n2, PrintStream printStream) {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            printStream.println(fArray[n + i]);
        }
    }

    public static void storeFloats(float[] fArray, PrintStream printStream) {
        TextIO.storeFloats(fArray, 0, fArray.length, printStream);
    }

    public static void storeFloats(float[] fArray, int n, int n2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeFloats(fArray, n, n2, printStream);
        printStream.close();
    }

    public static void storeFloats(float[] fArray, int n, int n2, CharSequence charSequence) throws IOException {
        TextIO.storeFloats(fArray, n, n2, new File(charSequence.toString()));
    }

    public static void storeFloats(float[] fArray, File file) throws IOException {
        TextIO.storeFloats(fArray, 0, fArray.length, file);
    }

    public static void storeFloats(float[] fArray, CharSequence charSequence) throws IOException {
        TextIO.storeFloats(fArray, 0, fArray.length, charSequence);
    }

    public static void storeFloats(FloatIterator floatIterator, PrintStream printStream) {
        while (floatIterator.hasNext()) {
            printStream.println(floatIterator.nextFloat());
        }
    }

    public static void storeFloats(FloatIterator floatIterator, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeFloats(floatIterator, printStream);
        printStream.close();
    }

    public static void storeFloats(FloatIterator floatIterator, CharSequence charSequence) throws IOException {
        TextIO.storeFloats(floatIterator, new File(charSequence.toString()));
    }

    public static long loadFloats(BufferedReader bufferedReader, float[][] fArray, long l, long l2) throws IOException {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        long l3 = 0L;
        try {
            for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
                float[] fArray2 = fArray[i];
                int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
                for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        return l3;
                    }
                    fArray2[j] = Float.parseFloat(string.trim());
                    ++l3;
                }
            }
        } catch (EOFException eOFException) {
            // empty catch block
        }
        return l3;
    }

    public static long loadFloats(BufferedReader bufferedReader, float[][] fArray) throws IOException {
        return TextIO.loadFloats(bufferedReader, fArray, 0L, FloatBigArrays.length(fArray));
    }

    public static long loadFloats(File file, float[][] fArray, long l, long l2) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long l3 = TextIO.loadFloats(bufferedReader, fArray, l, l2);
        bufferedReader.close();
        return l3;
    }

    public static long loadFloats(CharSequence charSequence, float[][] fArray, long l, long l2) throws IOException {
        return TextIO.loadFloats(new File(charSequence.toString()), fArray, l, l2);
    }

    public static long loadFloats(File file, float[][] fArray) throws IOException {
        return TextIO.loadFloats(file, fArray, 0L, FloatBigArrays.length(fArray));
    }

    public static long loadFloats(CharSequence charSequence, float[][] fArray) throws IOException {
        return TextIO.loadFloats(charSequence, fArray, 0L, FloatBigArrays.length(fArray));
    }

    public static void storeFloats(float[][] fArray, long l, long l2, PrintStream printStream) {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        for (int i = BigArrays.segment(l); i < BigArrays.segment(l + l2 + 0x7FFFFFFL); ++i) {
            float[] fArray2 = fArray[i];
            int n = (int)Math.min((long)fArray2.length, l + l2 - BigArrays.start(i));
            for (int j = (int)Math.max(0L, l - BigArrays.start(i)); j < n; ++j) {
                printStream.println(fArray2[j]);
            }
        }
    }

    public static void storeFloats(float[][] fArray, PrintStream printStream) {
        TextIO.storeFloats(fArray, 0L, FloatBigArrays.length(fArray), printStream);
    }

    public static void storeFloats(float[][] fArray, long l, long l2, File file) throws IOException {
        PrintStream printStream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
        TextIO.storeFloats(fArray, l, l2, printStream);
        printStream.close();
    }

    public static void storeFloats(float[][] fArray, long l, long l2, CharSequence charSequence) throws IOException {
        TextIO.storeFloats(fArray, l, l2, new File(charSequence.toString()));
    }

    public static void storeFloats(float[][] fArray, File file) throws IOException {
        TextIO.storeFloats(fArray, 0L, FloatBigArrays.length(fArray), file);
    }

    public static void storeFloats(float[][] fArray, CharSequence charSequence) throws IOException {
        TextIO.storeFloats(fArray, 0L, FloatBigArrays.length(fArray), charSequence);
    }

    public static FloatIterator asFloatIterator(BufferedReader bufferedReader) {
        return new FloatReaderWrapper(bufferedReader);
    }

    public static FloatIterator asFloatIterator(File file) throws IOException {
        return new FloatReaderWrapper(new BufferedReader(new FileReader(file)));
    }

    public static FloatIterator asFloatIterator(CharSequence charSequence) throws IOException {
        return TextIO.asFloatIterator(new File(charSequence.toString()));
    }

    public static FloatIterable asFloatIterable(File file) {
        return () -> TextIO.lambda$asFloatIterable$12(file);
    }

    public static FloatIterable asFloatIterable(CharSequence charSequence) {
        return () -> TextIO.lambda$asFloatIterable$13(charSequence);
    }

    private static FloatIterator lambda$asFloatIterable$13(CharSequence charSequence) {
        try {
            return TextIO.asFloatIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static FloatIterator lambda$asFloatIterable$12(File file) {
        try {
            return TextIO.asFloatIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ShortIterator lambda$asShortIterable$11(CharSequence charSequence) {
        try {
            return TextIO.asShortIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ShortIterator lambda$asShortIterable$10(File file) {
        try {
            return TextIO.asShortIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ByteIterator lambda$asByteIterable$9(CharSequence charSequence) {
        try {
            return TextIO.asByteIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static ByteIterator lambda$asByteIterable$8(File file) {
        try {
            return TextIO.asByteIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static BooleanIterator lambda$asBooleanIterable$7(CharSequence charSequence) {
        try {
            return TextIO.asBooleanIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static BooleanIterator lambda$asBooleanIterable$6(File file) {
        try {
            return TextIO.asBooleanIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static DoubleIterator lambda$asDoubleIterable$5(CharSequence charSequence) {
        try {
            return TextIO.asDoubleIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static DoubleIterator lambda$asDoubleIterable$4(File file) {
        try {
            return TextIO.asDoubleIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static LongIterator lambda$asLongIterable$3(CharSequence charSequence) {
        try {
            return TextIO.asLongIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static LongIterator lambda$asLongIterable$2(File file) {
        try {
            return TextIO.asLongIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static IntIterator lambda$asIntIterable$1(CharSequence charSequence) {
        try {
            return TextIO.asIntIterator(charSequence);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static IntIterator lambda$asIntIterable$0(File file) {
        try {
            return TextIO.asIntIterator(file);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static final class FloatReaderWrapper
    implements FloatIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private float next;

        public FloatReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Float.parseFloat(this.s.trim());
            return false;
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

    private static final class ShortReaderWrapper
    implements ShortIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private short next;

        public ShortReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Short.parseShort(this.s.trim());
            return false;
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

    private static final class ByteReaderWrapper
    implements ByteIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private byte next;

        public ByteReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Byte.parseByte(this.s.trim());
            return false;
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

    private static final class BooleanReaderWrapper
    implements BooleanIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private boolean next;

        public BooleanReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Boolean.parseBoolean(this.s.trim());
            return false;
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

    private static final class DoubleReaderWrapper
    implements DoubleIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private double next;

        public DoubleReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Double.parseDouble(this.s.trim());
            return false;
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

    private static final class LongReaderWrapper
    implements LongIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private long next;

        public LongReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Long.parseLong(this.s.trim());
            return false;
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

    private static final class IntReaderWrapper
    implements IntIterator {
        private final BufferedReader reader;
        private boolean toAdvance = true;
        private String s;
        private int next;

        public IntReaderWrapper(BufferedReader bufferedReader) {
            this.reader = bufferedReader;
        }

        @Override
        public boolean hasNext() {
            if (!this.toAdvance) {
                return this.s != null;
            }
            this.toAdvance = false;
            try {
                this.s = this.reader.readLine();
            } catch (EOFException eOFException) {
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.s == null) {
                return true;
            }
            this.next = Integer.parseInt(this.s.trim());
            return false;
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
}

