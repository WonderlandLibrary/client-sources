package com.mpatric.mp3agic;

import java.io.*;
import java.nio.channels.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class Mp3File extends FileWrapper
{
    private static final int DEFAULT_BUFFER_LENGTH = 65536;
    private static final int MINIMUM_BUFFER_LENGTH = 40;
    private static final int XING_MARKER_OFFSET_1 = 13;
    private static final int XING_MARKER_OFFSET_2 = 21;
    private static final int XING_MARKER_OFFSET_3 = 36;
    protected int bufferLength;
    private int xingOffset;
    private int startOffset;
    private int endOffset;
    private int frameCount;
    private Map<Integer, MutableInteger> bitrates;
    private int xingBitrate;
    private double bitrate;
    private String channelMode;
    private String emphasis;
    private String layer;
    private String modeExtension;
    private int sampleRate;
    private boolean copyright;
    private boolean original;
    private String version;
    private ID3v1 id3v1Tag;
    private ID3v2 id3v2Tag;
    private byte[] customTag;
    private boolean scanFile;
    
    protected Mp3File() {
        this.xingOffset = -1;
        this.startOffset = -1;
        this.endOffset = -1;
        this.frameCount = 0;
        this.bitrates = new HashMap<Integer, MutableInteger>();
        this.bitrate = 0.0;
    }
    
    public Mp3File(final String s) throws IOException, UnsupportedTagException, InvalidDataException {
        this(s, 65536, true);
    }
    
    public Mp3File(final String s, final int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(s, n, true);
    }
    
    public Mp3File(final String s, final boolean b) throws IOException, UnsupportedTagException, InvalidDataException {
        this(s, 65536, b);
    }
    
    public Mp3File(final String s, final int n, final boolean b) throws IOException, UnsupportedTagException, InvalidDataException {
        super(s);
        this.xingOffset = -1;
        this.startOffset = -1;
        this.endOffset = -1;
        this.frameCount = 0;
        this.bitrates = new HashMap<Integer, MutableInteger>();
        this.bitrate = 0.0;
        this.init(n, b);
    }
    
    public Mp3File(final File file) throws IOException, UnsupportedTagException, InvalidDataException {
        this(file, 65536, true);
    }
    
    public Mp3File(final File file, final int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(file, n, true);
    }
    
    public Mp3File(final File file, final int n, final boolean b) throws IOException, UnsupportedTagException, InvalidDataException {
        super(file);
        this.xingOffset = -1;
        this.startOffset = -1;
        this.endOffset = -1;
        this.frameCount = 0;
        this.bitrates = new HashMap<Integer, MutableInteger>();
        this.bitrate = 0.0;
        this.init(n, b);
    }
    
    public Mp3File(final Path path) throws IOException, UnsupportedTagException, InvalidDataException {
        this(path, 65536, true);
    }
    
    public Mp3File(final Path path, final int n) throws IOException, UnsupportedTagException, InvalidDataException {
        this(path, n, true);
    }
    
    public Mp3File(final Path path, final int n, final boolean b) throws IOException, UnsupportedTagException, InvalidDataException {
        super(path);
        this.xingOffset = -1;
        this.startOffset = -1;
        this.endOffset = -1;
        this.frameCount = 0;
        this.bitrates = new HashMap<Integer, MutableInteger>();
        this.bitrate = 0.0;
        this.init(n, b);
    }
    
    private void init(final int bufferLength, final boolean scanFile) throws IOException, UnsupportedTagException, InvalidDataException {
        if (bufferLength < 41) {
            throw new IllegalArgumentException("Buffer too small");
        }
        this.bufferLength = bufferLength;
        this.scanFile = scanFile;
        try (final SeekableByteChannel byteChannel = Files.newByteChannel(this.path, StandardOpenOption.READ)) {
            this.initId3v1Tag(byteChannel);
            this.scanFile(byteChannel);
            if (this.startOffset < 0) {
                throw new InvalidDataException("No mpegs frames found");
            }
            this.initId3v2Tag(byteChannel);
            if (scanFile) {
                this.initCustomTag(byteChannel);
            }
        }
    }
    
    protected int preScanFile(final SeekableByteChannel seekableByteChannel) {
        final ByteBuffer allocate = ByteBuffer.allocate(10);
        try {
            seekableByteChannel.position(0L);
            allocate.clear();
            if (seekableByteChannel.read(allocate) == 10) {
                try {
                    final byte[] array = allocate.array();
                    ID3v2TagFactory.sanityCheckTag(array);
                    return 10 + BufferTools.unpackSynchsafeInteger(array[6], array[7], array[8], array[9]);
                }
                catch (NoSuchTagException ex) {}
                catch (UnsupportedTagException ex2) {}
            }
        }
        catch (IOException ex3) {}
        return 0;
    }
    
    private void scanFile(final SeekableByteChannel seekableByteChannel) throws IOException, InvalidDataException {
        final ByteBuffer allocate = ByteBuffer.allocate(this.bufferLength);
        int preScanFile = this.preScanFile(seekableByteChannel);
        seekableByteChannel.position(preScanFile);
        int i = 0;
        int startOffset = preScanFile;
        while (i == 0) {
            allocate.clear();
            final int read = seekableByteChannel.read(allocate);
            final byte[] array = allocate.array();
            if (read < this.bufferLength) {
                i = 1;
            }
            if (read >= 40) {
                try {
                    int scanBlockForStart = 0;
                    if (this.startOffset < 0) {
                        scanBlockForStart = this.scanBlockForStart(array, read, preScanFile, scanBlockForStart);
                        if (this.startOffset >= 0 && !this.scanFile) {
                            return;
                        }
                        startOffset = this.startOffset;
                    }
                    preScanFile += this.scanBlock(array, read, preScanFile, scanBlockForStart);
                    seekableByteChannel.position(preScanFile);
                }
                catch (InvalidDataException ex) {
                    if (this.frameCount >= 2) {
                        return;
                    }
                    this.startOffset = -1;
                    this.xingOffset = -1;
                    this.frameCount = 0;
                    this.bitrates.clear();
                    i = 0;
                    preScanFile = startOffset + 1;
                    if (preScanFile == 0) {
                        throw new InvalidDataException("Valid start of mpeg frames not found", ex);
                    }
                    seekableByteChannel.position(preScanFile);
                }
            }
        }
    }
    
    private int scanBlockForStart(final byte[] array, final int n, final int n2, int i) {
        while (i < n - 40) {
            if (array[i] == -1 && (array[i + 1] & 0xFFFFFFE0) == 0xFFFFFFE0) {
                try {
                    final MpegFrame mpegFrame = new MpegFrame(array[i], array[i + 1], array[i + 2], array[i + 3]);
                    if (this.xingOffset >= 0 || !this.isXingFrame(array, i)) {
                        this.startOffset = n2 + i;
                        this.channelMode = mpegFrame.getChannelMode();
                        this.emphasis = mpegFrame.getEmphasis();
                        this.layer = mpegFrame.getLayer();
                        this.modeExtension = mpegFrame.getModeExtension();
                        this.sampleRate = mpegFrame.getSampleRate();
                        this.version = mpegFrame.getVersion();
                        this.copyright = mpegFrame.isCopyright();
                        this.original = mpegFrame.isOriginal();
                        ++this.frameCount;
                        this.addBitrate(mpegFrame.getBitrate());
                        i += mpegFrame.getLengthInBytes();
                        return i;
                    }
                    this.xingOffset = n2 + i;
                    this.xingBitrate = mpegFrame.getBitrate();
                    i += mpegFrame.getLengthInBytes();
                }
                catch (InvalidDataException ex) {
                    ++i;
                }
            }
            else {
                ++i;
            }
        }
        return i;
    }
    
    private int scanBlock(final byte[] array, final int n, final int n2, int i) throws InvalidDataException {
        while (i < n - 40) {
            final MpegFrame mpegFrame = new MpegFrame(array[i], array[i + 1], array[i + 2], array[i + 3]);
            this.sanityCheckFrame(mpegFrame, n2 + i);
            if (n2 + i + mpegFrame.getLengthInBytes() - 1 >= this.maxEndOffset()) {
                break;
            }
            this.endOffset = n2 + i + mpegFrame.getLengthInBytes() - 1;
            ++this.frameCount;
            this.addBitrate(mpegFrame.getBitrate());
            i += mpegFrame.getLengthInBytes();
        }
        return i;
    }
    
    private int maxEndOffset() {
        int n = (int)this.getLength();
        if (this.hasId3v1Tag()) {
            n -= 128;
        }
        return n;
    }
    
    private boolean isXingFrame(final byte[] array, final int n) {
        if (array.length >= n + 13 + 3) {
            if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 13, 4))) {
                return true;
            }
            if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 13, 4))) {
                return true;
            }
            if (array.length >= n + 21 + 3) {
                if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 21, 4))) {
                    return true;
                }
                if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 21, 4))) {
                    return true;
                }
                if (array.length >= n + 36 + 3) {
                    if ("Xing".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 36, 4))) {
                        return true;
                    }
                    if ("Info".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 36, 4))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void sanityCheckFrame(final MpegFrame mpegFrame, final int n) throws InvalidDataException {
        if (this.sampleRate != mpegFrame.getSampleRate()) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if (!this.layer.equals(mpegFrame.getLayer())) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if (!this.version.equals(mpegFrame.getVersion())) {
            throw new InvalidDataException("Inconsistent frame header");
        }
        if (n + mpegFrame.getLengthInBytes() > this.getLength()) {
            throw new InvalidDataException("Frame would extend beyond end of file");
        }
    }
    
    private void addBitrate(final int n) {
        final Integer n2 = new Integer(n);
        final MutableInteger mutableInteger = this.bitrates.get(n2);
        if (mutableInteger != null) {
            mutableInteger.increment();
        }
        else {
            this.bitrates.put(n2, new MutableInteger(1));
        }
        this.bitrate = (this.bitrate * (this.frameCount - 1) + n) / this.frameCount;
    }
    
    private void initId3v1Tag(final SeekableByteChannel seekableByteChannel) throws IOException {
        final ByteBuffer allocate = ByteBuffer.allocate(128);
        seekableByteChannel.position(this.getLength() - 128L);
        allocate.clear();
        if (seekableByteChannel.read(allocate) < 128) {
            throw new IOException("Not enough bytes read");
        }
        try {
            this.id3v1Tag = new ID3v1Tag(allocate.array());
        }
        catch (NoSuchTagException ex) {
            this.id3v1Tag = null;
        }
    }
    
    private void initId3v2Tag(final SeekableByteChannel seekableByteChannel) throws IOException, UnsupportedTagException, InvalidDataException {
        if (this.xingOffset == 0 || this.startOffset == 0) {
            this.id3v2Tag = null;
        }
        else {
            int n;
            if (this.hasXingFrame()) {
                n = this.xingOffset;
            }
            else {
                n = this.startOffset;
            }
            final ByteBuffer allocate = ByteBuffer.allocate(n);
            seekableByteChannel.position(0L);
            allocate.clear();
            if (seekableByteChannel.read(allocate) < n) {
                throw new IOException("Not enough bytes read");
            }
            try {
                this.id3v2Tag = ID3v2TagFactory.createTag(allocate.array());
            }
            catch (NoSuchTagException ex) {
                this.id3v2Tag = null;
            }
        }
    }
    
    private void initCustomTag(final SeekableByteChannel seekableByteChannel) throws IOException {
        int n = (int)(this.getLength() - (this.endOffset + 1));
        if (this.hasId3v1Tag()) {
            n -= 128;
        }
        if (n <= 0) {
            this.customTag = null;
        }
        else {
            final ByteBuffer allocate = ByteBuffer.allocate(n);
            seekableByteChannel.position(this.endOffset + 1);
            allocate.clear();
            final int read = seekableByteChannel.read(allocate);
            this.customTag = allocate.array();
            if (read < n) {
                throw new IOException("Not enough bytes read");
            }
        }
    }
    
    public int getFrameCount() {
        return this.frameCount;
    }
    
    public int getStartOffset() {
        return this.startOffset;
    }
    
    public int getEndOffset() {
        return this.endOffset;
    }
    
    public long getLengthInMilliseconds() {
        return (long)(8 * (this.endOffset - this.startOffset) / this.bitrate + 0.5);
    }
    
    public long getLengthInSeconds() {
        return (this.getLengthInMilliseconds() + 500L) / 1000L;
    }
    
    public boolean isVbr() {
        return this.bitrates.size() > 1;
    }
    
    public int getBitrate() {
        return (int)(this.bitrate + 0.5);
    }
    
    public Map<Integer, MutableInteger> getBitrates() {
        return this.bitrates;
    }
    
    public String getChannelMode() {
        return this.channelMode;
    }
    
    public boolean isCopyright() {
        return this.copyright;
    }
    
    public String getEmphasis() {
        return this.emphasis;
    }
    
    public String getLayer() {
        return this.layer;
    }
    
    public String getModeExtension() {
        return this.modeExtension;
    }
    
    public boolean isOriginal() {
        return this.original;
    }
    
    public int getSampleRate() {
        return this.sampleRate;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public boolean hasXingFrame() {
        return this.xingOffset >= 0;
    }
    
    public int getXingOffset() {
        return this.xingOffset;
    }
    
    public int getXingBitrate() {
        return this.xingBitrate;
    }
    
    public boolean hasId3v1Tag() {
        return this.id3v1Tag != null;
    }
    
    public ID3v1 getId3v1Tag() {
        return this.id3v1Tag;
    }
    
    public void setId3v1Tag(final ID3v1 id3v1Tag) {
        this.id3v1Tag = id3v1Tag;
    }
    
    public void removeId3v1Tag() {
        this.id3v1Tag = null;
    }
    
    public boolean hasId3v2Tag() {
        return this.id3v2Tag != null;
    }
    
    public ID3v2 getId3v2Tag() {
        return this.id3v2Tag;
    }
    
    public void setId3v2Tag(final ID3v2 id3v2Tag) {
        this.id3v2Tag = id3v2Tag;
    }
    
    public void removeId3v2Tag() {
        this.id3v2Tag = null;
    }
    
    public boolean hasCustomTag() {
        return this.customTag != null;
    }
    
    public byte[] getCustomTag() {
        return this.customTag;
    }
    
    public void setCustomTag(final byte[] customTag) {
        this.customTag = customTag;
    }
    
    public void removeCustomTag() {
        this.customTag = null;
    }
    
    public void save(final String s) throws IOException, NotSupportedException {
        if (this.path.toAbsolutePath().compareTo(Paths.get(s, new String[0]).toAbsolutePath()) == 0) {
            throw new IllegalArgumentException("Save filename same as source filename");
        }
        try (final SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get(s, new String[0]), EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), (FileAttribute<?>[])new FileAttribute[0])) {
            if (this.hasId3v2Tag()) {
                final ByteBuffer wrap = ByteBuffer.wrap(this.id3v2Tag.toBytes());
                wrap.rewind();
                byteChannel.write(wrap);
            }
            this.saveMpegFrames(byteChannel);
            if (this.hasCustomTag()) {
                final ByteBuffer wrap2 = ByteBuffer.wrap(this.customTag);
                wrap2.rewind();
                byteChannel.write(wrap2);
            }
            if (this.hasId3v1Tag()) {
                final ByteBuffer wrap3 = ByteBuffer.wrap(this.id3v1Tag.toBytes());
                wrap3.rewind();
                byteChannel.write(wrap3);
            }
            byteChannel.close();
        }
    }
    
    private void saveMpegFrames(final SeekableByteChannel seekableByteChannel) throws IOException {
        int n = this.xingOffset;
        if (n < 0) {
            n = this.startOffset;
        }
        if (n < 0) {
            return;
        }
        if (this.endOffset < n) {
            return;
        }
        final SeekableByteChannel byteChannel = Files.newByteChannel(this.path, StandardOpenOption.READ);
        final ByteBuffer allocate = ByteBuffer.allocate(this.bufferLength);
        try {
            byteChannel.position(n);
            while (true) {
                allocate.clear();
                final int read = byteChannel.read(allocate);
                allocate.rewind();
                if (n + read > this.endOffset) {
                    break;
                }
                allocate.limit(read);
                seekableByteChannel.write(allocate);
                n += read;
            }
            allocate.limit(this.endOffset - n + 1);
            seekableByteChannel.write(allocate);
        }
        finally {
            byteChannel.close();
        }
    }
}
