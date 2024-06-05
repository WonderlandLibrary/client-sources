package com.mpatric.mp3agic;

import java.io.*;

public class ID3v2ObseletePictureFrameData extends ID3v2PictureFrameData
{
    public ID3v2ObseletePictureFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2ObseletePictureFrameData(final boolean b, final String s, final byte b2, final EncodedText encodedText, final byte[] array) {
        super(b, s, b2, encodedText, array);
    }
    
    public ID3v2ObseletePictureFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b, array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        String byteBufferToString;
        try {
            byteBufferToString = BufferTools.byteBufferToString(array, 1, 3);
        }
        catch (UnsupportedEncodingException ex) {
            byteBufferToString = "unknown";
        }
        this.mimeType = "image/" + byteBufferToString.toLowerCase();
        this.pictureType = array[4];
        final int indexOfTerminatorForEncoding = BufferTools.indexOfTerminatorForEncoding(array, 5, array[0]);
        int n;
        if (indexOfTerminatorForEncoding >= 0) {
            this.description = new EncodedText(array[0], BufferTools.copyBuffer(array, 5, indexOfTerminatorForEncoding - 5));
            n = indexOfTerminatorForEncoding + this.description.getTerminator().length;
        }
        else {
            this.description = new EncodedText(array[0], "");
            n = 1;
        }
        this.imageData = BufferTools.copyBuffer(array, n, array.length - n);
    }
}
