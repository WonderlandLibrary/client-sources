/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Closer;
import com.google.common.io.FileWriteMode;
import com.google.common.io.LineProcessor;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Beta
@GwtIncompatible
public final class Files {
    private static final int TEMP_DIR_ATTEMPTS = 10000;
    private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>(){

        @Override
        public Iterable<File> children(File file) {
            File[] fileArray;
            if (file.isDirectory() && (fileArray = file.listFiles()) != null) {
                return Collections.unmodifiableList(Arrays.asList(fileArray));
            }
            return Collections.emptyList();
        }

        public String toString() {
            return "Files.fileTreeTraverser()";
        }

        @Override
        public Iterable children(Object object) {
            return this.children((File)object);
        }
    };

    private Files() {
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset));
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file, null);
    }

    static byte[] readFile(InputStream inputStream, long l) throws IOException {
        if (l > Integer.MAX_VALUE) {
            throw new OutOfMemoryError("file is too large to fit in a byte array: " + l + " bytes");
        }
        return l == 0L ? ByteStreams.toByteArray(inputStream) : ByteStreams.toByteArray(inputStream, (int)l);
    }

    public static ByteSink asByteSink(File file, FileWriteMode ... fileWriteModeArray) {
        return new FileByteSink(file, fileWriteModeArray, null);
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return Files.asByteSource(file).asCharSource(charset);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode ... fileWriteModeArray) {
        return Files.asByteSink(file, fileWriteModeArray).asCharSink(charset);
    }

    private static FileWriteMode[] modes(boolean bl) {
        FileWriteMode[] fileWriteModeArray;
        if (bl) {
            FileWriteMode[] fileWriteModeArray2 = new FileWriteMode[1];
            fileWriteModeArray = fileWriteModeArray2;
            fileWriteModeArray2[0] = FileWriteMode.APPEND;
        } else {
            fileWriteModeArray = new FileWriteMode[]{};
        }
        return fileWriteModeArray;
    }

    public static byte[] toByteArray(File file) throws IOException {
        return Files.asByteSource(file).read();
    }

    public static String toString(File file, Charset charset) throws IOException {
        return Files.asCharSource(file, charset).read();
    }

    public static void write(byte[] byArray, File file) throws IOException {
        Files.asByteSink(file, new FileWriteMode[0]).write(byArray);
    }

    public static void copy(File file, OutputStream outputStream) throws IOException {
        Files.asByteSource(file).copyTo(outputStream);
    }

    public static void copy(File file, File file2) throws IOException {
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", (Object)file, (Object)file2);
        Files.asByteSource(file).copyTo(Files.asByteSink(file2, new FileWriteMode[0]));
    }

    public static void write(CharSequence charSequence, File file, Charset charset) throws IOException {
        Files.asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    public static void append(CharSequence charSequence, File file, Charset charset) throws IOException {
        Files.write(charSequence, file, charset, true);
    }

    private static void write(CharSequence charSequence, File file, Charset charset, boolean bl) throws IOException {
        Files.asCharSink(file, charset, Files.modes(bl)).write(charSequence);
    }

    public static void copy(File file, Charset charset, Appendable appendable) throws IOException {
        Files.asCharSource(file, charset).copyTo(appendable);
    }

    public static boolean equal(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return false;
        }
        long l = file.length();
        long l2 = file2.length();
        if (l != 0L && l2 != 0L && l != l2) {
            return true;
        }
        return Files.asByteSource(file).contentEquals(Files.asByteSource(file2));
    }

    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        String string = System.currentTimeMillis() + "-";
        for (int i = 0; i < 10000; ++i) {
            File file2 = new File(file, string + i);
            if (!file2.mkdir()) continue;
            return file2;
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + string + "0 to " + string + 9999 + ')');
    }

    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to update modification time of " + file);
        }
    }

    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        File file2 = file.getCanonicalFile().getParentFile();
        if (file2 == null) {
            return;
        }
        file2.mkdirs();
        if (!file2.isDirectory()) {
            throw new IOException("Unable to create parent directories of " + file);
        }
    }

    public static void move(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", (Object)file, (Object)file2);
        if (!file.renameTo(file2)) {
            Files.copy(file, file2);
            if (!file.delete()) {
                if (!file2.delete()) {
                    throw new IOException("Unable to delete " + file2);
                }
                throw new IOException("Unable to delete " + file);
            }
        }
    }

    public static String readFirstLine(File file, Charset charset) throws IOException {
        return Files.asCharSource(file, charset).readFirstLine();
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        return Files.readLines(file, charset, new LineProcessor<List<String>>(){
            final List<String> result = Lists.newArrayList();

            @Override
            public boolean processLine(String string) {
                this.result.add(string);
                return false;
            }

            @Override
            public List<String> getResult() {
                return this.result;
            }

            @Override
            public Object getResult() {
                return this.getResult();
            }
        });
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return Files.asCharSource(file, charset).readLines(lineProcessor);
    }

    @CanIgnoreReturnValue
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) throws IOException {
        return Files.asByteSource(file).read(byteProcessor);
    }

    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return Files.asByteSource(file).hash(hashFunction);
    }

    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return Files.map(file, FileChannel.MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        if (!file.exists()) {
            throw new FileNotFoundException(file.toString());
        }
        return Files.map(file, mapMode, file.length());
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode, long l) throws FileNotFoundException, IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        try (Closer closer = Closer.create();){
            RandomAccessFile randomAccessFile = closer.register(new RandomAccessFile(file, mapMode == FileChannel.MapMode.READ_ONLY ? "r" : "rw"));
            MappedByteBuffer mappedByteBuffer = Files.map(randomAccessFile, mapMode, l);
            return mappedByteBuffer;
        }
    }

    private static MappedByteBuffer map(RandomAccessFile randomAccessFile, FileChannel.MapMode mapMode, long l) throws IOException {
        try (Closer closer = Closer.create();){
            FileChannel fileChannel = closer.register(randomAccessFile.getChannel());
            MappedByteBuffer mappedByteBuffer = fileChannel.map(mapMode, 0L, l);
            return mappedByteBuffer;
        }
    }

    public static String simplifyPath(String string) {
        Preconditions.checkNotNull(string);
        if (string.length() == 0) {
            return ".";
        }
        Iterable<String> iterable = Splitter.on('/').omitEmptyStrings().split(string);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : iterable) {
            if (string2.equals(".")) continue;
            if (string2.equals("..")) {
                if (arrayList.size() > 0 && !((String)arrayList.get(arrayList.size() - 1)).equals("..")) {
                    arrayList.remove(arrayList.size() - 1);
                    continue;
                }
                arrayList.add("..");
                continue;
            }
            arrayList.add(string2);
        }
        Object object = Joiner.on('/').join(arrayList);
        if (string.charAt(0) == '/') {
            object = "/" + (String)object;
        }
        while (((String)object).startsWith("/../")) {
            object = ((String)object).substring(3);
        }
        if (((String)object).equals("/..")) {
            object = "/";
        } else if ("".equals(object)) {
            object = ".";
        }
        return object;
    }

    public static String getFileExtension(String string) {
        Preconditions.checkNotNull(string);
        String string2 = new File(string).getName();
        int n = string2.lastIndexOf(46);
        return n == -1 ? "" : string2.substring(n + 1);
    }

    public static String getNameWithoutExtension(String string) {
        Preconditions.checkNotNull(string);
        String string2 = new File(string).getName();
        int n = string2.lastIndexOf(46);
        return n == -1 ? string2 : string2.substring(0, n);
    }

    public static TreeTraverser<File> fileTreeTraverser() {
        return FILE_TREE_TRAVERSER;
    }

    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }

    private static enum FilePredicate implements Predicate<File>
    {
        IS_DIRECTORY{

            @Override
            public boolean apply(File file) {
                return file.isDirectory();
            }

            public String toString() {
                return "Files.isDirectory()";
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((File)object);
            }
        }
        ,
        IS_FILE{

            @Override
            public boolean apply(File file) {
                return file.isFile();
            }

            public String toString() {
                return "Files.isFile()";
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((File)object);
            }
        };


        private FilePredicate() {
        }

        FilePredicate(1 var3_3) {
            this();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class FileByteSink
    extends ByteSink {
        private final File file;
        private final ImmutableSet<FileWriteMode> modes;

        private FileByteSink(File file, FileWriteMode ... fileWriteModeArray) {
            this.file = Preconditions.checkNotNull(file);
            this.modes = ImmutableSet.copyOf(fileWriteModeArray);
        }

        @Override
        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains((Object)FileWriteMode.APPEND));
        }

        public String toString() {
            return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
        }

        @Override
        public OutputStream openStream() throws IOException {
            return this.openStream();
        }

        FileByteSink(File file, FileWriteMode[] fileWriteModeArray, 1 var3_3) {
            this(file, fileWriteModeArray);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class FileByteSource
    extends ByteSource {
        private final File file;

        private FileByteSource(File file) {
            this.file = Preconditions.checkNotNull(file);
        }

        @Override
        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            if (this.file.isFile()) {
                return Optional.of(this.file.length());
            }
            return Optional.absent();
        }

        @Override
        public long size() throws IOException {
            if (!this.file.isFile()) {
                throw new FileNotFoundException(this.file.toString());
            }
            return this.file.length();
        }

        @Override
        public byte[] read() throws IOException {
            try (Closer closer = Closer.create();){
                FileInputStream fileInputStream = closer.register(this.openStream());
                byte[] byArray = Files.readFile(fileInputStream, fileInputStream.getChannel().size());
                return byArray;
            }
        }

        public String toString() {
            return "Files.asByteSource(" + this.file + ")";
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.openStream();
        }

        FileByteSource(File file, 1 var2_2) {
            this(file);
        }
    }
}

