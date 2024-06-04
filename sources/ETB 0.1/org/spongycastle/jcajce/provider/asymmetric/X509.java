package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;





public class X509
{
  public X509() {}
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public Mappings() {}
    
    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("KeyFactory.X.509", "org.spongycastle.jcajce.provider.asymmetric.x509.KeyFactory");
      provider.addAlgorithm("Alg.Alias.KeyFactory.X509", "X.509");
      



      provider.addAlgorithm("CertificateFactory.X.509", "org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory");
      provider.addAlgorithm("Alg.Alias.CertificateFactory.X509", "X.509");
    }
  }
}
