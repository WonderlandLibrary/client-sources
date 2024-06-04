package org.spongycastle.crypto.encodings;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;







public class OAEPEncoding
  implements AsymmetricBlockCipher
{
  private byte[] defHash;
  private Digest mgf1Hash;
  private AsymmetricBlockCipher engine;
  private SecureRandom random;
  private boolean forEncryption;
  
  public OAEPEncoding(AsymmetricBlockCipher cipher)
  {
    this(cipher, DigestFactory.createSHA1(), null);
  }
  


  public OAEPEncoding(AsymmetricBlockCipher cipher, Digest hash)
  {
    this(cipher, hash, null);
  }
  



  public OAEPEncoding(AsymmetricBlockCipher cipher, Digest hash, byte[] encodingParams)
  {
    this(cipher, hash, hash, encodingParams);
  }
  




  public OAEPEncoding(AsymmetricBlockCipher cipher, Digest hash, Digest mgf1Hash, byte[] encodingParams)
  {
    engine = cipher;
    this.mgf1Hash = mgf1Hash;
    defHash = new byte[hash.getDigestSize()];
    
    hash.reset();
    
    if (encodingParams != null)
    {
      hash.update(encodingParams, 0, encodingParams.length);
    }
    
    hash.doFinal(defHash, 0);
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return engine;
  }
  


  public void init(boolean forEncryption, CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      random = rParam.getRandom();
    }
    else
    {
      random = new SecureRandom();
    }
    
    engine.init(forEncryption, param);
    
    this.forEncryption = forEncryption;
  }
  
  public int getInputBlockSize()
  {
    int baseBlockSize = engine.getInputBlockSize();
    
    if (forEncryption)
    {
      return baseBlockSize - 1 - 2 * defHash.length;
    }
    

    return baseBlockSize;
  }
  

  public int getOutputBlockSize()
  {
    int baseBlockSize = engine.getOutputBlockSize();
    
    if (forEncryption)
    {
      return baseBlockSize;
    }
    

    return baseBlockSize - 1 - 2 * defHash.length;
  }
  




  public byte[] processBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      return encodeBlock(in, inOff, inLen);
    }
    

    return decodeBlock(in, inOff, inLen);
  }
  




  public byte[] encodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (inLen > getInputBlockSize())
    {
      throw new DataLengthException("input data too long");
    }
    
    byte[] block = new byte[getInputBlockSize() + 1 + 2 * defHash.length];
    



    System.arraycopy(in, inOff, block, block.length - inLen, inLen);
    



    block[(block.length - inLen - 1)] = 1;
    







    System.arraycopy(defHash, 0, block, defHash.length, defHash.length);
    



    byte[] seed = new byte[defHash.length];
    
    random.nextBytes(seed);
    



    byte[] mask = maskGeneratorFunction1(seed, 0, seed.length, block.length - defHash.length);
    
    for (int i = defHash.length; i != block.length; i++)
    {
      int tmp138_136 = i; byte[] tmp138_134 = block;tmp138_134[tmp138_136] = ((byte)(tmp138_134[tmp138_136] ^ mask[(i - defHash.length)]));
    }
    



    System.arraycopy(seed, 0, block, 0, defHash.length);
    



    mask = maskGeneratorFunction1(block, defHash.length, block.length - defHash.length, defHash.length);
    

    for (int i = 0; i != defHash.length; i++)
    {
      int tmp218_216 = i; byte[] tmp218_214 = block;tmp218_214[tmp218_216] = ((byte)(tmp218_214[tmp218_216] ^ mask[i]));
    }
    
    return engine.processBlock(block, 0, block.length);
  }
  







  public byte[] decodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    byte[] data = engine.processBlock(in, inOff, inLen);
    byte[] block = new byte[engine.getOutputBlockSize()];
    






    System.arraycopy(data, 0, block, block.length - data.length, data.length);
    
    boolean shortData = block.length < 2 * defHash.length + 1;
    



    byte[] mask = maskGeneratorFunction1(block, defHash.length, block.length - defHash.length, defHash.length);
    

    for (int i = 0; i != defHash.length; i++)
    {
      int tmp111_109 = i; byte[] tmp111_107 = block;tmp111_107[tmp111_109] = ((byte)(tmp111_107[tmp111_109] ^ mask[i]));
    }
    



    mask = maskGeneratorFunction1(block, 0, defHash.length, block.length - defHash.length);
    
    for (int i = defHash.length; i != block.length; i++)
    {
      int tmp169_167 = i; byte[] tmp169_165 = block;tmp169_165[tmp169_167] = ((byte)(tmp169_165[tmp169_167] ^ mask[(i - defHash.length)]));
    }
    




    boolean defHashWrong = false;
    
    for (int i = 0; i != defHash.length; i++)
    {
      if (defHash[i] != block[(defHash.length + i)])
      {
        defHashWrong = true;
      }
    }
    



    int start = block.length;
    
    for (int index = 2 * defHash.length; index != block.length; index++)
    {
      if (((block[index] != 0 ? 1 : 0) & (start == block.length ? 1 : 0)) != 0)
      {
        start = index;
      }
    }
    
    boolean dataStartWrong = (start > block.length - 1 ? 1 : 0) | (block[start] != 1 ? 1 : 0);
    
    start++;
    
    if ((defHashWrong | shortData | dataStartWrong))
    {
      Arrays.fill(block, (byte)0);
      throw new InvalidCipherTextException("data wrong");
    }
    



    byte[] output = new byte[block.length - start];
    
    System.arraycopy(block, start, output, 0, output.length);
    
    return output;
  }
  





  private void ItoOSP(int i, byte[] sp)
  {
    sp[0] = ((byte)(i >>> 24));
    sp[1] = ((byte)(i >>> 16));
    sp[2] = ((byte)(i >>> 8));
    sp[3] = ((byte)(i >>> 0));
  }
  







  private byte[] maskGeneratorFunction1(byte[] Z, int zOff, int zLen, int length)
  {
    byte[] mask = new byte[length];
    byte[] hashBuf = new byte[mgf1Hash.getDigestSize()];
    byte[] C = new byte[4];
    int counter = 0;
    
    mgf1Hash.reset();
    
    while (counter < length / hashBuf.length)
    {
      ItoOSP(counter, C);
      
      mgf1Hash.update(Z, zOff, zLen);
      mgf1Hash.update(C, 0, C.length);
      mgf1Hash.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * hashBuf.length, hashBuf.length);
      
      counter++;
    }
    
    if (counter * hashBuf.length < length)
    {
      ItoOSP(counter, C);
      
      mgf1Hash.update(Z, zOff, zLen);
      mgf1Hash.update(C, 0, C.length);
      mgf1Hash.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, mask, counter * hashBuf.length, mask.length - counter * hashBuf.length);
    }
    
    return mask;
  }
}
