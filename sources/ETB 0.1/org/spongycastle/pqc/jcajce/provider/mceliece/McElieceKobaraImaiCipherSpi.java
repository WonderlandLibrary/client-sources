package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2KeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceKobaraImaiCipher;
import org.spongycastle.pqc.jcajce.provider.util.AsymmetricHybridCipher;









public class McElieceKobaraImaiCipherSpi
  extends AsymmetricHybridCipher
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers
{
  private Digest digest;
  private McElieceKobaraImaiCipher cipher;
  private ByteArrayOutputStream buf = new ByteArrayOutputStream();
  

  public McElieceKobaraImaiCipherSpi()
  {
    buf = new ByteArrayOutputStream();
  }
  
  protected McElieceKobaraImaiCipherSpi(Digest digest, McElieceKobaraImaiCipher cipher)
  {
    this.digest = digest;
    this.cipher = cipher;
    buf = new ByteArrayOutputStream();
  }
  








  public byte[] update(byte[] input, int inOff, int inLen)
  {
    buf.write(input, inOff, inLen);
    return new byte[0];
  }
  














  public byte[] doFinal(byte[] input, int inOff, int inLen)
    throws BadPaddingException
  {
    update(input, inOff, inLen);
    if (opMode == 1)
    {
      return cipher.messageEncrypt(pad());
    }
    if (opMode == 2)
    {
      try
      {
        byte[] inputOfDecr = buf.toByteArray();
        buf.reset();
        
        return unpad(cipher.messageDecrypt(inputOfDecr));
      }
      catch (InvalidCipherTextException e)
      {
        throw new BadPaddingException(e.getMessage());
      }
    }
    

    throw new IllegalStateException("unknown mode in doFinal");
  }
  

  protected int encryptOutputSize(int inLen)
  {
    return 0;
  }
  
  protected int decryptOutputSize(int inLen)
  {
    return 0;
  }
  



  protected void initCipherEncrypt(Key key, AlgorithmParameterSpec params, SecureRandom sr)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    buf.reset();
    
    CipherParameters param = McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey)key);
    
    param = new ParametersWithRandom(param, sr);
    digest.reset();
    cipher.init(true, param);
  }
  

  protected void initCipherDecrypt(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    buf.reset();
    
    CipherParameters param = McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey)key);
    
    digest.reset();
    cipher.init(false, param);
  }
  
  public String getName()
  {
    return "McElieceKobaraImaiCipher";
  }
  

  public int getKeySize(Key key)
    throws InvalidKeyException
  {
    if ((key instanceof PublicKey))
    {
      McElieceCCA2KeyParameters mcElieceCCA2KeyParameters = (McElieceCCA2KeyParameters)McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey)key);
      return cipher.getKeySize(mcElieceCCA2KeyParameters);
    }
    if ((key instanceof PrivateKey))
    {
      McElieceCCA2KeyParameters mcElieceCCA2KeyParameters = (McElieceCCA2KeyParameters)McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey)key);
      return cipher.getKeySize(mcElieceCCA2KeyParameters);
    }
    

    throw new InvalidKeyException();
  }
  








  private byte[] pad()
  {
    buf.write(1);
    byte[] result = buf.toByteArray();
    buf.reset();
    return result;
  }
  









  private byte[] unpad(byte[] pmBytes)
    throws BadPaddingException
  {
    for (int index = pmBytes.length - 1; (index >= 0) && (pmBytes[index] == 0); index--) {}
    




    if (pmBytes[index] != 1)
    {
      throw new BadPaddingException("invalid ciphertext");
    }
    

    byte[] mBytes = new byte[index];
    System.arraycopy(pmBytes, 0, mBytes, 0, index);
    return mBytes;
  }
  
  public static class McElieceKobaraImai
    extends McElieceKobaraImaiCipherSpi
  {
    public McElieceKobaraImai()
    {
      super(new McElieceKobaraImaiCipher());
    }
  }
  
  public static class McElieceKobaraImai224
    extends McElieceKobaraImaiCipherSpi
  {
    public McElieceKobaraImai224()
    {
      super(new McElieceKobaraImaiCipher());
    }
  }
  
  public static class McElieceKobaraImai256
    extends McElieceKobaraImaiCipherSpi
  {
    public McElieceKobaraImai256()
    {
      super(new McElieceKobaraImaiCipher());
    }
  }
  
  public static class McElieceKobaraImai384
    extends McElieceKobaraImaiCipherSpi
  {
    public McElieceKobaraImai384()
    {
      super(new McElieceKobaraImaiCipher());
    }
  }
  
  public static class McElieceKobaraImai512
    extends McElieceKobaraImaiCipherSpi
  {
    public McElieceKobaraImai512()
    {
      super(new McElieceKobaraImaiCipher());
    }
  }
}
