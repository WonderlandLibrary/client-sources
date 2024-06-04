package org.spongycastle.crypto.tls;




class DTLSReplayWindow
{
  private static final long VALID_SEQ_MASK = 281474976710655L;
  


  private static final long WINDOW_SIZE = 64L;
  

  private long latestConfirmedSeq = -1L;
  private long bitmap = 0L;
  


  DTLSReplayWindow() {}
  


  boolean shouldDiscard(long seq)
  {
    if ((seq & 0xFFFFFFFFFFFF) != seq)
    {
      return true;
    }
    
    if (seq <= latestConfirmedSeq)
    {
      long diff = latestConfirmedSeq - seq;
      if (diff >= 64L)
      {
        return true;
      }
      if ((bitmap & 1L << (int)diff) != 0L)
      {
        return true;
      }
    }
    
    return false;
  }
  





  void reportAuthenticated(long seq)
  {
    if ((seq & 0xFFFFFFFFFFFF) != seq)
    {
      throw new IllegalArgumentException("'seq' out of range");
    }
    
    if (seq <= latestConfirmedSeq)
    {
      long diff = latestConfirmedSeq - seq;
      if (diff < 64L)
      {
        bitmap |= 1L << (int)diff;
      }
    }
    else
    {
      long diff = seq - latestConfirmedSeq;
      if (diff >= 64L)
      {
        bitmap = 1L;
      }
      else
      {
        bitmap <<= (int)diff;
        bitmap |= 1L;
      }
      latestConfirmedSeq = seq;
    }
  }
  



  void reset()
  {
    latestConfirmedSeq = -1L;
    bitmap = 0L;
  }
}
