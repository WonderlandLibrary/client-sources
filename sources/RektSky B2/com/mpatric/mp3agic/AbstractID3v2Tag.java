package com.mpatric.mp3agic;

import java.io.*;
import java.util.*;

public abstract class AbstractID3v2Tag implements ID3v2
{
    public static final String ID_IMAGE = "APIC";
    public static final String ID_ENCODER = "TENC";
    public static final String ID_URL = "WXXX";
    public static final String ID_ARTIST_URL = "WOAR";
    public static final String ID_COMMERCIAL_URL = "WCOM";
    public static final String ID_COPYRIGHT_URL = "WCOP";
    public static final String ID_AUDIOFILE_URL = "WOAF";
    public static final String ID_AUDIOSOURCE_URL = "WOAS";
    public static final String ID_RADIOSTATION_URL = "WORS";
    public static final String ID_PAYMENT_URL = "WPAY";
    public static final String ID_PUBLISHER_URL = "WPUB";
    public static final String ID_COPYRIGHT = "TCOP";
    public static final String ID_ORIGINAL_ARTIST = "TOPE";
    public static final String ID_BPM = "TBPM";
    public static final String ID_COMPOSER = "TCOM";
    public static final String ID_PUBLISHER = "TPUB";
    public static final String ID_COMMENT = "COMM";
    public static final String ID_TEXT_LYRICS = "USLT";
    public static final String ID_GENRE = "TCON";
    public static final String ID_YEAR = "TYER";
    public static final String ID_DATE = "TDAT";
    public static final String ID_ALBUM = "TALB";
    public static final String ID_TITLE = "TIT2";
    public static final String ID_KEY = "TKEY";
    public static final String ID_ARTIST = "TPE1";
    public static final String ID_ALBUM_ARTIST = "TPE2";
    public static final String ID_TRACK = "TRCK";
    public static final String ID_PART_OF_SET = "TPOS";
    public static final String ID_COMPILATION = "TCMP";
    public static final String ID_CHAPTER_TOC = "CTOC";
    public static final String ID_CHAPTER = "CHAP";
    public static final String ID_GROUPING = "TIT1";
    public static final String ID_RATING = "POPM";
    public static final String ID_IMAGE_OBSELETE = "PIC";
    public static final String ID_ENCODER_OBSELETE = "TEN";
    public static final String ID_URL_OBSELETE = "WXX";
    public static final String ID_COPYRIGHT_OBSELETE = "TCR";
    public static final String ID_ORIGINAL_ARTIST_OBSELETE = "TOA";
    public static final String ID_BPM_OBSELETE = "TBP";
    public static final String ID_COMPOSER_OBSELETE = "TCM";
    public static final String ID_PUBLISHER_OBSELETE = "TBP";
    public static final String ID_COMMENT_OBSELETE = "COM";
    public static final String ID_GENRE_OBSELETE = "TCO";
    public static final String ID_YEAR_OBSELETE = "TYE";
    public static final String ID_DATE_OBSELETE = "TDA";
    public static final String ID_ALBUM_OBSELETE = "TAL";
    public static final String ID_TITLE_OBSELETE = "TT2";
    public static final String ID_KEY_OBSELETE = "TKE";
    public static final String ID_ARTIST_OBSELETE = "TP1";
    public static final String ID_ALBUM_ARTIST_OBSELETE = "TP2";
    public static final String ID_TRACK_OBSELETE = "TRK";
    public static final String ID_PART_OF_SET_OBSELETE = "TPA";
    public static final String ID_COMPILATION_OBSELETE = "TCP";
    public static final String ID_GROUPING_OBSELETE = "TT1";
    public static final byte PICTURETYPE_OTHER = 0;
    public static final byte PICTURETYPE_32PXICON = 1;
    public static final byte PICTURETYPE_OTHERICON = 2;
    public static final byte PICTURETYPE_FRONTCOVER = 3;
    public static final byte PICTURETYPE_BACKCOVER = 4;
    public static final byte PICTURETYPE_LEAFLET = 5;
    public static final byte PICTURETYPE_MEDIA = 6;
    public static final byte PICTURETYPE_LEADARTIST = 7;
    public static final byte PICTURETYPE_ARTIST = 8;
    public static final byte PICTURETYPE_CONDUCTOR = 9;
    public static final byte PICTURETYPE_BAND = 10;
    public static final byte PICTURETYPE_COMPOSER = 11;
    public static final byte PICTURETYPE_LYRICIST = 12;
    public static final byte PICTURETYPE_RECORDINGLOCATION = 13;
    public static final byte PICTURETYPE_DURING_RECORDING = 14;
    public static final byte PICTURETYPE_DURING_PERFORMANCE = 15;
    public static final byte PICTURETYPE_SCREEN_CAPTURE = 16;
    public static final byte PICTURETYPE_ILLUSTRATION = 18;
    public static final byte PICTURETYPE_BAND_LOGOTYPE = 19;
    public static final byte PICTURETYPE_PUBLISHER_LOGOTYPE = 20;
    protected static final String TAG = "ID3";
    protected static final String FOOTER_TAG = "3DI";
    protected static final int HEADER_LENGTH = 10;
    protected static final int FOOTER_LENGTH = 10;
    protected static final int MAJOR_VERSION_OFFSET = 3;
    protected static final int MINOR_VERSION_OFFSET = 4;
    protected static final int FLAGS_OFFSET = 5;
    protected static final int DATA_LENGTH_OFFSET = 6;
    protected static final int FOOTER_BIT = 4;
    protected static final int EXPERIMENTAL_BIT = 5;
    protected static final int EXTENDED_HEADER_BIT = 6;
    protected static final int COMPRESSION_BIT = 6;
    protected static final int UNSYNCHRONISATION_BIT = 7;
    protected static final int PADDING_LENGTH = 256;
    private static final String ITUNES_COMMENT_DESCRIPTION = "iTunNORM";
    protected boolean unsynchronisation;
    protected boolean extendedHeader;
    protected boolean experimental;
    protected boolean footer;
    protected boolean compression;
    protected boolean padding;
    protected String version;
    private int dataLength;
    private int extendedHeaderLength;
    private byte[] extendedHeaderData;
    private boolean obseleteFormat;
    private final Map<String, ID3v2FrameSet> frameSets;
    
    public AbstractID3v2Tag() {
        this.unsynchronisation = false;
        this.extendedHeader = false;
        this.experimental = false;
        this.footer = false;
        this.compression = false;
        this.padding = false;
        this.version = null;
        this.dataLength = 0;
        this.obseleteFormat = false;
        this.frameSets = new TreeMap<String, ID3v2FrameSet>();
    }
    
    public AbstractID3v2Tag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        this(array, false);
    }
    
    public AbstractID3v2Tag(final byte[] array, final boolean obseleteFormat) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        this.unsynchronisation = false;
        this.extendedHeader = false;
        this.experimental = false;
        this.footer = false;
        this.compression = false;
        this.padding = false;
        this.version = null;
        this.dataLength = 0;
        this.obseleteFormat = false;
        this.frameSets = new TreeMap<String, ID3v2FrameSet>();
        this.obseleteFormat = obseleteFormat;
        this.unpackTag(array);
    }
    
    private void unpackTag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        ID3v2TagFactory.sanityCheckTag(array);
        int n = this.unpackHeader(array);
        try {
            if (this.extendedHeader) {
                n = this.unpackExtendedHeader(array, n);
            }
            int dataLength = this.dataLength;
            if (this.footer) {
                dataLength -= 10;
            }
            this.unpackFrames(array, n, dataLength);
            if (this.footer) {
                this.unpackFooter(array, this.dataLength);
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new InvalidDataException("Premature end of tag", ex);
        }
    }
    
    private int unpackHeader(final byte[] array) throws UnsupportedTagException, InvalidDataException {
        final byte b = array[3];
        this.version = b + "." + array[4];
        if (b != 2 && b != 3 && b != 4) {
            throw new UnsupportedTagException("Unsupported version " + this.version);
        }
        this.unpackFlags(array);
        if ((array[5] & 0xF) != 0x0) {
            throw new UnsupportedTagException("Unrecognised bits in header");
        }
        this.dataLength = BufferTools.unpackSynchsafeInteger(array[6], array[7], array[8], array[9]);
        if (this.dataLength < 1) {
            throw new InvalidDataException("Zero size tag");
        }
        return 10;
    }
    
    protected abstract void unpackFlags(final byte[] p0);
    
    private int unpackExtendedHeader(final byte[] array, final int n) {
        this.extendedHeaderLength = BufferTools.unpackSynchsafeInteger(array[n], array[n + 1], array[n + 2], array[n + 3]) + 4;
        this.extendedHeaderData = BufferTools.copyBuffer(array, n + 4, this.extendedHeaderLength);
        return this.extendedHeaderLength;
    }
    
    protected int unpackFrames(final byte[] array, final int n, final int n2) {
        int i = n;
        while (i <= n2) {
            try {
                final ID3v2Frame frame = this.createFrame(array, i);
                this.addFrame(frame, false);
                i += frame.getLength();
                continue;
            }
            catch (InvalidDataException ex) {}
            break;
        }
        return i;
    }
    
    protected void addFrame(final ID3v2Frame id3v2Frame, final boolean b) {
        final ID3v2FrameSet set = this.frameSets.get(id3v2Frame.getId());
        if (set == null) {
            final ID3v2FrameSet set2 = new ID3v2FrameSet(id3v2Frame.getId());
            set2.addFrame(id3v2Frame);
            this.frameSets.put(id3v2Frame.getId(), set2);
        }
        else if (b) {
            set.clear();
            set.addFrame(id3v2Frame);
        }
        else {
            set.addFrame(id3v2Frame);
        }
    }
    
    protected ID3v2Frame createFrame(final byte[] array, final int n) throws InvalidDataException {
        if (this.obseleteFormat) {
            return new ID3v2ObseleteFrame(array, n);
        }
        return new ID3v2Frame(array, n);
    }
    
    protected ID3v2Frame createFrame(final String s, final byte[] array) {
        if (this.obseleteFormat) {
            return new ID3v2ObseleteFrame(s, array);
        }
        return new ID3v2Frame(s, array);
    }
    
    private int unpackFooter(final byte[] array, final int n) throws InvalidDataException {
        if (!"3DI".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n, "3DI".length()))) {
            throw new InvalidDataException("Invalid footer");
        }
        return 10;
    }
    
    @Override
    public byte[] toBytes() throws NotSupportedException {
        final byte[] array = new byte[this.getLength()];
        this.packTag(array);
        return array;
    }
    
    public void packTag(final byte[] array) throws NotSupportedException {
        int n = this.packHeader(array, 0);
        if (this.extendedHeader) {
            n = this.packExtendedHeader(array, n);
        }
        this.packFrames(array, n);
        if (this.footer) {
            this.packFooter(array, this.dataLength);
        }
    }
    
    private int packHeader(final byte[] array, final int n) {
        try {
            BufferTools.stringIntoByteBuffer("ID3", 0, "ID3".length(), array, n);
        }
        catch (UnsupportedEncodingException ex) {}
        final String[] split = this.version.split("\\.");
        if (split.length > 0) {
            array[n + 3] = Byte.parseByte(split[0]);
        }
        if (split.length > 1) {
            array[n + 4] = Byte.parseByte(split[1]);
        }
        this.packFlags(array, n);
        BufferTools.packSynchsafeInteger(this.getDataLength(), array, n + 6);
        return n + 10;
    }
    
    protected abstract void packFlags(final byte[] p0, final int p1);
    
    private int packExtendedHeader(final byte[] array, final int n) {
        BufferTools.packSynchsafeInteger(this.extendedHeaderLength, array, n);
        BufferTools.copyIntoByteBuffer(this.extendedHeaderData, 0, this.extendedHeaderData.length, array, n + 4);
        return n + 4 + this.extendedHeaderData.length;
    }
    
    public int packFrames(final byte[] array, final int n) throws NotSupportedException {
        return this.packSpecifiedFrames(array, this.packSpecifiedFrames(array, n, null, "APIC"), "APIC", null);
    }
    
    private int packSpecifiedFrames(final byte[] array, int n, final String s, final String s2) throws NotSupportedException {
        for (final ID3v2FrameSet set : this.frameSets.values()) {
            if ((s == null || s.equals(set.getId())) && (s2 == null || !s2.equals(set.getId()))) {
                for (final ID3v2Frame id3v2Frame : set.getFrames()) {
                    if (id3v2Frame.getDataLength() > 0) {
                        final byte[] bytes = id3v2Frame.toBytes();
                        BufferTools.copyIntoByteBuffer(bytes, 0, bytes.length, array, n);
                        n += bytes.length;
                    }
                }
            }
        }
        return n;
    }
    
    private int packFooter(final byte[] array, final int n) {
        try {
            BufferTools.stringIntoByteBuffer("3DI", 0, "3DI".length(), array, n);
        }
        catch (UnsupportedEncodingException ex) {}
        final String[] split = this.version.split("\\.");
        if (split.length > 0) {
            array[n + 3] = Byte.parseByte(split[0]);
        }
        if (split.length > 1) {
            array[n + 4] = Byte.parseByte(split[1]);
        }
        this.packFlags(array, n);
        BufferTools.packSynchsafeInteger(this.getDataLength(), array, n + 6);
        return n + 10;
    }
    
    private int calculateDataLength() {
        int n = 0;
        if (this.extendedHeader) {
            n += this.extendedHeaderLength;
        }
        if (this.footer) {
            n += 10;
        }
        else if (this.padding) {
            n += 256;
        }
        final Iterator<ID3v2FrameSet> iterator = this.frameSets.values().iterator();
        while (iterator.hasNext()) {
            final Iterator<ID3v2Frame> iterator2 = iterator.next().getFrames().iterator();
            while (iterator2.hasNext()) {
                n += iterator2.next().getLength();
            }
        }
        return n;
    }
    
    protected boolean useFrameUnsynchronisation() {
        return false;
    }
    
    @Override
    public String getVersion() {
        return this.version;
    }
    
    protected void invalidateDataLength() {
        this.dataLength = 0;
    }
    
    @Override
    public int getDataLength() {
        if (this.dataLength == 0) {
            this.dataLength = this.calculateDataLength();
        }
        return this.dataLength;
    }
    
    @Override
    public int getLength() {
        return this.getDataLength() + 10;
    }
    
    @Override
    public Map<String, ID3v2FrameSet> getFrameSets() {
        return this.frameSets;
    }
    
    @Override
    public boolean getPadding() {
        return this.padding;
    }
    
    @Override
    public void setPadding(final boolean padding) {
        if (this.padding != padding) {
            this.invalidateDataLength();
            this.padding = padding;
        }
    }
    
    @Override
    public boolean hasFooter() {
        return this.footer;
    }
    
    @Override
    public void setFooter(final boolean footer) {
        if (this.footer != footer) {
            this.invalidateDataLength();
            this.footer = footer;
        }
    }
    
    @Override
    public boolean hasUnsynchronisation() {
        return this.unsynchronisation;
    }
    
    @Override
    public void setUnsynchronisation(final boolean unsynchronisation) {
        if (this.unsynchronisation != unsynchronisation) {
            this.invalidateDataLength();
            this.unsynchronisation = unsynchronisation;
        }
    }
    
    @Override
    public boolean getObseleteFormat() {
        return this.obseleteFormat;
    }
    
    @Override
    public String getTrack() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TRK" : "TRCK");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setTrack(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TRCK", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getPartOfSet() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TPA" : "TPOS");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setPartOfSet(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TPOS", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public boolean isCompilation() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCP" : "TCMP");
        return textFrameData != null && textFrameData.getText() != null && "1".equals(textFrameData.getText().toString());
    }
    
    @Override
    public void setCompilation(final boolean b) {
        this.invalidateDataLength();
        this.addFrame(this.createFrame("TCMP", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(b ? "1" : "0")).toBytes()), true);
    }
    
    @Override
    public String getGrouping() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TT1" : "TIT1");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setGrouping(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TIT1", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getArtist() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TP1" : "TPE1");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setArtist(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TPE1", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getAlbumArtist() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TP2" : "TPE2");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setAlbumArtist(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TPE2", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getTitle() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TT2" : "TIT2");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setTitle(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TIT2", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getAlbum() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TAL" : "TALB");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setAlbum(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TALB", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getYear() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TYE" : "TYER");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setYear(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TYER", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getDate() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TDA" : "TDAT");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setDate(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TDAT", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    private int getGenre(final String s) {
        if (s != null && s.length() > 0) {
            try {
                return this.extractGenreNumber(s);
            }
            catch (NumberFormatException ex) {
                return ID3v1Genres.matchGenreDescription(this.extractGenreDescription(s));
            }
        }
        return -1;
    }
    
    @Override
    public int getGenre() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCO" : "TCON");
        if (textFrameData == null || textFrameData.getText() == null) {
            return -1;
        }
        return this.getGenre(textFrameData.getText().toString());
    }
    
    @Override
    public void setGenre(final int n) {
        if (n >= 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TCON", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText("(" + Integer.toString(n) + ")" + ((n < ID3v1Genres.GENRES.length) ? ID3v1Genres.GENRES[n] : ""))).toBytes()), true);
        }
    }
    
    @Override
    public int getBPM() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TBP" : "TBPM");
        if (textFrameData == null || textFrameData.getText() == null) {
            return -1;
        }
        final String string = textFrameData.getText().toString();
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return (int)Float.parseFloat(string.trim().replaceAll(",", "."));
        }
    }
    
    @Override
    public void setBPM(final int n) {
        if (n >= 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TBPM", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(Integer.toString(n))).toBytes()), true);
        }
    }
    
    @Override
    public String getKey() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TKE" : "TKEY");
        if (textFrameData == null || textFrameData.getText() == null) {
            return null;
        }
        return textFrameData.getText().toString();
    }
    
    @Override
    public void setKey(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TKEY", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getGenreDescription() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCO" : "TCON");
        if (textFrameData == null || textFrameData.getText() == null) {
            return null;
        }
        final String string = textFrameData.getText().toString();
        if (string != null) {
            final int genre = this.getGenre(string);
            if (genre >= 0 && genre < ID3v1Genres.GENRES.length) {
                return ID3v1Genres.GENRES[genre];
            }
            final String genreDescription = this.extractGenreDescription(string);
            if (genreDescription != null && genreDescription.length() > 0) {
                return genreDescription;
            }
        }
        return null;
    }
    
    @Override
    public void setGenreDescription(final String s) throws IllegalArgumentException {
        final int matchGenreDescription = ID3v1Genres.matchGenreDescription(s);
        if (matchGenreDescription < 0) {
            throw new IllegalArgumentException("Unknown genre: " + s);
        }
        this.setGenre(matchGenreDescription);
    }
    
    protected int extractGenreNumber(final String s) throws NumberFormatException {
        final String trim = s.trim();
        if (trim.length() > 0 && trim.charAt(0) == '(') {
            final int index = trim.indexOf(41);
            if (index > 0) {
                return Integer.parseInt(trim.substring(1, index));
            }
        }
        return Integer.parseInt(trim);
    }
    
    protected String extractGenreDescription(final String s) throws NumberFormatException {
        final String trim = s.trim();
        if (trim.length() > 0) {
            if (trim.charAt(0) == '(') {
                final int index = trim.indexOf(41);
                if (index > 0) {
                    return trim.substring(index + 1);
                }
            }
            return trim;
        }
        return null;
    }
    
    @Override
    public String getComment() {
        final ID3v2CommentFrameData commentFrameData = this.extractCommentFrameData(this.obseleteFormat ? "COM" : "COMM", false);
        if (commentFrameData != null && commentFrameData.getComment() != null) {
            return commentFrameData.getComment().toString();
        }
        return null;
    }
    
    @Override
    public void setComment(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("COMM", new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", null, new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getItunesComment() {
        final ID3v2CommentFrameData commentFrameData = this.extractCommentFrameData(this.obseleteFormat ? "COM" : "COMM", true);
        if (commentFrameData != null && commentFrameData.getComment() != null) {
            return commentFrameData.getComment().toString();
        }
        return null;
    }
    
    @Override
    public void setItunesComment(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("COMM", new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", new EncodedText("iTunNORM"), new EncodedText(s)).toBytes()), true);
        }
    }
    
    protected ID3v2CommentFrameData extractLyricsFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            for (final ID3v2Frame id3v2Frame : set.getFrames()) {
                try {
                    return new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
                }
                catch (InvalidDataException ex) {
                    continue;
                }
                break;
            }
        }
        return null;
    }
    
    @Override
    public String getLyrics() {
        if (this.obseleteFormat) {
            return null;
        }
        final ID3v2CommentFrameData lyricsFrameData = this.extractLyricsFrameData("USLT");
        if (lyricsFrameData != null) {
            return lyricsFrameData.getComment().toString();
        }
        return null;
    }
    
    @Override
    public void setLyrics(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("USLT", new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), "eng", null, new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getComposer() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCM" : "TCOM");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setComposer(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TCOM", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getPublisher() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TBP" : "TPUB");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setPublisher(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TPUB", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getOriginalArtist() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TOA" : "TOPE");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setOriginalArtist(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TOPE", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getCopyright() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TCR" : "TCOP");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setCopyright(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TCOP", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public String getArtistUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WOAR");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setArtistUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WOAR", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getCommercialUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WCOM");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setCommercialUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WCOM", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getCopyrightUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WCOP");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setCopyrightUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WCOP", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getAudiofileUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WOAF");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setAudiofileUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WOAF", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getAudioSourceUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WOAS");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setAudioSourceUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WOAS", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getRadiostationUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WORS");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setRadiostationUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WORS", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getPaymentUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WPAY");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setPaymentUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WPAY", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getPublisherUrl() {
        final ID3v2WWWFrameData wwwFrameData = this.extractWWWFrameData("WPUB");
        if (wwwFrameData != null) {
            return wwwFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setPublisherUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WPUB", new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), s).toBytes()), true);
        }
    }
    
    @Override
    public String getUrl() {
        final ID3v2UrlFrameData urlFrameData = this.extractUrlFrameData(this.obseleteFormat ? "WXX" : "WXXX");
        if (urlFrameData != null) {
            return urlFrameData.getUrl();
        }
        return null;
    }
    
    @Override
    public void setUrl(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("WXXX", new ID3v2UrlFrameData(this.useFrameUnsynchronisation(), null, s).toBytes()), true);
        }
    }
    
    @Override
    public ArrayList<ID3v2ChapterFrameData> getChapters() {
        if (this.obseleteFormat) {
            return null;
        }
        return this.extractChapterFrameData("CHAP");
    }
    
    @Override
    public void setChapters(final ArrayList<ID3v2ChapterFrameData> list) {
        if (list != null) {
            this.invalidateDataLength();
            int n = 1;
            for (final ID3v2ChapterFrameData id3v2ChapterFrameData : list) {
                if (n != 0) {
                    n = 0;
                    this.addFrame(this.createFrame("CHAP", id3v2ChapterFrameData.toBytes()), true);
                }
                else {
                    this.addFrame(this.createFrame("CHAP", id3v2ChapterFrameData.toBytes()), false);
                }
            }
        }
    }
    
    @Override
    public ArrayList<ID3v2ChapterTOCFrameData> getChapterTOC() {
        if (this.obseleteFormat) {
            return null;
        }
        return this.extractChapterTOCFrameData("CTOC");
    }
    
    @Override
    public void setChapterTOC(final ArrayList<ID3v2ChapterTOCFrameData> list) {
        if (list != null) {
            this.invalidateDataLength();
            int n = 1;
            for (final ID3v2ChapterTOCFrameData id3v2ChapterTOCFrameData : list) {
                if (n != 0) {
                    n = 0;
                    this.addFrame(this.createFrame("CTOC", id3v2ChapterTOCFrameData.toBytes()), true);
                }
                else {
                    this.addFrame(this.createFrame("CTOC", id3v2ChapterTOCFrameData.toBytes()), false);
                }
            }
        }
    }
    
    @Override
    public String getEncoder() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData(this.obseleteFormat ? "TEN" : "TENC");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    @Override
    public void setEncoder(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TENC", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
    
    @Override
    public byte[] getAlbumImage() {
        final ID3v2PictureFrameData pictureFrameData = this.createPictureFrameData(this.obseleteFormat ? "PIC" : "APIC");
        if (pictureFrameData != null) {
            return pictureFrameData.getImageData();
        }
        return null;
    }
    
    @Override
    public void setAlbumImage(final byte[] array, final String s) {
        this.setAlbumImage(array, s, (byte)0, null);
    }
    
    @Override
    public void setAlbumImage(final byte[] array, final String s, final byte b, final String s2) {
        if (array != null && array.length > 0 && s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("APIC", new ID3v2PictureFrameData(this.useFrameUnsynchronisation(), s, b, (null == s2) ? null : new EncodedText(s2), array).toBytes()), true);
        }
    }
    
    @Override
    public void clearAlbumImage() {
        this.clearFrameSet(this.obseleteFormat ? "PIC" : "APIC");
    }
    
    @Override
    public String getAlbumImageMimeType() {
        final ID3v2PictureFrameData pictureFrameData = this.createPictureFrameData(this.obseleteFormat ? "PIC" : "APIC");
        if (pictureFrameData != null && pictureFrameData.getMimeType() != null) {
            return pictureFrameData.getMimeType();
        }
        return null;
    }
    
    @Override
    public void clearFrameSet(final String s) {
        if (this.frameSets.remove(s) != null) {
            this.invalidateDataLength();
        }
    }
    
    @Override
    public int getWmpRating() {
        final ID3v2PopmFrameData popmFrameData = this.extractPopmFrameData("POPM");
        if (popmFrameData != null && popmFrameData.getAddress() != null) {
            return popmFrameData.getRating();
        }
        return -1;
    }
    
    @Override
    public void setWmpRating(final int n) {
        if (n >= 0 && n < 6) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("POPM", new ID3v2PopmFrameData(this.useFrameUnsynchronisation(), n).toBytes()), true);
        }
    }
    
    private ArrayList<ID3v2ChapterFrameData> extractChapterFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ArrayList<ID3v2ChapterFrameData> list = new ArrayList<ID3v2ChapterFrameData>();
            for (final ID3v2Frame id3v2Frame : set.getFrames()) {
                try {
                    list.add(new ID3v2ChapterFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData()));
                }
                catch (InvalidDataException ex) {}
            }
            return list;
        }
        return null;
    }
    
    private ArrayList<ID3v2ChapterTOCFrameData> extractChapterTOCFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ArrayList<ID3v2ChapterTOCFrameData> list = new ArrayList<ID3v2ChapterTOCFrameData>();
            for (final ID3v2Frame id3v2Frame : set.getFrames()) {
                try {
                    list.add(new ID3v2ChapterTOCFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData()));
                }
                catch (InvalidDataException ex) {}
            }
            return list;
        }
        return null;
    }
    
    protected ID3v2TextFrameData extractTextFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ID3v2Frame id3v2Frame = set.getFrames().get(0);
            try {
                return new ID3v2TextFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
            }
            catch (InvalidDataException ex) {}
        }
        return null;
    }
    
    private ID3v2WWWFrameData extractWWWFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ID3v2Frame id3v2Frame = set.getFrames().get(0);
            try {
                return new ID3v2WWWFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
            }
            catch (InvalidDataException ex) {}
        }
        return null;
    }
    
    private ID3v2UrlFrameData extractUrlFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ID3v2Frame id3v2Frame = set.getFrames().get(0);
            try {
                return new ID3v2UrlFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
            }
            catch (InvalidDataException ex) {}
        }
        return null;
    }
    
    private ID3v2CommentFrameData extractCommentFrameData(final String s, final boolean b) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            for (final ID3v2Frame id3v2Frame : set.getFrames()) {
                try {
                    final ID3v2CommentFrameData id3v2CommentFrameData = new ID3v2CommentFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
                    if (b && "iTunNORM".equals(id3v2CommentFrameData.getDescription().toString())) {
                        return id3v2CommentFrameData;
                    }
                    if (!b) {
                        return id3v2CommentFrameData;
                    }
                    continue;
                }
                catch (InvalidDataException ex) {}
            }
        }
        return null;
    }
    
    private ID3v2PictureFrameData createPictureFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ID3v2Frame id3v2Frame = set.getFrames().get(0);
            try {
                ID3v2PictureFrameData id3v2PictureFrameData;
                if (this.obseleteFormat) {
                    id3v2PictureFrameData = new ID3v2ObseletePictureFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
                }
                else {
                    id3v2PictureFrameData = new ID3v2PictureFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
                }
                return id3v2PictureFrameData;
            }
            catch (InvalidDataException ex) {}
        }
        return null;
    }
    
    private ID3v2PopmFrameData extractPopmFrameData(final String s) {
        final ID3v2FrameSet set = this.frameSets.get(s);
        if (set != null) {
            final ID3v2Frame id3v2Frame = set.getFrames().get(0);
            try {
                return new ID3v2PopmFrameData(this.useFrameUnsynchronisation(), id3v2Frame.getData());
            }
            catch (InvalidDataException ex) {}
        }
        return null;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AbstractID3v2Tag)) {
            return false;
        }
        if (super.equals(o)) {
            return true;
        }
        final AbstractID3v2Tag abstractID3v2Tag = (AbstractID3v2Tag)o;
        if (this.unsynchronisation != abstractID3v2Tag.unsynchronisation) {
            return false;
        }
        if (this.extendedHeader != abstractID3v2Tag.extendedHeader) {
            return false;
        }
        if (this.experimental != abstractID3v2Tag.experimental) {
            return false;
        }
        if (this.footer != abstractID3v2Tag.footer) {
            return false;
        }
        if (this.compression != abstractID3v2Tag.compression) {
            return false;
        }
        if (this.dataLength != abstractID3v2Tag.dataLength) {
            return false;
        }
        if (this.extendedHeaderLength != abstractID3v2Tag.extendedHeaderLength) {
            return false;
        }
        if (this.version == null) {
            if (abstractID3v2Tag.version != null) {
                return false;
            }
        }
        else {
            if (abstractID3v2Tag.version == null) {
                return false;
            }
            if (!this.version.equals(abstractID3v2Tag.version)) {
                return false;
            }
        }
        if (this.frameSets == null) {
            if (abstractID3v2Tag.frameSets != null) {
                return false;
            }
        }
        else {
            if (abstractID3v2Tag.frameSets == null) {
                return false;
            }
            if (!this.frameSets.equals(abstractID3v2Tag.frameSets)) {
                return false;
            }
        }
        return true;
    }
}
