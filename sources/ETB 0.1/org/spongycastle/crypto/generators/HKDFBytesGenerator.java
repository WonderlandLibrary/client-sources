package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.HKDFParameters;
import org.spongycastle.crypto.params.KeyParameter;
















public class HKDFBytesGenerator
  implements DerivationFunction
{
  private HMac hMacHash;
  private int hashLen;
  private byte[] info;
  private byte[] currentT;
  private int generatedBytes;
  
  public HKDFBytesGenerator(Digest hash)
  {
    hMacHash = new HMac(hash);
    hashLen = hash.getDigestSize();
  }
  
  public void init(DerivationParameters param)
  {
    if (!(param instanceof HKDFParameters))
    {
      throw new IllegalArgumentException("HKDF parameters required for HKDFBytesGenerator");
    }
    

    HKDFParameters params = (HKDFParameters)param;
    if (params.skipExtract())
    {

      hMacHash.init(new KeyParameter(params.getIKM()));
    }
    else
    {
      hMacHash.init(extract(params.getSalt(), params.getIKM()));
    }
    
    info = params.getInfo();
    
    generatedBytes = 0;
    currentT = new byte[hashLen];
  }
  







  private KeyParameter extract(byte[] salt, byte[] ikm)
  {
    if (salt == null)
    {

      hMacHash.init(new KeyParameter(new byte[hashLen]));
    }
    else
    {
      hMacHash.init(new KeyParameter(salt));
    }
    
    hMacHash.update(ikm, 0, ikm.length);
    
    byte[] prk = new byte[hashLen];
    hMacHash.doFinal(prk, 0);
    return new KeyParameter(prk);
  }
  







  private void expandNext()
    throws DataLengthException
  {
    int n = generatedBytes / hashLen + 1;
    if (n >= 256)
    {
      throw new DataLengthException("HKDF cannot generate more than 255 blocks of HashLen size");
    }
    

    if (generatedBytes != 0)
    {
      hMacHash.update(currentT, 0, hashLen);
    }
    hMacHash.update(info, 0, info.length);
    hMacHash.update((byte)n);
    hMacHash.doFinal(currentT, 0);
  }
  
  public Digest getDigest()
  {
    return hMacHash.getUnderlyingDigest();
  }
  

  public int generateBytes(byte[] out, int outOff, int len)
    throws DataLengthException, IllegalArgumentException
  {
    if (generatedBytes + len > 255 * hashLen)
    {
      throw new DataLengthException("HKDF may only be used for 255 * HashLen bytes of output");
    }
    

    if (generatedBytes % hashLen == 0)
    {
      expandNext();
    }
    

    int toGenerate = len;
    int posInT = generatedBytes % hashLen;
    int leftInT = hashLen - generatedBytes % hashLen;
    int toCopy = Math.min(leftInT, toGenerate);
    System.arraycopy(currentT, posInT, out, outOff, toCopy);
    generatedBytes += toCopy;
    toGenerate -= toCopy;
    outOff += toCopy;
    
    while (toGenerate > 0)
    {
      expandNext();
      toCopy = Math.min(hashLen, toGenerate);
      System.arraycopy(currentT, 0, out, outOff, toCopy);
      generatedBytes += toCopy;
      toGenerate -= toCopy;
      outOff += toCopy;
    }
    
    return len;
  }
}
