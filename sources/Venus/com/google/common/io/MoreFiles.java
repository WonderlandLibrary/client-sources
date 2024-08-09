/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.TreeTraverser;
import com.google.common.io.AndroidIncompatible;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.io.InsecureRecursiveDeleteException;
import com.google.common.io.RecursiveDeleteOption;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

@Beta
@AndroidIncompatible
@GwtIncompatible
public final class MoreFiles {
    private MoreFiles() {
    }

    public static ByteSource asByteSource(Path path, OpenOption ... openOptionArray) {
        return new PathByteSource(path, openOptionArray, null);
    }

    public static ByteSink asByteSink(Path path, OpenOption ... openOptionArray) {
        return new PathByteSink(path, openOptionArray, null);
    }

    public static CharSource asCharSource(Path path, Charset charset, OpenOption ... openOptionArray) {
        return MoreFiles.asByteSource(path, openOptionArray).asCharSource(charset);
    }

    public static CharSink asCharSink(Path path, Charset charset, OpenOption ... openOptionArray) {
        return MoreFiles.asByteSink(path, openOptionArray).asCharSink(charset);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ImmutableList<Path> listFiles(Path path) throws IOException {
        try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path);){
            ImmutableList<Path> immutableList = ImmutableList.copyOf(directoryStream);
            return immutableList;
        } catch (DirectoryIteratorException directoryIteratorException) {
            throw directoryIteratorException.getCause();
        }
    }

    public static TreeTraverser<Path> directoryTreeTraverser() {
        return DirectoryTreeTraverser.access$200();
    }

    public static Predicate<Path> isDirectory(LinkOption ... linkOptionArray) {
        LinkOption[] linkOptionArray2 = (LinkOption[])linkOptionArray.clone();
        return new Predicate<Path>(linkOptionArray2){
            final LinkOption[] val$optionsCopy;
            {
                this.val$optionsCopy = linkOptionArray;
            }

            @Override
            public boolean apply(Path path) {
                return java.nio.file.Files.isDirectory(path, this.val$optionsCopy);
            }

            public String toString() {
                return "MoreFiles.isDirectory(" + Arrays.toString(this.val$optionsCopy) + ")";
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((Path)object);
            }
        };
    }

    public static Predicate<Path> isRegularFile(LinkOption ... linkOptionArray) {
        LinkOption[] linkOptionArray2 = (LinkOption[])linkOptionArray.clone();
        return new Predicate<Path>(linkOptionArray2){
            final LinkOption[] val$optionsCopy;
            {
                this.val$optionsCopy = linkOptionArray;
            }

            @Override
            public boolean apply(Path path) {
                return java.nio.file.Files.isRegularFile(path, this.val$optionsCopy);
            }

            public String toString() {
                return "MoreFiles.isRegularFile(" + Arrays.toString(this.val$optionsCopy) + ")";
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((Path)object);
            }
        };
    }

    public static void touch(Path path) throws IOException {
        Preconditions.checkNotNull(path);
        try {
            java.nio.file.Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
        } catch (NoSuchFileException noSuchFileException) {
            try {
                java.nio.file.Files.createFile(path, new FileAttribute[0]);
            } catch (FileAlreadyExistsException fileAlreadyExistsException) {
                // empty catch block
            }
        }
    }

    public static void createParentDirectories(Path path, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Path path2 = path.toAbsolutePath().normalize();
        Path path3 = path2.getParent();
        if (path3 == null) {
            return;
        }
        if (!java.nio.file.Files.isDirectory(path3, new LinkOption[0])) {
            java.nio.file.Files.createDirectories(path3, fileAttributeArray);
            if (!java.nio.file.Files.isDirectory(path3, new LinkOption[0])) {
                throw new IOException("Unable to create parent directories of " + path);
            }
        }
    }

    public static String getFileExtension(Path path) {
        Path path2 = path.getFileName();
        if (path2 == null) {
            return "";
        }
        String string = path2.toString();
        int n = string.lastIndexOf(46);
        return n == -1 ? "" : string.substring(n + 1);
    }

    public static String getNameWithoutExtension(Path path) {
        Path path2 = path.getFileName();
        if (path2 == null) {
            return "";
        }
        String string = path2.toString();
        int n = string.lastIndexOf(46);
        return n == -1 ? string : string.substring(0, n);
    }

    public static void deleteRecursively(Path path, RecursiveDeleteOption ... recursiveDeleteOptionArray) throws IOException {
        Path path2 = MoreFiles.getParentPath(path);
        if (path2 == null) {
            throw new FileSystemException(path.toString(), null, "can't delete recursively");
        }
        Collection<IOException> collection = null;
        try {
            boolean bl = false;
            try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path2);){
                if (directoryStream instanceof SecureDirectoryStream) {
                    bl = true;
                    collection = MoreFiles.deleteRecursivelySecure((SecureDirectoryStream)directoryStream, path.getFileName());
                }
            }
            if (!bl) {
                MoreFiles.checkAllowsInsecure(path, recursiveDeleteOptionArray);
                collection = MoreFiles.deleteRecursivelyInsecure(path);
            }
        } catch (IOException iOException) {
            if (collection == null) {
                throw iOException;
            }
            collection.add(iOException);
        }
        if (collection != null) {
            MoreFiles.throwDeleteFailed(path, collection);
        }
    }

    public static void deleteDirectoryContents(Path path, RecursiveDeleteOption ... recursiveDeleteOptionArray) throws IOException {
        Collection<IOException> collection = null;
        try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path);){
            if (directoryStream instanceof SecureDirectoryStream) {
                SecureDirectoryStream secureDirectoryStream = (SecureDirectoryStream)directoryStream;
                collection = MoreFiles.deleteDirectoryContentsSecure(secureDirectoryStream);
            } else {
                MoreFiles.checkAllowsInsecure(path, recursiveDeleteOptionArray);
                collection = MoreFiles.deleteDirectoryContentsInsecure(directoryStream);
            }
        } catch (IOException iOException) {
            if (collection == null) {
                throw iOException;
            }
            collection.add(iOException);
        }
        if (collection != null) {
            MoreFiles.throwDeleteFailed(path, collection);
        }
    }

    @Nullable
    private static Collection<IOException> deleteRecursivelySecure(SecureDirectoryStream<Path> secureDirectoryStream, Path path) {
        Collection<IOException> collection = null;
        try {
            if (MoreFiles.isDirectory(secureDirectoryStream, path, LinkOption.NOFOLLOW_LINKS)) {
                try (SecureDirectoryStream<Path> secureDirectoryStream2 = secureDirectoryStream.newDirectoryStream(path, LinkOption.NOFOLLOW_LINKS);){
                    collection = MoreFiles.deleteDirectoryContentsSecure(secureDirectoryStream2);
                }
                if (collection == null) {
                    secureDirectoryStream.deleteDirectory(path);
                }
            } else {
                secureDirectoryStream.deleteFile(path);
            }
            return collection;
        } catch (IOException iOException) {
            return MoreFiles.addException(collection, iOException);
        }
    }

    @Nullable
    private static Collection<IOException> deleteDirectoryContentsSecure(SecureDirectoryStream<Path> secureDirectoryStream) {
        Collection<IOException> collection = null;
        try {
            for (Path path : secureDirectoryStream) {
                collection = MoreFiles.concat(collection, MoreFiles.deleteRecursivelySecure(secureDirectoryStream, path.getFileName()));
            }
            return collection;
        } catch (DirectoryIteratorException directoryIteratorException) {
            return MoreFiles.addException(collection, directoryIteratorException.getCause());
        }
    }

    @Nullable
    private static Collection<IOException> deleteRecursivelyInsecure(Path path) {
        Collection<IOException> collection = null;
        try {
            if (java.nio.file.Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path);){
                    collection = MoreFiles.deleteDirectoryContentsInsecure(directoryStream);
                }
            }
            if (collection == null) {
                java.nio.file.Files.delete(path);
            }
            return collection;
        } catch (IOException iOException) {
            return MoreFiles.addException(collection, iOException);
        }
    }

    @Nullable
    private static Collection<IOException> deleteDirectoryContentsInsecure(DirectoryStream<Path> directoryStream) {
        Collection<IOException> collection = null;
        try {
            for (Path path : directoryStream) {
                collection = MoreFiles.concat(collection, MoreFiles.deleteRecursivelyInsecure(path));
            }
            return collection;
        } catch (DirectoryIteratorException directoryIteratorException) {
            return MoreFiles.addException(collection, directoryIteratorException.getCause());
        }
    }

    @Nullable
    private static Path getParentPath(Path path) throws IOException {
        Path path2 = path.getParent();
        if (path2 != null) {
            return path2;
        }
        if (path.getNameCount() == 0) {
            return null;
        }
        return path.getFileSystem().getPath(".", new String[0]);
    }

    private static void checkAllowsInsecure(Path path, RecursiveDeleteOption[] recursiveDeleteOptionArray) throws InsecureRecursiveDeleteException {
        if (!Arrays.asList(recursiveDeleteOptionArray).contains((Object)RecursiveDeleteOption.ALLOW_INSECURE)) {
            throw new InsecureRecursiveDeleteException(path.toString());
        }
    }

    private static boolean isDirectory(SecureDirectoryStream<Path> secureDirectoryStream, Path path, LinkOption ... linkOptionArray) throws IOException {
        return secureDirectoryStream.getFileAttributeView(path, BasicFileAttributeView.class, linkOptionArray).readAttributes().isDirectory();
    }

    private static Collection<IOException> addException(@Nullable Collection<IOException> collection, IOException iOException) {
        if (collection == null) {
            collection = new ArrayList<IOException>();
        }
        collection.add(iOException);
        return collection;
    }

    @Nullable
    private static Collection<IOException> concat(@Nullable Collection<IOException> collection, @Nullable Collection<IOException> collection2) {
        if (collection == null) {
            return collection2;
        }
        if (collection2 != null) {
            collection.addAll(collection2);
        }
        return collection;
    }

    private static void throwDeleteFailed(Path path, Collection<IOException> collection) throws FileSystemException {
        FileSystemException fileSystemException = new FileSystemException(path.toString(), null, "failed to delete one or more files; see suppressed exceptions for details");
        for (IOException iOException : collection) {
            fileSystemException.addSuppressed(iOException);
        }
        throw fileSystemException;
    }

    private static final class DirectoryTreeTraverser
    extends TreeTraverser<Path> {
        private static final DirectoryTreeTraverser INSTANCE = new DirectoryTreeTraverser();

        private DirectoryTreeTraverser() {
        }

        @Override
        public Iterable<Path> children(Path path) {
            if (java.nio.file.Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                try {
                    return MoreFiles.listFiles(path);
                } catch (IOException iOException) {
                    throw new DirectoryIteratorException(iOException);
                }
            }
            return ImmutableList.of();
        }

        @Override
        public Iterable children(Object object) {
            return this.children((Path)object);
        }

        static DirectoryTreeTraverser access$200() {
            return INSTANCE;
        }
    }

    private static final class PathByteSink
    extends ByteSink {
        private final Path path;
        private final OpenOption[] options;

        private PathByteSink(Path path, OpenOption ... openOptionArray) {
            this.path = Preconditions.checkNotNull(path);
            this.options = (OpenOption[])openOptionArray.clone();
        }

        @Override
        public OutputStream openStream() throws IOException {
            return java.nio.file.Files.newOutputStream(this.path, this.options);
        }

        public String toString() {
            return "MoreFiles.asByteSink(" + this.path + ", " + Arrays.toString(this.options) + ")";
        }

        PathByteSink(Path path, OpenOption[] openOptionArray, 1 var3_3) {
            this(path, openOptionArray);
        }
    }

    private static final class PathByteSource
    extends ByteSource {
        private static final LinkOption[] FOLLOW_LINKS = new LinkOption[0];
        private final Path path;
        private final OpenOption[] options;
        private final boolean followLinks;

        private PathByteSource(Path path, OpenOption ... openOptionArray) {
            this.path = Preconditions.checkNotNull(path);
            this.options = (OpenOption[])openOptionArray.clone();
            this.followLinks = PathByteSource.followLinks(this.options);
        }

        private static boolean followLinks(OpenOption[] openOptionArray) {
            for (OpenOption openOption : openOptionArray) {
                if (openOption != LinkOption.NOFOLLOW_LINKS) continue;
                return true;
            }
            return false;
        }

        @Override
        public InputStream openStream() throws IOException {
            return java.nio.file.Files.newInputStream(this.path, this.options);
        }

        private BasicFileAttributes readAttributes() throws IOException {
            LinkOption[] linkOptionArray;
            if (this.followLinks) {
                linkOptionArray = FOLLOW_LINKS;
            } else {
                LinkOption[] linkOptionArray2 = new LinkOption[1];
                linkOptionArray = linkOptionArray2;
                linkOptionArray2[0] = LinkOption.NOFOLLOW_LINKS;
            }
            return java.nio.file.Files.readAttributes(this.path, BasicFileAttributes.class, linkOptionArray);
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            BasicFileAttributes basicFileAttributes;
            try {
                basicFileAttributes = this.readAttributes();
            } catch (IOException iOException) {
                return Optional.absent();
            }
            if (basicFileAttributes.isDirectory() || basicFileAttributes.isSymbolicLink()) {
                return Optional.absent();
            }
            return Optional.of(basicFileAttributes.size());
        }

        @Override
        public long size() throws IOException {
            BasicFileAttributes basicFileAttributes = this.readAttributes();
            if (basicFileAttributes.isDirectory()) {
                throw new IOException("can't read: is a directory");
            }
            if (basicFileAttributes.isSymbolicLink()) {
                throw new IOException("can't read: is a symbolic link");
            }
            return basicFileAttributes.size();
        }

        @Override
        public byte[] read() throws IOException {
            try (SeekableByteChannel seekableByteChannel = java.nio.file.Files.newByteChannel(this.path, this.options);){
                byte[] byArray = Files.readFile(Channels.newInputStream(seekableByteChannel), seekableByteChannel.size());
                return byArray;
            }
        }

        public String toString() {
            return "MoreFiles.asByteSource(" + this.path + ", " + Arrays.toString(this.options) + ")";
        }

        PathByteSource(Path path, OpenOption[] openOptionArray, 1 var3_3) {
            this(path, openOptionArray);
        }
    }
}

