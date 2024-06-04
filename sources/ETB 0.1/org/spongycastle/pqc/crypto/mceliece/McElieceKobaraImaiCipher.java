package org.spongycastle.pqc.crypto.mceliece;

import java.math.BigInteger;
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
import org.spongycastle.pqc.math.linearalgebra.IntegerFunctions;
















public class McElieceKobaraImaiCipher
  implements MessageEncryptor
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.3";
  private static final String DEFAULT_PRNG_NAME = "SHA1PRNG";
  public static final byte[] PUBLIC_CONSTANT = "a predetermined public constant"
    .getBytes();
  
  private Digest messDigest;
  
  private SecureRandom sr;
  
  McElieceCCA2KeyParameters key;
  
  private int n;
  
  private int k;
  
  private int t;
  private boolean forEncryption;
  
  public McElieceKobaraImaiCipher() {}
  
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
  
  private void initCipherEncrypt(McElieceCCA2PublicKeyParameters pubKey)
  {
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
    
    int c2Len = messDigest.getDigestSize();
    int c4Len = k >> 3;
    int c5Len = IntegerFunctions.binomial(n, t).bitLength() - 1 >> 3;
    

    int mLen = c4Len + c5Len - c2Len - PUBLIC_CONSTANT.length;
    if (input.length > mLen)
    {
      mLen = input.length;
    }
    
    int c1Len = mLen + PUBLIC_CONSTANT.length;
    int c6Len = c1Len + c2Len - c4Len - c5Len;
    

    byte[] mConst = new byte[c1Len];
    System.arraycopy(input, 0, mConst, 0, input.length);
    System.arraycopy(PUBLIC_CONSTANT, 0, mConst, mLen, PUBLIC_CONSTANT.length);
    


    byte[] r = new byte[c2Len];
    sr.nextBytes(r);
    


    DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
    

    sr0.addSeedMaterial(r);
    

    byte[] c1 = new byte[c1Len];
    sr0.nextBytes(c1);
    

    for (int i = c1Len - 1; i >= 0; i--)
    {
      int tmp194_192 = i; byte[] tmp194_190 = c1;tmp194_190[tmp194_192] = ((byte)(tmp194_190[tmp194_192] ^ mConst[i]));
    }
    

    byte[] c2 = new byte[messDigest.getDigestSize()];
    messDigest.update(c1, 0, c1.length);
    messDigest.doFinal(c2, 0);
    

    for (int i = c2Len - 1; i >= 0; i--)
    {
      int tmp265_263 = i; byte[] tmp265_261 = c2;tmp265_261[tmp265_263] = ((byte)(tmp265_261[tmp265_263] ^ r[i]));
    }
    

    byte[] c2c1 = ByteUtils.concatenate(c2, c1);
    



    byte[] c6 = new byte[0];
    if (c6Len > 0)
    {
      c6 = new byte[c6Len];
      System.arraycopy(c2c1, 0, c6, 0, c6Len);
    }
    
    byte[] c5 = new byte[c5Len];
    System.arraycopy(c2c1, c6Len, c5, 0, c5Len);
    
    byte[] c4 = new byte[c4Len];
    System.arraycopy(c2c1, c6Len + c5Len, c4, 0, c4Len);
    

    GF2Vector c4Vec = GF2Vector.OS2VP(k, c4);
    

    GF2Vector z = Conversions.encode(n, t, c5);
    


    byte[] encC4 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters)key, c4Vec, z).getEncoded();
    

    if (c6Len > 0)
    {

      return ByteUtils.concatenate(c6, encC4);
    }
    
    return encC4;
  }
  

  public byte[] messageDecrypt(byte[] input)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      throw new IllegalStateException("cipher initialised for decryption");
    }
    
    int nDiv8 = n >> 3;
    
    if (input.length < nDiv8)
    {
      throw new InvalidCipherTextException("Bad Padding: Ciphertext too short.");
    }
    
    int c2Len = messDigest.getDigestSize();
    int c4Len = k >> 3;
    int c6Len = input.length - nDiv8;
    byte[] encC4;
    byte[] c6;
    byte[] encC4;
    if (c6Len > 0)
    {
      byte[][] c6EncC4 = ByteUtils.split(input, c6Len);
      byte[] c6 = c6EncC4[0];
      encC4 = c6EncC4[1];
    }
    else
    {
      c6 = new byte[0];
      encC4 = input;
    }
    

    GF2Vector encC4Vec = GF2Vector.OS2VP(n, encC4);
    

    GF2Vector[] c4z = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters)key, encC4Vec);
    
    byte[] c4 = c4z[0].getEncoded();
    GF2Vector z = c4z[1];
    

    if (c4.length > c4Len)
    {

      c4 = ByteUtils.subArray(c4, 0, c4Len);
    }
    

    byte[] c5 = Conversions.decode(n, t, z);
    

    byte[] c6c5c4 = ByteUtils.concatenate(c6, c5);
    c6c5c4 = ByteUtils.concatenate(c6c5c4, c4);
    


    int c1Len = c6c5c4.length - c2Len;
    byte[][] c2c1 = ByteUtils.split(c6c5c4, c2Len);
    byte[] c2 = c2c1[0];
    byte[] c1 = c2c1[1];
    

    byte[] rPrime = new byte[messDigest.getDigestSize()];
    messDigest.update(c1, 0, c1.length);
    messDigest.doFinal(rPrime, 0);
    

    for (int i = c2Len - 1; i >= 0; i--)
    {
      int tmp273_271 = i; byte[] tmp273_269 = rPrime;tmp273_269[tmp273_271] = ((byte)(tmp273_269[tmp273_271] ^ c2[i]));
    }
    

    DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
    

    sr0.addSeedMaterial(rPrime);
    

    byte[] mConstPrime = new byte[c1Len];
    sr0.nextBytes(mConstPrime);
    

    for (int i = c1Len - 1; i >= 0; i--)
    {
      int tmp340_338 = i; byte[] tmp340_336 = mConstPrime;tmp340_336[tmp340_338] = ((byte)(tmp340_336[tmp340_338] ^ c1[i]));
    }
    
    if (mConstPrime.length < c1Len)
    {
      throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
    }
    
    byte[][] temp = ByteUtils.split(mConstPrime, c1Len - PUBLIC_CONSTANT.length);
    
    byte[] mr = temp[0];
    byte[] constPrime = temp[1];
    
    if (!ByteUtils.equals(constPrime, PUBLIC_CONSTANT))
    {
      throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
    }
    
    return mr;
  }
}
