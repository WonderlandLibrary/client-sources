package com.mpatric.mp3agic;

public class ID3v2TagFactory
{
    public static AbstractID3v2Tag createTag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        sanityCheckTag(array);
        switch (array[3]) {
            case 2: {
                return createID3v22Tag(array);
            }
            case 3: {
                return new ID3v23Tag(array);
            }
            case 4: {
                return new ID3v24Tag(array);
            }
            default: {
                throw new UnsupportedTagException("Tag version not supported");
            }
        }
    }
    
    private static AbstractID3v2Tag createID3v22Tag(final byte[] array) throws NoSuchTagException, UnsupportedTagException, InvalidDataException {
        ID3v22Tag id3v22Tag = new ID3v22Tag(array);
        if (id3v22Tag.getFrameSets().isEmpty()) {
            id3v22Tag = new ID3v22Tag(array, true);
        }
        return id3v22Tag;
    }
    
    public static void sanityCheckTag(final byte[] array) throws NoSuchTagException, UnsupportedTagException {
        if (array.length < 10) {
            throw new NoSuchTagException("Buffer too short");
        }
        if (!"ID3".equals(BufferTools.byteBufferToStringIgnoringEncodingIssues(array, 0, "ID3".length()))) {
            throw new NoSuchTagException();
        }
        final byte b = array[3];
        if (b != 2 && b != 3 && b != 4) {
            throw new UnsupportedTagException("Unsupported version 2." + b + "." + array[4]);
        }
    }
}
