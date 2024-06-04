package org.spongycastle.asn1;

import java.io.InputStream;






abstract class LimitedInputStream
  extends InputStream
{
  protected final InputStream _in;
  private int _limit;
  
  LimitedInputStream(InputStream in, int limit)
  {
    _in = in;
    _limit = limit;
  }
  

  int getRemaining()
  {
    return _limit;
  }
  
  protected void setParentEofDetect(boolean on)
  {
    if ((_in instanceof IndefiniteLengthInputStream))
    {
      ((IndefiniteLengthInputStream)_in).setEofOn00(on);
    }
  }
}
