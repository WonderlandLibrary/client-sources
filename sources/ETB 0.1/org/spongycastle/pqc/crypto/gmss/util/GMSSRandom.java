package org.spongycastle.pqc.crypto.gmss.util;

import org.spongycastle.crypto.Digest;













public class GMSSRandom
{
  private Digest messDigestTree;
  
  public GMSSRandom(Digest messDigestTree2)
  {
    messDigestTree = messDigestTree2;
  }
  









  public byte[] nextSeed(byte[] outseed)
  {
    byte[] rand = new byte[outseed.length];
    messDigestTree.update(outseed, 0, outseed.length);
    rand = new byte[messDigestTree.getDigestSize()];
    messDigestTree.doFinal(rand, 0);
    

    addByteArrays(outseed, rand);
    addOne(outseed);
    


    return rand;
  }
  

  private void addByteArrays(byte[] a, byte[] b)
  {
    byte overflow = 0;
    

    for (int i = 0; i < a.length; i++)
    {
      int temp = (0xFF & a[i]) + (0xFF & b[i]) + overflow;
      a[i] = ((byte)temp);
      overflow = (byte)(temp >> 8);
    }
  }
  

  private void addOne(byte[] a)
  {
    byte overflow = 1;
    

    for (int i = 0; i < a.length; i++)
    {
      int temp = (0xFF & a[i]) + overflow;
      a[i] = ((byte)temp);
      overflow = (byte)(temp >> 8);
    }
  }
}
