package org.spongycastle.jcajce.provider.config;

import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;

public abstract interface ProviderConfiguration
{
  public abstract ECParameterSpec getEcImplicitlyCa();
  
  public abstract DHParameterSpec getDHDefaultParameters(int paramInt);
  
  public abstract Set getAcceptableNamedCurves();
  
  public abstract Map getAdditionalECParameters();
}
