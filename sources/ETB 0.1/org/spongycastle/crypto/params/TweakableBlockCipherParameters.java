package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.util.Arrays;




public class TweakableBlockCipherParameters
  implements CipherParameters
{
  private final byte[] tweak;
  private final KeyParameter key;
  
  public TweakableBlockCipherParameters(KeyParameter key, byte[] tweak)
  {
    this.key = key;
    this.tweak = Arrays.clone(tweak);
  }
  





  public KeyParameter getKey()
  {
    return key;
  }
  





  public byte[] getTweak()
  {
    return tweak;
  }
}
