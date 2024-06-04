package org.spongycastle.pqc.jcajce.provider;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi;

public class Rainbow
{
  private static final String PREFIX = "org.spongycastle.pqc.jcajce.provider.rainbow.";
  
  public Rainbow() {}
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public Mappings() {}
    
    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("KeyFactory.Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi");
      provider.addAlgorithm("KeyPairGenerator.Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyPairGeneratorSpi");
      
      addSignatureAlgorithm(provider, "SHA224", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha224", PQCObjectIdentifiers.rainbowWithSha224);
      addSignatureAlgorithm(provider, "SHA256", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha256", PQCObjectIdentifiers.rainbowWithSha256);
      addSignatureAlgorithm(provider, "SHA384", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha384", PQCObjectIdentifiers.rainbowWithSha384);
      addSignatureAlgorithm(provider, "SHA512", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha512", PQCObjectIdentifiers.rainbowWithSha512);
      
      AsymmetricKeyInfoConverter keyFact = new RainbowKeyFactorySpi();
      
      registerOid(provider, PQCObjectIdentifiers.rainbow, "Rainbow", keyFact);
    }
  }
}
