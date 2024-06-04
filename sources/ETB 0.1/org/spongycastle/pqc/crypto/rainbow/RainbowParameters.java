package org.spongycastle.pqc.crypto.rainbow;

import org.spongycastle.crypto.CipherParameters;


















public class RainbowParameters
  implements CipherParameters
{
  private final int[] DEFAULT_VI = { 6, 12, 17, 22, 33 };
  


  private int[] vi;
  


  public RainbowParameters()
  {
    vi = DEFAULT_VI;
  }
  






  public RainbowParameters(int[] vi)
  {
    this.vi = vi;
    try
    {
      checkParams();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void checkParams()
    throws Exception
  {
    if (vi == null)
    {
      throw new Exception("no layers defined.");
    }
    if (vi.length > 1)
    {
      for (int i = 0; i < vi.length - 1; i++)
      {
        if (vi[i] >= vi[(i + 1)])
        {
          throw new Exception("v[i] has to be smaller than v[i+1]");
        }
        
      }
      
    }
    else {
      throw new Exception("Rainbow needs at least 1 layer, such that v1 < v2.");
    }
  }
  






  public int getNumOfLayers()
  {
    return vi.length - 1;
  }
  





  public int getDocLength()
  {
    return vi[(vi.length - 1)] - vi[0];
  }
  





  public int[] getVi()
  {
    return vi;
  }
}
