package com.mpatric.mp3agic;

import java.io.*;

public class ID3v2UrlFrameData extends AbstractID3v2FrameData
{
    protected String url;
    protected EncodedText description;
    
    public ID3v2UrlFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2UrlFrameData(final boolean b, final EncodedText description, final String url) {
        super(b);
        this.description = description;
        this.url = url;
    }
    
    public ID3v2UrlFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        final int indexOfTerminatorForEncoding = BufferTools.indexOfTerminatorForEncoding(array, 1, array[0]);
        int n;
        if (indexOfTerminatorForEncoding >= 0) {
            this.description = new EncodedText(array[0], BufferTools.copyBuffer(array, 1, indexOfTerminatorForEncoding - 1));
            n = indexOfTerminatorForEncoding + this.description.getTerminator().length;
        }
        else {
            this.description = new EncodedText(array[0], "");
            n = 1;
        }
        try {
            this.url = BufferTools.byteBufferToString(array, n, array.length - n);
        }
        catch (UnsupportedEncodingException ex) {
            this.url = "";
        }
    }
    
    @Override
    protected byte[] packFrameData() {
        final byte[] array = new byte[this.getLength()];
        if (this.description != null) {
            array[0] = this.description.getTextEncoding();
        }
        else {
            array[0] = 0;
        }
        int n = 1;
        if (this.description != null) {
            final byte[] bytes = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(bytes, 0, bytes.length, array, n);
            n += bytes.length;
        }
        else {
            array[n++] = 0;
        }
        if (this.url != null && this.url.length() > 0) {
            try {
                BufferTools.stringIntoByteBuffer(this.url, 0, this.url.length(), array, n);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return array;
    }
    
    @Override
    protected int getLength() {
        int n = 1;
        if (this.description != null) {
            n += this.description.toBytes(true, true).length;
        }
        else {
            ++n;
        }
        if (this.url != null) {
            n += this.url.length();
        }
        return n;
    }
    
    public EncodedText getDescription() {
        return this.description;
    }
    
    public void setDescription(final EncodedText description) {
        this.description = description;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * super.hashCode() + ((this.description == null) ? 0 : this.description.hashCode())) + ((this.url == null) ? 0 : this.url.hashCode());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ID3v2UrlFrameData id3v2UrlFrameData = (ID3v2UrlFrameData)o;
        if (this.description == null) {
            if (id3v2UrlFrameData.description != null) {
                return false;
            }
        }
        else if (!this.description.equals(id3v2UrlFrameData.description)) {
            return false;
        }
        if (this.url == null) {
            if (id3v2UrlFrameData.url != null) {
                return false;
            }
        }
        else if (!this.url.equals(id3v2UrlFrameData.url)) {
            return false;
        }
        return true;
    }
}
