package com.mpatric.mp3agic;

import java.nio.*;
import java.util.*;

public class ID3v2ChapterTOCFrameData extends AbstractID3v2FrameData
{
    protected boolean isRoot;
    protected boolean isOrdered;
    protected String id;
    protected String[] children;
    protected ArrayList<ID3v2Frame> subframes;
    
    public ID3v2ChapterTOCFrameData(final boolean b) {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
    }
    
    public ID3v2ChapterTOCFrameData(final boolean b, final boolean isRoot, final boolean isOrdered, final String id, final String[] children) {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
        this.isRoot = isRoot;
        this.isOrdered = isOrdered;
        this.id = id;
        this.children = children;
    }
    
    public ID3v2ChapterTOCFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.subframes = new ArrayList<ID3v2Frame>();
        this.synchroniseAndUnpackFrameData(array);
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        this.id = ByteBufferUtils.extractNullTerminatedString(wrap);
        final byte value = wrap.get();
        if ((value & 0x1) == 0x1) {
            this.isRoot = true;
        }
        if ((value & 0x2) == 0x2) {
            this.isOrdered = true;
        }
        final byte value2 = wrap.get();
        this.children = new String[value2];
        for (byte b = 0; b < value2; ++b) {
            this.children[b] = ByteBufferUtils.extractNullTerminatedString(wrap);
        }
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
        allocate.put(this.getFlags());
        allocate.put((byte)this.children.length);
        final String[] children = this.children;
        for (int length = children.length, i = 0; i < length; ++i) {
            allocate.put(children[i].getBytes());
            allocate.put((byte)0);
        }
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
    
    private byte getFlags() {
        byte b = 0;
        if (this.isRoot) {
            b |= 0x1;
        }
        if (this.isOrdered) {
            b |= 0x2;
        }
        return b;
    }
    
    public boolean isRoot() {
        return this.isRoot;
    }
    
    public void setRoot(final boolean isRoot) {
        this.isRoot = isRoot;
    }
    
    public boolean isOrdered() {
        return this.isOrdered;
    }
    
    public void setOrdered(final boolean isOrdered) {
        this.isOrdered = isOrdered;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String[] getChildren() {
        return this.children;
    }
    
    public void setChildren(final String[] children) {
        this.children = children;
    }
    
    @Deprecated
    public String[] getChilds() {
        return this.children;
    }
    
    @Deprecated
    public void setChilds(final String[] children) {
        this.children = children;
    }
    
    public ArrayList<ID3v2Frame> getSubframes() {
        return this.subframes;
    }
    
    public void setSubframes(final ArrayList<ID3v2Frame> subframes) {
        this.subframes = subframes;
    }
    
    @Override
    protected int getLength() {
        int n = 3;
        if (this.id != null) {
            n += this.id.length();
        }
        if (this.children != null) {
            n += this.children.length;
            final String[] children = this.children;
            for (int length = children.length, i = 0; i < length; ++i) {
                n += children[i].length();
            }
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
        sb.append("ID3v2ChapterTOCFrameData [isRoot=");
        sb.append(this.isRoot);
        sb.append(", isOrdered=");
        sb.append(this.isOrdered);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", children=");
        sb.append(Arrays.toString(this.children));
        sb.append(", subframes=");
        sb.append(this.subframes);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * super.hashCode() + Arrays.hashCode(this.children)) + ((this.id == null) ? 0 : this.id.hashCode())) + (this.isOrdered ? 1231 : 1237)) + (this.isRoot ? 1231 : 1237)) + ((this.subframes == null) ? 0 : this.subframes.hashCode());
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
        final ID3v2ChapterTOCFrameData id3v2ChapterTOCFrameData = (ID3v2ChapterTOCFrameData)o;
        if (!Arrays.equals(this.children, id3v2ChapterTOCFrameData.children)) {
            return false;
        }
        if (this.id == null) {
            if (id3v2ChapterTOCFrameData.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(id3v2ChapterTOCFrameData.id)) {
            return false;
        }
        if (this.isOrdered != id3v2ChapterTOCFrameData.isOrdered) {
            return false;
        }
        if (this.isRoot != id3v2ChapterTOCFrameData.isRoot) {
            return false;
        }
        if (this.subframes == null) {
            if (id3v2ChapterTOCFrameData.subframes != null) {
                return false;
            }
        }
        else if (!this.subframes.equals(id3v2ChapterTOCFrameData.subframes)) {
            return false;
        }
        return true;
    }
}
