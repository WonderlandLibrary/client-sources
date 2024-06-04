package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.SRP6GroupParameters;





















public class SRP6Client
{
  protected BigInteger N;
  protected BigInteger g;
  protected BigInteger a;
  protected BigInteger A;
  protected BigInteger B;
  protected BigInteger x;
  protected BigInteger u;
  protected BigInteger S;
  protected BigInteger M1;
  protected BigInteger M2;
  protected BigInteger Key;
  protected Digest digest;
  protected SecureRandom random;
  
  public SRP6Client() {}
  
  public void init(BigInteger N, BigInteger g, Digest digest, SecureRandom random)
  {
    this.N = N;
    this.g = g;
    this.digest = digest;
    this.random = random;
  }
  
  public void init(SRP6GroupParameters group, Digest digest, SecureRandom random)
  {
    init(group.getN(), group.getG(), digest, random);
  }
  







  public BigInteger generateClientCredentials(byte[] salt, byte[] identity, byte[] password)
  {
    x = SRP6Util.calculateX(digest, N, salt, identity, password);
    a = selectPrivateValue();
    A = g.modPow(a, N);
    
    return A;
  }
  





  public BigInteger calculateSecret(BigInteger serverB)
    throws CryptoException
  {
    B = SRP6Util.validatePublicValue(N, serverB);
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
    BigInteger k = SRP6Util.calculateK(digest, N, g);
    BigInteger exp = u.multiply(x).add(a);
    BigInteger tmp = g.modPow(x, N).multiply(k).mod(N);
    return B.subtract(tmp).mod(N).modPow(exp, N);
  }
  






  public BigInteger calculateClientEvidenceMessage()
    throws CryptoException
  {
    if ((A == null) || (B == null) || (S == null))
    {
      throw new CryptoException("Impossible to compute M1: some data are missing from the previous operations (A,B,S)");
    }
    

    M1 = SRP6Util.calculateM1(digest, N, A, B, S);
    return M1;
  }
  





  public boolean verifyServerEvidenceMessage(BigInteger serverM2)
    throws CryptoException
  {
    if ((A == null) || (M1 == null) || (S == null))
    {
      throw new CryptoException("Impossible to compute and verify M2: some data are missing from the previous operations (A,M1,S)");
    }
    


    BigInteger computedM2 = SRP6Util.calculateM2(digest, N, A, M1, S);
    if (computedM2.equals(serverM2))
    {
      M2 = serverM2;
      return true;
    }
    return false;
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
