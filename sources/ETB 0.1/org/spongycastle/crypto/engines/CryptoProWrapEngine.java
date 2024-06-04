package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.modes.GCFBBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.util.Pack;

public class CryptoProWrapEngine extends GOST28147WrapEngine
{
  public CryptoProWrapEngine() {}
  
  public void init(boolean forWrapping, CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom pr = (ParametersWithRandom)param;
      param = pr.getParameters();
    }
    
    ParametersWithUKM pU = (ParametersWithUKM)param;
    byte[] sBox = null;
    



    if ((pU.getParameters() instanceof ParametersWithSBox))
    {
      KeyParameter kParam = (KeyParameter)((ParametersWithSBox)pU.getParameters()).getParameters();
      sBox = ((ParametersWithSBox)pU.getParameters()).getSBox();
    }
    else
    {
      kParam = (KeyParameter)pU.getParameters();
    }
    
    KeyParameter kParam = new KeyParameter(cryptoProDiversify(kParam.getKey(), pU.getUKM(), sBox));
    
    if (sBox != null)
    {
      super.init(forWrapping, new ParametersWithUKM(new ParametersWithSBox(kParam, sBox), pU.getUKM()));
    }
    else
    {
      super.init(forWrapping, new ParametersWithUKM(kParam, pU.getUKM()));
    }
  }
  





















  private static byte[] cryptoProDiversify(byte[] K, byte[] ukm, byte[] sBox)
  {
    for (int i = 0; i != 8; i++)
    {
      int sOn = 0;
      int sOff = 0;
      for (int j = 0; j != 8; j++)
      {
        int kj = Pack.littleEndianToInt(K, j * 4);
        if (bitSet(ukm[i], j))
        {
          sOn += kj;
        }
        else
        {
          sOff += kj;
        }
      }
      
      byte[] s = new byte[8];
      Pack.intToLittleEndian(sOn, s, 0);
      Pack.intToLittleEndian(sOff, s, 4);
      
      GCFBBlockCipher c = new GCFBBlockCipher(new GOST28147Engine());
      
      c.init(true, new org.spongycastle.crypto.params.ParametersWithIV(new ParametersWithSBox(new KeyParameter(K), sBox), s));
      
      c.processBlock(K, 0, K, 0);
      c.processBlock(K, 8, K, 8);
      c.processBlock(K, 16, K, 16);
      c.processBlock(K, 24, K, 24);
    }
    
    return K;
  }
  
  private static boolean bitSet(byte v, int bitNo)
  {
    return (v & 1 << bitNo) != 0;
  }
}
