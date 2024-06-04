package org.spongycastle.x509;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.util.Selector;
import org.spongycastle.util.Store;



























/**
 * @deprecated
 */
public class ExtendedPKIXParameters
  extends PKIXParameters
{
  private List stores;
  private Selector selector;
  private boolean additionalLocationsEnabled;
  private List additionalStores;
  private Set trustedACIssuers;
  private Set necessaryACAttributes;
  private Set prohibitedACAttributes;
  private Set attrCertCheckers;
  public static final int PKIX_VALIDITY_MODEL = 0;
  public static final int CHAIN_VALIDITY_MODEL = 1;
  
  public ExtendedPKIXParameters(Set trustAnchors)
    throws InvalidAlgorithmParameterException
  {
    super(trustAnchors);
    stores = new ArrayList();
    additionalStores = new ArrayList();
    trustedACIssuers = new HashSet();
    necessaryACAttributes = new HashSet();
    prohibitedACAttributes = new HashSet();
    attrCertCheckers = new HashSet();
  }
  








  public static ExtendedPKIXParameters getInstance(PKIXParameters pkixParams)
  {
    try
    {
      params = new ExtendedPKIXParameters(pkixParams.getTrustAnchors());
    }
    catch (Exception e)
    {
      ExtendedPKIXParameters params;
      throw new RuntimeException(e.getMessage()); }
    ExtendedPKIXParameters params;
    params.setParams(pkixParams);
    return params;
  }
  







  protected void setParams(PKIXParameters params)
  {
    setDate(params.getDate());
    setCertPathCheckers(params.getCertPathCheckers());
    setCertStores(params.getCertStores());
    setAnyPolicyInhibited(params.isAnyPolicyInhibited());
    setExplicitPolicyRequired(params.isExplicitPolicyRequired());
    setPolicyMappingInhibited(params.isPolicyMappingInhibited());
    setRevocationEnabled(params.isRevocationEnabled());
    setInitialPolicies(params.getInitialPolicies());
    setPolicyQualifiersRejected(params.getPolicyQualifiersRejected());
    setSigProvider(params.getSigProvider());
    setTargetCertConstraints(params.getTargetCertConstraints());
    try
    {
      setTrustAnchors(params.getTrustAnchors());

    }
    catch (Exception e)
    {
      throw new RuntimeException(e.getMessage());
    }
    if ((params instanceof ExtendedPKIXParameters))
    {
      ExtendedPKIXParameters _params = (ExtendedPKIXParameters)params;
      validityModel = validityModel;
      useDeltas = useDeltas;
      additionalLocationsEnabled = additionalLocationsEnabled;
      
      selector = (selector == null ? null : (Selector)selector.clone());
      stores = new ArrayList(stores);
      additionalStores = new ArrayList(additionalStores);
      trustedACIssuers = new HashSet(trustedACIssuers);
      prohibitedACAttributes = new HashSet(prohibitedACAttributes);
      necessaryACAttributes = new HashSet(necessaryACAttributes);
      attrCertCheckers = new HashSet(attrCertCheckers);
    }
  }
  




























  private int validityModel = 0;
  
  private boolean useDeltas = false;
  





  public boolean isUseDeltasEnabled()
  {
    return useDeltas;
  }
  





  public void setUseDeltasEnabled(boolean useDeltas)
  {
    this.useDeltas = useDeltas;
  }
  





  public int getValidityModel()
  {
    return validityModel;
  }
  






  public void setCertStores(List stores)
  {
    if (stores != null)
    {
      Iterator it = stores.iterator();
      while (it.hasNext())
      {
        addCertStore((CertStore)it.next());
      }
    }
  }
  











  public void setStores(List stores)
  {
    if (stores == null)
    {
      this.stores = new ArrayList();
    }
    else
    {
      for (Iterator i = stores.iterator(); i.hasNext();)
      {
        if (!(i.next() instanceof Store))
        {
          throw new ClassCastException("All elements of list must be of type org.spongycastle.util.Store.");
        }
      }
      

      this.stores = new ArrayList(stores);
    }
  }
  














  public void addStore(Store store)
  {
    if (store != null)
    {
      stores.add(store);
    }
  }
  















  public void addAdditionalStore(Store store)
  {
    if (store != null)
    {
      additionalStores.add(store);
    }
  }
  
  /**
   * @deprecated
   */
  public void addAddionalStore(Store store)
  {
    addAdditionalStore(store);
  }
  










  public List getAdditionalStores()
  {
    return Collections.unmodifiableList(additionalStores);
  }
  










  public List getStores()
  {
    return Collections.unmodifiableList(new ArrayList(stores));
  }
  





  public void setValidityModel(int validityModel)
  {
    this.validityModel = validityModel;
  }
  

  public Object clone()
  {
    try
    {
      params = new ExtendedPKIXParameters(getTrustAnchors());
    }
    catch (Exception e)
    {
      ExtendedPKIXParameters params;
      throw new RuntimeException(e.getMessage()); }
    ExtendedPKIXParameters params;
    params.setParams(this);
    return params;
  }
  






  public boolean isAdditionalLocationsEnabled()
  {
    return additionalLocationsEnabled;
  }
  






  public void setAdditionalLocationsEnabled(boolean enabled)
  {
    additionalLocationsEnabled = enabled;
  }
  



















  public Selector getTargetConstraints()
  {
    if (selector != null)
    {
      return (Selector)selector.clone();
    }
    

    return null;
  }
  




















  public void setTargetConstraints(Selector selector)
  {
    if (selector != null)
    {
      this.selector = ((Selector)selector.clone());
    }
    else
    {
      this.selector = null;
    }
  }
  

















  public void setTargetCertConstraints(CertSelector selector)
  {
    super.setTargetCertConstraints(selector);
    if (selector != null)
    {

      this.selector = X509CertStoreSelector.getInstance((X509CertSelector)selector);
    }
    else
    {
      this.selector = null;
    }
  }
  










  public Set getTrustedACIssuers()
  {
    return Collections.unmodifiableSet(trustedACIssuers);
  }
  














  public void setTrustedACIssuers(Set trustedACIssuers)
  {
    if (trustedACIssuers == null)
    {
      this.trustedACIssuers.clear();
      return;
    }
    for (Iterator it = trustedACIssuers.iterator(); it.hasNext();)
    {
      if (!(it.next() instanceof TrustAnchor))
      {

        throw new ClassCastException("All elements of set must be of type " + TrustAnchor.class.getName() + ".");
      }
    }
    this.trustedACIssuers.clear();
    this.trustedACIssuers.addAll(trustedACIssuers);
  }
  









  public Set getNecessaryACAttributes()
  {
    return Collections.unmodifiableSet(necessaryACAttributes);
  }
  













  public void setNecessaryACAttributes(Set necessaryACAttributes)
  {
    if (necessaryACAttributes == null)
    {
      this.necessaryACAttributes.clear();
      return;
    }
    for (Iterator it = necessaryACAttributes.iterator(); it.hasNext();)
    {
      if (!(it.next() instanceof String))
      {
        throw new ClassCastException("All elements of set must be of type String.");
      }
    }
    
    this.necessaryACAttributes.clear();
    this.necessaryACAttributes.addAll(necessaryACAttributes);
  }
  








  public Set getProhibitedACAttributes()
  {
    return Collections.unmodifiableSet(prohibitedACAttributes);
  }
  













  public void setProhibitedACAttributes(Set prohibitedACAttributes)
  {
    if (prohibitedACAttributes == null)
    {
      this.prohibitedACAttributes.clear();
      return;
    }
    for (Iterator it = prohibitedACAttributes.iterator(); it.hasNext();)
    {
      if (!(it.next() instanceof String))
      {
        throw new ClassCastException("All elements of set must be of type String.");
      }
    }
    
    this.prohibitedACAttributes.clear();
    this.prohibitedACAttributes.addAll(prohibitedACAttributes);
  }
  







  public Set getAttrCertCheckers()
  {
    return Collections.unmodifiableSet(attrCertCheckers);
  }
  












  public void setAttrCertCheckers(Set attrCertCheckers)
  {
    if (attrCertCheckers == null)
    {
      this.attrCertCheckers.clear();
      return;
    }
    for (Iterator it = attrCertCheckers.iterator(); it.hasNext();)
    {
      if (!(it.next() instanceof PKIXAttrCertChecker))
      {

        throw new ClassCastException("All elements of set must be of type " + PKIXAttrCertChecker.class.getName() + ".");
      }
    }
    this.attrCertCheckers.clear();
    this.attrCertCheckers.addAll(attrCertCheckers);
  }
}
