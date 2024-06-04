package org.spongycastle.jcajce;

import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;




public class PKIXCRLStoreSelector<T extends CRL>
  implements Selector<T>
{
  private final CRLSelector baseSelector;
  private final boolean deltaCRLIndicator;
  private final boolean completeCRLEnabled;
  private final BigInteger maxBaseCRLNumber;
  private final byte[] issuingDistributionPoint;
  private final boolean issuingDistributionPointEnabled;
  
  public static class Builder
  {
    private final CRLSelector baseSelector;
    private boolean deltaCRLIndicator = false;
    private boolean completeCRLEnabled = false;
    private BigInteger maxBaseCRLNumber = null;
    private byte[] issuingDistributionPoint = null;
    private boolean issuingDistributionPointEnabled = false;
    





    public Builder(CRLSelector crlSelector)
    {
      baseSelector = ((CRLSelector)crlSelector.clone());
    }
    










    public Builder setCompleteCRLEnabled(boolean completeCRLEnabled)
    {
      this.completeCRLEnabled = completeCRLEnabled;
      
      return this;
    }
    










    public Builder setDeltaCRLIndicatorEnabled(boolean deltaCRLIndicator)
    {
      this.deltaCRLIndicator = deltaCRLIndicator;
      
      return this;
    }
    










    public void setMaxBaseCRLNumber(BigInteger maxBaseCRLNumber)
    {
      this.maxBaseCRLNumber = maxBaseCRLNumber;
    }
    







    public void setIssuingDistributionPointEnabled(boolean issuingDistributionPointEnabled)
    {
      this.issuingDistributionPointEnabled = issuingDistributionPointEnabled;
    }
    


















    public void setIssuingDistributionPoint(byte[] issuingDistributionPoint)
    {
      this.issuingDistributionPoint = Arrays.clone(issuingDistributionPoint);
    }
    





    public PKIXCRLStoreSelector<? extends CRL> build()
    {
      return new PKIXCRLStoreSelector(this, null);
    }
  }
  







  private PKIXCRLStoreSelector(Builder baseBuilder)
  {
    baseSelector = baseSelector;
    deltaCRLIndicator = deltaCRLIndicator;
    completeCRLEnabled = completeCRLEnabled;
    maxBaseCRLNumber = maxBaseCRLNumber;
    issuingDistributionPoint = issuingDistributionPoint;
    issuingDistributionPointEnabled = issuingDistributionPointEnabled;
  }
  










  public boolean isIssuingDistributionPointEnabled()
  {
    return issuingDistributionPointEnabled;
  }
  


  public boolean match(CRL obj)
  {
    if (!(obj instanceof X509CRL))
    {
      return baseSelector.match(obj);
    }
    
    X509CRL crl = (X509CRL)obj;
    ASN1Integer dci = null;
    
    try
    {
      byte[] bytes = crl.getExtensionValue(Extension.deltaCRLIndicator.getId());
      if (bytes != null)
      {
        dci = ASN1Integer.getInstance(ASN1OctetString.getInstance(bytes).getOctets());
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

      byte[] idp = crl.getExtensionValue(Extension.issuingDistributionPoint
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
    

    return baseSelector.match(obj);
  }
  







  public boolean isDeltaCRLIndicatorEnabled()
  {
    return deltaCRLIndicator;
  }
  
  public Object clone()
  {
    return this;
  }
  






  public boolean isCompleteCRLEnabled()
  {
    return completeCRLEnabled;
  }
  





  public BigInteger getMaxBaseCRLNumber()
  {
    return maxBaseCRLNumber;
  }
  











  public byte[] getIssuingDistributionPoint()
  {
    return Arrays.clone(issuingDistributionPoint);
  }
  
  public X509Certificate getCertificateChecking()
  {
    if ((baseSelector instanceof X509CRLSelector))
    {
      return ((X509CRLSelector)baseSelector).getCertificateChecking();
    }
    
    return null;
  }
  
  public static Collection<? extends CRL> getCRLs(PKIXCRLStoreSelector selector, CertStore certStore)
    throws CertStoreException
  {
    return certStore.getCRLs(new SelectorClone(selector));
  }
  
  private static class SelectorClone
    extends X509CRLSelector
  {
    private final PKIXCRLStoreSelector selector;
    
    SelectorClone(PKIXCRLStoreSelector selector)
    {
      this.selector = selector;
      
      if ((baseSelector instanceof X509CRLSelector))
      {
        X509CRLSelector baseSelector = (X509CRLSelector)baseSelector;
        
        setCertificateChecking(baseSelector.getCertificateChecking());
        setDateAndTime(baseSelector.getDateAndTime());
        setIssuers(baseSelector.getIssuers());
        setMinCRLNumber(baseSelector.getMinCRL());
        setMaxCRLNumber(baseSelector.getMaxCRL());
      }
    }
    
    public boolean match(CRL crl)
    {
      return selector == null ? false : crl != null ? true : selector.match(crl);
    }
  }
}
