package com.mpatric.mp3agic;

import java.io.*;

public class ID3v2WWWFrameData extends AbstractID3v2FrameData
{
    protected String url;
    
    public ID3v2WWWFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2WWWFrameData(final boolean b, final String url) {
        super(b);
        this.url = url;
    }
    
    public ID3v2WWWFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        try {
            this.url = BufferTools.byteBufferToString(array, 0, array.length);
        }
        catch (UnsupportedEncodingException ex) {
            this.url = "";
        }
    }
    
    @Override
    protected byte[] packFrameData() {
        final byte[] array = new byte[this.getLength()];
        if (this.url != null && this.url.length() > 0) {
            try {
                BufferTools.stringIntoByteBuffer(this.url, 0, this.url.length(), array, 0);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return array;
    }
    
    @Override
    protected int getLength() {
        int length = 0;
        if (this.url != null) {
            length = this.url.length();
        }
        return length;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
