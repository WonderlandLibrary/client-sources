package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;











public class RSAEngine
  implements AsymmetricBlockCipher
{
  private RSACoreEngine core;
  
  public RSAEngine() {}
  
  public void init(boolean forEncryption, CipherParameters param)
  {
    if (core == null)
    {
      core = new RSACoreEngine();
    }
    
    core.init(forEncryption, param);
  }
  







  public int getInputBlockSize()
  {
    return core.getInputBlockSize();
  }
  







  public int getOutputBlockSize()
  {
    return core.getOutputBlockSize();
  }
  












  public byte[] processBlock(byte[] in, int inOff, int inLen)
  {
    if (core == null)
    {
      throw new IllegalStateException("RSA engine not initialised");
    }
    
    return core.convertOutput(core.processBlock(core.convertInput(in, inOff, inLen)));
  }
}
