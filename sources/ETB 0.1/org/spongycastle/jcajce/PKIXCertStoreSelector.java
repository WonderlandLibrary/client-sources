package org.spongycastle.jcajce;

import java.io.IOException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import org.spongycastle.util.Selector;













public class PKIXCertStoreSelector<T extends Certificate>
  implements Selector<T>
{
  private final CertSelector baseSelector;
  
  public static class Builder
  {
    private final CertSelector baseSelector;
    
    public Builder(CertSelector certSelector)
    {
      baseSelector = ((CertSelector)certSelector.clone());
    }
    





    public PKIXCertStoreSelector<? extends Certificate> build()
    {
      return new PKIXCertStoreSelector(baseSelector, null);
    }
  }
  


  private PKIXCertStoreSelector(CertSelector baseSelector)
  {
    this.baseSelector = baseSelector;
  }
  
  public boolean match(Certificate cert)
  {
    return baseSelector.match(cert);
  }
  
  public Object clone()
  {
    return new PKIXCertStoreSelector(baseSelector);
  }
  
  public static Collection<? extends Certificate> getCertificates(PKIXCertStoreSelector selector, CertStore certStore)
    throws CertStoreException
  {
    return certStore.getCertificates(new SelectorClone(selector));
  }
  
  private static class SelectorClone
    extends X509CertSelector
  {
    private final PKIXCertStoreSelector selector;
    
    SelectorClone(PKIXCertStoreSelector selector)
    {
      this.selector = selector;
      
      if ((baseSelector instanceof X509CertSelector))
      {
        X509CertSelector baseSelector = (X509CertSelector)baseSelector;
        
        setAuthorityKeyIdentifier(baseSelector.getAuthorityKeyIdentifier());
        setBasicConstraints(baseSelector.getBasicConstraints());
        setCertificate(baseSelector.getCertificate());
        setCertificateValid(baseSelector.getCertificateValid());
        setKeyUsage(baseSelector.getKeyUsage());
        setMatchAllSubjectAltNames(baseSelector.getMatchAllSubjectAltNames());
        setPrivateKeyValid(baseSelector.getPrivateKeyValid());
        setSerialNumber(baseSelector.getSerialNumber());
        setSubjectKeyIdentifier(baseSelector.getSubjectKeyIdentifier());
        setSubjectPublicKey(baseSelector.getSubjectPublicKey());
        
        try
        {
          setExtendedKeyUsage(baseSelector.getExtendedKeyUsage());
          setIssuer(baseSelector.getIssuerAsBytes());
          setNameConstraints(baseSelector.getNameConstraints());
          setPathToNames(baseSelector.getPathToNames());
          setPolicy(baseSelector.getPolicy());
          setSubject(baseSelector.getSubjectAsBytes());
          setSubjectAlternativeNames(baseSelector.getSubjectAlternativeNames());
          setSubjectPublicKeyAlgID(baseSelector.getSubjectPublicKeyAlgID());
        }
        catch (IOException e)
        {
          throw new IllegalStateException("base selector invalid: " + e.getMessage(), e);
        }
      }
    }
    
    public boolean match(Certificate certificate)
    {
      return selector == null ? false : certificate != null ? true : selector.match(certificate);
    }
  }
}
