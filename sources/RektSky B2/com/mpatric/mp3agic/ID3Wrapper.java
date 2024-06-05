package com.mpatric.mp3agic;

public class ID3Wrapper
{
    private final ID3v1 id3v1Tag;
    private final ID3v2 id3v2Tag;
    
    public ID3Wrapper(final ID3v1 id3v1Tag, final ID3v2 id3v2Tag) {
        this.id3v1Tag = id3v1Tag;
        this.id3v2Tag = id3v2Tag;
    }
    
    public ID3v1 getId3v1Tag() {
        return this.id3v1Tag;
    }
    
    public ID3v2 getId3v2Tag() {
        return this.id3v2Tag;
    }
    
    public String getTrack() {
        if (this.id3v2Tag != null && this.id3v2Tag.getTrack() != null && this.id3v2Tag.getTrack().length() > 0) {
            return this.id3v2Tag.getTrack();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getTrack();
        }
        return null;
    }
    
    public void setTrack(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setTrack(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setTrack(s);
        }
    }
    
    public String getArtist() {
        if (this.id3v2Tag != null && this.id3v2Tag.getArtist() != null && this.id3v2Tag.getArtist().length() > 0) {
            return this.id3v2Tag.getArtist();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getArtist();
        }
        return null;
    }
    
    public void setArtist(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setArtist(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setArtist(s);
        }
    }
    
    public String getTitle() {
        if (this.id3v2Tag != null && this.id3v2Tag.getTitle() != null && this.id3v2Tag.getTitle().length() > 0) {
            return this.id3v2Tag.getTitle();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getTitle();
        }
        return null;
    }
    
    public void setTitle(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setTitle(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setTitle(s);
        }
    }
    
    public String getAlbum() {
        if (this.id3v2Tag != null && this.id3v2Tag.getAlbum() != null && this.id3v2Tag.getAlbum().length() > 0) {
            return this.id3v2Tag.getAlbum();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getAlbum();
        }
        return null;
    }
    
    public void setAlbum(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setAlbum(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setAlbum(s);
        }
    }
    
    public String getYear() {
        if (this.id3v2Tag != null && this.id3v2Tag.getYear() != null && this.id3v2Tag.getYear().length() > 0) {
            return this.id3v2Tag.getYear();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getYear();
        }
        return null;
    }
    
    public void setYear(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setYear(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setYear(s);
        }
    }
    
    public int getGenre() {
        if (this.id3v2Tag != null && this.id3v2Tag.getGenre() != -1) {
            return this.id3v2Tag.getGenre();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getGenre();
        }
        return -1;
    }
    
    public void setGenre(final int n) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setGenre(n);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setGenre(n);
        }
    }
    
    public String getGenreDescription() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getGenreDescription();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getGenreDescription();
        }
        return null;
    }
    
    public String getComment() {
        if (this.id3v2Tag != null && this.id3v2Tag.getComment() != null && this.id3v2Tag.getComment().length() > 0) {
            return this.id3v2Tag.getComment();
        }
        if (this.id3v1Tag != null) {
            return this.id3v1Tag.getComment();
        }
        return null;
    }
    
    public void setComment(final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setComment(s);
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setComment(s);
        }
    }
    
    public String getComposer() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getComposer();
        }
        return null;
    }
    
    public void setComposer(final String composer) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setComposer(composer);
        }
    }
    
    public String getOriginalArtist() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getOriginalArtist();
        }
        return null;
    }
    
    public void setOriginalArtist(final String originalArtist) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setOriginalArtist(originalArtist);
        }
    }
    
    public void setAlbumArtist(final String albumArtist) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setAlbumArtist(albumArtist);
        }
    }
    
    public String getAlbumArtist() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getAlbumArtist();
        }
        return null;
    }
    
    public String getCopyright() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getCopyright();
        }
        return null;
    }
    
    public void setCopyright(final String copyright) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setCopyright(copyright);
        }
    }
    
    public String getUrl() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getUrl();
        }
        return null;
    }
    
    public void setUrl(final String url) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setUrl(url);
        }
    }
    
    public String getEncoder() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getEncoder();
        }
        return null;
    }
    
    public void setEncoder(final String encoder) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setEncoder(encoder);
        }
    }
    
    public byte[] getAlbumImage() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getAlbumImage();
        }
        return null;
    }
    
    public void setAlbumImage(final byte[] array, final String s) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setAlbumImage(array, s);
        }
    }
    
    public String getAlbumImageMimeType() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getAlbumImageMimeType();
        }
        return null;
    }
    
    public void setLyrics(final String lyrics) {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.setLyrics(lyrics);
        }
    }
    
    public String getLyrics() {
        if (this.id3v2Tag != null) {
            return this.id3v2Tag.getLyrics();
        }
        return null;
    }
    
    public void clearComment() {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.clearFrameSet("COMM");
        }
        if (this.id3v1Tag != null) {
            this.id3v1Tag.setComment(null);
        }
    }
    
    public void clearCopyright() {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.clearFrameSet("TCOP");
        }
    }
    
    public void clearEncoder() {
        if (this.id3v2Tag != null) {
            this.id3v2Tag.clearFrameSet("TENC");
        }
    }
}
