package org.spongycastle.crypto.engines;

import java.util.ArrayList;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;








public class DSTU7624WrapEngine
  implements Wrapper
{
  private static final int BYTES_IN_INTEGER = 4;
  private boolean forWrapping;
  private DSTU7624Engine engine;
  private byte[] B;
  private byte[] intArray;
  private byte[] checkSumArray;
  private byte[] zeroArray;
  private ArrayList<byte[]> Btemp;
  
  public DSTU7624WrapEngine(int blockBitLength)
  {
    engine = new DSTU7624Engine(blockBitLength);
    B = new byte[engine.getBlockSize() / 2];
    checkSumArray = new byte[engine.getBlockSize()];
    zeroArray = new byte[engine.getBlockSize()];
    Btemp = new ArrayList();
    intArray = new byte[4];
  }
  

  public void init(boolean forWrapping, CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      param = ((ParametersWithRandom)param).getParameters();
    }
    
    this.forWrapping = forWrapping;
    if ((param instanceof KeyParameter))
    {
      engine.init(forWrapping, param);
    }
    else
    {
      throw new IllegalArgumentException("invalid parameters passed to DSTU7624WrapEngine");
    }
  }
  

  public String getAlgorithmName()
  {
    return "DSTU7624WrapEngine";
  }
  
  public byte[] wrap(byte[] in, int inOff, int inLen)
  {
    if (!forWrapping)
    {
      throw new IllegalStateException("not set for wrapping");
    }
    
    if (inLen % engine.getBlockSize() != 0)
    {

      throw new DataLengthException("wrap data must be a multiple of " + engine.getBlockSize() + " bytes");
    }
    
    if (inOff + inLen > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    int n = 2 * (1 + inLen / engine.getBlockSize());
    int V = (n - 1) * 6;
    

    byte[] wrappedBuffer = new byte[inLen + engine.getBlockSize()];
    System.arraycopy(in, inOff, wrappedBuffer, 0, inLen);
    
    System.arraycopy(wrappedBuffer, 0, B, 0, engine.getBlockSize() / 2);
    
    Btemp.clear();
    
    int bHalfBlocksLen = wrappedBuffer.length - engine.getBlockSize() / 2;
    int bufOff = engine.getBlockSize() / 2;
    while (bHalfBlocksLen != 0)
    {
      byte[] temp = new byte[engine.getBlockSize() / 2];
      System.arraycopy(wrappedBuffer, bufOff, temp, 0, engine.getBlockSize() / 2);
      
      Btemp.add(temp);
      
      bHalfBlocksLen -= engine.getBlockSize() / 2;
      bufOff += engine.getBlockSize() / 2;
    }
    
    for (int j = 0; j < V; j++)
    {
      System.arraycopy(B, 0, wrappedBuffer, 0, engine.getBlockSize() / 2);
      System.arraycopy(Btemp.get(0), 0, wrappedBuffer, engine.getBlockSize() / 2, engine.getBlockSize() / 2);
      
      engine.processBlock(wrappedBuffer, 0, wrappedBuffer, 0);
      
      intToBytes(j + 1, intArray, 0);
      for (int byteNum = 0; byteNum < 4; byteNum++)
      {
        int tmp374_373 = (byteNum + engine.getBlockSize() / 2); byte[] tmp374_360 = wrappedBuffer;tmp374_360[tmp374_373] = ((byte)(tmp374_360[tmp374_373] ^ intArray[byteNum]));
      }
      
      System.arraycopy(wrappedBuffer, engine.getBlockSize() / 2, B, 0, engine.getBlockSize() / 2);
      
      for (int i = 2; i < n; i++)
      {
        System.arraycopy(Btemp.get(i - 1), 0, Btemp.get(i - 2), 0, engine.getBlockSize() / 2);
      }
      
      System.arraycopy(wrappedBuffer, 0, Btemp.get(n - 2), 0, engine.getBlockSize() / 2);
    }
    

    System.arraycopy(B, 0, wrappedBuffer, 0, engine.getBlockSize() / 2);
    bufOff = engine.getBlockSize() / 2;
    
    for (int i = 0; i < n - 1; i++)
    {
      System.arraycopy(Btemp.get(i), 0, wrappedBuffer, bufOff, engine.getBlockSize() / 2);
      bufOff += engine.getBlockSize() / 2;
    }
    
    return wrappedBuffer;
  }
  

  public byte[] unwrap(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forWrapping)
    {
      throw new IllegalStateException("not set for unwrapping");
    }
    
    if (inLen % engine.getBlockSize() != 0)
    {

      throw new DataLengthException("unwrap data must be a multiple of " + engine.getBlockSize() + " bytes");
    }
    
    int n = 2 * inLen / engine.getBlockSize();
    
    int V = (n - 1) * 6;
    
    byte[] buffer = new byte[inLen];
    System.arraycopy(in, inOff, buffer, 0, inLen);
    
    byte[] B = new byte[engine.getBlockSize() / 2];
    System.arraycopy(buffer, 0, B, 0, engine.getBlockSize() / 2);
    
    Btemp.clear();
    
    int bHalfBlocksLen = buffer.length - engine.getBlockSize() / 2;
    int bufOff = engine.getBlockSize() / 2;
    while (bHalfBlocksLen != 0)
    {
      byte[] temp = new byte[engine.getBlockSize() / 2];
      System.arraycopy(buffer, bufOff, temp, 0, engine.getBlockSize() / 2);
      
      Btemp.add(temp);
      
      bHalfBlocksLen -= engine.getBlockSize() / 2;
      bufOff += engine.getBlockSize() / 2;
    }
    
    for (int j = 0; j < V; j++)
    {
      System.arraycopy(Btemp.get(n - 2), 0, buffer, 0, engine.getBlockSize() / 2);
      System.arraycopy(B, 0, buffer, engine.getBlockSize() / 2, engine.getBlockSize() / 2);
      intToBytes(V - j, intArray, 0);
      for (int byteNum = 0; byteNum < 4; byteNum++)
      {
        int tmp345_344 = (byteNum + engine.getBlockSize() / 2); byte[] tmp345_331 = buffer;tmp345_331[tmp345_344] = ((byte)(tmp345_331[tmp345_344] ^ intArray[byteNum]));
      }
      
      engine.processBlock(buffer, 0, buffer, 0);
      
      System.arraycopy(buffer, 0, B, 0, engine.getBlockSize() / 2);
      
      for (int i = 2; i < n; i++)
      {
        System.arraycopy(Btemp.get(n - i - 1), 0, Btemp.get(n - i), 0, engine.getBlockSize() / 2);
      }
      
      System.arraycopy(buffer, engine.getBlockSize() / 2, Btemp.get(0), 0, engine.getBlockSize() / 2);
    }
    
    System.arraycopy(B, 0, buffer, 0, engine.getBlockSize() / 2);
    bufOff = engine.getBlockSize() / 2;
    
    for (int i = 0; i < n - 1; i++)
    {
      System.arraycopy(Btemp.get(i), 0, buffer, bufOff, engine.getBlockSize() / 2);
      bufOff += engine.getBlockSize() / 2;
    }
    
    System.arraycopy(buffer, buffer.length - engine.getBlockSize(), checkSumArray, 0, engine.getBlockSize());
    
    byte[] wrappedBuffer = new byte[buffer.length - engine.getBlockSize()];
    if (!Arrays.areEqual(checkSumArray, zeroArray))
    {
      throw new InvalidCipherTextException("checksum failed");
    }
    

    System.arraycopy(buffer, 0, wrappedBuffer, 0, buffer.length - engine.getBlockSize());
    


    return wrappedBuffer;
  }
  

  private void intToBytes(int number, byte[] outBytes, int outOff)
  {
    outBytes[(outOff + 3)] = ((byte)(number >> 24));
    outBytes[(outOff + 2)] = ((byte)(number >> 16));
    outBytes[(outOff + 1)] = ((byte)(number >> 8));
    outBytes[outOff] = ((byte)number);
  }
}
