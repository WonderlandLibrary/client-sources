package org.spongycastle.x509;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CertSelector;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.util.Selector;







/**
 * @deprecated
 */
public class ExtendedPKIXBuilderParameters
  extends ExtendedPKIXParameters
{
  private int maxPathLength = 5;
  
  private Set excludedCerts = Collections.EMPTY_SET;
  







  public Set getExcludedCerts()
  {
    return Collections.unmodifiableSet(excludedCerts);
  }
  









  public void setExcludedCerts(Set excludedCerts)
  {
    if (excludedCerts == null)
    {
      excludedCerts = Collections.EMPTY_SET;
    }
    else
    {
      this.excludedCerts = new HashSet(excludedCerts);
    }
  }
  






















  public ExtendedPKIXBuilderParameters(Set trustAnchors, Selector targetConstraints)
    throws InvalidAlgorithmParameterException
  {
    super(trustAnchors);
    setTargetConstraints(targetConstraints);
  }
  





















  public void setMaxPathLength(int maxPathLength)
  {
    if (maxPathLength < -1)
    {
      throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
    }
    
    this.maxPathLength = maxPathLength;
  }
  









  public int getMaxPathLength()
  {
    return maxPathLength;
  }
  







  protected void setParams(PKIXParameters params)
  {
    super.setParams(params);
    if ((params instanceof ExtendedPKIXBuilderParameters))
    {
      ExtendedPKIXBuilderParameters _params = (ExtendedPKIXBuilderParameters)params;
      maxPathLength = maxPathLength;
      excludedCerts = new HashSet(excludedCerts);
    }
    if ((params instanceof PKIXBuilderParameters))
    {
      PKIXBuilderParameters _params = (PKIXBuilderParameters)params;
      maxPathLength = _params.getMaxPathLength();
    }
  }
  






  public Object clone()
  {
    ExtendedPKIXBuilderParameters params = null;
    
    try
    {
      params = new ExtendedPKIXBuilderParameters(getTrustAnchors(), getTargetConstraints());

    }
    catch (Exception e)
    {
      throw new RuntimeException(e.getMessage());
    }
    params.setParams(this);
    return params;
  }
  














  public static ExtendedPKIXParameters getInstance(PKIXParameters pkixParams)
  {
    try
    {
      params = new ExtendedPKIXBuilderParameters(pkixParams.getTrustAnchors(), X509CertStoreSelector.getInstance(
        (X509CertSelector)pkixParams.getTargetCertConstraints()));
    }
    catch (Exception e)
    {
      ExtendedPKIXBuilderParameters params;
      throw new RuntimeException(e.getMessage()); }
    ExtendedPKIXBuilderParameters params;
    params.setParams(pkixParams);
    return params;
  }
}
