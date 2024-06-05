package com.mpatric.mp3agic;

public class ID3v24Tag extends AbstractID3v2Tag
{
    public static final String VERSION = "4.0";
    public static final String ID_RECTIME = "TDRC";
    
    public ID3v24Tag() {
        this.version = "4.0";
    }
    
    public ID3v24Tag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        super(array);
    }
    
    @Override
    protected void unpackFlags(final byte[] array) {
        this.unsynchronisation = BufferTools.checkBit(array[5], 7);
        this.extendedHeader = BufferTools.checkBit(array[5], 6);
        this.experimental = BufferTools.checkBit(array[5], 5);
        this.footer = BufferTools.checkBit(array[5], 4);
    }
    
    @Override
    protected void packFlags(final byte[] array, final int n) {
        array[n + 5] = BufferTools.setBit(array[n + 5], 7, this.unsynchronisation);
        array[n + 5] = BufferTools.setBit(array[n + 5], 6, this.extendedHeader);
        array[n + 5] = BufferTools.setBit(array[n + 5], 5, this.experimental);
        array[n + 5] = BufferTools.setBit(array[n + 5], 4, this.footer);
    }
    
    @Override
    protected boolean useFrameUnsynchronisation() {
        return this.unsynchronisation;
    }
    
    @Override
    protected ID3v2Frame createFrame(final byte[] array, final int n) throws InvalidDataException {
        return new ID3v24Frame(array, n);
    }
    
    @Override
    protected ID3v2Frame createFrame(final String s, final byte[] array) {
        return new ID3v24Frame(s, array);
    }
    
    @Override
    public void setGenreDescription(final String s) {
        final ID3v2TextFrameData id3v2TextFrameData = new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s));
        ID3v2FrameSet set = this.getFrameSets().get("TCON");
        if (set == null) {
            this.getFrameSets().put("TCON", set = new ID3v2FrameSet("TCON"));
        }
        set.clear();
        set.addFrame(this.createFrame("TCON", id3v2TextFrameData.toBytes()));
    }
    
    public String getRecordingTime() {
        final ID3v2TextFrameData textFrameData = this.extractTextFrameData("TDRC");
        if (textFrameData != null && textFrameData.getText() != null) {
            return textFrameData.getText().toString();
        }
        return null;
    }
    
    public void setRecordingTime(final String s) {
        if (s != null && s.length() > 0) {
            this.invalidateDataLength();
            this.addFrame(this.createFrame("TDRC", new ID3v2TextFrameData(this.useFrameUnsynchronisation(), new EncodedText(s)).toBytes()), true);
        }
    }
}
