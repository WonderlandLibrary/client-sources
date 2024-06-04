package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.util.Arrays;






























public class GMSSParameters
{
  private int numOfLayers;
  private int[] heightOfTrees;
  private int[] winternitzParameter;
  private int[] K;
  
  public GMSSParameters(int layers, int[] heightOfTrees, int[] winternitzParameter, int[] K)
    throws IllegalArgumentException
  {
    init(layers, heightOfTrees, winternitzParameter, K);
  }
  

  private void init(int layers, int[] heightOfTrees, int[] winternitzParameter, int[] K)
    throws IllegalArgumentException
  {
    boolean valid = true;
    String errMsg = "";
    numOfLayers = layers;
    if ((numOfLayers != winternitzParameter.length) || (numOfLayers != heightOfTrees.length) || (numOfLayers != K.length))
    {


      valid = false;
      errMsg = "Unexpected parameterset format";
    }
    for (int i = 0; i < numOfLayers; i++)
    {
      if ((K[i] < 2) || ((heightOfTrees[i] - K[i]) % 2 != 0))
      {
        valid = false;
        errMsg = "Wrong parameter K (K >= 2 and H-K even required)!";
      }
      
      if ((heightOfTrees[i] < 4) || (winternitzParameter[i] < 2))
      {
        valid = false;
        errMsg = "Wrong parameter H or w (H > 3 and w > 1 required)!";
      }
    }
    
    if (valid)
    {
      this.heightOfTrees = Arrays.clone(heightOfTrees);
      this.winternitzParameter = Arrays.clone(winternitzParameter);
      this.K = Arrays.clone(K);
    }
    else
    {
      throw new IllegalArgumentException(errMsg);
    }
  }
  
  public GMSSParameters(int keySize)
    throws IllegalArgumentException
  {
    if (keySize <= 10)
    {
      int[] defh = { 10 };
      int[] defw = { 3 };
      int[] defk = { 2 };
      init(defh.length, defh, defw, defk);
    }
    else if (keySize <= 20)
    {
      int[] defh = { 10, 10 };
      int[] defw = { 5, 4 };
      int[] defk = { 2, 2 };
      init(defh.length, defh, defw, defk);
    }
    else
    {
      int[] defh = { 10, 10, 10, 10 };
      int[] defw = { 9, 9, 9, 3 };
      int[] defk = { 2, 2, 2, 2 };
      init(defh.length, defh, defw, defk);
    }
  }
  





  public int getNumOfLayers()
  {
    return numOfLayers;
  }
  





  public int[] getHeightOfTrees()
  {
    return Arrays.clone(heightOfTrees);
  }
  







  public int[] getWinternitzParameter()
  {
    return Arrays.clone(winternitzParameter);
  }
  





  public int[] getK()
  {
    return Arrays.clone(K);
  }
}
