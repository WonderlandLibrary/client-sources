package org.spongycastle.jcajce.provider.keystore;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class BC
{
  private static final String PREFIX = "org.spongycastle.jcajce.provider.keystore.bc.";
  
  public BC() {}
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public Mappings() {}
    
    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("KeyStore.BKS", "org.spongycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Std");
      provider.addAlgorithm("KeyStore.BKS-V1", "org.spongycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Version1");
      provider.addAlgorithm("KeyStore.BouncyCastle", "org.spongycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$BouncyCastleStore");
      provider.addAlgorithm("Alg.Alias.KeyStore.UBER", "BouncyCastle");
      provider.addAlgorithm("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
      provider.addAlgorithm("Alg.Alias.KeyStore.spongycastle", "BouncyCastle");
    }
  }
}
