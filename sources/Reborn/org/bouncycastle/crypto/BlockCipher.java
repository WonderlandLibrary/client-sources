package org.bouncycastle.crypto;

public interface BlockCipher
{
    void init(final boolean p0, final CipherParameters p1) throws IllegalArgumentException;
    
    String getAlgorithmName();
    
    int getBlockSize();
    
    int processBlock(final byte[] p0, final int p1, final byte[] p2, final int p3) throws DataLengthException, IllegalStateException;
    
    void reset();
}
