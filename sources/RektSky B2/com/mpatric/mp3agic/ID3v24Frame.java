package com.mpatric.mp3agic;

public class ID3v24Frame extends ID3v2Frame
{
    public ID3v24Frame(final byte[] array, final int n) throws InvalidDataException {
        super(array, n);
    }
    
    public ID3v24Frame(final String s, final byte[] array) {
        super(s, array);
    }
    
    @Override
    protected void unpackDataLength(final byte[] array, final int n) {
        this.dataLength = BufferTools.unpackSynchsafeInteger(array[n + 4], array[n + 4 + 1], array[n + 4 + 2], array[n + 4 + 3]);
    }
    
    @Override
    protected byte[] packDataLength() {
        return BufferTools.packSynchsafeInteger(this.dataLength);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ID3v24Frame && super.equals(o);
    }
}
