package org.spongycastle.jcajce;

import java.security.InvalidParameterException;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;






public class PKIXExtendedBuilderParameters
  implements CertPathParameters
{
  private final PKIXExtendedParameters baseParameters;
  private final Set<X509Certificate> excludedCerts;
  private final int maxPathLength;
  
  public static class Builder
  {
    private final PKIXExtendedParameters baseParameters;
    private int maxPathLength = 5;
    private Set<X509Certificate> excludedCerts = new HashSet();
    
    public Builder(PKIXBuilderParameters baseParameters)
    {
      this.baseParameters = new PKIXExtendedParameters.Builder(baseParameters).build();
      maxPathLength = baseParameters.getMaxPathLength();
    }
    
    public Builder(PKIXExtendedParameters baseParameters)
    {
      this.baseParameters = baseParameters;
    }
    








    public Builder addExcludedCerts(Set<X509Certificate> excludedCerts)
    {
      this.excludedCerts.addAll(excludedCerts);
      
      return this;
    }
    




















    public Builder setMaxPathLength(int maxPathLength)
    {
      if (maxPathLength < -1)
      {
        throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
      }
      
      this.maxPathLength = maxPathLength;
      
      return this;
    }
    
    public PKIXExtendedBuilderParameters build()
    {
      return new PKIXExtendedBuilderParameters(this, null);
    }
  }
  




  private PKIXExtendedBuilderParameters(Builder builder)
  {
    baseParameters = baseParameters;
    excludedCerts = Collections.unmodifiableSet(excludedCerts);
    maxPathLength = maxPathLength;
  }
  
  public PKIXExtendedParameters getBaseParameters()
  {
    return baseParameters;
  }
  







  public Set getExcludedCerts()
  {
    return excludedCerts;
  }
  







  public int getMaxPathLength()
  {
    return maxPathLength;
  }
  



  public Object clone()
  {
    return this;
  }
}
