package org.spongycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V1TBSCertificateGenerator;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.X509Principal;





/**
 * @deprecated
 */
public class X509V1CertificateGenerator
{
  private final JcaJceHelper bcHelper = new BCJcaJceHelper();
  private final CertificateFactory certificateFactory = new CertificateFactory();
  
  private V1TBSCertificateGenerator tbsGen;
  private ASN1ObjectIdentifier sigOID;
  private AlgorithmIdentifier sigAlgId;
  private String signatureAlgorithm;
  
  public X509V1CertificateGenerator()
  {
    tbsGen = new V1TBSCertificateGenerator();
  }
  



  public void reset()
  {
    tbsGen = new V1TBSCertificateGenerator();
  }
  




  public void setSerialNumber(BigInteger serialNumber)
  {
    if (serialNumber.compareTo(BigInteger.ZERO) <= 0)
    {
      throw new IllegalArgumentException("serial number must be a positive integer");
    }
    
    tbsGen.setSerialNumber(new ASN1Integer(serialNumber));
  }
  





  public void setIssuerDN(X500Principal issuer)
  {
    try
    {
      tbsGen.setIssuer(new X509Principal(issuer.getEncoded()));
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("can't process principal: " + e);
    }
  }
  





  public void setIssuerDN(X509Name issuer)
  {
    tbsGen.setIssuer(issuer);
  }
  

  public void setNotBefore(Date date)
  {
    tbsGen.setStartDate(new Time(date));
  }
  

  public void setNotAfter(Date date)
  {
    tbsGen.setEndDate(new Time(date));
  }
  




  public void setSubjectDN(X500Principal subject)
  {
    try
    {
      tbsGen.setSubject(new X509Principal(subject.getEncoded()));
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("can't process principal: " + e);
    }
  }
  




  public void setSubjectDN(X509Name subject)
  {
    tbsGen.setSubject(subject);
  }
  

  public void setPublicKey(PublicKey key)
  {
    try
    {
      tbsGen.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(key.getEncoded()));
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("unable to process key - " + e.toString());
    }
  }
  







  public void setSignatureAlgorithm(String signatureAlgorithm)
  {
    this.signatureAlgorithm = signatureAlgorithm;
    
    try
    {
      sigOID = X509Util.getAlgorithmOID(signatureAlgorithm);
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("Unknown signature type requested");
    }
    
    sigAlgId = X509Util.getSigAlgID(sigOID, signatureAlgorithm);
    
    tbsGen.setSignature(sigAlgId);
  }
  



  /**
   * @deprecated
   */
  public X509Certificate generateX509Certificate(PrivateKey key)
    throws SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      return generateX509Certificate(key, "SC", null);
    }
    catch (NoSuchProviderException e)
    {
      throw new SecurityException("BC provider not installed!");
    }
  }
  




  /**
   * @deprecated
   */
  public X509Certificate generateX509Certificate(PrivateKey key, SecureRandom random)
    throws SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      return generateX509Certificate(key, "SC", random);
    }
    catch (NoSuchProviderException e)
    {
      throw new SecurityException("BC provider not installed!");
    }
  }
  





  /**
   * @deprecated
   */
  public X509Certificate generateX509Certificate(PrivateKey key, String provider)
    throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException
  {
    return generateX509Certificate(key, provider, null);
  }
  






  /**
   * @deprecated
   */
  public X509Certificate generateX509Certificate(PrivateKey key, String provider, SecureRandom random)
    throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      return generate(key, provider, random);
    }
    catch (NoSuchProviderException e)
    {
      throw e;
    }
    catch (SignatureException e)
    {
      throw e;
    }
    catch (InvalidKeyException e)
    {
      throw e;
    }
    catch (GeneralSecurityException e)
    {
      throw new SecurityException("exception: " + e);
    }
  }
  









  public X509Certificate generate(PrivateKey key)
    throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    return generate(key, (SecureRandom)null);
  }
  










  public X509Certificate generate(PrivateKey key, SecureRandom random)
    throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    TBSCertificate tbsCert = tbsGen.generateTBSCertificate();
    

    try
    {
      signature = X509Util.calculateSignature(sigOID, signatureAlgorithm, key, random, tbsCert);
    }
    catch (IOException e) {
      byte[] signature;
      throw new ExtCertificateEncodingException("exception encoding TBS cert", e);
    }
    byte[] signature;
    return generateJcaObject(tbsCert, signature);
  }
  







  public X509Certificate generate(PrivateKey key, String provider)
    throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    return generate(key, provider, null);
  }
  








  public X509Certificate generate(PrivateKey key, String provider, SecureRandom random)
    throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    TBSCertificate tbsCert = tbsGen.generateTBSCertificate();
    

    try
    {
      signature = X509Util.calculateSignature(sigOID, signatureAlgorithm, provider, key, random, tbsCert);
    }
    catch (IOException e) {
      byte[] signature;
      throw new ExtCertificateEncodingException("exception encoding TBS cert", e);
    }
    byte[] signature;
    return generateJcaObject(tbsCert, signature);
  }
  
  private X509Certificate generateJcaObject(TBSCertificate tbsCert, byte[] signature)
    throws CertificateEncodingException
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(tbsCert);
    v.add(sigAlgId);
    v.add(new DERBitString(signature));
    
    try
    {
      return (X509Certificate)certificateFactory.engineGenerateCertificate(new ByteArrayInputStream(new DERSequence(v)
        .getEncoded("DER")));
    }
    catch (Exception e)
    {
      throw new ExtCertificateEncodingException("exception producing certificate object", e);
    }
  }
  





  public Iterator getSignatureAlgNames()
  {
    return X509Util.getAlgNames();
  }
}
