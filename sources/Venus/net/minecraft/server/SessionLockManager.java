/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;

public class SessionLockManager
implements AutoCloseable {
    private final FileChannel field_232994_a_;
    private final FileLock field_232995_b_;
    private static final ByteBuffer field_232996_c_;

    public static SessionLockManager func_232998_a_(Path path) throws IOException {
        Path path2 = path.resolve("session.lock");
        if (!Files.isDirectory(path, new LinkOption[0])) {
            Files.createDirectories(path, new FileAttribute[0]);
        }
        FileChannel fileChannel = FileChannel.open(path2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        try {
            fileChannel.write(field_232996_c_.duplicate());
            fileChannel.force(false);
            FileLock fileLock = fileChannel.tryLock();
            if (fileLock == null) {
                throw AlreadyLockedException.func_233000_a_(path2);
            }
            return new SessionLockManager(fileChannel, fileLock);
        } catch (IOException iOException) {
            try {
                fileChannel.close();
            } catch (IOException iOException2) {
                iOException.addSuppressed(iOException2);
            }
            throw iOException;
        }
    }

    private SessionLockManager(FileChannel fileChannel, FileLock fileLock) {
        this.field_232994_a_ = fileChannel;
        this.field_232995_b_ = fileLock;
    }

    @Override
    public void close() throws IOException {
        try {
            if (this.field_232995_b_.isValid()) {
                this.field_232995_b_.release();
            }
        } finally {
            if (this.field_232994_a_.isOpen()) {
                this.field_232994_a_.close();
            }
        }
    }

    public boolean func_232997_a_() {
        return this.field_232995_b_.isValid();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static boolean func_232999_b_(Path path) throws IOException {
        Path path2 = path.resolve("session.lock");
        try (FileChannel fileChannel = FileChannel.open(path2, StandardOpenOption.WRITE);){
            boolean bl;
            block15: {
                FileLock fileLock = fileChannel.tryLock();
                try {
                    boolean bl2 = bl = fileLock == null;
                    if (fileLock == null) break block15;
                } catch (Throwable throwable) {
                    if (fileLock != null) {
                        try {
                            fileLock.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                fileLock.close();
            }
            return bl;
        } catch (AccessDeniedException accessDeniedException) {
            return false;
        } catch (NoSuchFileException noSuchFileException) {
            return true;
        }
    }

    static {
        byte[] byArray = "\u2603".getBytes(Charsets.UTF_8);
        field_232996_c_ = ByteBuffer.allocateDirect(byArray.length);
        field_232996_c_.put(byArray);
        field_232996_c_.flip();
    }

    public static class AlreadyLockedException
    extends IOException {
        private AlreadyLockedException(Path path, String string) {
            super(path.toAbsolutePath() + ": " + string);
        }

        public static AlreadyLockedException func_233000_a_(Path path) {
            return new AlreadyLockedException(path, "already locked (possibly by other Minecraft instance?)");
        }
    }
}

