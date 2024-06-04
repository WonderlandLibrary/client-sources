package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;




public class Tiger
{
  private Tiger() {}
  
  public static class Digest
    extends BCMessageDigest
    implements Cloneable
  {
    public Digest()
    {
      super();
    }
    
    public Object clone()
      throws CloneNotSupportedException
    {
      Digest d = (Digest)super.clone();
      digest = new TigerDigest((TigerDigest)digest);
      
      return d;
    }
  }
  



  public static class HashMac
    extends BaseMac
  {
    public HashMac()
    {
      super();
    }
  }
  
  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    public KeyGenerator()
    {
      super(192, new CipherKeyGenerator());
    }
  }
  



  public static class TigerHmac
    extends BaseMac
  {
    public TigerHmac()
    {
      super();
    }
  }
  



  public static class PBEWithMacKeyFactory
    extends PBESecretKeyFactory
  {
    public PBEWithMacKeyFactory()
    {
      super(null, false, 2, 3, 192, 0);
    }
  }
  



  public static class PBEWithHashMac
    extends BaseMac
  {
    public PBEWithHashMac()
    {
      super(2, 3, 192);
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = Tiger.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.TIGER", PREFIX + "$Digest");
      provider.addAlgorithm("MessageDigest.Tiger", PREFIX + "$Digest");
      
      addHMACAlgorithm(provider, "TIGER", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
      addHMACAlias(provider, "TIGER", IANAObjectIdentifiers.hmacTIGER);
      
      provider.addAlgorithm("SecretKeyFactory.PBEWITHHMACTIGER", PREFIX + "$PBEWithMacKeyFactory");
    }
  }
}
