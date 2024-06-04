package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSABlindingParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;









public class RSABlindingEngine
  implements AsymmetricBlockCipher
{
  private RSACoreEngine core = new RSACoreEngine();
  

  private RSAKeyParameters key;
  
  private BigInteger blindingFactor;
  
  private boolean forEncryption;
  

  public RSABlindingEngine() {}
  

  public void init(boolean forEncryption, CipherParameters param)
  {
    RSABlindingParameters p;
    
    RSABlindingParameters p;
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      p = (RSABlindingParameters)rParam.getParameters();
    }
    else
    {
      p = (RSABlindingParameters)param;
    }
    
    core.init(forEncryption, p.getPublicKey());
    
    this.forEncryption = forEncryption;
    key = p.getPublicKey();
    blindingFactor = p.getBlindingFactor();
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
    BigInteger msg = core.convertInput(in, inOff, inLen);
    
    if (forEncryption)
    {
      msg = blindMessage(msg);
    }
    else
    {
      msg = unblindMessage(msg);
    }
    
    return core.convertOutput(msg);
  }
  




  private BigInteger blindMessage(BigInteger msg)
  {
    BigInteger blindMsg = blindingFactor;
    blindMsg = msg.multiply(blindMsg.modPow(key.getExponent(), key.getModulus()));
    blindMsg = blindMsg.mod(key.getModulus());
    
    return blindMsg;
  }
  




  private BigInteger unblindMessage(BigInteger blindedMsg)
  {
    BigInteger m = key.getModulus();
    BigInteger msg = blindedMsg;
    BigInteger blindFactorInverse = blindingFactor.modInverse(m);
    msg = msg.multiply(blindFactorInverse);
    msg = msg.mod(m);
    
    return msg;
  }
}
