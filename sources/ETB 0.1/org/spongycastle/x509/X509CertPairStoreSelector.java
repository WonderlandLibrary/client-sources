package org.spongycastle.x509;

import org.spongycastle.util.Selector;




















public class X509CertPairStoreSelector
  implements Selector
{
  private X509CertStoreSelector forwardSelector;
  private X509CertStoreSelector reverseSelector;
  private X509CertificatePair certPair;
  
  public X509CertPairStoreSelector() {}
  
  public X509CertificatePair getCertPair()
  {
    return certPair;
  }
  





  public void setCertPair(X509CertificatePair certPair)
  {
    this.certPair = certPair;
  }
  




  public void setForwardSelector(X509CertStoreSelector forwardSelector)
  {
    this.forwardSelector = forwardSelector;
  }
  




  public void setReverseSelector(X509CertStoreSelector reverseSelector)
  {
    this.reverseSelector = reverseSelector;
  }
  






  public Object clone()
  {
    X509CertPairStoreSelector cln = new X509CertPairStoreSelector();
    
    certPair = certPair;
    
    if (forwardSelector != null)
    {
      cln.setForwardSelector(
        (X509CertStoreSelector)forwardSelector.clone());
    }
    
    if (reverseSelector != null)
    {
      cln.setReverseSelector(
        (X509CertStoreSelector)reverseSelector.clone());
    }
    
    return cln;
  }
  








  public boolean match(Object obj)
  {
    try
    {
      if (!(obj instanceof X509CertificatePair))
      {
        return false;
      }
      X509CertificatePair pair = (X509CertificatePair)obj;
      
      if ((forwardSelector != null) && 
        (!forwardSelector.match(pair.getForward())))
      {
        return false;
      }
      
      if ((reverseSelector != null) && 
        (!reverseSelector.match(pair.getReverse())))
      {
        return false;
      }
      
      if (certPair != null)
      {
        return certPair.equals(obj);
      }
      
      return true;
    }
    catch (Exception e) {}
    
    return false;
  }
  






  public X509CertStoreSelector getForwardSelector()
  {
    return forwardSelector;
  }
  





  public X509CertStoreSelector getReverseSelector()
  {
    return reverseSelector;
  }
}
