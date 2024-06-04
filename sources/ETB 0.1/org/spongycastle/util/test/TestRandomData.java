package org.spongycastle.util.test;

import org.spongycastle.util.encoders.Hex;









public class TestRandomData
  extends FixedSecureRandom
{
  public TestRandomData(String encoding)
  {
    super(new FixedSecureRandom.Source[] { new FixedSecureRandom.Data(Hex.decode(encoding)) });
  }
  





  public TestRandomData(byte[] encoding)
  {
    super(new FixedSecureRandom.Source[] { new FixedSecureRandom.Data(encoding) });
  }
}
