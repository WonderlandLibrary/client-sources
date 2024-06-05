package com.mpatric.mp3agic;

import java.util.*;
import java.io.*;

public class ID3v1Tag implements ID3v1
{
    public static final int TAG_LENGTH = 128;
    private static final String VERSION_0 = "0";
    private static final String VERSION_1 = "1";
    private static final String TAG = "TAG";
    private static final int TITLE_OFFSET = 3;
    private static final int TITLE_LENGTH = 30;
    private static final int ARTIST_OFFSET = 33;
    private static final int ARTIST_LENGTH = 30;
    private static final int ALBUM_OFFSET = 63;
    private static final int ALBUM_LENGTH = 30;
    private static final int YEAR_OFFSET = 93;
    private static final int YEAR_LENGTH = 4;
    private static final int COMMENT_OFFSET = 97;
    private static final int COMMENT_LENGTH_V1_0 = 30;
    private static final int COMMENT_LENGTH_V1_1 = 28;
    private static final int TRACK_MARKER_OFFSET = 125;
    private static final int TRACK_OFFSET = 126;
    private static final int GENRE_OFFSET = 127;
    private String track;
    private String artist;
    private String title;
    private String album;
    private String year;
    private int genre;
    private String comment;
    
    public ID3v1Tag() {
        this.track = null;
        this.artist = null;
        this.title = null;
        this.album = null;
        this.year = null;
        this.genre = -1;
        this.comment = null;
    }
    
    public ID3v1Tag(final byte[] array) throws NoSuchTagException {
        this.track = null;
        this.artist = null;
        this.title = null;
        this.album = null;
        this.year = null;
        this.genre = -1;
        this.comment = null;
        this.unpackTag(array);
    }
    
    private void unpackTag(final byte[] array) throws NoSuchTagException {
        this.sanityCheckTag(array);
        this.title = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 3, 30));
        this.artist = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 33, 30));
        this.album = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 63, 30));
        this.year = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 93, 4));
        this.genre = (array[127] & 0xFF);
        if (this.genre == 255) {
            this.genre = -1;
        }
        if (array[125] != 0) {
            this.comment = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 97, 30));
            this.track = null;
        }
        else {
            this.comment = BufferTools.trimStringRight(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 97, 28));
            final byte b = array[126];
            if (b == 0) {
                this.track = "";
            }
            else {
                this.track = Integer.toString(b);
            }
        }
    }
    
    private void sanityCheckTag(final byte[] array) throws NoSuchTagException {
        if (array.length != 128) {
            throw new NoSuchTagException("Buffer length wrong");
        }
        if (!"TAG".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 0, "TAG".length()))) {
            throw new NoSuchTagException();
        }
    }
    
    @Override
    public byte[] toBytes() {
        final byte[] array = new byte[128];
        this.packTag(array);
        return array;
    }
    
    public void toBytes(final byte[] array) {
        this.packTag(array);
    }
    
    public void packTag(final byte[] array) {
        Arrays.fill(array, (byte)0);
        try {
            BufferTools.stringIntoByteBuffer("TAG", 0, 3, array, 0);
        }
        catch (UnsupportedEncodingException ex) {}
        this.packField(array, this.title, 30, 3);
        this.packField(array, this.artist, 30, 33);
        this.packField(array, this.album, 30, 63);
        this.packField(array, this.year, 4, 93);
        if (this.genre < 128) {
            array[127] = (byte)this.genre;
        }
        else {
            array[127] = (byte)(this.genre - 256);
        }
        if (this.track == null) {
            this.packField(array, this.comment, 30, 97);
        }
        else {
            this.packField(array, this.comment, 28, 97);
            final String numericsOnly = this.numericsOnly(this.track);
            if (numericsOnly.length() > 0) {
                final int int1 = Integer.parseInt(numericsOnly);
                if (int1 < 128) {
                    array[126] = (byte)int1;
                }
                else {
                    array[126] = (byte)(int1 - 256);
                }
            }
        }
    }
    
    private void packField(final byte[] array, final String s, final int n, final int n2) {
        if (s != null) {
            try {
                BufferTools.stringIntoByteBuffer(s, 0, Math.min(s.length(), n), array, n2);
            }
            catch (UnsupportedEncodingException ex) {}
        }
    }
    
    private String numericsOnly(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '0' || char1 > '9') {
                break;
            }
            sb.append(char1);
        }
        return sb.toString();
    }
    
    @Override
    public String getVersion() {
        if (this.track == null) {
            return "0";
        }
        return "1";
    }
    
    @Override
    public String getTrack() {
        return this.track;
    }
    
    @Override
    public void setTrack(final String track) {
        this.track = track;
    }
    
    @Override
    public String getArtist() {
        return this.artist;
    }
    
    @Override
    public void setArtist(final String artist) {
        this.artist = artist;
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }
    
    @Override
    public String getAlbum() {
        return this.album;
    }
    
    @Override
    public void setAlbum(final String album) {
        this.album = album;
    }
    
    @Override
    public String getYear() {
        return this.year;
    }
    
    @Override
    public void setYear(final String year) {
        this.year = year;
    }
    
    @Override
    public int getGenre() {
        return this.genre;
    }
    
    @Override
    public void setGenre(final int genre) {
        this.genre = genre;
    }
    
    @Override
    public String getGenreDescription() {
        try {
            return ID3v1Genres.GENRES[this.genre];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return "Unknown";
        }
    }
    
    @Override
    public String getComment() {
        return this.comment;
    }
    
    @Override
    public void setComment(final String comment) {
        this.comment = comment;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * 1 + ((this.album == null) ? 0 : this.album.hashCode())) + ((this.artist == null) ? 0 : this.artist.hashCode())) + ((this.comment == null) ? 0 : this.comment.hashCode())) + this.genre) + ((this.title == null) ? 0 : this.title.hashCode())) + ((this.track == null) ? 0 : this.track.hashCode())) + ((this.year == null) ? 0 : this.year.hashCode());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ID3v1Tag id3v1Tag = (ID3v1Tag)o;
        if (this.album == null) {
            if (id3v1Tag.album != null) {
                return false;
            }
        }
        else if (!this.album.equals(id3v1Tag.album)) {
            return false;
        }
        if (this.artist == null) {
            if (id3v1Tag.artist != null) {
                return false;
            }
        }
        else if (!this.artist.equals(id3v1Tag.artist)) {
            return false;
        }
        if (this.comment == null) {
            if (id3v1Tag.comment != null) {
                return false;
            }
        }
        else if (!this.comment.equals(id3v1Tag.comment)) {
            return false;
        }
        if (this.genre != id3v1Tag.genre) {
            return false;
        }
        if (this.title == null) {
            if (id3v1Tag.title != null) {
                return false;
            }
        }
        else if (!this.title.equals(id3v1Tag.title)) {
            return false;
        }
        if (this.track == null) {
            if (id3v1Tag.track != null) {
                return false;
            }
        }
        else if (!this.track.equals(id3v1Tag.track)) {
            return false;
        }
        if (this.year == null) {
            if (id3v1Tag.year != null) {
                return false;
            }
        }
        else if (!this.year.equals(id3v1Tag.year)) {
            return false;
        }
        return true;
    }
}
