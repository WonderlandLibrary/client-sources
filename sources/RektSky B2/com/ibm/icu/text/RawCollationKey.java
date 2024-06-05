package com.ibm.icu.text;

import com.ibm.icu.util.*;

public final class RawCollationKey extends ByteArrayWrapper
{
    public RawCollationKey() {
    }
    
    public RawCollationKey(final int capacity) {
        this.bytes = new byte[capacity];
    }
    
    public RawCollationKey(final byte[] bytes) {
        this.bytes = bytes;
    }
    
    public RawCollationKey(final byte[] bytesToAdopt, final int size) {
        super(bytesToAdopt, size);
    }
    
    public int compareTo(final RawCollationKey rhs) {
        final int result = super.compareTo((ByteArrayWrapper)rhs);
        return (result < 0) ? -1 : ((result == 0) ? 0 : 1);
    }
}
