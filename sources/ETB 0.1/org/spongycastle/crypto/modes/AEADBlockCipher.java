package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;

public abstract interface AEADBlockCipher
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException;
  
  public abstract String getAlgorithmName();
  
  public abstract BlockCipher getUnderlyingCipher();
  
  public abstract void processAADByte(byte paramByte);
  
  public abstract void processAADBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract int processByte(byte paramByte, byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException;
  
  public abstract int processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws DataLengthException;
  
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws IllegalStateException, InvalidCipherTextException;
  
  public abstract byte[] getMac();
  
  public abstract int getUpdateOutputSize(int paramInt);
  
  public abstract int getOutputSize(int paramInt);
  
  public abstract void reset();
}
