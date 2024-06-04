package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.io.Streams;










public class CertificateFactory
  extends CertificateFactorySpi
{
  private final JcaJceHelper bcHelper = new BCJcaJceHelper();
  
  private static final PEMUtil PEM_CERT_PARSER = new PEMUtil("CERTIFICATE");
  private static final PEMUtil PEM_CRL_PARSER = new PEMUtil("CRL");
  private static final PEMUtil PEM_PKCS7_PARSER = new PEMUtil("PKCS7");
  
  private ASN1Set sData = null;
  private int sDataObjectCount = 0;
  private InputStream currentStream = null;
  
  private ASN1Set sCrlData = null;
  private int sCrlDataObjectCount = 0;
  private InputStream currentCrlStream = null;
  
  public CertificateFactory() {}
  
  private java.security.cert.Certificate readDERCertificate(ASN1InputStream dIn) throws IOException, CertificateParsingException
  {
    return getCertificate(ASN1Sequence.getInstance(dIn.readObject()));
  }
  

  private java.security.cert.Certificate readPEMCertificate(InputStream in)
    throws IOException, CertificateParsingException
  {
    return getCertificate(PEM_CERT_PARSER.readPEMObject(in));
  }
  
  private java.security.cert.Certificate getCertificate(ASN1Sequence seq)
    throws CertificateParsingException
  {
    if (seq == null)
    {
      return null;
    }
    
    if ((seq.size() > 1) && 
      ((seq.getObjectAt(0) instanceof ASN1ObjectIdentifier)))
    {
      if (seq.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData))
      {

        sData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)seq.getObjectAt(1), true)).getCertificates();
        
        return getCertificate();
      }
    }
    
    return new X509CertificateObject(bcHelper, 
      org.spongycastle.asn1.x509.Certificate.getInstance(seq));
  }
  
  private java.security.cert.Certificate getCertificate()
    throws CertificateParsingException
  {
    if (sData != null)
    {
      while (sDataObjectCount < sData.size())
      {
        Object obj = sData.getObjectAt(sDataObjectCount++);
        
        if ((obj instanceof ASN1Sequence))
        {
          return new X509CertificateObject(bcHelper, 
            org.spongycastle.asn1.x509.Certificate.getInstance(obj));
        }
      }
    }
    
    return null;
  }
  

  protected CRL createCRL(CertificateList c)
    throws CRLException
  {
    return new X509CRLObject(bcHelper, c);
  }
  

  private CRL readPEMCRL(InputStream in)
    throws IOException, CRLException
  {
    return getCRL(PEM_CRL_PARSER.readPEMObject(in));
  }
  

  private CRL readDERCRL(ASN1InputStream aIn)
    throws IOException, CRLException
  {
    return getCRL(ASN1Sequence.getInstance(aIn.readObject()));
  }
  
  private CRL getCRL(ASN1Sequence seq)
    throws CRLException
  {
    if (seq == null)
    {
      return null;
    }
    
    if ((seq.size() > 1) && 
      ((seq.getObjectAt(0) instanceof ASN1ObjectIdentifier)))
    {
      if (seq.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData))
      {

        sCrlData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)seq.getObjectAt(1), true)).getCRLs();
        
        return getCRL();
      }
    }
    
    return createCRL(
      CertificateList.getInstance(seq));
  }
  
  private CRL getCRL()
    throws CRLException
  {
    if ((sCrlData == null) || (sCrlDataObjectCount >= sCrlData.size()))
    {
      return null;
    }
    
    return createCRL(
      CertificateList.getInstance(sCrlData
      .getObjectAt(sCrlDataObjectCount++)));
  }
  





  public java.security.cert.Certificate engineGenerateCertificate(InputStream in)
    throws CertificateException
  {
    if (currentStream == null)
    {
      currentStream = in;
      sData = null;
      sDataObjectCount = 0;
    }
    else if (currentStream != in)
    {
      currentStream = in;
      sData = null;
      sDataObjectCount = 0;
    }
    
    try
    {
      if (sData != null)
      {
        if (sDataObjectCount != sData.size())
        {
          return getCertificate();
        }
        

        sData = null;
        sDataObjectCount = 0;
        return null;
      }
      
      InputStream pis;
      
      InputStream pis;
      if (in.markSupported())
      {
        pis = in;
      }
      else
      {
        pis = new ByteArrayInputStream(Streams.readAll(in));
      }
      
      pis.mark(1);
      int tag = pis.read();
      
      if (tag == -1)
      {
        return null;
      }
      
      pis.reset();
      if (tag != 48)
      {
        return readPEMCertificate(pis);
      }
      

      return readDERCertificate(new ASN1InputStream(pis));

    }
    catch (Exception e)
    {
      throw new ExCertificateException("parsing issue: " + e.getMessage(), e);
    }
  }
  






  public Collection engineGenerateCertificates(InputStream inStream)
    throws CertificateException
  {
    BufferedInputStream in = new BufferedInputStream(inStream);
    
    List certs = new ArrayList();
    java.security.cert.Certificate cert;
    while ((cert = engineGenerateCertificate(in)) != null)
    {
      certs.add(cert);
    }
    
    return certs;
  }
  





  public CRL engineGenerateCRL(InputStream in)
    throws CRLException
  {
    if (currentCrlStream == null)
    {
      currentCrlStream = in;
      sCrlData = null;
      sCrlDataObjectCount = 0;
    }
    else if (currentCrlStream != in)
    {
      currentCrlStream = in;
      sCrlData = null;
      sCrlDataObjectCount = 0;
    }
    
    try
    {
      if (sCrlData != null)
      {
        if (sCrlDataObjectCount != sCrlData.size())
        {
          return getCRL();
        }
        

        sCrlData = null;
        sCrlDataObjectCount = 0;
        return null;
      }
      
      InputStream pis;
      
      InputStream pis;
      if (in.markSupported())
      {
        pis = in;
      }
      else
      {
        pis = new ByteArrayInputStream(Streams.readAll(in));
      }
      
      pis.mark(1);
      int tag = pis.read();
      
      if (tag == -1)
      {
        return null;
      }
      
      pis.reset();
      if (tag != 48)
      {
        return readPEMCRL(pis);
      }
      

      return readDERCRL(new ASN1InputStream(pis, true));

    }
    catch (CRLException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new CRLException(e.toString());
    }
  }
  











  public Collection engineGenerateCRLs(InputStream inStream)
    throws CRLException
  {
    List crls = new ArrayList();
    BufferedInputStream in = new BufferedInputStream(inStream);
    CRL crl;
    while ((crl = engineGenerateCRL(in)) != null)
    {
      crls.add(crl);
    }
    
    return crls;
  }
  
  public Iterator engineGetCertPathEncodings()
  {
    return PKIXCertPath.certPathEncodings.iterator();
  }
  

  public CertPath engineGenerateCertPath(InputStream inStream)
    throws CertificateException
  {
    return engineGenerateCertPath(inStream, "PkiPath");
  }
  


  public CertPath engineGenerateCertPath(InputStream inStream, String encoding)
    throws CertificateException
  {
    return new PKIXCertPath(inStream, encoding);
  }
  

  public CertPath engineGenerateCertPath(List certificates)
    throws CertificateException
  {
    Iterator iter = certificates.iterator();
    
    while (iter.hasNext())
    {
      Object obj = iter.next();
      if ((obj != null) && 
      
        (!(obj instanceof X509Certificate)))
      {
        throw new CertificateException("list contains non X509Certificate object while creating CertPath\n" + obj.toString());
      }
    }
    
    return new PKIXCertPath(certificates);
  }
  
  private class ExCertificateException
    extends CertificateException
  {
    private Throwable cause;
    
    public ExCertificateException(Throwable cause)
    {
      this.cause = cause;
    }
    
    public ExCertificateException(String msg, Throwable cause)
    {
      super();
      
      this.cause = cause;
    }
    
    public Throwable getCause()
    {
      return cause;
    }
  }
}
