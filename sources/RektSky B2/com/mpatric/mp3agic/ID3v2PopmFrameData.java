package com.mpatric.mp3agic;

import java.io.*;
import java.util.*;

public class ID3v2PopmFrameData extends AbstractID3v2FrameData
{
    protected static final String WMP9_ADDRESS = "Windows Media Player 9 Series";
    protected String address;
    protected int rating;
    private static final Map<Byte, Integer> byteToRating;
    private static final byte[] wmp9encodedRatings;
    
    public ID3v2PopmFrameData(final boolean b, final byte[] array) throws InvalidDataException {
        super(b);
        this.address = "";
        this.rating = -1;
        this.synchroniseAndUnpackFrameData(array);
    }
    
    public ID3v2PopmFrameData(final boolean b, final int rating) {
        super(b);
        this.address = "";
        this.rating = -1;
        this.address = "Windows Media Player 9 Series";
        this.rating = rating;
    }
    
    @Override
    protected void unpackFrameData(final byte[] array) throws InvalidDataException {
        try {
            this.address = BufferTools.byteBufferToString(array, 0, array.length - 2);
        }
        catch (UnsupportedEncodingException ex) {
            this.address = "";
        }
        final byte b = array[array.length - 1];
        if (ID3v2PopmFrameData.byteToRating.containsKey(b)) {
            this.rating = ID3v2PopmFrameData.byteToRating.get(b);
        }
        else {
            this.rating = -1;
        }
    }
    
    @Override
    protected byte[] packFrameData() {
        final byte[] copy = Arrays.copyOf(this.address.getBytes(), this.address.length() + 2);
        copy[copy.length - 2] = 0;
        copy[copy.length - 1] = ID3v2PopmFrameData.wmp9encodedRatings[this.rating];
        return copy;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public int getRating() {
        return this.rating;
    }
    
    public void setRating(final int rating) {
        this.rating = rating;
    }
    
    @Override
    protected int getLength() {
        return this.address.length() + 2;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * super.hashCode() + ((this.address == null) ? 0 : this.address.hashCode())) + this.rating;
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
        final ID3v2PopmFrameData id3v2PopmFrameData = (ID3v2PopmFrameData)o;
        if (this.address == null) {
            if (id3v2PopmFrameData.address != null) {
                return false;
            }
        }
        else if (!this.address.equals(id3v2PopmFrameData.address)) {
            return false;
        }
        return this.rating == id3v2PopmFrameData.rating;
    }
    
    static {
        byteToRating = new HashMap<Byte, Integer>(5);
        wmp9encodedRatings = new byte[] { 0, 1, 64, -128, -60, -1 };
        for (int i = 0; i < 6; ++i) {
            ID3v2PopmFrameData.byteToRating.put(ID3v2PopmFrameData.wmp9encodedRatings[i], i);
        }
    }
}
