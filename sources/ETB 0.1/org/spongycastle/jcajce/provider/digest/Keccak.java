package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.KeccakDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;




public class Keccak
{
  private Keccak() {}
  
  public static class DigestKeccak
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestKeccak(int size)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new KeccakDigest((KeccakDigest)digest);
      
      return d;
    }
  }
  
  public static class Digest224
    extends Keccak.DigestKeccak
  {
    public Digest224()
    {
      super();
    }
  }
  
  public static class Digest256
    extends Keccak.DigestKeccak
  {
    public Digest256()
    {
      super();
    }
  }
  
  public static class Digest288
    extends Keccak.DigestKeccak
  {
    public Digest288()
    {
      super();
    }
  }
  
  public static class Digest384
    extends Keccak.DigestKeccak
  {
    public Digest384()
    {
      super();
    }
  }
  
  public static class Digest512
    extends Keccak.DigestKeccak
  {
    public Digest512()
    {
      super();
    }
  }
  
  public static class HashMac224
    extends BaseMac
  {
    public HashMac224()
    {
      super();
    }
  }
  
  public static class HashMac256
    extends BaseMac
  {
    public HashMac256()
    {
      super();
    }
  }
  
  public static class HashMac288
    extends BaseMac
  {
    public HashMac288()
    {
      super();
    }
  }
  
  public static class HashMac384
    extends BaseMac
  {
    public HashMac384()
    {
      super();
    }
  }
  
  public static class HashMac512
    extends BaseMac
  {
    public HashMac512()
    {
      super();
    }
  }
  
  public static class KeyGenerator224
    extends BaseKeyGenerator
  {
    public KeyGenerator224()
    {
      super(224, new CipherKeyGenerator());
    }
  }
  
  public static class KeyGenerator256
    extends BaseKeyGenerator
  {
    public KeyGenerator256()
    {
      super(256, new CipherKeyGenerator());
    }
  }
  
  public static class KeyGenerator288
    extends BaseKeyGenerator
  {
    public KeyGenerator288()
    {
      super(288, new CipherKeyGenerator());
    }
  }
  
  public static class KeyGenerator384
    extends BaseKeyGenerator
  {
    public KeyGenerator384()
    {
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class KeyGenerator512
    extends BaseKeyGenerator
  {
    public KeyGenerator512()
    {
      super(512, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = Keccak.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.KECCAK-224", PREFIX + "$Digest224");
      provider.addAlgorithm("MessageDigest.KECCAK-288", PREFIX + "$Digest288");
      provider.addAlgorithm("MessageDigest.KECCAK-256", PREFIX + "$Digest256");
      provider.addAlgorithm("MessageDigest.KECCAK-384", PREFIX + "$Digest384");
      provider.addAlgorithm("MessageDigest.KECCAK-512", PREFIX + "$Digest512");
      
      addHMACAlgorithm(provider, "KECCAK224", PREFIX + "$HashMac224", PREFIX + "$KeyGenerator224");
      addHMACAlgorithm(provider, "KECCAK256", PREFIX + "$HashMac256", PREFIX + "$KeyGenerator256");
      addHMACAlgorithm(provider, "KECCAK288", PREFIX + "$HashMac288", PREFIX + "$KeyGenerator288");
      addHMACAlgorithm(provider, "KECCAK384", PREFIX + "$HashMac384", PREFIX + "$KeyGenerator384");
      addHMACAlgorithm(provider, "KECCAK512", PREFIX + "$HashMac512", PREFIX + "$KeyGenerator512");
    }
  }
}
