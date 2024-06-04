package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.DigestFactory;








public class OpenSSLPBEParametersGenerator
  extends PBEParametersGenerator
{
  private Digest digest = DigestFactory.createMD5();
  







  public OpenSSLPBEParametersGenerator() {}
  






  public void init(byte[] password, byte[] salt)
  {
    super.init(password, salt, 1);
  }
  




  private byte[] generateDerivedKey(int bytesNeeded)
  {
    byte[] buf = new byte[digest.getDigestSize()];
    byte[] key = new byte[bytesNeeded];
    int offset = 0;
    
    for (;;)
    {
      digest.update(password, 0, password.length);
      digest.update(salt, 0, salt.length);
      
      digest.doFinal(buf, 0);
      
      int len = bytesNeeded > buf.length ? buf.length : bytesNeeded;
      System.arraycopy(buf, 0, key, offset, len);
      offset += len;
      

      bytesNeeded -= len;
      if (bytesNeeded == 0) {
        break;
      }
      


      digest.reset();
      digest.update(buf, 0, buf.length);
    }
    
    return key;
  }
  









  public CipherParameters generateDerivedParameters(int keySize)
  {
    keySize /= 8;
    
    byte[] dKey = generateDerivedKey(keySize);
    
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
