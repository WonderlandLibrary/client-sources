package org.bouncycastle.crypto;

public interface StreamCipher
{
    byte returnByte(final byte p0);
    
    void processBytes(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4) throws DataLengthException;
}
