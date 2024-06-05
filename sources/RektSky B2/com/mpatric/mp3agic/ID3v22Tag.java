package com.mpatric.mp3agic;

public class ID3v22Tag extends AbstractID3v2Tag
{
    public static final String VERSION = "2.0";
    
    public ID3v22Tag() {
        this.version = "2.0";
    }
    
    public ID3v22Tag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(array);
    }
    
    public ID3v22Tag(final byte[] array, final boolean b) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(array, b);
    }
    
    @Override
    protected void unpackFlags(final byte[] array) {
        this.unsynchronisation = BufferTools.checkBit(array[5], 7);
        this.compression = BufferTools.checkBit(array[5], 6);
    }
    
    @Override
    protected void packFlags(final byte[] array, final int n) {
        array[n + 5] = BufferTools.setBit(array[n + 5], 7, this.unsynchronisation);
        array[n + 5] = BufferTools.setBit(array[n + 5], 6, this.compression);
    }
}
