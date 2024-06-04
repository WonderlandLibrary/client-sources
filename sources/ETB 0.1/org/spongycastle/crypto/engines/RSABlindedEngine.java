package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.util.BigIntegers;






public class RSABlindedEngine
  implements AsymmetricBlockCipher
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  
  private RSACoreEngine core = new RSACoreEngine();
  

  private RSAKeyParameters key;
  

  private SecureRandom random;
  

  public RSABlindedEngine() {}
  

  public void init(boolean forEncryption, CipherParameters param)
  {
    core.init(forEncryption, param);
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      key = ((RSAKeyParameters)rParam.getParameters());
      random = rParam.getRandom();
    }
    else
    {
      key = ((RSAKeyParameters)param);
      random = new SecureRandom();
    }
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
    if (key == null)
    {
      throw new IllegalStateException("RSA engine not initialised");
    }
    
    BigInteger input = core.convertInput(in, inOff, inLen);
    BigInteger result;
    BigInteger result;
    if ((key instanceof RSAPrivateCrtKeyParameters))
    {
      RSAPrivateCrtKeyParameters k = (RSAPrivateCrtKeyParameters)key;
      
      BigInteger e = k.getPublicExponent();
      if (e != null)
      {
        BigInteger m = k.getModulus();
        BigInteger r = BigIntegers.createRandomInRange(ONE, m.subtract(ONE), random);
        
        BigInteger blindedInput = r.modPow(e, m).multiply(input).mod(m);
        BigInteger blindedResult = core.processBlock(blindedInput);
        
        BigInteger rInv = r.modInverse(m);
        BigInteger result = blindedResult.multiply(rInv).mod(m);
        
        if (!input.equals(result.modPow(e, m)))
        {
          throw new IllegalStateException("RSA engine faulty decryption/signing detected");
        }
      }
      else
      {
        result = core.processBlock(input);
      }
    }
    else
    {
      result = core.processBlock(input);
    }
    
    return core.convertOutput(result);
  }
}
