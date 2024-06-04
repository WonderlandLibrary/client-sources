package org.spongycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.spongycastle.util.Arrays;














public class TLSKeyMaterialSpec
  implements KeySpec
{
  public static final String MASTER_SECRET = "master secret";
  public static final String KEY_EXPANSION = "key expansion";
  private final byte[] secret;
  private final String label;
  private final int length;
  private final byte[] seed;
  
  public TLSKeyMaterialSpec(byte[] secret, String label, int length, byte[]... seedMaterial)
  {
    this.secret = Arrays.clone(secret);
    this.label = label;
    this.length = length;
    seed = Arrays.concatenate(seedMaterial);
  }
  





  public String getLabel()
  {
    return label;
  }
  





  public int getLength()
  {
    return length;
  }
  





  public byte[] getSecret()
  {
    return Arrays.clone(secret);
  }
  





  public byte[] getSeed()
  {
    return Arrays.clone(seed);
  }
}
