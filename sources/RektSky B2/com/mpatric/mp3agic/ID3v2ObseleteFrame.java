package com.mpatric.mp3agic;

public class ID3v2ObseleteFrame extends ID3v2Frame
{
    private static final int HEADER_LENGTH = 6;
    private static final int ID_OFFSET = 0;
    private static final int ID_LENGTH = 3;
    protected static final int DATA_LENGTH_OFFSET = 3;
    
    public ID3v2ObseleteFrame(final byte[] array, final int n) throws InvalidDataException {
        super(array, n);
    }
    
    public ID3v2ObseleteFrame(final String s, final byte[] array) {
        super(s, array);
    }
    
    @Override
    protected int unpackHeader(final byte[] array, final int n) {
        this.id = BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 0, 3);
        this.unpackDataLength(array, n);
        return n + 6;
    }
    
    @Override
    protected void unpackDataLength(final byte[] array, final int n) {
        this.dataLength = BufferTools.unpackInteger((byte)0, array[n + 3], array[n + 3 + 1], array[n + 3 + 2]);
    }
    
    @Override
    public void packFrame(final byte[] array, final int n) throws NotSupportedException {
        throw new NotSupportedException("Packing Obselete frames is not supported");
    }
    
    @Override
    public int getLength() {
        return this.dataLength + 6;
    }
}
