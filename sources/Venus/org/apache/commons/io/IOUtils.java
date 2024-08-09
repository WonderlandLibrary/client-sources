/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.StringBuilderWriter;

public class IOUtils {
    public static final int EOF = -1;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String LINE_SEPARATOR;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static char[] SKIP_CHAR_BUFFER;
    private static byte[] SKIP_BYTE_BUFFER;

    public static void close(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection)uRLConnection).disconnect();
        }
    }

    public static void closeQuietly(Reader reader) {
        IOUtils.closeQuietly((Closeable)reader);
    }

    public static void closeQuietly(Writer writer) {
        IOUtils.closeQuietly((Closeable)writer);
    }

    public static void closeQuietly(InputStream inputStream) {
        IOUtils.closeQuietly((Closeable)inputStream);
    }

    public static void closeQuietly(OutputStream outputStream) {
        IOUtils.closeQuietly((Closeable)outputStream);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    public static void closeQuietly(Closeable ... closeableArray) {
        if (closeableArray == null) {
            return;
        }
        for (Closeable closeable : closeableArray) {
            IOUtils.closeQuietly(closeable);
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void closeQuietly(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int n) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream, n);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    public static BufferedReader toBufferedReader(Reader reader, int n) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
    }

    public static BufferedReader buffer(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    public static BufferedReader buffer(Reader reader, int n) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
    }

    public static BufferedWriter buffer(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
    }

    public static BufferedWriter buffer(Writer writer, int n) {
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer, n);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream) {
        if (outputStream == null) {
            throw new NullPointerException();
        }
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream, int n) {
        if (outputStream == null) {
            throw new NullPointerException();
        }
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream, n);
    }

    public static BufferedInputStream buffer(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedInputStream buffer(InputStream inputStream, int n) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream, n);
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, (OutputStream)byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(InputStream inputStream, long l) throws IOException {
        if (l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + l);
        }
        return IOUtils.toByteArray(inputStream, (int)l);
    }

    public static byte[] toByteArray(InputStream inputStream, int n) throws IOException {
        int n2;
        int n3;
        if (n < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + n);
        }
        if (n == 0) {
            return new byte[0];
        }
        byte[] byArray = new byte[n];
        for (n2 = 0; n2 < n && (n3 = inputStream.read(byArray, n2, n - n2)) != -1; n2 += n3) {
        }
        if (n2 != n) {
            throw new IOException("Unexpected readed size. current: " + n2 + ", excepted: " + n);
        }
        return byArray;
    }

    @Deprecated
    public static byte[] toByteArray(Reader reader) throws IOException {
        return IOUtils.toByteArray(reader, Charset.defaultCharset());
    }

    public static byte[] toByteArray(Reader reader, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(reader, (OutputStream)byteArrayOutputStream, charset);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader, String string) throws IOException {
        return IOUtils.toByteArray(reader, Charsets.toCharset(string));
    }

    @Deprecated
    public static byte[] toByteArray(String string) throws IOException {
        return string.getBytes(Charset.defaultCharset());
    }

    public static byte[] toByteArray(URI uRI) throws IOException {
        return IOUtils.toByteArray(uRI.toURL());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] toByteArray(URL uRL) throws IOException {
        URLConnection uRLConnection = uRL.openConnection();
        try {
            byte[] byArray = IOUtils.toByteArray(uRLConnection);
            return byArray;
        } finally {
            IOUtils.close(uRLConnection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] toByteArray(URLConnection uRLConnection) throws IOException {
        InputStream inputStream = uRLConnection.getInputStream();
        try {
            byte[] byArray = IOUtils.toByteArray(inputStream);
            return byArray;
        } finally {
            inputStream.close();
        }
    }

    @Deprecated
    public static char[] toCharArray(InputStream inputStream) throws IOException {
        return IOUtils.toCharArray(inputStream, Charset.defaultCharset());
    }

    public static char[] toCharArray(InputStream inputStream, Charset charset) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        IOUtils.copy(inputStream, (Writer)charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }

    public static char[] toCharArray(InputStream inputStream, String string) throws IOException {
        return IOUtils.toCharArray(inputStream, Charsets.toCharset(string));
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        IOUtils.copy(reader, (Writer)charArrayWriter);
        return charArrayWriter.toCharArray();
    }

    @Deprecated
    public static String toString(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, Charset.defaultCharset());
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        IOUtils.copy(inputStream, (Writer)stringBuilderWriter, charset);
        return stringBuilderWriter.toString();
    }

    public static String toString(InputStream inputStream, String string) throws IOException {
        return IOUtils.toString(inputStream, Charsets.toCharset(string));
    }

    public static String toString(Reader reader) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        IOUtils.copy(reader, (Writer)stringBuilderWriter);
        return stringBuilderWriter.toString();
    }

    @Deprecated
    public static String toString(URI uRI) throws IOException {
        return IOUtils.toString(uRI, Charset.defaultCharset());
    }

    public static String toString(URI uRI, Charset charset) throws IOException {
        return IOUtils.toString(uRI.toURL(), Charsets.toCharset(charset));
    }

    public static String toString(URI uRI, String string) throws IOException {
        return IOUtils.toString(uRI, Charsets.toCharset(string));
    }

    @Deprecated
    public static String toString(URL uRL) throws IOException {
        return IOUtils.toString(uRL, Charset.defaultCharset());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String toString(URL uRL, Charset charset) throws IOException {
        InputStream inputStream = uRL.openStream();
        try {
            String string = IOUtils.toString(inputStream, charset);
            return string;
        } finally {
            inputStream.close();
        }
    }

    public static String toString(URL uRL, String string) throws IOException {
        return IOUtils.toString(uRL, Charsets.toCharset(string));
    }

    @Deprecated
    public static String toString(byte[] byArray) throws IOException {
        return new String(byArray, Charset.defaultCharset());
    }

    public static String toString(byte[] byArray, String string) throws IOException {
        return new String(byArray, Charsets.toCharset(string));
    }

    @Deprecated
    public static List<String> readLines(InputStream inputStream) throws IOException {
        return IOUtils.readLines(inputStream, Charset.defaultCharset());
    }

    public static List<String> readLines(InputStream inputStream, Charset charset) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charsets.toCharset(charset));
        return IOUtils.readLines(inputStreamReader);
    }

    public static List<String> readLines(InputStream inputStream, String string) throws IOException {
        return IOUtils.readLines(inputStream, Charsets.toCharset(string));
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = IOUtils.toBufferedReader(reader);
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = bufferedReader.readLine();
        while (string != null) {
            arrayList.add(string);
            string = bufferedReader.readLine();
        }
        return arrayList;
    }

    public static LineIterator lineIterator(Reader reader) {
        return new LineIterator(reader);
    }

    public static LineIterator lineIterator(InputStream inputStream, Charset charset) throws IOException {
        return new LineIterator(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static LineIterator lineIterator(InputStream inputStream, String string) throws IOException {
        return IOUtils.lineIterator(inputStream, Charsets.toCharset(string));
    }

    @Deprecated
    public static InputStream toInputStream(CharSequence charSequence) {
        return IOUtils.toInputStream(charSequence, Charset.defaultCharset());
    }

    public static InputStream toInputStream(CharSequence charSequence, Charset charset) {
        return IOUtils.toInputStream(charSequence.toString(), charset);
    }

    public static InputStream toInputStream(CharSequence charSequence, String string) throws IOException {
        return IOUtils.toInputStream(charSequence, Charsets.toCharset(string));
    }

    @Deprecated
    public static InputStream toInputStream(String string) {
        return IOUtils.toInputStream(string, Charset.defaultCharset());
    }

    public static InputStream toInputStream(String string, Charset charset) {
        return new ByteArrayInputStream(string.getBytes(Charsets.toCharset(charset)));
    }

    public static InputStream toInputStream(String string, String string2) throws IOException {
        byte[] byArray = string.getBytes(Charsets.toCharset(string2));
        return new ByteArrayInputStream(byArray);
    }

    public static void write(byte[] byArray, OutputStream outputStream) throws IOException {
        if (byArray != null) {
            outputStream.write(byArray);
        }
    }

    public static void writeChunked(byte[] byArray, OutputStream outputStream) throws IOException {
        if (byArray != null) {
            int n = byArray.length;
            int n2 = 0;
            while (n > 0) {
                int n3 = Math.min(n, 4096);
                outputStream.write(byArray, n2, n3);
                n -= n3;
                n2 += n3;
            }
        }
    }

    @Deprecated
    public static void write(byte[] byArray, Writer writer) throws IOException {
        IOUtils.write(byArray, writer, Charset.defaultCharset());
    }

    public static void write(byte[] byArray, Writer writer, Charset charset) throws IOException {
        if (byArray != null) {
            writer.write(new String(byArray, Charsets.toCharset(charset)));
        }
    }

    public static void write(byte[] byArray, Writer writer, String string) throws IOException {
        IOUtils.write(byArray, writer, Charsets.toCharset(string));
    }

    public static void write(char[] cArray, Writer writer) throws IOException {
        if (cArray != null) {
            writer.write(cArray);
        }
    }

    public static void writeChunked(char[] cArray, Writer writer) throws IOException {
        if (cArray != null) {
            int n = cArray.length;
            int n2 = 0;
            while (n > 0) {
                int n3 = Math.min(n, 4096);
                writer.write(cArray, n2, n3);
                n -= n3;
                n2 += n3;
            }
        }
    }

    @Deprecated
    public static void write(char[] cArray, OutputStream outputStream) throws IOException {
        IOUtils.write(cArray, outputStream, Charset.defaultCharset());
    }

    public static void write(char[] cArray, OutputStream outputStream, Charset charset) throws IOException {
        if (cArray != null) {
            outputStream.write(new String(cArray).getBytes(Charsets.toCharset(charset)));
        }
    }

    public static void write(char[] cArray, OutputStream outputStream, String string) throws IOException {
        IOUtils.write(cArray, outputStream, Charsets.toCharset(string));
    }

    public static void write(CharSequence charSequence, Writer writer) throws IOException {
        if (charSequence != null) {
            IOUtils.write(charSequence.toString(), writer);
        }
    }

    @Deprecated
    public static void write(CharSequence charSequence, OutputStream outputStream) throws IOException {
        IOUtils.write(charSequence, outputStream, Charset.defaultCharset());
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, Charset charset) throws IOException {
        if (charSequence != null) {
            IOUtils.write(charSequence.toString(), outputStream, charset);
        }
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, String string) throws IOException {
        IOUtils.write(charSequence, outputStream, Charsets.toCharset(string));
    }

    public static void write(String string, Writer writer) throws IOException {
        if (string != null) {
            writer.write(string);
        }
    }

    @Deprecated
    public static void write(String string, OutputStream outputStream) throws IOException {
        IOUtils.write(string, outputStream, Charset.defaultCharset());
    }

    public static void write(String string, OutputStream outputStream, Charset charset) throws IOException {
        if (string != null) {
            outputStream.write(string.getBytes(Charsets.toCharset(charset)));
        }
    }

    public static void write(String string, OutputStream outputStream, String string2) throws IOException {
        IOUtils.write(string, outputStream, Charsets.toCharset(string2));
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, Writer writer) throws IOException {
        if (stringBuffer != null) {
            writer.write(stringBuffer.toString());
        }
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream) throws IOException {
        IOUtils.write(stringBuffer, outputStream, (String)null);
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream, String string) throws IOException {
        if (stringBuffer != null) {
            outputStream.write(stringBuffer.toString().getBytes(Charsets.toCharset(string)));
        }
    }

    @Deprecated
    public static void writeLines(Collection<?> collection, String string, OutputStream outputStream) throws IOException {
        IOUtils.writeLines(collection, string, outputStream, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> collection, String string, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (string == null) {
            string = LINE_SEPARATOR;
        }
        Charset charset2 = Charsets.toCharset(charset);
        for (Object obj : collection) {
            if (obj != null) {
                outputStream.write(obj.toString().getBytes(charset2));
            }
            outputStream.write(string.getBytes(charset2));
        }
    }

    public static void writeLines(Collection<?> collection, String string, OutputStream outputStream, String string2) throws IOException {
        IOUtils.writeLines(collection, string, outputStream, Charsets.toCharset(string2));
    }

    public static void writeLines(Collection<?> collection, String string, Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (string == null) {
            string = LINE_SEPARATOR;
        }
        for (Object obj : collection) {
            if (obj != null) {
                writer.write(obj.toString());
            }
            writer.write(string);
        }
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long l = IOUtils.copyLarge(inputStream, outputStream);
        if (l > Integer.MAX_VALUE) {
            return 1;
        }
        return (int)l;
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int n) throws IOException {
        return IOUtils.copyLarge(inputStream, outputStream, new byte[n]);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return IOUtils.copy(inputStream, outputStream, 4096);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] byArray) throws IOException {
        int n;
        long l = 0L;
        while (-1 != (n = inputStream.read(byArray))) {
            outputStream.write(byArray, 0, n);
            l += (long)n;
        }
        return l;
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long l, long l2) throws IOException {
        return IOUtils.copyLarge(inputStream, outputStream, l, l2, new byte[4096]);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long l, long l2, byte[] byArray) throws IOException {
        int n;
        int n2;
        if (l > 0L) {
            IOUtils.skipFully(inputStream, l);
        }
        if (l2 == 0L) {
            return 0L;
        }
        int n3 = n2 = byArray.length;
        if (l2 > 0L && l2 < (long)n2) {
            n3 = (int)l2;
        }
        long l3 = 0L;
        while (n3 > 0 && -1 != (n = inputStream.read(byArray, 0, n3))) {
            outputStream.write(byArray, 0, n);
            l3 += (long)n;
            if (l2 <= 0L) continue;
            n3 = (int)Math.min(l2 - l3, (long)n2);
        }
        return l3;
    }

    @Deprecated
    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        IOUtils.copy(inputStream, writer, Charset.defaultCharset());
    }

    public static void copy(InputStream inputStream, Writer writer, Charset charset) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charsets.toCharset(charset));
        IOUtils.copy((Reader)inputStreamReader, writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String string) throws IOException {
        IOUtils.copy(inputStream, writer, Charsets.toCharset(string));
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long l = IOUtils.copyLarge(reader, writer);
        if (l > Integer.MAX_VALUE) {
            return 1;
        }
        return (int)l;
    }

    public static long copyLarge(Reader reader, Writer writer) throws IOException {
        return IOUtils.copyLarge(reader, writer, new char[4096]);
    }

    public static long copyLarge(Reader reader, Writer writer, char[] cArray) throws IOException {
        int n;
        long l = 0L;
        while (-1 != (n = reader.read(cArray))) {
            writer.write(cArray, 0, n);
            l += (long)n;
        }
        return l;
    }

    public static long copyLarge(Reader reader, Writer writer, long l, long l2) throws IOException {
        return IOUtils.copyLarge(reader, writer, l, l2, new char[4096]);
    }

    public static long copyLarge(Reader reader, Writer writer, long l, long l2, char[] cArray) throws IOException {
        int n;
        if (l > 0L) {
            IOUtils.skipFully(reader, l);
        }
        if (l2 == 0L) {
            return 0L;
        }
        int n2 = cArray.length;
        if (l2 > 0L && l2 < (long)cArray.length) {
            n2 = (int)l2;
        }
        long l3 = 0L;
        while (n2 > 0 && -1 != (n = reader.read(cArray, 0, n2))) {
            writer.write(cArray, 0, n);
            l3 += (long)n;
            if (l2 <= 0L) continue;
            n2 = (int)Math.min(l2 - l3, (long)cArray.length);
        }
        return l3;
    }

    @Deprecated
    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        IOUtils.copy(reader, outputStream, Charset.defaultCharset());
    }

    public static void copy(Reader reader, OutputStream outputStream, Charset charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.toCharset(charset));
        IOUtils.copy(reader, (Writer)outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(Reader reader, OutputStream outputStream, String string) throws IOException {
        IOUtils.copy(reader, outputStream, Charsets.toCharset(string));
    }

    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException {
        int n;
        if (inputStream == inputStream2) {
            return false;
        }
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        int n2 = inputStream.read();
        while (-1 != n2) {
            n = inputStream2.read();
            if (n2 != n) {
                return true;
            }
            n2 = inputStream.read();
        }
        n = inputStream2.read();
        return n == -1;
    }

    public static boolean contentEquals(Reader reader, Reader reader2) throws IOException {
        int n;
        if (reader == reader2) {
            return false;
        }
        reader = IOUtils.toBufferedReader(reader);
        reader2 = IOUtils.toBufferedReader(reader2);
        int n2 = reader.read();
        while (-1 != n2) {
            n = reader2.read();
            if (n2 != n) {
                return true;
            }
            n2 = reader.read();
        }
        n = reader2.read();
        return n == -1;
    }

    public static boolean contentEqualsIgnoreEOL(Reader reader, Reader reader2) throws IOException {
        if (reader == reader2) {
            return false;
        }
        BufferedReader bufferedReader = IOUtils.toBufferedReader(reader);
        BufferedReader bufferedReader2 = IOUtils.toBufferedReader(reader2);
        String string = bufferedReader.readLine();
        String string2 = bufferedReader2.readLine();
        while (string != null && string2 != null && string.equals(string2)) {
            string = bufferedReader.readLine();
            string2 = bufferedReader2.readLine();
        }
        return string == null ? string2 == null : string.equals(string2);
    }

    public static long skip(InputStream inputStream, long l) throws IOException {
        long l2;
        long l3;
        if (l < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + l);
        }
        if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[2048];
        }
        for (l2 = l; l2 > 0L && (l3 = (long)inputStream.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(l2, 2048L))) >= 0L; l2 -= l3) {
        }
        return l - l2;
    }

    public static long skip(ReadableByteChannel readableByteChannel, long l) throws IOException {
        long l2;
        int n;
        if (l < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + l);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)Math.min(l, 2048L));
        for (l2 = l; l2 > 0L; l2 -= (long)n) {
            byteBuffer.position(0);
            byteBuffer.limit((int)Math.min(l2, 2048L));
            n = readableByteChannel.read(byteBuffer);
            if (n == -1) break;
        }
        return l - l2;
    }

    public static long skip(Reader reader, long l) throws IOException {
        long l2;
        long l3;
        if (l < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + l);
        }
        if (SKIP_CHAR_BUFFER == null) {
            SKIP_CHAR_BUFFER = new char[2048];
        }
        for (l2 = l; l2 > 0L && (l3 = (long)reader.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(l2, 2048L))) >= 0L; l2 -= l3) {
        }
        return l - l2;
    }

    public static void skipFully(InputStream inputStream, long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + l);
        }
        long l2 = IOUtils.skip(inputStream, l);
        if (l2 != l) {
            throw new EOFException("Bytes to skip: " + l + " actual: " + l2);
        }
    }

    public static void skipFully(ReadableByteChannel readableByteChannel, long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + l);
        }
        long l2 = IOUtils.skip(readableByteChannel, l);
        if (l2 != l) {
            throw new EOFException("Bytes to skip: " + l + " actual: " + l2);
        }
    }

    public static void skipFully(Reader reader, long l) throws IOException {
        long l2 = IOUtils.skip(reader, l);
        if (l2 != l) {
            throw new EOFException("Chars to skip: " + l + " actual: " + l2);
        }
    }

    public static int read(Reader reader, char[] cArray, int n, int n2) throws IOException {
        int n3;
        int n4;
        int n5;
        if (n2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + n2);
        }
        for (n4 = n2; n4 > 0 && -1 != (n5 = reader.read(cArray, n + (n3 = n2 - n4), n4)); n4 -= n5) {
        }
        return n2 - n4;
    }

    public static int read(Reader reader, char[] cArray) throws IOException {
        return IOUtils.read(reader, cArray, 0, cArray.length);
    }

    public static int read(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3;
        int n4;
        int n5;
        if (n2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + n2);
        }
        for (n4 = n2; n4 > 0 && -1 != (n5 = inputStream.read(byArray, n + (n3 = n2 - n4), n4)); n4 -= n5) {
        }
        return n2 - n4;
    }

    public static int read(InputStream inputStream, byte[] byArray) throws IOException {
        return IOUtils.read(inputStream, byArray, 0, byArray.length);
    }

    public static int read(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int n;
        int n2 = byteBuffer.remaining();
        while (byteBuffer.remaining() > 0 && -1 != (n = readableByteChannel.read(byteBuffer))) {
        }
        return n2 - byteBuffer.remaining();
    }

    public static void readFully(Reader reader, char[] cArray, int n, int n2) throws IOException {
        int n3 = IOUtils.read(reader, cArray, n, n2);
        if (n3 != n2) {
            throw new EOFException("Length to read: " + n2 + " actual: " + n3);
        }
    }

    public static void readFully(Reader reader, char[] cArray) throws IOException {
        IOUtils.readFully(reader, cArray, 0, cArray.length);
    }

    public static void readFully(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3 = IOUtils.read(inputStream, byArray, n, n2);
        if (n3 != n2) {
            throw new EOFException("Length to read: " + n2 + " actual: " + n3);
        }
    }

    public static void readFully(InputStream inputStream, byte[] byArray) throws IOException {
        IOUtils.readFully(inputStream, byArray, 0, byArray.length);
    }

    public static byte[] readFully(InputStream inputStream, int n) throws IOException {
        byte[] byArray = new byte[n];
        IOUtils.readFully(inputStream, byArray, 0, byArray.length);
        return byArray;
    }

    public static void readFully(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int n = byteBuffer.remaining();
        int n2 = IOUtils.read(readableByteChannel, byteBuffer);
        if (n2 != n) {
            throw new EOFException("Length to read: " + n + " actual: " + n2);
        }
    }

    static {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter(4);
        PrintWriter printWriter = new PrintWriter(stringBuilderWriter);
        printWriter.println();
        LINE_SEPARATOR = stringBuilderWriter.toString();
        printWriter.close();
    }
}

