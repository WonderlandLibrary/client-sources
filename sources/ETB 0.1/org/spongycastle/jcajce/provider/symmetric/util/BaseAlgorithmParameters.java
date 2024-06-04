package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public abstract class BaseAlgorithmParameters extends java.security.AlgorithmParametersSpi
{
  public BaseAlgorithmParameters() {}
  
  protected boolean isASN1FormatString(String format)
  {
    return (format == null) || (format.equals("ASN.1"));
  }
  

  protected AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec)
    throws InvalidParameterSpecException
  {
    if (paramSpec == null)
    {
      throw new NullPointerException("argument to getParameterSpec must not be null");
    }
    
    return localEngineGetParameterSpec(paramSpec);
  }
  
  protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException;
}
