package com.mpatric.mp3agic;

public class MpegFrame
{
    public static final String MPEG_VERSION_1_0 = "1.0";
    public static final String MPEG_VERSION_2_0 = "2.0";
    public static final String MPEG_VERSION_2_5 = "2.5";
    public static final String MPEG_LAYER_1 = "I";
    public static final String MPEG_LAYER_2 = "II";
    public static final String MPEG_LAYER_3 = "III";
    public static final String[] MPEG_LAYERS;
    public static final String CHANNEL_MODE_MONO = "Mono";
    public static final String CHANNEL_MODE_DUAL_MONO = "Dual mono";
    public static final String CHANNEL_MODE_JOINT_STEREO = "Joint stereo";
    public static final String CHANNEL_MODE_STEREO = "Stereo";
    public static final String MODE_EXTENSION_BANDS_4_31 = "Bands 4-31";
    public static final String MODE_EXTENSION_BANDS_8_31 = "Bands 8-31";
    public static final String MODE_EXTENSION_BANDS_12_31 = "Bands 12-31";
    public static final String MODE_EXTENSION_BANDS_16_31 = "Bands 16-31";
    public static final String MODE_EXTENSION_NONE = "None";
    public static final String MODE_EXTENSION_INTENSITY_STEREO = "Intensity stereo";
    public static final String MODE_EXTENSION_M_S_STEREO = "M/S stereo";
    public static final String MODE_EXTENSION_INTENSITY_M_S_STEREO = "Intensity & M/S stereo";
    public static final String MODE_EXTENSION_NA = "n/a";
    public static final String EMPHASIS_NONE = "None";
    public static final String EMPHASIS__50_15_MS = "50/15 ms";
    public static final String EMPHASIS_CCITT_J_17 = "CCITT J.17";
    private static final int FRAME_DATA_LENGTH = 4;
    private static final int FRAME_SYNC = 2047;
    private static final long BITMASK_FRAME_SYNC = 4292870144L;
    private static final long BITMASK_VERSION = 1572864L;
    private static final long BITMASK_LAYER = 393216L;
    private static final long BITMASK_PROTECTION = 65536L;
    private static final long BITMASK_BITRATE = 61440L;
    private static final long BITMASK_SAMPLE_RATE = 3072L;
    private static final long BITMASK_PADDING = 512L;
    private static final long BITMASK_PRIVATE = 256L;
    private static final long BITMASK_CHANNEL_MODE = 192L;
    private static final long BITMASK_MODE_EXTENSION = 48L;
    private static final long BITMASK_COPYRIGHT = 8L;
    private static final long BITMASK_ORIGINAL = 4L;
    private static final long BITMASK_EMPHASIS = 3L;
    private String version;
    private int layer;
    private boolean protection;
    private int bitrate;
    private int sampleRate;
    private boolean padding;
    private boolean privat;
    private String channelMode;
    private String modeExtension;
    private boolean copyright;
    private boolean original;
    private String emphasis;
    
    public MpegFrame(final byte[] array) throws InvalidDataException {
        if (array.length < 4) {
            throw new InvalidDataException("Mpeg frame too short");
        }
        this.setFields(BufferTools.unpackInteger(array[0], array[1], array[2], array[3]));
    }
    
    public MpegFrame(final byte b, final byte b2, final byte b3, final byte b4) throws InvalidDataException {
        this.setFields(BufferTools.unpackInteger(b, b2, b3, b4));
    }
    
    protected MpegFrame() {
    }
    
    private void setFields(final long n) throws InvalidDataException {
        if (this.extractField(n, 4292870144L) != 2047L) {
            throw new InvalidDataException("Frame sync missing");
        }
        this.setVersion(this.extractField(n, 1572864L));
        this.setLayer(this.extractField(n, 393216L));
        this.setProtection(this.extractField(n, 65536L));
        this.setBitRate(this.extractField(n, 61440L));
        this.setSampleRate(this.extractField(n, 3072L));
        this.setPadding(this.extractField(n, 512L));
        this.setPrivate(this.extractField(n, 256L));
        this.setChannelMode(this.extractField(n, 192L));
        this.setModeExtension(this.extractField(n, 48L));
        this.setCopyright(this.extractField(n, 8L));
        this.setOriginal(this.extractField(n, 4L));
        this.setEmphasis(this.extractField(n, 3L));
    }
    
    protected int extractField(final long n, final long n2) {
        int n3 = 0;
        for (int i = 0; i <= 31; ++i) {
            if ((n2 >> i & 0x1L) != 0x0L) {
                n3 = i;
                break;
            }
        }
        return (int)(n >> n3 & n2 >> n3);
    }
    
    private void setVersion(final int n) throws InvalidDataException {
        switch (n) {
            case 0: {
                this.version = "2.5";
                break;
            }
            case 2: {
                this.version = "2.0";
                break;
            }
            case 3: {
                this.version = "1.0";
                break;
            }
            default: {
                throw new InvalidDataException("Invalid mpeg audio version in frame header");
            }
        }
    }
    
    private void setLayer(final int n) throws InvalidDataException {
        switch (n) {
            case 1: {
                this.layer = 3;
                break;
            }
            case 2: {
                this.layer = 2;
                break;
            }
            case 3: {
                this.layer = 1;
                break;
            }
            default: {
                throw new InvalidDataException("Invalid mpeg layer description in frame header");
            }
        }
    }
    
    private void setProtection(final int n) {
        this.protection = (n == 1);
    }
    
    private void setBitRate(final int n) throws InvalidDataException {
        if ("1.0".equals(this.version)) {
            if (this.layer == 1) {
                switch (n) {
                    case 1: {
                        this.bitrate = 32;
                        return;
                    }
                    case 2: {
                        this.bitrate = 64;
                        return;
                    }
                    case 3: {
                        this.bitrate = 96;
                        return;
                    }
                    case 4: {
                        this.bitrate = 128;
                        return;
                    }
                    case 5: {
                        this.bitrate = 160;
                        return;
                    }
                    case 6: {
                        this.bitrate = 192;
                        return;
                    }
                    case 7: {
                        this.bitrate = 224;
                        return;
                    }
                    case 8: {
                        this.bitrate = 256;
                        return;
                    }
                    case 9: {
                        this.bitrate = 288;
                        return;
                    }
                    case 10: {
                        this.bitrate = 320;
                        return;
                    }
                    case 11: {
                        this.bitrate = 352;
                        return;
                    }
                    case 12: {
                        this.bitrate = 384;
                        return;
                    }
                    case 13: {
                        this.bitrate = 416;
                        return;
                    }
                    case 14: {
                        this.bitrate = 448;
                        return;
                    }
                }
            }
            else if (this.layer == 2) {
                switch (n) {
                    case 1: {
                        this.bitrate = 32;
                        return;
                    }
                    case 2: {
                        this.bitrate = 48;
                        return;
                    }
                    case 3: {
                        this.bitrate = 56;
                        return;
                    }
                    case 4: {
                        this.bitrate = 64;
                        return;
                    }
                    case 5: {
                        this.bitrate = 80;
                        return;
                    }
                    case 6: {
                        this.bitrate = 96;
                        return;
                    }
                    case 7: {
                        this.bitrate = 112;
                        return;
                    }
                    case 8: {
                        this.bitrate = 128;
                        return;
                    }
                    case 9: {
                        this.bitrate = 160;
                        return;
                    }
                    case 10: {
                        this.bitrate = 192;
                        return;
                    }
                    case 11: {
                        this.bitrate = 224;
                        return;
                    }
                    case 12: {
                        this.bitrate = 256;
                        return;
                    }
                    case 13: {
                        this.bitrate = 320;
                        return;
                    }
                    case 14: {
                        this.bitrate = 384;
                        return;
                    }
                }
            }
            else if (this.layer == 3) {
                switch (n) {
                    case 1: {
                        this.bitrate = 32;
                        return;
                    }
                    case 2: {
                        this.bitrate = 40;
                        return;
                    }
                    case 3: {
                        this.bitrate = 48;
                        return;
                    }
                    case 4: {
                        this.bitrate = 56;
                        return;
                    }
                    case 5: {
                        this.bitrate = 64;
                        return;
                    }
                    case 6: {
                        this.bitrate = 80;
                        return;
                    }
                    case 7: {
                        this.bitrate = 96;
                        return;
                    }
                    case 8: {
                        this.bitrate = 112;
                        return;
                    }
                    case 9: {
                        this.bitrate = 128;
                        return;
                    }
                    case 10: {
                        this.bitrate = 160;
                        return;
                    }
                    case 11: {
                        this.bitrate = 192;
                        return;
                    }
                    case 12: {
                        this.bitrate = 224;
                        return;
                    }
                    case 13: {
                        this.bitrate = 256;
                        return;
                    }
                    case 14: {
                        this.bitrate = 320;
                        return;
                    }
                }
            }
        }
        else if ("2.0".equals(this.version) || "2.5".equals(this.version)) {
            if (this.layer == 1) {
                switch (n) {
                    case 1: {
                        this.bitrate = 32;
                        return;
                    }
                    case 2: {
                        this.bitrate = 48;
                        return;
                    }
                    case 3: {
                        this.bitrate = 56;
                        return;
                    }
                    case 4: {
                        this.bitrate = 64;
                        return;
                    }
                    case 5: {
                        this.bitrate = 80;
                        return;
                    }
                    case 6: {
                        this.bitrate = 96;
                        return;
                    }
                    case 7: {
                        this.bitrate = 112;
                        return;
                    }
                    case 8: {
                        this.bitrate = 128;
                        return;
                    }
                    case 9: {
                        this.bitrate = 144;
                        return;
                    }
                    case 10: {
                        this.bitrate = 160;
                        return;
                    }
                    case 11: {
                        this.bitrate = 176;
                        return;
                    }
                    case 12: {
                        this.bitrate = 192;
                        return;
                    }
                    case 13: {
                        this.bitrate = 224;
                        return;
                    }
                    case 14: {
                        this.bitrate = 256;
                        return;
                    }
                }
            }
            else if (this.layer == 2 || this.layer == 3) {
                switch (n) {
                    case 1: {
                        this.bitrate = 8;
                        return;
                    }
                    case 2: {
                        this.bitrate = 16;
                        return;
                    }
                    case 3: {
                        this.bitrate = 24;
                        return;
                    }
                    case 4: {
                        this.bitrate = 32;
                        return;
                    }
                    case 5: {
                        this.bitrate = 40;
                        return;
                    }
                    case 6: {
                        this.bitrate = 48;
                        return;
                    }
                    case 7: {
                        this.bitrate = 56;
                        return;
                    }
                    case 8: {
                        this.bitrate = 64;
                        return;
                    }
                    case 9: {
                        this.bitrate = 80;
                        return;
                    }
                    case 10: {
                        this.bitrate = 96;
                        return;
                    }
                    case 11: {
                        this.bitrate = 112;
                        return;
                    }
                    case 12: {
                        this.bitrate = 128;
                        return;
                    }
                    case 13: {
                        this.bitrate = 144;
                        return;
                    }
                    case 14: {
                        this.bitrate = 160;
                        return;
                    }
                }
            }
        }
        throw new InvalidDataException("Invalid bitrate in frame header");
    }
    
    private void setSampleRate(final int n) throws InvalidDataException {
        if ("1.0".equals(this.version)) {
            switch (n) {
                case 0: {
                    this.sampleRate = 44100;
                    return;
                }
                case 1: {
                    this.sampleRate = 48000;
                    return;
                }
                case 2: {
                    this.sampleRate = 32000;
                    return;
                }
            }
        }
        else if ("2.0".equals(this.version)) {
            switch (n) {
                case 0: {
                    this.sampleRate = 22050;
                    return;
                }
                case 1: {
                    this.sampleRate = 24000;
                    return;
                }
                case 2: {
                    this.sampleRate = 16000;
                    return;
                }
            }
        }
        else if ("2.5".equals(this.version)) {
            switch (n) {
                case 0: {
                    this.sampleRate = 11025;
                    return;
                }
                case 1: {
                    this.sampleRate = 12000;
                    return;
                }
                case 2: {
                    this.sampleRate = 8000;
                    return;
                }
            }
        }
        throw new InvalidDataException("Invalid sample rate in frame header");
    }
    
    private void setPadding(final int n) {
        this.padding = (n == 1);
    }
    
    private void setPrivate(final int n) {
        this.privat = (n == 1);
    }
    
    private void setChannelMode(final int n) throws InvalidDataException {
        switch (n) {
            case 0: {
                this.channelMode = "Stereo";
                break;
            }
            case 1: {
                this.channelMode = "Joint stereo";
                break;
            }
            case 2: {
                this.channelMode = "Dual mono";
                break;
            }
            case 3: {
                this.channelMode = "Mono";
                break;
            }
            default: {
                throw new InvalidDataException("Invalid channel mode in frame header");
            }
        }
    }
    
    private void setModeExtension(final int n) throws InvalidDataException {
        if (!"Joint stereo".equals(this.channelMode)) {
            this.modeExtension = "n/a";
            return;
        }
        if (this.layer == 1 || this.layer == 2) {
            switch (n) {
                case 0: {
                    this.modeExtension = "Bands 4-31";
                    return;
                }
                case 1: {
                    this.modeExtension = "Bands 8-31";
                    return;
                }
                case 2: {
                    this.modeExtension = "Bands 12-31";
                    return;
                }
                case 3: {
                    this.modeExtension = "Bands 16-31";
                    return;
                }
            }
        }
        else if (this.layer == 3) {
            switch (n) {
                case 0: {
                    this.modeExtension = "None";
                    return;
                }
                case 1: {
                    this.modeExtension = "Intensity stereo";
                    return;
                }
                case 2: {
                    this.modeExtension = "M/S stereo";
                    return;
                }
                case 3: {
                    this.modeExtension = "Intensity & M/S stereo";
                    return;
                }
            }
        }
        throw new InvalidDataException("Invalid mode extension in frame header");
    }
    
    private void setCopyright(final int n) {
        this.copyright = (n == 1);
    }
    
    private void setOriginal(final int n) {
        this.original = (n == 1);
    }
    
    private void setEmphasis(final int n) throws InvalidDataException {
        switch (n) {
            case 0: {
                this.emphasis = "None";
                break;
            }
            case 1: {
                this.emphasis = "50/15 ms";
                break;
            }
            case 3: {
                this.emphasis = "CCITT J.17";
                break;
            }
            default: {
                throw new InvalidDataException("Invalid emphasis in frame header");
            }
        }
    }
    
    public int getBitrate() {
        return this.bitrate;
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
        return MpegFrame.MPEG_LAYERS[this.layer];
    }
    
    public String getModeExtension() {
        return this.modeExtension;
    }
    
    public boolean isOriginal() {
        return this.original;
    }
    
    public boolean hasPadding() {
        return this.padding;
    }
    
    public boolean isPrivate() {
        return this.privat;
    }
    
    public boolean isProtection() {
        return this.protection;
    }
    
    public int getSampleRate() {
        return this.sampleRate;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public int getLengthInBytes() {
        int n;
        if (this.padding) {
            n = 1;
        }
        else {
            n = 0;
        }
        long n2;
        if (this.layer == 1) {
            n2 = 48000 * this.bitrate / this.sampleRate + n * 4;
        }
        else {
            n2 = 144000 * this.bitrate / this.sampleRate + n;
        }
        return (int)n2;
    }
    
    static {
        MPEG_LAYERS = new String[] { null, "I", "II", "III" };
    }
}
