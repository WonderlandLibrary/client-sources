package com.mpatric.mp3agic;

public abstract class AbstractID3v2FrameData
{
    boolean unsynchronisation;
    
    public AbstractID3v2FrameData(final boolean unsynchronisation) {
        this.unsynchronisation = unsynchronisation;
    }
    
    protected final void synchroniseAndUnpackFrameData(final byte[] array) throws InvalidDataException {
        if (this.unsynchronisation && BufferTools.sizeSynchronisationWouldSubtract(array) > 0) {
            this.unpackFrameData(BufferTools.synchroniseBuffer(array));
        }
        else {
            this.unpackFrameData(array);
        }
    }
    
    protected byte[] packAndUnsynchroniseFrameData() {
        final byte[] packFrameData = this.packFrameData();
        if (this.unsynchronisation && BufferTools.sizeUnsynchronisationWouldAdd(packFrameData) > 0) {
            return BufferTools.unsynchroniseBuffer(packFrameData);
        }
        return packFrameData;
    }
    
    protected byte[] toBytes() {
        return this.packAndUnsynchroniseFrameData();
    }
    
    @Override
    public int hashCode() {
        return 31 * 1 + (this.unsynchronisation ? 1231 : 1237);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.unsynchronisation == ((AbstractID3v2FrameData)o).unsynchronisation);
    }
    
    protected abstract void unpackFrameData(final byte[] p0) throws InvalidDataException;
    
    protected abstract byte[] packFrameData();
    
    protected abstract int getLength();
}
