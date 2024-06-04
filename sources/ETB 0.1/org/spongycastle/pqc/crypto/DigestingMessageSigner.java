package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;





public class DigestingMessageSigner
  implements Signer
{
  private final Digest messDigest;
  private final MessageSigner messSigner;
  private boolean forSigning;
  
  public DigestingMessageSigner(MessageSigner messSigner, Digest messDigest)
  {
    this.messSigner = messSigner;
    this.messDigest = messDigest;
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    this.forSigning = forSigning;
    AsymmetricKeyParameter k;
    AsymmetricKeyParameter k;
    if ((param instanceof ParametersWithRandom))
    {
      k = (AsymmetricKeyParameter)((ParametersWithRandom)param).getParameters();
    }
    else
    {
      k = (AsymmetricKeyParameter)param;
    }
    
    if ((forSigning) && (!k.isPrivate()))
    {
      throw new IllegalArgumentException("Signing Requires Private Key.");
    }
    
    if ((!forSigning) && (k.isPrivate()))
    {
      throw new IllegalArgumentException("Verification Requires Public Key.");
    }
    
    reset();
    
    messSigner.init(forSigning, param);
  }
  







  public byte[] generateSignature()
  {
    if (!forSigning)
    {
      throw new IllegalStateException("DigestingMessageSigner not initialised for signature generation.");
    }
    
    byte[] hash = new byte[messDigest.getDigestSize()];
    messDigest.doFinal(hash, 0);
    
    return messSigner.generateSignature(hash);
  }
  
  public void update(byte b)
  {
    messDigest.update(b);
  }
  
  public void update(byte[] in, int off, int len)
  {
    messDigest.update(in, off, len);
  }
  
  public void reset()
  {
    messDigest.reset();
  }
  







  public boolean verifySignature(byte[] signature)
  {
    if (forSigning)
    {
      throw new IllegalStateException("DigestingMessageSigner not initialised for verification");
    }
    
    byte[] hash = new byte[messDigest.getDigestSize()];
    messDigest.doFinal(hash, 0);
    
    return messSigner.verifySignature(hash, signature);
  }
}
