package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.DSTU7564Digest;
import org.spongycastle.crypto.macs.DSTU7564Mac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;




public class DSTU7564
{
  private DSTU7564() {}
  
  public static class DigestDSTU7564
    extends BCMessageDigest
    implements Cloneable
  {
    public DigestDSTU7564(int size)
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      BCMessageDigest d = (BCMessageDigest)super.clone();
      digest = new DSTU7564Digest((DSTU7564Digest)digest);
      
      return d;
    }
  }
  
  public static class Digest256
    extends DSTU7564.DigestDSTU7564
  {
    public Digest256()
    {
      super();
    }
  }
  
  public static class Digest384
    extends DSTU7564.DigestDSTU7564
  {
    public Digest384()
    {
      super();
    }
  }
  
  public static class Digest512
    extends DSTU7564.DigestDSTU7564
  {
    public Digest512()
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
  
  public static class KeyGenerator256
    extends BaseKeyGenerator
  {
    public KeyGenerator256()
    {
      super(256, new CipherKeyGenerator());
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
    private static final String PREFIX = DSTU7564.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.DSTU7564-256", PREFIX + "$Digest256");
      provider.addAlgorithm("MessageDigest.DSTU7564-384", PREFIX + "$Digest384");
      provider.addAlgorithm("MessageDigest.DSTU7564-512", PREFIX + "$Digest512");
      
      provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.dstu7564digest_256, PREFIX + "$Digest256");
      provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.dstu7564digest_384, PREFIX + "$Digest384");
      provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.dstu7564digest_512, PREFIX + "$Digest512");
      
      addHMACAlgorithm(provider, "DSTU7564-256", PREFIX + "$HashMac256", PREFIX + "$KeyGenerator256");
      addHMACAlgorithm(provider, "DSTU7564-384", PREFIX + "$HashMac384", PREFIX + "$KeyGenerator384");
      addHMACAlgorithm(provider, "DSTU7564-512", PREFIX + "$HashMac512", PREFIX + "$KeyGenerator512");
      
      addHMACAlias(provider, "DSTU7564-256", UAObjectIdentifiers.dstu7564mac_256);
      addHMACAlias(provider, "DSTU7564-384", UAObjectIdentifiers.dstu7564mac_384);
      addHMACAlias(provider, "DSTU7564-512", UAObjectIdentifiers.dstu7564mac_512);
    }
  }
}
