package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.common.TorKeyAgreement;
import org.silvertunnel_ng.netlib.layer.tor.util.AESCounterMode;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class Node
{
  private static final Logger LOG = LoggerFactory.getLogger(Node.class);
  

  private static final int DIGEST_LEN = 20;
  

  private Router router;
  

  private byte[] symmetricKeyForCreate;
  

  private TorKeyAgreement dhKeyAgreement;
  

  private byte[] dhXBytes;
  

  private byte[] dhYBytes;
  

  private byte[] keyHandshake;
  

  private byte[] forwardDigest;
  

  private byte[] backwardDigest;
  

  private byte[] keyForward;
  

  private byte[] keyBackward;
  

  private AESCounterMode aesEncrypt;
  

  private AESCounterMode aesDecrypt;
  

  private MessageDigest sha1Forward;
  
  private MessageDigest sha1Backward;
  

  private Node() {}
  

  Node(Router init, byte[] dhXBytes)
    throws TorException
  {
    if (init == null) {
      throw new NullPointerException("can't init node on NULL server");
    }
    
    router = init;
    SecureRandom rnd = new SecureRandom();
    
    dhKeyAgreement = new TorKeyAgreement();
    BigInteger dhX = new BigInteger(1, dhXBytes);
    BigInteger dhPrivate = new BigInteger(TorKeyAgreement.P1024.bitLength() - 1, rnd);
    BigInteger dhXY = dhX.modPow(dhPrivate, TorKeyAgreement.P1024);
    byte[] dhXYBytes = convertBigIntegerTo128Bytes(dhXY);
    
    BigInteger dhY = TorKeyAgreement.G.modPow(dhPrivate, TorKeyAgreement.P1024);
    dhYBytes = convertBigIntegerTo128Bytes(dhY);
    

    int NUM_OF_DIGESTS = 5;
    byte[] k = new byte[100];
    byte[] sha1Input = new byte[dhXYBytes.length + 1];
    System.arraycopy(dhXYBytes, 0, sha1Input, 0, dhXYBytes.length);
    for (int i = 0; i < 5; i++) {
      sha1Input[(sha1Input.length - 1)] = ((byte)i);
      byte[] singleDigest = Encryption.getDigest(sha1Input);
      System.arraycopy(singleDigest, 0, k, i * 20, 20);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.<init>: dhX = \n" + 
        Encoding.toHexString(dhXBytes, 100) + "\n" + "dhY = \n" + 
        Encoding.toHexString(dhYBytes, 100) + "\n" + "dhXY = keymaterial:\n" + 
        
        Encoding.toHexString(dhXYBytes, 100) + "\n" + "Key Data:\n" + 
        Encoding.toHexString(k, 100));
    }
    


    keyHandshake = new byte[20];
    System.arraycopy(k, 0, keyHandshake, 0, 20);
    
    backwardDigest = new byte[20];
    System.arraycopy(k, 20, backwardDigest, 0, 20);
    sha1Backward = Encryption.getMessagesDigest();
    sha1Backward.update(backwardDigest, 0, 20);
    
    forwardDigest = new byte[20];
    System.arraycopy(k, 40, forwardDigest, 0, 20);
    sha1Forward = Encryption.getMessagesDigest();
    sha1Forward.update(forwardDigest, 0, 20);
    
    keyForward = new byte[16];
    System.arraycopy(k, 60, keyForward, 0, 16);
    aesDecrypt = new AESCounterMode(keyForward);
    
    keyBackward = new byte[16];
    System.arraycopy(k, 76, keyBackward, 0, 16);
    aesEncrypt = new AESCounterMode(keyBackward);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.<init>: dhX = \n" + Encoding.toHexString(dhXBytes, 100) + "\n" + "dhY = \n" + 
        Encoding.toHexString(dhYBytes, 100) + "\n" + "dhXY = keymaterial:\n" + 
        
        Encoding.toHexString(dhXYBytes, 100) + "\n" + "Key Data:\n" + 
        Encoding.toHexString(k, 100) + "\n" + "Key Data kf:\n" + 
        Encoding.toHexString(keyForward, 100) + "\n" + "Key Data kb:\n" + 
        Encoding.toHexString(keyBackward, 100));
    }
  }
  



  public Node(Router init)
    throws TorException
  {
    this(init, false);
  }
  




  public Node(Router init, boolean createFast)
    throws TorException
  {
    if (init == null) {
      throw new NullPointerException("can't init node on NULL server");
    }
    
    router = init;
    SecureRandom secureRandom = new SecureRandom();
    
    if (createFast) {
      dhXBytes = new byte[20];
      secureRandom.nextBytes(dhXBytes);
    }
    else {
      try {
        dhKeyAgreement = new TorKeyAgreement();
      } catch (TorException e) {
        LOG.error("Error while doing dh! Exception : ", e);
        throw e;
      }
      dhXBytes = dhKeyAgreement.getPublicKeyBytes();
      
      symmetricKeyForCreate = new byte[16];
      secureRandom.nextBytes(symmetricKeyForCreate);
    }
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.<init client>: dhX = \n" + 
        Encoding.toHexString(dhXBytes, 100) + "\n" + "dhY = \n" + 
        Encoding.toHexString(dhYBytes, 100));
    }
  }
  












  public final byte[] asymEncrypt(byte[] data)
    throws TorException
  {
    return Encryption.asymEncrypt(router.getOnionKey(), 
      getSymmetricKeyForCreate(), data);
  }
  





  public void finishDh(byte[] data)
    throws TorException
  {
    byte[] dhXYBytes;
    



    if (dhKeyAgreement == null)
    {
      byte[] dhXYBytes = new byte[40];
      dhYBytes = new byte[20];
      System.arraycopy(data, 0, dhYBytes, 0, 20);
      System.arraycopy(dhXBytes, 0, dhXYBytes, 0, 20);
      System.arraycopy(dhYBytes, 0, dhXYBytes, 20, 20);

    }
    else
    {
      dhYBytes = new byte[''];
      System.arraycopy(data, 0, dhYBytes, 0, 128);
      BigInteger otherPublicSecret = new BigInteger(1, dhYBytes);
      if (!TorKeyAgreement.isValidPublicValue(otherPublicSecret)) {
        LOG.warn("other DH public value is invalid!");
        throw new TorException("other DH public value is invalid!");
      }
      dhXYBytes = dhKeyAgreement.getSharedSecret(otherPublicSecret);
    }
    
    int NUM_OF_DIGESTS = 5;
    byte[] keyData = new byte[100];
    byte[] sha1Input = new byte[dhXYBytes.length + 1];
    System.arraycopy(dhXYBytes, 0, sha1Input, 0, dhXYBytes.length);
    for (int i = 0; i < 5; i++) {
      sha1Input[(sha1Input.length - 1)] = ((byte)i);
      byte[] singleDigest = Encryption.getDigest(sha1Input);
      System.arraycopy(singleDigest, 0, keyData, i * 20, 20);
    }
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.finishDh: dhX = \n" + 
        Encoding.toHexString(dhXBytes, 100) + "\n" + "dhY = \n" + 
        Encoding.toHexString(dhYBytes, 100) + "\n" + "dhXY = keymaterial:\n" + 
        
        Encoding.toHexString(dhXYBytes, 100) + "\n" + "Key Data:\n" + 
        Encoding.toHexString(keyData, 100));
    }
    


    boolean equal = true;
    for (int i = 0; (equal) && (i < 20); i++) {
      equal = keyData[i] == data[(dhYBytes.length + i)];
    }
    
    if (!equal) {
      throw new TorException("derived key material is wrong!");
    }
    


    keyHandshake = new byte[20];
    System.arraycopy(keyData, 0, keyHandshake, 0, 20);
    
    forwardDigest = new byte[20];
    System.arraycopy(keyData, 20, forwardDigest, 0, 20);
    sha1Forward = Encryption.getMessagesDigest();
    sha1Forward.update(forwardDigest);
    
    backwardDigest = new byte[20];
    System.arraycopy(keyData, 40, backwardDigest, 0, 20);
    sha1Backward = Encryption.getMessagesDigest();
    sha1Backward.update(backwardDigest);
    
    keyForward = new byte[16];
    System.arraycopy(keyData, 60, keyForward, 0, 16);
    aesEncrypt = new AESCounterMode(keyForward);
    
    keyBackward = new byte[16];
    System.arraycopy(keyData, 76, keyBackward, 0, 16);
    aesDecrypt = new AESCounterMode(keyBackward);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.finishDh: dhX = \n" + 
        Encoding.toHexString(dhXBytes, 100) + "\n" + "dhY = \n" + 
        Encoding.toHexString(dhYBytes, 100) + "\n" + "dhXY = keymaterial:\n" + 
        
        Encoding.toHexString(dhXYBytes, 100) + "\n" + "Key Data:\n" + 
        Encoding.toHexString(keyData, 100) + "\n" + "Key Data keyForward:\n" + 
        
        Encoding.toHexString(keyForward, 100) + "\n" + "Key Data keyBackward:\n" + 
        
        Encoding.toHexString(keyBackward, 100));
    }
  }
  





  public byte[] calcForwardDigest(byte[] data)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.calcForwardDigest() on:\n" + 
        Encoding.toHexString(data, 100));
    }
    sha1Forward.update(data, 0, data.length);
    byte[] digest = Encryption.intermediateDigest(sha1Forward);
    if (LOG.isDebugEnabled()) {
      LOG.debug(" result:\n" + Encoding.toHexString(digest, 100));
    }
    byte[] fourBytes = new byte[4];
    System.arraycopy(digest, 0, fourBytes, 0, 4);
    return fourBytes;
  }
  





  public byte[] calcBackwardDigest(byte[] data)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.calcBackwardDigest() on:\n" + 
        Encoding.toHexString(data, 100));
    }
    sha1Backward.update(data, 0, data.length);
    byte[] digest = Encryption.intermediateDigest(sha1Backward);
    if (LOG.isDebugEnabled()) {
      LOG.debug(" result:\n" + Encoding.toHexString(digest, 100));
    }
    byte[] fourBytes = new byte[4];
    System.arraycopy(digest, 0, fourBytes, 0, 4);
    return fourBytes;
  }
  




  public void symEncrypt(byte[] data)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.symEncrypt for node " + router.getNickname());
      LOG.debug("Node.symEncrypt in:\n" + Encoding.toHexString(data, 100));
    }
    

    byte[] encrypted = aesEncrypt.processStream(data);
    
    if (encrypted.length > data.length) {
      System.arraycopy(encrypted, 0, data, 0, data.length);
    } else {
      System.arraycopy(encrypted, 0, data, 0, encrypted.length);
    }
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.symEncrypt out:\n" + Encoding.toHexString(data, 100));
    }
  }
  





  public void symDecrypt(byte[] data)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.symDecrypt for node " + router.getNickname());
    }
    

    byte[] decrypted = aesDecrypt.processStream(data);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("Node.symDecrypt in:\n" + 
        Encoding.toHexString(data, 100));
      LOG.debug("Node.symDecrypt out:\n" + 
        Encoding.toHexString(decrypted, 100));
    }
    

    if (decrypted.length > data.length) {
      System.arraycopy(decrypted, 0, data, 0, data.length);
    } else {
      System.arraycopy(decrypted, 0, data, 0, decrypted.length);
    }
  }
  




  private byte[] convertBigIntegerTo128Bytes(BigInteger a)
  {
    byte[] temp = a.toByteArray();
    byte[] result = new byte[''];
    if (temp.length > 128) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("convertBigIntegerTo128Bytes temp longer than 128!");
        LOG.debug("Big Integer a = " + a);
        LOG.debug("temp.length = " + temp.length);
        LOG.debug("temp data :\n" + Encoding.toHexString(temp, 100));
      }
      System.arraycopy(temp, temp.length - 128, result, 0, 128);
    } else {
      System.arraycopy(temp, 0, result, 128 - temp.length, temp.length);
    }
    return result;
  }
  



  public Router getRouter()
  {
    return router;
  }
  
  public byte[] getSymmetricKeyForCreate() {
    return symmetricKeyForCreate;
  }
  
  public byte[] getDhXBytes() {
    return dhXBytes;
  }
  
  public byte[] getDhYBytes() {
    return dhYBytes;
  }
  
  public byte[] getKeyHandshake() {
    return keyHandshake;
  }
  
  public byte[] getForwardDigest() {
    return forwardDigest;
  }
  
  public byte[] getBackwardDigest() {
    return backwardDigest;
  }
  
  public byte[] getKf() {
    return keyForward;
  }
  
  public byte[] getKb() {
    return keyBackward;
  }
  
  public AESCounterMode getAesEncrypt() {
    return aesEncrypt;
  }
  
  public AESCounterMode getAesDecrypt() {
    return aesDecrypt;
  }
}
