package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;










public class DigestingStateAwareMessageSigner
  extends DigestingMessageSigner
{
  private final StateAwareMessageSigner signer;
  
  public DigestingStateAwareMessageSigner(StateAwareMessageSigner messSigner, Digest messDigest)
  {
    super(messSigner, messDigest);
    
    signer = messSigner;
  }
  








  public AsymmetricKeyParameter getUpdatedPrivateKey()
  {
    return signer.getUpdatedPrivateKey();
  }
}
