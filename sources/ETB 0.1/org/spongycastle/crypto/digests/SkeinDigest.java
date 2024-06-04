package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.util.Memoable;

































public class SkeinDigest
  implements ExtendedDigest, Memoable
{
  public static final int SKEIN_256 = 256;
  public static final int SKEIN_512 = 512;
  public static final int SKEIN_1024 = 1024;
  private SkeinEngine engine;
  
  public SkeinDigest(int stateSizeBits, int digestSizeBits)
  {
    engine = new SkeinEngine(stateSizeBits, digestSizeBits);
    init(null);
  }
  
  public SkeinDigest(SkeinDigest digest)
  {
    engine = new SkeinEngine(engine);
  }
  
  public void reset(Memoable other)
  {
    SkeinDigest d = (SkeinDigest)other;
    engine.reset(engine);
  }
  
  public Memoable copy()
  {
    return new SkeinDigest(this);
  }
  
  public String getAlgorithmName()
  {
    return "Skein-" + engine.getBlockSize() * 8 + "-" + engine.getOutputSize() * 8;
  }
  
  public int getDigestSize()
  {
    return engine.getOutputSize();
  }
  
  public int getByteLength()
  {
    return engine.getBlockSize();
  }
  






  public void init(SkeinParameters params)
  {
    engine.init(params);
  }
  
  public void reset()
  {
    engine.reset();
  }
  
  public void update(byte in)
  {
    engine.update(in);
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    engine.update(in, inOff, len);
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    return engine.doFinal(out, outOff);
  }
}
