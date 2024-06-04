package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.KGCMBlockCipher;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
















public class KGMac
  implements Mac
{
  private final KGCMBlockCipher cipher;
  private final int macSizeBits;
  
  public KGMac(KGCMBlockCipher cipher)
  {
    this.cipher = cipher;
    macSizeBits = (cipher.getUnderlyingCipher().getBlockSize() * 8);
  }
  









  public KGMac(KGCMBlockCipher cipher, int macSizeBits)
  {
    this.cipher = cipher;
    this.macSizeBits = macSizeBits;
  }
  



  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV param = (ParametersWithIV)params;
      
      byte[] iv = param.getIV();
      KeyParameter keyParam = (KeyParameter)param.getParameters();
      

      cipher.init(true, new AEADParameters(keyParam, macSizeBits, iv));
    }
    else
    {
      throw new IllegalArgumentException("KGMAC requires ParametersWithIV");
    }
  }
  
  public String getAlgorithmName()
  {
    return cipher.getUnderlyingCipher().getAlgorithmName() + "-KGMAC";
  }
  
  public int getMacSize()
  {
    return macSizeBits / 8;
  }
  
  public void update(byte in) throws IllegalStateException
  {
    cipher.processAADByte(in);
  }
  
  public void update(byte[] in, int inOff, int len)
    throws DataLengthException, IllegalStateException
  {
    cipher.processAADBytes(in, inOff, len);
  }
  
  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    try
    {
      return cipher.doFinal(out, outOff);

    }
    catch (InvalidCipherTextException e)
    {
      throw new IllegalStateException(e.toString());
    }
  }
  
  public void reset()
  {
    cipher.reset();
  }
}
