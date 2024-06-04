package org.spongycastle.crypto.prng;



public class ThreadedSeedGenerator
{
  public ThreadedSeedGenerator() {}
  


  private class SeedGenerator
    implements Runnable
  {
    private volatile int counter = 0;
    private volatile boolean stop = false;
    
    private SeedGenerator() {}
    
    public void run() { while (!stop)
      {
        counter += 1;
      }
    }
    



    public byte[] generateSeed(int numbytes, boolean fast)
    {
      Thread t = new Thread(this);
      byte[] result = new byte[numbytes];
      counter = 0;
      stop = false;
      int last = 0;
      

      t.start();
      int end; int end; if (fast)
      {
        end = numbytes;
      }
      else
      {
        end = numbytes * 8;
      }
      for (int i = 0; i < end; i++)
      {
        while (counter == last)
        {
          try
          {
            Thread.sleep(1L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
        


        last = counter;
        if (fast)
        {
          result[i] = ((byte)(last & 0xFF));
        }
        else
        {
          int bytepos = i / 8;
          result[bytepos] = ((byte)(result[bytepos] << 1 | last & 0x1));
        }
      }
      
      stop = true;
      return result;
    }
  }
  













  public byte[] generateSeed(int numBytes, boolean fast)
  {
    SeedGenerator gen = new SeedGenerator(null);
    
    return gen.generateSeed(numBytes, fast);
  }
}
