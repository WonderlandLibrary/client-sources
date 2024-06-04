package org.spongycastle.crypto.engines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.EphemeralKeyPair;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyParser;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.IESParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Pack;


















public class IESEngine
{
  BasicAgreement agree;
  DerivationFunction kdf;
  Mac mac;
  BufferedBlockCipher cipher;
  byte[] macBuf;
  boolean forEncryption;
  CipherParameters privParam;
  CipherParameters pubParam;
  IESParameters param;
  byte[] V;
  private EphemeralKeyPairGenerator keyPairGenerator;
  private KeyParser keyParser;
  private byte[] IV;
  
  public IESEngine(BasicAgreement agree, DerivationFunction kdf, Mac mac)
  {
    this.agree = agree;
    this.kdf = kdf;
    this.mac = mac;
    macBuf = new byte[mac.getMacSize()];
    cipher = null;
  }
  














  public IESEngine(BasicAgreement agree, DerivationFunction kdf, Mac mac, BufferedBlockCipher cipher)
  {
    this.agree = agree;
    this.kdf = kdf;
    this.mac = mac;
    macBuf = new byte[mac.getMacSize()];
    this.cipher = cipher;
  }
  












  public void init(boolean forEncryption, CipherParameters privParam, CipherParameters pubParam, CipherParameters params)
  {
    this.forEncryption = forEncryption;
    this.privParam = privParam;
    this.pubParam = pubParam;
    V = new byte[0];
    
    extractParams(params);
  }
  







  public void init(AsymmetricKeyParameter publicKey, CipherParameters params, EphemeralKeyPairGenerator ephemeralKeyPairGenerator)
  {
    forEncryption = true;
    pubParam = publicKey;
    keyPairGenerator = ephemeralKeyPairGenerator;
    
    extractParams(params);
  }
  







  public void init(AsymmetricKeyParameter privateKey, CipherParameters params, KeyParser publicKeyParser)
  {
    forEncryption = false;
    privParam = privateKey;
    keyParser = publicKeyParser;
    
    extractParams(params);
  }
  
  private void extractParams(CipherParameters params)
  {
    if ((params instanceof ParametersWithIV))
    {
      IV = ((ParametersWithIV)params).getIV();
      param = ((IESParameters)((ParametersWithIV)params).getParameters());
    }
    else
    {
      IV = null;
      param = ((IESParameters)params);
    }
  }
  
  public BufferedBlockCipher getCipher()
  {
    return cipher;
  }
  
  public Mac getMac()
  {
    return mac;
  }
  



  private byte[] encryptBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    byte[] C = null;byte[] K = null;byte[] K1 = null;byte[] K2 = null;
    int len;
    int len;
    if (cipher == null)
    {

      K1 = new byte[inLen];
      K2 = new byte[param.getMacKeySize() / 8];
      K = new byte[K1.length + K2.length];
      
      kdf.generateBytes(K, 0, K.length);
      
      if (V.length != 0)
      {
        System.arraycopy(K, 0, K2, 0, K2.length);
        System.arraycopy(K, K2.length, K1, 0, K1.length);
      }
      else
      {
        System.arraycopy(K, 0, K1, 0, K1.length);
        System.arraycopy(K, inLen, K2, 0, K2.length);
      }
      
      C = new byte[inLen];
      
      for (int i = 0; i != inLen; i++)
      {
        C[i] = ((byte)(in[(inOff + i)] ^ K1[i]));
      }
      len = inLen;

    }
    else
    {
      K1 = new byte[((IESWithCipherParameters)param).getCipherKeySize() / 8];
      K2 = new byte[param.getMacKeySize() / 8];
      K = new byte[K1.length + K2.length];
      
      kdf.generateBytes(K, 0, K.length);
      System.arraycopy(K, 0, K1, 0, K1.length);
      System.arraycopy(K, K1.length, K2, 0, K2.length);
      

      if (IV != null)
      {
        cipher.init(true, new ParametersWithIV(new KeyParameter(K1), IV));
      }
      else
      {
        cipher.init(true, new KeyParameter(K1));
      }
      
      C = new byte[cipher.getOutputSize(inLen)];
      len = cipher.processBytes(in, inOff, inLen, C, 0);
      len += cipher.doFinal(C, len);
    }
    


    byte[] P2 = param.getEncodingV();
    byte[] L2 = null;
    if (V.length != 0)
    {
      L2 = getLengthTag(P2);
    }
    


    byte[] T = new byte[mac.getMacSize()];
    
    mac.init(new KeyParameter(K2));
    mac.update(C, 0, C.length);
    if (P2 != null)
    {
      mac.update(P2, 0, P2.length);
    }
    if (V.length != 0)
    {
      mac.update(L2, 0, L2.length);
    }
    mac.doFinal(T, 0);
    


    byte[] Output = new byte[V.length + len + T.length];
    System.arraycopy(V, 0, Output, 0, V.length);
    System.arraycopy(C, 0, Output, V.length, len);
    System.arraycopy(T, 0, Output, V.length + len, T.length);
    return Output;
  }
  




  private byte[] decryptBlock(byte[] in_enc, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    int len = 0;
    

    if (inLen < V.length + mac.getMacSize())
    {
      throw new InvalidCipherTextException("Length of input must be greater than the MAC and V combined");
    }
    byte[] K2;
    byte[] M;
    if (cipher == null)
    {

      byte[] K1 = new byte[inLen - V.length - mac.getMacSize()];
      byte[] K2 = new byte[param.getMacKeySize() / 8];
      byte[] K = new byte[K1.length + K2.length];
      
      kdf.generateBytes(K, 0, K.length);
      
      if (V.length != 0)
      {
        System.arraycopy(K, 0, K2, 0, K2.length);
        System.arraycopy(K, K2.length, K1, 0, K1.length);
      }
      else
      {
        System.arraycopy(K, 0, K1, 0, K1.length);
        System.arraycopy(K, K1.length, K2, 0, K2.length);
      }
      

      byte[] M = new byte[K1.length];
      
      for (int i = 0; i != K1.length; i++)
      {
        M[i] = ((byte)(in_enc[(inOff + V.length + i)] ^ K1[i]));
      }
      
    }
    else
    {
      byte[] K1 = new byte[((IESWithCipherParameters)param).getCipherKeySize() / 8];
      K2 = new byte[param.getMacKeySize() / 8];
      byte[] K = new byte[K1.length + K2.length];
      
      kdf.generateBytes(K, 0, K.length);
      System.arraycopy(K, 0, K1, 0, K1.length);
      System.arraycopy(K, K1.length, K2, 0, K2.length);
      
      CipherParameters cp = new KeyParameter(K1);
      

      if (IV != null)
      {
        cp = new ParametersWithIV(cp, IV);
      }
      
      cipher.init(false, cp);
      
      M = new byte[cipher.getOutputSize(inLen - V.length - mac.getMacSize())];
      

      len = cipher.processBytes(in_enc, inOff + V.length, inLen - V.length - mac.getMacSize(), M, 0);
    }
    

    byte[] P2 = param.getEncodingV();
    byte[] L2 = null;
    if (V.length != 0)
    {
      L2 = getLengthTag(P2);
    }
    

    int end = inOff + inLen;
    byte[] T1 = Arrays.copyOfRange(in_enc, end - mac.getMacSize(), end);
    
    byte[] T2 = new byte[T1.length];
    mac.init(new KeyParameter(K2));
    mac.update(in_enc, inOff + V.length, inLen - V.length - T2.length);
    
    if (P2 != null)
    {
      mac.update(P2, 0, P2.length);
    }
    if (V.length != 0)
    {
      mac.update(L2, 0, L2.length);
    }
    mac.doFinal(T2, 0);
    
    if (!Arrays.constantTimeAreEqual(T1, T2))
    {
      throw new InvalidCipherTextException("invalid MAC");
    }
    
    if (cipher == null)
    {
      return M;
    }
    

    len += cipher.doFinal(M, len);
    
    return Arrays.copyOfRange(M, 0, len);
  }
  





  public byte[] processBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      if (keyPairGenerator != null)
      {
        EphemeralKeyPair ephKeyPair = keyPairGenerator.generate();
        
        privParam = ephKeyPair.getKeyPair().getPrivate();
        V = ephKeyPair.getEncodedPublicKey();
      }
      

    }
    else if (keyParser != null)
    {
      ByteArrayInputStream bIn = new ByteArrayInputStream(in, inOff, inLen);
      
      try
      {
        pubParam = keyParser.readKey(bIn);
      }
      catch (IOException e)
      {
        throw new InvalidCipherTextException("unable to recover ephemeral public key: " + e.getMessage(), e);
      }
      catch (IllegalArgumentException e)
      {
        throw new InvalidCipherTextException("unable to recover ephemeral public key: " + e.getMessage(), e);
      }
      
      int encLength = inLen - bIn.available();
      V = Arrays.copyOfRange(in, inOff, inOff + encLength);
    }
    


    agree.init(privParam);
    BigInteger z = agree.calculateAgreement(pubParam);
    byte[] Z = BigIntegers.asUnsignedByteArray(agree.getFieldSize(), z);
    

    if (V.length != 0)
    {
      byte[] VZ = Arrays.concatenate(V, Z);
      Arrays.fill(Z, (byte)0);
      Z = VZ;
    }
    

    try
    {
      KDFParameters kdfParam = new KDFParameters(Z, param.getDerivationV());
      kdf.init(kdfParam);
      
      return forEncryption ? 
        encryptBlock(in, inOff, inLen) : 
        decryptBlock(in, inOff, inLen);
    }
    finally
    {
      Arrays.fill(Z, (byte)0);
    }
  }
  

  protected byte[] getLengthTag(byte[] p2)
  {
    byte[] L2 = new byte[8];
    if (p2 != null)
    {
      Pack.longToBigEndian(p2.length * 8L, L2, 0);
    }
    return L2;
  }
}
