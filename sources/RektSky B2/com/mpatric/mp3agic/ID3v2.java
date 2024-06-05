package com.mpatric.mp3agic;

import java.util.*;

public interface ID3v2 extends ID3v1
{
    boolean getPadding();
    
    void setPadding(final boolean p0);
    
    boolean hasFooter();
    
    void setFooter(final boolean p0);
    
    boolean hasUnsynchronisation();
    
    void setUnsynchronisation(final boolean p0);
    
    int getBPM();
    
    void setBPM(final int p0);
    
    String getGrouping();
    
    void setGrouping(final String p0);
    
    String getKey();
    
    void setKey(final String p0);
    
    String getDate();
    
    void setDate(final String p0);
    
    String getComposer();
    
    void setComposer(final String p0);
    
    String getPublisher();
    
    void setPublisher(final String p0);
    
    String getOriginalArtist();
    
    void setOriginalArtist(final String p0);
    
    String getAlbumArtist();
    
    void setAlbumArtist(final String p0);
    
    String getCopyright();
    
    void setCopyright(final String p0);
    
    String getArtistUrl();
    
    void setArtistUrl(final String p0);
    
    String getCommercialUrl();
    
    void setCommercialUrl(final String p0);
    
    String getCopyrightUrl();
    
    void setCopyrightUrl(final String p0);
    
    String getAudiofileUrl();
    
    void setAudiofileUrl(final String p0);
    
    String getAudioSourceUrl();
    
    void setAudioSourceUrl(final String p0);
    
    String getRadiostationUrl();
    
    void setRadiostationUrl(final String p0);
    
    String getPaymentUrl();
    
    void setPaymentUrl(final String p0);
    
    String getPublisherUrl();
    
    void setPublisherUrl(final String p0);
    
    String getUrl();
    
    void setUrl(final String p0);
    
    String getPartOfSet();
    
    void setPartOfSet(final String p0);
    
    boolean isCompilation();
    
    void setCompilation(final boolean p0);
    
    ArrayList<ID3v2ChapterFrameData> getChapters();
    
    void setChapters(final ArrayList<ID3v2ChapterFrameData> p0);
    
    ArrayList<ID3v2ChapterTOCFrameData> getChapterTOC();
    
    void setChapterTOC(final ArrayList<ID3v2ChapterTOCFrameData> p0);
    
    String getEncoder();
    
    void setEncoder(final String p0);
    
    byte[] getAlbumImage();
    
    void setAlbumImage(final byte[] p0, final String p1);
    
    void setAlbumImage(final byte[] p0, final String p1, final byte p2, final String p3);
    
    void clearAlbumImage();
    
    String getAlbumImageMimeType();
    
    int getWmpRating();
    
    void setWmpRating(final int p0);
    
    String getItunesComment();
    
    void setItunesComment(final String p0);
    
    String getLyrics();
    
    void setLyrics(final String p0);
    
    void setGenreDescription(final String p0);
    
    int getDataLength();
    
    int getLength();
    
    boolean getObseleteFormat();
    
    Map<String, ID3v2FrameSet> getFrameSets();
    
    void clearFrameSet(final String p0);
}
