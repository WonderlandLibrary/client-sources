package com.mpatric.mp3agic;

public class ID3v2TextFrameData extends AbstractID3v2FrameData
{
    protected EncodedText text;
    
    public ID3v2TextFrameData(final boolean b) {
        super(b);
    }
    
    public ID3v2TextFrameData(final boolean b, final EncodedText text) {
        super(b);
        this.text = text;
    }
    
    public ID3v2TextFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        this.text = new EncodedText(array[0], BufferTools.copyBuffer(array, 1, array.length - 1));
    }
    
    @Override
    protected byte[] packFrameData() {
        final byte[] array = new byte[this.getLength()];
        if (this.text != null) {
            array[0] = this.text.getTextEncoding();
            final byte[] bytes = this.text.toBytes(true, false);
            if (bytes.length > 0) {
                BufferTools.copyIntoByteBuffer(bytes, 0, bytes.length, array, 1);
            }
        }
        return array;
    }
    
    @Override
    protected int getLength() {
        int n = 1;
        if (this.text != null) {
            n += this.text.toBytes(true, false).length;
        }
        return n;
    }
    
    public EncodedText getText() {
        return this.text;
    }
    
    public void setText(final EncodedText text) {
        this.text = text;
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + ((this.text == null) ? 0 : this.text.hashCode());
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
        final ID3v2TextFrameData id3v2TextFrameData = (ID3v2TextFrameData)o;
        if (this.text == null) {
            if (id3v2TextFrameData.text != null) {
                return false;
            }
        }
        else if (!this.text.equals(id3v2TextFrameData.text)) {
            return false;
        }
        return true;
    }
}
