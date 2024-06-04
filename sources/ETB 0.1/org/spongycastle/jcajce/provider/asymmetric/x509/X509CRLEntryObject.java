package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CRLReason;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.TBSCertList.CRLEntry;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.util.Strings;







class X509CRLEntryObject
  extends X509CRLEntry
{
  private TBSCertList.CRLEntry c;
  private X500Name certificateIssuer;
  private int hashValue;
  private boolean isHashValueSet;
  
  protected X509CRLEntryObject(TBSCertList.CRLEntry c)
  {
    this.c = c;
    certificateIssuer = null;
  }
  




















  protected X509CRLEntryObject(TBSCertList.CRLEntry c, boolean isIndirect, X500Name previousCertificateIssuer)
  {
    this.c = c;
    certificateIssuer = loadCertificateIssuer(isIndirect, previousCertificateIssuer);
  }
  




  public boolean hasUnsupportedCriticalExtension()
  {
    Set extns = getCriticalExtensionOIDs();
    
    return (extns != null) && (!extns.isEmpty());
  }
  
  private X500Name loadCertificateIssuer(boolean isIndirect, X500Name previousCertificateIssuer)
  {
    if (!isIndirect)
    {
      return null;
    }
    
    Extension ext = getExtension(Extension.certificateIssuer);
    if (ext == null)
    {
      return previousCertificateIssuer;
    }
    
    try
    {
      GeneralName[] names = GeneralNames.getInstance(ext.getParsedValue()).getNames();
      for (int i = 0; i < names.length; i++)
      {
        if (names[i].getTagNo() == 4)
        {
          return X500Name.getInstance(names[i].getName());
        }
      }
      return null;
    }
    catch (Exception e) {}
    
    return null;
  }
  

  public X500Principal getCertificateIssuer()
  {
    if (certificateIssuer == null)
    {
      return null;
    }
    try
    {
      return new X500Principal(certificateIssuer.getEncoded());
    }
    catch (IOException e) {}
    
    return null;
  }
  

  private Set getExtensionOIDs(boolean critical)
  {
    Extensions extensions = c.getExtensions();
    
    if (extensions != null)
    {
      Set set = new HashSet();
      Enumeration e = extensions.oids();
      
      while (e.hasMoreElements())
      {
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
        Extension ext = extensions.getExtension(oid);
        
        if (critical == ext.isCritical())
        {
          set.add(oid.getId());
        }
      }
      
      return set;
    }
    
    return null;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  private Extension getExtension(ASN1ObjectIdentifier oid)
  {
    Extensions exts = c.getExtensions();
    
    if (exts != null)
    {
      return exts.getExtension(oid);
    }
    
    return null;
  }
  
  public byte[] getExtensionValue(String oid)
  {
    Extension ext = getExtension(new ASN1ObjectIdentifier(oid));
    
    if (ext != null)
    {
      try
      {
        return ext.getExtnValue().getEncoded();
      }
      catch (Exception e)
      {
        throw new IllegalStateException("Exception encoding: " + e.toString());
      }
    }
    
    return null;
  }
  




  public int hashCode()
  {
    if (!isHashValueSet)
    {
      hashValue = super.hashCode();
      isHashValueSet = true;
    }
    
    return hashValue;
  }
  
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if ((o instanceof X509CRLEntryObject))
    {
      X509CRLEntryObject other = (X509CRLEntryObject)o;
      
      return c.equals(c);
    }
    
    return super.equals(this);
  }
  
  public byte[] getEncoded()
    throws CRLException
  {
    try
    {
      return c.getEncoded("DER");
    }
    catch (IOException e)
    {
      throw new CRLException(e.toString());
    }
  }
  
  public BigInteger getSerialNumber()
  {
    return c.getUserCertificate().getValue();
  }
  
  public Date getRevocationDate()
  {
    return c.getRevocationDate().getDate();
  }
  
  public boolean hasExtensions()
  {
    return c.getExtensions() != null;
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("      userCertificate: ").append(getSerialNumber()).append(nl);
    buf.append("       revocationDate: ").append(getRevocationDate()).append(nl);
    buf.append("       certificateIssuer: ").append(getCertificateIssuer()).append(nl);
    
    Extensions extensions = c.getExtensions();
    
    if (extensions != null)
    {
      Enumeration e = extensions.oids();
      if (e.hasMoreElements())
      {
        buf.append("   crlEntryExtensions:").append(nl);
        
        while (e.hasMoreElements())
        {
          ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
          Extension ext = extensions.getExtension(oid);
          if (ext.getExtnValue() != null)
          {
            byte[] octs = ext.getExtnValue().getOctets();
            ASN1InputStream dIn = new ASN1InputStream(octs);
            buf.append("                       critical(").append(ext.isCritical()).append(") ");
            try
            {
              if (oid.equals(Extension.reasonCode))
              {
                buf.append(CRLReason.getInstance(ASN1Enumerated.getInstance(dIn.readObject()))).append(nl);
              }
              else if (oid.equals(Extension.certificateIssuer))
              {
                buf.append("Certificate issuer: ").append(GeneralNames.getInstance(dIn.readObject())).append(nl);
              }
              else
              {
                buf.append(oid.getId());
                buf.append(" value = ").append(ASN1Dump.dumpAsString(dIn.readObject())).append(nl);
              }
            }
            catch (Exception ex)
            {
              buf.append(oid.getId());
              buf.append(" value = ").append("*****").append(nl);
            }
          }
          else
          {
            buf.append(nl);
          }
        }
      }
    }
    
    return buf.toString();
  }
}
