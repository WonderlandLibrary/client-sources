package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.generators.Poly1305KeyGenerator;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;



public class Poly1305
{
  private Poly1305() {}
  
  public static class Mac
    extends BaseMac
  {
    public Mac()
    {
      super();
    }
  }
  
  public static class KeyGen
    extends BaseKeyGenerator
  {
    public KeyGen()
    {
      super(256, new Poly1305KeyGenerator());
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = Poly1305.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("Mac.POLY1305", PREFIX + "$Mac");
      
      provider.addAlgorithm("KeyGenerator.POLY1305", PREFIX + "$KeyGen");
    }
  }
}
