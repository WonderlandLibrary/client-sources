package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.SRP6GroupParameters;














public class SRP6VerifierGenerator
{
  protected BigInteger N;
  protected BigInteger g;
  protected Digest digest;
  
  public SRP6VerifierGenerator() {}
  
  public void init(BigInteger N, BigInteger g, Digest digest)
  {
    this.N = N;
    this.g = g;
    this.digest = digest;
  }
  
  public void init(SRP6GroupParameters group, Digest digest)
  {
    N = group.getN();
    g = group.getG();
    this.digest = digest;
  }
  







  public BigInteger generateVerifier(byte[] salt, byte[] identity, byte[] password)
  {
    BigInteger x = SRP6Util.calculateX(digest, N, salt, identity, password);
    
    return g.modPow(x, N);
  }
}
