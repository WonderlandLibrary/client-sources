package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
















public class PKCS5S1ParametersGenerator
  extends PBEParametersGenerator
{
  private Digest digest;
  
  public PKCS5S1ParametersGenerator(Digest digest)
  {
    this.digest = digest;
  }
  



  private byte[] generateDerivedKey()
  {
    byte[] digestBytes = new byte[digest.getDigestSize()];
    
    digest.update(password, 0, password.length);
    digest.update(salt, 0, salt.length);
    
    digest.doFinal(digestBytes, 0);
    for (int i = 1; i < iterationCount; i++)
    {
      digest.update(digestBytes, 0, digestBytes.length);
      digest.doFinal(digestBytes, 0);
    }
    
    return digestBytes;
  }
  









  public CipherParameters generateDerivedParameters(int keySize)
  {
    keySize /= 8;
    
    if (keySize > digest.getDigestSize())
    {
      throw new IllegalArgumentException("Can't generate a derived key " + keySize + " bytes long.");
    }
    

    byte[] dKey = generateDerivedKey();
    
    return new KeyParameter(dKey, 0, keySize);
  }
  












  public CipherParameters generateDerivedParameters(int keySize, int ivSize)
  {
    keySize /= 8;
    ivSize /= 8;
    
    if (keySize + ivSize > digest.getDigestSize())
    {
      throw new IllegalArgumentException("Can't generate a derived key " + (keySize + ivSize) + " bytes long.");
    }
    

    byte[] dKey = generateDerivedKey();
    
    return new ParametersWithIV(new KeyParameter(dKey, 0, keySize), dKey, keySize, ivSize);
  }
  









  public CipherParameters generateDerivedMacParameters(int keySize)
  {
    return generateDerivedParameters(keySize);
  }
}
