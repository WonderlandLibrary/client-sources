package org.spongycastle.crypto.signers;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;



public class GenericSigner
  implements Signer
{
  private final AsymmetricBlockCipher engine;
  private final Digest digest;
  private boolean forSigning;
  
  public GenericSigner(AsymmetricBlockCipher engine, Digest digest)
  {
    this.engine = engine;
    this.digest = digest;
  }
  










  public void init(boolean forSigning, CipherParameters parameters)
  {
    this.forSigning = forSigning;
    AsymmetricKeyParameter k;
    AsymmetricKeyParameter k;
    if ((parameters instanceof ParametersWithRandom))
    {
      k = (AsymmetricKeyParameter)((ParametersWithRandom)parameters).getParameters();
    }
    else
    {
      k = (AsymmetricKeyParameter)parameters;
    }
    
    if ((forSigning) && (!k.isPrivate()))
    {
      throw new IllegalArgumentException("signing requires private key");
    }
    
    if ((!forSigning) && (k.isPrivate()))
    {
      throw new IllegalArgumentException("verification requires public key");
    }
    
    reset();
    
    engine.init(forSigning, parameters);
  }
  




  public void update(byte input)
  {
    digest.update(input);
  }
  






  public void update(byte[] input, int inOff, int length)
  {
    digest.update(input, inOff, length);
  }
  




  public byte[] generateSignature()
    throws CryptoException, DataLengthException
  {
    if (!forSigning)
    {
      throw new IllegalStateException("GenericSigner not initialised for signature generation.");
    }
    
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    
    return engine.processBlock(hash, 0, hash.length);
  }
  





  public boolean verifySignature(byte[] signature)
  {
    if (forSigning)
    {
      throw new IllegalStateException("GenericSigner not initialised for verification");
    }
    
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    
    try
    {
      byte[] sig = engine.processBlock(signature, 0, signature.length);
      

      if (sig.length < hash.length)
      {
        byte[] tmp = new byte[hash.length];
        System.arraycopy(sig, 0, tmp, tmp.length - sig.length, sig.length);
        sig = tmp;
      }
      
      return Arrays.constantTimeAreEqual(sig, hash);
    }
    catch (Exception e) {}
    
    return false;
  }
  

  public void reset()
  {
    digest.reset();
  }
}
