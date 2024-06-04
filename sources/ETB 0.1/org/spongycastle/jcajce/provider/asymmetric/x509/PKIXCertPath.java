package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;








public class PKIXCertPath
  extends CertPath
{
  private final JcaJceHelper helper = new BCJcaJceHelper();
  static final List certPathEncodings;
  private List certificates;
  
  static
  {
    List encodings = new ArrayList();
    encodings.add("PkiPath");
    encodings.add("PEM");
    encodings.add("PKCS7");
    certPathEncodings = Collections.unmodifiableList(encodings);
  }
  






  private List sortCerts(List certs)
  {
    if (certs.size() < 2)
    {
      return certs;
    }
    
    X500Principal issuer = ((X509Certificate)certs.get(0)).getIssuerX500Principal();
    boolean okay = true;
    
    for (int i = 1; i != certs.size(); i++)
    {
      X509Certificate cert = (X509Certificate)certs.get(i);
      
      if (issuer.equals(cert.getSubjectX500Principal()))
      {
        issuer = ((X509Certificate)certs.get(i)).getIssuerX500Principal();
      }
      else
      {
        okay = false;
        break;
      }
    }
    
    if (okay)
    {
      return certs;
    }
    

    List retList = new ArrayList(certs.size());
    List orig = new ArrayList(certs);
    
    for (int i = 0; i < certs.size(); i++)
    {
      X509Certificate cert = (X509Certificate)certs.get(i);
      boolean found = false;
      
      X500Principal subject = cert.getSubjectX500Principal();
      
      for (int j = 0; j != certs.size(); j++)
      {
        X509Certificate c = (X509Certificate)certs.get(j);
        if (c.getIssuerX500Principal().equals(subject))
        {
          found = true;
          break;
        }
      }
      
      if (!found)
      {
        retList.add(cert);
        certs.remove(i);
      }
    }
    

    if (retList.size() > 1)
    {
      return orig;
    }
    
    for (int i = 0; i != retList.size(); i++)
    {
      issuer = ((X509Certificate)retList.get(i)).getIssuerX500Principal();
      
      for (int j = 0; j < certs.size(); j++)
      {
        X509Certificate c = (X509Certificate)certs.get(j);
        if (issuer.equals(c.getSubjectX500Principal()))
        {
          retList.add(c);
          certs.remove(j);
          break;
        }
      }
    }
    

    if (certs.size() > 0)
    {
      return orig;
    }
    
    return retList;
  }
  
  PKIXCertPath(List certificates)
  {
    super("X.509");
    this.certificates = sortCerts(new ArrayList(certificates));
  }
  







  PKIXCertPath(InputStream inStream, String encoding)
    throws CertificateException
  {
    super("X.509");
    try
    {
      if (encoding.equalsIgnoreCase("PkiPath"))
      {
        ASN1InputStream derInStream = new ASN1InputStream(inStream);
        ASN1Primitive derObject = derInStream.readObject();
        if (!(derObject instanceof ASN1Sequence))
        {
          throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
        }
        Enumeration e = ((ASN1Sequence)derObject).getObjects();
        certificates = new ArrayList();
        CertificateFactory certFactory = helper.createCertificateFactory("X.509");
        while (e.hasMoreElements())
        {
          ASN1Encodable element = (ASN1Encodable)e.nextElement();
          byte[] encoded = element.toASN1Primitive().getEncoded("DER");
          certificates.add(0, certFactory.generateCertificate(new ByteArrayInputStream(encoded)));
        }
        
      }
      else if ((encoding.equalsIgnoreCase("PKCS7")) || (encoding.equalsIgnoreCase("PEM")))
      {
        inStream = new BufferedInputStream(inStream);
        certificates = new ArrayList();
        CertificateFactory certFactory = helper.createCertificateFactory("X.509");
        Certificate cert;
        while ((cert = certFactory.generateCertificate(inStream)) != null)
        {
          certificates.add(cert);
        }
      }
      else
      {
        throw new CertificateException("unsupported encoding: " + encoding);
      }
    }
    catch (IOException ex)
    {
      throw new CertificateException("IOException throw while decoding CertPath:\n" + ex.toString());
    }
    catch (NoSuchProviderException ex)
    {
      throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + ex.toString());
    }
    
    certificates = sortCerts(certificates);
  }
  








  public Iterator getEncodings()
  {
    return certPathEncodings.iterator();
  }
  







  public byte[] getEncoded()
    throws CertificateEncodingException
  {
    Iterator iter = getEncodings();
    if (iter.hasNext())
    {
      Object enc = iter.next();
      if ((enc instanceof String))
      {
        return getEncoded((String)enc);
      }
    }
    return null;
  }
  










  public byte[] getEncoded(String encoding)
    throws CertificateEncodingException
  {
    if (encoding.equalsIgnoreCase("PkiPath"))
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      
      ListIterator iter = certificates.listIterator(certificates.size());
      while (iter.hasPrevious())
      {
        v.add(toASN1Object((X509Certificate)iter.previous()));
      }
      
      return toDEREncoded(new DERSequence(v));
    }
    if (encoding.equalsIgnoreCase("PKCS7"))
    {
      ContentInfo encInfo = new ContentInfo(PKCSObjectIdentifiers.data, null);
      
      ASN1EncodableVector v = new ASN1EncodableVector();
      for (int i = 0; i != certificates.size(); i++)
      {
        v.add(toASN1Object((X509Certificate)certificates.get(i)));
      }
      
      SignedData sd = new SignedData(new ASN1Integer(1L), new DERSet(), encInfo, new DERSet(v), null, new DERSet());
      






      return toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, sd));
    }
    
    if (encoding.equalsIgnoreCase("PEM"))
    {
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      PemWriter pWrt = new PemWriter(new OutputStreamWriter(bOut));
      
      try
      {
        for (int i = 0; i != certificates.size(); i++)
        {
          pWrt.writeObject(new PemObject("CERTIFICATE", ((X509Certificate)certificates.get(i)).getEncoded()));
        }
        
        pWrt.close();
      }
      catch (Exception e)
      {
        throw new CertificateEncodingException("can't encode certificate for PEM encoded path");
      }
      
      return bOut.toByteArray();
    }
    

    throw new CertificateEncodingException("unsupported encoding: " + encoding);
  }
  







  public List getCertificates()
  {
    return Collections.unmodifiableList(new ArrayList(certificates));
  }
  








  private ASN1Primitive toASN1Object(X509Certificate cert)
    throws CertificateEncodingException
  {
    try
    {
      return new ASN1InputStream(cert.getEncoded()).readObject();
    }
    catch (Exception e)
    {
      throw new CertificateEncodingException("Exception while encoding certificate: " + e.toString());
    }
  }
  
  private byte[] toDEREncoded(ASN1Encodable obj)
    throws CertificateEncodingException
  {
    try
    {
      return obj.toASN1Primitive().getEncoded("DER");
    }
    catch (IOException e)
    {
      throw new CertificateEncodingException("Exception thrown: " + e);
    }
  }
}
