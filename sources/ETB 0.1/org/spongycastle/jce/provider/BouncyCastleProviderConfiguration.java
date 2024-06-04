package org.spongycastle.jce.provider;

import java.math.BigInteger;
import java.security.Permission;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.config.ProviderConfigurationPermission;




class BouncyCastleProviderConfiguration
  implements ProviderConfiguration
{
  private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission("SC", "threadLocalEcImplicitlyCa");
  
  private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission("SC", "ecImplicitlyCa");
  
  private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission("SC", "threadLocalDhDefaultParams");
  
  private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission("SC", "DhDefaultParams");
  
  private static Permission BC_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("SC", "acceptableEcCurves");
  
  private static Permission BC_ADDITIONAL_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("SC", "additionalEcParameters");
  

  private ThreadLocal ecThreadSpec = new ThreadLocal();
  private ThreadLocal dhThreadSpec = new ThreadLocal();
  
  private volatile org.spongycastle.jce.spec.ECParameterSpec ecImplicitCaParams;
  private volatile Object dhDefaultParams;
  private volatile Set acceptableNamedCurves = new HashSet();
  private volatile Map additionalECParameters = new HashMap();
  
  BouncyCastleProviderConfiguration() {}
  
  void setParameter(String parameterName, Object parameter) { SecurityManager securityManager = System.getSecurityManager();
    
    if (parameterName.equals("threadLocalEcImplicitlyCa"))
    {


      if (securityManager != null)
      {
        securityManager.checkPermission(BC_EC_LOCAL_PERMISSION); }
      org.spongycastle.jce.spec.ECParameterSpec curveSpec;
      org.spongycastle.jce.spec.ECParameterSpec curveSpec;
      if (((parameter instanceof org.spongycastle.jce.spec.ECParameterSpec)) || (parameter == null))
      {
        curveSpec = (org.spongycastle.jce.spec.ECParameterSpec)parameter;
      }
      else
      {
        curveSpec = EC5Util.convertSpec((java.security.spec.ECParameterSpec)parameter, false);
      }
      
      if (curveSpec == null)
      {
        ecThreadSpec.remove();
      }
      else
      {
        ecThreadSpec.set(curveSpec);
      }
    }
    else if (parameterName.equals("ecImplicitlyCa"))
    {
      if (securityManager != null)
      {
        securityManager.checkPermission(BC_EC_PERMISSION);
      }
      
      if (((parameter instanceof org.spongycastle.jce.spec.ECParameterSpec)) || (parameter == null))
      {
        ecImplicitCaParams = ((org.spongycastle.jce.spec.ECParameterSpec)parameter);
      }
      else
      {
        ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec)parameter, false);
      }
    }
    else if (parameterName.equals("threadLocalDhDefaultParams"))
    {


      if (securityManager != null)
      {
        securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
      }
      Object dhSpec;
      if (((parameter instanceof DHParameterSpec)) || ((parameter instanceof DHParameterSpec[])) || (parameter == null))
      {
        dhSpec = parameter;
      }
      else
      {
        throw new IllegalArgumentException("not a valid DHParameterSpec");
      }
      Object dhSpec;
      if (dhSpec == null)
      {
        dhThreadSpec.remove();
      }
      else
      {
        dhThreadSpec.set(dhSpec);
      }
    }
    else if (parameterName.equals("DhDefaultParams"))
    {
      if (securityManager != null)
      {
        securityManager.checkPermission(BC_DH_PERMISSION);
      }
      
      if (((parameter instanceof DHParameterSpec)) || ((parameter instanceof DHParameterSpec[])) || (parameter == null))
      {
        dhDefaultParams = parameter;
      }
      else
      {
        throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
      }
    }
    else if (parameterName.equals("acceptableEcCurves"))
    {
      if (securityManager != null)
      {
        securityManager.checkPermission(BC_EC_CURVE_PERMISSION);
      }
      
      acceptableNamedCurves = ((Set)parameter);
    }
    else if (parameterName.equals("additionalEcParameters"))
    {
      if (securityManager != null)
      {
        securityManager.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
      }
      
      additionalECParameters = ((Map)parameter);
    }
  }
  
  public org.spongycastle.jce.spec.ECParameterSpec getEcImplicitlyCa()
  {
    org.spongycastle.jce.spec.ECParameterSpec spec = (org.spongycastle.jce.spec.ECParameterSpec)ecThreadSpec.get();
    
    if (spec != null)
    {
      return spec;
    }
    
    return ecImplicitCaParams;
  }
  
  public DHParameterSpec getDHDefaultParameters(int keySize)
  {
    Object params = dhThreadSpec.get();
    if (params == null)
    {
      params = dhDefaultParams;
    }
    
    if ((params instanceof DHParameterSpec))
    {
      DHParameterSpec spec = (DHParameterSpec)params;
      
      if (spec.getP().bitLength() == keySize)
      {
        return spec;
      }
    }
    else if ((params instanceof DHParameterSpec[]))
    {
      DHParameterSpec[] specs = (DHParameterSpec[])params;
      
      for (int i = 0; i != specs.length; i++)
      {
        if (specs[i].getP().bitLength() == keySize)
        {
          return specs[i];
        }
      }
    }
    
    return null;
  }
  
  public Set getAcceptableNamedCurves()
  {
    return Collections.unmodifiableSet(acceptableNamedCurves);
  }
  
  public Map getAdditionalECParameters()
  {
    return Collections.unmodifiableMap(additionalECParameters);
  }
}
