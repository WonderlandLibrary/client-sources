package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

























public class DESedeWrapEngine
  implements Wrapper
{
  private CBCBlockCipher engine;
  private KeyParameter param;
  private ParametersWithIV paramPlusIV;
  private byte[] iv;
  private boolean forWrapping;
  private static final byte[] IV2 = { 74, -35, -94, 44, 121, -24, 33, 5 };
  





  Digest sha1 = DigestFactory.createSHA1();
  byte[] digest = new byte[20];
  



  public DESedeWrapEngine() {}
  


  public void init(boolean forWrapping, CipherParameters param)
  {
    this.forWrapping = forWrapping;
    engine = new CBCBlockCipher(new DESedeEngine());
    SecureRandom sr;
    SecureRandom sr;
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom pr = (ParametersWithRandom)param;
      param = pr.getParameters();
      sr = pr.getRandom();
    }
    else
    {
      sr = new SecureRandom();
    }
    
    if ((param instanceof KeyParameter))
    {
      this.param = ((KeyParameter)param);
      
      if (this.forWrapping)
      {



        iv = new byte[8];
        sr.nextBytes(iv);
        
        paramPlusIV = new ParametersWithIV(this.param, iv);
      }
    }
    else if ((param instanceof ParametersWithIV))
    {
      paramPlusIV = ((ParametersWithIV)param);
      iv = paramPlusIV.getIV();
      this.param = ((KeyParameter)paramPlusIV.getParameters());
      
      if (this.forWrapping)
      {
        if ((iv == null) || (iv.length != 8))
        {
          throw new IllegalArgumentException("IV is not 8 octets");
        }
        
      }
      else {
        throw new IllegalArgumentException("You should not supply an IV for unwrapping");
      }
    }
  }
  






  public String getAlgorithmName()
  {
    return "DESede";
  }
  








  public byte[] wrap(byte[] in, int inOff, int inLen)
  {
    if (!forWrapping)
    {
      throw new IllegalStateException("Not initialized for wrapping");
    }
    
    byte[] keyToBeWrapped = new byte[inLen];
    
    System.arraycopy(in, inOff, keyToBeWrapped, 0, inLen);
    

    byte[] CKS = calculateCMSKeyChecksum(keyToBeWrapped);
    

    byte[] WKCKS = new byte[keyToBeWrapped.length + CKS.length];
    
    System.arraycopy(keyToBeWrapped, 0, WKCKS, 0, keyToBeWrapped.length);
    System.arraycopy(CKS, 0, WKCKS, keyToBeWrapped.length, CKS.length);
    



    int blockSize = engine.getBlockSize();
    
    if (WKCKS.length % blockSize != 0)
    {
      throw new IllegalStateException("Not multiple of block length");
    }
    
    engine.init(true, paramPlusIV);
    
    byte[] TEMP1 = new byte[WKCKS.length];
    
    for (int currentBytePos = 0; currentBytePos != WKCKS.length; currentBytePos += blockSize)
    {
      engine.processBlock(WKCKS, currentBytePos, TEMP1, currentBytePos);
    }
    

    byte[] TEMP2 = new byte[iv.length + TEMP1.length];
    
    System.arraycopy(iv, 0, TEMP2, 0, iv.length);
    System.arraycopy(TEMP1, 0, TEMP2, iv.length, TEMP1.length);
    

    byte[] TEMP3 = reverse(TEMP2);
    



    ParametersWithIV param2 = new ParametersWithIV(param, IV2);
    
    engine.init(true, param2);
    
    for (int currentBytePos = 0; currentBytePos != TEMP3.length; currentBytePos += blockSize)
    {
      engine.processBlock(TEMP3, currentBytePos, TEMP3, currentBytePos);
    }
    
    return TEMP3;
  }
  









  public byte[] unwrap(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forWrapping)
    {
      throw new IllegalStateException("Not set for unwrapping");
    }
    
    if (in == null)
    {
      throw new InvalidCipherTextException("Null pointer as ciphertext");
    }
    
    int blockSize = engine.getBlockSize();
    if (inLen % blockSize != 0)
    {
      throw new InvalidCipherTextException("Ciphertext not multiple of " + blockSize);
    }
    


















    ParametersWithIV param2 = new ParametersWithIV(param, IV2);
    
    engine.init(false, param2);
    
    byte[] TEMP3 = new byte[inLen];
    
    for (int currentBytePos = 0; currentBytePos != inLen; currentBytePos += blockSize)
    {
      engine.processBlock(in, inOff + currentBytePos, TEMP3, currentBytePos);
    }
    

    byte[] TEMP2 = reverse(TEMP3);
    

    iv = new byte[8];
    
    byte[] TEMP1 = new byte[TEMP2.length - 8];
    
    System.arraycopy(TEMP2, 0, iv, 0, 8);
    System.arraycopy(TEMP2, 8, TEMP1, 0, TEMP2.length - 8);
    


    paramPlusIV = new ParametersWithIV(param, iv);
    
    engine.init(false, paramPlusIV);
    
    byte[] WKCKS = new byte[TEMP1.length];
    
    for (int currentBytePos = 0; currentBytePos != WKCKS.length; currentBytePos += blockSize)
    {
      engine.processBlock(TEMP1, currentBytePos, WKCKS, currentBytePos);
    }
    


    byte[] result = new byte[WKCKS.length - 8];
    byte[] CKStoBeVerified = new byte[8];
    
    System.arraycopy(WKCKS, 0, result, 0, WKCKS.length - 8);
    System.arraycopy(WKCKS, WKCKS.length - 8, CKStoBeVerified, 0, 8);
    


    if (!checkCMSKeyChecksum(result, CKStoBeVerified))
    {
      throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
    }
    


    return result;
  }
  















  private byte[] calculateCMSKeyChecksum(byte[] key)
  {
    byte[] result = new byte[8];
    
    sha1.update(key, 0, key.length);
    sha1.doFinal(digest, 0);
    
    System.arraycopy(digest, 0, result, 0, 8);
    
    return result;
  }
  









  private boolean checkCMSKeyChecksum(byte[] key, byte[] checksum)
  {
    return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(key), checksum);
  }
  
  private static byte[] reverse(byte[] bs)
  {
    byte[] result = new byte[bs.length];
    for (int i = 0; i < bs.length; i++)
    {
      result[i] = bs[(bs.length - (i + 1))];
    }
    return result;
  }
}
