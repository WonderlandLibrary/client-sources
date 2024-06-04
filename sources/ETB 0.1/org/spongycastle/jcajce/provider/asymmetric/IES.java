package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class IES
{
  private static final String PREFIX = "org.spongycastle.jcajce.provider.asymmetric.ies.";
  
  public IES() {}
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public Mappings() {}
    
    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("AlgorithmParameters.IES", "org.spongycastle.jcajce.provider.asymmetric.ies.AlgorithmParametersSpi");
      provider.addAlgorithm("AlgorithmParameters.ECIES", "org.spongycastle.jcajce.provider.asymmetric.ies.AlgorithmParametersSpi");
    }
  }
}
