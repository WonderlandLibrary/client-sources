package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class Tables64kGCMMultiplier implements GCMMultiplier
{
  private byte[] H;
  private int[][][] M;
  
  public Tables64kGCMMultiplier() {}
  
  public void init(byte[] H) {
    if (M == null)
    {
      M = new int[16]['Ā'][4];
    }
    else if (Arrays.areEqual(this.H, H))
    {
      return;
    }
    
    this.H = Arrays.clone(H);
    

    GCMUtil.asInts(H, M[0]['']);
    
    for (int j = 64; j >= 1; j >>= 1)
    {
      GCMUtil.multiplyP(M[0][(j + j)], M[0][j]);
    }
    
    int i = 0;
    for (;;)
    {
      for (int j = 2; j < 256; j += j)
      {
        for (int k = 1; k < j; k++)
        {
          GCMUtil.xor(M[i][j], M[i][k], M[i][(j + k)]);
        }
      }
      
      i++; if (i == 16)
      {
        return;
      }
      

      for (int j = 128; j > 0; j >>= 1)
      {
        GCMUtil.multiplyP8(M[(i - 1)][j], M[i][j]);
      }
    }
  }
  


  public void multiplyH(byte[] x)
  {
    int[] z = new int[4];
    for (int i = 15; i >= 0; i--)
    {

      int[] m = M[i][(x[i] & 0xFF)];
      z[0] ^= m[0];
      z[1] ^= m[1];
      z[2] ^= m[2];
      z[3] ^= m[3];
    }
    
    org.spongycastle.util.Pack.intToBigEndian(z, x, 0);
  }
}
