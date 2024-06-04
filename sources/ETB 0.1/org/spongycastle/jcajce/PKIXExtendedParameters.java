package org.spongycastle.jcajce;

import java.security.cert.CertPathParameters;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.x509.GeneralName;




















public class PKIXExtendedParameters
  implements CertPathParameters
{
  public static final int PKIX_VALIDITY_MODEL = 0;
  public static final int CHAIN_VALIDITY_MODEL = 1;
  private final PKIXParameters baseParameters;
  private final PKIXCertStoreSelector targetConstraints;
  private final Date date;
  private final List<PKIXCertStore> extraCertStores;
  private final Map<GeneralName, PKIXCertStore> namedCertificateStoreMap;
  private final List<PKIXCRLStore> extraCRLStores;
  private final Map<GeneralName, PKIXCRLStore> namedCRLStoreMap;
  private final boolean revocationEnabled;
  private final boolean useDeltas;
  private final int validityModel;
  private final Set<TrustAnchor> trustAnchors;
  
  public static class Builder
  {
    private final PKIXParameters baseParameters;
    private final Date date;
    private PKIXCertStoreSelector targetConstraints;
    private List<PKIXCertStore> extraCertStores = new ArrayList();
    private Map<GeneralName, PKIXCertStore> namedCertificateStoreMap = new HashMap();
    private List<PKIXCRLStore> extraCRLStores = new ArrayList();
    private Map<GeneralName, PKIXCRLStore> namedCRLStoreMap = new HashMap();
    private boolean revocationEnabled;
    private int validityModel = 0;
    private boolean useDeltas = false;
    private Set<TrustAnchor> trustAnchors;
    
    public Builder(PKIXParameters baseParameters)
    {
      this.baseParameters = ((PKIXParameters)baseParameters.clone());
      CertSelector constraints = baseParameters.getTargetCertConstraints();
      if (constraints != null)
      {
        targetConstraints = new PKIXCertStoreSelector.Builder(constraints).build();
      }
      Date checkDate = baseParameters.getDate();
      date = (checkDate == null ? new Date() : checkDate);
      revocationEnabled = baseParameters.isRevocationEnabled();
      trustAnchors = baseParameters.getTrustAnchors();
    }
    
    public Builder(PKIXExtendedParameters baseParameters)
    {
      this.baseParameters = baseParameters;
      date = date;
      targetConstraints = targetConstraints;
      extraCertStores = new ArrayList(extraCertStores);
      namedCertificateStoreMap = new HashMap(namedCertificateStoreMap);
      extraCRLStores = new ArrayList(extraCRLStores);
      namedCRLStoreMap = new HashMap(namedCRLStoreMap);
      useDeltas = useDeltas;
      validityModel = validityModel;
      revocationEnabled = baseParameters.isRevocationEnabled();
      trustAnchors = baseParameters.getTrustAnchors();
    }
    
    public Builder addCertificateStore(PKIXCertStore store)
    {
      extraCertStores.add(store);
      
      return this;
    }
    
    public Builder addNamedCertificateStore(GeneralName issuerAltName, PKIXCertStore store)
    {
      namedCertificateStoreMap.put(issuerAltName, store);
      
      return this;
    }
    
    public Builder addCRLStore(PKIXCRLStore store)
    {
      extraCRLStores.add(store);
      
      return this;
    }
    
    public Builder addNamedCRLStore(GeneralName issuerAltName, PKIXCRLStore store)
    {
      namedCRLStoreMap.put(issuerAltName, store);
      
      return this;
    }
    
    public Builder setTargetConstraints(PKIXCertStoreSelector selector)
    {
      targetConstraints = selector;
      
      return this;
    }
    





    public Builder setUseDeltasEnabled(boolean useDeltas)
    {
      this.useDeltas = useDeltas;
      
      return this;
    }
    





    public Builder setValidityModel(int validityModel)
    {
      this.validityModel = validityModel;
      
      return this;
    }
    






    public Builder setTrustAnchor(TrustAnchor trustAnchor)
    {
      trustAnchors = Collections.singleton(trustAnchor);
      
      return this;
    }
    






    public Builder setTrustAnchors(Set<TrustAnchor> trustAnchors)
    {
      this.trustAnchors = trustAnchors;
      
      return this;
    }
    





    public void setRevocationEnabled(boolean revocationEnabled)
    {
      this.revocationEnabled = revocationEnabled;
    }
    
    public PKIXExtendedParameters build()
    {
      return new PKIXExtendedParameters(this, null);
    }
  }
  












  private PKIXExtendedParameters(Builder builder)
  {
    baseParameters = baseParameters;
    date = date;
    extraCertStores = Collections.unmodifiableList(extraCertStores);
    namedCertificateStoreMap = Collections.unmodifiableMap(new HashMap(namedCertificateStoreMap));
    extraCRLStores = Collections.unmodifiableList(extraCRLStores);
    namedCRLStoreMap = Collections.unmodifiableMap(new HashMap(namedCRLStoreMap));
    targetConstraints = targetConstraints;
    revocationEnabled = revocationEnabled;
    useDeltas = useDeltas;
    validityModel = validityModel;
    trustAnchors = Collections.unmodifiableSet(trustAnchors);
  }
  
  public List<PKIXCertStore> getCertificateStores()
  {
    return extraCertStores;
  }
  

  public Map<GeneralName, PKIXCertStore> getNamedCertificateStoreMap()
  {
    return namedCertificateStoreMap;
  }
  
  public List<PKIXCRLStore> getCRLStores()
  {
    return extraCRLStores;
  }
  
  public Map<GeneralName, PKIXCRLStore> getNamedCRLStoreMap()
  {
    return namedCRLStoreMap;
  }
  
  public Date getDate()
  {
    return new Date(date.getTime());
  }
  








  public boolean isUseDeltasEnabled()
  {
    return useDeltas;
  }
  







  public int getValidityModel()
  {
    return validityModel;
  }
  
  public Object clone()
  {
    return this;
  }
  










  public PKIXCertStoreSelector getTargetConstraints()
  {
    return targetConstraints;
  }
  
  public Set getTrustAnchors()
  {
    return trustAnchors;
  }
  
  public Set getInitialPolicies()
  {
    return baseParameters.getInitialPolicies();
  }
  
  public String getSigProvider()
  {
    return baseParameters.getSigProvider();
  }
  
  public boolean isExplicitPolicyRequired()
  {
    return baseParameters.isExplicitPolicyRequired();
  }
  
  public boolean isAnyPolicyInhibited()
  {
    return baseParameters.isAnyPolicyInhibited();
  }
  
  public boolean isPolicyMappingInhibited()
  {
    return baseParameters.isPolicyMappingInhibited();
  }
  
  public List getCertPathCheckers()
  {
    return baseParameters.getCertPathCheckers();
  }
  
  public List<CertStore> getCertStores()
  {
    return baseParameters.getCertStores();
  }
  
  public boolean isRevocationEnabled()
  {
    return revocationEnabled;
  }
}
