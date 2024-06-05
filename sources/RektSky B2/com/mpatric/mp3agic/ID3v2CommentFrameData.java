package com.mpatric.mp3agic;

import java.io.*;

public class ID3v2CommentFrameData extends AbstractID3v2FrameData
{
    private static final String DEFAULT_LANGUAGE = "eng";
    private String language;
    private EncodedText description;
    private EncodedText comment;
    
    public ID3v2CommentFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2CommentFrameData(final boolean b, final String language, final EncodedText description, final EncodedText comment) {
        super(b);
        if (description != null && comment != null && description.getTextEncoding() != comment.getTextEncoding()) {
            throw new IllegalArgumentException("description and comment must have same text encoding");
        }
        this.language = language;
        this.description = description;
        this.comment = comment;
    }
    
    public ID3v2CommentFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        try {
            this.language = BufferTools.byteBufferToString(array, 1, 3);
        }
        catch (UnsupportedEncodingException ex) {
            this.language = "";
        }
        final int indexOfTerminatorForEncoding = BufferTools.indexOfTerminatorForEncoding(array, 4, array[0]);
        int n;
        if (indexOfTerminatorForEncoding >= 4) {
            this.description = new EncodedText(array[0], BufferTools.copyBuffer(array, 4, indexOfTerminatorForEncoding - 4));
            n = indexOfTerminatorForEncoding + this.description.getTerminator().length;
        }
        else {
            this.description = new EncodedText(array[0], "");
            n = 4;
        }
        this.comment = new EncodedText(array[0], BufferTools.copyBuffer(array, n, array.length - n));
    }
    
    @Override
    protected byte[] packFrameData() {
        final byte[] array = new byte[this.getLength()];
        if (this.comment != null) {
            array[0] = this.comment.getTextEncoding();
        }
        else {
            array[0] = 0;
        }
        String s;
        if (this.language == null) {
            s = "eng";
        }
        else if (this.language.length() > 3) {
            s = this.language.substring(0, 3);
        }
        else {
            s = BufferTools.padStringRight(this.language, 3, '\0');
        }
        try {
            BufferTools.stringIntoByteBuffer(s, 0, 3, array, 1);
        }
        catch (UnsupportedEncodingException ex) {}
        final int n = 4;
        int n2;
        if (this.description != null) {
            final byte[] bytes = this.description.toBytes(true, true);
            BufferTools.copyIntoByteBuffer(bytes, 0, bytes.length, array, n);
            n2 = n + bytes.length;
        }
        else {
            final byte[] array2 = (this.comment != null) ? this.comment.getTerminator() : new byte[] { 0 };
            BufferTools.copyIntoByteBuffer(array2, 0, array2.length, array, n);
            n2 = n + array2.length;
        }
        if (this.comment != null) {
            final byte[] bytes2 = this.comment.toBytes(true, false);
            BufferTools.copyIntoByteBuffer(bytes2, 0, bytes2.length, array, n2);
        }
        return array;
    }
    
    @Override
    protected int getLength() {
        final int n = 4;
        int n2;
        if (this.description != null) {
            n2 = n + this.description.toBytes(true, true).length;
        }
        else {
            n2 = n + ((this.comment != null) ? this.comment.getTerminator().length : 1);
        }
        if (this.comment != null) {
            n2 += this.comment.toBytes(true, false).length;
        }
        return n2;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(final String language) {
        this.language = language;
    }
    
    public EncodedText getComment() {
        return this.comment;
    }
    
    public void setComment(final EncodedText comment) {
        this.comment = comment;
    }
    
    public EncodedText getDescription() {
        return this.description;
    }
    
    public void setDescription(final EncodedText description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * super.hashCode() + ((this.comment == null) ? 0 : this.comment.hashCode())) + ((this.description == null) ? 0 : this.description.hashCode())) + ((this.language == null) ? 0 : this.language.hashCode());
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
        final ID3v2CommentFrameData id3v2CommentFrameData = (ID3v2CommentFrameData)o;
        if (this.comment == null) {
            if (id3v2CommentFrameData.comment != null) {
                return false;
            }
        }
        else if (!this.comment.equals(id3v2CommentFrameData.comment)) {
            return false;
        }
        if (this.description == null) {
            if (id3v2CommentFrameData.description != null) {
                return false;
            }
        }
        else if (!this.description.equals(id3v2CommentFrameData.description)) {
            return false;
        }
        if (this.language == null) {
            if (id3v2CommentFrameData.language != null) {
                return false;
            }
        }
        else if (!this.language.equals(id3v2CommentFrameData.language)) {
            return false;
        }
        return true;
    }
}
