package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.ChaCha7539Engine;
import org.spongycastle.crypto.engines.ChaChaEngine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;



public final class ChaCha
{
  private ChaCha() {}
  
  public static class Base
    extends BaseStreamCipher
  {
    public Base()
    {
      super(8);
    }
  }
  
  public static class KeyGen
    extends BaseKeyGenerator
  {
    public KeyGen()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class Base7539
    extends BaseStreamCipher
  {
    public Base7539()
    {
      super(12);
    }
  }
  
  public static class KeyGen7539
    extends BaseKeyGenerator
  {
    public KeyGen7539()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = ChaCha.class.getName();
    


    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("Cipher.CHACHA", PREFIX + "$Base");
      provider.addAlgorithm("KeyGenerator.CHACHA", PREFIX + "$KeyGen");
      
      provider.addAlgorithm("Cipher.CHACHA7539", PREFIX + "$Base7539");
      provider.addAlgorithm("KeyGenerator.CHACHA7539", PREFIX + "$KeyGen7539");
    }
  }
}
