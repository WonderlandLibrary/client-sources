package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.prng.DigestRandomGenerator;
import org.spongycastle.pqc.crypto.MessageEncryptor;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;


















public class McEliecePointchevalCipher
  implements MessageEncryptor
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.2";
  private Digest messDigest;
  private SecureRandom sr;
  private int n;
  private int k;
  private int t;
  McElieceCCA2KeyParameters key;
  private boolean forEncryption;
  
  public McEliecePointchevalCipher() {}
  
  public void init(boolean forEncryption, CipherParameters param)
  {
    this.forEncryption = forEncryption;
    
    if (forEncryption)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        sr = rParam.getRandom();
        key = ((McElieceCCA2PublicKeyParameters)rParam.getParameters());
        initCipherEncrypt((McElieceCCA2PublicKeyParameters)key);

      }
      else
      {
        sr = new SecureRandom();
        key = ((McElieceCCA2PublicKeyParameters)param);
        initCipherEncrypt((McElieceCCA2PublicKeyParameters)key);
      }
    }
    else
    {
      key = ((McElieceCCA2PrivateKeyParameters)param);
      initCipherDecrypt((McElieceCCA2PrivateKeyParameters)key);
    }
  }
  









  public int getKeySize(McElieceCCA2KeyParameters key)
    throws IllegalArgumentException
  {
    if ((key instanceof McElieceCCA2PublicKeyParameters))
    {
      return ((McElieceCCA2PublicKeyParameters)key).getN();
    }
    
    if ((key instanceof McElieceCCA2PrivateKeyParameters))
    {
      return ((McElieceCCA2PrivateKeyParameters)key).getN();
    }
    throw new IllegalArgumentException("unsupported type");
  }
  


  protected int decryptOutputSize(int inLen)
  {
    return 0;
  }
  
  protected int encryptOutputSize(int inLen)
  {
    return 0;
  }
  

  private void initCipherEncrypt(McElieceCCA2PublicKeyParameters pubKey)
  {
    sr = (sr != null ? sr : new SecureRandom());
    messDigest = Utils.getDigest(pubKey.getDigest());
    n = pubKey.getN();
    k = pubKey.getK();
    t = pubKey.getT();
  }
  
  private void initCipherDecrypt(McElieceCCA2PrivateKeyParameters privKey)
  {
    messDigest = Utils.getDigest(privKey.getDigest());
    n = privKey.getN();
    k = privKey.getK();
    t = privKey.getT();
  }
  
  public byte[] messageEncrypt(byte[] input)
  {
    if (!forEncryption)
    {
      throw new IllegalStateException("cipher initialised for decryption");
    }
    
    int kDiv8 = k >> 3;
    

    byte[] r = new byte[kDiv8];
    sr.nextBytes(r);
    

    GF2Vector rPrime = new GF2Vector(k, sr);
    

    byte[] rPrimeBytes = rPrime.getEncoded();
    

    byte[] mr = ByteUtils.concatenate(input, r);
    

    messDigest.update(mr, 0, mr.length);
    byte[] hmr = new byte[messDigest.getDigestSize()];
    messDigest.doFinal(hmr, 0);
    


    GF2Vector z = Conversions.encode(n, t, hmr);
    


    byte[] c1 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters)key, rPrime, z).getEncoded();
    

    DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
    

    sr0.addSeedMaterial(rPrimeBytes);
    

    byte[] c2 = new byte[input.length + kDiv8];
    sr0.nextBytes(c2);
    

    for (int i = 0; i < input.length; i++)
    {
      int tmp194_192 = i; byte[] tmp194_190 = c2;tmp194_190[tmp194_192] = ((byte)(tmp194_190[tmp194_192] ^ input[i]));
    }
    
    for (int i = 0; i < kDiv8; i++)
    {
      int tmp225_224 = (input.length + i); byte[] tmp225_218 = c2;tmp225_218[tmp225_224] = ((byte)(tmp225_218[tmp225_224] ^ r[i]));
    }
    

    return ByteUtils.concatenate(c1, c2);
  }
  
  public byte[] messageDecrypt(byte[] input)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      throw new IllegalStateException("cipher initialised for decryption");
    }
    
    int c1Len = n + 7 >> 3;
    int c2Len = input.length - c1Len;
    

    byte[][] c1c2 = ByteUtils.split(input, c1Len);
    byte[] c1 = c1c2[0];
    byte[] c2 = c1c2[1];
    

    GF2Vector c1Vec = GF2Vector.OS2VP(n, c1);
    GF2Vector[] c1Dec = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters)key, c1Vec);
    
    byte[] rPrimeBytes = c1Dec[0].getEncoded();
    
    GF2Vector z = c1Dec[1];
    

    DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
    

    sr0.addSeedMaterial(rPrimeBytes);
    

    byte[] mrBytes = new byte[c2Len];
    sr0.nextBytes(mrBytes);
    

    for (int i = 0; i < c2Len; i++)
    {
      int tmp139_137 = i; byte[] tmp139_135 = mrBytes;tmp139_135[tmp139_137] = ((byte)(tmp139_135[tmp139_137] ^ c2[i]));
    }
    

    messDigest.update(mrBytes, 0, mrBytes.length);
    byte[] hmr = new byte[messDigest.getDigestSize()];
    messDigest.doFinal(hmr, 0);
    

    c1Vec = Conversions.encode(n, t, hmr);
    

    if (!c1Vec.equals(z))
    {
      throw new InvalidCipherTextException("Bad Padding: Invalid ciphertext.");
    }
    

    int kDiv8 = k >> 3;
    byte[][] mr = ByteUtils.split(mrBytes, c2Len - kDiv8);
    

    return mr[0];
  }
}
