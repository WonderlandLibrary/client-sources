package com.mpatric.mp3agic;

import java.nio.*;
import java.util.*;

public class ID3v2ChapterFrameData extends AbstractID3v2FrameData
{
    protected String id;
    protected int startTime;
    protected int endTime;
    protected int startOffset;
    protected int endOffset;
    protected ArrayList<ID3v2Frame> subframes;
    
    public ID3v2ChapterFrameData(final boolean b) {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
    }
    
    public ID3v2ChapterFrameData(final boolean b, final String id, final int startTime, final int endTime, final int startOffset, final int endOffset) {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }
    
    public ID3v2ChapterFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        this.id = ByteBufferUtils.extractNullTerminatedString(wrap);
        wrap.position(this.id.length() + 1);
        this.startTime = wrap.getInt();
        this.endTime = wrap.getInt();
        this.startOffset = wrap.getInt();
        this.endOffset = wrap.getInt();
        int i = wrap.position();
        while (i < array.length) {
            final ID3v2Frame id3v2Frame = new ID3v2Frame(array, i);
            i += id3v2Frame.getLength();
            this.subframes.add(id3v2Frame);
        }
    }
    
    public void addSubframe(final String s, final AbstractID3v2FrameData abstractID3v2FrameData) {
        this.subframes.add(new ID3v2Frame(s, abstractID3v2FrameData.toBytes()));
    }
    
    @Override
    protected byte[] packFrameData() {
        final ByteBuffer allocate = ByteBuffer.allocate(this.getLength());
        allocate.put(this.id.getBytes());
        allocate.put((byte)0);
        allocate.putInt(this.startTime);
        allocate.putInt(this.endTime);
        allocate.putInt(this.startOffset);
        allocate.putInt(this.endOffset);
        for (final ID3v2Frame id3v2Frame : this.subframes) {
            try {
                allocate.put(id3v2Frame.toBytes());
            }
            catch (NotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        return allocate.array();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public int getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final int startTime) {
        this.startTime = startTime;
    }
    
    public int getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final int endTime) {
        this.endTime = endTime;
    }
    
    public int getStartOffset() {
        return this.startOffset;
    }
    
    public void setStartOffset(final int startOffset) {
        this.startOffset = startOffset;
    }
    
    public int getEndOffset() {
        return this.endOffset;
    }
    
    public void setEndOffset(final int endOffset) {
        this.endOffset = endOffset;
    }
    
    public ArrayList<ID3v2Frame> getSubframes() {
        return this.subframes;
    }
    
    public void setSubframes(final ArrayList<ID3v2Frame> subframes) {
        this.subframes = subframes;
    }
    
    @Override
    protected int getLength() {
        int n = 1;
        n += 16;
        if (this.id != null) {
            n += this.id.length();
        }
        if (this.subframes != null) {
            final Iterator<ID3v2Frame> iterator = this.subframes.iterator();
            while (iterator.hasNext()) {
                n += iterator.next().getLength();
            }
        }
        return n;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID3v2ChapterFrameData [id=");
        sb.append(this.id);
        sb.append(", startTime=");
        sb.append(this.startTime);
        sb.append(", endTime=");
        sb.append(this.endTime);
        sb.append(", startOffset=");
        sb.append(this.startOffset);
        sb.append(", endOffset=");
        sb.append(this.endOffset);
        sb.append(", subframes=");
        sb.append(this.subframes);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * 1 + this.endOffset) + this.endTime) + ((this.id == null) ? 0 : this.id.hashCode())) + this.startOffset) + this.startTime) + ((this.subframes == null) ? 0 : this.subframes.hashCode());
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
        final ID3v2ChapterFrameData id3v2ChapterFrameData = (ID3v2ChapterFrameData)o;
        if (this.endOffset != id3v2ChapterFrameData.endOffset) {
            return false;
        }
        if (this.endTime != id3v2ChapterFrameData.endTime) {
            return false;
        }
        if (this.id == null) {
            if (id3v2ChapterFrameData.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(id3v2ChapterFrameData.id)) {
            return false;
        }
        if (this.startOffset != id3v2ChapterFrameData.startOffset) {
            return false;
        }
        if (this.startTime != id3v2ChapterFrameData.startTime) {
            return false;
        }
        if (this.subframes == null) {
            if (id3v2ChapterFrameData.subframes != null) {
                return false;
            }
        }
        else if (!this.subframes.equals(id3v2ChapterFrameData.subframes)) {
            return false;
        }
        return true;
    }
}
