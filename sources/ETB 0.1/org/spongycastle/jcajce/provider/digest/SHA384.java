package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.OldHMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;




public class SHA384
{
  private SHA384() {}
  
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
      digest = new SHA384Digest((SHA384Digest)digest);
      
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
      super(384, new CipherKeyGenerator());
    }
  }
  
  public static class OldSHA384
    extends BaseMac
  {
    public OldSHA384()
    {
      super();
    }
  }
  
  public static class Mappings
    extends DigestAlgorithmProvider
  {
    private static final String PREFIX = SHA384.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("MessageDigest.SHA-384", PREFIX + "$Digest");
      provider.addAlgorithm("Alg.Alias.MessageDigest.SHA384", "SHA-384");
      provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha384, "SHA-384");
      provider.addAlgorithm("Mac.OLDHMACSHA384", PREFIX + "$OldSHA384");
      
      provider.addAlgorithm("Mac.PBEWITHHMACSHA384", PREFIX + "$HashMac");
      
      addHMACAlgorithm(provider, "SHA384", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
      addHMACAlias(provider, "SHA384", PKCSObjectIdentifiers.id_hmacWithSHA384);
    }
  }
}
