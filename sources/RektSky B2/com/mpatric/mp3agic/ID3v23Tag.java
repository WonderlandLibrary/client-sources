package com.mpatric.mp3agic;

public class ID3v23Tag extends AbstractID3v2Tag
{
    public static final String VERSION = "3.0";
    
    public ID3v23Tag() {
        this.version = "3.0";
    }
    
    public ID3v23Tag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(array);
    }
    
    @Override
    protected void unpackFlags(final byte[] array) {
        this.unsynchronisation = BufferTools.checkBit(array[5], 7);
        this.extendedHeader = BufferTools.checkBit(array[5], 6);
        this.experimental = BufferTools.checkBit(array[5], 5);
    }
    
    @Override
    protected void packFlags(final byte[] array, final int n) {
        array[n + 5] = BufferTools.setBit(array[n + 5], 7, this.unsynchronisation);
        array[n + 5] = BufferTools.setBit(array[n + 5], 6, this.extendedHeader);
        array[n + 5] = BufferTools.setBit(array[n + 5], 5, this.experimental);
    }
}
