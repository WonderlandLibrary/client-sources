package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.Holder;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.ObjectDigestInfo;
import org.spongycastle.jce.PrincipalUtil;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;













/**
 * @deprecated
 */
public class AttributeCertificateHolder
  implements CertSelector, Selector
{
  final Holder holder;
  
  AttributeCertificateHolder(ASN1Sequence seq)
  {
    holder = Holder.getInstance(seq);
  }
  


  public AttributeCertificateHolder(X509Principal issuerName, BigInteger serialNumber)
  {
    holder = new Holder(new IssuerSerial(GeneralNames.getInstance(new DERSequence(new GeneralName(issuerName))), new ASN1Integer(serialNumber)));
  }
  


  public AttributeCertificateHolder(X500Principal issuerName, BigInteger serialNumber)
  {
    this(X509Util.convertPrincipal(issuerName), serialNumber);
  }
  


  public AttributeCertificateHolder(X509Certificate cert)
    throws CertificateParsingException
  {
    try
    {
      name = PrincipalUtil.getIssuerX509Principal(cert);
    }
    catch (Exception e) {
      X509Principal name;
      throw new CertificateParsingException(e.getMessage());
    }
    
    X509Principal name;
    holder = new Holder(new IssuerSerial(generateGeneralNames(name), new ASN1Integer(cert.getSerialNumber())));
  }
  
  public AttributeCertificateHolder(X509Principal principal)
  {
    holder = new Holder(generateGeneralNames(principal));
  }
  
  public AttributeCertificateHolder(X500Principal principal)
  {
    this(X509Util.convertPrincipal(principal));
  }
  


























  public AttributeCertificateHolder(int digestedObjectType, String digestAlgorithm, String otherObjectTypeID, byte[] objectDigest)
  {
    holder = new Holder(new ObjectDigestInfo(digestedObjectType, new ASN1ObjectIdentifier(otherObjectTypeID), new AlgorithmIdentifier(new ASN1ObjectIdentifier(digestAlgorithm)), Arrays.clone(objectDigest)));
  }
  














  public int getDigestedObjectType()
  {
    if (holder.getObjectDigestInfo() != null)
    {
      return 
        holder.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
    }
    return -1;
  }
  






  public String getDigestAlgorithm()
  {
    if (holder.getObjectDigestInfo() != null)
    {
      return 
        holder.getObjectDigestInfo().getDigestAlgorithm().getAlgorithm().getId();
    }
    return null;
  }
  





  public byte[] getObjectDigest()
  {
    if (holder.getObjectDigestInfo() != null)
    {
      return holder.getObjectDigestInfo().getObjectDigest().getBytes();
    }
    return null;
  }
  






  public String getOtherObjectTypeID()
  {
    if (holder.getObjectDigestInfo() != null)
    {
      holder.getObjectDigestInfo().getOtherObjectTypeID().getId();
    }
    return null;
  }
  
  private GeneralNames generateGeneralNames(X509Principal principal)
  {
    return GeneralNames.getInstance(new DERSequence(new GeneralName(principal)));
  }
  
  private boolean matchesDN(X509Principal subject, GeneralNames targets)
  {
    GeneralName[] names = targets.getNames();
    
    for (int i = 0; i != names.length; i++)
    {
      GeneralName gn = names[i];
      
      if (gn.getTagNo() == 4)
      {
        try
        {

          if (new X509Principal(gn.getName().toASN1Primitive().getEncoded()).equals(subject))
          {
            return true;
          }
        }
        catch (IOException localIOException) {}
      }
    }
    


    return false;
  }
  
  private Object[] getNames(GeneralName[] names)
  {
    List l = new ArrayList(names.length);
    
    for (int i = 0; i != names.length; i++)
    {
      if (names[i].getTagNo() == 4)
      {
        try
        {
          l.add(new X500Principal(names[i]
            .getName().toASN1Primitive().getEncoded()));
        }
        catch (IOException e)
        {
          throw new RuntimeException("badly formed Name object");
        }
      }
    }
    
    return l.toArray(new Object[l.size()]);
  }
  
  private Principal[] getPrincipals(GeneralNames names)
  {
    Object[] p = getNames(names.getNames());
    List l = new ArrayList();
    
    for (int i = 0; i != p.length; i++)
    {
      if ((p[i] instanceof Principal))
      {
        l.add(p[i]);
      }
    }
    
    return (Principal[])l.toArray(new Principal[l.size()]);
  }
  







  public Principal[] getEntityNames()
  {
    if (holder.getEntityName() != null)
    {
      return getPrincipals(holder.getEntityName());
    }
    
    return null;
  }
  





  public Principal[] getIssuer()
  {
    if (holder.getBaseCertificateID() != null)
    {
      return getPrincipals(holder.getBaseCertificateID().getIssuer());
    }
    
    return null;
  }
  







  public BigInteger getSerialNumber()
  {
    if (holder.getBaseCertificateID() != null)
    {
      return holder.getBaseCertificateID().getSerial().getValue();
    }
    
    return null;
  }
  
  public Object clone()
  {
    return new AttributeCertificateHolder(
      (ASN1Sequence)holder.toASN1Primitive());
  }
  
  public boolean match(Certificate cert)
  {
    if (!(cert instanceof X509Certificate))
    {
      return false;
    }
    
    X509Certificate x509Cert = (X509Certificate)cert;
    
    try
    {
      if (holder.getBaseCertificateID() != null)
      {
        return (holder.getBaseCertificateID().getSerial().getValue().equals(x509Cert.getSerialNumber())) && 
          (matchesDN(PrincipalUtil.getIssuerX509Principal(x509Cert), holder.getBaseCertificateID().getIssuer()));
      }
      
      if (holder.getEntityName() != null)
      {
        if (matchesDN(PrincipalUtil.getSubjectX509Principal(x509Cert), holder
          .getEntityName()))
        {
          return true;
        }
      }
      if (holder.getObjectDigestInfo() != null)
      {
        MessageDigest md = null;
        try
        {
          md = MessageDigest.getInstance(getDigestAlgorithm(), "SC");

        }
        catch (Exception e)
        {
          return false;
        }
        switch (getDigestedObjectType())
        {

        case 0: 
          md.update(cert.getPublicKey().getEncoded());
          break;
        case 1: 
          md.update(cert.getEncoded());
        }
        
        if (!Arrays.areEqual(md.digest(), getObjectDigest()))
        {
          return false;
        }
      }
    }
    catch (CertificateEncodingException e)
    {
      return false;
    }
    
    return false;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    
    if (!(obj instanceof AttributeCertificateHolder))
    {
      return false;
    }
    
    AttributeCertificateHolder other = (AttributeCertificateHolder)obj;
    
    return holder.equals(holder);
  }
  
  public int hashCode()
  {
    return holder.hashCode();
  }
  
  public boolean match(Object obj)
  {
    if (!(obj instanceof X509Certificate))
    {
      return false;
    }
    
    return match((Certificate)obj);
  }
}
