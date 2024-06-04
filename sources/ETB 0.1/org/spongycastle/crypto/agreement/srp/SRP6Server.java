package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.SRP6GroupParameters;





















public class SRP6Server
{
  protected BigInteger N;
  protected BigInteger g;
  protected BigInteger v;
  protected SecureRandom random;
  protected Digest digest;
  protected BigInteger A;
  protected BigInteger b;
  protected BigInteger B;
  protected BigInteger u;
  protected BigInteger S;
  protected BigInteger M1;
  protected BigInteger M2;
  protected BigInteger Key;
  
  public SRP6Server() {}
  
  public void init(BigInteger N, BigInteger g, BigInteger v, Digest digest, SecureRandom random)
  {
    this.N = N;
    this.g = g;
    this.v = v;
    
    this.random = random;
    this.digest = digest;
  }
  
  public void init(SRP6GroupParameters group, BigInteger v, Digest digest, SecureRandom random)
  {
    init(group.getN(), group.getG(), v, digest, random);
  }
  




  public BigInteger generateServerCredentials()
  {
    BigInteger k = SRP6Util.calculateK(digest, N, g);
    b = selectPrivateValue();
    B = k.multiply(v).mod(N).add(g.modPow(b, N)).mod(N);
    
    return B;
  }
  





  public BigInteger calculateSecret(BigInteger clientA)
    throws CryptoException
  {
    A = SRP6Util.validatePublicValue(N, clientA);
    u = SRP6Util.calculateU(digest, N, A, B);
    S = calculateS();
    
    return S;
  }
  
  protected BigInteger selectPrivateValue()
  {
    return SRP6Util.generatePrivateValue(digest, N, g, random);
  }
  
  private BigInteger calculateS()
  {
    return v.modPow(u, N).multiply(A).mod(N).modPow(b, N);
  }
  







  public boolean verifyClientEvidenceMessage(BigInteger clientM1)
    throws CryptoException
  {
    if ((A == null) || (B == null) || (S == null))
    {
      throw new CryptoException("Impossible to compute and verify M1: some data are missing from the previous operations (A,B,S)");
    }
    


    BigInteger computedM1 = SRP6Util.calculateM1(digest, N, A, B, S);
    if (computedM1.equals(clientM1))
    {
      M1 = clientM1;
      return true;
    }
    return false;
  }
  






  public BigInteger calculateServerEvidenceMessage()
    throws CryptoException
  {
    if ((A == null) || (M1 == null) || (S == null))
    {
      throw new CryptoException("Impossible to compute M2: some data are missing from the previous operations (A,M1,S)");
    }
    


    M2 = SRP6Util.calculateM2(digest, N, A, M1, S);
    return M2;
  }
  






  public BigInteger calculateSessionKey()
    throws CryptoException
  {
    if ((S == null) || (M1 == null) || (M2 == null))
    {
      throw new CryptoException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
    }
    
    Key = SRP6Util.calculateKey(digest, N, S);
    return Key;
  }
}
