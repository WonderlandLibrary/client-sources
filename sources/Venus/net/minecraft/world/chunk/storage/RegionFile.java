/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.RegionBitmap;
import net.minecraft.world.chunk.storage.RegionFileVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionFile
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ByteBuffer field_227123_b_ = ByteBuffer.allocateDirect(1);
    private final FileChannel dataFile;
    private final Path field_227124_d_;
    private final RegionFileVersion field_227125_e_;
    private final ByteBuffer field_227126_f_ = ByteBuffer.allocateDirect(8192);
    private final IntBuffer offsets;
    private final IntBuffer chunkTimestamps;
    @VisibleForTesting
    protected final RegionBitmap field_227128_i_ = new RegionBitmap();

    public RegionFile(File file, File file2, boolean bl) throws IOException {
        this(file.toPath(), file2.toPath(), RegionFileVersion.field_227159_b_, bl);
    }

    public RegionFile(Path path, Path path2, RegionFileVersion regionFileVersion, boolean bl) throws IOException {
        this.field_227125_e_ = regionFileVersion;
        if (!Files.isDirectory(path2, new LinkOption[0])) {
            throw new IllegalArgumentException("Expected directory, got " + path2.toAbsolutePath());
        }
        this.field_227124_d_ = path2;
        this.offsets = this.field_227126_f_.asIntBuffer();
        this.offsets.limit(1024);
        this.field_227126_f_.position(4096);
        this.chunkTimestamps = this.field_227126_f_.asIntBuffer();
        this.dataFile = bl ? FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC) : FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        this.field_227128_i_.func_227120_a_(0, 2);
        this.field_227126_f_.position(0);
        int n = this.dataFile.read(this.field_227126_f_, 0L);
        if (n != -1) {
            if (n != 8192) {
                LOGGER.warn("Region file {} has truncated header: {}", (Object)path, (Object)n);
            }
            long l = Files.size(path);
            for (int i = 0; i < 1024; ++i) {
                int n2 = this.offsets.get(i);
                if (n2 == 0) continue;
                int n3 = RegionFile.func_227142_b_(n2);
                int n4 = RegionFile.func_227131_a_(n2);
                if (n3 < 2) {
                    LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", (Object)path, (Object)i, (Object)n3);
                    this.offsets.put(i, 0);
                    continue;
                }
                if (n4 == 0) {
                    LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", (Object)path, (Object)i);
                    this.offsets.put(i, 0);
                    continue;
                }
                if ((long)n3 * 4096L > l) {
                    LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", (Object)path, (Object)i, (Object)n3);
                    this.offsets.put(i, 0);
                    continue;
                }
                this.field_227128_i_.func_227120_a_(n3, n4);
            }
        }
    }

    private Path func_227145_e_(ChunkPos chunkPos) {
        String string = "c." + chunkPos.x + "." + chunkPos.z + ".mcc";
        return this.field_227124_d_.resolve(string);
    }

    @Nullable
    public synchronized DataInputStream func_222666_a(ChunkPos chunkPos) throws IOException {
        int n = this.getOffset(chunkPos);
        if (n == 0) {
            return null;
        }
        int n2 = RegionFile.func_227142_b_(n);
        int n3 = RegionFile.func_227131_a_(n);
        int n4 = n3 * 4096;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n4);
        this.dataFile.read(byteBuffer, n2 * 4096);
        byteBuffer.flip();
        if (byteBuffer.remaining() < 5) {
            LOGGER.error("Chunk {} header is truncated: expected {} but read {}", (Object)chunkPos, (Object)n4, (Object)byteBuffer.remaining());
            return null;
        }
        int n5 = byteBuffer.getInt();
        byte by = byteBuffer.get();
        if (n5 == 0) {
            LOGGER.warn("Chunk {} is allocated, but stream is missing", (Object)chunkPos);
            return null;
        }
        int n6 = n5 - 1;
        if (RegionFile.func_227130_a_(by)) {
            if (n6 != 0) {
                LOGGER.warn("Chunk has both internal and external streams");
            }
            return this.func_227133_a_(chunkPos, RegionFile.func_227141_b_(by));
        }
        if (n6 > byteBuffer.remaining()) {
            LOGGER.error("Chunk {} stream is truncated: expected {} but read {}", (Object)chunkPos, (Object)n6, (Object)byteBuffer.remaining());
            return null;
        }
        if (n6 < 0) {
            LOGGER.error("Declared size {} of chunk {} is negative", (Object)n5, (Object)chunkPos);
            return null;
        }
        return this.func_227134_a_(chunkPos, by, RegionFile.func_227137_a_(byteBuffer, n6));
    }

    private static boolean func_227130_a_(byte by) {
        return (by & 0x80) != 0;
    }

    private static byte func_227141_b_(byte by) {
        return (byte)(by & 0xFFFFFF7F);
    }

    @Nullable
    private DataInputStream func_227134_a_(ChunkPos chunkPos, byte by, InputStream inputStream) throws IOException {
        RegionFileVersion regionFileVersion = RegionFileVersion.func_227166_a_(by);
        if (regionFileVersion == null) {
            LOGGER.error("Chunk {} has invalid chunk stream version {}", (Object)chunkPos, (Object)by);
            return null;
        }
        return new DataInputStream(new BufferedInputStream(regionFileVersion.func_227168_a_(inputStream)));
    }

    @Nullable
    private DataInputStream func_227133_a_(ChunkPos chunkPos, byte by) throws IOException {
        Path path = this.func_227145_e_(chunkPos);
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            LOGGER.error("External chunk path {} is not file", (Object)path);
            return null;
        }
        return this.func_227134_a_(chunkPos, by, Files.newInputStream(path, new OpenOption[0]));
    }

    private static ByteArrayInputStream func_227137_a_(ByteBuffer byteBuffer, int n) {
        return new ByteArrayInputStream(byteBuffer.array(), byteBuffer.position(), n);
    }

    private int func_227132_a_(int n, int n2) {
        return n << 8 | n2;
    }

    private static int func_227131_a_(int n) {
        return n & 0xFF;
    }

    private static int func_227142_b_(int n) {
        return n >> 8 & 0xFFFFFF;
    }

    private static int func_227144_c_(int n) {
        return (n + 4096 - 1) / 4096;
    }

    public boolean func_222662_b(ChunkPos chunkPos) {
        int n = this.getOffset(chunkPos);
        if (n == 0) {
            return true;
        }
        int n2 = RegionFile.func_227142_b_(n);
        int n3 = RegionFile.func_227131_a_(n);
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        try {
            this.dataFile.read(byteBuffer, n2 * 4096);
            byteBuffer.flip();
            if (byteBuffer.remaining() != 5) {
                return false;
            }
            int n4 = byteBuffer.getInt();
            byte by = byteBuffer.get();
            if (RegionFile.func_227130_a_(by)) {
                if (!RegionFileVersion.func_227170_b_(RegionFile.func_227141_b_(by))) {
                    return false;
                }
                if (!Files.isRegularFile(this.func_227145_e_(chunkPos), new LinkOption[0])) {
                    return false;
                }
            } else {
                if (!RegionFileVersion.func_227170_b_(by)) {
                    return false;
                }
                if (n4 == 0) {
                    return false;
                }
                int n5 = n4 - 1;
                if (n5 < 0 || n5 > 4096 * n3) {
                    return false;
                }
            }
            return true;
        } catch (IOException iOException) {
            return true;
        }
    }

    public DataOutputStream func_222661_c(ChunkPos chunkPos) throws IOException {
        return new DataOutputStream(new BufferedOutputStream(this.field_227125_e_.func_227169_a_(new ChunkBuffer(this, chunkPos))));
    }

    public void func_235985_a_() throws IOException {
        this.dataFile.force(false);
    }

    protected synchronized void func_227135_a_(ChunkPos chunkPos, ByteBuffer byteBuffer) throws IOException {
        ICompleteCallback iCompleteCallback;
        int n;
        int n2 = RegionFile.getIndex(chunkPos);
        int n3 = this.offsets.get(n2);
        int n4 = RegionFile.func_227142_b_(n3);
        int n5 = RegionFile.func_227131_a_(n3);
        int n6 = byteBuffer.remaining();
        int n7 = RegionFile.func_227144_c_(n6);
        if (n7 >= 256) {
            Path path = this.func_227145_e_(chunkPos);
            LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", (Object)chunkPos, (Object)n6, (Object)path);
            n7 = 1;
            n = this.field_227128_i_.func_227119_a_(n7);
            iCompleteCallback = this.func_227138_a_(path, byteBuffer);
            ByteBuffer byteBuffer2 = this.func_227129_a_();
            this.dataFile.write(byteBuffer2, n * 4096);
        } else {
            n = this.field_227128_i_.func_227119_a_(n7);
            iCompleteCallback = () -> this.lambda$func_227135_a_$0(chunkPos);
            this.dataFile.write(byteBuffer, n * 4096);
        }
        int n8 = (int)(Util.millisecondsSinceEpoch() / 1000L);
        this.offsets.put(n2, this.func_227132_a_(n, n7));
        this.chunkTimestamps.put(n2, n8);
        this.func_227140_b_();
        iCompleteCallback.run();
        if (n4 != 0) {
            this.field_227128_i_.func_227121_b_(n4, n5);
        }
    }

    private ByteBuffer func_227129_a_() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.putInt(1);
        byteBuffer.put((byte)(this.field_227125_e_.func_227165_a_() | 0x80));
        byteBuffer.flip();
        return byteBuffer;
    }

    private ICompleteCallback func_227138_a_(Path path, ByteBuffer byteBuffer) throws IOException {
        Path path2 = Files.createTempFile(this.field_227124_d_, "tmp", (String)null, new FileAttribute[0]);
        try (FileChannel fileChannel = FileChannel.open(path2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);){
            byteBuffer.position(5);
            fileChannel.write(byteBuffer);
        }
        return () -> RegionFile.lambda$func_227138_a_$1(path2, path);
    }

    private void func_227140_b_() throws IOException {
        this.field_227126_f_.position(0);
        this.dataFile.write(this.field_227126_f_, 0L);
    }

    private int getOffset(ChunkPos chunkPos) {
        return this.offsets.get(RegionFile.getIndex(chunkPos));
    }

    public boolean contains(ChunkPos chunkPos) {
        return this.getOffset(chunkPos) != 0;
    }

    private static int getIndex(ChunkPos chunkPos) {
        return chunkPos.getRegionPositionX() + chunkPos.getRegionPositionZ() * 32;
    }

    @Override
    public void close() throws IOException {
        try {
            this.func_227143_c_();
        } finally {
            try {
                this.dataFile.force(false);
            } finally {
                this.dataFile.close();
            }
        }
    }

    private void func_227143_c_() throws IOException {
        int n;
        int n2 = (int)this.dataFile.size();
        if (n2 != (n = RegionFile.func_227144_c_(n2) * 4096)) {
            ByteBuffer byteBuffer = field_227123_b_.duplicate();
            byteBuffer.position(0);
            this.dataFile.write(byteBuffer, n - 1);
        }
    }

    private static void lambda$func_227138_a_$1(Path path, Path path2) throws IOException {
        Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING);
    }

    private void lambda$func_227135_a_$0(ChunkPos chunkPos) throws IOException {
        Files.deleteIfExists(this.func_227145_e_(chunkPos));
    }

    class ChunkBuffer
    extends ByteArrayOutputStream {
        private final ChunkPos pos;
        final RegionFile this$0;

        public ChunkBuffer(RegionFile regionFile, ChunkPos chunkPos) {
            this.this$0 = regionFile;
            super(8096);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(regionFile.field_227125_e_.func_227165_a_());
            this.pos = chunkPos;
        }

        @Override
        public void close() throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.buf, 0, this.count);
            byteBuffer.putInt(0, this.count - 5 + 1);
            this.this$0.func_227135_a_(this.pos, byteBuffer);
        }
    }

    static interface ICompleteCallback {
        public void run() throws IOException;
    }
}

