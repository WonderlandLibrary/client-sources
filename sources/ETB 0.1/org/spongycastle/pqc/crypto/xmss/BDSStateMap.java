package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.spongycastle.util.Integers;

public class BDSStateMap
  implements Serializable
{
  private final Map<Integer, BDS> bdsState = new TreeMap();
  


  BDSStateMap() {}
  

  BDSStateMap(XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed)
  {
    for (long index = 0L; index < globalIndex; index += 1L)
    {
      updateState(params, index, publicSeed, secretKeySeed);
    }
  }
  
  BDSStateMap(BDSStateMap stateMap, XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed)
  {
    for (Iterator it = bdsState.keySet().iterator(); it.hasNext();)
    {
      Integer key = (Integer)it.next();
      
      bdsState.put(key, bdsState.get(key));
    }
    
    updateState(params, globalIndex, publicSeed, secretKeySeed);
  }
  
  private void updateState(XMSSMTParameters params, long globalIndex, byte[] publicSeed, byte[] secretKeySeed)
  {
    XMSSParameters xmssParams = params.getXMSSParameters();
    int xmssHeight = xmssParams.getHeight();
    



    long indexTree = XMSSUtil.getTreeIndex(globalIndex, xmssHeight);
    int indexLeaf = XMSSUtil.getLeafIndex(globalIndex, xmssHeight);
    

    OTSHashAddress otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
    

    if (indexLeaf < (1 << xmssHeight) - 1)
    {
      if ((get(0) == null) || (indexLeaf == 0))
      {
        put(0, new BDS(xmssParams, publicSeed, secretKeySeed, otsHashAddress));
      }
      
      update(0, publicSeed, secretKeySeed, otsHashAddress);
    }
    

    for (int layer = 1; layer < params.getLayers(); layer++)
    {

      indexLeaf = XMSSUtil.getLeafIndex(indexTree, xmssHeight);
      indexTree = XMSSUtil.getTreeIndex(indexTree, xmssHeight);
      

      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(layer)).withTreeAddress(indexTree)).withOTSAddress(indexLeaf).build();
      

      if ((indexLeaf < (1 << xmssHeight) - 1) && 
        (XMSSUtil.isNewAuthenticationPathNeeded(globalIndex, xmssHeight, layer)))
      {
        if (get(layer) == null)
        {
          put(layer, new BDS(params.getXMSSParameters(), publicSeed, secretKeySeed, otsHashAddress));
        }
        
        update(layer, publicSeed, secretKeySeed, otsHashAddress);
      }
    }
  }
  
  void setXMSS(XMSSParameters xmss)
  {
    for (Iterator it = bdsState.keySet().iterator(); it.hasNext();)
    {
      Integer key = (Integer)it.next();
      
      BDS bds = (BDS)bdsState.get(key);
      bds.setXMSS(xmss);
      bds.validate();
    }
  }
  
  public boolean isEmpty()
  {
    return bdsState.isEmpty();
  }
  
  public BDS get(int index)
  {
    return (BDS)bdsState.get(Integers.valueOf(index));
  }
  
  public BDS update(int index, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress)
  {
    return (BDS)bdsState.put(Integers.valueOf(index), ((BDS)bdsState.get(Integers.valueOf(index))).getNextState(publicSeed, secretKeySeed, otsHashAddress));
  }
  
  public void put(int index, BDS bds)
  {
    bdsState.put(Integers.valueOf(index), bds);
  }
}
