package com.mpatric.mp3agic;

public interface ID3v1
{
    String getVersion();
    
    String getTrack();
    
    void setTrack(final String p0);
    
    String getArtist();
    
    void setArtist(final String p0);
    
    String getTitle();
    
    void setTitle(final String p0);
    
    String getAlbum();
    
    void setAlbum(final String p0);
    
    String getYear();
    
    void setYear(final String p0);
    
    int getGenre();
    
    void setGenre(final int p0);
    
    String getGenreDescription();
    
    String getComment();
    
    void setComment(final String p0);
    
    byte[] toBytes() throws NotSupportedException;
}
