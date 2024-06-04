package org.silvertunnel_ng.netlib.layer.tor.common;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;






























public class TorKeyAgreement
{
  public static final int DH_LEN = 128;
  public static final int DH_SEC_LEN = 40;
  public static final BigInteger P1024 = new BigInteger("00FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF", 16);
  




  public static final BigInteger G = new BigInteger("2");
  



  private static final int PRIVATE_KEY_SIZE = 320;
  


  private static final DHParameterSpec DH_PARAMETER_SPEC = new DHParameterSpec(P1024, G, 320);
  

  private final KeyAgreement dh;
  

  private final KeyPair keyPair;
  


  public TorKeyAgreement()
    throws TorException
  {
    keyPair = generateKeyPair();
    dh = createDH();
  }
  






  public BigInteger getPublicValue()
  {
    DHPublicKey pubKey = (DHPublicKey)keyPair.getPublic();
    return pubKey.getY();
  }
  







  public byte[] getPublicKeyBytes()
  {
    byte[] output = new byte[''];
    byte[] yBytes = getPublicValue().toByteArray();
    if ((yBytes[0] == 0) && (yBytes.length == 129))
    {
      System.arraycopy(yBytes, 1, output, 0, 128);
    }
    else if (yBytes.length <= 128)
    {
      int offset = 128 - yBytes.length;
      System.arraycopy(yBytes, 0, output, offset, yBytes.length);
    }
    else
    {
      throw new IllegalStateException("Public value is longer than DH_LEN but not because of sign bit");
    }
    
    return output;
  }
  










  public static boolean isValidPublicValue(byte[] publicValue)
  {
    return isValidPublicValue(new BigInteger(1, publicValue));
  }
  
  public static boolean isValidPublicValue(BigInteger publicValue) {
    if ((publicValue.signum() < 1) || (publicValue.equals(BigInteger.ONE)))
    {
      return false;
    }
    if (publicValue.compareTo(P1024.subtract(BigInteger.ONE)) >= 0)
    {
      return false;
    }
    return true;
  }
  







  public byte[] getSharedSecret(BigInteger otherPublic)
    throws TorException
  {
    try
    {
      KeyFactory factory = KeyFactory.getInstance("DH");
      DHPublicKeySpec pub = new DHPublicKeySpec(otherPublic, P1024, G);
      PublicKey key = factory.generatePublic(pub);
      dh.doPhase(key, true);
      return dh.generateSecret();
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
  
  private final KeyAgreement createDH() throws TorException
  {
    try
    {
      KeyAgreement newDH = KeyAgreement.getInstance("DH");
      newDH.init(keyPair.getPrivate());
      return newDH;
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
  
  private KeyPair generateKeyPair() throws TorException
  {
    try
    {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
      keyGen.initialize(DH_PARAMETER_SPEC);
      return keyGen.generateKeyPair();
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
}
