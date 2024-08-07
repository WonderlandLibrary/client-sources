package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.Digest;








public class DigestRandomGenerator
  implements RandomGenerator
{
  private static long CYCLE_COUNT = 10L;
  
  private long stateCounter;
  
  private long seedCounter;
  
  private Digest digest;
  private byte[] state;
  private byte[] seed;
  
  public DigestRandomGenerator(Digest digest)
  {
    this.digest = digest;
    
    seed = new byte[digest.getDigestSize()];
    seedCounter = 1L;
    
    state = new byte[digest.getDigestSize()];
    stateCounter = 1L;
  }
  
  public void addSeedMaterial(byte[] inSeed)
  {
    synchronized (this)
    {
      digestUpdate(inSeed);
      digestUpdate(seed);
      digestDoFinal(seed);
    }
  }
  
  public void addSeedMaterial(long rSeed)
  {
    synchronized (this)
    {
      digestAddCounter(rSeed);
      digestUpdate(seed);
      
      digestDoFinal(seed);
    }
  }
  
  public void nextBytes(byte[] bytes)
  {
    nextBytes(bytes, 0, bytes.length);
  }
  
  public void nextBytes(byte[] bytes, int start, int len)
  {
    synchronized (this)
    {
      int stateOff = 0;
      
      generateState();
      
      int end = start + len;
      for (int i = start; i != end; i++)
      {
        if (stateOff == state.length)
        {
          generateState();
          stateOff = 0;
        }
        bytes[i] = state[(stateOff++)];
      }
    }
  }
  
  private void cycleSeed()
  {
    digestUpdate(seed);
    digestAddCounter(seedCounter++);
    
    digestDoFinal(seed);
  }
  
  private void generateState()
  {
    digestAddCounter(stateCounter++);
    digestUpdate(state);
    digestUpdate(seed);
    
    digestDoFinal(state);
    
    if (stateCounter % CYCLE_COUNT == 0L)
    {
      cycleSeed();
    }
  }
  
  private void digestAddCounter(long seed)
  {
    for (int i = 0; i != 8; i++)
    {
      digest.update((byte)(int)seed);
      seed >>>= 8;
    }
  }
  
  private void digestUpdate(byte[] inSeed)
  {
    digest.update(inSeed, 0, inSeed.length);
  }
  
  private void digestDoFinal(byte[] result)
  {
    digest.doFinal(result, 0);
  }
}
