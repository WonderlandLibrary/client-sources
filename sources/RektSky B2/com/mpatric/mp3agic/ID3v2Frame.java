package com.mpatric.mp3agic;

import java.io.*;
import java.util.*;

public class ID3v2Frame
{
    private static final int HEADER_LENGTH = 10;
    private static final int ID_OFFSET = 0;
    private static final int ID_LENGTH = 4;
    protected static final int DATA_LENGTH_OFFSET = 4;
    private static final int FLAGS1_OFFSET = 8;
    private static final int FLAGS2_OFFSET = 9;
    private static final int PRESERVE_TAG_BIT = 6;
    private static final int PRESERVE_FILE_BIT = 5;
    private static final int READ_ONLY_BIT = 4;
    private static final int GROUP_BIT = 6;
    private static final int COMPRESSION_BIT = 3;
    private static final int ENCRYPTION_BIT = 2;
    private static final int UNSYNCHRONISATION_BIT = 1;
    private static final int DATA_LENGTH_INDICATOR_BIT = 0;
    protected String id;
    protected int dataLength;
    protected byte[] data;
    private boolean preserveTag;
    private boolean preserveFile;
    private boolean readOnly;
    private boolean group;
    private boolean compression;
    private boolean encryption;
    private boolean unsynchronisation;
    private boolean dataLengthIndicator;
    
    public ID3v2Frame(final byte[] array, final int n) throws InvalidDataException {
        this.dataLength = 0;
        this.data = null;
        this.preserveTag = false;
        this.preserveFile = false;
        this.readOnly = false;
        this.group = false;
        this.compression = false;
        this.encryption = false;
        this.unsynchronisation = false;
        this.dataLengthIndicator = false;
        this.unpackFrame(array, n);
    }
    
    public ID3v2Frame(final String id, final byte[] data) {
        this.dataLength = 0;
        this.data = null;
        this.preserveTag = false;
        this.preserveFile = false;
        this.readOnly = false;
        this.group = false;
        this.compression = false;
        this.encryption = false;
        this.unsynchronisation = false;
        this.dataLengthIndicator = false;
        this.id = id;
        this.data = data;
        this.dataLength = data.length;
    }
    
    protected final void unpackFrame(final byte[] array, final int n) throws InvalidDataException {
        final int unpackHeader = this.unpackHeader(array, n);
        this.sanityCheckUnpackedHeader();
        this.data = BufferTools.copyBuffer(array, unpackHeader, this.dataLength);
    }
    
    protected int unpackHeader(final byte[] array, final int n) {
        this.id = BufferTools.byteBufferToStringIgnoringEncodingIssues(array, n + 0, 4);
        this.unpackDataLength(array, n);
        this.unpackFlags(array, n);
        return n + 10;
    }
    
    protected void unpackDataLength(final byte[] array, final int n) {
        this.dataLength = BufferTools.unpackInteger(array[n + 4], array[n + 4 + 1], array[n + 4 + 2], array[n + 4 + 3]);
    }
    
    private void unpackFlags(final byte[] array, final int n) {
        this.preserveTag = BufferTools.checkBit(array[n + 8], 6);
        this.preserveFile = BufferTools.checkBit(array[n + 8], 5);
        this.readOnly = BufferTools.checkBit(array[n + 8], 4);
        this.group = BufferTools.checkBit(array[n + 9], 6);
        this.compression = BufferTools.checkBit(array[n + 9], 3);
        this.encryption = BufferTools.checkBit(array[n + 9], 2);
        this.unsynchronisation = BufferTools.checkBit(array[n + 9], 1);
        this.dataLengthIndicator = BufferTools.checkBit(array[n + 9], 0);
    }
    
    protected void sanityCheckUnpackedHeader() throws InvalidDataException {
        for (int i = 0; i < this.id.length(); ++i) {
            if ((this.id.charAt(i) < 'A' || this.id.charAt(i) > 'Z') && (this.id.charAt(i) < '0' || this.id.charAt(i) > '9')) {
                throw new InvalidDataException("Not a valid frame - invalid tag " + this.id);
            }
        }
    }
    
    public byte[] toBytes() throws NotSupportedException {
        final byte[] array = new byte[this.getLength()];
        this.packFrame(array, 0);
        return array;
    }
    
    public void toBytes(final byte[] array, final int n) throws NotSupportedException {
        this.packFrame(array, n);
    }
    
    public void packFrame(final byte[] array, final int n) throws NotSupportedException {
        this.packHeader(array, n);
        BufferTools.copyIntoByteBuffer(this.data, 0, this.data.length, array, n + 10);
    }
    
    private void packHeader(final byte[] array, final int n) {
        try {
            BufferTools.stringIntoByteBuffer(this.id, 0, this.id.length(), array, 0);
        }
        catch (UnsupportedEncodingException ex) {}
        BufferTools.copyIntoByteBuffer(this.packDataLength(), 0, 4, array, 4);
        BufferTools.copyIntoByteBuffer(this.packFlags(), 0, 2, array, 8);
    }
    
    protected byte[] packDataLength() {
        return BufferTools.packInteger(this.dataLength);
    }
    
    private byte[] packFlags() {
        final byte[] array = new byte[2];
        array[0] = BufferTools.setBit(array[0], 6, this.preserveTag);
        array[0] = BufferTools.setBit(array[0], 5, this.preserveFile);
        array[0] = BufferTools.setBit(array[0], 4, this.readOnly);
        array[1] = BufferTools.setBit(array[1], 6, this.group);
        array[1] = BufferTools.setBit(array[1], 3, this.compression);
        array[1] = BufferTools.setBit(array[1], 2, this.encryption);
        array[1] = BufferTools.setBit(array[1], 1, this.unsynchronisation);
        array[1] = BufferTools.setBit(array[1], 0, this.dataLengthIndicator);
        return array;
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getDataLength() {
        return this.dataLength;
    }
    
    public int getLength() {
        return this.dataLength + 10;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public void setData(final byte[] data) {
        this.data = data;
        if (data == null) {
            this.dataLength = 0;
        }
        else {
            this.dataLength = data.length;
        }
    }
    
    public boolean hasDataLengthIndicator() {
        return this.dataLengthIndicator;
    }
    
    public boolean hasCompression() {
        return this.compression;
    }
    
    public boolean hasEncryption() {
        return this.encryption;
    }
    
    public boolean hasGroup() {
        return this.group;
    }
    
    public boolean hasPreserveFile() {
        return this.preserveFile;
    }
    
    public boolean hasPreserveTag() {
        return this.preserveTag;
    }
    
    public boolean isReadOnly() {
        return this.readOnly;
    }
    
    public boolean hasUnsynchronisation() {
        return this.unsynchronisation;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * 1 + (this.compression ? 1231 : 1237)) + Arrays.hashCode(this.data)) + this.dataLength) + (this.dataLengthIndicator ? 1231 : 1237)) + (this.encryption ? 1231 : 1237)) + (this.group ? 1231 : 1237)) + ((this.id == null) ? 0 : this.id.hashCode())) + (this.preserveFile ? 1231 : 1237)) + (this.preserveTag ? 1231 : 1237)) + (this.readOnly ? 1231 : 1237)) + (this.unsynchronisation ? 1231 : 1237);
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
        final ID3v2Frame id3v2Frame = (ID3v2Frame)o;
        if (this.compression != id3v2Frame.compression) {
            return false;
        }
        if (!Arrays.equals(this.data, id3v2Frame.data)) {
            return false;
        }
        if (this.dataLength != id3v2Frame.dataLength) {
            return false;
        }
        if (this.dataLengthIndicator != id3v2Frame.dataLengthIndicator) {
            return false;
        }
        if (this.encryption != id3v2Frame.encryption) {
            return false;
        }
        if (this.group != id3v2Frame.group) {
            return false;
        }
        if (this.id == null) {
            if (id3v2Frame.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(id3v2Frame.id)) {
            return false;
        }
        return this.preserveFile == id3v2Frame.preserveFile && this.preserveTag == id3v2Frame.preserveTag && this.readOnly == id3v2Frame.readOnly && this.unsynchronisation == id3v2Frame.unsynchronisation;
    }
}
