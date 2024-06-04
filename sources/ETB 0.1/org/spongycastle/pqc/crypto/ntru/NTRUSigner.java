package org.spongycastle.pqc.crypto.ntru;

import java.nio.ByteBuffer;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;











/**
 * @deprecated
 */
public class NTRUSigner
{
  private NTRUSigningParameters params;
  private Digest hashAlg;
  private NTRUSigningPrivateKeyParameters signingKeyPair;
  private NTRUSigningPublicKeyParameters verificationKey;
  
  public NTRUSigner(NTRUSigningParameters params)
  {
    this.params = params;
  }
  






  public void init(boolean forSigning, CipherParameters params)
  {
    if (forSigning)
    {
      signingKeyPair = ((NTRUSigningPrivateKeyParameters)params);
    }
    else
    {
      verificationKey = ((NTRUSigningPublicKeyParameters)params);
    }
    hashAlg = paramshashAlg;
    hashAlg.reset();
  }
  





  public void update(byte b)
  {
    if (hashAlg == null)
    {
      throw new IllegalStateException("Call initSign or initVerify first!");
    }
    
    hashAlg.update(b);
  }
  







  public void update(byte[] m, int off, int length)
  {
    if (hashAlg == null)
    {
      throw new IllegalStateException("Call initSign or initVerify first!");
    }
    
    hashAlg.update(m, off, length);
  }
  






  public byte[] generateSignature()
  {
    if ((hashAlg == null) || (signingKeyPair == null))
    {
      throw new IllegalStateException("Call initSign first!");
    }
    
    byte[] msgHash = new byte[hashAlg.getDigestSize()];
    
    hashAlg.doFinal(msgHash, 0);
    return signHash(msgHash, signingKeyPair);
  }
  
  private byte[] signHash(byte[] msgHash, NTRUSigningPrivateKeyParameters kp)
  {
    int r = 0;
    


    NTRUSigningPublicKeyParameters kPub = kp.getPublicKey();
    IntegerPolynomial i;
    IntegerPolynomial s;
    do { r++;
      if (r > params.signFailTolerance)
      {
        throw new IllegalStateException("Signing failed: too many retries (max=" + params.signFailTolerance + ")");
      }
      i = createMsgRep(msgHash, r);
      s = sign(i, kp);
    }
    while (!verify(i, s, h));
    
    byte[] rawSig = s.toBinary(params.q);
    ByteBuffer sbuf = ByteBuffer.allocate(rawSig.length + 4);
    sbuf.put(rawSig);
    sbuf.putInt(r);
    return sbuf.array();
  }
  
  private IntegerPolynomial sign(IntegerPolynomial i, NTRUSigningPrivateKeyParameters kp)
  {
    int N = params.N;
    int q = params.q;
    int perturbationBases = params.B;
    
    NTRUSigningPrivateKeyParameters kPriv = kp;
    NTRUSigningPublicKeyParameters kPub = kp.getPublicKey();
    
    IntegerPolynomial s = new IntegerPolynomial(N);
    int iLoop = perturbationBases;
    while (iLoop >= 1)
    {
      Polynomial f = getBasisf;
      Polynomial fPrime = getBasisfPrime;
      
      IntegerPolynomial y = f.mult(i);
      y.div(q);
      y = fPrime.mult(y);
      
      IntegerPolynomial x = fPrime.mult(i);
      x.div(q);
      x = f.mult(x);
      
      IntegerPolynomial si = y;
      si.sub(x);
      s.add(si);
      
      IntegerPolynomial hi = (IntegerPolynomial)getBasish.clone();
      if (iLoop > 1)
      {
        hi.sub(getBasis1h);
      }
      else
      {
        hi.sub(h);
      }
      i = si.mult(hi, q);
      
      iLoop--;
    }
    
    Polynomial f = getBasis0f;
    Polynomial fPrime = getBasis0fPrime;
    
    IntegerPolynomial y = f.mult(i);
    y.div(q);
    y = fPrime.mult(y);
    
    IntegerPolynomial x = fPrime.mult(i);
    x.div(q);
    x = f.mult(x);
    
    y.sub(x);
    s.add(y);
    s.modPositive(q);
    return s;
  }
  







  public boolean verifySignature(byte[] sig)
  {
    if ((hashAlg == null) || (verificationKey == null))
    {
      throw new IllegalStateException("Call initVerify first!");
    }
    
    byte[] msgHash = new byte[hashAlg.getDigestSize()];
    
    hashAlg.doFinal(msgHash, 0);
    
    return verifyHash(msgHash, sig, verificationKey);
  }
  
  private boolean verifyHash(byte[] msgHash, byte[] sig, NTRUSigningPublicKeyParameters pub)
  {
    ByteBuffer sbuf = ByteBuffer.wrap(sig);
    byte[] rawSig = new byte[sig.length - 4];
    sbuf.get(rawSig);
    IntegerPolynomial s = IntegerPolynomial.fromBinary(rawSig, params.N, params.q);
    int r = sbuf.getInt();
    return verify(createMsgRep(msgHash, r), s, h);
  }
  
  private boolean verify(IntegerPolynomial i, IntegerPolynomial s, IntegerPolynomial h)
  {
    int q = params.q;
    double normBoundSq = params.normBoundSq;
    double betaSq = params.betaSq;
    
    IntegerPolynomial t = h.mult(s, q);
    t.sub(i);
    long centeredNormSq = (s.centeredNormSq(q) + betaSq * t.centeredNormSq(q));
    return centeredNormSq <= normBoundSq;
  }
  
  protected IntegerPolynomial createMsgRep(byte[] msgHash, int r)
  {
    int N = params.N;
    int q = params.q;
    
    int c = 31 - Integer.numberOfLeadingZeros(q);
    int B = (c + 7) / 8;
    IntegerPolynomial i = new IntegerPolynomial(N);
    
    ByteBuffer cbuf = ByteBuffer.allocate(msgHash.length + 4);
    cbuf.put(msgHash);
    cbuf.putInt(r);
    NTRUSignerPrng prng = new NTRUSignerPrng(cbuf.array(), params.hashAlg);
    
    for (int t = 0; t < N; t++)
    {
      byte[] o = prng.nextBytes(B);
      int hi = o[(o.length - 1)];
      hi >>= 8 * B - c;
      hi <<= 8 * B - c;
      o[(o.length - 1)] = ((byte)hi);
      
      ByteBuffer obuf = ByteBuffer.allocate(4);
      obuf.put(o);
      obuf.rewind();
      
      coeffs[t] = Integer.reverseBytes(obuf.getInt());
    }
    return i;
  }
}
