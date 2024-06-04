package org.silvertunnel_ng.netlib.layer.tor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.RSAPublicKeyStructure;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.OAEPEncoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.openssl.PEMKeyPair;
import org.spongycastle.openssl.PEMParser;
import org.spongycastle.openssl.PEMWriter;
import org.spongycastle.util.encoders.Base64;

































































public class Encryption
{
  private static final Logger LOG = LoggerFactory.getLogger(Encryption.class);
  
  public static final String DIGEST_ALGORITHM = "SHA-1";
  
  private static final String PK_ALGORITHM = "RSA/ECB/PKCS1Padding";
  
  private static final int KEY_STRENGTH = 1024;
  
  private static final int KEY_CERTAINTY = 80;
  
  static
  {
    try
    {
      if (Security.getProvider("SC") == null)
      {
        Security.addProvider(new BouncyCastleProvider());
      }
    } catch (Throwable t) {
      LOG.error("Cannot initialize class Encryption", t);
    }
  }
  





  public static byte[] getDigest(byte[] input)
  {
    return getDigest("SHA-1", input);
  }
  





  public static byte[] getDigest(String algorithm, byte[] input)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(input);
      return md.digest();
    }
    catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  


  public static MessageDigest getMessagesDigest()
  {
    try
    {
      return MessageDigest.getInstance("SHA-1");
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  








  public static byte[] intermediateDigest(MessageDigest md)
  {
    try
    {
      MessageDigest mdClone = (MessageDigest)md.clone();
      return mdClone.digest();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  








  public static boolean verifySignature(byte[] signature, PublicKey signingKey, byte[] data)
  {
    return verifySignatureWithHash(signature, signingKey, getDigest(data));
  }
  







  public static boolean verifySignatureWithHash(byte[] signature, PublicKey signingKey, byte[] dataDigest)
  {
    try
    {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(2, signingKey);
      byte[] decryptedDigest = cipher.doFinal(signature);
      
      if ((decryptedDigest != null) && (dataDigest != null) && (decryptedDigest.length > dataDigest.length))
      {




        byte[] fixedDecryptedDigest = new byte[dataDigest.length];
        System.arraycopy(decryptedDigest, decryptedDigest.length - dataDigest.length, fixedDecryptedDigest, 0, dataDigest.length);
        decryptedDigest = fixedDecryptedDigest;
      }
      
      boolean verificationSuccessful = Arrays.equals(decryptedDigest, dataDigest);
      if (!verificationSuccessful) {
        LOG.info("verifySignature(): decryptedDigest=" + Encoding.toHexString(decryptedDigest));
        LOG.info("verifySignature(): dataDigest     =" + Encoding.toHexString(dataDigest));
      }
      
      return verificationSuccessful;
    }
    catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  





  public static byte[] signData(byte[] data, PrivateKey signingKey)
  {
    try
    {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(1, signingKey);
      return cipher.doFinal(getDigest(data));
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  



  public static RSAPublicKey extractPublicRSAKey(String s)
  {
    RSAPublicKey theKey;
    


    try
    {
      theKey = new RSAKeyEncoder().parsePEMPublicKey(s);
    } catch (Exception e) { RSAPublicKey theKey;
      LOG.warn("Encryption.extractPublicRSAKey: Caught exception:" + e.getMessage(), e);
      theKey = null;
    }
    
    return theKey;
  }
  



  public static RSAKeyPair extractRSAKeyPair(String s)
  {
    RSAKeyPair rsaKeyPair;
    


    try
    {
      PEMParser parser = new PEMParser(new StringReader(s));
      Object o = parser.readObject();
      parser.close();
      
      if (!(o instanceof PEMKeyPair)) {
        throw new IOException("Encryption.extractRSAKeyPair: no private key found in string '" + s + "'");
      }
      PEMKeyPair keyPair = (PEMKeyPair)o;
      if (keyPair.getPrivateKeyInfo() == null) {
        throw new IOException("Encryption.extractRSAKeyPair: no private key found in key pair of string '" + s + "'");
      }
      if (keyPair.getPublicKeyInfo() == null) {
        throw new IOException("Encryption.extractRSAKeyPair: no public key found in key pair of string '" + s + "'");
      }
      

      RSAPrivateCrtKey privateKey = new TempJCERSAPrivateCrtKey(keyPair.getPrivateKeyInfo());
      LOG.debug("JCEPrivateKey={}", privateKey);
      RSAPublicKey publicKey = new TempJCERSAPublicKey(keyPair.getPublicKeyInfo());
      rsaKeyPair = new RSAKeyPair(publicKey, privateKey);
    } catch (Exception e) {
      RSAKeyPair rsaKeyPair;
      LOG.warn("Encryption.extractPrivateRSAKey: Caught exception:" + e.getMessage());
      rsaKeyPair = null;
    }
    
    return rsaKeyPair;
  }
  





  public static String getPEMStringFromRSAKeyPair(RSAKeyPair rsaKeyPair)
  {
    StringWriter pemStrWriter = new StringWriter();
    PEMWriter pemWriter = new PEMWriter(pemStrWriter);
    try {
      KeyPair keyPair = new KeyPair(rsaKeyPair.getPublic(), rsaKeyPair.getPrivate());
      pemWriter.writeObject(keyPair.getPrivate());
      pemWriter.close();
    }
    catch (IOException e) {
      LOG.warn("Caught exception:" + e.getMessage());
      return "";
    }
    
    return pemStrWriter.toString();
  }
  





  public static RSAPublicKey getRSAPublicKey(BigInteger modulus, BigInteger publicExponent)
  {
    try
    {
      return (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  





  public static RSAPrivateKey getRSAPrivateKey(BigInteger modulus, BigInteger privateExponent)
  {
    try
    {
      return (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateKeySpec(modulus, privateExponent));
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
  



  public static RSAPublicKey extractBinaryRSAKey(byte[] b)
  {
    RSAPublicKey theKey;
    


    try
    {
      ASN1InputStream ais = new ASN1InputStream(b);
      Object asnObject = ais.readObject();
      ASN1Sequence sequence = (ASN1Sequence)asnObject;
      RSAPublicKeyStructure tempKey = new RSAPublicKeyStructure(sequence);
      RSAPublicKey theKey = getRSAPublicKey(tempKey.getModulus(), tempKey.getPublicExponent());
      ais.close();
    } catch (IOException e) {
      LOG.warn("Caught exception:" + e.getMessage());
      theKey = null;
    }
    
    return theKey;
  }
  





  public static byte[] getPKCS1EncodingFromRSAPublicKey(RSAPublicKey pubKeyStruct)
  {
    try
    {
      RSAPublicKeyStructure myKey = new RSAPublicKeyStructure(pubKeyStruct.getModulus(), pubKeyStruct.getPublicExponent());
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      ASN1OutputStream aOut = new ASN1OutputStream(bOut);
      aOut.writeObject(myKey.toASN1Object());
      aOut.close();
      return bOut.toByteArray();
    } catch (Exception e) {}
    return null;
  }
  













  public static String getPEMStringFromRSAPublicKey(RSAPublicKey rsaPublicKey)
  {
    StringBuffer tmpDirSigningKey = new StringBuffer();
    
    try
    {
      tmpDirSigningKey.append("-----BEGIN RSA PUBLIC KEY-----\n");
      
      byte[] base64Encoding = Base64.encode(getPKCS1EncodingFromRSAPublicKey(rsaPublicKey));
      for (int i = 0; i < base64Encoding.length; i++) {
        tmpDirSigningKey.append((char)base64Encoding[i]);
        if ((i + 1) % 64 == 0) {
          tmpDirSigningKey.append("\n");
        }
      }
      tmpDirSigningKey.append("\n");
      
      tmpDirSigningKey.append("-----END RSA PUBLIC KEY-----\n");
    } catch (Exception e) {
      return null;
    }
    
    return tmpDirSigningKey.toString();
  }
  














  public static byte[] asymEncrypt(RSAPublicKey pub, byte[] symmetricKey, byte[] data)
    throws TorException
  {
    if (data == null) {
      throw new NullPointerException("can't encrypt NULL data");
    }
    
    HybridEncryption hybridEncryption = new HybridEncryption();
    
    return hybridEncryption.encrypt(data, pub, symmetricKey);
  }
  













  public static byte[] asymDecrypt(RSAPrivateKey priv, byte[] data)
    throws TorException
  {
    if (data == null) {
      throw new NullPointerException("can't encrypt NULL data");
    }
    if (data.length < 70) {
      throw new TorException("input array too short");
    }
    try
    {
      int encryptedBytes = 0;
      

      OAEPEncoding oaep = new OAEPEncoding(new RSAEngine());
      oaep.init(false, new RSAKeyParameters(true, priv.getModulus(), priv.getPrivateExponent()));
      
      encryptedBytes = oaep.getInputBlockSize();
      byte[] oaepInput = new byte[encryptedBytes];
      System.arraycopy(data, 0, oaepInput, 0, encryptedBytes);
      byte[] part1 = oaep.decodeBlock(oaepInput, 0, encryptedBytes);
      

      byte[] symmetricKey = new byte[16];
      System.arraycopy(part1, 0, symmetricKey, 0, 16);
      
      AESCounterMode aes = new AESCounterMode(symmetricKey);
      
      byte[] aesInput = new byte[data.length - encryptedBytes];
      System.arraycopy(data, encryptedBytes, aesInput, 0, aesInput.length);
      byte[] part2 = aes.processStream(aesInput);
      

      byte[] result = new byte[part1.length - 16 + part2.length];
      System.arraycopy(part1, 16, result, 0, part1.length - 16);
      System.arraycopy(part2, 0, result, part1.length - 16, part2.length);
      
      return result;
    }
    catch (InvalidCipherTextException e) {
      LOG.error("Encryption.asymDecrypt(): can't decrypt cipher text:" + e.getMessage());
      throw new TorException("Encryption.asymDecrypt(): InvalidCipherTextException:" + e.getMessage());
    }
  }
  







  public static RSAKeyPair createNewRSAKeyPair()
  {
    try
    {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
      keyGen.initialize(1024);
      KeyPair keypair = keyGen.genKeyPair();
      RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey)keypair.getPrivate();
      RSAPublicKey publicKey = (RSAPublicKey)keypair.getPublic();
      
      return new RSAKeyPair(publicKey, privateKey);
    }
    catch (NoSuchAlgorithmException e) {
      LOG.error("Could not create new key pair", e);
      throw new RuntimeException(e);
    }
  }
  
  public Encryption() {}
}
