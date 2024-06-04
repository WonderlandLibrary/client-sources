package org.spongycastle.crypto.prng;




public class ReversedWindowGenerator
  implements RandomGenerator
{
  private final RandomGenerator generator;
  


  private byte[] window;
  

  private int windowCount;
  


  public ReversedWindowGenerator(RandomGenerator generator, int windowSize)
  {
    if (generator == null)
    {
      throw new IllegalArgumentException("generator cannot be null");
    }
    if (windowSize < 2)
    {
      throw new IllegalArgumentException("windowSize must be at least 2");
    }
    
    this.generator = generator;
    window = new byte[windowSize];
  }
  






  public void addSeedMaterial(byte[] seed)
  {
    synchronized (this)
    {
      windowCount = 0;
      generator.addSeedMaterial(seed);
    }
  }
  






  public void addSeedMaterial(long seed)
  {
    synchronized (this)
    {
      windowCount = 0;
      generator.addSeedMaterial(seed);
    }
  }
  






  public void nextBytes(byte[] bytes)
  {
    doNextBytes(bytes, 0, bytes.length);
  }
  










  public void nextBytes(byte[] bytes, int start, int len)
  {
    doNextBytes(bytes, start, len);
  }
  



  private void doNextBytes(byte[] bytes, int start, int len)
  {
    synchronized (this)
    {
      int done = 0;
      while (done < len)
      {
        if (windowCount < 1)
        {
          generator.nextBytes(window, 0, window.length);
          windowCount = window.length;
        }
        
        bytes[(start + done++)] = window[(--windowCount)];
      }
    }
  }
}
