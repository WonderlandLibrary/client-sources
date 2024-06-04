package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.mceliece.McElieceCipher;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyParameters;
import org.spongycastle.pqc.jcajce.provider.util.AsymmetricBlockCipher;



public class McEliecePKCSCipherSpi
  extends AsymmetricBlockCipher
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers
{
  private McElieceCipher cipher;
  
  public McEliecePKCSCipherSpi(McElieceCipher cipher)
  {
    this.cipher = cipher;
  }
  




  protected void initCipherEncrypt(Key key, AlgorithmParameterSpec params, SecureRandom sr)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    CipherParameters param = McElieceKeysToParams.generatePublicKeyParameter((PublicKey)key);
    
    param = new ParametersWithRandom(param, sr);
    cipher.init(true, param);
    maxPlainTextSize = cipher.maxPlainTextSize;
    cipherTextSize = cipher.cipherTextSize;
  }
  

  protected void initCipherDecrypt(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    CipherParameters param = McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey)key);
    
    cipher.init(false, param);
    maxPlainTextSize = cipher.maxPlainTextSize;
    cipherTextSize = cipher.cipherTextSize;
  }
  
  protected byte[] messageEncrypt(byte[] input)
    throws IllegalBlockSizeException, BadPaddingException
  {
    byte[] output = null;
    try
    {
      output = cipher.messageEncrypt(input);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return output;
  }
  
  protected byte[] messageDecrypt(byte[] input)
    throws IllegalBlockSizeException, BadPaddingException
  {
    byte[] output = null;
    try
    {
      output = cipher.messageDecrypt(input);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return output;
  }
  
  public String getName()
  {
    return "McEliecePKCS";
  }
  
  public int getKeySize(Key key) throws InvalidKeyException
  {
    McElieceKeyParameters mcElieceKeyParameters;
    McElieceKeyParameters mcElieceKeyParameters;
    if ((key instanceof PublicKey))
    {
      mcElieceKeyParameters = (McElieceKeyParameters)McElieceKeysToParams.generatePublicKeyParameter((PublicKey)key);
    }
    else
    {
      mcElieceKeyParameters = (McElieceKeyParameters)McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey)key);
    }
    


    return cipher.getKeySize(mcElieceKeyParameters);
  }
  
  public static class McEliecePKCS
    extends McEliecePKCSCipherSpi
  {
    public McEliecePKCS()
    {
      super();
    }
  }
}
