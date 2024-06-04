package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;




public class SHA3
{
  private SHA3() {}
  
  public static class DigestSHA3
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestSHA3(int size)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new SHA3Digest((SHA3Digest)digest);
      
      return d;
    }
  }
  
  public static class HashMacSHA3
    extends BaseMac
  {
    public HashMacSHA3(int size)
    {
      super();
    }
  }
  
  public static class KeyGeneratorSHA3
    extends BaseKeyGenerator
  {
    public KeyGeneratorSHA3(int size)
    {
      super(size, new CipherKeyGenerator());
    }
  }
  
  public static class Digest224
    extends SHA3.DigestSHA3
  {
    public Digest224()
    {
      super();
    }
  }
  
  public static class Digest256
    extends SHA3.DigestSHA3
  {
    public Digest256()
    {
      super();
    }
  }
  
  public static class Digest384
    extends SHA3.DigestSHA3
  {
    public Digest384()
    {
      super();
    }
  }
  
  public static class Digest512
    extends SHA3.DigestSHA3
  {
    public Digest512()
    {
      super();
    }
  }
  
  public static class HashMac224
    extends SHA3.HashMacSHA3
  {
    public HashMac224()
    {
      super();
    }
  }
  
  public static class HashMac256
    extends SHA3.HashMacSHA3
  {
    public HashMac256()
    {
      super();
    }
  }
  
  public static class HashMac384
    extends SHA3.HashMacSHA3
  {
    public HashMac384()
    {
      super();
    }
  }
  
  public static class HashMac512
    extends SHA3.HashMacSHA3
  {
    public HashMac512()
    {
      super();
    }
  }
  
  public static class KeyGenerator224
    extends SHA3.KeyGeneratorSHA3
  {
    public KeyGenerator224()
    {
      super();
    }
  }
  
  public static class KeyGenerator256
    extends SHA3.KeyGeneratorSHA3
  {
    public KeyGenerator256()
    {
      super();
    }
  }
  
  public static class KeyGenerator384
    extends SHA3.KeyGeneratorSHA3
  {
    public KeyGenerator384()
    {
      super();
    }
  }
  
  public static class KeyGenerator512
    extends SHA3.KeyGeneratorSHA3
  {
    public KeyGenerator512()
    {
      super();
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = SHA3.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.SHA3-224", PREFIX + "$Digest224");
      provider.addAlgorithm("MessageDigest.SHA3-256", PREFIX + "$Digest256");
      provider.addAlgorithm("MessageDigest.SHA3-384", PREFIX + "$Digest384");
      provider.addAlgorithm("MessageDigest.SHA3-512", PREFIX + "$Digest512");
      provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.id_sha3_224, PREFIX + "$Digest224");
      provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.id_sha3_256, PREFIX + "$Digest256");
      provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.id_sha3_384, PREFIX + "$Digest384");
      provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.id_sha3_512, PREFIX + "$Digest512");
      
      addHMACAlgorithm(provider, "SHA3-224", PREFIX + "$HashMac224", PREFIX + "$KeyGenerator224");
      addHMACAlias(provider, "SHA3-224", NISTObjectIdentifiers.id_hmacWithSHA3_224);
      
      addHMACAlgorithm(provider, "SHA3-256", PREFIX + "$HashMac256", PREFIX + "$KeyGenerator256");
      addHMACAlias(provider, "SHA3-256", NISTObjectIdentifiers.id_hmacWithSHA3_256);
      
      addHMACAlgorithm(provider, "SHA3-384", PREFIX + "$HashMac384", PREFIX + "$KeyGenerator384");
      addHMACAlias(provider, "SHA3-384", NISTObjectIdentifiers.id_hmacWithSHA3_384);
      
      addHMACAlgorithm(provider, "SHA3-512", PREFIX + "$HashMac512", PREFIX + "$KeyGenerator512");
      addHMACAlias(provider, "SHA3-512", NISTObjectIdentifiers.id_hmacWithSHA3_512);
    }
  }
}
