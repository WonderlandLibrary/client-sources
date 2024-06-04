package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;












public class PKCS5S2ParametersGenerator
  extends PBEParametersGenerator
{
  private Mac hMac;
  private byte[] state;
  
  public PKCS5S2ParametersGenerator()
  {
    this(DigestFactory.createSHA1());
  }
  
  public PKCS5S2ParametersGenerator(Digest digest)
  {
    hMac = new HMac(digest);
    state = new byte[hMac.getMacSize()];
  }
  





  private void F(byte[] S, int c, byte[] iBuf, byte[] out, int outOff)
  {
    if (c == 0)
    {
      throw new IllegalArgumentException("iteration count must be at least 1.");
    }
    
    if (S != null)
    {
      hMac.update(S, 0, S.length);
    }
    
    hMac.update(iBuf, 0, iBuf.length);
    hMac.doFinal(state, 0);
    
    System.arraycopy(state, 0, out, outOff, state.length);
    
    for (int count = 1; count < c; count++)
    {
      hMac.update(state, 0, state.length);
      hMac.doFinal(state, 0);
      
      for (int j = 0; j != state.length; j++)
      {
        int tmp139_138 = (outOff + j); byte[] tmp139_132 = out;tmp139_132[tmp139_138] = ((byte)(tmp139_132[tmp139_138] ^ state[j]));
      }
    }
  }
  

  private byte[] generateDerivedKey(int dkLen)
  {
    int hLen = hMac.getMacSize();
    int l = (dkLen + hLen - 1) / hLen;
    byte[] iBuf = new byte[4];
    byte[] outBytes = new byte[l * hLen];
    int outPos = 0;
    
    CipherParameters param = new KeyParameter(password);
    
    hMac.init(param);
    
    for (int i = 1; i <= l; i++)
    {

      int pos = 3;
      for (;;) { int tmp73_71 = pos;iBuf;
        


























































































        if ((tmp73_69[tmp73_71] = (byte)(tmp73_69[tmp73_71] + 1)) != 0)
          break;
        pos--;
      }
      
      F(salt, iterationCount, iBuf, outBytes, outPos);
      outPos += hLen;
    }
    
    return outBytes;
  }
  








  public CipherParameters generateDerivedParameters(int keySize)
  {
    keySize /= 8;
    
    byte[] dKey = Arrays.copyOfRange(generateDerivedKey(keySize), 0, keySize);
    
    return new KeyParameter(dKey, 0, keySize);
  }
  











  public CipherParameters generateDerivedParameters(int keySize, int ivSize)
  {
    keySize /= 8;
    ivSize /= 8;
    
    byte[] dKey = generateDerivedKey(keySize + ivSize);
    
    return new ParametersWithIV(new KeyParameter(dKey, 0, keySize), dKey, keySize, ivSize);
  }
  








  public CipherParameters generateDerivedMacParameters(int keySize)
  {
    return generateDerivedParameters(keySize);
  }
}
