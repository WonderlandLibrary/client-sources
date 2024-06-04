package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageEncryptor;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.Vector;






















public class McElieceCipher
  implements MessageEncryptor
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
  private SecureRandom sr;
  private int n;
  private int k;
  private int t;
  public int maxPlainTextSize;
  public int cipherTextSize;
  private McElieceKeyParameters key;
  private boolean forEncryption;
  
  public McElieceCipher() {}
  
  public void init(boolean forEncryption, CipherParameters param)
  {
    this.forEncryption = forEncryption;
    if (forEncryption)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        sr = rParam.getRandom();
        key = ((McEliecePublicKeyParameters)rParam.getParameters());
        initCipherEncrypt((McEliecePublicKeyParameters)key);

      }
      else
      {
        sr = new SecureRandom();
        key = ((McEliecePublicKeyParameters)param);
        initCipherEncrypt((McEliecePublicKeyParameters)key);
      }
    }
    else
    {
      key = ((McEliecePrivateKeyParameters)param);
      initCipherDecrypt((McEliecePrivateKeyParameters)key);
    }
  }
  









  public int getKeySize(McElieceKeyParameters key)
  {
    if ((key instanceof McEliecePublicKeyParameters))
    {
      return ((McEliecePublicKeyParameters)key).getN();
    }
    
    if ((key instanceof McEliecePrivateKeyParameters))
    {
      return ((McEliecePrivateKeyParameters)key).getN();
    }
    throw new IllegalArgumentException("unsupported type");
  }
  


  private void initCipherEncrypt(McEliecePublicKeyParameters pubKey)
  {
    sr = (sr != null ? sr : new SecureRandom());
    n = pubKey.getN();
    k = pubKey.getK();
    t = pubKey.getT();
    cipherTextSize = (n >> 3);
    maxPlainTextSize = (k >> 3);
  }
  

  private void initCipherDecrypt(McEliecePrivateKeyParameters privKey)
  {
    n = privKey.getN();
    k = privKey.getK();
    
    maxPlainTextSize = (k >> 3);
    cipherTextSize = (n >> 3);
  }
  






  public byte[] messageEncrypt(byte[] input)
  {
    if (!forEncryption)
    {
      throw new IllegalStateException("cipher initialised for decryption");
    }
    GF2Vector m = computeMessageRepresentative(input);
    GF2Vector z = new GF2Vector(n, t, sr);
    
    GF2Matrix g = ((McEliecePublicKeyParameters)key).getG();
    Vector mG = g.leftMultiply(m);
    GF2Vector mGZ = (GF2Vector)mG.add(z);
    
    return mGZ.getEncoded();
  }
  
  private GF2Vector computeMessageRepresentative(byte[] input)
  {
    byte[] data = new byte[maxPlainTextSize + ((k & 0x7) != 0 ? 1 : 0)];
    System.arraycopy(input, 0, data, 0, input.length);
    data[input.length] = 1;
    return GF2Vector.OS2VP(k, data);
  }
  







  public byte[] messageDecrypt(byte[] input)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      throw new IllegalStateException("cipher initialised for decryption");
    }
    
    GF2Vector vec = GF2Vector.OS2VP(n, input);
    McEliecePrivateKeyParameters privKey = (McEliecePrivateKeyParameters)key;
    GF2mField field = privKey.getField();
    PolynomialGF2mSmallM gp = privKey.getGoppaPoly();
    GF2Matrix sInv = privKey.getSInv();
    Permutation p1 = privKey.getP1();
    Permutation p2 = privKey.getP2();
    GF2Matrix h = privKey.getH();
    PolynomialGF2mSmallM[] qInv = privKey.getQInv();
    

    Permutation p = p1.rightMultiply(p2);
    

    Permutation pInv = p.computeInverse();
    

    GF2Vector cPInv = (GF2Vector)vec.multiply(pInv);
    

    GF2Vector syndrome = (GF2Vector)h.rightMultiply(cPInv);
    

    GF2Vector z = GoppaCode.syndromeDecode(syndrome, field, gp, qInv);
    GF2Vector mSG = (GF2Vector)cPInv.add(z);
    

    mSG = (GF2Vector)mSG.multiply(p1);
    z = (GF2Vector)z.multiply(p);
    

    GF2Vector mS = mSG.extractRightVector(k);
    

    GF2Vector mVec = (GF2Vector)sInv.leftMultiply(mS);
    

    return computeMessage(mVec);
  }
  
  private byte[] computeMessage(GF2Vector mr)
    throws InvalidCipherTextException
  {
    byte[] mrBytes = mr.getEncoded();
    

    for (int index = mrBytes.length - 1; (index >= 0) && (mrBytes[index] == 0); index--) {}
    




    if ((index < 0) || (mrBytes[index] != 1))
    {
      throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
    }
    

    byte[] mBytes = new byte[index];
    System.arraycopy(mrBytes, 0, mBytes, 0, index);
    return mBytes;
  }
}
