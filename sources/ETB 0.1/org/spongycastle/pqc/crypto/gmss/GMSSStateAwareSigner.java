package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.util.Memoable;








public class GMSSStateAwareSigner
  implements StateAwareMessageSigner
{
  private final GMSSSigner gmssSigner;
  private GMSSPrivateKeyParameters key;
  
  public GMSSStateAwareSigner(Digest digest)
  {
    if (!(digest instanceof Memoable))
    {
      throw new IllegalArgumentException("digest must implement Memoable");
    }
    
    final Memoable dig = ((Memoable)digest).copy();
    gmssSigner = new GMSSSigner(new GMSSDigestProvider()
    {
      public Digest get()
      {
        return (Digest)dig.copy();
      }
    });
  }
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        key = ((GMSSPrivateKeyParameters)rParam.getParameters());
      }
      else
      {
        key = ((GMSSPrivateKeyParameters)param);
      }
    }
    
    gmssSigner.init(forSigning, param);
  }
  
  public byte[] generateSignature(byte[] message)
  {
    if (key == null)
    {
      throw new IllegalStateException("signing key no longer usable");
    }
    
    byte[] sig = gmssSigner.generateSignature(message);
    
    key = key.nextKey();
    
    return sig;
  }
  
  public boolean verifySignature(byte[] message, byte[] signature)
  {
    return gmssSigner.verifySignature(message, signature);
  }
  
  public AsymmetricKeyParameter getUpdatedPrivateKey()
  {
    AsymmetricKeyParameter k = key;
    
    key = null;
    
    return k;
  }
}
