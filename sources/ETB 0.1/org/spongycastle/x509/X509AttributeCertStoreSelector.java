package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.Target;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.Targets;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.util.Selector;














/**
 * @deprecated
 */
public class X509AttributeCertStoreSelector
  implements Selector
{
  private AttributeCertificateHolder holder;
  private AttributeCertificateIssuer issuer;
  private BigInteger serialNumber;
  private Date attributeCertificateValid;
  private X509AttributeCertificate attributeCert;
  private Collection targetNames = new HashSet();
  
  private Collection targetGroups = new HashSet();
  





  public X509AttributeCertStoreSelector() {}
  





  public boolean match(Object obj)
  {
    if (!(obj instanceof X509AttributeCertificate))
    {
      return false;
    }
    
    X509AttributeCertificate attrCert = (X509AttributeCertificate)obj;
    
    if (attributeCert != null)
    {
      if (!attributeCert.equals(attrCert))
      {
        return false;
      }
    }
    if (serialNumber != null)
    {
      if (!attrCert.getSerialNumber().equals(serialNumber))
      {
        return false;
      }
    }
    if (holder != null)
    {
      if (!attrCert.getHolder().equals(holder))
      {
        return false;
      }
    }
    if (issuer != null)
    {
      if (!attrCert.getIssuer().equals(issuer))
      {
        return false;
      }
    }
    
    if (attributeCertificateValid != null)
    {
      try
      {
        attrCert.checkValidity(attributeCertificateValid);
      }
      catch (CertificateExpiredException e)
      {
        return false;
      }
      catch (CertificateNotYetValidException e)
      {
        return false;
      }
    }
    if ((!targetNames.isEmpty()) || (!targetGroups.isEmpty()))
    {


      byte[] targetInfoExt = attrCert.getExtensionValue(X509Extensions.TargetInformation.getId());
      if (targetInfoExt != null)
      {

        try
        {

          targetinfo = TargetInformation.getInstance(new ASN1InputStream(
          
            ((DEROctetString)DEROctetString.fromByteArray(targetInfoExt)).getOctets())
            .readObject());
        }
        catch (IOException e) {
          TargetInformation targetinfo;
          return false;
        }
        catch (IllegalArgumentException e)
        {
          return false; }
        TargetInformation targetinfo;
        Targets[] targetss = targetinfo.getTargetsObjects();
        if (!targetNames.isEmpty())
        {
          boolean found = false;
          
          for (int i = 0; i < targetss.length; i++)
          {
            Targets t = targetss[i];
            Target[] targets = t.getTargets();
            for (int j = 0; j < targets.length; j++)
            {
              if (targetNames.contains(GeneralName.getInstance(targets[j]
                .getTargetName())))
              {
                found = true;
                break;
              }
            }
          }
          if (!found)
          {
            return false;
          }
        }
        if (!targetGroups.isEmpty())
        {
          boolean found = false;
          
          for (int i = 0; i < targetss.length; i++)
          {
            Targets t = targetss[i];
            Target[] targets = t.getTargets();
            for (int j = 0; j < targets.length; j++)
            {
              if (targetGroups.contains(GeneralName.getInstance(targets[j]
                .getTargetGroup())))
              {
                found = true;
                break;
              }
            }
          }
          if (!found)
          {
            return false;
          }
        }
      }
    }
    return true;
  }
  





  public Object clone()
  {
    X509AttributeCertStoreSelector sel = new X509AttributeCertStoreSelector();
    attributeCert = attributeCert;
    attributeCertificateValid = getAttributeCertificateValid();
    holder = holder;
    issuer = issuer;
    serialNumber = serialNumber;
    targetGroups = getTargetGroups();
    targetNames = getTargetNames();
    return sel;
  }
  





  public X509AttributeCertificate getAttributeCert()
  {
    return attributeCert;
  }
  






  public void setAttributeCert(X509AttributeCertificate attributeCert)
  {
    this.attributeCert = attributeCert;
  }
  





  public Date getAttributeCertificateValid()
  {
    if (attributeCertificateValid != null)
    {
      return new Date(attributeCertificateValid.getTime());
    }
    
    return null;
  }
  







  public void setAttributeCertificateValid(Date attributeCertificateValid)
  {
    if (attributeCertificateValid != null)
    {

      this.attributeCertificateValid = new Date(attributeCertificateValid.getTime());
    }
    else
    {
      this.attributeCertificateValid = null;
    }
  }
  





  public AttributeCertificateHolder getHolder()
  {
    return holder;
  }
  





  public void setHolder(AttributeCertificateHolder holder)
  {
    this.holder = holder;
  }
  





  public AttributeCertificateIssuer getIssuer()
  {
    return issuer;
  }
  






  public void setIssuer(AttributeCertificateIssuer issuer)
  {
    this.issuer = issuer;
  }
  





  public BigInteger getSerialNumber()
  {
    return serialNumber;
  }
  






  public void setSerialNumber(BigInteger serialNumber)
  {
    this.serialNumber = serialNumber;
  }
  












  public void addTargetName(GeneralName name)
  {
    targetNames.add(name);
  }
  












  public void addTargetName(byte[] name)
    throws IOException
  {
    addTargetName(GeneralName.getInstance(ASN1Primitive.fromByteArray(name)));
  }
  











  public void setTargetNames(Collection names)
    throws IOException
  {
    targetNames = extractGeneralNames(names);
  }
  









  public Collection getTargetNames()
  {
    return Collections.unmodifiableCollection(targetNames);
  }
  












  public void addTargetGroup(GeneralName group)
  {
    targetGroups.add(group);
  }
  












  public void addTargetGroup(byte[] name)
    throws IOException
  {
    addTargetGroup(GeneralName.getInstance(ASN1Primitive.fromByteArray(name)));
  }
  











  public void setTargetGroups(Collection names)
    throws IOException
  {
    targetGroups = extractGeneralNames(names);
  }
  










  public Collection getTargetGroups()
  {
    return Collections.unmodifiableCollection(targetGroups);
  }
  
  private Set extractGeneralNames(Collection names)
    throws IOException
  {
    if ((names == null) || (names.isEmpty()))
    {
      return new HashSet();
    }
    Set temp = new HashSet();
    for (Iterator it = names.iterator(); it.hasNext();)
    {
      Object o = it.next();
      if ((o instanceof GeneralName))
      {
        temp.add(o);
      }
      else
      {
        temp.add(GeneralName.getInstance(ASN1Primitive.fromByteArray((byte[])o)));
      }
    }
    return temp;
  }
}
