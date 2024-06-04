package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

















public class RC2WrapEngine
  implements Wrapper
{
  private CBCBlockCipher engine;
  private CipherParameters param;
  private ParametersWithIV paramPlusIV;
  private byte[] iv;
  private boolean forWrapping;
  private SecureRandom sr;
  private static final byte[] IV2 = { 74, -35, -94, 44, 121, -24, 33, 5 };
  





  Digest sha1 = DigestFactory.createSHA1();
  byte[] digest = new byte[20];
  


  public RC2WrapEngine() {}
  


  public void init(boolean forWrapping, CipherParameters param)
  {
    this.forWrapping = forWrapping;
    engine = new CBCBlockCipher(new RC2Engine());
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom pWithR = (ParametersWithRandom)param;
      sr = pWithR.getRandom();
      param = pWithR.getParameters();
    }
    else
    {
      sr = new SecureRandom();
    }
    
    if ((param instanceof ParametersWithIV))
    {
      paramPlusIV = ((ParametersWithIV)param);
      iv = paramPlusIV.getIV();
      this.param = paramPlusIV.getParameters();
      
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
    else
    {
      this.param = param;
      
      if (this.forWrapping)
      {



        iv = new byte[8];
        
        sr.nextBytes(iv);
        
        paramPlusIV = new ParametersWithIV(this.param, iv);
      }
    }
  }
  






  public String getAlgorithmName()
  {
    return "RC2";
  }
  









  public byte[] wrap(byte[] in, int inOff, int inLen)
  {
    if (!forWrapping)
    {
      throw new IllegalStateException("Not initialized for wrapping");
    }
    
    int length = inLen + 1;
    if (length % 8 != 0)
    {
      length += 8 - length % 8;
    }
    
    byte[] keyToBeWrapped = new byte[length];
    
    keyToBeWrapped[0] = ((byte)inLen);
    System.arraycopy(in, inOff, keyToBeWrapped, 1, inLen);
    
    byte[] pad = new byte[keyToBeWrapped.length - inLen - 1];
    
    if (pad.length > 0)
    {
      sr.nextBytes(pad);
      System.arraycopy(pad, 0, keyToBeWrapped, inLen + 1, pad.length);
    }
    

    byte[] CKS = calculateCMSKeyChecksum(keyToBeWrapped);
    

    byte[] WKCKS = new byte[keyToBeWrapped.length + CKS.length];
    
    System.arraycopy(keyToBeWrapped, 0, WKCKS, 0, keyToBeWrapped.length);
    System.arraycopy(CKS, 0, WKCKS, keyToBeWrapped.length, CKS.length);
    


    byte[] TEMP1 = new byte[WKCKS.length];
    
    System.arraycopy(WKCKS, 0, TEMP1, 0, WKCKS.length);
    
    int noOfBlocks = WKCKS.length / engine.getBlockSize();
    int extraBytes = WKCKS.length % engine.getBlockSize();
    
    if (extraBytes != 0)
    {
      throw new IllegalStateException("Not multiple of block length");
    }
    
    engine.init(true, paramPlusIV);
    
    for (int i = 0; i < noOfBlocks; i++)
    {
      int currentBytePos = i * engine.getBlockSize();
      
      engine.processBlock(TEMP1, currentBytePos, TEMP1, currentBytePos);
    }
    

    byte[] TEMP2 = new byte[iv.length + TEMP1.length];
    
    System.arraycopy(iv, 0, TEMP2, 0, iv.length);
    System.arraycopy(TEMP1, 0, TEMP2, iv.length, TEMP1.length);
    

    byte[] TEMP3 = new byte[TEMP2.length];
    
    for (int i = 0; i < TEMP2.length; i++)
    {
      TEMP3[i] = TEMP2[(TEMP2.length - (i + 1))];
    }
    




    ParametersWithIV param2 = new ParametersWithIV(param, IV2);
    
    engine.init(true, param2);
    
    for (int i = 0; i < noOfBlocks + 1; i++)
    {
      int currentBytePos = i * engine.getBlockSize();
      
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
    
    if (inLen % engine.getBlockSize() != 0)
    {

      throw new InvalidCipherTextException("Ciphertext not multiple of " + engine.getBlockSize());
    }
    
















    ParametersWithIV param2 = new ParametersWithIV(param, IV2);
    
    engine.init(false, param2);
    
    byte[] TEMP3 = new byte[inLen];
    
    System.arraycopy(in, inOff, TEMP3, 0, inLen);
    
    for (int i = 0; i < TEMP3.length / engine.getBlockSize(); i++)
    {
      int currentBytePos = i * engine.getBlockSize();
      
      engine.processBlock(TEMP3, currentBytePos, TEMP3, currentBytePos);
    }
    

    byte[] TEMP2 = new byte[TEMP3.length];
    
    for (int i = 0; i < TEMP3.length; i++)
    {
      TEMP2[i] = TEMP3[(TEMP3.length - (i + 1))];
    }
    


    iv = new byte[8];
    
    byte[] TEMP1 = new byte[TEMP2.length - 8];
    
    System.arraycopy(TEMP2, 0, iv, 0, 8);
    System.arraycopy(TEMP2, 8, TEMP1, 0, TEMP2.length - 8);
    


    paramPlusIV = new ParametersWithIV(param, iv);
    
    engine.init(false, paramPlusIV);
    
    byte[] LCEKPADICV = new byte[TEMP1.length];
    
    System.arraycopy(TEMP1, 0, LCEKPADICV, 0, TEMP1.length);
    
    for (int i = 0; i < LCEKPADICV.length / engine.getBlockSize(); i++)
    {
      int currentBytePos = i * engine.getBlockSize();
      
      engine.processBlock(LCEKPADICV, currentBytePos, LCEKPADICV, currentBytePos);
    }
    




    byte[] result = new byte[LCEKPADICV.length - 8];
    byte[] CKStoBeVerified = new byte[8];
    
    System.arraycopy(LCEKPADICV, 0, result, 0, LCEKPADICV.length - 8);
    System.arraycopy(LCEKPADICV, LCEKPADICV.length - 8, CKStoBeVerified, 0, 8);
    





    if (!checkCMSKeyChecksum(result, CKStoBeVerified))
    {
      throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
    }
    

    if (result.length - ((result[0] & 0xFF) + 1) > 7)
    {
      throw new InvalidCipherTextException("too many pad bytes (" + (result.length - ((result[0] & 0xFF) + 1)) + ")");
    }
    


    byte[] CEK = new byte[result[0]];
    System.arraycopy(result, 1, CEK, 0, CEK.length);
    return CEK;
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
}
