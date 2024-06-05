package com.mpatric.mp3agic;

import java.io.*;
import java.util.*;

public class ID3v2PictureFrameData extends AbstractID3v2FrameData
{
    protected String mimeType;
    protected byte pictureType;
    protected EncodedText description;
    protected byte[] imageData;
    
    public ID3v2PictureFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2PictureFrameData(final boolean b, final String mimeType, final byte pictureType, final EncodedText description, final byte[] imageData) {
        super(b);
        this.mimeType = mimeType;
        this.pictureType = pictureType;
        this.description = description;
        this.imageData = imageData;
    }
    
    public ID3v2PictureFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        int indexOfTerminator = BufferTools.indexOfTerminator(array, 1, 1);
        if (indexOfTerminator >= 0) {
            try {
                this.mimeType = BufferTools.byteBufferToString(array, 1, indexOfTerminator - 1);
            }
            catch (UnsupportedEncodingException ex) {
                this.mimeType = "image/unknown";
            }
        }
        else {
            this.mimeType = "image/unknown";
        }
        this.pictureType = array[indexOfTerminator + 1];
        indexOfTerminator += 2;
        final int indexOfTerminatorForEncoding = BufferTools.indexOfTerminatorForEncoding(array, indexOfTerminator, array[0]);
        int n;
        if (indexOfTerminatorForEncoding >= 0) {
            this.description = new EncodedText(array[0], BufferTools.copyBuffer(array, indexOfTerminator, indexOfTerminatorForEncoding - indexOfTerminator));
            n = indexOfTerminatorForEncoding + this.description.getTerminator().length;
        }
        else {
            this.description = new EncodedText(array[0], "");
            n = indexOfTerminator;
        }
        this.imageData = BufferTools.copyBuffer(array, n, array.length - n);
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
        int length = 0;
        if (this.mimeType != null && this.mimeType.length() > 0) {
            length = this.mimeType.length();
            try {
                BufferTools.stringIntoByteBuffer(this.mimeType, 0, length, array, 1);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        int n = length + 1;
        array[n++] = 0;
        array[n++] = this.pictureType;
        if (this.description != null && this.description.toBytes().length > 0) {
            final byte[] bytes = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(bytes, 0, bytes.length, array, n);
            n += bytes.length;
        }
        else {
            array[n++] = 0;
        }
        if (this.imageData != null && this.imageData.length > 0) {
            BufferTools.copyIntoByteBuffer(this.imageData, 0, this.imageData.length, array, n);
        }
        return array;
    }
    
    @Override
    protected int getLength() {
        int n = 3;
        if (this.mimeType != null) {
            n += this.mimeType.length();
        }
        if (this.description != null) {
            n += this.description.toBytes(true, true).length;
        }
        else {
            ++n;
        }
        if (this.imageData != null) {
            n += this.imageData.length;
        }
        return n;
    }
    
    public String getMimeType() {
        return this.mimeType;
    }
    
    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }
    
    public byte getPictureType() {
        return this.pictureType;
    }
    
    public void setPictureType(final byte pictureType) {
        this.pictureType = pictureType;
    }
    
    public EncodedText getDescription() {
        return this.description;
    }
    
    public void setDescription(final EncodedText description) {
        this.description = description;
    }
    
    public byte[] getImageData() {
        return this.imageData;
    }
    
    public void setImageData(final byte[] imageData) {
        this.imageData = imageData;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * super.hashCode() + ((this.description == null) ? 0 : this.description.hashCode())) + Arrays.hashCode(this.imageData)) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode())) + this.pictureType;
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
        final ID3v2PictureFrameData id3v2PictureFrameData = (ID3v2PictureFrameData)o;
        if (this.description == null) {
            if (id3v2PictureFrameData.description != null) {
                return false;
            }
        }
        else if (!this.description.equals(id3v2PictureFrameData.description)) {
            return false;
        }
        if (!Arrays.equals(this.imageData, id3v2PictureFrameData.imageData)) {
            return false;
        }
        if (this.mimeType == null) {
            if (id3v2PictureFrameData.mimeType != null) {
                return false;
            }
        }
        else if (!this.mimeType.equals(id3v2PictureFrameData.mimeType)) {
            return false;
        }
        return this.pictureType == id3v2PictureFrameData.pictureType;
    }
}
