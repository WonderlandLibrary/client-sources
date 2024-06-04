package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.extension.X509ExtensionUtil;









public class X509CRLStoreSelector
  extends X509CRLSelector
  implements Selector
{
  private boolean deltaCRLIndicator = false;
  
  private boolean completeCRLEnabled = false;
  
  private BigInteger maxBaseCRLNumber = null;
  
  private byte[] issuingDistributionPoint = null;
  
  private boolean issuingDistributionPointEnabled = false;
  


  private X509AttributeCertificate attrCertChecking;
  



  public X509CRLStoreSelector() {}
  


  public boolean isIssuingDistributionPointEnabled()
  {
    return issuingDistributionPointEnabled;
  }
  







  public void setIssuingDistributionPointEnabled(boolean issuingDistributionPointEnabled)
  {
    this.issuingDistributionPointEnabled = issuingDistributionPointEnabled;
  }
  











  public void setAttrCertificateChecking(X509AttributeCertificate attrCert)
  {
    attrCertChecking = attrCert;
  }
  






  public X509AttributeCertificate getAttrCertificateChecking()
  {
    return attrCertChecking;
  }
  
  public boolean match(Object obj)
  {
    if (!(obj instanceof X509CRL))
    {
      return false;
    }
    X509CRL crl = (X509CRL)obj;
    ASN1Integer dci = null;
    
    try
    {
      byte[] bytes = crl.getExtensionValue(X509Extensions.DeltaCRLIndicator.getId());
      if (bytes != null)
      {
        dci = ASN1Integer.getInstance(
          X509ExtensionUtil.fromExtensionValue(bytes));
      }
    }
    catch (Exception e)
    {
      return false;
    }
    if (isDeltaCRLIndicatorEnabled())
    {
      if (dci == null)
      {
        return false;
      }
    }
    if (isCompleteCRLEnabled())
    {
      if (dci != null)
      {
        return false;
      }
    }
    if (dci != null)
    {

      if (maxBaseCRLNumber != null)
      {
        if (dci.getPositiveValue().compareTo(maxBaseCRLNumber) == 1)
        {
          return false;
        }
      }
    }
    if (issuingDistributionPointEnabled)
    {

      byte[] idp = crl.getExtensionValue(X509Extensions.IssuingDistributionPoint
        .getId());
      if (issuingDistributionPoint == null)
      {
        if (idp != null)
        {
          return false;
        }
        

      }
      else if (!Arrays.areEqual(idp, issuingDistributionPoint))
      {
        return false;
      }
    }
    

    return super.match((X509CRL)obj);
  }
  
  public boolean match(CRL crl)
  {
    return match(crl);
  }
  







  public boolean isDeltaCRLIndicatorEnabled()
  {
    return deltaCRLIndicator;
  }
  










  public void setDeltaCRLIndicatorEnabled(boolean deltaCRLIndicator)
  {
    this.deltaCRLIndicator = deltaCRLIndicator;
  }
  








  public static X509CRLStoreSelector getInstance(X509CRLSelector selector)
  {
    if (selector == null)
    {
      throw new IllegalArgumentException("cannot create from null selector");
    }
    
    X509CRLStoreSelector cs = new X509CRLStoreSelector();
    cs.setCertificateChecking(selector.getCertificateChecking());
    cs.setDateAndTime(selector.getDateAndTime());
    try
    {
      cs.setIssuerNames(selector.getIssuerNames());

    }
    catch (IOException e)
    {
      throw new IllegalArgumentException(e.getMessage());
    }
    cs.setIssuers(selector.getIssuers());
    cs.setMaxCRLNumber(selector.getMaxCRL());
    cs.setMinCRLNumber(selector.getMinCRL());
    return cs;
  }
  
  public Object clone()
  {
    X509CRLStoreSelector sel = getInstance(this);
    deltaCRLIndicator = deltaCRLIndicator;
    completeCRLEnabled = completeCRLEnabled;
    maxBaseCRLNumber = maxBaseCRLNumber;
    attrCertChecking = attrCertChecking;
    issuingDistributionPointEnabled = issuingDistributionPointEnabled;
    issuingDistributionPoint = Arrays.clone(issuingDistributionPoint);
    return sel;
  }
  






  public boolean isCompleteCRLEnabled()
  {
    return completeCRLEnabled;
  }
  









  public void setCompleteCRLEnabled(boolean completeCRLEnabled)
  {
    this.completeCRLEnabled = completeCRLEnabled;
  }
  






  public BigInteger getMaxBaseCRLNumber()
  {
    return maxBaseCRLNumber;
  }
  










  public void setMaxBaseCRLNumber(BigInteger maxBaseCRLNumber)
  {
    this.maxBaseCRLNumber = maxBaseCRLNumber;
  }
  












  public byte[] getIssuingDistributionPoint()
  {
    return Arrays.clone(issuingDistributionPoint);
  }
  


















  public void setIssuingDistributionPoint(byte[] issuingDistributionPoint)
  {
    this.issuingDistributionPoint = Arrays.clone(issuingDistributionPoint);
  }
}
