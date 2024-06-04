package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.Util;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class AuthorityKeyCertificate
  implements Cloneable
{
  private static final Logger LOG = LoggerFactory.getLogger(AuthorityKeyCertificate.class);
  

  private final String authorityKeyCertificateStr;
  

  private static Pattern pattern;
  

  private final Fingerprint dirIdentityKeyDigest;
  
  private final Date dirKeyPublished;
  
  private final Date dirKeyExpires;
  
  private final RSAPublicKey dirIdentityKey;
  
  private final RSAPublicKey dirSigningKey;
  
  private final Fingerprint dirSigningKeyDigest;
  

  static
  {
    try
    {
      pattern = Pattern.compile("^(dir-key-certificate-version 3\nfingerprint (\\w+)\ndir-key-published ([0-9: \\-]+)\ndir-key-expires ([0-9: \\-]+)\ndir-identity-key\n(-----BEGIN RSA PUBLIC KEY.*?END RSA PUBLIC KEY-----)\ndir-signing-key\n(-----BEGIN RSA PUBLIC KEY.*?END RSA PUBLIC KEY-----)\n(dir-key-crosscert\n-----BEGIN ID SIGNATURE-----(.*?)-----END ID SIGNATURE-----\n){0,1}dir-key-certification\n)-----BEGIN SIGNATURE-----(.*?)-----END SIGNATURE-----", 43);






    }
    catch (Exception e)
    {





      LOG.error("could not initialze class AuthorityKeyCertificate", e);
    }
  }
  







  public AuthorityKeyCertificate(String authorityKeyCertificateStr)
    throws TorException
  {
    this.authorityKeyCertificateStr = authorityKeyCertificateStr;
    

    Matcher m = pattern.matcher(authorityKeyCertificateStr);
    m.find();
    

    String fingerprintStr = m.group(2);
    
    dirIdentityKeyDigest = new FingerprintImpl(DatatypeConverter.parseHexBinary(fingerprintStr));
    

    String dirKeyPublishedStr = m.group(3);
    dirKeyPublished = Util.parseUtcTimestamp(dirKeyPublishedStr);
    String dirKeyExpiresStr = m.group(4);
    dirKeyExpires = Util.parseUtcTimestamp(dirKeyExpiresStr);
    

    String dirIdentityKeyStr = m.group(5);
    dirIdentityKey = Encryption.extractPublicRSAKey(dirIdentityKeyStr);
    String dirSigningKeyStr = m.group(6);
    dirSigningKey = Encryption.extractPublicRSAKey(dirSigningKeyStr);
    
    dirSigningKeyDigest = new FingerprintImpl(Encryption.getDigest(
      Encryption.getPKCS1EncodingFromRSAPublicKey(dirSigningKey)));
    


    try
    {
      byte[] dirIdentityKeyPkcs = Encryption.getPKCS1EncodingFromRSAPublicKey(dirIdentityKey);
      
      byte[] dirIdentityKeyHash = Encryption.getDigest(dirIdentityKeyPkcs);
      
      if (!new FingerprintImpl(dirIdentityKeyHash).equals(dirIdentityKeyDigest))
      {
        throw new TorException("dirIdentityKey hash(" + new FingerprintImpl(dirIdentityKeyHash) + ")!=fingerprint(" + dirIdentityKeyDigest + ")");
      }
      

    }
    catch (TorException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      LOG.warn("error while verify identity-key against fingerprint", e);
      
      throw new TorException("error while verify identity-key against fingerprint: " + e);
    }
    


    String dirKeyCertificationStr = m.group(9);
    while (dirIdentityKeyStr.length() % 4 != 0)
    {
      dirIdentityKeyStr = dirIdentityKeyStr + "=";
    }
    
    byte[] dirKeyCertification = DatatypeConverter.parseBase64Binary(dirKeyCertificationStr);
    String signedDataStr = m.group(1);
    byte[] signedData = null;
    try
    {
      signedData = signedDataStr.getBytes("UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      LOG.warn("unexpected", e);
    }
    if (!Encryption.verifySignature(dirKeyCertification, dirIdentityKey, signedData))
    {

      throw new TorException("dirKeyCertification check failed for fingerprint=" + dirIdentityKeyDigest);
    }
  }
  









  public String toString()
  {
    return "AuthorityKeyCertificate(fingerprint=" + dirIdentityKeyDigest + ",dirKeyPublished=" + Util.formatUtcTimestamp(dirKeyPublished) + ",dirKeyExpires=" + Util.formatUtcTimestamp(dirKeyExpires) + ",dirIdentityKey=" + dirIdentityKey + ",dirSigningKey=" + dirSigningKey + ")";
  }
  





  public String getAuthorityKeyCertificateStr()
  {
    return authorityKeyCertificateStr;
  }
  



  public Fingerprint getDirIdentityKeyDigest()
  {
    return dirIdentityKeyDigest;
  }
  
  public Date getDirKeyPublished()
  {
    return dirKeyPublished;
  }
  
  public Date getDirKeyExpires()
  {
    return dirKeyExpires;
  }
  
  public RSAPublicKey getDirIdentityKey()
  {
    return dirIdentityKey;
  }
  
  public RSAPublicKey getDirSigningKey()
  {
    return dirSigningKey;
  }
  
  public Fingerprint getDirSigningKeyDigest()
  {
    return dirSigningKeyDigest;
  }
}
