package com.mpatric.mp3agic;

import java.util.*;

public class ID3v2FrameSet
{
    private String id;
    private ArrayList<ID3v2Frame> frames;
    
    public ID3v2FrameSet(final String id) {
        this.id = id;
        this.frames = new ArrayList<ID3v2Frame>();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void clear() {
        this.frames.clear();
    }
    
    public void addFrame(final ID3v2Frame id3v2Frame) {
        this.frames.add(id3v2Frame);
    }
    
    public List<ID3v2Frame> getFrames() {
        return this.frames;
    }
    
    @Override
    public String toString() {
        return this.id + ": " + this.frames.size();
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * 1 + ((this.frames == null) ? 0 : this.frames.hashCode())) + ((this.id == null) ? 0 : this.id.hashCode());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ID3v2FrameSet set = (ID3v2FrameSet)o;
        if (this.frames == null) {
            if (set.frames != null) {
                return false;
            }
        }
        else if (!this.frames.equals(set.frames)) {
            return false;
        }
        if (this.id == null) {
            if (set.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(set.id)) {
            return false;
        }
        return true;
    }
}
