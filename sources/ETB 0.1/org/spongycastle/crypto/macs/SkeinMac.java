package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.SkeinEngine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.crypto.params.SkeinParameters.Builder;































public class SkeinMac
  implements Mac
{
  public static final int SKEIN_256 = 256;
  public static final int SKEIN_512 = 512;
  public static final int SKEIN_1024 = 1024;
  private SkeinEngine engine;
  
  public SkeinMac(int stateSizeBits, int digestSizeBits)
  {
    engine = new SkeinEngine(stateSizeBits, digestSizeBits);
  }
  
  public SkeinMac(SkeinMac mac)
  {
    engine = new SkeinEngine(engine);
  }
  
  public String getAlgorithmName()
  {
    return "Skein-MAC-" + engine.getBlockSize() * 8 + "-" + engine.getOutputSize() * 8;
  }
  



  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    SkeinParameters skeinParameters;
    


    if ((params instanceof SkeinParameters))
    {
      skeinParameters = (SkeinParameters)params;
    } else { SkeinParameters skeinParameters;
      if ((params instanceof KeyParameter))
      {
        skeinParameters = new SkeinParameters.Builder().setKey(((KeyParameter)params).getKey()).build();

      }
      else
      {
        throw new IllegalArgumentException("Invalid parameter passed to Skein MAC init - " + params.getClass().getName()); } }
    SkeinParameters skeinParameters;
    if (skeinParameters.getKey() == null)
    {
      throw new IllegalArgumentException("Skein MAC requires a key parameter.");
    }
    engine.init(skeinParameters);
  }
  
  public int getMacSize()
  {
    return engine.getOutputSize();
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
